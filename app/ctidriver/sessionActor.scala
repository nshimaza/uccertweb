/**
  * Copyright (c) 2016 Naoto Shimazaki
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
  * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
  * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
  * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all copies or substantial
  * portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
  * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
  * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
  * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
  * DEALINGS IN THE SOFTWARE.
  */

package ctidriver

import java.net.InetSocketAddress

import akka.actor.{Props, Actor}
import akka.io.{Tcp, IO}
import akka.util.ByteString
import ctidriver.MessageType._
import ctidriver.Tag._

import scala.collection.immutable.BitSet

/**
  * Created by nshimaza on 2016/03/11.
  */
class SessionActorImpl(server: InetSocketAddress, filter: MessageFilter) extends Actor {
  import context.system

  private val listener = context.parent
  private var packetizer = Packetizer()

  IO(Tcp) ! Tcp.Connect(server)

  def receive = {
    case Tcp.CommandFailed(c: Tcp.Connect) =>
      listener ! SessionActorProtocol.ConnectSocketFailed
      context stop self

    case c @ Tcp.Connected(remote, local) =>
      val connection = sender()
      connection ! Tcp.Register(self)
      connection ! Tcp.Write((List((MessageTypeTag, Some(OPEN_REQ)), (InvokeID, InvokeIDGen.next())) ++
        SessionActorImpl.openReqBody).encode.withLength)

      context become {
        case c: Tcp.ConnectionClosed =>
          listener ! SessionActorProtocol.SocketClosed
          context stop self

        case Tcp.Received(openingData) =>
          packetizer = packetizer(openingData)
          if (packetizer.isOutOfSync) {
            listener ! SessionActorProtocol.SocketOutOfSync
            context stop self
          } else {
            for (openReqReply <- packetizer.packets) {
              val msg = openReqReply.decode
              msg.findEnum[MessageType](MessageTypeTag) match {
                case Some(OPEN_CONF) =>
                  listener ! SessionActorProtocol.SessionEstablished

                  context become {
                    case Tcp.Received(data) =>
                      packetizer = packetizer(data)
                      if (packetizer.isOutOfSync) {
                        listener ! SessionActorProtocol.SocketOutOfSync
                        context stop self
                      } else {
                        for (packet <- packetizer.packets) {
                          lazy val msg = packet.decode
                          filter(packet).foreach(_ (msg))
                        }
                      }

                    case SessionActorProtocol.SendMessage(m) =>
                      connection ! Tcp.Write(m.encode.withLength)

                    case c: Tcp.ConnectionClosed =>
                      listener ! SessionActorProtocol.SocketClosed
                      context stop self
                  }

                case _ =>
                  connection ! Tcp.Close
                  listener ! SessionActorProtocol.OpenFailed
                  context stop self
              }
            }
          }

      }
  }
}

object SessionActorProtocol {
  trait SessionAborted
  case object ConnectSocketFailed extends SessionAborted
  case object SocketClosed extends SessionAborted
  case object OpenFailed extends SessionAborted
  case object SocketOutOfSync extends SessionAborted
  case object AbortSession
  case object SessionEstablished
  case class MessageReceived(m: Message)
  case class SendMessage(m: Message)
}

object SessionActorImpl {
  val openReqBody = List((VersionNumber, ProtocolVersion), (IdleTimeout, NoIdleTimeout), (PeripheralID, 5000),
    (ServiceRequested, BitSet.empty + CtiServiceMask.ALL_EVENTS),
    (CallMsgMask, BitSet.empty),
    (AgentStateMaskTag, BitSet.empty +
      AgentStateMask.AGENT_LOGIN +
      AgentStateMask.AGENT_LOGOUT +
      AgentStateMask.AGENT_NOT_READY +
      AgentStateMask.AGENT_AVAILABLE +
      AgentStateMask.AGENT_TALKING +
      AgentStateMask.AGENT_WORK_NOT_READY +
      AgentStateMask.AGENT_WORK_READY +
      AgentStateMask.AGENT_BUSY_OTHER +
      AgentStateMask.AGENT_RESERVED +
      AgentStateMask.AGENT_HOLD),
    (ConfigMsgMask, BitSet.empty), (Reserved1, 0), (Reserved2, 0), (Reserved3, 0),
    (CLIENT_ID, "UCCERTWeb"), (CLIENT_PASSWORD, ByteString()), (CLIENT_SIGNATURE, "UCCERTWeb"))
}

trait SessionActorPropsFactory {
  def apply(server: InetSocketAddress, filter: MessageFilter): Props

}

class SessionActorImplPropsFactory extends SessionActorPropsFactory {
  def apply(server: InetSocketAddress, filter: MessageFilter) = {
    Props(classOf[SessionActorImpl], server, filter)
  }
}
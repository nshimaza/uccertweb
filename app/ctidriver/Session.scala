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
import javax.inject.Inject

import akka.actor.{ActorContext, Actor, ActorRef, Props}
import akka.util.ByteString
import com.google.inject.assistedinject.Assisted
import ctidriver.MessageType._
import ctidriver.Tag._

import scala.collection.immutable.BitSet

/**
  * Use this class instance only within an Actor.
  * This code is not thread safe.  Never call single instance from multiple threads.
  */
class Session(server: InetSocketAddress, socketActorPropsFactory: SocketActorPropsFactory)
  extends Actor {

  val socketActor = context.actorOf(socketActorPropsFactory(server))

  def connecting: Receive = {
    case akka.io.Tcp.Connected =>
      val msg = List((MessageTypeTag, Some(OPEN_REQ)), (InvokeID, InvokeIDGen.next())) ++ Session.openReqBody
      socketActor ! SessionProtocol.Send(msg.encode.withLength)
      context.become(opening)

    case akka.io.Tcp.CommandFailed =>
      context stop self
  }

  def opening: Receive = {
    case SessionProtocol.Received(data) =>
      val msg = data.decode
      msg.findEnum(MessageTypeTag) match {
        case Some(OPEN_CONF) =>
          context.become(established)

        case _ =>
          socketActor ! SessionProtocol.Close
      }

    case _ =>
      context stop self
  }

  def established: Receive = {
    case SessionProtocol.Received =>
  }

  def receive = connecting
}

trait SessionPropsFactory {
  def apply(): Props
}

class SessionImplPropsFactory @Inject()(@Assisted server: InetSocketAddress,
                                        socketActorPropsFactory: SocketActorPropsFactory)
  extends SessionPropsFactory {
  def apply() = {
    Props(classOf[Session], server, socketActorPropsFactory)
  }
}


object SessionProtocol {
  case class Received(data: ByteString)
  case class Send(data: ByteString)
  case object Close
}

object Session {
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

/*
class XXXXXPacketizerOldDoNotUse(listener: ByteString => Unit) {

  object State extends Enumeration {
    val WAIT_LENGTH, WAIT_BODY = Value
  }

  var buf = ByteString.empty
  var state = State.WAIT_LENGTH
  var offset = -4

  def receive(data: ByteString) = {
    offset = offset + data.size
    buf = buf ++ data

    while (offset >= 0) {
      state match {
        case State.WAIT_LENGTH =>
          // at the state WAIT_LENGTH, the head of buf is aligned to message length field
          // now you have plenty bytes to decode message length
          val message_length = buf.toInt
          if (message_length < 0 || message_length > MaxMessageLen) {
            val msg = s"Message length $message_length out of defined range (0 - $MaxMessageLen) decoded." +
              "  Potential socket out of sync."
            ctilog.error(msg)
            throw new java.io.SyncFailedException(msg)
          }
          buf = buf.drop(4)
          // here you have buf which contains message aligned to Message Type field at the head
          // let's set next state for waiting body
          state = State.WAIT_BODY

          // the message length doesn't include Message Type filed so you need to wait 4 more bytes
          offset = buf.size - (message_length + 4)

        case State.WAIT_BODY =>
          // at the state WAIT_BODY, the head of buf is aligned to message type field
          // now you have at least one entire message in buf

          // take single message to packet
          val len = buf.size - offset
          val packet = buf.take(len)

          buf = buf.drop(len)
          // here you have buf which is aligned to next message length
          // let's set next state for waiting message length
          // we need 4 bytes to continue
          state = State.WAIT_LENGTH
          offset = buf.size - 4

          listener(packet)
      }
    }
  }
}
*/


/*
trait UsesSocketActor { val socketActor: ActorRef }

trait MixInSocketActorSample {
  object SocketActorInitializer {
    val server = new InetSocketAddress("localhost", DefaultCtiServerPortA)
    val handler: Message => Unit = (m) => Unit
    val filter_entry = MessageFilterEntry(Set(AGENT_STATE_EVENT), handler)
    val filter = MessageFilter(Seq(filter_entry))
    val props = Props(classOf[SocketActorImpl], server, filter)
  }
//  val socketActor: ActorRef = system.actorOf(SocketActorInitializer.props)
}

class SocketActorImpl(server: InetSocketAddress, filter: MessageFilter) extends Actor {
  import akka.io.Tcp._
  import context.system

  val packetizer = Packetizer1()

  IO(Tcp) ! Connect(server)

  def receive = {
    case CommandFailed(_: Connect) =>
      println("connect to cti server failed.")
      context stop self

    case c @ Connected(remote, local) =>
      val connection = sender()
      connection ! Register(self)
      context become {
        case data: ByteString => connection ! Write(data.withlength)
        case SessionProtocol.Send(data) => connection ! Write(data.withlength)
        case _: ConnectionClosed => context stop self
        case SessionProtocol.Close => connection ! Tcp.Close

        case Received(data) =>
          for (packet <- packetizer(data)) {
            lazy val msg = packet.decode
            filter(packet).foreach(_(msg))
          }
      }
  }
}
*/
/*
object foo {
  import ctidriver.Tag._
  val handler1: Message => Unit = (m) => { println("handler1 called.  msg: " + m) ; Unit }
  val handler2: Message => Unit = (m) => { println("handler2 called.  msg: " + m) ; Unit }
  val filter_entry1 = MessageFilterEntry(handler1, Set(FAILURE_EVENT))
  val filter_entry2 = MessageFilterEntry(handler2, Set(FAILURE_EVENT))
  val filter = MessageFilter(Seq(filter_entry1, filter_entry2))
  val packet = List((MessageTypeTag, Some(FAILURE_EVENT)), (Status, Some(StatusCode.UNSPECIFIED_FAILURE))).encode

  def act() = {
    lazy val msg = packet.decode
    filter(packet).foreach(_(msg))
  }
}

object bar {
  def body(n: Int) = (1 to n).sum
  def act(n: Int, i: Int) = {
    val start1 = System.nanoTime()
    val r1 = (1 to i).map(e => body(n)).sum
    val end1 = System.nanoTime()
    println(r1 + "  elapsed " + (end1 - start1))

    val a = body(n)
    val start2 = System.nanoTime()
    val r2 = (1 to i).map(e => a).sum
    val end2 = System.nanoTime()
    println(r2 + "  elapsed " + (end2 - start2))
  }
}
*/

/*
class SocketActorOld(cti_server: InetSocketAddress, listener: ActorRef) extends Actor {
  import akka.io.Tcp._
  import context.system

  val packetizer = new XXXXXPacketizerOldDoNotUse(listener ! _)

  IO(Tcp) ! Connect(cti_server)

  def receive = {
    case CommandFailed(_: Connect) =>
      println("connect to cti server failed.")
      context stop self

    case c @ Connected(remote, local) =>
      val connection = sender()
      connection ! Register(self)
      context become {
        case Received(data) => packetizer receive data
        case data: ByteString => connection ! Write(data.withlength)
        case SessionProtocol.Send(data) => connection ! Write(data.withlength)
        case _: ConnectionClosed => context stop self
        case SessionProtocol.Close => connection ! Tcp.Close
      }
      listener ! c

  }

}
*/




/**
  * Packetizing binary stream from TCP into entire CTI Server Protocol message.
  */
/*
class PacketizerActor(packet_listener: ActorRef) extends Actor {

  object State extends Enumeration {
    val WAIT_LENGTH, WAIT_BODY = Value
  }

  var buf = ByteString.empty
  var state = State.WAIT_LENGTH
  var offset = -4

  def receive = {
    case data: ByteString =>
      offset = offset + data.size
      buf = buf ++ data

      while (offset >= 0) {
        state match {
          case State.WAIT_LENGTH =>
            // at the state WAIT_LENGTH, the head of buf is aligned to message length field
            // now you have plenty bytes to decode message length
            val message_length = buf.toInt
            buf = buf.drop(4)
            // here you have buf which contains message aligned to Message Type field at the head
            // let's set next state for waiting body
            state = State.WAIT_BODY

            // the message length doesn't include Message Type filed so you need to wait 4 more bytes
            offset = buf.size - (message_length + 4)

          case State.WAIT_BODY =>
            // at the state WAIT_BODY, the head of buf is aligned to message type field
            // now you have at least one entire message in buf

            // take single message to packet
            val len = buf.size - offset
            val packet = buf.take(len)

            buf = buf.drop(len)
            // here you have buf which is aligned to next message length
            // let's set next state for waiting message length
            // we need 4 bytes to continue
            state = State.WAIT_LENGTH
            offset = buf.size - 4

            packet_listener ! packet
        }
      }
  }
}
*/
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
import akka.io.Tcp._
import akka.io.{Tcp, IO}
import akka.util.ByteString

/**
  * Created by nshimaza on 2016/03/02.
  */
class SocketActorImpl(server: InetSocketAddress) extends Actor {
  import context.system
  import SocketActorProtocol._

  private val listener = context.parent
  private var packetizer = Packetizer()

  IO(Tcp) ! Connect(server)

  def receive = {
    case CommandFailed(c: Connect) =>
//      listener ! CommandFailed(c)
      listener ! OpenSocketFailed
      context stop self

    case c @ Connected(remote, local) =>
//      listener ! c
      listener ! SocketOpened
      val connection = sender()
      connection ! Register(self)
      context become {
        case Send(data) => connection ! Write(data.withlength)

        case CloseSocket => connection ! Tcp.Close

        case Received(data) =>
          packetizer = packetizer(data)
          packetizer.packets.foreach { listener ! PacketReceived(_) }

        case c: ConnectionClosed =>
          listener ! SocketClosed
          context stop self
      }
  }
}

trait SocketActorPropsFactory {
  def apply(server: InetSocketAddress): Props
}

class SocketActorImplPropsFactory extends SocketActorPropsFactory {
  def apply(server: InetSocketAddress) = {
    Props(classOf[SocketActorImpl], server)
  }
}

object SocketActorProtocol {
  case object OpenSocketFailed
  case object SocketOpened
  case object SocketClosed
  case object CloseSocket
  case class PacketReceived(packet: ByteString)
  case class Send(rawMessageBody: ByteString)
}
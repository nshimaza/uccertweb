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

import akka.actor.{Props, ActorContext, ActorRef, Actor}
import akka.io.Tcp._
import akka.io.{Tcp, IO}

/**
  * Created by nshimaza on 2016/03/02.
  */
trait SocketActor extends Actor {
}

trait SocketActorFactory {
  def apply(context: ActorContext, server: InetSocketAddress, name: String): ActorRef
}

trait UsesSocketActor {
  def socketActorFactory: SocketActorFactory
}

object SocketActorImplFactory extends SocketActorFactory {
  def apply(context: ActorContext, server: InetSocketAddress, name: String): ActorRef = {
    context.actorOf(Props(classOf[SocketActorImpl], server), name)
  }
}

trait MixInSocketActorImpl {
  def socketActorFactory = SocketActorImplFactory
}

class SocketActorImpl(server: InetSocketAddress) extends SocketActor {
  import context.system

  private val listener = context.parent
  private var packetizer = Packetizer()

  IO(Tcp) ! Connect(server)

  def receive = {
    case CommandFailed(c: Connect) =>
      listener ! CommandFailed(c)
      context stop self

    case c @ Connected(remote, local) =>
      listener ! c
      val connection = sender()
      connection ! Register(self)
      context become {
        case SessionProtocol.Send(data) => connection ! Write(data.withlength)

        case SessionProtocol.Close => connection ! Tcp.Close

        case Received(data) =>
          packetizer = packetizer(data)
          packetizer.packets.foreach { listener ! SessionProtocol.Received(_) }

        case c: ConnectionClosed =>
          listener ! c
          context stop self
      }
  }
}


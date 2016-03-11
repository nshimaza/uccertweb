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

import akka.actor.{ Actor, ActorRef, Props, PoisonPill }
import akka.event.Logging
import akka.io.{ IO, Tcp, TcpSO }
import akka.io.Tcp._
import akka.util.ByteString


/**
  * FakeCtiServer supports only one TCP client at a time.
  * New connection from another client results unknown behavior.
  */

class MockCtiServer(port: Int) extends Actor {
  import MockCtiServerProtocol._
  import context.system

  var scenario: List[ByteString] = List()
  var curr = scenario
  var handler: Option[ActorRef] = None
  var probe: Option[ActorRef] = None

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", port))

  def receive = {
    case b @ Bound(localAddress) =>
//      println("FakeCtiServer started to listen")

    case CommandFailed(_: Bind) => context stop self

    case c @ Connected(remote, local) =>
      val peer = sender()
      val new_handler = context.actorOf(Props(classOf[MockCtiServerHandler], peer))
      peer ! Register(new_handler)
      handler.foreach(_ ! PoisonPill)
      handler = Some(new_handler)

    case PeerClosed => handler = None

    case WarmRestart(new_probe) =>
      handler.foreach(_ ! PoisonPill)
      handler = None
      probe.foreach(_ ! PoisonPill)
      probe = Some(new_probe)

    case ClientHandlerReady => probe.foreach(_ ! ClientHandlerReady)

    case Scenario(new_scenario: List[ByteString]) =>
      scenario = new_scenario
      curr = scenario

    case Tick =>
      val packet = Packet(curr.head)
      curr = curr.tail
      handler.foreach(_ ! packet)

    case Packet(data) =>
      probe.foreach(_ ! data)

    case CloseClient =>
      handler.foreach(_ ! CloseClient)





    case Rewind => curr = scenario
//    case AddServerProve(p) => probes = probes + p
//    case RemoveServerProve(p) => probes = probes - p
  }
}

class MockCtiServerHandler(peer: ActorRef) extends Actor {
  import MockCtiServerProtocol._

  case object InitializationDone
  self ! InitializationDone

  def receive = {
    case CloseClient =>
      peer ! Close
      context stop self

    case InitializationDone =>
      context.parent ! ClientHandlerReady

    case Packet(body) =>
      peer ! Write(body)

    case Received(data) =>
      context.parent ! Packet(data)

    case p @ PeerClosed =>
      context.parent ! p
      context stop self
  }
}

object MockCtiServerProtocol {
  case class WarmRestart(new_probe: ActorRef)
  case object ClientHandlerReady
  case class Scenario(scenario: List[ByteString])
  case object Tick
  case object CloseClient



  case class Packet(body: ByteString)
  case class AddServerProve(p: ActorRef)
  case class RemoveServerProve(p: ActorRef)
  case object Rewind
}


class LoopbackServer(port: Int) extends Actor {
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", port))

  def receive = {
    case b @ Bound(localAddress) => // println("LoopbackServer started to listen")
    case CommandFailed(_: Bind) => context stop self
    case c @ Connected(remote, local) => sender() ! Register(context.actorOf(Props[LoopbackServerHandler]))
  }
}

class LoopbackServerHandler extends Actor {
  def receive = {
    case Received(data) => sender() ! Write(data)
    case PeerClosed => context stop self
  }
}

class MockTCPClient(server: InetSocketAddress, listener: ActorRef) extends Actor {
  import context.system

  IO(Tcp) ! Connect(server)

  def receive = {
    case CommandFailed(_: Connect) =>
      listener ! "connect failed"
      context stop self

    case c @ Connected(remote, local) =>
      val connection = sender()
      connection ! Register(self)
      context become {
        case data: ByteString =>
          connection ! Write(data)
        case CommandFailed(w: Write) =>
          // O/S buffer was full
          listener ! "write failed"
        case Received(data) =>
          listener ! data
        case "close" =>
          connection ! Close
        case _: ConnectionClosed =>
          listener ! "connection closed"
          context stop self
      }
      listener ! c
  }
}

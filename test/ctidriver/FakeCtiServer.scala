package ctidriver

import akka.actor.{ Actor, ActorRef, Props }
import akka.io.{ IO, Tcp, TcpSO }
import akka.io.Tcp._
import akka.util.ByteString
import java.net.InetSocketAddress

/**
  * FakeCtiServer supports only one TCP client at a time.
  * New connection from another client results unknown behavior.
  */

object FakeCtiServerProtocol {
  case class Scenario(scenario: List[ByteString])
  case class Packet(body: ByteString)
  case class AddServerProve(p: ActorRef)
  case class RemoveServerProve(p: ActorRef)
  case object ClientHandlerReady
  case object Rewind
  case object Tick
}

class FakeCtiServer(port: Int) extends Actor {
  import FakeCtiServerProtocol._
  import context.system

  var scenario: List[ByteString] = List()
  var curr = scenario
  var handlers: Set[ActorRef] = Set.empty
  var probes: Set[ActorRef] = Set.empty


  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", port))

  def receive = {
    case b @ Bound(localAddress) =>
      println("FakeCtiServer started to listen")

    case CommandFailed(_: Bind) => context stop self

    case c @ Connected(remote, local) =>
      val peer = sender()
      val handler = context.actorOf(Props(new FakeCtiServerHandler(self, peer)))
      peer ! Register(handler)
      handlers = handlers + handler

    case PeerClosed => handlers = handlers - sender()

    case AddServerProve(p) => probes = probes + p
    case RemoveServerProve(p) => probes = probes - p
    case ClientHandlerReady => probes.foreach(p => p ! ClientHandlerReady)

    case Scenario(new_scenario: List[ByteString]) =>
      scenario = new_scenario
      curr = scenario

    case Rewind => curr = scenario

    case Tick =>
      val packet = Packet(curr.head)
      curr = curr.tail
      handlers.foreach(p => p ! packet)
  }
}

class FakeCtiServerHandler(parent: ActorRef, peer: ActorRef) extends Actor {
  import FakeCtiServerProtocol._

  case object InitializationDone
  self ! InitializationDone

  def receive = {
    case InitializationDone =>
      parent ! ClientHandlerReady

    case Packet(body) =>
      peer ! Write(body)

    case Received(data) =>
//      println("FakeCtiServerHander received data from client (ignored):", data)

    case p @ PeerClosed =>
      parent ! p
      context stop self
  }
}

class LoopbackServer(port: Int) extends Actor {
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", port))

  def receive = {
    case b @ Bound(localAddress) => println("LoopbackServer started to listen")
    case CommandFailed(_: Bind) => context stop self
    case c @ Connected(remote, local) => sender() ! Register(context.actorOf(Props[LoopbackHandler]))
  }
}

class LoopbackHandler extends Actor {
  def receive = {
    case Received(data) => sender() ! Write(data)
    case PeerClosed => context stop self
  }
}

class TCPClient(server: InetSocketAddress, listener: ActorRef) extends Actor {
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

package ctidriver

import akka.actor.{ Actor, ActorRef, Props }
import akka.io.{ IO, Tcp }
import akka.io.Tcp._
import akka.util.ByteString
import java.net.InetSocketAddress

/**
  * Created by nshimaza on 2016/01/21.
  */
class FakeCtiServer(scenario: Seq[ByteString]) {
  var curr = scenario
  var peers: Set[ActorRef] = Set.empty

  class Server extends Actor {
    import context.system

    IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 42027))

    def receive = {
      case b @ Bound(localAddress) =>
        println("FakeCtiServer started to listen")

      case CommandFailed(_: Bind) =>
        context stop self

      case c @ Connected(remote, local) =>
        val handler = context.actorOf(Props[Handler])
        val connection = sender()
        connection ! Register(handler)
        peers = peers + connection
    }
  }

  class Handler extends Actor {
    def receive = {
      case Received(data) =>
        println("received:", data)

      case PeerClosed =>
        peers = peers - sender()
        context stop self
    }
  }

  def rewind() = { curr = scenario }

  def tick() = {
    val packet = curr.head
    curr = curr.tail
    peers.foreach(p => p ! Write(packet))
  }
}

class LoopbackServer extends Actor {
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 42027))

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

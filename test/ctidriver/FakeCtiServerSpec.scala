package ctidriver

import akka.actor.{ Actor, ActorRef, ActorSystem, Props }
import akka.io.{ IO, Tcp }
import akka.io.Tcp._
import akka.testkit.{ TestActorRef, TestActors, TestKit, TestProbe }
import akka.util.ByteString
import org.scalatest.{ WordSpecLike, MustMatchers, BeforeAndAfterAll }
import scala.concurrent.duration._
import scala.concurrent.Future
import java.net.InetSocketAddress

/**
  * Created by nshimaza on 2016/01/27.
  */


class TCPClient(remote: InetSocketAddress, listener: ActorRef) extends Actor {
  import context.system

  IO(Tcp) ! Connect(remote)

  def receive = {
    case CommandFailed(_: Connect) =>
      listener ! "connect failed"
      context stop self

    case c @ Connected(remote, local) =>
      listener ! c
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
  }
}

class LoopbackServerSpec(_system: ActorSystem) extends TestKit(_system)
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("FakeServerSpec"))

  val server = system.actorOf(Props[LoopbackServer])

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "LoopbackServer" must {
    "accept connection from TCP client" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", 42027), probe.ref)))

      val msg = probe.expectMsgClass(1.second, classOf[Connected])

      msg.remoteAddress.getHostName must be("localhost")
      msg.remoteAddress.getPort must be(42027)
      msg.localAddress.getHostName must be("localhost")
    }

    "echo back what TCP client sent" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", 42027), probe.ref)))

      probe.expectMsgClass(1.second, classOf[Connected])

      client ! ByteString("Hello World.")
      probe.expectMsg(1.second, ByteString("Hello World."))
    }


  }
}
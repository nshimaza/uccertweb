package ctidriver

import akka.actor.{ Actor, ActorRef, ActorSystem, Props }
import akka.io.{ IO, Tcp }
import akka.io.Tcp._
import akka.testkit.{ TestActorRef, TestActors, TestKit, TestProbe }
import akka.util.ByteString
import ctidriver.FakeCtiServerProtocol.Scenario
import org.scalatest.{ WordSpecLike, MustMatchers, BeforeAndAfterAll }
import scala.concurrent.duration._
import scala.concurrent.Future
import java.net.InetSocketAddress

/**
  * Created by nshimaza on 2016/01/27.
  */


class TCPClient(server: InetSocketAddress, listener: ActorRef) extends Actor {
  import context.system

  IO(Tcp) ! Connect(server)

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

  def this() = this(ActorSystem("LoopbackServerSpec"))

  val server = system.actorOf(Props[LoopbackServer])

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "LoopbackServer" must {
    "accept connection from TCP client" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", 42027), probe.ref)))

      val msg = probe.expectMsgClass(3.second, classOf[Connected])

      msg.remoteAddress.getHostName must be("localhost")
      msg.remoteAddress.getPort must be(42027)
      msg.localAddress.getHostName must be("localhost")
    }

    "echo back what TCP client sent" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", 42027), probe.ref)))

      probe.expectMsgClass(3.second, classOf[Connected])

      client ! ByteString("Hello World.")
      probe.expectMsg(3.second, ByteString("Hello World."))
    }


  }
}

class FakeCtiServerSpec(_system: ActorSystem) extends TestKit(_system)
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {
  import FakeCtiServerProtocol._

  def this() = this(ActorSystem("FakeCtiServerSpec"))

  val server = system.actorOf(Props[FakeCtiServer])

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "FakeCtiServer" must {
    "accept connection from TCP client" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", 42027), probe.ref)))

      val msg = probe.expectMsgClass(3.second, classOf[Connected])

      msg.remoteAddress.getHostName must be("localhost")
      msg.remoteAddress.getPort must be(42027)
      msg.localAddress.getHostName must be("localhost")

      client ! "close"
    }

    "send single element of scenario at Tick arrival" in {
      val server_prove = TestProbe()
      server ! AddServerProve(server_prove.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", 42027), probe.ref)))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_prove.expectMsg(3.second, ClientHandlerReady)

      server ! Scenario(List(ByteString("Tick1"), ByteString("Tick2")))
      server ! Tick
      probe.expectMsg(3.second, ByteString("Tick1"))

      client ! "close"
      server ! RemoveServerProve(server_prove.ref)
    }

    "send single packet for each Tick" in {
      val server_prove = TestProbe()
      server ! AddServerProve(server_prove.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", 42027), probe.ref)))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_prove.expectMsg(3.second, ClientHandlerReady)

      server ! Scenario(List(ByteString("message 1"), ByteString("message 2")))
      server ! Tick
      probe.expectMsg(3.second, ByteString("message 1"))

      server ! Tick
      probe.expectMsg(3.second, ByteString("message 2"))

      client ! "close"
      server ! RemoveServerProve(server_prove.ref)
    }



  }
}
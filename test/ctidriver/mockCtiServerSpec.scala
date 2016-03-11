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

import akka.actor.{ActorSystem, Props}
import akka.io.Tcp._
import akka.testkit.{ TestActorRef, TestActors, TestKit, TestProbe }
import akka.util.ByteString
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{WordSpecLike, MustMatchers, BeforeAndAfterAll}
import scala.concurrent.duration._

/**
  * Created by nshimaza on 2016/01/27.
  */
@RunWith(classOf[JUnitRunner])
class MockCtiServerSpec(_system: ActorSystem) extends TestKit(_system)
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {
  import MockCtiServerProtocol._

  def this() = this(ActorSystem("FakeCtiServerSpec"))

  val serverPort = 42028
  val serverAddr = new InetSocketAddress("localhost", serverPort)
  val mockServer = system.actorOf(Props(classOf[MockCtiServer], serverPort))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  def setupClientConnection() = {
    val serverProbe = TestProbe()
    mockServer ! WarmRestart(serverProbe.ref)
    val clientProbe = TestProbe()
    val client = system.actorOf(Props(classOf[MockTCPClient], serverAddr, clientProbe.ref))
    clientProbe.expectMsgClass(classOf[Connected])
    serverProbe.expectMsg(ClientHandlerReady)

    (serverProbe, clientProbe, client)
  }

  "MockCtiServer" must {
    "accept connection from TCP client" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(classOf[MockTCPClient], serverAddr, probe.ref))

      val msg = probe.expectMsgClass(classOf[Connected])

      msg.remoteAddress.getHostName mustBe "localhost"
      msg.remoteAddress.getPort mustBe serverPort
      msg.localAddress.getHostName mustBe "localhost"

      TestHelpers.stopActors(probe.ref, client)
    }

    "send single element of scenario at Tick arrival" in {
      val (serverProbe, clientProbe, client) = setupClientConnection()

      mockServer ! Scenario(List(ByteString("Tick1"), ByteString("Tick2")))
      mockServer ! Tick
      clientProbe.expectMsg(ByteString("Tick1"))

      TestHelpers.stopActors(serverProbe.ref, clientProbe.ref, client)
    }

    "send single packet for each Tick" in {
      val (serverProbe, clientProbe, client) = setupClientConnection()

      mockServer ! Scenario(List(ByteString("message 1"), ByteString("message 2")))
      mockServer ! Tick
      clientProbe.expectMsg(ByteString("message 1"))

      mockServer ! Tick
      clientProbe.expectMsg(ByteString("message 2"))

      TestHelpers.stopActors(serverProbe.ref, clientProbe.ref, client)
    }

    "forward received data to probe" in {
      val (serverProbe, clientProbe, client) = setupClientConnection()

      client ! ByteString("hello")
      serverProbe.expectMsg(ByteString("hello"))

      TestHelpers.stopActors(serverProbe.ref, clientProbe.ref, client)
    }
  }
}

@RunWith(classOf[JUnitRunner])
class LoopbackServerSpec(_system: ActorSystem) extends TestKit(_system)
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("LoopbackServerSpec"))

  val serverPort = 42027
  val loopServer = system.actorOf(Props(new LoopbackServer(serverPort)))
  val serverAddr = new InetSocketAddress("localhost", serverPort)

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "LoopbackServer" must {
    "accept connection from TCP client" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new MockTCPClient(serverAddr, probe.ref)))

      val msg = probe.expectMsgClass(3.second, classOf[Connected])

      msg.remoteAddress.getHostName mustBe "localhost"
      msg.remoteAddress.getPort mustBe serverPort
      msg.localAddress.getHostName mustBe "localhost"

      TestHelpers.stopActors(probe.ref, client)
    }

    "echo back what TCP client sent" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new MockTCPClient(serverAddr, probe.ref)))

      probe.expectMsgClass(classOf[Connected])

      client ! ByteString("Hello World.")
      probe.expectMsg(ByteString("Hello World."))

      TestHelpers.stopActors(probe.ref, client)
    }
  }
}

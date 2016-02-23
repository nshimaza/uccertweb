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

import akka.actor.{ Actor, ActorRef, ActorSystem, Props }
import akka.io.{ IO, Tcp }
import akka.io.Tcp._
import akka.testkit.{ TestActorRef, TestActors, TestKit, TestProbe }
import akka.util.ByteString
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ WordSpecLike, MustMatchers, BeforeAndAfterAll }
import scala.concurrent.duration._
import scala.concurrent.Future

/**
  * Created by nshimaza on 2016/01/27.
  */
@RunWith(classOf[JUnitRunner])
class FakeCtiServerSpec(_system: ActorSystem) extends TestKit(_system)
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {
  import FakeCtiServerProtocol._

  def this() = this(ActorSystem("FakeCtiServerSpec"))

  val server_port = 42028
  val server = system.actorOf(Props(classOf[FakeCtiServer], server_port))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "FakeCtiServer" must {
    "accept connection from TCP client" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(classOf[TCPClient], new InetSocketAddress("localhost", server_port), probe.ref))

      val msg = probe.expectMsgClass(3.second, classOf[Connected])

      msg.remoteAddress.getHostName must be("localhost")
      msg.remoteAddress.getPort must be(server_port)
      msg.localAddress.getHostName must be("localhost")
    }

    "send single element of scenario at Tick arrival" in {
      val server_probe = TestProbe()
      server ! WarmRestart(server_probe.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(classOf[TCPClient], new InetSocketAddress("localhost", server_port), probe.ref))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_probe.expectMsg(3.second, ClientHandlerReady)

      server ! Scenario(List(ByteString("Tick1"), ByteString("Tick2")))
      server ! Tick
      probe.expectMsg(3.second, ByteString("Tick1"))
    }

    "send single packet for each Tick" in {
      val server_probe = TestProbe()
      server ! WarmRestart(server_probe.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(classOf[TCPClient], new InetSocketAddress("localhost", server_port), probe.ref))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_probe.expectMsg(3.second, ClientHandlerReady)

      server ! Scenario(List(ByteString("message 1"), ByteString("message 2")))
      server ! Tick
      probe.expectMsg(3.second, ByteString("message 1"))

      server ! Tick
      probe.expectMsg(3.second, ByteString("message 2"))
    }



  }
}

class LoopbackServerSpec(_system: ActorSystem) extends TestKit(_system)
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("LoopbackServerSpec"))

  val server_port = 42027
  val server = system.actorOf(Props(new LoopbackServer(server_port)))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "LoopbackServer" must {
    "accept connection from TCP client" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", server_port), probe.ref)))

      val msg = probe.expectMsgClass(3.second, classOf[Connected])

      msg.remoteAddress.getHostName must be("localhost")
      msg.remoteAddress.getPort must be(server_port)
      msg.localAddress.getHostName must be("localhost")
    }

    "echo back what TCP client sent" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new TCPClient(new InetSocketAddress("localhost", server_port), probe.ref)))

      probe.expectMsgClass(3.second, classOf[Connected])

      client ! ByteString("Hello World.")
      probe.expectMsg(3.second, ByteString("Hello World."))
    }


  }
}


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

import akka.actor.{ActorRef, Actor, Props, ActorSystem}
import akka.testkit.{TestProbe, ImplicitSender, TestKit}
import com.google.inject.{Guice, AbstractModule}
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}
import org.scalatest.junit.JUnitRunner

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


@RunWith(classOf[JUnitRunner])
class HAActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("HAActorSpec"))

  val serverPortA = HAActorSpecValues.serverPortA
  val serverA = new InetSocketAddress("localhost", serverPortA)
  val mockServerA = system.actorOf(Props(classOf[MockCtiServer], serverPortA))
  val serverPortB = HAActorSpecValues.serverPortB
  val serverB = new InetSocketAddress("localhost", serverPortB)
  val mockServerB = system.actorOf(Props(classOf[MockCtiServer], serverPortB))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  def setupProbeAndMock = {
    val forwarderProbe = TestProbe()
    val forwarder = system.actorOf(Props(classOf[ForwarderActor], forwarderProbe.ref), "forwarder")
    forwarderProbe.expectMsg("started")
    system.actorSelection("/user/forwarder") ! "hello"
    forwarderProbe.expectMsg("hello")

    val haActorFactory = Guice.createInjector(new HAActorSpecModule).getInstance(classOf[HAActorPropsFactory])
    val haActor = system.actorOf(haActorFactory(serverA, serverB, MessageFilter(Traversable.empty)))

    val (mock, server) = forwarderProbe.expectMsgClass(classOf[(ActorRef, InetSocketAddress)])
    server mustBe new InetSocketAddress("localhost", serverPortA)

    (forwarderProbe, forwarder, haActor, mock, server)
  }

  "HAActor" must {
    "foo" in {
      val testProbe = TestProbe()
      val forwarderOrig = system.actorOf(Props(classOf[ForwarderActor], testProbe.ref), "forwarder")
      testProbe.expectMsg("started")
      system.actorSelection("user/forwarder") ! "hello"
      testProbe.expectMsg("hello")

      TestHelpers.stopActors(testProbe.ref, forwarderOrig)
    }

    "create SessionActor as its child" in {
      val (forwarderProbe, forwarder, haActor, mock, server) = setupProbeAndMock

      TestHelpers.stopActors(haActor, mock, forwarder, forwarderProbe.ref)
    }

    "reconnect to secondary server with 5 second pause on ConnectSocketFailed" in {
      val (forwarderProbe, forwarder, haActor, mock, server) = setupProbeAndMock
      server mustBe new InetSocketAddress("localhost", serverPortA)

      mock ! SessionActorMockProtocol.Abort(SessionActorProtocol.ConnectSocketFailed)
      val (mock2, server2) = forwarderProbe.expectMsgClass(8.seconds, classOf[(ActorRef, InetSocketAddress)])
      server2 mustBe new InetSocketAddress("localhost", serverPortB)

      TestHelpers.stopActors(haActor, mock, mock2, forwarder, forwarderProbe.ref)
    }

    "reconnect to secondary server with 5 second pause on OpenFailed" in {
      val (forwarderProbe, forwarder, haActor, mock, server) = setupProbeAndMock
      server mustBe new InetSocketAddress("localhost", serverPortA)

      mock ! SessionActorMockProtocol.Abort(SessionActorProtocol.OpenFailed)
      val (mock2, server2) = forwarderProbe.expectMsgClass(8.seconds, classOf[(ActorRef, InetSocketAddress)])
      server2 mustBe new InetSocketAddress("localhost", serverPortB)

      TestHelpers.stopActors(haActor, mock, mock2, forwarder, forwarderProbe.ref)
    }

    "reconnect to secondary server immediately SocketClosed after SessionEstablished" in {
      val (forwarderProbe, forwarder, haActor, mock, server) = setupProbeAndMock
      server mustBe new InetSocketAddress("localhost", serverPortA)

      mock ! SessionActorMockProtocol.ToParent(SessionActorProtocol.SessionEstablished)
      mock ! SessionActorMockProtocol.Abort(SessionActorProtocol.SocketClosed)
      val (mock2, server2) = forwarderProbe.expectMsgClass(classOf[(ActorRef, InetSocketAddress)])
      server2 mustBe new InetSocketAddress("localhost", serverPortB)

      TestHelpers.stopActors(haActor, mock, mock2, forwarder, forwarderProbe.ref)
    }

    "reconnect to secondary server immediately SocketOutOfSync after SessionEstablished" in {
      val (forwarderProbe, forwarder, haActor, mock, server) = setupProbeAndMock
      server mustBe new InetSocketAddress("localhost", serverPortA)

      mock ! SessionActorMockProtocol.ToParent(SessionActorProtocol.SessionEstablished)
      mock ! SessionActorMockProtocol.Abort(SessionActorProtocol.SocketOutOfSync)
      val (mock2, server2) = forwarderProbe.expectMsgClass(classOf[(ActorRef, InetSocketAddress)])
      server2 mustBe new InetSocketAddress("localhost", serverPortB)

      TestHelpers.stopActors(haActor, mock, mock2, forwarder, forwarderProbe.ref)
    }

    "alter two servers on failure after SessionEstablished" in {
      val (forwarderProbe, forwarder, haActor, mock, server) = setupProbeAndMock
      server mustBe new InetSocketAddress("localhost", serverPortA)

      mock ! SessionActorMockProtocol.ToParent(SessionActorProtocol.SessionEstablished)
      mock ! SessionActorMockProtocol.Abort(SessionActorProtocol.SocketOutOfSync)
      val (mock2, server2) = forwarderProbe.expectMsgClass(classOf[(ActorRef, InetSocketAddress)])
      server2 mustBe new InetSocketAddress("localhost", serverPortB)

      mock2 ! SessionActorMockProtocol.ToParent(SessionActorProtocol.SessionEstablished)
      mock2 ! SessionActorMockProtocol.Abort(SessionActorProtocol.SocketClosed)
      val (mock3, server3) = forwarderProbe.expectMsgClass(classOf[(ActorRef, InetSocketAddress)])
      server3 mustBe new InetSocketAddress("localhost", serverPortA)

      TestHelpers.stopActors(haActor, mock, mock2, mock3, forwarder, forwarderProbe.ref)
    }
  }
}

object HAActorSpecValues {
  val serverPortA = 42032
  val serverPortB = 42033
}

class ForwarderActor(listener: ActorRef) extends Actor {

  listener ! "started"

  def receive = {
    case m => listener ! m
  }
}

class SessionActorMock(server: InetSocketAddress, filter: MessageFilter) extends Actor {

  context.actorSelection("/user/forwarder") ! (self, server)

  def receive = {
    case SessionActorMockProtocol.Abort(m) =>
      context.parent ! m
      context stop self

    case SessionActorMockProtocol.ToParent(m) =>
      context.parent ! m
  }
}

class SessionActorMockPropsFactory extends SessionActorPropsFactory {
  def apply(server: InetSocketAddress, filter: MessageFilter) = {
    Props(classOf[SessionActorMock], server, filter)
  }
}

object SessionActorMockProtocol {
  case class Abort(m: SessionActorProtocol.SessionAborted)
  case class ToParent(m: Any)
}

class HAActorSpecModule extends AbstractModule {
  def configure() = {
    bind(classOf[HAActorPropsFactory]).to(classOf[HAActorImplPropsFactory])
    bind(classOf[SessionActorPropsFactory]).to(classOf[SessionActorMockPropsFactory])
  }
}
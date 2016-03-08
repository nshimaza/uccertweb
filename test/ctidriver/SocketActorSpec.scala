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

import akka.actor._
import akka.testkit.{TestProbe, ImplicitSender, TestKit}
import akka.io.Tcp._
import akka.util.ByteString
import ctidriver.MockCtiServerProtocol._
import ctidriver.MessageType._
import ctidriver.Tag.Status
import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.{MustMatchers, BeforeAndAfterAll, WordSpecLike}
import org.scalatest.junit.JUnitRunner

import scala.collection.immutable.BitSet
import scala.concurrent.duration._

/**
  * Created by nshimaza on 2016/03/04.
  */
@RunWith(classOf[JUnitRunner])
class SocketActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("SessionSpec"))

  val serverPort = 42030
  val server = new InetSocketAddress("localhost", serverPort)
  val mockServer = system.actorOf(Props(classOf[MockCtiServer], serverPort))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  def setupProbeAndMock() = {
    val serverProbe = TestProbe()
    mockServer ! WarmRestart(serverProbe.ref)
    val mockProbe = TestProbe()
    val mockParent = system.actorOf(Props(classOf[SocketActorSpecParentMock], server, mockProbe.ref))
    mockProbe.expectMsgClass(classOf[Connected])
    serverProbe.expectMsg(ClientHandlerReady)

    (serverProbe, mockProbe, mockParent)
  }

  "SocketActor" must {
    "send CommandFailed to parent when connection failed" in {
      val failServer = new InetSocketAddress("localhost", serverPort + 1)
      val probe = TestProbe()
      val mock = system.actorOf(Props(classOf[SocketActorSpecParentMock], failServer, probe.ref))

      probe.expectMsgClass(classOf[CommandFailed])
      probe.expectMsgClass(classOf[SocketActorSpecChildTerminated])

      TestHelpers.stopActors(probe.ref, mock)
    }

    "send Connected to parent when connection succeed" in {
      val mockProbe = TestProbe()
      val mockParent = system.actorOf(Props(classOf[SocketActorSpecParentMock], server, mockProbe.ref))

      mockProbe.expectMsgClass(classOf[Connected])

      TestHelpers.stopActors(mockProbe.ref, mockParent)
    }

    "contains remote info in Connected message" in {
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[SocketActorSpecParentMock], server, mockProbe.ref))

      val msg = mockProbe.expectMsgClass(classOf[Connected])
      msg.remoteAddress.getHostName mustBe "localhost"
      msg.remoteAddress.getPort mustBe serverPort
      msg.localAddress.getHostName mustBe "localhost"

      TestHelpers.stopActors(mockProbe.ref, mock)
    }

    "packetize received stream" in {
      val (serverProbe, mockProbe, mockParent) = setupProbeAndMock()

      mockServer ! Scenario(List(ByteString(0,0,0,4), ByteString(1,2,3,4), ByteString(5,6,7,8),
        ByteString(0,0), ByteString(0,3, 9,8,7,6), ByteString(5,4,3)))
      mockServer ! Tick
      mockServer ! Tick
      mockServer ! Tick
      mockProbe.expectMsg(SessionProtocol.Received(ByteString(1,2,3,4,5,6,7,8)))
      mockServer ! Tick
      mockServer ! Tick
      mockServer ! Tick
      mockProbe.expectMsg(SessionProtocol.Received(ByteString(9,8,7,6,5,4,3)))

      TestHelpers.stopActors(serverProbe.ref, mockProbe.ref, mockParent)
    }

    "work with OPEN_REQ and OPEN_CONF" in {
      val (serverProbe, mockProbe, mockParent) = setupProbeAndMock()

      val openReqBody = List((MessageTypeTag, Some(OPEN_REQ)), (InvokeID, 0x04030201),
        (VersionNumber, ProtocolVersion), (IdleTimeout, NoIdleTimeout), (PeripheralID, 0x02030405),
        (ServiceRequested, BitSet.empty + CtiServiceMask.ALL_EVENTS),
        (CallMsgMask, BitSet.empty + CallEventMessageMask.BEGIN_CALL + CallEventMessageMask.END_CALL),
        (AgentStateMaskTag, BitSet.empty + AgentStateMask.AGENT_AVAILABLE + AgentStateMask.AGENT_HOLD),
        (ConfigMsgMask, BitSet.empty), (Reserved1, 0), (Reserved2, 0), (Reserved3, 0),
        (CLIENT_ID, "ClientID"), (CLIENT_PASSWORD, ByteString()), (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_EXTENSION, "3001"),(AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (APP_PATH_ID, 0x03040506)).encode
      val openConfBody = ByteString(0,0,0,4, 4,3,2,1, 0,0,0,0x10, 2,3,4,5, 0,0,0,0x13, 4,5,6,7, 0,1, 0,17, 0,3,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        228,2,5,6, 224,2,0,0)
      val openConfRaw = openConfBody.withlength
      val openConfExpected = List((MessageTypeTag, Some(OPEN_CONF)), (InvokeID, 0x04030201),
        (ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS), (MonitorID, 0x02030405),
        (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN + PGStatusCode.LIMITED_FUNCTION),
        (ICMCentralControllerTime, 0x04050607), (PeripheralOnline, true),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)), (AgentStateTag, Some(AgentState.AVAILABLE)),
        (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (NUM_PERIPHERALS, 0x0506.toShort),
        (MULTI_LINE_AGENT_CONTROL, false))

      mockServer ! Scenario(List(openConfRaw))
      mockParent ! ToSocketActor(SessionProtocol.Send(openReqBody))
      mockServer ! Tick
      val msg = mockProbe.expectMsgClass(classOf[SessionProtocol.Received])
      msg.data.decode mustBe openConfExpected

      TestHelpers.stopActors(serverProbe.ref, mockProbe.ref, mockParent)
    }

    "work with FAILURE_EVENT" in {
      val (serverProbe, mockProbe, mockParent) = setupProbeAndMock()

      val openReqBody = List((MessageTypeTag, Some(OPEN_REQ)), (InvokeID, 0x04030201),
        (VersionNumber, ProtocolVersion), (IdleTimeout, NoIdleTimeout), (PeripheralID, 0x02030405),
        (ServiceRequested, BitSet.empty + CtiServiceMask.ALL_EVENTS),
        (CallMsgMask, BitSet.empty + CallEventMessageMask.BEGIN_CALL + CallEventMessageMask.END_CALL),
        (AgentStateMaskTag, BitSet.empty + AgentStateMask.AGENT_AVAILABLE + AgentStateMask.AGENT_HOLD),
        (ConfigMsgMask, BitSet.empty), (Reserved1, 0), (Reserved2, 0), (Reserved3, 0),
        (CLIENT_ID, "ClientID"), (CLIENT_PASSWORD, ByteString()), (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_EXTENSION, "3001"),(AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (APP_PATH_ID, 0x03040506)).encode
      val openConfRaw = List((MessageTypeTag, Some(OPEN_CONF)), (InvokeID, 0x04030201),
        (ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS), (MonitorID, 0x02030405),
        (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN + PGStatusCode.LIMITED_FUNCTION),
        (ICMCentralControllerTime, 0x04050607), (PeripheralOnline, true),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)), (AgentStateTag, Some(AgentState.AVAILABLE)),
        (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (NUM_PERIPHERALS, 0x0506.toShort),
        (MULTI_LINE_AGENT_CONTROL, false)).encode.withlength
      val failureEventMsg = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (Status, Some(StatusCode.UNSPECIFIED_FAILURE)))
      val failureEventRaw = failureEventMsg.encode.withlength

      mockServer ! Scenario(List(openConfRaw, failureEventRaw))
      mockParent ! ToSocketActor(SessionProtocol.Send(openReqBody))
      mockServer ! Tick
      mockServer ! Tick
      mockProbe.expectMsgClass(classOf[SessionProtocol.Received])
      val msg = mockProbe.expectMsgClass(classOf[SessionProtocol.Received])
      msg.data.decode mustBe failureEventMsg

      TestHelpers.stopActors(serverProbe.ref, mockProbe.ref, mockParent)
    }
  }

  "send ConnectionClosed and terminate on connection closed by peer" in {
    val (serverProbe, mockProbe, mockParent) = setupProbeAndMock()

    mockServer ! Scenario(List(ByteString(0,0,0,4), ByteString(1,2,3,4), ByteString(5,6,7,8),
      ByteString(0,0), ByteString(0,3, 9,8,7,6), ByteString(5,4,3)))
    mockServer ! Tick
    mockServer ! Tick
    mockServer ! Tick
    mockProbe.expectMsg(SessionProtocol.Received(ByteString(1,2,3,4,5,6,7,8)))
    mockServer ! Tick
    mockServer ! Tick
    mockServer ! CloseClient
    mockProbe.expectMsgClass(classOf[ConnectionClosed])
    mockProbe.expectMsgClass(classOf[SocketActorSpecChildTerminated])

    TestHelpers.stopActors(serverProbe.ref, mockProbe.ref, mockParent)
  }

  "send ConnectionClosed and terminate on completion of Close" in {
    val (serverProbe, mockProbe, mockParent) = setupProbeAndMock()

    mockServer ! Scenario(List(ByteString(0,0,0,4), ByteString(1,2,3,4), ByteString(5,6,7,8),
      ByteString(0,0), ByteString(0,3, 9,8,7,6), ByteString(5,4,3)))
    mockServer ! Tick
    mockServer ! Tick
    mockServer ! Tick
    mockProbe.expectMsg(3.second, SessionProtocol.Received(ByteString(1,2,3,4,5,6,7,8)))
    mockServer ! Tick
    mockServer ! Tick
    mockParent ! ToSocketActor(SessionProtocol.Close)
    mockProbe.expectMsgClass(3.second, classOf[ConnectionClosed])
    mockProbe.expectMsgClass(3.second, classOf[SocketActorSpecChildTerminated])

    TestHelpers.stopActors(serverProbe.ref, mockProbe.ref, mockParent)
  }

}

case class SocketActorSpecChildTerminated(child: ActorRef)
case class ToSocketActor(m: Any)

class SocketActorSpecParentMock(server: InetSocketAddress, probe: ActorRef) extends Actor {

  val socketActor = context.actorOf(Props(classOf[SocketActorImpl], server), "SocketActor")
  context.watch(socketActor)

  def receive = {
    case ToSocketActor(m) => socketActor ! m

    case Terminated(child) => probe ! SocketActorSpecChildTerminated(child)

    case m => probe ! m
  }
}
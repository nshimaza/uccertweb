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
import akka.util.ByteString
import ctidriver.MessageType._
import ctidriver.MockCtiServerProtocol._
import ctidriver.SessionActorProtocol._
import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

import scala.collection.immutable.BitSet

/**
  * Created by nshimaza on 2016/03/11.
  */
@RunWith(classOf[JUnitRunner])
class SessionActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("sessionActorSpec"))

  val serverPort = 42031
  val server = new InetSocketAddress("localhost", serverPort)
  val mockServer = system.actorOf(Props(classOf[MockCtiServer], serverPort))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  def setupProbeAndMock(filter: MessageFilter) = {
    val serverProbe = TestProbe()
    mockServer ! WarmRestart(serverProbe.ref)
    val childProps = Props(classOf[SessionActorImpl], server, filter)
    val mockProbe = TestProbe()
    val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))
    serverProbe.expectMsg(ClientHandlerReady)

    val msg = serverProbe.expectMsgClass(classOf[ByteString]).drop(4).decode
    msg.findEnum[MessageType](MessageTypeTag) mustBe Some(OPEN_REQ)
    val openConfRaw = (List((MessageTypeTag, Some(OPEN_CONF)), (InvokeID, msg.findT[Int](InvokeID).get)) ++
      SessionActorSpec.openConfBody).encode.withlength
    mockServer ! Scenario(List(openConfRaw))
    mockServer ! Tick
    mockProbe.expectMsg(SessionEstablished)

    (serverProbe, mockProbe, mock)
  }

  "SessionActor" must {
    "send ConnectSocketFailed to parent on failure of connecting socket to server" in {
      val failServer = new InetSocketAddress("localhost", serverPort + 1)
      val childProps = Props(classOf[SessionActorImpl], failServer, MessageFilter(Traversable.empty))
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))

      mockProbe.expectMsg(ConnectSocketFailed)
      mockProbe.expectMsgClass(classOf[ChildActorTerminated])

      TestHelpers.stopActors(mock, mockProbe.ref)
    }

    "connect to server on instantiation" in {
      val serverProbe = TestProbe()
      mockServer ! WarmRestart(serverProbe.ref)
      val childProps = Props(classOf[SessionActorImpl], server, MessageFilter(Traversable.empty))
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))

      serverProbe.expectMsg(ClientHandlerReady)
      serverProbe.expectMsgClass(classOf[Any])

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "send OPEN_REQ message as a first message" in {
      val serverProbe = TestProbe()
      mockServer ! WarmRestart(serverProbe.ref)
      val childProps = Props(classOf[SessionActorImpl], server, MessageFilter(Traversable.empty))
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))
      serverProbe.expectMsg(ClientHandlerReady)

      val data = serverProbe.expectMsgClass(classOf[ByteString])
      data.drop(4).decode.findEnum[MessageType](MessageTypeTag) mustBe Some(OPEN_REQ)

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "send SessionEstablished on receiving OPEN_CONF" in {
      val serverProbe = TestProbe()
      mockServer ! WarmRestart(serverProbe.ref)
      val childProps = Props(classOf[SessionActorImpl], server, MessageFilter(Traversable.empty))
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))
      serverProbe.expectMsg(ClientHandlerReady)

      val msg = serverProbe.expectMsgClass(classOf[ByteString]).drop(4).decode
      msg.findEnum[MessageType](MessageTypeTag) mustBe Some(OPEN_REQ)
      val openConfRaw = (List((MessageTypeTag, Some(OPEN_CONF)), (InvokeID, msg.findT[Int](InvokeID).get)) ++
        SessionActorSpec.openConfBody).encode.withlength
      mockServer ! Scenario(List(openConfRaw))
      mockServer ! Tick

      mockProbe.expectMsg(SessionEstablished)

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "recognize OPEN_CONF though message is divided into multiple TCP segments" in {
      val serverProbe = TestProbe()
      mockServer ! WarmRestart(serverProbe.ref)
      val childProps = Props(classOf[SessionActorImpl], server, MessageFilter(Traversable.empty))
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))
      serverProbe.expectMsg(ClientHandlerReady)

      val msg = serverProbe.expectMsgClass(classOf[ByteString]).drop(4).decode
      msg.findEnum[MessageType](MessageTypeTag) mustBe Some(OPEN_REQ)
      val openConfRaw = (List((MessageTypeTag, Some(OPEN_CONF)), (InvokeID, msg.findT[Int](InvokeID).get)) ++
        SessionActorSpec.openConfBody).encode.withlength
      val raw1 = openConfRaw.take(10)
      val raw2 = openConfRaw.drop(10).take(10)
      val raw3 = openConfRaw.drop(20)

      mockServer ! Scenario(List(raw1, raw2, raw3))
      mockServer ! Tick
      mockServer ! Tick
      mockServer ! Tick

      mockProbe.expectMsg(SessionEstablished)

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "send SocketClosed and terminate on socket closure before session established" in {
      val serverProbe = TestProbe()
      mockServer ! WarmRestart(serverProbe.ref)
      val childProps = Props(classOf[SessionActorImpl], server, MessageFilter(Traversable.empty))
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))
      serverProbe.expectMsg(ClientHandlerReady)

      val msg = serverProbe.expectMsgClass(classOf[ByteString]).drop(4).decode
      msg.findEnum[MessageType](MessageTypeTag) mustBe Some(OPEN_REQ)

      mockServer ! CloseClient
      mockProbe.expectMsg(SocketClosed)
      mockProbe.expectMsgClass(classOf[ChildActorTerminated])

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "send SocketClosed and terminate on socket closure after session established" in {
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(MessageFilter(Traversable.empty))

      mockServer ! CloseClient
      mockProbe.expectMsg(SocketClosed)
      mockProbe.expectMsgClass(classOf[ChildActorTerminated])

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "send OpenFailed and terminate on FAILURE_CONF against OPEN_REQ" in {
      val serverProbe = TestProbe()
      mockServer ! WarmRestart(serverProbe.ref)
      val childProps = Props(classOf[SessionActorImpl], server, MessageFilter(Traversable.empty))
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))
      serverProbe.expectMsg(ClientHandlerReady)

      val msg = serverProbe.expectMsgClass(classOf[ByteString]).drop(4).decode
      msg.findEnum[MessageType](MessageTypeTag) mustBe Some(OPEN_REQ)

      val failureConfRaw = (List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, msg.findT[Int](InvokeID).get)) ++
        SessionActorSpec.failureConfBody).encode.withlength
      mockServer ! Scenario(List(failureConfRaw))
      mockServer ! Tick
      mockProbe.expectMsg(OpenFailed)
      mockProbe.expectMsgClass(classOf[ChildActorTerminated])

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "send SocketOutOfSync and terminate on malformed message received while opening" in {
      val serverProbe = TestProbe()
      mockServer ! WarmRestart(serverProbe.ref)
      val childProps = Props(classOf[SessionActorImpl], server, MessageFilter(Traversable.empty))
      val mockProbe = TestProbe()
      val mock = system.actorOf(Props(classOf[MockParentActor], childProps, mockProbe.ref))
      serverProbe.expectMsg(ClientHandlerReady)

      val msg = serverProbe.expectMsgClass(classOf[ByteString]).drop(4).decode
      msg.findEnum[MessageType](MessageTypeTag) mustBe Some(OPEN_REQ)

      mockServer ! Scenario(List(ByteString(9,9,9,9, 1,2,3,4, 5,6,7,8)))
      mockServer ! Tick
      mockProbe.expectMsg(SocketOutOfSync)
      mockProbe.expectMsgClass(classOf[ChildActorTerminated])

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "send SocketOutOfSync and terminate on malformed message received after established" in {
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(MessageFilter(Traversable.empty))

      mockServer ! Scenario(List(ByteString(9,9,9,9, 1,2,3,4, 5,6,7,8)))
      mockServer ! Tick
      mockProbe.expectMsg(SocketOutOfSync)
      mockProbe.expectMsgClass(classOf[ChildActorTerminated])

      TestHelpers.stopActors(mock, mockProbe.ref, serverProbe.ref)
    }

    "deliver message to callback handlers which desires the message type" in {
      val handlerProbe = TestProbe()
      val handler: Message => Unit = { m => handlerProbe.ref ! m }
      val filter = MessageFilter(Traversable(MessageFilterEntry(Set(AGENT_STATE_EVENT), handler)))
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(filter)

      mockServer ! Scenario(List(SessionActorSpec.agentStateEvent.encode.withlength))
      mockServer ! Tick
      handlerProbe.expectMsg(SessionActorSpec.agentStateEvent)

      TestHelpers.stopActors(mock, mockProbe.ref, handlerProbe.ref, serverProbe.ref)
    }

    "deliver entire message to handlers though it is divided into multiple TCP segments" in {
      val handlerProbe = TestProbe()
      val handler: Message => Unit = { m => handlerProbe.ref ! m }
      val filter = MessageFilter(Traversable(MessageFilterEntry(Set(AGENT_STATE_EVENT), handler)))
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(filter)

      val agentStateEventRaw = SessionActorSpec.agentStateEvent.encode.withlength
      val raw1 = agentStateEventRaw.take(15)
      val raw2 = agentStateEventRaw.drop(15).take(20)
      val raw3 = agentStateEventRaw.drop(35)

      mockServer ! Scenario(List(raw1, raw2, raw3))
      mockServer ! Tick
      mockServer ! Tick
      mockServer ! Tick

      handlerProbe.expectMsg(SessionActorSpec.agentStateEvent)

      TestHelpers.stopActors(mock, mockProbe.ref, handlerProbe.ref, serverProbe.ref)
    }

    "deliver each message separately though it is arrived in single TCP segments" in {
      val handlerProbe = TestProbe()
      val handler: Message => Unit = { m => handlerProbe.ref ! m }
      val filter = MessageFilter(Traversable(MessageFilterEntry(Set(FAILURE_EVENT, USER_MESSAGE_EVENT), handler)))
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(filter)

      val raw = SessionActorSpec.userMessageEvent.encode.withlength ++ SessionActorSpec.failureEvent.encode.withlength
      mockServer ! Scenario(List(raw))
      mockServer ! Tick

      handlerProbe.expectMsg(SessionActorSpec.userMessageEvent)
      handlerProbe.expectMsg(SessionActorSpec.failureEvent)

      TestHelpers.stopActors(mock, mockProbe.ref, handlerProbe.ref, serverProbe.ref)
    }

    "drop message if the message type is not wanted by callback handlers" in {
      val handlerProbe = TestProbe()
      val handler: Message => Unit = { m => handlerProbe.ref ! m }
      val filter = MessageFilter(Traversable(MessageFilterEntry(Set(BEGIN_CALL_EVENT), handler)))
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(filter)

      mockServer ! Scenario(List(SessionActorSpec.agentStateEvent.encode.withlength))
      mockServer ! Tick
      handlerProbe.expectNoMsg()

      TestHelpers.stopActors(mock, mockProbe.ref, handlerProbe.ref, serverProbe.ref)
    }

    "duplicate message deliverly when multiple handlers wanted same message type" in {
      val handlerProbe1 = TestProbe()
      val handler1: Message => Unit = { m => handlerProbe1.ref ! m }
      val entry1 = MessageFilterEntry(Set(AGENT_STATE_EVENT), handler1)
      val handlerProbe2 = TestProbe()
      val handler2: Message => Unit = { m => handlerProbe2.ref ! m }
      val entry2 = MessageFilterEntry(Set(AGENT_STATE_EVENT), handler2)
      val filter = MessageFilter(Traversable(entry1, entry2))
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(filter)

      mockServer ! Scenario(List(SessionActorSpec.agentStateEvent.encode.withlength))
      mockServer ! Tick
      handlerProbe1.expectMsg(SessionActorSpec.agentStateEvent)
      handlerProbe2.expectMsg(SessionActorSpec.agentStateEvent)

      TestHelpers.stopActors(mock, mockProbe.ref, handlerProbe1.ref, handlerProbe2.ref, serverProbe.ref)
    }

    "deliver messages only to desiring handlers" in {
      val handlerProbe1 = TestProbe()
      val handler1: Message => Unit = { m => handlerProbe1.ref ! m }
      val entry1 = MessageFilterEntry(Set(AGENT_STATE_EVENT), handler1)
      val handlerProbe2 = TestProbe()
      val handler2: Message => Unit = { m => handlerProbe2.ref ! m }
      val entry2 = MessageFilterEntry(Set(USER_MESSAGE_EVENT), handler2)
      val filter = MessageFilter(Traversable(entry1, entry2))
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(filter)

      mockServer ! Scenario(List(SessionActorSpec.userMessageEvent.encode.withlength ++
        SessionActorSpec.agentStateEvent.encode.withlength))
      mockServer ! Tick
      handlerProbe1.expectMsg(SessionActorSpec.agentStateEvent)
      handlerProbe2.expectMsg(SessionActorSpec.userMessageEvent)

      TestHelpers.stopActors(mock, mockProbe.ref, handlerProbe1.ref, handlerProbe2.ref, serverProbe.ref)
    }

    "work with combination of filtering and duplication" in {
      val handlerProbe1 = TestProbe()
      val handler1: Message => Unit = { m => handlerProbe1.ref ! m }
      val entry1 = MessageFilterEntry(Set(AGENT_STATE_EVENT), handler1)
      val handlerProbe2 = TestProbe()
      val handler2: Message => Unit = { m => handlerProbe2.ref ! m }
      val entry2 = MessageFilterEntry(Set(AGENT_STATE_EVENT, USER_MESSAGE_EVENT), handler2)
      val filter = MessageFilter(Traversable(entry1, entry2))
      val (serverProbe, mockProbe, mock) = setupProbeAndMock(filter)

      mockServer ! Scenario(List(SessionActorSpec.userMessageEvent.encode.withlength ++
        SessionActorSpec.agentStateEvent.encode.withlength))
      mockServer ! Tick
      handlerProbe1.expectMsg(SessionActorSpec.agentStateEvent)
      handlerProbe2.expectMsg(SessionActorSpec.userMessageEvent)
      handlerProbe2.expectMsg(SessionActorSpec.agentStateEvent)

      TestHelpers.stopActors(mock, mockProbe.ref, handlerProbe1.ref, handlerProbe2.ref, serverProbe.ref)
    }
  }
}

object SessionActorSpec {
  val openConfBody = List((ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS), (MonitorID, 0x02030405),
    (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN + PGStatusCode.LIMITED_FUNCTION),
    (ICMCentralControllerTime, 0x04050607), (PeripheralOnline, true),
    (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)), (AgentStateTag, Some(AgentState.AVAILABLE)),
    (NUM_PERIPHERALS, 0x0506.toShort), (MULTI_LINE_AGENT_CONTROL, false))
  val failureConfBody = List((StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))
  val agentStateEvent = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304),
    (PeripheralID, 0x02030405), (SessionID, 0x03040506),
    (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
    (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
    (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809: Short),
    (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a: Short), (MRDID, 0x090a0b0c),
    (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
    (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706: Short),
    (CLIENT_SIGNATURE, "ClientSignature"),
    (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
    (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
    (DIRECTION, Some(CallDirection.In)),
    (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a: Short),
    (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))
  val userMessageEvent = List((MessageTypeTag, Some(USER_MESSAGE_EVENT)),
    (ICMCentralControllerTime, 0x01020304), (Distribution, Some(DistributionValue.TEAM)),
    (CLIENT_ID, "ClientID"), (TEXT, "UserMessageEventText"))
  val failureEvent = List((MessageTypeTag, Some(FAILURE_EVENT)),
    (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))
}

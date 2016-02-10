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

import akka.actor.{ActorRef, Actor, ActorSystem, Props}
import akka.io.Tcp._
import akka.testkit.{ TestActorRef, TestActors, TestKit, TestProbe }
import akka.util.ByteString
import ctidriver.FakeCtiServerProtocol._
import ctidriver.MessageType._
import ctidriver.Tag._
import org.scalatest.{ WordSpecLike, MustMatchers, BeforeAndAfterAll }
import scala.collection.immutable.BitSet
import scala.concurrent.duration._


/**
  * Created by nshimaza on 2016/01/24.
  */
//class StubReceiver extends Actor {
//  var buf: List[ByteString] = List.empty[ByteString]
//  def receive = {
//    case data: ByteString => buf = buf :+ data
//  }
//}

class SessionSpec(_system: ActorSystem) extends TestKit(_system)
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("SessionSpec"))

  val sv_port = 42029
  val server = system.actorOf(Props(classOf[FakeCtiServer], sv_port))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
/*
  "PacketizerActor" must {
    "start with state WAIT_LENGTH" in {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actor.state mustBe (actor.State.WAIT_LENGTH)
    }

    "stay on state WAIT_LENGTH until 4 bytes received" in  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3)
      actor.state mustBe (actor.State.WAIT_LENGTH)
    }

    "transit to state WAIT_BODY when 4 bytes received" in new  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3, 4)
      actor.state mustBe (actor.State.WAIT_BODY)
    }

    "transit to state WAIT_BODY when 4 bytes received separately" in  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1)
      actor.state mustBe (actor.State.WAIT_LENGTH)
      actorRef ! ByteString(2)
      actor.state mustBe (actor.State.WAIT_LENGTH)
      actorRef ! ByteString(3)
      actor.state mustBe (actor.State.WAIT_LENGTH)
      actorRef ! ByteString(4)
      actor.state mustBe (actor.State.WAIT_BODY)
    }

    "transit to state WAIT_BODY when more than 4 bytes received" in  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3, 4, 5)
      actor.state mustBe (actor.State.WAIT_BODY)
    }

    "keep remaining data received when successfully decode message length" in  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3, 4, 5)
      actor.state mustBe (actor.State.WAIT_BODY)
      actor.buf.size mustBe (1)
      actor.buf.head mustBe (5: Byte)
    }

    "decode and keep message length when transit to WAIT_BODY" in {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3, 4, 5, 6, 7, 8)
      actor.state mustBe (actor.State.WAIT_BODY)
      actor.offset mustBe (4 - (0x01020304 + 4))
    }

  }
*/

  "PacketizerNew" must {
    "start with state WAIT_LENGH" in {
      val packetizer = new PacketizerNew()

      packetizer.state mustBe packetizer.State.WAIT_LENGTH
    }

    "start with offset -4" in {
      val packetizer = new PacketizerNew()

      packetizer.offset mustBe -4
    }

    "return empty Seq[ByteString] on receive until entire packet becomes available" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(1, 2, 3) mustBe Seq[ByteString]()
    }

    "stay on state WAIT_LENGTH until 4 bytes received" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(1, 2, 3) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
    }

    "transit to state WAIT_BODY when 4 bytes received" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(0, 0, 1, 2) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_BODY
    }

    "transit to state WAIT_BODY when 4 bytes received separately" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(0) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer receive ByteString(0) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer receive ByteString(3) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer receive ByteString(4) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_BODY
    }

    "transit to state WAIT_BODY when more than 4 bytes received" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(0, 0, 3, 4, 5) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_BODY
    }

    "keep remaining data received when successfully decode message length" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(0, 0, 3, 4, 5) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_BODY
      packetizer.buf.size mustBe 1
      packetizer.buf.head mustBe 5.toByte
    }

    "decode and keep message length when transit to WAIT_BODY" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(0,0,0x10,0xe0, 5,6,7,8) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_BODY
      packetizer.offset mustBe (4 - (0x000010e0 + 4))
    }

    "throw java.io.SyncFailedException when negative message length is decoded" in {
      val packetizer = new PacketizerNew

      intercept[java.io.SyncFailedException] {
        val packets = packetizer receive encodeByteString(-1: Int)
      }
    }

    "throw java.io.SyncFailedException when decoded message length is greater than MaxMessageLen" in {
      val packetizer = new PacketizerNew

      intercept[java.io.SyncFailedException] {
        val packets = packetizer receive encodeByteString(MaxMessageLen + 1: Int)
      }
    }

    "decode mesasge length when it is just equal to MaxMessageLen" in {
      val packetizer = new PacketizerNew

      packetizer receive encodeByteString(MaxMessageLen) mustBe Seq()
      packetizer.state mustBe packetizer.State.WAIT_BODY
      packetizer.offset mustBe (-(MaxMessageLen + 4))
    }

    "transit back to state WAIT_LENGTH when desired bytes are received" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8) mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer.offset mustBe (-4)
    }

    "send entire single message to listener when it reached next WAIT_LENGTH state" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8) mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer.offset mustBe (-4)
    }

    "keep remaining bytes after sending an entire message" in {
      val packetizer = new PacketizerNew

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 9,10) mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer.offset mustBe (-2)
      packetizer.buf mustBe ByteString(9, 10)
    }

    "can separate multiple messages received at a time" in {
      val packetizer = new PacketizerNew

      val packets = packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 0,0,0,3, 2,3,4,5, 6,7,8)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer.offset mustBe (-4)
      packets mustBe Seq(ByteString(1,2,3,4, 5,6,7,8), ByteString(2,3,4,5, 6,7,8))
    }





  }

  "Packetizer" must {
    "start with state WAIT_LENGTH" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer.state mustBe packetizer.State.WAIT_LENGTH
    }

    "start with offset -4" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer.offset mustBe -4
    }

    "stay on state WAIT_LENGTH until 4 bytes received" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer receive ByteString(1, 2, 3)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
    }

    "transit to state WAIT_BODY when 4 bytes received" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer receive ByteString(0, 0, 1, 2)
      packetizer.state mustBe packetizer.State.WAIT_BODY
    }

    "transit to state WAIT_BODY when 4 bytes received separately" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer receive ByteString(0)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer receive ByteString(0)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer receive ByteString(3)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer receive ByteString(4)
      packetizer.state mustBe packetizer.State.WAIT_BODY
    }

    "transit to state WAIT_BODY when more than 4 bytes received" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer receive ByteString(0, 0, 3, 4, 5)
      packetizer.state mustBe packetizer.State.WAIT_BODY
    }

    "keep remaining data received when successfully decode message length" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer receive ByteString(0, 0, 3, 4, 5)
      packetizer.state mustBe packetizer.State.WAIT_BODY
      packetizer.buf.size mustBe 1
      packetizer.buf.head mustBe 5.toByte
    }

    "decode and keep message length when transit to WAIT_BODY" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer receive ByteString(0,0,0x10,0xe0, 5,6,7,8)
      packetizer.state mustBe packetizer.State.WAIT_BODY
      packetizer.offset mustBe (4 - (0x000010e0 + 4))
    }

    "throw java.io.SyncFailedException when negative message length is decoded" in {
      val packetizer = new Packetizer(_ => Unit)

      intercept[java.io.SyncFailedException] {
        packetizer receive encodeByteString(-1: Int)
      }
    }

    "throw java.io.SyncFailedException when decoded message length is greater than MaxMessageLen" in {
      val packetizer = new Packetizer(_ => Unit)

      intercept[java.io.SyncFailedException] {
        packetizer receive encodeByteString(MaxMessageLen + 1: Int)
      }
    }

    "decode mesasge length when it is just equal to MaxMessageLen" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer receive encodeByteString(MaxMessageLen)
      packetizer.state mustBe packetizer.State.WAIT_BODY
      packetizer.offset mustBe (-(MaxMessageLen + 4))
    }

    "transit back to state WAIT_LENGTH when desired bytes are received" in {
      val packetizer = new Packetizer(_ => Unit)

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer.offset mustBe (-4)
    }

    "send entire single message to listener when it reached next WAIT_LENGTH state" in {
      var data = ByteString.empty
      val packetizer = new Packetizer(data = _)

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer.offset mustBe (-4)
      data mustBe ByteString(1,2,3,4, 5,6,7,8)
    }

    "keep remaining bytes after sending an entire message" in {
      var data = ByteString.empty
      val packetizer = new Packetizer(data = _)

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 9,10)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer.offset mustBe (-2)
      data mustBe ByteString(1,2,3,4, 5,6,7,8)
      packetizer.buf mustBe ByteString(9, 10)
    }

    "can separate multiple messages received at a time" in {
      var data = Seq.empty[ByteString]
      val packetizer = new Packetizer(p => { data = data :+ p })

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 0,0,0,3, 2,3,4,5, 6,7,8)
      packetizer.state mustBe packetizer.State.WAIT_LENGTH
      packetizer.offset mustBe (-4)
      data mustBe Seq(ByteString(1,2,3,4, 5,6,7,8), ByteString(2,3,4,5, 6,7,8))
    }
  }

  "SocketActor" must {
    "connect to server" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(classOf[SocketActor], new InetSocketAddress("localhost", sv_port), probe.ref))

      val msg = probe.expectMsgClass(3.second, classOf[Connected])

      msg.remoteAddress.getHostName mustBe "localhost"
      msg.remoteAddress.getPort mustBe sv_port
      msg.localAddress.getHostName mustBe "localhost"

      client ! SessionProtocol.Close
    }

    "packetize received stream" in {
      val server_probe = TestProbe()
      server ! WarmRestart(server_probe.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(classOf[SocketActor], new InetSocketAddress("localhost", sv_port), probe.ref))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_probe.expectMsg(3.second, ClientHandlerReady)

      server ! Scenario(List(ByteString(0,0,0,4), ByteString(1,2,3,4), ByteString(5,6,7,8),
        ByteString(0,0), ByteString(0,3, 9,8,7,6), ByteString(5,4,3)))
      server ! Tick
      server ! Tick
      server ! Tick
      probe.expectMsg(3.second, ByteString(1,2,3,4,5,6,7,8))
      server ! Tick
      server ! Tick
      server ! Tick
      probe.expectMsg(3.second, ByteString(9,8,7,6,5,4,3))
    }

    "work with OPEN_REQ and OPEN_CONF" in {
      val server_probe = TestProbe()
      server ! WarmRestart(server_probe.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(classOf[SocketActor], new InetSocketAddress("localhost", sv_port), probe.ref))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_probe.expectMsg(3.second, ClientHandlerReady)


      val open_req_body = List((MessageTypeTag, Some(OPEN_REQ)), (InvokeID, 0x04030201),
        (VersionNumber, ProtocolVersion), (IdleTimeout, NoIdleTimeout), (PeripheralID, 0x02030405),
        (ServiceRequested, BitSet.empty + CtiServiceMask.ALL_EVENTS),
        (CallMsgMask, BitSet.empty + CallEventMessageMask.BEGIN_CALL + CallEventMessageMask.END_CALL),
        (AgentStateMaskTag, BitSet.empty + AgentStateMask.AGENT_AVAILABLE + AgentStateMask.AGENT_HOLD),
        (ConfigMsgMask, BitSet.empty), (Reserved1, 0), (Reserved2, 0), (Reserved3, 0),
        (CLIENT_ID, "ClientID"), (CLIENT_PASSWORD, ByteString()), (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_EXTENSION, "3001"),(AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (APP_PATH_ID, 0x03040506)).encode
      val open_conf_body = ByteString(0,0,0,4, 4,3,2,1, 0,0,0,0x10, 2,3,4,5, 0,0,0,0x13, 4,5,6,7, 0,1, 0,17, 0,3,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        228,2,5,6, 224,2,0,0)
      val open_conf_raw = open_conf_body.withlength
      val open_conf_expected = List((MessageTypeTag, Some(OPEN_CONF)), (InvokeID, 0x04030201),
        (ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS), (MonitorID, 0x02030405),
        (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN + PGStatusCode.LIMITED_FUNCTION),
        (ICMCentralControllerTime, 0x04050607), (PeripheralOnline, true),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)), (AgentStateTag, Some(AgentState.AVAILABLE)),
        (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (NUM_PERIPHERALS, 0x0506.toShort),
        (MULTI_LINE_AGENT_CONTROL, false))

      server ! Scenario(List(open_conf_raw))
      client ! open_req_body
      server ! Tick
      val msg = probe.expectMsgClass(3.second, classOf[akka.util.ByteString])
      msg.decode mustBe open_conf_expected
    }

    "work with FAILURE_EVENT" in {
      val server_probe = TestProbe()
      server ! WarmRestart(server_probe.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(classOf[SocketActor], new InetSocketAddress("localhost", sv_port), probe.ref))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_probe.expectMsg(3.second, ClientHandlerReady)


      val open_req_body = List((MessageTypeTag, Some(OPEN_REQ)), (InvokeID, 0x04030201),
        (VersionNumber, ProtocolVersion), (IdleTimeout, NoIdleTimeout), (PeripheralID, 0x02030405),
        (ServiceRequested, BitSet.empty + CtiServiceMask.ALL_EVENTS),
        (CallMsgMask, BitSet.empty + CallEventMessageMask.BEGIN_CALL + CallEventMessageMask.END_CALL),
        (AgentStateMaskTag, BitSet.empty + AgentStateMask.AGENT_AVAILABLE + AgentStateMask.AGENT_HOLD),
        (ConfigMsgMask, BitSet.empty), (Reserved1, 0), (Reserved2, 0), (Reserved3, 0),
        (CLIENT_ID, "ClientID"), (CLIENT_PASSWORD, ByteString()), (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_EXTENSION, "3001"),(AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (APP_PATH_ID, 0x03040506)).encode
      val open_conf_raw = List((MessageTypeTag, Some(OPEN_CONF)), (InvokeID, 0x04030201),
        (ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS), (MonitorID, 0x02030405),
        (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN + PGStatusCode.LIMITED_FUNCTION),
        (ICMCentralControllerTime, 0x04050607), (PeripheralOnline, true),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)), (AgentStateTag, Some(AgentState.AVAILABLE)),
        (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (NUM_PERIPHERALS, 0x0506.toShort),
        (MULTI_LINE_AGENT_CONTROL, false)).encode.withlength
      val failure_event_msg = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (Status, Some(StatusCode.UNSPECIFIED_FAILURE)))
      val failure_event_raw = failure_event_msg.encode.withlength

      server ! Scenario(List(open_conf_raw, failure_event_raw))
      client ! open_req_body
      server ! Tick
      server ! Tick
      probe.expectMsgClass(3.second, classOf[akka.util.ByteString])
      val msg = probe.expectMsgClass(3.second, classOf[akka.util.ByteString])
      msg.decode mustBe failure_event_msg
   }
  }

  "MessageFilter" must {
//    "be constructed with multiple listener" in {
//      def handler1(p: ByteString): Unit = Unit
//      val msgset1 = Set(AGENT_STATE_EVENT)
//      val listener1: (ByteString => Unit, Set[MessageType]) = (handler1, msgset1)
//      val handler2 = (p: ByteString) => Unit: Unit
//      val msgset2 = Set(BEGIN_CALL_EVENT, END_CALL_EVENT)
//      val listener2 = (handler2, msgset2)
//      val filter_conf = Seq(listener1, listener2)
//      val msg_filter = new MessageFilter(filter_conf)
//
//      msg_filter.toString mustBe MessageFilter(Seq(
//        ((p: ByteString) => Unit: Unit, Set(AGENT_STATE_EVENT)),
//        ((p: ByteString) => Unit: Unit, Set(BEGIN_CALL_EVENT, END_CALL_EVENT)))).toString
//    }

    "returns handlers which wanted to process the message" in {
      val handler = (m: Message) => Unit: Unit
      val entry = MessageFilterEntry(handler, Set(AGENT_STATE_EVENT))
      val filter = MessageFilter(Traversable(entry))
      val msg = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304)).encode

      filter.handlers(msg) mustBe Traversable(handler)
    }

    "work with multiple type of messages" in {
      val handler = (m: Message) => Unit: Unit
      val entry = MessageFilterEntry(handler, Set(BEGIN_CALL_EVENT, END_CALL_EVENT))
      val filter = MessageFilter(Traversable(entry))
      val msg1 = List((MessageTypeTag, Some(END_CALL_EVENT)), (MonitorID, 0x01020304)).encode
      val msg2 = List((MessageTypeTag, Some(BEGIN_CALL_EVENT)), (MonitorID, 0x01020304)).encode

      filter.handlers(msg1) mustBe Traversable(handler)
      filter.handlers(msg2) mustBe Traversable(handler)
    }

    "identify all defined type of message" in {
      val handler = (m: Message) => Unit: Unit
      val filter = MessageFilter(Traversable(MessageFilterEntry(handler, MessageType.values)))
      for (mtyp <- MessageType.values) {
        val msg = encodeByteString(mtyp.id) ++ ByteString(4,3,2,1, 9,8,7,6)
        filter.handlers(msg) mustBe Traversable(handler)
      }
    }

    "return empty list of handler if no handler wanted to process the message" in {
      val handler1 = (m: Message) => Unit: Unit
      val handler2 = (m: Message) => Unit: Unit
      val entry1 = MessageFilterEntry(handler1, Set(AGENT_STATE_EVENT))
      val entry2 = MessageFilterEntry(handler2, Set(BEGIN_CALL_EVENT, END_CALL_EVENT))
      val filter = MessageFilter(Traversable(entry1, entry2))
      val msg = List((MessageTypeTag, Some(CLOSE_CONF)), (InvokeID, 0x03040506)).encode

      filter.handlers(msg) mustBe Traversable()
    }

    "return only desiring handler for the message" in {
      val handler1 = (m: Message) => Unit: Unit
      val handler2 = (m: Message) => Unit: Unit
      val entry1 = MessageFilterEntry(handler1, Set(FAILURE_EVENT))
      val entry2 = MessageFilterEntry(handler2, Set(FAILURE_EVENT, FAILURE_CONF))
      val filter = MessageFilter(Traversable(entry1, entry2))
      val msg = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x03040506)).encode

      filter.handlers(msg) mustBe Traversable(handler2)
    }

    "return all desiring handlers for the message" in {
      val handler1 = (m: Message) => Unit: Unit
      val handler2 = (m: Message) => Unit: Unit
      val entry1 = MessageFilterEntry(handler1, Set(FAILURE_EVENT))
      val entry2 = MessageFilterEntry(handler2, Set(FAILURE_EVENT, FAILURE_CONF))
      val filter = MessageFilter(Traversable(entry1, entry2))
      val msg = List((MessageTypeTag, Some(FAILURE_EVENT)), (InvokeID, 0x03040506)).encode

      (filter.handlers(msg).toSeq diff Seq(handler2, handler1)) mustBe Seq()
    }

    "return no handler for undefined message type" in {
      val handler = (m: Message) => Unit: Unit
      val filter = MessageFilter(Traversable(MessageFilterEntry(handler, MessageType.values)))
      val msg = ByteString(1,1,1,1, 1,2,3,4, 5,6,7,8)

      filter.handlers(msg) mustBe Traversable()
    }
  }

//  "SessionActor" must {
//    "accept server InetSockAddr and message listener on construction" in {
//      val
//
//      val session = system.actorOf(Props(new SocketActor(new InetSocketAddress("localhost", server_port), probe.ref)))
//
//    }
//  }
}

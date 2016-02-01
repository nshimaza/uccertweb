package ctidriver

import java.net.InetSocketAddress

import akka.actor.{ Actor, ActorSystem, Props }
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

  val server_port = 42029
  val server = system.actorOf(Props(new FakeCtiServer(server_port)))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "PacketizerActor" must {
    "start with state WAIT_LENGTH" in {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actor.state must be(actor.State.WAIT_LENGTH)
    }

    "stay on state WAIT_LENGTH until 4 bytes received" in  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3)
      actor.state must be(actor.State.WAIT_LENGTH)
    }

    "transit to state WAIT_BODY when 4 bytes received" in new  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3, 4)
      actor.state must be(actor.State.WAIT_BODY)
    }

    "transit to state WAIT_BODY when 4 bytes received separately" in  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1)
      actor.state must be(actor.State.WAIT_LENGTH)
      actorRef ! ByteString(2)
      actor.state must be(actor.State.WAIT_LENGTH)
      actorRef ! ByteString(3)
      actor.state must be(actor.State.WAIT_LENGTH)
      actorRef ! ByteString(4)
      actor.state must be(actor.State.WAIT_BODY)
    }

    "transit to state WAIT_BODY when more than 4 bytes received" in  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3, 4, 5)
      actor.state must be(actor.State.WAIT_BODY)
    }

    "keep remaining data received when successfully decode message length" in  {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3, 4, 5)
      actor.state must be(actor.State.WAIT_BODY)
      actor.buf.size must be(1)
      actor.buf.head must be(5: Byte)
    }

    "decode and keep message length when transit to WAIT_BODY" in {
      val actorRef = TestActorRef(Props(new PacketizerActor(TestProbe().ref)))
      val actor: PacketizerActor = actorRef.underlyingActor

      actorRef ! ByteString(1, 2, 3, 4, 5, 6, 7, 8)
      actor.state must be(actor.State.WAIT_BODY)
      actor.offset must be(4 - (0x01020304 + 4))
    }

  }

  "Packetizer" must {
    "start with state WAIT_LENGTH" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer.state must be(packetizer.State.WAIT_LENGTH)
    }

    "start with offset -4" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer.offset must be(-4)
    }

    "stay on state WAIT_LENGTH until 4 bytes received" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer receive ByteString(1, 2, 3)
      packetizer.state must be(packetizer.State.WAIT_LENGTH)
    }

    "transit to state WAIT_BODY when 4 bytes received" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer receive ByteString(1, 2, 3, 4)
      packetizer.state must be(packetizer.State.WAIT_BODY)
    }

    "transit to state WAIT_BODY when 4 bytes received separately" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer receive ByteString(1)
      packetizer.state must be(packetizer.State.WAIT_LENGTH)
      packetizer receive ByteString(2)
      packetizer.state must be(packetizer.State.WAIT_LENGTH)
      packetizer receive ByteString(3)
      packetizer.state must be(packetizer.State.WAIT_LENGTH)
      packetizer receive ByteString(4)
      packetizer.state must be(packetizer.State.WAIT_BODY)
    }

    "transit to state WAIT_BODY when more than 4 bytes received" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer receive ByteString(1, 2, 3, 4, 5)
      packetizer.state must be(packetizer.State.WAIT_BODY)
    }

    "keep remaining data received when successfully decode message length" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer receive ByteString(1, 2, 3, 4, 5)
      packetizer.state must be(packetizer.State.WAIT_BODY)
      packetizer.buf.size must be(1)
      packetizer.buf.head must be(5: Byte)
    }

    "decode and keep message length when transit to WAIT_BODY" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer receive ByteString(1,2,3,4, 5,6,7,8)
      packetizer.state must be(packetizer.State.WAIT_BODY)
      packetizer.offset must be(4 - (0x01020304 + 4))
    }

    "transit back to state WAIT_LENGTH when desired bytes are received" in {
      val packetizer = new Packetizer(TestProbe().ref)

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8)
      packetizer.state must be(packetizer.State.WAIT_LENGTH)
      packetizer.offset must be(-4)
    }

    "send entire single message to listener when it reached next WAIT_LENGTH state" in {
      val probe = TestProbe()
      val packetizer = new Packetizer(probe.ref)

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8)
      packetizer.state must be(packetizer.State.WAIT_LENGTH)
      packetizer.offset must be(-4)
      probe.expectMsg(1.second, ByteString(1,2,3,4, 5,6,7,8))
    }

    "keep remaining bytes after sending an entire message" in {
      val probe = TestProbe()
      val packetizer = new Packetizer(probe.ref)

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 9,10)
      packetizer.state must be(packetizer.State.WAIT_LENGTH)
      packetizer.offset must be(-2)
      probe.expectMsg(1.second, ByteString(1,2,3,4, 5,6,7,8))
      packetizer.buf must be(ByteString(9,10))
    }

    "can separate multiple messages received at a time" in {
      val probe = TestProbe()
      val packetizer = new Packetizer(probe.ref)

      packetizer receive ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 0,0,0,3, 2,3,4,5, 6,7,8)
      packetizer.state must be(packetizer.State.WAIT_LENGTH)
      packetizer.offset must be(-4)
      probe.expectMsgAllOf(1.second, ByteString(1,2,3,4, 5,6,7,8), ByteString(2,3,4,5, 6,7,8)) must
        be(Seq(ByteString(1,2,3,4, 5,6,7,8), ByteString(2,3,4,5, 6,7,8)))
    }
  }

  "SocketActor" must {
    "connect to server" in {
      val probe = TestProbe()
      val client = system.actorOf(Props(new SocketActor(new InetSocketAddress("localhost", server_port), probe.ref)))

      val msg = probe.expectMsgClass(3.second, classOf[Connected])

      msg.remoteAddress.getHostName must be("localhost")
      msg.remoteAddress.getPort must be(server_port)
      msg.localAddress.getHostName must be("localhost")

      client ! SessionProtocol.Close
    }

    "packetize received stream" in {
      val server_prove = TestProbe()
      server ! AddServerProve(server_prove.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(new SocketActor(new InetSocketAddress("localhost", server_port), probe.ref)))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_prove.expectMsg(3.second, ClientHandlerReady)

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

      client ! SessionProtocol.Close
      server ! RemoveServerProve(server_prove.ref)
    }

    "work with OPEN_REQ and OPEN_CONF" in {
      val server_prove = TestProbe()
      server ! AddServerProve(server_prove.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(new SocketActor(new InetSocketAddress("localhost", server_port), probe.ref)))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_prove.expectMsg(3.second, ClientHandlerReady)


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
      msg.decode must be(open_conf_expected)

      client ! SessionProtocol.Close
      server ! RemoveServerProve(server_prove.ref)
    }

    "work with FAILURE_EVENT" in {
      val server_prove = TestProbe()
      server ! AddServerProve(server_prove.ref)
      val probe = TestProbe()
      val client = system.actorOf(Props(new SocketActor(new InetSocketAddress("localhost", server_port), probe.ref)))
      probe.expectMsgClass(3.second, classOf[Connected])
      server_prove.expectMsg(3.second, ClientHandlerReady)


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
      msg.decode must be(failure_event_msg)

      client ! SessionProtocol.Close
      server ! RemoveServerProve(server_prove.ref)
    }
  }
}

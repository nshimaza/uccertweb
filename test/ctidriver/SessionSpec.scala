package ctidriver

import akka.actor.{ Actor, ActorSystem, Props }
import akka.testkit.{ TestActorRef, TestActors, TestKit, TestProbe }
import akka.util.ByteString
import org.scalatest.{ WordSpecLike, MustMatchers, BeforeAndAfterAll }
import scala.concurrent.duration._
import scala.concurrent.Future


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
//  val stub = system.actorOf(Props[StubReceiver], "stub")

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

  "Packetizer" should {
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
}

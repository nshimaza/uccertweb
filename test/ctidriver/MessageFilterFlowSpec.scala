package ctidriver

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString
import ctidriver.MessageType._
import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatest.junit.JUnitRunner

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by nshimaza on 2016/04/05.
  */
@RunWith(classOf[JUnitRunner])
class MessageFilterFlowSpec extends WordSpec with MustMatchers {

  implicit val system = ActorSystem("MessageFilterFlowSpec")
  implicit val materializer = ActorMaterializer()

  "MessageFilterFlow" must {
    "pass through entire received ByteString frame if desired message type" in {
      val msg = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304)).encode
      val i = Source(List(msg))

      val msgFilter = MessageFilterFlow(Set(AGENT_STATE_EVENT))

      val f: Future[Seq[ByteString]] = i.via(msgFilter).runWith(Sink.seq)
      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs Seq(msg)
    }

    "pass through multiple message with same message type" in {
      val msg1 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304)).encode
      val msg2 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020305)).encode
      val msg3 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020306)).encode
      val msg4 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020307)).encode
      val i = Source(List(msg1, msg2, msg3, msg4))

      val msgFilter = MessageFilterFlow(Set(AGENT_STATE_EVENT))

      val f: Future[Seq[ByteString]] = i.via(msgFilter).runWith(Sink.seq)
      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs  Seq(msg1, msg2, msg3, msg4)
    }

    "path through multiple message types" in {
      val msg1 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304)).encode
      val msg2 = List((MessageTypeTag, Some(BEGIN_CALL_EVENT)), (MonitorID, 0x01020305)).encode
      val msg3 = List((MessageTypeTag, Some(END_CALL_EVENT)), (MonitorID, 0x01020306)).encode
      val msg4 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020307)).encode
      val i = Source(List(msg1, msg2, msg3, msg4))

      val msgFilter = MessageFilterFlow(Set(AGENT_STATE_EVENT, BEGIN_CALL_EVENT))

      val f: Future[Seq[ByteString]] = i.via(msgFilter).runWith(Sink.seq)
      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs Seq(msg1, msg2, msg4)
    }

    "identify all defined message types" in {
      val msgs = MessageType.values.toList.map(t => encodeByteString(t.id) ++ ByteString(4,3,2,1, 9,8,7,6))
      val i = Source(msgs)

      val msgFilter = MessageFilterFlow(MessageType.values)

      val f: Future[Seq[ByteString]] = i.via(msgFilter).runWith(Sink.seq)
      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs msgs
    }

    "drop not configured message type" in {
      val msg1 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304)).encode
      val msg2 = List((MessageTypeTag, Some(BEGIN_CALL_EVENT)), (MonitorID, 0x01020305)).encode
      val msg3 = List((MessageTypeTag, Some(END_CALL_EVENT)), (MonitorID, 0x01020306)).encode
      val msg4 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020307)).encode
      val i = Source(List(msg1, msg2, msg3, msg4))

      val msgFilter = MessageFilterFlow(Set(AGENT_STATE_EVENT))

      val f: Future[Seq[ByteString]] = i.via(msgFilter).runWith(Sink.seq)
      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs Seq(msg1, msg4)
    }

    "drop all frames with empty set of configured message types" in {
      val msg1 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304)).encode
      val msg2 = List((MessageTypeTag, Some(BEGIN_CALL_EVENT)), (MonitorID, 0x01020305)).encode
      val msg3 = List((MessageTypeTag, Some(END_CALL_EVENT)), (MonitorID, 0x01020306)).encode
      val msg4 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020307)).encode
      val i = Source(List(msg1, msg2, msg3, msg4))

      val msgFilter = MessageFilterFlow(Set.empty)

      val f: Future[Seq[ByteString]] = i.via(msgFilter).runWith(Sink.seq)
      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs Seq.empty
    }

    "drop all frames if non of message are configured" in {
      val msg1 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304)).encode
      val msg2 = List((MessageTypeTag, Some(BEGIN_CALL_EVENT)), (MonitorID, 0x01020305)).encode
      val msg3 = List((MessageTypeTag, Some(END_CALL_EVENT)), (MonitorID, 0x01020306)).encode
      val msg4 = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020307)).encode
      val i = Source(List(msg1, msg2, msg3, msg4))

      val msgFilter = MessageFilterFlow(Set(OPEN_CONF, CLOSE_CONF))

      val f: Future[Seq[ByteString]] = i.via(msgFilter).runWith(Sink.seq)
      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs Seq.empty
    }

    "drop frame if message type is unrecognized" in {
      val msg = ByteString(1,1,1,1, 1,2,3,4, 5,6,7,8)
      val i = Source(List(msg))

      val msgFilter = MessageFilterFlow(MessageType.values)

      val f: Future[Seq[ByteString]] = i.via(msgFilter).runWith(Sink.seq)
      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs Seq.empty
    }
  }
}

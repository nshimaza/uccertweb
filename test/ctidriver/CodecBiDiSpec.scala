package ctidriver

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import akka.stream.testkit.scaladsl.TestSink
import akka.util.ByteString
import ctidriver.MessageType._
import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{MustMatchers, WordSpec}

import scala.annotation.tailrec
import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by nshimaza on 2016/04/12.
  */
@RunWith(classOf[JUnitRunner])
class CodecBiDiSpec extends WordSpec with MustMatchers {

  implicit val system = ActorSystem("CodecBiDiSpec")
  implicit val materializer = ActorMaterializer()

  def buildProbeGraph(raw: List[ByteString], messageTypes: Set[MessageType]) = {
    val codec = CodecBiDi(messageTypes)
    val out = TestSink.probe[Message]

    RunnableGraph.fromGraph(GraphDSL.create(out) { implicit builder =>
      o =>
        import GraphDSL.Implicits._

        val c = builder.add(codec)

        //@formatter:off
        Source(raw) ~> c.in2
                       c.out2 ~> o

        Source.empty[Message] ~> c.in1
                                 c.out1 ~> Sink.ignore
        //@formatter:on
        ClosedShape
    })
  }

  def buildSeqGraph(raw: List[ByteString], messageTypes: Set[MessageType]) = {
    val codec = CodecBiDi(messageTypes)
    val out = Sink.seq[Message]

    RunnableGraph.fromGraph(GraphDSL.create(out) { implicit builder =>
      o =>
        import GraphDSL.Implicits._

        val c = builder.add(codec)

        //@formatter:off
        Source(raw) ~> c.in2
                       c.out2 ~> o

        Source.empty[Message] ~> c.in1
                                 c.out1 ~> Sink.ignore
        //@formatter:on
        ClosedShape
    })
  }

  def interleave(b: ByteString, len: Int): List[ByteString] = {
    @tailrec
    def loop(r: List[ByteString], s: ByteString): List[ByteString] = {
      if (s.isEmpty) {
        r
      } else {
        val (s1, s2) = s.splitAt(len)
        loop(r :+ s1, s2)
      }
    }

    loop(Nil, b)
  }

  "CodecBiDi" must {
    "decode single complete message" in {
      val raw = List(ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength)
      val msg: Message = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
        (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))

      buildProbeGraph(raw, MessageType.values).run()
        .request(1)
        .expectNext(msg)
        .expectComplete()
    }

    "decode two messages in a raw byte chunk" in {
      val raw = List(
        ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength ++
        ByteString(0,0,0,2, 0,0,0,17).withLength
      )
      val msg1: Message = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
        (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))
      val msg2: Message = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))

      buildProbeGraph(raw, MessageType.values).run()
        .request(2)
        .expectNext(msg1, msg2)
        .expectComplete()
    }

    "decode two messages in separate raw byte chunks" in {
      val raw = List(
        ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength,
        ByteString(0,0,0,2, 0,0,0,17).withLength
      )
      val msg1: Message = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
        (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))
      val msg2: Message = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))

      buildProbeGraph(raw, MessageType.values).run()
        .request(2)
        .expectNext(msg1, msg2)
        .expectComplete()
    }

    "decode three messages in a raw byte chunk" in {
      val raw = List(
        ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength ++
          ByteString(0,0,0,2, 0,0,0,17).withLength ++
          ByteString(0,0,0,6, 3,4,6,7).withLength
      )
      val msg1: Message = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
        (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))
      val msg2: Message = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))
      val msg3: Message = List((MessageTypeTag, Some(HEARTBEAT_CONF)), (InvokeID, 0x03040607))

      buildProbeGraph(raw, MessageType.values).run()
        .request(3)
        .expectNext(msg1, msg2, msg3)
        .expectComplete()
    }

    "decode three messages in separate raw byte chunks" in {
      val raw = List(
        ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength,
        ByteString(0,0,0,2, 0,0,0,17).withLength,
        ByteString(0,0,0,6, 3,4,6,7).withLength
      )
      val msg1: Message = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
        (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))
      val msg2: Message = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))
      val msg3: Message = List((MessageTypeTag, Some(HEARTBEAT_CONF)), (InvokeID, 0x03040607))

      buildProbeGraph(raw, MessageType.values).run()
        .request(3)
        .expectNext(msg1, msg2, msg3)
        .expectComplete()
    }

    "decode messages interleaved into multiple chunks" in {
      val bytes = ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength ++
        ByteString(0,0,0,2, 0,0,0,17).withLength ++
        ByteString(0,0,0,6, 3,4,6,7).withLength
      val bytes1 = bytes.take(10)
      val bytes2 = bytes.drop(10).take(10)
      val bytes3 = bytes.drop(20).take(10)
      val bytes4 = bytes.drop(30)
      val raw = List(bytes1, bytes2, bytes3, bytes4)
      val msg1: Message = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
        (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))
      val msg2: Message = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))
      val msg3: Message = List((MessageTypeTag, Some(HEARTBEAT_CONF)), (InvokeID, 0x03040607))

      buildProbeGraph(raw, MessageType.values).run()
        .request(3)
        .expectNext(msg1, msg2, msg3)
        .expectComplete()
    }

    "decode each message when same message type arrived" in {
      val bytes = ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength ++
        ByteString(0,0,0,6, 3,4,6,7).withLength ++
        ByteString(0,0,0,6, 3,4,6,7).withLength
      val bytes1 = bytes.take(10)
      val bytes2 = bytes.drop(10).take(10)
      val bytes3 = bytes.drop(20).take(10)
      val bytes4 = bytes.drop(30)
      val raw = List(bytes1, bytes2, bytes3, bytes4)
      val msg1: Message = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
        (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))
      val msg3: Message = List((MessageTypeTag, Some(HEARTBEAT_CONF)), (InvokeID, 0x03040607))

      buildProbeGraph(raw, MessageType.values).run()
        .request(3)
        .expectNext(msg1, msg3, msg3)
        .expectComplete()
    }

    "decode only desired message" in {
      val bytes = ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength ++
        ByteString(0,0,0,2, 0,0,0,17).withLength ++
        ByteString(0,0,0,6, 3,4,6,7).withLength
      val bytes1 = bytes.take(10)
      val bytes2 = bytes.drop(10).take(10)
      val bytes3 = bytes.drop(20).take(10)
      val bytes4 = bytes.drop(30)
      val raw = List(bytes1, bytes2, bytes3, bytes4)
      val msg2: Message = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))

      buildProbeGraph(raw, Set(FAILURE_EVENT)).run()
        .request(3)
        .expectNext(msg2)
        .expectComplete()
    }

    "decode only desired messages" in {
      val bytes = ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength ++
        ByteString(0,0,0,2, 0,0,0,17).withLength ++
        ByteString(0,0,0,6, 3,4,6,7).withLength
      val bytes1 = bytes.take(10)
      val bytes2 = bytes.drop(10).take(10)
      val bytes3 = bytes.drop(20).take(10)
      val bytes4 = bytes.drop(30)
      val raw = List(bytes1, bytes2, bytes3, bytes4)
      val msg1: Message = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
        (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS)))
      val msg3: Message = List((MessageTypeTag, Some(HEARTBEAT_CONF)), (InvokeID, 0x03040607))

      buildProbeGraph(raw, Set(FAILURE_CONF, HEARTBEAT_CONF)).run()
        .request(3)
        .expectNext(msg1, msg3)
        .expectComplete()
    }

    "decode nothing with empty filter" in {
      val bytes = ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength ++
        ByteString(0,0,0,2, 0,0,0,17).withLength ++
        ByteString(0,0,0,6, 3,4,6,7).withLength
      val bytes1 = bytes.take(10)
      val bytes2 = bytes.drop(10).take(10)
      val bytes3 = bytes.drop(20).take(10)
      val bytes4 = bytes.drop(30)
      val raw = List(bytes1, bytes2, bytes3, bytes4)

      buildProbeGraph(raw, Set()).run()
        .request(1)
        .expectComplete()
    }

    "decode nothing when no message matched to given filter" in {
      val bytes = ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97).withLength ++
        ByteString(0,0,0,2, 0,0,0,17).withLength ++
        ByteString(0,0,0,6, 3,4,6,7).withLength
      val bytes1 = bytes.take(10)
      val bytes2 = bytes.drop(10).take(10)
      val bytes3 = bytes.drop(20).take(10)
      val bytes4 = bytes.drop(30)
      val raw = List(bytes1, bytes2, bytes3, bytes4)

      buildProbeGraph(raw, Set(CLOSE_CONF, CALL_DELIVERED_EVENT, CALL_HELD_EVENT)).run()
        .request(1)
        .expectComplete()
    }

    "decode all sample messages in separate byte strings" in {
      val raw = MessageSamples.samples.map(_._2.withLength)

      val f = buildSeqGraph(raw, MessageType.values).run()

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs MessageSamples.samples.map(_._1)
    }

    "decode all sample messages in single byte string" in {
      val raw = List(MessageSamples.samples.map(_._2.withLength).reduce(_ ++ _))

      val f = buildSeqGraph(raw, MessageType.values).run()

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs MessageSamples.samples.map(_._1)
    }

    "decode all sample messages in large interleaved byte string" in {
      val raw = interleave(MessageSamples.samples.map(_._2.withLength).reduce(_ ++ _), 200)

      val f = buildSeqGraph(raw, MessageType.values).run()

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs MessageSamples.samples.map(_._1)
    }

    "decode all sample messages in small interleaved byte string" in {
      val raw = interleave(MessageSamples.samples.map(_._2.withLength).reduce(_ ++ _), 5)

      val f = buildSeqGraph(raw, MessageType.values).run()

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs MessageSamples.samples.map(_._1)
    }

    "encode and decode all sample messages" in {
      val messages = MessageSamples.samples.map(_._1)

      val f = Source(messages)
        .via(CodecBiDi(MessageType.values).join(Flow.apply))
        .runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs messages
    }
  }
}

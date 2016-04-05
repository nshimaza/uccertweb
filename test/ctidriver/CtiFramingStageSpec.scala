package ctidriver

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString
import org.junit.runner.RunWith
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatest.junit.JUnitRunner

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/**
  * Created by nshimaza on 2016/04/05.
  */
@RunWith(classOf[JUnitRunner])
class CtiFramingStageSpec extends WordSpec with MustMatchers {

  implicit val system = ActorSystem("CtiFramingStageSpec")
  implicit val materializer = ActorMaterializer()

  "CtiFramingStage" must {
    "separate single complete frame" in {
      val i = Source(List(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8)))
      val framing = new CtiFramingStage
      val f: Future[Seq[ByteString]] = i.via(framing).runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs
        Seq(ByteString(1,2,3,4, 5,6,7,8))
    }

    "separate two complete frame" in {
      val i = Source(List(ByteString(
        0,0,0,4, 1,2,3,4, 5,6,7,8,
        0,0,0,3, 2,3,4,5, 6,7,8
      )))
      val framing = new CtiFramingStage
      val f: Future[Seq[ByteString]] = i.via(framing).runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs
        Seq(ByteString(1,2,3,4, 5,6,7,8), ByteString(2,3,4,5, 6,7,8))
    }

    "separate multiple complete frame" in {
      val i = Source(List(ByteString(
        0,0,0,1, 11,12,13,14, 1,
        0,0,0,2, 11,12,13,14, 2,3,
        0,0,0,3, 11,12,13,14, 3,4,5,
        0,0,0,4, 11,12,13,14, 4,5,6,7
      )))
      val framing = new CtiFramingStage
      val f: Future[Seq[ByteString]] = i.via(framing).runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs
        Seq(ByteString(11,12,13,14, 1),
          ByteString(11,12,13,14, 2,3),
          ByteString(11,12,13,14, 3,4,5),
          ByteString(11,12,13,14, 4,5,6,7))
    }

    "truncate entire bytes of incomplete frame" in {
      val i = Source(List(ByteString(0,0,0,4, 1)))
      val framing = new CtiFramingStage
      val f: Future[Seq[ByteString]] = i.via(framing).runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs
        Seq()
    }

    "truncate only last incomplete frame" in {
      val i = Source(List(ByteString(
        0,0,0,1, 11,12,13,14, 1,
        0,0,0,2, 11,12,13,14, 2,3,
        0,0,0,3, 11,12,13,14, 3,4,5,
        0,0,0,4, 11,12,13,14, 4,5,6
      )))
      val framing = new CtiFramingStage
      val f: Future[Seq[ByteString]] = i.via(framing).runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs
        Seq(ByteString(11,12,13,14, 1),
          ByteString(11,12,13,14, 2,3),
          ByteString(11,12,13,14, 3,4,5))
    }

    "join separately received chunks into single complete frame" in {
      val i = Source(List(
        ByteString(0,0),
        ByteString(0),
        ByteString(4, 1),
        ByteString(2,3),
        ByteString(4, 5,6,7,8)
      ))
      val framing = new CtiFramingStage
      val f: Future[Seq[ByteString]] = i.via(framing).runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs
        Seq(ByteString(1,2,3,4, 5,6,7,8))
    }

    "form complete frames from interleaved byte stream" in {
      val i = Source(List(
        ByteString(0,0,0,4, 1,2,3,4, 5,6),
        ByteString(7,8, 0,0,0,2, 1,2,3,4, 5,6, 0,0),
        ByteString(0,3, 1,2,3,4, 5,6,7)
      ))
      val framing = new CtiFramingStage
      val f: Future[Seq[ByteString]] = i.via(framing).runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs
        Seq(ByteString(1,2,3,4, 5,6,7,8), ByteString(1,2,3,4, 5,6), ByteString(1,2,3,4, 5,6,7))
    }
  }
}

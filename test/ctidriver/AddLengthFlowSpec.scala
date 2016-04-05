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
class AddLengthFlowSpec extends WordSpec with MustMatchers {

  implicit val system = ActorSystem("AddLengthFlowSpec")
  implicit val materializer = ActorMaterializer()

  "AddLengthFlow" must {
    "add length field with received ByteString - 4 in 4 octet big endian" in {
      val i = Source(List(ByteString(1,2,3,4,5,6,7,8,9,10)))
      val addLen = AddLengthFlow()
      val f: Future[Seq[ByteString]] = i.via(addLen).runWith(Sink.seq)

      Await.result(f, 3.seconds) must contain theSameElementsInOrderAs
        Seq(ByteString(0,0,0,6, 1,2,3,4,5,6,7,8,9,10))
    }
  }
}

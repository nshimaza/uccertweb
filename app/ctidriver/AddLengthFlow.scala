package ctidriver

import java.nio.ByteOrder

import akka.stream.scaladsl.Flow
import akka.util.ByteString

/**
  * Created by nshimaza on 2016/04/05.
  */
object AddLengthFlow {
  implicit val order = ByteOrder.BIG_ENDIAN

  def apply() = {
    Flow[ByteString].map(bytes => ByteString.newBuilder.putInt(bytes.size - 4).append(bytes).result())
  }
}

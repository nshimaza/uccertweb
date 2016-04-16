package ctidriver

import akka.stream.scaladsl.Flow
import akka.util.ByteString
import ctidriver.MessageType.MessageType

/**
  * Created by nshimaza on 2016/04/05.
  */
object MessageFilterFlow {
  def apply(set: Set[MessageType]) = {
    def isInterestedMessage(bytes: ByteString) = {
      val ((tag, mayBeMessageType), len) = MessageType.decode(Tag.MessageTypeTag, bytes)
      mayBeMessageType exists set.contains
    }

    Flow[ByteString].filter(isInterestedMessage(_))
  }
}

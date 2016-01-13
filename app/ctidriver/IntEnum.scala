package ctidriver

import akka.util.ByteString

/**
 * Enumeration with factory func which returns Value in wrapping Option.
 */
class IntEnum extends Enumeration {
  def decode(tag: Tag, buf: ByteString): (Tag, Option[Value]) = {
    try {
      (tag, Some(super.apply(buf.toInt)))
    } catch {
      case e: NoSuchElementException => (tag, None)
    }
  }

  def decode2(tag: Tag, buf: ByteString): ((Tag, Option[Value]), ByteString) = {
    try {
      ((tag, Some(super.apply(buf.toInt))), buf.drop(4))
    } catch {
      case e: NoSuchElementException => ((tag, None), buf.drop(4))
    }
  }

  def decodeWithLen(tag: Tag, buf: ByteString) = (decode(tag, buf), 4)
}

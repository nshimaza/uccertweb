package ctidriver

import akka.util.ByteString

/**
 * Enumeration with factory func which returns Value in wrapping Option.
 */
class IntEnum extends Enumeration {
  def decode(tag: Tag.Value, buf: ByteString): (Tag.Value, Option[Value]) = {
    try {
      (tag, Some(super.apply(buf.toInt)))
    } catch {
      case e: NoSuchElementException => (tag, None)
    }
  }

  def decode2(tag: Tag.Value, buf: ByteString): ((Tag.Value, Option[Value]), ByteString) = {
    try {
      ((tag, Some(super.apply(buf.toInt))), buf.drop(4))
    } catch {
      case e: NoSuchElementException => ((tag, None), buf.drop(4))
    }
  }

  def decodeWithLen(tag: Tag.Value, buf: ByteString) = (decode(tag, buf), 4)
}

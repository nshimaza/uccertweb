package ctidriver

import akka.util.ByteString

/**
 * Enumeration with factory func which returns Value in wrapping Option.
 */
class ByteEnum extends Enumeration {
  def decode(tag: Tag, buf: ByteString): (Tag, Option[Value]) = {
    try {
      (tag, Some(super.apply(buf(0).toInt & 0xff)))
    } catch {
      case e: NoSuchElementException => (tag, None)
    }
  }

  def decodeWithLen(tag: Tag, buf: ByteString) = (decode(tag, buf), 1)
}

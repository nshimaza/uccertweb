package ctidriver

import akka.util.ByteString

/**
 * Enumeration with factory func which returns Value in wrapping Option.
 */
class ShortEnum extends Enumeration {
  def decode(tag: Tag.Value, buf: ByteString): (Tag.Value, Option[Value]) = {
    try {
      (tag, Some(super.apply(buf.toShort.toInt & 0xffff)))
    } catch {
      case e: NoSuchElementException => (tag, None)
    }
  }

  def decodeWithLen(tag: Tag.Value, buf: ByteString) = (decode(tag, buf), 2)
}

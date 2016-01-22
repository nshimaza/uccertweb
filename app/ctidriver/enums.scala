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

  def getId(a: Any): Int = a match { case o: Option[Any] => o.get match { case n: this.Value => n.id } }

  def encode(a: Any) = ByteString(getId(a))
  def encodeFloat(tag: Tag, a: Any) = ByteString(tag.id, 1) ++ encode(a)
}

class ShortEnum extends Enumeration {
  def decode(tag: Tag, buf: ByteString): (Tag, Option[Value]) = {
    try {
      (tag, Some(super.apply(buf.toShort.toInt & 0xffff)))
    } catch {
      case e: NoSuchElementException => (tag, None)
    }
  }

  def decodeWithLen(tag: Tag, buf: ByteString) = (decode(tag, buf), 2)

  def getId(a: Any): Int = a match { case o: Option[Any] => o.get match { case n: this.Value => n.id } }

  def encode(a: Any) = encodeByteString(getId(a).toShort)
  def encodeFloat(tag: Tag, a: Any) = ByteString(tag.id, 2) ++ encode(a)
}

class IntEnum extends Enumeration {
  def decode(tag: Tag, buf: ByteString): (Tag, Option[Value]) = {
    try {
      (tag, Some(super.apply(buf.toInt)))
    } catch {
      case e: NoSuchElementException => (tag, None)
    }
  }

  def decodeWithLen(tag: Tag, buf: ByteString) = (decode(tag, buf), 4)

  def getId(a: Any): Int = a match { case o: Option[Any] => o.get match { case n: this.Value => n.id } }

  def encode(a: Any) = encodeByteString(getId(a))
  def encodeFloat(tag: Tag, a: Any) = ByteString(tag.id, 4) ++ encode(a)
}

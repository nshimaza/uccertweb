package ctidriver

import akka.util.ByteString

/**
  * Enumeration with factory func which returns Value in wrapping Option.
  */

abstract class CtiEnum extends Enumeration {
  // Abstruct functions
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int)
  def encode(tag: Tag, a: Any): ByteString

  def decodeFloat(tag: Tag, body: ByteString) = {
    val (result, len) = decode(tag, body.drop(1))
    (result, len + 1)
  }

  def getId(a: Any): Int = a match { case o: Option[Any] => o.get match { case n: this.Value => n.id } }

  def encodeFloat(tag: Tag, a: Any) = {
    val buf = encode(tag, a)
    ByteString(tag.id, buf.size) ++ buf
  }
}

class ByteEnum extends CtiEnum {
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int) = {
    try {
      ((tag, Some(super.apply(body(0).toInt & 0xff))), 1)
    } catch {
      case e: NoSuchElementException => ((tag, None), 1)
    }
  }

  def encode(tag: Tag, a: Any) = ByteString(getId(a))
}

class ShortEnum extends CtiEnum {
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int) = {
    try {
      ((tag, Some(super.apply(body.toShort.toInt & 0xffff))), 2)
    } catch {
      case e: NoSuchElementException => ((tag, None), 2)
    }
  }

  def encode(tag: Tag, a: Any) = encodeByteString(getId(a).toShort)
}

class IntEnum extends CtiEnum {
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int) = {
    try {
      ((tag, Some(super.apply(body.toInt))), 4)
    } catch {
      case e: NoSuchElementException => ((tag, None), 4)
    }
  }

  def encode(tag: Tag, a: Any) = encodeByteString(getId(a))
}

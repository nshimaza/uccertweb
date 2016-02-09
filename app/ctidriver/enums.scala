package ctidriver

import akka.util.ByteString
import Tag.Tag

/**
  * Enumeration with factory func which returns Value in wrapping Option.
  */

abstract class CtiEnum extends Enumeration {
  val unrecognized_enum_id = "Unrecognized CTI Enumeration ID decoded.  Return None."

  // Abstruct functions
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int)
  def encode(tag: Tag, a: Any): ByteString

  def decodeFloat(tag: Tag, body: ByteString) = {
    val (result, len) = decode(tag, body.tail)
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
      ((tag, Some(super.apply(body.head.toInt & 0xff))), 1)
    } catch {
      case e: NoSuchElementException =>
        ctilog.warn(unrecognized_enum_id)
        ((tag, None), 1)
    }
  }

  def encode(tag: Tag, a: Any) = ByteString(getId(a))
}

class ShortEnum extends CtiEnum {
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int) = {
    try {
      ((tag, Some(super.apply(body.toShort.toInt & 0xffff))), 2)
    } catch {
      case e: NoSuchElementException =>
        ctilog.warn(unrecognized_enum_id)
        ((tag, None), 2)
    }
  }

  def encode(tag: Tag, a: Any) = encodeByteString(getId(a).toShort)
}

class IntEnum extends CtiEnum {
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int) = {
    try {
      ((tag, Some(super.apply(body.toInt))), 4)
    } catch {
      case e: NoSuchElementException =>
        ctilog.warn(unrecognized_enum_id)
        ((tag, None), 4)
    }
  }

  def encode(tag: Tag, a: Any) = encodeByteString(getId(a))
}

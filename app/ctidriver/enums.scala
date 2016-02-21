/**
  * Copyright (c) 2016 Naoto Shimazaki
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
  * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
  * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
  * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all copies or substantial
  * portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
  * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
  * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
  * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
  * DEALINGS IN THE SOFTWARE.
  */

package ctidriver

import akka.util.ByteString
import Tag.Tag

/**
  * Enumeration with factory func which returns Value in wrapping Option.
  */

abstract class CtiEnum extends Enumeration {
  // Abstruct functions
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int)
  def encode(tag: Tag, a: Any): ByteString

  def decodeFloat(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int) = {
    val (result, len) = decode(tag, body.tail)
    (result, len + 1)
  }

  def getId(a: Any): Int = a match { case o: Option[Any] => o.get match { case n: this.Value => n.id } }

  def encodeFloat(tag: Tag, a: Any): ByteString = {
    val buf = encode(tag, a)
    ByteString(tag.id, buf.size) ++ buf
  }
}

object CtiEnum {
  val UnrecognizedEnumId = "Unrecognized CTI Enumeration ID decoded.  Return None."
}

class ByteEnum extends CtiEnum {
  def decode(tag: Tag, body: ByteString): ((Tag, Option[Value]), Int) = {
    try {
      ((tag, Some(super.apply(body.head.toInt & 0xff))), 1)
    } catch {
      case e: NoSuchElementException =>
        ctilog.warn(CtiEnum.UnrecognizedEnumId)
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
        ctilog.warn(CtiEnum.UnrecognizedEnumId)
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
        ctilog.warn(CtiEnum.UnrecognizedEnumId)
        ((tag, None), 4)
    }
  }

  def encode(tag: Tag, a: Any) = encodeByteString(getId(a))
}

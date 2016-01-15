package ctidriver

import akka.util.ByteString
import scala.annotation.tailrec

/**
 * Message extractor functions
 */
object Decoder {
  def decode(buf: ByteString): Message = {
    val msgType = MessageType fromInt buf.toInt
    val body = buf.drop(4)
    val (fixed: Message, next_offset)
    = decodeFixedPart(List((Tag.MessageTypeTag, msgType)), MessageType.getFixedPartList(msgType), body)

/*
    println(fixed)
    print("next_offset:"); println(next_offset)
    print("body.size:"); println(body.size)
    print("pass to floating decoder:") ; println(body.drop(next_offset).to[List])
*/

    decodeFloatingPart(fixed, body.drop(next_offset)).reverse
  }

  @tailrec
  def decodeFixedPart(decoded: Message, seq: List[Tag], buf: ByteString, done: Int = 0): (Message, Int) = {
    if (seq.isEmpty)
      (decoded, done)
    else {
      val tag = seq.head
      val (result, next_offset) = Tag.decodeFixedField(tag, buf)

      decodeFixedPart(result +: decoded, seq.tail, buf.drop(next_offset), done + next_offset)
    }
  }

  @tailrec
  def decodeFloatingPart(decoded: Message, buf: ByteString): Message = {
    if (buf.size <= 0)
      decoded
    else {
      val tag = Tag(buf(0).toInt & 0xff)
      val len = buf(1).toInt & 0xff
      val body = buf.drop(2)
      val rest = body.drop(len)

      decodeFloatingPart(Tag.decodeFloatingField(tag, len, body) +: decoded, rest)
    }
  }
}

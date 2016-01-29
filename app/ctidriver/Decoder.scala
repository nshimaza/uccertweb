package ctidriver

import akka.util.ByteString
import scala.annotation.tailrec
import Tag.Tag

/**
 * Message extractor functions
 */
object Decoder {
  def decode(buf: ByteString): Message = {
    val (msgType, len)  = MessageType.decode(Tag.MessageTypeTag, buf)
    val body = buf.drop(len)
    val (fixed, next_offset) = decodeFixedPart(List(msgType), MessageType.getFixedPartList(msgType._2), body)

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
      val tag = Tag(buf.head.toInt & 0xff)
      val body = buf.tail
      val (result, next_offset) = Tag.decodeFloatingField(tag, body)

      decodeFloatingPart(result +: decoded, body.drop(next_offset))
    }
  }
}

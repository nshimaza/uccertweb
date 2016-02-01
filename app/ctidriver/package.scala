import akka.util.ByteString
import scala.annotation.tailrec
import scala.collection.immutable.BitSet
import scala.reflect.ClassTag

package object ctidriver {
  import Tag.Tag

  //
  // Type alias of container for extracted CTI Server Protocol message
  //
//  type Tag = Tag.Value
  type Message = List[(Tag, Any)]

  //
  // General constants for CTI Server Protocol messages
  //

  val DefaultPeripheralID: Int  = 5000
  val ProtocolVersion: Int      = 19
  val MessageHeaderSize: Int    = 8
  val MaxECCNameLen: Int        = 32
  val MaxECCValueLen: Int       = 210
  val MaxFloatStringLen: Int    = 255
  val MaxTagValue: Int          = 255
  val NoIdleTimeout: Int        = 0xffffffff
  val DefaultCtiServerPortA     = 42027
  val DefaultCtiServerPortB     = 43027

  type Section = Section.Value
  object Section extends Enumeration {
    val FixedPart, FloatingPart = Value
  }


  //
  // Invoke ID singleton
  //
  object InvokeIDGen {
    private var id: Int = 0

    def next = synchronized {
      id += 1
      id
    }
  }


  //
  // Helper functions for binary data processing
  //

  def ubyteToInt(b: Byte): Int = b.toInt & 0xff
  
  def intToByte(i: Int): Byte = (i & 0xff).toByte

  // Encode integer to big-endian byte string
  def encodeByteString(i: Int) =
    ByteString((i & 0xff000000) >> 24, (i & 0x00ff0000) >> 16, (i & 0x0000ff00) >>  8, (i & 0x000000ff) >>  0)

  // Encode short to big-endian byte string
  def encodeByteString(s: Short) = ByteString((s & 0xff00) >> 8, s & 0x00ff)

  def shortToByteArray(value: Short, isBigEndian: Boolean = true): Array[Byte] = {
    if (isBigEndian)
      Array(((value & 0xff00) >> 8).toByte, (value & 0x00ff).toByte)          
    else
      Array((value & 0x00ff).toByte, ((value & 0xff00) >> 8).toByte)
  }

  def intToByteArray(value: Int, isBigEndian: Boolean = true): Array[Byte] = {
    if (isBigEndian) {
      Array(
          ((value & 0xff000000) >> 24).toByte,
          ((value & 0x00ff0000) >> 16).toByte,
          ((value & 0x0000ff00) >>  8).toByte,
          ((value & 0x000000ff) >>  0).toByte)          
    } else {
      Array(
          ((value & 0x000000ff) >>  0).toByte,
          ((value & 0x0000ff00) >>  8).toByte,
          ((value & 0x00ff0000) >> 16).toByte,
          ((value & 0xff000000) >> 24).toByte)      
    }
  }

  implicit class CtiString(val s: String) extends AnyVal {
    def toIntOpt: Option[Int] = scala.util.Try(s.toInt).toOption
  }

  implicit class CtiMessage(val msg: Message) extends AnyVal {
    def encode: ByteString = encodeMsgField(ByteString.empty, msg)

    @tailrec
    private def encodeMsgField(encoded: ByteString, rest: Message): ByteString = {
      if (rest.isEmpty)
        encoded
      else {
        val (tag, field) = rest.head
        encodeMsgField(encoded ++ Tag.encodeField(tag, field), rest.tail)
      }
    }

    def findField(tag: Tag): Option[Any] = msg.find(_._1 == tag).flatMap(tpl => Some(tpl._2))
    def findT[T](tag: Tag)(implicit c: ClassTag[T]): Option[T] = findField(tag).collectFirst{ case v: T => v }
    def findStrInt(tag: Tag): Option[Int] = findT[String](tag).flatMap(_.toIntOpt)
    def findEnum[T](tag: Tag)(implicit c: ClassTag[T]): Option[T] =
      findField(tag).collectFirst{ case s: Some[Any] => s.get }.collectFirst{ case e: T => e }
  }

  implicit class CtiByteString(val buf: ByteString) extends AnyVal {
    // Decode big-endian integer from byte string
    def toInt: Int =
      ((0xff & buf(0)) << 24) | ((0xff & buf(1)) << 16) | ((0xff & buf(2)) << 8) | (0xff & buf(3))

    // Decode big-endian short from byte string
    def toShort: Short =
      (((0xff & buf(0)) << 8) | (0xff & buf(1))).toShort

    // Decode null terminated string from byte string
    def toString(len: Int): String =
      buf.take({ val l = buf.take(len).indexOf(0x00); if (l < 0) len else l }).utf8String

    // Decode CTI Named Variable
    def toNamedVar(len: Int): (String, String) = {
      val delim = { val l = buf.take(len).indexOf(0x00); if (l < 0) len else l }
      (buf.toString(delim), buf.drop(delim + 1).toString(len - (delim + 1)))
    }

    // Decode CTI Named Variable Array
    def toNamedArray(len: Int): (Int, String, String) = {
      val index = buf.head.toInt & 0xff
      val name = buf.tail
      val len2 = len - 1
      val delim = { val l = name.take(len2).indexOf(0x00); if (l < 0) len2 else l }
      (index, name.toString(delim), name.drop(delim + 1).toString(len2 - (delim + 1)))
    }

    def ++(i: Int): ByteString = buf ++ encodeByteString(i)

    def ++(s: Short): ByteString = buf ++ encodeByteString(s)

    def withlength: ByteString = encodeByteString(buf.size - 4) ++ buf

    def decode: Message = {
      val (msgType, len)  = MessageType.decode(Tag.MessageTypeTag, buf)
      val body = buf.drop(len)
      val (fixed, next_offset) = decodeFixedPart(List(msgType), MessageType.getFixedPartList(msgType._2), body)

      decodeFloatingPart(fixed, body.drop(next_offset)).reverse
    }

    @tailrec
    private def decodeFixedPart(decoded: Message, seq: List[Tag], rest: ByteString, done: Int = 0): (Message, Int) = {
      if (seq.isEmpty)
        (decoded, done)
      else {
        val tag = seq.head
        val (result, next_offset) = Tag.decodeFixedField(tag, rest)

        decodeFixedPart(result +: decoded, seq.tail, rest.drop(next_offset), done + next_offset)
      }
    }

    @tailrec
    private def decodeFloatingPart(decoded: Message, rest: ByteString): Message = {
      if (rest.size <= 0)
        decoded
      else {
        val tag = Tag(rest.head.toInt & 0xff)
        val body = rest.tail
        val (result, next_offset) = Tag.decodeFloatingField(tag, body)

        decodeFloatingPart(result +: decoded, body.drop(next_offset))
      }
    }
  }

  implicit class CtiBitSet(val m: BitSet) extends AnyVal {
    def toInt: Int = (m.toBitMask(0) & 0xffffffff).toInt
    def toShort: Short = (m.toBitMask(0) & 0xffff).toShort
  }
}
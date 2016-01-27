import akka.util.ByteString
import scala.collection.immutable.BitSet

package object ctidriver {

  //
  // Type alias of container for extracted CTI Server Protocol message
  //
  type Tag = Tag.Value
  type Message = List[(Tag, Any)]
  type CtiMessage = List[(Tag, Any)]
  type RevCtiMessage = List[(Tag, Any)]

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
  }

  implicit class CtiBitSet(val m: BitSet) extends AnyVal {
    def toInt: Int = (m.toBitMask(0) & 0xffffffff).toInt
    def toShort: Short = (m.toBitMask(0) & 0xffff).toShort
  }
}
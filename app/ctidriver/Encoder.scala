package ctidriver

import akka.util.ByteString
import scala.annotation.tailrec
import scala.collection.immutable.BitSet
import scala.collection.mutable.ArrayBuffer

import MessageType._
import Tag._

object Encoder {
  //
  // Message builder functions
  //

  def encode(msg: Message): ByteString = encodeMsgField(ByteString.empty, msg)

  @tailrec
  def encodeMsgField(encoded: ByteString, rest: Message): ByteString = {
    if (rest.isEmpty)
      encoded
    else {
      val (tag, field) = rest.head
      encodeMsgField(encoded ++ encodeField(tag, field), rest.drop(1))
    }
  }


//  def setMessageLength(buf: => ArrayBuffer[Byte]) = {
//    val len = intToByteArray(buf.size - 8)		// message length doesn't include message header
//    buf(0) = len(0)
//    buf(1) = len(1)
//    buf(2) = len(2)
//    buf(3) = len(3)
//  }

  //
  // Building a floating field
  //

  def buildFloat(tag: Tag.Value, s: String) = {
    val body = ByteString(s).take(MaxFloatStringLen - 1) ++ ByteString(0.toByte)
    ByteString(tag.id, body.size) ++ body
  }

  def buildFloat(tag: Tag.Value, i: Int) = ByteString(tag.id, 4) ++ encodeByteString(i)

  def buildFloat(tag: Tag.Value, s: Short) = ByteString(tag.id, 2) ++ encodeByteString(s)

  def buildFloat(tag: Tag.Value, b: Boolean) =
    ByteString(tag.id, 2) ++ encodeByteString((if (b) 1 else 0).toShort)

  def buildNamedVar(name: String, value: String) = {
    val body = ByteString(name).take(MaxECCNameLen - 1) ++ ByteString(0.toByte) ++
      ByteString(value).take(MaxECCValueLen - 1) ++ ByteString(0.toByte)
    ByteString(Tag.NAMED_VARIABLE.id, body.size) ++ body
  }

  def buildNamedVarList(varList: Iterable[(String, String)]) =
    varList.map { case (name, value) => buildNamedVar(name, value) }.reduce(_ ++ _)

  def buildNamedArray(name: String, index: Int, value: String) = {
    val body = ByteString(index.toByte) ++ ByteString(name).take(MaxECCNameLen - 1) ++ ByteString(0.toByte) ++
      ByteString(value).take(MaxECCValueLen - 1) ++ ByteString(0.toByte)
    ByteString(Tag.NAMED_ARRAY.id, body.size) ++ body
  }

  def buildNamedArrayList(arrayList: Iterable[(String, Int, String)]) =
    arrayList.map { case (name, index, value) => buildNamedArray(name, index, value) }.reduce(_ ++ _)

  //
  // Build final CTI request message in ByteString combining message type, invoke ID and body of the message
  //
  def buildCtiRequest(m: (MessageType, ByteString), invokeID: Int): ByteString =
    encodeByteString(m._2.size + 4) ++ m._1.id ++ invokeID ++ m._2

  //
  // Building OPEN_REQ message in ByteArray
  //
  // Fixed part length without Invoke ID: 40 bytes
  def buildOpenReq(idleTimeout: Int = NoIdleTimeout,
                   peripheralID: Int = DefaultPeripheralID,
                   serviceRequested: BitSet,
                   callMsgMask: BitSet,
                   agentStateMask: BitSet,
                   configMsgMask: Int,
                   clientID: String,
                   clientPassword: String): (MessageType, ByteString) = {
    (OPEN_REQ, encodeByteString(ProtocolVersion) ++ idleTimeout ++
      peripheralID ++ serviceRequested.toInt ++ callMsgMask.toInt ++
      agentStateMask.toInt ++ configMsgMask ++ 0 ++ 0 ++ 0 ++
      buildFloat(CLIENT_ID, clientID) ++ buildFloat(CLIENT_PASSWORD, clientPassword))
  }

  //
  // Building CLOSE_REQ message in ByteArray
  //
  // Fixed part length without Invoke ID: 4 bytes
  def buildCloseReq(Status: StatusCode.Value): (MessageType, ByteString) = {
    (CLOSE_REQ, encodeByteString(Status.id))
  }
}

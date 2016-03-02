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

import scala.annotation.tailrec

/**
  * Created by nshimaza on 2016/02/21.
  */
class Packetizer {
  var buf = ByteString.empty
  var state = State.WaitLength
  var offset = -4

  def apply(data: ByteString): Seq[ByteString] = {
    offset = offset + data.size
    buf = buf ++ data
    var result: Seq[ByteString] = Seq.empty

    while (offset >= 0) {
      state match {
        case State.WaitLength =>
          // at the state WAIT_LENGTH, the head of buf is aligned to message length field
          // now you have plenty bytes to decode message length
          val message_length = buf.toInt
          if (message_length < 0 || message_length > MaxMessageLen) {
            val msg = s"Message length $message_length out of defined range (0 - $MaxMessageLen) decoded." +
              "  Potential socket out of sync."
            ctilog.error(msg)
            throw new java.io.SyncFailedException(msg)
          }
          buf = buf.drop(4)
          // here you have buf which contains message aligned to Message Type field at the head
          // let's set next state for waiting body
          state = State.WaitBody

          // the message length doesn't include Message Type filed so you need to wait 4 more bytes
          offset = buf.size - (message_length + 4)

        case State.WaitBody =>
          // at the state WAIT_BODY, the head of buf is aligned to message type field
          // now you have at least one entire message in buf

          // take single message to packet
          val len = buf.size - offset
          val packet = buf.take(len)

          buf = buf.drop(len)
          // here you have buf which is aligned to next message length
          // let's set next state for waiting message length
          // we need 4 bytes to continue
          state = State.WaitLength
          offset = buf.size - 4

          result = packet +: result
      }
    }
    result.reverse
  }

  object State extends Enumeration {
    val WaitLength, WaitBody = Value
  }
}

object Packetizer {
  def apply() = {
    new Packetizer
  }
}

/**
  * Pure functional packetizer.  It is purely immutable.
  *
  * @param buf temporary buffer to hold unprocessed bytes
  * @param state holds current state
  * @param offset holds internal offet tward next message boundary
  * @param packets holds sequence of packetized input bytes
  */
case class Packetizer2 private (buf: ByteString, state: Packetizer2.State, offset: Int, packets: Seq[ByteString]) {
  def apply(data: ByteString): Packetizer2 = {
    loop(buf ++ data, state, offset + data.size, Seq.empty)
  }

  @tailrec
  private def loop(buf: ByteString,
                   state: Packetizer2.State,
                   offset: Int,
                   result: Seq[ByteString]): Packetizer2 = {
    if (offset < 0) {
      Packetizer2(buf, state, offset, result.reverse)
    } else {
      state match {
        case Packetizer2.OutOfSync =>
          Packetizer2(buf, state, offset, result)

        case Packetizer2.WaitLength =>
          // at the state WaitLength, the head of buf is aligned to message length field
          // now you have plenty bytes to decode message length
          val message_length = buf.toInt
          if (message_length < 0 || message_length > MaxMessageLen) {
            Packetizer2(buf, Packetizer2.OutOfSync, offset, result.reverse)
          } else {
            // here you have buf which contains message aligned to Message Type field at the head
            // we can transit to the next state for waiting body
            // the message length doesn't include Message Type filed so you need to wait 4 more bytes
            loop(buf.drop(4), Packetizer2.WaitBody, buf.size - 4 - (message_length + 4), result)
          }

        case Packetizer2.WaitBody =>
          // at the state WaitBody, the head of buf is aligned to message type field
          // now you have at least one entire message in buf

          // take single message to packet
          val len = buf.size - offset
          val packet = buf.take(len)

          // next message length begins from "len" bytes from the head of buf
          // we can transit to the next state for waiting message length
          // we have "offset" bytes remaining and we need 4 bytes to continue
          loop(buf.drop(len), Packetizer2.WaitLength, offset - 4, packet +: result)
      }
    }
  }
}

object Packetizer2 {
  sealed abstract class State
  case object WaitLength extends State
  case object WaitBody extends State
  case object OutOfSync extends State

  def apply(data: ByteString = ByteString.empty): Packetizer2 = {
    new Packetizer2(ByteString.empty, Packetizer2.WaitLength, -4, Seq()).apply(data)
  }
}


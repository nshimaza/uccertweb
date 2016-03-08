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

/**
  * Pure functional packetizer.  It is purely immutable.
  *
  * @param buf temporary buffer to hold unprocessed bytes
  * @param state holds current state
  * @param offset holds internal offet tward next message boundary
  * @param packets holds sequence of packetized input bytes
  */
case class Packetizer private(private[ctidriver] val buf: ByteString,
                              private[ctidriver] val state: Packetizer.State,
                              private[ctidriver] val offset: Int,
                              packets: Seq[ByteString],
                              isOutOfSync: Boolean) {
  def apply(data: ByteString): Packetizer = {
    loop(buf ++ data, state, offset + data.size, Seq.empty, isOutOfSync)
  }

  @tailrec
  private def loop(buf: ByteString,
                   state: Packetizer.State,
                   offset: Int,
                   result: Seq[ByteString],
                   isOutOfSync: Boolean): Packetizer = {
    if (isOutOfSync) {
      this.copy()
    } else if (offset < 0) {
      Packetizer(buf, state, offset, result.reverse, isOutOfSync = false)
    } else {
      state match {
        case Packetizer.WaitLength =>
          // at the state WaitLength, the head of buf is aligned to message length field
          // now you have plenty bytes to decode message length
          val message_length = buf.toInt
          if (message_length < 0 || message_length > MaxMessageLen) {
            Packetizer(buf, Packetizer.WaitBody, offset, result.reverse, isOutOfSync = true)
          } else {
            // here you have buf which contains message aligned to Message Type field at the head
            // we can transit to the next state for waiting body
            // the message length doesn't include Message Type filed so you need to wait 4 more bytes
            loop(buf.drop(4), Packetizer.WaitBody, buf.size - 4 - (message_length + 4), result, isOutOfSync = false)
          }

        case Packetizer.WaitBody =>
          // at the state WaitBody, the head of buf is aligned to message type field
          // now you have at least one entire message in buf

          // take single message to packet
          val len = buf.size - offset
          val packet = buf.take(len)

          // next message length begins from "len" bytes from the head of buf
          // we can transit to the next state for waiting message length
          // we have "offset" bytes remaining and we need 4 bytes to continue
          loop(buf.drop(len), Packetizer.WaitLength, offset - 4, packet +: result, isOutOfSync = false)
      }
    }
  }
}

object Packetizer {
  sealed abstract class State
  case object WaitLength extends State
  case object WaitBody extends State

  def apply(data: ByteString = ByteString.empty): Packetizer = {
    new Packetizer(ByteString.empty, Packetizer.WaitLength, -4, Seq(), false).apply(data)
  }
}


/*
class Packetizer1 {
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

object Packetizer1 {
  def apply() = {
    new Packetizer1
  }
}
*/

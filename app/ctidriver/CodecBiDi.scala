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

import java.nio.ByteOrder

import akka.stream.scaladsl.{BidiFlow, Flow}
import akka.util.ByteString
import ctidriver.MessageType.MessageType

/** CodecBiDi is packaged CTI encoder and decoder into single Akka Stream bi-directional flow shape.
  *
  * CodecBiDi receives raw binary CTI messages from inbound upstream, filters, decodes, and emits decoded Messages
  * to downstream.  CodecBiDi also receives complete Messages from outbound upstream, encodes, frames and emits
  * encoded raw bytes to outbound downstream.
  *
  * Usage:
  * Construct a TCP outgoing connection flow shape which connects to CTI server.
  * Connect I1 inlet to your producer Akka stream stage.
  * Connect O1 outlet to the inlet of the TCP flow shape.
  * Connect I2 inlet to the outlet of the TCP flow shape.
  * Connect O2 outlet to your consumer Akka stream stage.
  *
  * Example:
  * val stack = CodecBiDi(messageTypes).join(Tcp().outgoingConnection(hostname, port))
  *
  * CodecBiDi receives raw byte stream from CTI server in unframed ByteString form.  It assumes upstream is
  * Akka Stream TCP flow shape.  Incoming ByteString do not have to be aligned to frame boundary.  CodecBidi
  * aligns frame boundary by itself.
  * When CodecBidi found invalid value in frame length field, it consider it has lost frame synchronization
  * with the CTI server.  In such case, it fails and stops processing.
  * CodecBidi filters incoming message based on the set of message type passed via factory method parameter.
  * CodecBidi only passes message types contained in the set to downstream.  Other message types are dropped.
  *
  */
object CodecBiDi {

  def apply(messageTypes: Set[MessageType]) = {

    implicit val order = ByteOrder.BIG_ENDIAN

    def isInterestedMessage(bytes: ByteString) = {
      val ((_, mayBeMessageType), _) = MessageType.decode(Tag.MessageTypeTag, bytes)
      mayBeMessageType exists messageTypes.contains
    }

    val outbound = Flow[Message]
      .map(_.encode)
      .map(bytes => ByteString.newBuilder.putInt(bytes.size - 4).append(bytes).result())

    val inbound = Flow
      .fromGraph(new CtiFramingStage)
      .filter(isInterestedMessage).map(_.decode)

    BidiFlow.fromFlows(outbound, inbound)
  }
}

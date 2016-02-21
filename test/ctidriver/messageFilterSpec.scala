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
import ctidriver.MessageType._
import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.{ WordSpec, MustMatchers }
import org.scalatest.junit.JUnitRunner

/**
  * Testing MessageFilter class
  */
@RunWith(classOf[JUnitRunner])
class messageFilterSpec extends WordSpec with MustMatchers {
  "MessageFilter" must {
    "returns handlers which wanted to process the message" in {
      val handler: Message => Unit = (_) => Unit
      val entry = MessageFilterEntry(Set(AGENT_STATE_EVENT), handler)
      val filter = MessageFilter(Traversable(entry))
      val msg = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304)).encode

      filter(msg) must contain theSameElementsAs Traversable(handler)
    }

    "work with multiple type of messages" in {
      val handler: Message => Unit = (_) => Unit
      val entry = MessageFilterEntry(Set(BEGIN_CALL_EVENT, END_CALL_EVENT), handler)
      val filter = MessageFilter(Traversable(entry))
      val msg1 = List((MessageTypeTag, Some(END_CALL_EVENT)), (MonitorID, 0x01020304)).encode
      val msg2 = List((MessageTypeTag, Some(BEGIN_CALL_EVENT)), (MonitorID, 0x01020304)).encode

      filter(msg1) must contain theSameElementsAs Traversable(handler)
      filter(msg2) must contain theSameElementsAs Traversable(handler)
    }

    "identify all defined type of message" in {
      val handler: Message => Unit = (_) => Unit
      val filter = MessageFilter(Traversable(MessageFilterEntry(MessageType.values, handler)))
      for (mtyp <- MessageType.values) {
        val msg = encodeByteString(mtyp.id) ++ ByteString(4,3,2,1, 9,8,7,6)
        filter(msg) must contain theSameElementsAs Traversable(handler)
      }
    }

    "return no handler for entry with empty set of message type" in {
      val handler: Message => Unit = (_) => Unit
      val filter = MessageFilter(Traversable(MessageFilterEntry(Set(), handler)))
      for (mtyp <- MessageType.values) {
        val msg = encodeByteString(mtyp.id) ++ ByteString(4,3,2,1, 9,8,7,6)
        filter(msg) mustBe empty
      }
    }

    "return no handler for empty filter entry list" in {
      val filter = MessageFilter(Traversable())
      for (mtyp <- MessageType.values) {
        val msg = encodeByteString(mtyp.id) ++ ByteString(4,3,2,1, 9,8,7,6)
        filter(msg) mustBe empty
      }
    }

    "return empty list of handler if no handler wanted to process the message" in {
      val handler1: Message => Unit = (_) => Unit
      val handler2: Message => Unit = (_) => Unit
      val entry1 = MessageFilterEntry(Set(AGENT_STATE_EVENT), handler1)
      val entry2 = MessageFilterEntry(Set(BEGIN_CALL_EVENT, END_CALL_EVENT), handler2)
      val filter = MessageFilter(Traversable(entry1, entry2))
      val msg = List((MessageTypeTag, Some(CLOSE_CONF)), (InvokeID, 0x03040506)).encode

      filter(msg) mustBe empty
    }

    "return only desiring handler for the message" in {
      val handler1: Message => Unit = (_) => Unit
      val handler2: Message => Unit = (_) => Unit
      val entry1 = MessageFilterEntry(Set(FAILURE_EVENT), handler1)
      val entry2 = MessageFilterEntry(Set(FAILURE_EVENT, FAILURE_CONF), handler2)
      val filter = MessageFilter(Traversable(entry1, entry2))
      val msg = List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x03040506)).encode

      filter(msg) must contain theSameElementsAs Traversable(handler2)
    }

    "return all desiring handlers for the message" in {
      val handler1: Message => Unit = (_) => Unit
      val handler2: Message => Unit = (_) => Unit
      val entry1 = MessageFilterEntry(Set(FAILURE_EVENT), handler1)
      val entry2 = MessageFilterEntry(Set(FAILURE_EVENT, FAILURE_CONF), handler2)
      val filter = MessageFilter(Traversable(entry1, entry2))
      val msg = List((MessageTypeTag, Some(FAILURE_EVENT)), (InvokeID, 0x03040506)).encode

      filter(msg) must contain theSameElementsAs Traversable(handler2, handler1)
    }

    "return no handler for undefined message type" in {
      val handler: Message => Unit = (_) => Unit
      val filter = MessageFilter(Traversable(MessageFilterEntry(MessageType.values, handler)))
      val msg = ByteString(1,1,1,1, 1,2,3,4, 5,6,7,8)

      filter(msg) mustBe empty
    }
  }
}

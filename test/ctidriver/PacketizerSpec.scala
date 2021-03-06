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
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ MustMatchers, WordSpec }

/**
  * Testing Packetizer class
  */
@RunWith(classOf[JUnitRunner])
class PacketizerSpec extends WordSpec with MustMatchers {
/*
  "Packetizer1" must {
    "start with state WaitLength" in {
      val packetizer = Packetizer1()

      packetizer.state mustBe packetizer.State.WaitLength
    }

    "start with offset -4" in {
      val packetizer = Packetizer1()

      packetizer.offset mustBe -4
    }

    "return empty Seq[ByteString] on receive until entire packet becomes available" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(1, 2, 3)) mustBe Seq[ByteString]()
    }

    "stay on state WaitLength until 4 bytes received" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(1, 2, 3)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitLength
    }

    "transit to state WaitBody when 4 bytes received" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(0, 0, 1, 2)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitBody
    }

    "transit to state WaitBody when 4 bytes received separately" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(0)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitLength
      packetizer(ByteString(0)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitLength
      packetizer(ByteString(3)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitLength
      packetizer(ByteString(4)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitBody
    }

    "transit to state WaitBody when more than 4 bytes received" in {
      val packetizer = Packetizer1()

      packetizer apply ByteString(0, 0, 3, 4, 5) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitBody
    }

    "keep remaining data received when successfully decode message length" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(0, 0, 3, 4, 5)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitBody
      packetizer.buf.size mustBe 1
      packetizer.buf.head mustBe 5.toByte
    }

    "decode and keep message length when transit to WaitBody" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(0,0,0x10,0xe0, 5,6,7,8)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitBody
      packetizer.offset mustBe (4 - (0x000010e0 + 4))
    }

    "throw java.io.SyncFailedException when negative message length is decoded" in {
      val packetizer = Packetizer1()

      intercept[java.io.SyncFailedException] {
        val packets = packetizer(encodeByteString(-1: Int))
      }
    }

    "throw java.io.SyncFailedException when decoded message length is greater than MaxMessageLen" in {
      val packetizer = Packetizer1()

      intercept[java.io.SyncFailedException] {
        val packets = packetizer(encodeByteString(MaxMessageLen + 1: Int))
      }
    }

    "decode mesasge length when it is just equal to MaxMessageLen" in {
      val packetizer = Packetizer1()

      packetizer(encodeByteString(MaxMessageLen)) mustBe Seq()
      packetizer.state mustBe packetizer.State.WaitBody
      packetizer.offset mustBe (-(MaxMessageLen + 4))
    }

    "transit back to state WaitLength when desired bytes are received" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8)) mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      packetizer.state mustBe packetizer.State.WaitLength
      packetizer.offset mustBe (-4)
    }

    "return entire single message to listener when it reached next WaitLength state" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8)) mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      packetizer.state mustBe packetizer.State.WaitLength
      packetizer.offset mustBe (-4)
    }

    "keep remaining bytes after sending an entire message" in {
      val packetizer = Packetizer1()

      packetizer(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 9,10)) mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      packetizer.state mustBe packetizer.State.WaitLength
      packetizer.offset mustBe (-2)
      packetizer.buf mustBe ByteString(9, 10)
    }

    "can separate multiple messages received at a time" in {
      val packetizer = Packetizer1()

      val packets = packetizer(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 0,0,0,3, 2,3,4,5, 6,7,8))
      packetizer.state mustBe packetizer.State.WaitLength
      packetizer.offset mustBe (-4)
      packets mustBe Seq(ByteString(1,2,3,4, 5,6,7,8), ByteString(2,3,4,5, 6,7,8))
    }
  }
*/

  "Packetizer" must {
    "start with state WaitLength" in {
      val packetizer = Packetizer()

      packetizer.state mustBe Packetizer.WaitLength
    }

    "start with offset -4" in {
      val packetizer = Packetizer()

      packetizer.offset mustBe -4
    }

    "return empty Seq[ByteString] on receive until entire packet becomes available" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(1, 2, 3))
      new_packetizer.packets mustBe Seq[ByteString]()
    }

    "stay on state WaitLength until 4 bytes received" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(1, 2, 3))
      new_packetizer.packets mustBe Seq()
      new_packetizer.state mustBe Packetizer.WaitLength
    }

    "transit to state WaitBody when 4 bytes received" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(0, 0, 1, 2))
      new_packetizer.packets mustBe Seq()
      new_packetizer.state mustBe Packetizer.WaitBody
    }

    "transit to state WaitBody when 4 bytes received separately" in {
      val packetizer = Packetizer()

      val packetizer1 = packetizer(ByteString(0))
      packetizer1.packets mustBe Seq()
      packetizer1.state mustBe Packetizer.WaitLength
      val packetizer2 = packetizer1(ByteString(0))
      packetizer2.packets mustBe Seq()
      packetizer2.state mustBe Packetizer.WaitLength
      val packetizer3 = packetizer2(ByteString(3))
      packetizer3.packets mustBe Seq()
      packetizer3.state mustBe Packetizer.WaitLength
      val packetizer4 = packetizer3(ByteString(4))
      packetizer4.packets mustBe Seq()
      packetizer4.state mustBe Packetizer.WaitBody
    }

    "transit to state WaitBody when more than 4 bytes received" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(0, 0, 3, 4, 5))
      new_packetizer.packets mustBe Seq()
      new_packetizer.state mustBe Packetizer.WaitBody
    }

    "keep remaining data received when successfully decode message length" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(0, 0, 3, 4, 5))
      new_packetizer.packets mustBe Seq()
      new_packetizer.state mustBe Packetizer.WaitBody
      new_packetizer.buf.size mustBe 1
      new_packetizer.buf.head mustBe 5.toByte
    }

    "decode and keep message length when transit to WaitBody" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(0,0,0x10,0xe0, 5,6,7,8))
      new_packetizer.packets mustBe Seq()
      new_packetizer.state mustBe Packetizer.WaitBody
      new_packetizer.offset mustBe (4 - (0x000010e0 + 4))
    }

    "transit to OutOfSync state when negative message length is decoded" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(encodeByteString(-1: Int))

      new_packetizer.isOutOfSync mustBe true
    }

    "transit to OutOfSync state when decoded message length is greater than MaxMessageLen" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(encodeByteString(MaxMessageLen + 1: Int))

      new_packetizer.isOutOfSync mustBe true
    }

    "decode mesasge length when it is just equal to MaxMessageLen" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(encodeByteString(MaxMessageLen))
      new_packetizer.packets mustBe Seq()
      new_packetizer.state mustBe Packetizer.WaitBody
      new_packetizer.offset mustBe (-(MaxMessageLen + 4))
    }

    "transit back to state WaitLength when desired bytes are received" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8))
      new_packetizer.packets mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      new_packetizer.state mustBe Packetizer.WaitLength
      new_packetizer.offset mustBe (-4)
    }

    "return entire single message to listener when it reached next WaitLength state" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8))
      new_packetizer.packets mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      new_packetizer.state mustBe Packetizer.WaitLength
      new_packetizer.offset mustBe (-4)
    }

    "keep remaining bytes after sending an entire message" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 9,10))
      new_packetizer.packets mustBe Seq(ByteString(1,2,3,4, 5,6,7,8))
      new_packetizer.state mustBe Packetizer.WaitLength
      new_packetizer.offset mustBe (-2)
      new_packetizer.buf mustBe ByteString(9, 10)
    }

    "can separate multiple messages received at a time" in {
      val packetizer = Packetizer()

      val new_packetizer = packetizer(ByteString(0,0,0,4, 1,2,3,4, 5,6,7,8, 0,0,0,3, 2,3,4,5, 6,7,8))
      new_packetizer.state mustBe Packetizer.WaitLength
      new_packetizer.offset mustBe (-4)
      new_packetizer.packets mustBe Seq(ByteString(1,2,3,4, 5,6,7,8), ByteString(2,3,4,5, 6,7,8))
    }

  }
}

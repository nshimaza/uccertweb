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
import org.scalatest.{ WordSpecLike, MustMatchers }
import org.scalatest.junit.JUnitRunner

/**
  * Test CtiEnum and its child classes.
  */
@RunWith(classOf[JUnitRunner])
class CtiEnumSpec extends WordSpecLike with MustMatchers {
  "AgentAvailabilityStatus" must {
    "decode 0x00000000 as NOT_AVAILABLE with length 4" in {
      AgentAvailabilityStatus.decode(Tag.AgentAvailabilityStatusTag, ByteString(0,0,0,0)) mustBe
        ((Tag.AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.NOT_AVAILABLE)), 4)
    }

    "decode 0x00000002 as APPLICATION_AVAILABLE with length 4" in {
      AgentAvailabilityStatus.decode(Tag.AgentAvailabilityStatusTag, ByteString(0,0,0,2)) mustBe
        ((Tag.AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.APPLICATION_AVAILABLE)), 4)
    }

    "decode 0x00000003 as None with length 4" in {
      AgentAvailabilityStatus.decode(Tag.AgentAvailabilityStatusTag, ByteString(0,0,0,3)) mustBe
        ((Tag.AgentAvailabilityStatusTag, None), 4)
    }
  }

  "AgentGreetingAction" must {
    "decode 0x0000 as StopGreeting with length 2" in {
      AgentGreetingAction.decode(Tag.AgentActionTag, ByteString(0,0)) mustBe
        ((Tag.AgentActionTag, Some(AgentGreetingAction.StopGreeting)), 2)
    }

    "decode 0x0002 as Enable with length 2" in {
      AgentGreetingAction.decode(Tag.AgentActionTag, ByteString(0,2)) mustBe
        ((Tag.AgentActionTag, Some(AgentGreetingAction.Enable)), 2)
    }

    "decode 0x0003 as None with length 2" in {
      AgentGreetingAction.decode(Tag.AgentActionTag, ByteString(0,3)) mustBe ((Tag.AgentActionTag, None), 2)
    }
  }

  "ForcedFlag" must {
    "decode 0x00 as StopGreeting with length 2" in {
      ForcedFlag.decode(Tag.ForcedFlagTag, ByteString(0)) mustBe ((Tag.ForcedFlagTag, Some(ForcedFlag.FALSE)), 1)
    }

    "decode 0x02 as Enable with length 2" in {
      ForcedFlag.decode(Tag.ForcedFlagTag, ByteString(2)) mustBe
        ((Tag.ForcedFlagTag, Some(ForcedFlag.AgentAuthenticationOnly)), 1)
    }

    "decode 0x03 as None with length 2" in {
      ForcedFlag.decode(Tag.ForcedFlagTag, ByteString(3)) mustBe ((Tag.ForcedFlagTag, None), 1)
    }
  }
}

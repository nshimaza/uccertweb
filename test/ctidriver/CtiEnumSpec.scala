package ctidriver

import akka.util.ByteString
import org.scalatest.{ WordSpecLike, MustMatchers }

/**
  * Test CtiEnum and its child classes.
  */
class CtiEnumSpec extends WordSpecLike with MustMatchers {
  "AgentAvailabilityStatus" must {
    "decode 0x00000000 as NOT_AVAILABLE with length 4" in {
      AgentAvailabilityStatus.decode(Tag.AgentAvailabilityStatusTag, ByteString(0,0,0,0)) must
        be((Tag.AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.NOT_AVAILABLE)), 4)
    }

    "decode 0x00000002 as APPLICATION_AVAILABLE with length 4" in {
      AgentAvailabilityStatus.decode(Tag.AgentAvailabilityStatusTag, ByteString(0,0,0,2)) must
        be((Tag.AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.APPLICATION_AVAILABLE)), 4)
    }

    "decode 0x00000003 as None with length 4" in {
      AgentAvailabilityStatus.decode(Tag.AgentAvailabilityStatusTag, ByteString(0,0,0,3)) must
        be((Tag.AgentAvailabilityStatusTag, None), 4)
    }
  }

  "AgentGreetingAction" must {
    "decode 0x0000 as StopGreeting with length 2" in {
      AgentGreetingAction.decode(Tag.AgentActionTag, ByteString(0,0)) must
        be((Tag.AgentActionTag, Some(AgentGreetingAction.StopGreeting)), 2)
    }

    "decode 0x0002 as Enable with length 2" in {
      AgentGreetingAction.decode(Tag.AgentActionTag, ByteString(0,2)) must
        be((Tag.AgentActionTag, Some(AgentGreetingAction.Enable)), 2)
    }

    "decode 0x0003 as None with length 2" in {
      AgentGreetingAction.decode(Tag.AgentActionTag, ByteString(0,3)) must be((Tag.AgentActionTag, None), 2)
    }
  }

  "ForcedFlag" must {
    "decode 0x00 as StopGreeting with length 2" in {
      ForcedFlag.decode(Tag.ForcedFlagTag, ByteString(0)) must
        be((Tag.ForcedFlagTag, Some(ForcedFlag.FALSE)), 1)
    }

    "decode 0x02 as Enable with length 2" in {
      ForcedFlag.decode(Tag.ForcedFlagTag, ByteString(2)) must
        be((Tag.ForcedFlagTag, Some(ForcedFlag.AgentAuthenticationOnly)), 1)
    }

    "decode 0x03 as None with length 2" in {
      ForcedFlag.decode(Tag.ForcedFlagTag, ByteString(3)) must be((Tag.ForcedFlagTag, None), 1)
    }
  }
}

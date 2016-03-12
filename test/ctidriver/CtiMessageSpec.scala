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
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ WordSpecLike, MustMatchers }


/**
  * Created by nshimaza on 2016/02/01.
  */
@RunWith(classOf[JUnitRunner])
class CtiMessageSpec extends WordSpecLike with MustMatchers {
  "CtiMessage" must {
    "have function findField" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))
      msg.findField(MessageTypeTag)
    }

    "return None on findField when given tag is not found" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)),
        (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)))
      msg.findField(InvokeID) must be(None)
    }

    "return Some(value) on findField when given tag is found" in {
      val msg: Message = List((MessageTypeTag, Some(BEGIN_CALL_EVENT)), (MonitorID, 0x01020304),
        (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
        (NumCTIClients, 0x0304:Short), (NumNamedVariables, 0x0405:Short), (NumNamedArrays, 0x0506:Short),
        (CallTypeTag, Some(CallType.PREROUTE_ACD_IN)),
        (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
        (CalledPartyDisposition, Some(DispositionCodeValue.Disconnect_drop_handled_primary_route)),
        (CONNECTION_DEVID, "ConnectionDeviceID"),
        (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)),
        (DNIS, "0398765432"), (DIALED_NUMBER, "0120123123"), (CED, "321"), (ROUTER_CALL_KEY_DAY, 0x06070809),
        (ROUTER_CALL_KEY_CALLID, 0x0708090a), (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x08090a0b),
        (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
        (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
        (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"), (CALL_WRAPUP_DATA, "Wrapup"),
        (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal")),
        (CTI_CLIENT_SIGNATURE, "CtiClientSignature"), (CTI_CLIENT_TIMESTAMP, 0x090a0b0c),
        (CALL_REFERENCE_ID, ByteString(9,8,7,6,5,4,3,2,1,0)))
      msg.findField(ANI) must be(Some("09012345678"))
    }

    "return None on findT when given tag is not found" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)), (ConnectionCallID, 0x06070809))
      msg.findT[Int](PeripheralID) must be(None)
    }

    "return None on findT when given type and actual type is different" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)), (ConnectionCallID, 0x06070809))
      msg.findT[Short](ConnectionCallID) must be(None)
    }

    "return Some(value) on findT when given tag and type are matched" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)), (ConnectionCallID, 0x06070809))
      msg.findT[Int](ConnectionCallID) must be(Some(0x06070809))
    }

    "return Some(Short) on findT[Short] when given tag and type are matched" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)), (NumCTIClients, 0x0304:Short))
      msg.findT[Short](NumCTIClients) must be(Some(0x0304:Short))
    }

    "return None on findStrInt when given tag is not found" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)), (ANI, "09012345678"))
      msg.findStrInt(DNIS) must be(None)
    }

    "return None on findStrInt when given tag is found but content is not String" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)))
      msg.findStrInt(UUI) must be(None)
    }

    "return None on findStrInt when given tag is found but format error in the string" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)), (CONNECTION_DEVID, "ConnectionDeviceID"))
      msg.findStrInt(CONNECTION_DEVID) must be(None)
    }

    "return Some(Int) on findStrInt when given tag is found and succesfully parsed as integer" in {
      val msg: Message = List((MessageTypeTag, Some(FAILURE_EVENT)), (AGENT_EXTENSION, "3001"))
      msg.findStrInt(AGENT_EXTENSION) must be(Some(3001))
    }


  }
}

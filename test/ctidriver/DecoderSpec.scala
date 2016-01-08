import akka.util.ByteString
import ctidriver._
import ctidriver.MessageType._
import ctidriver.Tag._
import org.scalatest.FunSuite
import scala.collection.immutable.BitSet

class DecoderSpec extends FunSuite {

  test("decoding AGENT_GREETING_CONTROL_CONF") {
    val src = ByteString(0,0,0,250, 1,2,3,4)
    val msg: Message = List((MessageTypeTag, AGENT_GREETING_CONTROL_CONF), (InvokeID, 0x01020304))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding ALTERNATE_CALL_CONF") {
    val src = ByteString(0,0,0,41, 1,2,3,5)
    val msg: Message = List((MessageTypeTag, ALTERNATE_CALL_CONF), (InvokeID, 0x01020305))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding ANSWER_CALL_CONF") {
    val src = ByteString(0,0,0,43, 1,2,4,5)
    val msg: Message = List((MessageTypeTag, ANSWER_CALL_CONF), (InvokeID, 0x01020405))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding BAD_CALL_CONF") {
    val src = ByteString(0,0,0,140, 1,3,4,5)
    val msg: Message = List((MessageTypeTag, BAD_CALL_CONF), (InvokeID, 0x01030405))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CHANGE_MONITOR_MASK_CONF") {
    val src = ByteString(0,0,0,98, 2,3,4,5)
    val msg: Message = List((MessageTypeTag, CHANGE_MONITOR_MASK_CONF), (InvokeID, 0x02030405))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CLEAR_CALL_CONF") {
    val src = ByteString(0,0,0,45, 3,3,4,5)
    val msg: Message = List((MessageTypeTag, CLEAR_CALL_CONF), (InvokeID, 0x03030405))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CLEAR_CONNECTION_CONF") {
    val src = ByteString(0,0,0,47, 3,4,4,5)
    val msg: Message = List((MessageTypeTag, CLEAR_CONNECTION_CONF), (InvokeID, 0x03040405))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CLIENT_EVENT_REPORT_CONF") {
    val src = ByteString(0,0,0,33, 3,4,5,5)
    val msg: Message = List((MessageTypeTag, CLIENT_EVENT_REPORT_CONF), (InvokeID, 0x03040505))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CLOSE_CONF") {
    val src = ByteString(0,0,0,8, 3,4,5,6)
    val msg: Message = List((MessageTypeTag, CLOSE_CONF), (InvokeID, 0x03040506))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding DEFLECT_CALL_CONF") {
    val src = ByteString(0,0,0,53, 3,4,5,7)
    val msg: Message = List((MessageTypeTag, DEFLECT_CALL_CONF), (InvokeID, 0x03040507))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding HEARTBEAT_CONF") {
    val src = ByteString(0,0,0,6, 3,4,6,7)
    val msg: Message = List((MessageTypeTag, HEARTBEAT_CONF), (InvokeID, 0x03040607))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding HOLD_CALL_CONF") {
    val src = ByteString(0,0,0,55, 3,5,6,7)
    val msg: Message = List((MessageTypeTag, HOLD_CALL_CONF), (InvokeID, 0x03050607))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding MONITOR_AGENT_TEAM_STOP_CONF") {
    val src = ByteString(0,0,0,138, 4,5,6,7)
    val msg: Message = List((MessageTypeTag, MONITOR_AGENT_TEAM_STOP_CONF), (InvokeID, 0x04050607))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding MONITOR_STOP_CONF") {
    val src = ByteString(0,0,0,96, 5,5,6,7)
    val msg: Message = List((MessageTypeTag, MONITOR_STOP_CONF), (InvokeID, 0x05050607))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding RECONNECT_CALL_CONF") {
    val src = ByteString(0,0,0,61, 5,6,6,7)
    val msg: Message = List((MessageTypeTag, RECONNECT_CALL_CONF), (InvokeID, 0x05060607))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding REGISTER_VARIABLES_CONF") {
    val src = ByteString(0,0,0,111, 5,6,7,7)
    val msg: Message = List((MessageTypeTag, REGISTER_VARIABLES_CONF), (InvokeID, 0x05060707))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding RELEASE_CALL_CONF") {
    val src = ByteString(0,0,0,29, 5,6,7,8)
    val msg: Message = List((MessageTypeTag, RELEASE_CALL_CONF), (InvokeID, 0x05060708))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding RETRIEVE_CALL_CONF") {
    val src = ByteString(0,0,0,63, 5,6,7,9)
    val msg: Message = List((MessageTypeTag, RETRIEVE_CALL_CONF), (InvokeID, 0x05060709))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SEND_DTMF_SIGNAL_CONF") {
    val src = ByteString(0,0,0,92, 5,6,8,9)
    val msg: Message = List((MessageTypeTag, SEND_DTMF_SIGNAL_CONF), (InvokeID, 0x05060809))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SESSION_MONITOR_STOP_CONF") {
    val src = ByteString(0,0,0,104, 5,7,8,9)
    val msg: Message = List((MessageTypeTag, SESSION_MONITOR_STOP_CONF), (InvokeID, 0x05070809))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SET_AGENT_STATE_CONF") {
    val src = ByteString(0,0,0,39, 6,7,8,9)
    val msg: Message = List((MessageTypeTag, SET_AGENT_STATE_CONF), (InvokeID, 0x06070809))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SET_APP_DATA_CONF") {
    val src = ByteString(0,0,0,130, 7,7,8,9)
    val msg: Message = List((MessageTypeTag, SET_APP_DATA_CONF), (InvokeID, 0x07070809))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SET_CALL_DATA_CONF") {
    val src = ByteString(0,0,0,27, 7,8,8,9)
    val msg: Message = List((MessageTypeTag, SET_CALL_DATA_CONF), (InvokeID, 0x07080809))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SET_DEVICE_ATTRIBUTES_CONF") {
    val src = ByteString(0,0,0,142, 7,8,9,9)
    val msg: Message = List((MessageTypeTag, SET_DEVICE_ATTRIBUTES_CONF), (InvokeID, 0x07080909))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding STOP_RECORDING_CONF") {
    val src = ByteString(0,0,0,150, 7,8,9,10)
    val msg: Message = List((MessageTypeTag, STOP_RECORDING_CONF), (InvokeID, 0x0708090a))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding UNREGISTER_SERVICE_CONF") {
    val src = ByteString(0,0,0,146, 7,8,9,11)
    val msg: Message = List((MessageTypeTag, UNREGISTER_SERVICE_CONF), (InvokeID, 0x0708090b))

    assert(Decoder.decode(src) == msg)
  }


  test("decoding USER_MESSAGE_CONF") {
    val src = ByteString(0,0,0,108, 7,8,10,11)
    val msg: Message = List((MessageTypeTag, USER_MESSAGE_CONF), (InvokeID, 0x07080a0b))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding MONITOR_AGENT_TEAM_START_CONF") {
    val src = ByteString(0,0,0,136, 1,2,3,4, 5,6,7,8)
    val msg: Message = List((MessageTypeTag, MONITOR_AGENT_TEAM_START_CONF), (InvokeID, 0x01020304),
      (MonitorID, 0x05060708))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding MONITOR_START_CONF") {
    val src = ByteString(0,0,0,94, 2,3,4,5, 6,7,8,9)
    val msg: Message = List((MessageTypeTag, MONITOR_START_CONF), (InvokeID, 0x02030405),
      (MonitorID, 0x06070809))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SESSION_MONITOR_START_CONF") {
    val src = ByteString(0,0,0,102, 3,4,5,6, 7,8,9,10)
    val msg: Message = List((MessageTypeTag, SESSION_MONITOR_START_CONF), (InvokeID, 0x03040506),
      (MonitorID, 0x0708090a))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CONFERENCE_CALL_CONF") {
    val src = ByteString(0,0,0,49, 1,2,3,4, 2,3,4,5, 0,1, 3,4, 4,5, 0,2,
      47,22,
      0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
      0x44,0,
      39,4,3,4,5,6, 40,2,0,76,
      41,23,
      0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,
      0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CONFERENCE_CALL_CONF), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.DYNAMIC)),
      (NumParties, 0x0304:Short), (LineHandle, 0x0405:Short), (LineTypeTag, Some(LineType.INSIDE)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID"), (PARTY_CALLID, 0x03040506),
      (PARTY_DEVID_TYPE, Some(DeviceIDType.AGENT_DEVICE)), (PARTY_DEVID, "ConnectedPartyDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding TRANSFER_CALL_CONF") {
    val src = ByteString(0,0,0,65, 1,2,3,4, 2,3,4,5, 0,0, 3,4, 4,5, 0,0,
      47,22,
      0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
      0x44,0,
      39,4,3,4,5,6, 40,2,0,76,
      41,23,
      0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,
      0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, TRANSFER_CALL_CONF), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)),
      (NumParties, 0x0304:Short), (LineHandle, 0x0405:Short), (LineTypeTag, Some(LineType.INBOUND_ACD)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID"), (PARTY_CALLID, 0x03040506),
      (PARTY_DEVID_TYPE, Some(DeviceIDType.AGENT_DEVICE)), (PARTY_DEVID, "ConnectedPartyDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CONSULTATION_CALL_CONF") {
    val src = ByteString(0,0,0,51, 1,2,3,4, 2,3,4,5, 0,0, 3,4, 0,1,
      47,22,
      0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
      0x44,0)
    val msg: Message = List((MessageTypeTag, CONSULTATION_CALL_CONF), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)),
      (LineHandle, 0x0304:Short), (LineTypeTag, Some(LineType.OUTBOUND_ACD)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding MAKE_CALL_CONF") {
    val src = ByteString(0,0,0,57, 1,2,3,4, 2,3,4,5, 0,0, 3,4, 0,1,
      47,22,
      0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
      0x44,0)
    val msg: Message = List((MessageTypeTag, MAKE_CALL_CONF), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)),
      (LineHandle, 0x0304:Short), (LineTypeTag, Some(LineType.OUTBOUND_ACD)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding MAKE_PREDICTIVE_CALL_CONF") {
    val src = ByteString(0,0,0,59, 1,2,3,4, 2,3,4,5, 0,0, 3,4, 0,1,
      47,22,
      0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
      0x44,0)
    val msg: Message = List((MessageTypeTag, MAKE_PREDICTIVE_CALL_CONF), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)),
      (LineHandle, 0x0304:Short), (LineTypeTag, Some(LineType.OUTBOUND_ACD)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SUPERVISOR_ASSIST_CONF") {
    val src = ByteString(0,0,0,119, 2,3,4,5, 1,2,3,4, 0,1, 3,4, 0,8,
      25,19,
      0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, SUPERVISOR_ASSIST_CONF), (InvokeID, 0x02030405),
      (ConnectionCallID, 0x01020304), (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)),
      (LineHandle, 0x0304:Short), (LineTypeTag, Some(LineType.DID)), (CONNECTION_DEVID, "ConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding EMERGENCY_CALL_CONF") {
    val src = ByteString(0,0,0,122, 2,3,4,5, 1,2,3,4, 0,1, 3,4, 0,8,
      25,19,
      0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, EMERGENCY_CALL_CONF), (InvokeID, 0x02030405),
      (ConnectionCallID, 0x01020304), (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)),
      (LineHandle, 0x0304:Short), (LineTypeTag, Some(LineType.DID)), (CONNECTION_DEVID, "ConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }



//AGENT_DESK_SETTINGS_CONF


  test("decoding CONTROL_FAILURE_CONF") {
    val src = ByteString(0,0,0,35, 1,2,3,4, 0,78, 2,3,4,5)
    val msg: Message = List((MessageTypeTag, CONTROL_FAILURE_CONF), (InvokeID, 0x01020304),
      (FailureCode, Some(ControlFailureCode.REQUEST_TIMEOUT_REJECTION)), (PeripheralErrorCode, 0x02030405))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding FAILURE_CONF") {
    val src = ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97)
    val msg: Message = List((MessageTypeTag, FAILURE_CONF), (InvokeID, 0x01020304),
      (Status, Some(StatusCode.INVALID_MONITOR_STATUS)))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding LIST_AGENT_TEAM_CONF") {
    val src = ByteString(0,0,0,134, 2,3,4,5, 0,64, 0,2, 0,1, 92,4,3,4,5,6)
    val msg: Message = List((MessageTypeTag, LIST_AGENT_TEAM_CONF), (InvokeID, 0x02030405),
      (NumberOfAgentTeams, 64), (SegmentNumber, 2), (More, true), (LIST_TEAM_ID, 0x03040506))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding OPEN_CONF") {
    val src = ByteString(0,0,0,4, 1,2,3,4, 0,0,0,0x10, 2,3,4,5, 0,0,0,0x13, 4,5,6,7, 0,1, 0,17, 0,3,
      4,5,0x33, 0x30, 0x30, 0x31,0,
      5,5,0x31, 0x30, 0x30, 0x31,0,
      6,5,0x33, 0x30, 0x30, 0x31,0,
      228,2,5,6, 224,2,0,0)
    val msg: Message = List((MessageTypeTag, OPEN_CONF), (InvokeID, 0x01020304),
      (ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS), (MonitorID, 0x02030405),
      (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN + PGStatusCode.LIMITED_FUNCTION),
      (ICMCentralControllerTime, 0x04050607), (PeripheralOnline, true),
      (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)), (AgentStateTag, Some(AgentState.AVAILABLE)),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (NUM_PERIPHERALS, 0x0506),
      (MULTI_LINE_AGENT_CONTROL, false))

    assert(Decoder.decode(src) == msg)
  }


// QUERY_AGENT_STATISTICS_CONF

  test("decoding QUERY_AGENT_STATE_CONF") {
    val src = ByteString(0,0,0,37, 1,2,3,4, 0,10, 2,3, 3,4,5,6, 4,5,6,7, 0,0, 5,6,7,8, 6,7,8,9, 0,0,0,2,
      5,5,0x31, 0x30, 0x30, 0x31,0,
      4,5,0x33, 0x30, 0x30, 0x31,0,
      6,5,0x33, 0x30, 0x30, 0x31,0,
      62,4,7,8,9,10, 63,4,8,9,10,11, 64,2,9,10, 65,2,0,7)
    val msg: Message = List((MessageTypeTag, QUERY_AGENT_STATE_CONF), (InvokeID, 0x01020304),
      (AgentStateTag, Some(AgentState.HOLD)), (NumSkillGroups, 0x0203), (MRDID, 0x03040506), (NumTasks, 0x04050607),
      (AgentMode, false), (MaxTaskLimit, 0x05060708), (ICMAgentID, 0x06070809),
      (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.APPLICATION_AVAILABLE)),
      (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"), (SKILL_GROUP_NUMBER, 0x0708090a),
      (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a), (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding QUERY_DEVICE_INFO_CONF") {
    val src = ByteString(0,0,0,79, 1,2,3,4, 0,8, 0,20, 0,0x80, 2,3, 3,4, 4,5, 5,6, 6,7,
      0,0,1,1, 0,0,0,2, 0,0,1,1, 0,0,4,1, 0,0,0,3, 70,2,7,8, 71,2,0,4)
    val msg: Message = List((MessageTypeTag, QUERY_DEVICE_INFO_CONF), (InvokeID, 0x01020304),
      (PeripheralTypeTag, Some(PeripheralType.VRU)), (TypeOfDeviceTag, Some(TypeOfDevice.TRUNK_GROUP)),
      (ClassOfDeviceTag, BitSet.empty + ClassOfDevice.VOICE), (NumLines, 0x0203), (Reserved16, 0x0304),
      (MaxActiveCalls, 0x0405), (MaxHeldCalls, 0x0506), (MaxDeviceInConference, 0x0607),
      (MakeCallSetup, BitSet.empty + AgentStateMask.AGENT_LOGIN + AgentStateMask.AGENT_RESERVED),
      (TransferConferenceSetup, BitSet.empty + TransferConferenceSetupMask.CONF_SETUP_CONSULT_ANY),
      (CallEventsSupported, BitSet.empty + CallEventMessageMask.CALL_DELIVERED + CallEventMessageMask.CALL_CONFERENCED),
      (CallControlSupported, BitSet.empty + CallControlMask.QUERY_AGENT_STATE + CallControlMask.MAKE_CALL),
      (OtherFeaturesSupported, BitSet.empty + OtherFeatureMask.POST_ROUTE + OtherFeatureMask.UNIQUE_CONSULT_CALLID),
      (LINE_HANDLE, 0x0708), (LINE_TYPE, Some(LineType.SUPERVISOR)))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding REGISTER_SERVICE_CONF") {
    val src = ByteString(0,0,0,144, 1,2,3,4, 2,3,4,5)
    val msg: Message = List((MessageTypeTag, REGISTER_SERVICE_CONF), (InvokeID, 0x01020304),
      (RegisteredServiceID, 0x02030405))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SNAPSHOT_CALL_CONF") {
    val src = ByteString(0,0,0,83, 1,2,3,4, 0,2, 2,3, 3,4, 4,5, 5,6, 0,3,
      8,12,
      0x30, 0x39, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,0,
      9,10,
      0,1,2,3,4,5,6,7,8,9,
      10,11,
      0x30, 0x33, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32,0,
      11,11,
      0x30, 0x31, 0x32, 0x30, 0x31, 0x32, 0x33, 0x31, 0x32, 0x33,0,
      12,4,
      0x33, 0x32, 0x31,0,
      72,4,6,7,8,9, 73,4,7,8,9,10,
      13,4,0x43,0x56,0x31,0, 14,4,0x43,0x56,0x32,0, 15,4,0x43,0x56,0x33,0, 16,4,0x43,0x56,0x34,0,
      17,4,0x43,0x56,0x35,0, 18,4,0x43,0x56,0x36,0, 19,4,0x43,0x56,0x37,0, 20,4,0x43,0x56,0x38,0,
      21,4,0x43,0x56,0x39,0, 22,5,0x43,0x56,0x31,0x30,0,
      46,7,0x57,0x72,0x61,0x70,0x75,0x70,0,
      82,14,0x45,0x43,0x43,0x56,0x61,0x72,0,0x45,0x43,0x43,0x56,0x61,0x6c,0,
      83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0)
    val msg: Message = List((MessageTypeTag, SNAPSHOT_CALL_CONF), (InvokeID, 0x01020304),
      (CallTypeTag, Some(CallType.PREROUTE_ACD_IN)), (NumCTIClients, 0x0203), (NumCallDevices, 0x0304),
      (NumNamedVariables, 0x0405), (NumNamedArrays, 0x0506),
      (CalledPartyDisposition, Some(DispositionCodeValue.Abandoned_Ring)),
      (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)), (DNIS, "0398765432"),
      (DIALED_NUMBER, "0120123123"), (CED, "321"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"), (CALL_WRAPUP_DATA, "Wrapup"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal")))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SNAPSHOT_DEVICE_CONF") {
    val src = ByteString(0,0,0,85, 1,2,3,4, 2,3,
      56,4,3,4,5,6,
      57,2,0,0,
      58,23,
      0x43,0x61,0x6c,0x6c,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,
      0x49,0x44,0,
      75,2,0,3,
      218,2,0,2)
    val msg: Message = List((MessageTypeTag, SNAPSHOT_DEVICE_CONF), (InvokeID, 0x01020304), (NumCalls, 0x0203),
      (CALL_CONN_CALLID, 0x03040506), (CALL_CONN_DEVID_TYPE, Some(ConnectionDeviceIDType.STATIC)),
      (CALL_CONN_DEVID, "CallConnectionDeviceID"), (CALL_STATE, Some(LocalConnectionState.CONNECT)),
      (SILENT_MONITOR_STATUS, Some(SilentMonitorStatus.TARGET)))

    assert(Decoder.decode(src) == msg)
  }


  test("decoding START_RECORDING_CONF") {
    val src = ByteString(0,0,0,148, 1,2,3,4, 2,3,4,5, 3,4,5,6,
      1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
      226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)
    val msg: Message = List((MessageTypeTag, START_RECORDING_CONF), (InvokeID, 0x01020304),
      (SessionID, 0x02030405), (ServerData, 0x03040506),
      (CLIENT_ID, "ClientID"), (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SUPERVISE_CALL_CONF") {
    val src = ByteString(0,0,0,125, 1,2,3,4, 2,3,4,5, 0,1,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, SUPERVISE_CALL_CONF), (InvokeID, 0x01020304),
      (ConnectionCallID, 0x02030405), (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)),
      (CONNECTION_DEVID, "ConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding AGENT_PRE_CALL_ABORT_EVENT") {
    val src = ByteString(0,0,0,106, 1,2,3,4, 2,3,4,5,
      6,5,0x33,0x30,0x30,0x31,0,
      72,4,6,7,8,9, 73,4,7,8,9,10, 110,4,8,9,10,11)
    val msg: Message = List((MessageTypeTag, AGENT_PRE_CALL_ABORT_EVENT), (MonitorID, 0x01020304),
      (MRDID, 0x02030405), (AGENT_INSTRUMENT, "3001"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a), (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x08090a0b))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding AGENT_PRE_CALL_EVENT") {
    val src = ByteString(0,0,0,105, 1,2,3,4, 2,3, 3,4, 4,5,6,7, 5,6,7,8, 6,7,8,9, 7,8,9,10, 8,9, 9,10,11,12,
      6,5,0x33,0x30,0x30,0x31,0,
      72,4,6,7,8,9, 73,4,7,8,9,10, 110,4,8,9,10,11,
      8,12,0x30,0x39,0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0,
      9,10,0,1,2,3,4,5,6,7,8,9,
      11,11,0x30, 0x31, 0x32, 0x30, 0x31, 0x32, 0x33, 0x31, 0x32, 0x33,0,
      12,4,0x33, 0x32, 0x31,0,
      13,4,0x43,0x56,0x31,0, 14,4,0x43,0x56,0x32,0, 15,4,0x43,0x56,0x33,0, 16,4,0x43,0x56,0x34,0,
      17,4,0x43,0x56,0x35,0, 18,4,0x43,0x56,0x36,0, 19,4,0x43,0x56,0x37,0, 20,4,0x43,0x56,0x38,0,
      21,4,0x43,0x56,0x39,0, 22,5,0x43,0x56,0x31,0x30,0,
      82,14,0x45,0x43,0x43,0x56,0x61,0x72,0,0x45,0x43,0x43,0x56,0x61,0x6c,0,
      83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0)
    val msg: Message = List((MessageTypeTag, AGENT_PRE_CALL_EVENT), (MonitorID, 0x01020304),
      (NumNamedVariables, 0x0203), (NumNamedArrays, 0x0304), (ServiceNumber, 0x04050607),
      (ServiceID, 0x05060708), (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a),
      (SkillGroupPriority, 0x0809), (MRDID, 0x090a0b0c),
      (AGENT_INSTRUMENT, "3001"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a), (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x08090a0b),
      (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)),
      (DIALED_NUMBER, "0120123123"), (CED, "321"),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal")))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding AGENT_STATE_EVENT") {
    val src = ByteString(0,0,0,30, 1,2,3,4, 2,3,4,5, 3,4,5,6, 0,17, 0,7, 4,5,6,7,
      6,7,8,9, 7,8,9,10, 8,9, 0,10, 9,10, 9,10,11,12, 10,11,12,13, 0,0, 9,8,7,6, 8,7,6,5, 0,0,0,1, 7,6,
      3,16,0x43,0x6c,0x69,0x65,0x6e,0x74,0x53,0x69,0x67,0x6e,0x61,0x74,0x75,0x72,0x65,0,
      5,5,0x31,0x30,0x30,0x31,0, 4,5,0x33,0x30,0x30,0x31,0, 6,5,0x33,0x30,0x30,0x31,0,
      150,4,6,5,4,3, 123,2,0,4, 244,4,0,0,0,1,
      62,4,7,8,9,10, 63,4,8,9,10,11, 64,2,9,10, 65,2,0,7)
    val msg: Message = List((MessageTypeTag, AGENT_STATE_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (SessionID, 0x03040506),
      (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
      (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809),
      (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a), (MRDID, 0x090a0b0c),
      (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
      (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706),
      (CLIENT_SIGNATURE, "ClientSignature"),
      (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
      (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
      (DIRECTION, Some(CallDirection.In)),
      (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a),
      (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding AGENT_TEAM_CONFIG_EVENT") {
    val src = ByteString(0,0,0,128, 1,2,3,4, 2,3,4,5, 3,4, 0,2,
      243,14,0x41,0x67,0x65,0x6e,0x74,0x54,0x65,0x61,0x6d,0x4e,0x61,0x6d,0x65,0,
      86,5,0x31,0x30,0x30,0x31,0,
      87,2,0,4, 88,2,0,10, 89,4,4,5,6,7)
    val msg: Message = List((MessageTypeTag, AGENT_TEAM_CONFIG_EVENT), (PeripheralID, 0x01020304),
      (TeamID, 0x02030405), (NumberOfAgents, 0x0304), (ConfigOperationTag, Some(ConfigOperation.RemoveAgent)),
      (AGENT_TEAM_NAME, "AgentTeamName"), (ATC_AGENT_ID, "1001"), (AGENT_FLAGS, BitSet.empty + AgentFlags.Supervisor),
      (ATC_AGENT_STATE, Some(AgentState.HOLD)), (ATC_STATE_DURATION, 0x04050607))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding BEGIN_CALL_EVENT") {
    val src = ByteString(0,0,0,23, 1,2,3,4, 2,3,4,5, 0,17, 3,4, 4,5, 5,6, 0,2, 0,0, 6,7,8,9, 0,13,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      8,12,0x30, 0x39, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,0,
      9,10,0,1,2,3,4,5,6,7,8,9,
      10,11,0x30, 0x33, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32,0,
      11,11,0x30, 0x31, 0x32, 0x30, 0x31, 0x32, 0x33, 0x31, 0x32, 0x33,0,
      12,4,0x33, 0x32, 0x31,0,
      72,4,6,7,8,9, 73,4,7,8,9,10, 110,4,8,9,10,11,
      13,4,0x43,0x56,0x31,0, 14,4,0x43,0x56,0x32,0, 15,4,0x43,0x56,0x33,0, 16,4,0x43,0x56,0x34,0,
      17,4,0x43,0x56,0x35,0, 18,4,0x43,0x56,0x36,0, 19,4,0x43,0x56,0x37,0, 20,4,0x43,0x56,0x38,0,
      21,4,0x43,0x56,0x39,0, 22,5,0x43,0x56,0x31,0x30,0,
      46,7,0x57,0x72,0x61,0x70,0x75,0x70,0,
      82,14,0x45,0x43,0x43,0x56,0x61,0x72,0,0x45,0x43,0x43,0x56,0x61,0x6c,0,
      83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0,
      23,19,0x43,0x74,0x69,0x43,0x6c,0x69,0x65,0x6e,0x74,0x53,0x69,0x67,0x6e,0x61,0x74,0x75,0x72,0x65,0,
      24,4,9,10,11,12,
      223,10,9,8,7,6,5,4,3,2,1,0)
    val msg: Message = List((MessageTypeTag, BEGIN_CALL_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (NumCTIClients, 0x0304), (NumNamedVariables, 0x0405), (NumNamedArrays, 0x0506),
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

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_AGENT_GREETING_EVENT") {
    val src = ByteString(0,0,0,248, 1,2,3,4, 2,3,4,5, 0,0, 6,7,8,9, 0,1, 3,4,5,6,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      5,5,0x31,0x30,0x30,0x31,0)
    val msg: Message = List((MessageTypeTag, CALL_AGENT_GREETING_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (EventCodeTag, Some(EventCode.GreetingHasEndedWithSuccess)), (PeripheralErrorCode, 0x03040506),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (AGENT_ID, "1001"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_ATTRIBUTE_CHANGE_EVENT") {
    val src = ByteString(0,0,0,240, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 3,4,5,6,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_ATTRIBUTE_CHANGE_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (CallTypeID, 0x06070809),
      (ServiceNumber, 0x03040506),
      (CONNECTION_DEVID, "ConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_CLEARED_EVENT") {
    val src = ByteString(0,0,0,13, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 0,0, 0,5,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_CLEARED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.CALL_CANCELLED)),
      (CONNECTION_DEVID, "ConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_CONFERENCED_EVENT") {
    val src = ByteString(0,0,0,17, 1,2,3,4, 2,3,4,5, 0,17, 0,76, 6,7,8,9, 3,4, 0,4, 4,5,6,7, 5,6,7,8, 6,7,
      7,8, 0,76, 7,8,9,10, 0,76, 0,70, 0,3, 0,25,
      35,16,0x50,0x72,0x69,0x6d,0x61,0x72,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      36,18,0x53,0x65,0x63,0x6f,0x6e,0x64,0x61,0x72,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      37,19,0x43,0x6f,0x6e,0x74,0x72,0x6f,0x6c,0x6c,0x65,0x72,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      38,19,0x41,0x64,0x64,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      39,4,8,9,10,11,
      40,2,0,75,
      41,23,
      0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,
      0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_CONFERENCED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (PrimaryDeviceIDType, Some(DeviceIDType.AGENT_DEVICE)), (PrimaryCallID, 0x06070809),
      (LineHandle, 0x0304), (LineTypeTag, Some(LineType.SUPERVISOR)),
      (SkillGroupNumber, 0x04050607), (SkillGroupID, 0x05060708), (SkillGroupPriority, 0x0607),
      (NumParties, 0x0708), (SecondaryDeviceIDType, Some(DeviceIDType.AGENT_DEVICE)),
      (SecondaryCallID, 0x0708090a), (ControllerDeviceType, Some(DeviceIDType.AGENT_DEVICE)),
      (AddedPartyDeviceType, Some(DeviceIDType.TRUNK_IDENTIFIER)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.PARK)),
      (PRIMARY_DEVID, "PrimaryDeviceID"), (SECONDARY_DEVID, "SecondaryDeviceID"),
      (CONTROLLER_DEVID, "ControllerDeviceID"), (ADDED_PARTY_DEVID, "AddedPartyDeviceID"),
      (PARTY_CALLID, 0x08090a0b), (PARTY_DEVID_TYPE, Some(DeviceIDType.EXTERNAL)),
      (PARTY_DEVID, "ConnectedPartyDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_CONNECTION_CLEARED_EVENT") {
    val src = ByteString(0,0,0,14, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 0,75, 0,0, 0,5,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      33,18,0x52,0x65,0x6c,0x65,0x61,0x73,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_CONNECTION_CLEARED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (ReleasingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.CALL_CANCELLED)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (RELEASING_DEVID, "ReleasingDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_DATA_UPDATE_EVENT") {
    val src = ByteString(0,0,0,25, 1,2,3,4, 2,3,4,5, 0,17, 3,4, 4,5, 5,6, 0,2, 0,0, 6,7,8,9,
      0,0, 2,3,4,5, 0,13, 4,5,6,7, 5,6,7,8,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      47,22,
      0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
      0x44,0,
      8,12,0x30, 0x39, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,0,
      9,10,0,1,2,3,4,5,6,7,8,9,
      10,11,0x30, 0x33, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32,0,
      11,11,0x30, 0x31, 0x32, 0x30, 0x31, 0x32, 0x33, 0x31, 0x32, 0x33,0,
      12,4,0x33, 0x32, 0x31,0,
      72,4,6,7,8,9, 73,4,7,8,9,10, 110,4,8,9,10,11,
      13,4,0x43,0x56,0x31,0, 14,4,0x43,0x56,0x32,0, 15,4,0x43,0x56,0x33,0, 16,4,0x43,0x56,0x34,0,
      17,4,0x43,0x56,0x35,0, 18,4,0x43,0x56,0x36,0, 19,4,0x43,0x56,0x37,0, 20,4,0x43,0x56,0x38,0,
      21,4,0x43,0x56,0x39,0, 22,5,0x43,0x56,0x31,0x30,0,
      46,7,0x57,0x72,0x61,0x70,0x75,0x70,0,
      82,14,0x45,0x43,0x43,0x56,0x61,0x72,0,0x45,0x43,0x43,0x56,0x61,0x6c,0,
      83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0,
      95,12,0x30,0x38,0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0,
      96,10,0x31,0x32,0x33,0x31,0x32,0x33,0x31,0x32,0x33,0,
      23,19,0x43,0x74,0x69,0x43,0x6c,0x69,0x65,0x6e,0x74,0x53,0x69,0x67,0x6e,0x61,0x74,0x75,0x72,0x65,0,
      24,4,9,10,11,12,
      223,10,9,8,7,6,5,4,3,2,1,0)
    val msg: Message = List((MessageTypeTag, CALL_DATA_UPDATE_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (NumCTIClients, 0x0304), (NumNamedVariables, 0x0405), (NumNamedArrays, 0x0506),
      (CallTypeTag, Some(CallType.PREROUTE_ACD_IN)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)), (NewConnectionCallID, 0x02030405),
      (CalledPartyDisposition, Some(DispositionCodeValue.Disconnect_drop_handled_primary_route)),
      (CampaignID, 0x04050607), (QueryRuleID, 0x05060708),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (NEW_CONNECTION_DEVID, "NewConnectionDeviceID"),
      (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)),
      (DNIS, "0398765432"), (DIALED_NUMBER, "0120123123"), (CED, "321"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a), (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x08090a0b),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"), (CALL_WRAPUP_DATA, "Wrapup"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal")),
      (CUSTOMER_PHONE_NUMBER, "08012345678"), (CUSTOMER_ACCOUNT_NUMBER, "123123123"),
      (CTI_CLIENT_SIGNATURE, "CtiClientSignature"), (CTI_CLIENT_TIMESTAMP, 0x090a0b0c),
      (CALL_REFERENCE_ID, ByteString(9,8,7,6,5,4,3,2,1,0)))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_DELIVERED_EVENT") {
    val src = ByteString(0,0,0,9, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 3,4, 0,4,
      4,5,6,7, 5,6,7,8, 4,5,6,7, 5,6,7,8, 6,7, 0,76, 0,75, 0,74, 0,73, 0,0, 0xff,0xff, 4,5, 5,6,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      26,17,0x41,0x6c,0x65,0x72,0x74,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      29,21,0x4c,0x61,0x73,0x74,0x52,0x65,0x64,0x69,0x72,0x65,0x63,0x74,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      121,4,1,2,3,4,
      122,4,2,3,4,5,
      202,4,3,4,5,6,
      8,12,0x30, 0x39, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,0,
      215,1,0,
      9,10,0,1,2,3,4,5,6,7,8,9,
      10,11,0x30, 0x33, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32,0,
      11,11,0x30, 0x31, 0x32, 0x30, 0x31, 0x32, 0x33, 0x31, 0x32, 0x33,0,
      12,4,0x33, 0x32, 0x31,0,
      13,4,0x43,0x56,0x31,0, 14,4,0x43,0x56,0x32,0, 15,4,0x43,0x56,0x33,0, 16,4,0x43,0x56,0x34,0,
      17,4,0x43,0x56,0x35,0, 18,4,0x43,0x56,0x36,0, 19,4,0x43,0x56,0x37,0, 20,4,0x43,0x56,0x38,0,
      21,4,0x43,0x56,0x39,0, 22,5,0x43,0x56,0x31,0x30,0,
      46,7,0x57,0x72,0x61,0x70,0x75,0x70,0,
      82,14,0x45,0x43,0x43,0x56,0x61,0x72,0,0x45,0x43,0x43,0x56,0x61,0x6c,0,
      83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0)
    val msg: Message = List((MessageTypeTag, CALL_DELIVERED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (LineHandle, 0x0304), (LineTypeTag, Some(LineType.SUPERVISOR)),
      (ServiceNumber, 0x04050607), (ServiceID, 0x05060708),
      (SkillGroupNumber, 0x04050607), (SkillGroupID, 0x05060708), (SkillGroupPriority, 0x0607),
      (AlertingDeviceType, Some(DeviceIDType.AGENT_DEVICE)),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LastRedirectDeviceType, Some(DeviceIDType.CTI_PORT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.NONE)),
      (NumNamedVariables, 0x0405), (NumNamedArrays, 0x0506),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (ALERTING_DEVID, "AlertingDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"), (CALLED_DEVID, "CalledDeviceID"),
      (LAST_REDIRECT_DEVID, "LastRedirectDeviceID"),
      (TRUNK_NUMBER, 0x01020304), (TRUNK_GROUP_NUMBER, 0x02030405), (SECONDARY_CONNECTION_CALL_ID, 0x03040506),
      (ANI, "09012345678"), (ANI_II, ""), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)),
      (DNIS, "0398765432"), (DIALED_NUMBER, "0120123123"), (CED, "321"),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"), (CALL_WRAPUP_DATA, "Wrapup"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal")))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_DEQUEUED_EVENT") {
    val src = ByteString(0,0,0,86, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 0,77,
      4,5,6,7, 5,6,7,8, 6,7, 7,8, 0,0, 0xff,0xff,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      62,4,8,9,10,11,
      63,4,9,10,11,12,
      64,2,10,11)
    val msg: Message = List((MessageTypeTag, CALL_DEQUEUED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (QueueDeviceType, Some(DeviceIDType.QUEUE)), (ServiceNumber, 0x04050607), (ServiceID, 0x05060708),
      (NumQueued, 0x0607), (NumSkillGroups, 0x0708),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.NONE)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (SKILL_GROUP_NUMBER, 0x08090a0b),
      (SKILL_GROUP_ID, 0x090a0b0c),
      (SKILL_GROUP_PRIORITY, 0x0a0b))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_DIVERTED_EVENT") {
    val src = ByteString(0,0,0,19, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9,
      4,5,6,7, 5,6,7,8, 0,77, 0,77, 0,0, 0xff,0xff,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      44,18,0x44,0x69,0x76,0x65,0x72,0x74,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_DIVERTED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (ServiceNumber, 0x04050607), (ServiceID, 0x05060708),
      (DivertingDeviceType, Some(DeviceIDType.QUEUE)), (CalledDeviceType, Some(DeviceIDType.QUEUE)),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.NONE)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (DIVERTING_DEVID, "DivertingDeviceID"),
      (CALLED_DEVID, "CalledDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_ESTABLISHED_EVENT") {
    val src = ByteString(0,0,0,10, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 4,5, 0,4, 5,6,7,8, 6,7,8,9,
      7,8,9,10, 8,9,10,11, 9,10, 0,76, 0,75, 0,74, 0,73, 0,3, 0,22,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      30,18,0x41,0x6e,0x73,0x77,0x65,0x72,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      29,21,0x4c,0x61,0x73,0x74,0x52,0x65,0x64,0x69,0x72,0x65,0x63,0x74,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      121,4,1,2,3,4,
      122,4,2,3,4,5)
    val msg: Message = List((MessageTypeTag, CALL_ESTABLISHED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (LineHandle, 0x0405:Short), (LineTypeTag, Some(LineType.SUPERVISOR)), (ServiceNumber, 0x05060708),
      (ServiceID, 0x06070809), (SkillGroupNumber, 0x0708090a), (SkillGroupID, 0x08090a0b),
      (SkillGroupPriority, 0x090a:Short), (AnsweringDeviceType, Some(DeviceIDType.AGENT_DEVICE)),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LastRedirectDeviceType, Some(DeviceIDType.CTI_PORT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (ANSWERING_DEVID, "AnsweringDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"), (CALLED_DEVID, "CalledDeviceID"),
      (LAST_REDIRECT_DEVID, "LastRedirectDeviceID"),
      (TRUNK_NUMBER, 0x01020304), (TRUNK_GROUP_NUMBER, 0x02030405))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_FAILED_EVENT") {
    val src = ByteString(0,0,0,16, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 0,75, 0,74, 0,1, 0,22,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      34,16,0x46,0x61,0x69,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_FAILED_EVENT),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (FailingDeviceType, Some(DeviceIDType.EXTERNAL)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.INITIATE)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (FAILING_DEVID, "FailingDeviceID"), (CALLED_DEVID, "CalledDeviceID")
   )

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_HELD_EVENT") {
    val src = ByteString(0,0,0,11, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 0,75, 0,1, 0,22,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      31,17,0x48,0x6f,0x6c,0x64,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_HELD_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (HoldingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (LocalConnectionStateTag, Some(LocalConnectionState.INITIATE)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (HOLDING_DEVID, "HoldingDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_ORIGINATED_EVENT") {
    val src = ByteString(0,0,0,15, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 4,5, 0,4, 5,6,7,8, 6,7,8,9,
      7,8,9,10, 8,9,10,11, 9,10, 0,75, 0,74, 0,3, 0,22,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_ORIGINATED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (LineHandle, 0x0405:Short), (LineTypeTag, Some(LineType.SUPERVISOR)), (ServiceNumber, 0x05060708),
      (ServiceID, 0x06070809), (SkillGroupNumber, 0x0708090a), (SkillGroupID, 0x08090a0b),
      (SkillGroupPriority, 0x090a:Short),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"), (CALLED_DEVID, "CalledDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_QUEUED_EVENT") {
    val src = ByteString(0,0,0,21, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9,
      4,5,6,7, 5,6,7,8, 0,77, 0,75, 0,74, 0,73, 6,7, 7,8, 0,0, 0xff,0xff,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      45,14,0x51,0x75,0x65,0x75,0x65,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      29,21,0x4c,0x61,0x73,0x74,0x52,0x65,0x64,0x69,0x72,0x65,0x63,0x74,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      62,4,8,9,10,11,
      63,4,9,10,11,12,
      64,2,10,11)
    val msg: Message = List((MessageTypeTag, CALL_QUEUED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (ServiceNumber, 0x04050607), (ServiceID, 0x05060708),
      (QueueDeviceType, Some(DeviceIDType.QUEUE)),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LastRedirectDeviceType, Some(DeviceIDType.CTI_PORT)),
      (NumQueued, 0x0607), (NumSkillGroups, 0x0708),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.NONE)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (QUEUE_DEVID, "QueueDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"), (CALLED_DEVID, "CalledDeviceID"),
      (LAST_REDIRECT_DEVID, "LastRedirectDeviceID"),
      (SKILL_GROUP_NUMBER, 0x08090a0b),
      (SKILL_GROUP_ID, 0x090a0b0c),
      (SKILL_GROUP_PRIORITY, 0x0a0b))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_REACHED_NETWORK_EVENT") {
    val src = ByteString(0,0,0,34, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 4,5, 0,4,
      0,70, 0,74, 0,3, 0,22,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      48,18,0x54,0x72,0x75,0x6e,0x6b,0x55,0x73,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      121,4,4,5,6,7, 122,4,5,6,7,8)
    val msg: Message = List((MessageTypeTag, CALL_REACHED_NETWORK_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (LineHandle, 0x0405:Short), (LineTypeTag, Some(LineType.SUPERVISOR)),
      (TrunkUsedDeviceType, Some(DeviceIDType.TRUNK_IDENTIFIER)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (TRUNK_USED_DEVID, "TrunkUsedDeviceID"), (CALLED_DEVID, "CalledDeviceID"),
      (TRUNK_NUMBER, 0x04050607), (TRUNK_GROUP_NUMBER, 0x05060708))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_RETRIEVED_EVENT") {
    val src = ByteString(0,0,0,12, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 0,75, 0,1, 0,22,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      32,19,0x52,0x65,0x74,0x72,0x69,0x65,0x76,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_RETRIEVED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (RetrievingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (LocalConnectionStateTag, Some(LocalConnectionState.INITIATE)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (RETRIEVING_DEVID, "RetrievingDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_SERVICE_INITIATED_EVENT") {
    val src = ByteString(0,0,0,20, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 4,5, 0,4, 5,6,7,8, 6,7,8,9,
      7,8,9,10, 8,9,10,11, 9,10, 0,75, 0,3, 0,22,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_SERVICE_INITIATED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (LineHandle, 0x0405:Short), (LineTypeTag, Some(LineType.SUPERVISOR)), (ServiceNumber, 0x05060708),
      (ServiceID, 0x06070809), (SkillGroupNumber, 0x0708090a), (SkillGroupID, 0x08090a0b),
      (SkillGroupPriority, 0x090a:Short),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_TRANSFERRED_EVENT") {
    val src = ByteString(0,0,0,18, 1,2,3,4, 2,3,4,5, 0,17, 0,76, 6,7,8,9, 3,4, 0,4, 4,5,6,7, 5,6,7,8, 6,7,
      7,8, 0,76, 7,8,9,10, 0,76, 0,70, 0,3, 0,25,
      35,16,0x50,0x72,0x69,0x6d,0x61,0x72,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      36,18,0x53,0x65,0x63,0x6f,0x6e,0x64,0x61,0x72,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      42,21,0x54,0x72,0x61,0x6e,0x73,0x66,0x65,0x72,0x72,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      43,20,0x54,0x72,0x61,0x6e,0x73,0x66,0x65,0x72,0x72,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      39,4,8,9,10,11,
      40,2,0,75,
      41,23,
      0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,
      0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, CALL_TRANSFERRED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (PrimaryDeviceIDType, Some(DeviceIDType.AGENT_DEVICE)), (PrimaryCallID, 0x06070809),
      (LineHandle, 0x0304), (LineTypeTag, Some(LineType.SUPERVISOR)),
      (SkillGroupNumber, 0x04050607), (SkillGroupID, 0x05060708), (SkillGroupPriority, 0x0607),
      (NumParties, 0x0708), (SecondaryDeviceIDType, Some(DeviceIDType.AGENT_DEVICE)),
      (SecondaryCallID, 0x0708090a), (TransferringDeviceType, Some(DeviceIDType.AGENT_DEVICE)),
      (TransferredDeviceType, Some(DeviceIDType.TRUNK_IDENTIFIER)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.PARK)),
      (PRIMARY_DEVID, "PrimaryDeviceID"), (SECONDARY_DEVID, "SecondaryDeviceID"),
      (TRANSFERRING_DEVID, "TransferringDeviceID"), (TRANSFERRED_DEVID, "TransferredDeviceID"),
      (PARTY_CALLID, 0x08090a0b), (PARTY_DEVID_TYPE, Some(DeviceIDType.EXTERNAL)),
      (PARTY_DEVID, "ConnectedPartyDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CALL_TRANSLATION_ROUTE_EVENT") {
    val src = ByteString(0,0,0,22, 4,5, 5,6,
      8,12,0x30, 0x39, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,0,
      9,10,0,1,2,3,4,5,6,7,8,9,
      10,11,0x30, 0x33, 0x39, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32,0,
      11,11,0x30, 0x31, 0x32, 0x30, 0x31, 0x32, 0x33, 0x31, 0x32, 0x33,0,
      12,4,0x33, 0x32, 0x31,0,
      72,4,6,7,8,9, 73,4,7,8,9,10, 110,4,8,9,10,11,
      13,4,0x43,0x56,0x31,0, 14,4,0x43,0x56,0x32,0, 15,4,0x43,0x56,0x33,0, 16,4,0x43,0x56,0x34,0,
      17,4,0x43,0x56,0x35,0, 18,4,0x43,0x56,0x36,0, 19,4,0x43,0x56,0x37,0, 20,4,0x43,0x56,0x38,0,
      21,4,0x43,0x56,0x39,0, 22,5,0x43,0x56,0x31,0x30,0,
      82,14,0x45,0x43,0x43,0x56,0x61,0x72,0,0x45,0x43,0x43,0x56,0x61,0x6c,0,
      83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0)
    val msg: Message = List((MessageTypeTag, CALL_TRANSLATION_ROUTE_EVENT),
      (NumNamedVariables, 0x0405), (NumNamedArrays, 0x0506),
      (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)),
      (DNIS, "0398765432"), (DIALED_NUMBER, "0120123123"), (CED, "321"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a), (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x08090a0b),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal")))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CLIENT_SESSION_CLOSED_EVENT") {
    val src = ByteString(0,0,0,100, 1,2,3,4, 2,3,4,5, 0,0,0,17, 3,4,5,6,
      81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
      1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
      3,16,0x43,0x6c,0x69,0x65,0x6e,0x74,0x53,0x69,0x67,0x6e,0x61,0x74,0x75,0x72,0x65,0,
      4,5,0x33, 0x30, 0x30, 0x31,0,
      5,5,0x31, 0x30, 0x30, 0x31,0,
      6,5,0x33, 0x30, 0x30, 0x31,0,
      226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)
    val msg: Message = List((MessageTypeTag, CLIENT_SESSION_CLOSED_EVENT), (SessionID, 0x01020304),
      (PeripheralID, 0x02030405), (Status, Some(StatusCode.UNSPECIFIED_FAILURE)), (ClientPort, 0x03040506),
      (CLIENT_ADDRESS, "ClientAddress"), (CLIENT_ID, "ClientID"), (CLIENT_SIGNATURE, "ClientSignature"),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding CLIENT_SESSION_OPENED_EVENT") {
    val src = ByteString(0,0,0,99, 1,2,3,4, 2,3,4,5, 0,0,0,0x10, 0,0,1,1, 0,0,1,1, 3,4,5,6,
      81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
      1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
      3,16,0x43,0x6c,0x69,0x65,0x6e,0x74,0x53,0x69,0x67,0x6e,0x61,0x74,0x75,0x72,0x65,0,
      4,5,0x33, 0x30, 0x30, 0x31,0,
      5,5,0x31, 0x30, 0x30, 0x31,0,
      6,5,0x33, 0x30, 0x30, 0x31,0,
      226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)
    val msg: Message = List((MessageTypeTag, CLIENT_SESSION_OPENED_EVENT), (SessionID, 0x01020304),
      (PeripheralID, 0x02030405),
      (ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS),
      (CallMsgMask, BitSet.empty + CallEventMessageMask.CALL_DELIVERED + CallEventMessageMask.CALL_CONFERENCED),
      (AgentStateMaskTag, BitSet.empty + AgentStateMask.AGENT_LOGIN + AgentStateMask.AGENT_RESERVED),
      (ClientPort, 0x03040506),
      (CLIENT_ADDRESS, "ClientAddress"), (CLIENT_ID, "ClientID"), (CLIENT_SIGNATURE, "ClientSignature"),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding EMERGENCY_CALL_EVENT") {
    val src = ByteString(0,0,0,123, 1,2,3,4, 3,4,5,6, 0,1, 2,3,4,5,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
      81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
      4,5,0x33, 0x30, 0x30, 0x31,0,
      5,5,0x31, 0x30, 0x30, 0x31,0,
      6,5,0x33, 0x30, 0x30, 0x31,0,
      226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)
    val msg: Message = List((MessageTypeTag, EMERGENCY_CALL_EVENT), (PeripheralID, 0x01020304),
      (ConnectionCallID, 0x03040506), (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)),
      (SessionID, 0x02030405),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (CLIENT_ID, "ClientID"), (CLIENT_ADDRESS, "ClientAddress"),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding END_CALL_EVENT") {
    val src = ByteString(0,0,0,24, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, END_CALL_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (CONNECTION_DEVID, "ConnectionDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding FAILURE_EVENT") {
    val src = ByteString(0,0,0,2, 0,0,0,17)
    val msg: Message = List((MessageTypeTag, FAILURE_EVENT), (Status, Some(StatusCode.UNSPECIFIED_FAILURE)))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding RTP_STARTED_EVENT") {
    val src = ByteString(0,0,0,116, 1,2,3,4, 2,3,4,5, 3,4,5,6, 0,2, 0,0, 4,5,6,7, 0,0, 5,6,7,8, 6,7,
      0,0, 6,7,8,9,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
      4,5,0x33, 0x30, 0x30, 0x31,0,
      5,5,0x31, 0x30, 0x30, 0x31,0,
      6,5,0x33, 0x30, 0x30, 0x31,0,
      125,15,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
      126,12,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x50,0x6f,0x72,0x74,0,
      226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0,
      227,19,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)
    val msg: Message = List((MessageTypeTag, RTP_STARTED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (ClientPort, 0x03040506), (RTPDirectionTag, Some(RTPDirection.BiDirectional)),
      (RTPTypeTag, Some(RTPType.Audio)), (BitRate, 0x04050607), (EchoCancellation, false),
      (PacketSize, 0x05060708), (PayloadType, 0x0607),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (CLIENT_ADDRESS, "ClientAddress"),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"),
      (SENDING_ADDRESS, "SendingAddress"), (SENDING_PORT, "SendingPort"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6"), (SENDING_ADDRESS_IPV6, "SendingAddressIPv6"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding RTP_STOPPED_EVENT") {
    val src = ByteString(0,0,0,117, 1,2,3,4, 2,3,4,5, 3,4,5,6, 0,2, 0,0, 6,7,8,9,
      25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
      81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
      5,5,0x31, 0x30, 0x30, 0x31,0,
      4,5,0x33, 0x30, 0x30, 0x31,0,
      6,5,0x33, 0x30, 0x30, 0x31,0,
      125,15,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
      126,12,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x50,0x6f,0x72,0x74,0,
      226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0,
      227,19,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)
    val msg: Message = List((MessageTypeTag, RTP_STOPPED_EVENT), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (ClientPort, 0x03040506), (RTPDirectionTag, Some(RTPDirection.BiDirectional)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (CLIENT_ADDRESS, "ClientAddress"),
      (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
      (SENDING_ADDRESS, "SendingAddress"), (SENDING_PORT, "SendingPort"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6"), (SENDING_ADDRESS_IPV6, "SendingAddressIPv6"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding SYSTEM_EVENT") {
    val src = ByteString(0,0,0,31, 0,0,0,3, 1,2,3,4, 0,0,0,5, 2,3,4,5, 3,4,5,6, 4,5,6,7, 0,70,
    7,16,0x53,0x79,0x73,0x74,0x65,0x6d,0x45,0x76,0x65,0x6e,0x74,0x54,0x65,0x78,0x74,0,
    206,14,0x45,0x76,0x65,0x6e,0x74,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)
    val msg: Message = List((MessageTypeTag, SYSTEM_EVENT),
      (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN),
      (ICMCentralControllerTime, 0x01020304), (SystemEventIDTag, Some(SystemEventID.TEXT_FYI)),
      (SystemEventArg1, 0x02030405), (SystemEventArg2, 0x03040506),
      (SystemEventArg3, 0x04050607), (EventDeviceType, Some(DeviceIDType.TRUNK_IDENTIFIER)),
      (TEXT, "SystemEventText"), (EVENT_DEVICE_ID, "EventDeviceID"))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding UNKNOWN_TYPE") {
    val src = ByteString(0,0,0,255, 0,1,2,3,4,5,6,7,8,9
    )
    val msg: Message = List((MessageTypeTag, UNKNOWN_TYPE),
      (RawBytes, ByteString(0,1,2,3,4,5,6,7,8,9)))

    assert(Decoder.decode(src) == msg)
  }

  test("decoding USER_MESSAGE_EVENT") {
    val src = ByteString(0,0,0,109, 1,2,3,4, 0,2,
      1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
      7,21,0x55,0x73,0x65,0x72,0x4d,0x65,0x73,0x73,0x61,0x67,0x65,0x45,0x76,0x65,0x6e,0x74,0x54,0x65,0x78,0x74,0)
    val msg: Message = List((MessageTypeTag, USER_MESSAGE_EVENT),
      (ICMCentralControllerTime, 0x01020304), (Distribution, Some(DistributionValue.TEAM)),
      (CLIENT_ID, "ClientID"), (TEXT, "UserMessageEventText"))

    val dst = Decoder decode src
    //    print("src:"); println(src.to[List])
    //    print("msg:"); println(msg)
    //    print("decoded:"); println(dst)
    val i = msg.iterator
    val j = dst.iterator
    while (i.hasNext && j.hasNext) {
      val ielm = i.next()
      val jelm = j.next()
      if (ielm != jelm) {
        print(ielm)
        print(" !!!! Not Equal !!!! ")
        println(jelm)
      }
    }

    assert(Decoder.decode(src) == msg)
  }

}
package ctidriver

import akka.util.ByteString
import ctidriver.MessageType._
import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatest.junit.JUnitRunner

import scala.collection.immutable.BitSet

/**
  * Created by nshimaza on 2016/04/13.
  */
@RunWith(classOf[JUnitRunner])
class MessageSamplesSpec extends WordSpec with MustMatchers {
  "Decoder and encoder" must {
    "decode and encode all message in MessageSamples" in {
      MessageSamples.samples foreach { s =>
        s._2.decode mustBe s._1
        s._1.encode mustBe s._2
      }
    }

    "decode undefined message type as None with raw bytes" in {
      InvalidMessageSamples.samples foreach { s =>
        s._2.decode mustBe s._1
      }
    }
  }
}

object MessageSamples {
//  case class Entry(message: Message, bytes: ByteString)

  val samples = List(
    // 000: UNKNOWN_TYPE
    (List((MessageTypeTag, Some(UNKNOWN_TYPE)), (RawBytes, ByteString(1,2,3,4,5,6,7,8,9,10))),
      ByteString(0,0,0,0, 1,2,3,4,5,6,7,8,9,10)),

    // 001: FAILURE_CONF
    (List((MessageTypeTag, Some(FAILURE_CONF)), (InvokeID, 0x01020304),
      (StatusCodeTag, Some(StatusCode.INVALID_MONITOR_STATUS))),
      ByteString(0,0,0,1, 1,2,3,4, 0,0,0,97)),

    // 002: FAILURE_EVENT
    (List((MessageTypeTag, Some(FAILURE_EVENT)),
      (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE))),
      ByteString(0,0,0,2, 0,0,0,17)),

    // 003: OPEN_REQ
    (List((MessageTypeTag, Some(OPEN_REQ)), (InvokeID, 0x04030201),
      (VersionNumber, ProtocolVersion), (IdleTimeout, NoIdleTimeout), (PeripheralID, 0x02030405),
      (ServiceRequested, BitSet.empty + CtiServiceMask.ALL_EVENTS),
      (CallMsgMask, BitSet.empty + CallEventMessageMask.BEGIN_CALL + CallEventMessageMask.END_CALL),
      (AgentStateMaskTag, BitSet.empty + AgentStateMask.AGENT_AVAILABLE + AgentStateMask.AGENT_HOLD),
      (ConfigMsgMask, BitSet.empty), (Reserved1, 0), (Reserved2, 0), (Reserved3, 0),
      (CLIENT_ID, "ClientID"), (CLIENT_PASSWORD, ByteString()), (CLIENT_SIGNATURE, "ClientSignature"),
      (AGENT_EXTENSION, "3001"),(AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (APP_PATH_ID, 0x03040506)),
      ByteString(0,0,0,3, 4,3,2,1, 0,0,0,ProtocolVersion, 0xff,0xff,0xff,0xff, 2,3,4,5, 0,0,0,0x10,
        0,0,0x60,0, 0,0,0x02,0x08, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,
        1,9,0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x49, 0x44,0,
        2,0,
        3,16,0x43, 0x6c, 0x69, 0x65, 0x6e, 0x74, 0x53, 0x69, 0x67, 0x6e, 0x61, 0x74, 0x75, 0x72, 0x65,0,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        97,4,3,4,5,6)),

    // 004: OPEN_CONF
    (List((MessageTypeTag, Some(OPEN_CONF)), (InvokeID, 0x01020304),
      (ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS), (MonitorID, 0x02030405),
      (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN + PGStatusCode.LIMITED_FUNCTION),
      (ICMCentralControllerTime, 0x04050607), (PeripheralOnline, true),
      (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)), (AgentStateTag, Some(AgentState.AVAILABLE)),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"), (NUM_PERIPHERALS, 0x0506.toShort),
      (MULTI_LINE_AGENT_CONTROL, false)),
      ByteString(0,0,0,4, 1,2,3,4, 0,0,0,0x10, 2,3,4,5, 0,0,0,0x13, 4,5,6,7, 0,1, 0,17, 0,3,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        228,2,5,6, 224,2,0,0)),

    // 005: HEARTBEAT_REQ
    (List((MessageTypeTag, Some(HEARTBEAT_REQ)), (InvokeID, 0x02030405)),
      ByteString(0,0,0,5, 2,3,4,5)),

    // 006: HEARTBEAT_CONF
    (List((MessageTypeTag, Some(HEARTBEAT_CONF)), (InvokeID, 0x03040607)),
      ByteString(0,0,0,6, 3,4,6,7)),

    // 007: CLOSE_REQ
    (List((MessageTypeTag, Some(CLOSE_REQ)), (InvokeID, 0x04050607),
      (StatusCodeTag, Some(StatusCode.INVALID_REQUEST_TYPE))),
      ByteString(0,0,0,7, 4,5,6,7, 0,0,0,98)),

    // 008: CLOSE_CONF
    (List((MessageTypeTag, Some(CLOSE_CONF)), (InvokeID, 0x03040506)),
      ByteString(0,0,0,8, 3,4,5,6)),

    // 009: CALL_DELIVERED_EVENT
    (List((MessageTypeTag, Some(CALL_DELIVERED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (LineHandle, 0x0304: Short), (LineTypeTag, Some(LineType.SUPERVISOR)),
      (ServiceNumber, 0x04050607), (ServiceID, 0x05060708),
      (SkillGroupNumber, 0x04050607), (SkillGroupID, 0x05060708), (SkillGroupPriority, 0x0607: Short),
      (AlertingDeviceType, Some(DeviceIDType.AGENT_DEVICE)),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LastRedirectDeviceType, Some(DeviceIDType.CTI_PORT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.NONE)),
      (NumNamedVariables, 0x0405: Short), (NumNamedArrays, 0x0506: Short),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (ALERTING_DEVID, "AlertingDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"), (CALLED_DEVID, "CalledDeviceID"),
      (LAST_REDIRECT_DEVID, "LastRedirectDeviceID"),
      (TRUNK_NUMBER, 0x01020304), (TRUNK_GROUP_NUMBER, 0x02030405), (SECONDARY_CONNECTION_CALL_ID, 0x03040506),
      (ANI, "09012345678"), (ANI_II, ""), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)),
      (DNIS, "0398765432"), (DIALED_NUMBER, "0120123123"), (CED, "321"),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"), (CALL_WRAPUP_DATA, "Wrapup"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal"))),
      ByteString(0,0,0,9, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 3,4, 0,4,
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
        83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0)),

    // 010: CALL_ESTABLISHED_EVENT
    (List((MessageTypeTag, Some(CALL_ESTABLISHED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (LineHandle, 0x0405: Short), (LineTypeTag, Some(LineType.SUPERVISOR)), (ServiceNumber, 0x05060708),
      (ServiceID, 0x06070809), (SkillGroupNumber, 0x0708090a), (SkillGroupID, 0x08090a0b),
      (SkillGroupPriority, 0x090a: Short), (AnsweringDeviceType, Some(DeviceIDType.AGENT_DEVICE)),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LastRedirectDeviceType, Some(DeviceIDType.CTI_PORT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (ANSWERING_DEVID, "AnsweringDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"), (CALLED_DEVID, "CalledDeviceID"),
      (LAST_REDIRECT_DEVID, "LastRedirectDeviceID"),
      (TRUNK_NUMBER, 0x01020304), (TRUNK_GROUP_NUMBER, 0x02030405)),
      ByteString(0,0,0,10, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 4,5, 0,4, 5,6,7,8, 6,7,8,9,
        7,8,9,10, 8,9,10,11, 9,10, 0,76, 0,75, 0,74, 0,73, 0,3, 0,22,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        30,18,0x41,0x6e,0x73,0x77,0x65,0x72,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        29,21,0x4c,0x61,0x73,0x74,0x52,0x65,0x64,0x69,0x72,0x65,0x63,0x74,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        121,4,1,2,3,4,
        122,4,2,3,4,5)),

    // 011: CALL_HELD_EVENT
    (List((MessageTypeTag, Some(CALL_HELD_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (HoldingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (LocalConnectionStateTag, Some(LocalConnectionState.INITIATE)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (HOLDING_DEVID, "HoldingDeviceID")),
      ByteString(0,0,0,11, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 0,75, 0,1, 0,22,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        31,16,0x48,0x6f,0x6c,0x64,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 012: CALL_RETRIEVED_EVENT
    (List((MessageTypeTag, Some(CALL_RETRIEVED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (RetrievingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (LocalConnectionStateTag, Some(LocalConnectionState.INITIATE)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (RETRIEVING_DEVID, "RetrievingDeviceID")),
      ByteString(0,0,0,12, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 0,75, 0,1, 0,22,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        32,19,0x52,0x65,0x74,0x72,0x69,0x65,0x76,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 013: CALL_CLEARED_EVENT
    (List((MessageTypeTag, Some(CALL_CLEARED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.CALL_CANCELLED)),
      (CONNECTION_DEVID, "ConnectionDeviceID")),
      ByteString(0,0,0,13, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 0,0, 0,5,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 014: CALL_CONNECTION_CLEARED_EVENT
    (List((MessageTypeTag, Some(CALL_CONNECTION_CLEARED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (ReleasingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.CALL_CANCELLED)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (RELEASING_DEVID, "ReleasingDeviceID")),
      ByteString(0,0,0,14, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 0,75, 0,0, 0,5,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        33,18,0x52,0x65,0x6c,0x65,0x61,0x73,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 015: CALL_ORIGINATED_EVENT
    (List((MessageTypeTag, Some(CALL_ORIGINATED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (LineHandle, 0x0405: Short), (LineTypeTag, Some(LineType.SUPERVISOR)), (ServiceNumber, 0x05060708),
      (ServiceID, 0x06070809), (SkillGroupNumber, 0x0708090a), (SkillGroupID, 0x08090a0b),
      (SkillGroupPriority, 0x090a: Short),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"), (CALLED_DEVID, "CalledDeviceID")),
      ByteString(0,0,0,15, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 4,5, 0,4, 5,6,7,8, 6,7,8,9,
        7,8,9,10, 8,9,10,11, 9,10, 0,75, 0,74, 0,3, 0,22,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 016: CALL_FAILED_EVENT
    (List((MessageTypeTag, Some(CALL_FAILED_EVENT)),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (FailingDeviceType, Some(DeviceIDType.EXTERNAL)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.INITIATE)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (FAILING_DEVID, "FailingDeviceID"), (CALLED_DEVID, "CalledDeviceID")),
      ByteString(0,0,0,16, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 0,75, 0,74, 0,1, 0,22,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        34,16,0x46,0x61,0x69,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 017: CALL_CONFERENCED_EVENT
    (List((MessageTypeTag, Some(CALL_CONFERENCED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (PrimaryDeviceIDType, Some(DeviceIDType.AGENT_DEVICE)), (PrimaryCallID, 0x06070809),
      (LineHandle, 0x0304: Short), (LineTypeTag, Some(LineType.SUPERVISOR)),
      (SkillGroupNumber, 0x04050607), (SkillGroupID, 0x05060708), (SkillGroupPriority, 0x0607: Short),
      (NumParties, 0x0708: Short), (SecondaryDeviceIDType, Some(DeviceIDType.AGENT_DEVICE)),
      (SecondaryCallID, 0x0708090a), (ControllerDeviceType, Some(DeviceIDType.AGENT_DEVICE)),
      (AddedPartyDeviceType, Some(DeviceIDType.TRUNK_IDENTIFIER)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.PARK)),
      (PRIMARY_DEVID, "PrimaryDeviceID"), (SECONDARY_DEVID, "SecondaryDeviceID"),
      (CONTROLLER_DEVID, "ControllerDeviceID"), (ADDED_PARTY_DEVID, "AddedPartyDeviceID"),
      (PARTY_CALLID, 0x08090a0b), (PARTY_DEVID_TYPE, Some(DeviceIDType.EXTERNAL)),
      (PARTY_DEVID, "ConnectedPartyDeviceID")),
      ByteString(0,0,0,17, 1,2,3,4, 2,3,4,5, 0,17, 0,76, 6,7,8,9, 3,4, 0,4, 4,5,6,7, 5,6,7,8, 6,7,
        7,8, 0,76, 7,8,9,10, 0,76, 0,70, 0,3, 0,25,
        35,16,0x50,0x72,0x69,0x6d,0x61,0x72,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        36,18,0x53,0x65,0x63,0x6f,0x6e,0x64,0x61,0x72,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        37,19,0x43,0x6f,0x6e,0x74,0x72,0x6f,0x6c,0x6c,0x65,0x72,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        38,19,0x41,0x64,0x64,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        39,4,8,9,10,11,
        40,2,0,75,
        41,23,
        0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,
        0x49,0x44,0)),

    // 018: CALL_TRANSFERRED_EVENT
    (List((MessageTypeTag, Some(CALL_TRANSFERRED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (PrimaryDeviceIDType, Some(DeviceIDType.AGENT_DEVICE)), (PrimaryCallID, 0x06070809),
      (LineHandle, 0x0304: Short), (LineTypeTag, Some(LineType.SUPERVISOR)),
      (SkillGroupNumber, 0x04050607), (SkillGroupID, 0x05060708), (SkillGroupPriority, 0x0607: Short),
      (NumParties, 0x0708: Short), (SecondaryDeviceIDType, Some(DeviceIDType.AGENT_DEVICE)),
      (SecondaryCallID, 0x0708090a), (TransferringDeviceType, Some(DeviceIDType.AGENT_DEVICE)),
      (TransferredDeviceType, Some(DeviceIDType.TRUNK_IDENTIFIER)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.PARK)),
      (PRIMARY_DEVID, "PrimaryDeviceID"), (SECONDARY_DEVID, "SecondaryDeviceID"),
      (TRANSFERRING_DEVID, "TransferringDeviceID"), (TRANSFERRED_DEVID, "TransferredDeviceID"),
      (PARTY_CALLID, 0x08090a0b), (PARTY_DEVID_TYPE, Some(DeviceIDType.EXTERNAL)),
      (PARTY_DEVID, "ConnectedPartyDeviceID")),
      ByteString(0,0,0,18, 1,2,3,4, 2,3,4,5, 0,17, 0,76, 6,7,8,9, 3,4, 0,4, 4,5,6,7, 5,6,7,8, 6,7,
        7,8, 0,76, 7,8,9,10, 0,76, 0,70, 0,3, 0,25,
        35,16,0x50,0x72,0x69,0x6d,0x61,0x72,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        36,18,0x53,0x65,0x63,0x6f,0x6e,0x64,0x61,0x72,0x79,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        42,21,0x54,0x72,0x61,0x6e,0x73,0x66,0x65,0x72,0x72,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        43,20,0x54,0x72,0x61,0x6e,0x73,0x66,0x65,0x72,0x72,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        39,4,8,9,10,11,
        40,2,0,75,
        41,23,
        0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,
        0x49,0x44,0)),

    // 019: CALL_DIVERTED_EVENT
    (List((MessageTypeTag, Some(CALL_DIVERTED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (ServiceNumber, 0x04050607), (ServiceID, 0x05060708),
      (DivertingDeviceType, Some(DeviceIDType.QUEUE)), (CalledDeviceType, Some(DeviceIDType.QUEUE)),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.NONE)),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (DIVERTING_DEVID, "DivertingDeviceID"),
      (CALLED_DEVID, "CalledDeviceID")),
      ByteString(0,0,0,19, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9,
        4,5,6,7, 5,6,7,8, 0,77, 0,77, 0,0, 0xff,0xff,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        44,18,0x44,0x69,0x76,0x65,0x72,0x74,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 020: CALL_SERVICE_INITIATED_EVENT
    (List((MessageTypeTag, Some(CALL_SERVICE_INITIATED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (LineHandle, 0x0405: Short), (LineTypeTag, Some(LineType.SUPERVISOR)), (ServiceNumber, 0x05060708),
      (ServiceID, 0x06070809), (SkillGroupNumber, 0x0708090a), (SkillGroupID, 0x08090a0b),
      (SkillGroupPriority, 0x090a: Short),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (CALLING_DEVID, "CallingDeviceID")),
      ByteString(0,0,0,20, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 4,5, 0,4, 5,6,7,8, 6,7,8,9,
        7,8,9,10, 8,9,10,11, 9,10, 0,75, 0,3, 0,22,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 021: CALL_QUEUED_EVENT
    (List((MessageTypeTag, Some(CALL_QUEUED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (ServiceNumber, 0x04050607), (ServiceID, 0x05060708),
      (QueueDeviceType, Some(DeviceIDType.QUEUE)),
      (CallingDeviceType, Some(DeviceIDType.EXTERNAL)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LastRedirectDeviceType, Some(DeviceIDType.CTI_PORT)),
      (NumQueued, 0x0607: Short), (NumSkillGroups, 0x0708: Short),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.NONE)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (QUEUE_DEVID, "QueueDeviceID"),
      (CALLING_DEVID, "CallingDeviceID"), (CALLED_DEVID, "CalledDeviceID"),
      (LAST_REDIRECT_DEVID, "LastRedirectDeviceID"),
      (SKILL_GROUP_NUMBER, 0x08090a0b),
      (SKILL_GROUP_ID, 0x090a0b0c),
      (SKILL_GROUP_PRIORITY, 0x0a0b: Short)),
      ByteString(0,0,0,21, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9,
        4,5,6,7, 5,6,7,8, 0,77, 0,75, 0,74, 0,73, 6,7, 7,8, 0,0, 0xff,0xff,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        45,14,0x51,0x75,0x65,0x75,0x65,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        27,16,0x43,0x61,0x6c,0x6c,0x69,0x6e,0x67,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        29,21,0x4c,0x61,0x73,0x74,0x52,0x65,0x64,0x69,0x72,0x65,0x63,0x74,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        62,4,8,9,10,11,
        63,4,9,10,11,12,
        64,2,10,11)),

    // 022: CALL_TRANSLATION_ROUTE_EVENT
    (List((MessageTypeTag, Some(CALL_TRANSLATION_ROUTE_EVENT)),
      (NumNamedVariables, 0x0405: Short), (NumNamedArrays, 0x0506: Short),
      (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)),
      (DNIS, "0398765432"), (DIALED_NUMBER, "0120123123"), (CED, "321"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a), (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x08090a0b),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal"))),
      ByteString(0,0,0,22, 4,5, 5,6,
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
        83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0)),

    // 023: BEGIN_CALL_EVENT
    (List((MessageTypeTag, Some(BEGIN_CALL_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (NumCTIClients, 0x0304: Short), (NumNamedVariables, 0x0405: Short), (NumNamedArrays, 0x0506: Short),
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
      (CALL_REFERENCE_ID, ByteString(9,8,7,6,5,4,3,2,1,0))),
      ByteString(0,0,0,23, 1,2,3,4, 2,3,4,5, 0,17, 3,4, 4,5, 5,6, 0,2, 0,0, 6,7,8,9, 0,13,
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
        223,10,9,8,7,6,5,4,3,2,1,0)),

    // 024: END_CALL_EVENT
    (List((MessageTypeTag, Some(END_CALL_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (CONNECTION_DEVID, "ConnectionDeviceID")),
      ByteString(0,0,0,24, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 025: CALL_DATA_UPDATE_EVENT
    (List((MessageTypeTag, Some(CALL_DATA_UPDATE_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (NumCTIClients, 0x0304: Short), (NumNamedVariables, 0x0405: Short), (NumNamedArrays, 0x0506: Short),
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
      (CALL_REFERENCE_ID, ByteString(9,8,7,6,5,4,3,2,1,0))),
      ByteString(0,0,0,25, 1,2,3,4, 2,3,4,5, 0,17, 3,4, 4,5, 5,6, 0,2, 0,0, 6,7,8,9,
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
        223,10,9,8,7,6,5,4,3,2,1,0)),

    // 026: SET_CALL_DATA_REQ
    (List((MessageTypeTag, Some(SET_CALL_DATA_REQ)), (InvokeID, 0x07080809), (PeripheralID, 0x09080706),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x08070605),
      (NumNamedVariables, 0x0706: Short), (NumNamedArrays, 0x0605: Short), (CallTypeTag, Some(CallType.PREROUTE_ACD_IN)),
      (CalledPartyDisposition, Some(DispositionCodeValue.Disconnect_drop_handled_primary_route)),
      (CampaignID, 0x05040302), (QueryRuleID, 0x04030201),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)), (CED, "321"),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"), (CALL_WRAPUP_DATA, "Wrapup"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal")),
      (CUSTOMER_PHONE_NUMBER, "CustPhoneNum"), (CUSTOMER_ACCOUNT_NUMBER, "CustAcctNum"),
      (ROUTER_CALL_KEY_DAY, 0x01020304), (ROUTER_CALL_KEY_CALLID, 0x02030405),
      (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x03040506), (CALL_ORIGINATED_FROM, 0x44:Byte)),
      ByteString(0,0,0,26, 7,8,8,9, 9,8,7,6, 0,0, 8,7,6,5, 7,6, 6,5, 0,2, 0,13, 5,4,3,2, 4,3,2,1,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        8,12,0x30, 0x39, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,0,
        9,10,0,1,2,3,4,5,6,7,8,9,
        12,4,0x33, 0x32, 0x31,0,
        13,4,0x43,0x56,0x31,0, 14,4,0x43,0x56,0x32,0, 15,4,0x43,0x56,0x33,0, 16,4,0x43,0x56,0x34,0,
        17,4,0x43,0x56,0x35,0, 18,4,0x43,0x56,0x36,0, 19,4,0x43,0x56,0x37,0, 20,4,0x43,0x56,0x38,0,
        21,4,0x43,0x56,0x39,0, 22,5,0x43,0x56,0x31,0x30,0,
        46,7,0x57,0x72,0x61,0x70,0x75,0x70,0,
        82,14,0x45,0x43,0x43,0x56,0x61,0x72,0,0x45,0x43,0x43,0x56,0x61,0x6c,0,
        83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0,
        95,13,0x43, 0x75, 0x73, 0x74, 0x50, 0x68, 0x6f, 0x6e, 0x65, 0x4e, 0x75, 0x6d,0,
        96,12,0x43, 0x75, 0x73, 0x74, 0x41, 0x63, 0x63, 0x74, 0x4e, 0x75, 0x6d,0,
        72,4,1,2,3,4, 73,4,2,3,4,5, 110,4,3,4,5,6,
        232,1,0x44)),

    // 027: SET_CALL_DATA_CONF
    (List((MessageTypeTag, Some(SET_CALL_DATA_CONF)), (InvokeID, 0x07080809)),
      ByteString(0,0,0,27, 7,8,8,9)),

    // 028: RELEASE_CALL_REQ
    (List((MessageTypeTag, Some(RELEASE_CALL_REQ)), (InvokeID, 0x0809090a), (PeripheralID, 0x090a0a0b),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x0a0b0b0c),
      (CONNECTION_DEVID, "ConnectionDeviceID")),
      ByteString(0,0,0,28, 8,9,9,10, 9,10,10,11, 0,0, 10,11,11,12,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 029: RELEASE_CALL_CONF
    (List((MessageTypeTag, Some(RELEASE_CALL_CONF)), (InvokeID, 0x05060708)),
      ByteString(0,0,0,29, 5,6,7,8)),

    // 030: AGENT_STATE_EVENT
    (List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (SessionID, 0x03040506),
      (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
      (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809: Short),
      (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a: Short), (MRDID, 0x090a0b0c),
      (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
      (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706: Short),
      (CLIENT_SIGNATURE, "ClientSignature"),
      (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
      (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
      (DIRECTION, Some(CallDirection.In)),
      (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a: Short),
      (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER))),
      ByteString(0,0,0,30, 1,2,3,4, 2,3,4,5, 3,4,5,6, 0,17, 0,7, 4,5,6,7,
        6,7,8,9, 7,8,9,10, 8,9, 0,10, 9,10, 9,10,11,12, 10,11,12,13, 0,0, 9,8,7,6, 8,7,6,5, 0,0,0,1, 7,6,
        3,16,0x43,0x6c,0x69,0x65,0x6e,0x74,0x53,0x69,0x67,0x6e,0x61,0x74,0x75,0x72,0x65,0,
        5,5,0x31,0x30,0x30,0x31,0, 4,5,0x33,0x30,0x30,0x31,0, 6,5,0x33,0x30,0x30,0x31,0,
        150,4,6,5,4,3, 123,2,0,4, 244,4,0,0,0,1,
        62,4,7,8,9,10, 63,4,8,9,10,11, 64,2,9,10, 65,2,0,7)),

    // 031: SYSTEM_EVENT
    (List((MessageTypeTag, Some(SYSTEM_EVENT)),
      (PGStatus, BitSet.empty + PGStatusCode.OPC_DOWN + PGStatusCode.CC_DOWN),
      (ICMCentralControllerTime, 0x01020304), (SystemEventIDTag, Some(SystemEventID.TEXT_FYI)),
      (SystemEventArg1, 0x02030405), (SystemEventArg2, 0x03040506),
      (SystemEventArg3, 0x04050607), (EventDeviceType, Some(DeviceIDType.TRUNK_IDENTIFIER)),
      (TEXT, "SystemEventText"), (EVENT_DEVICE_ID, "EventDeviceID")),
      ByteString(0,0,0,31, 0,0,0,3, 1,2,3,4, 0,0,0,5, 2,3,4,5, 3,4,5,6, 4,5,6,7, 0,70,
        7,16,0x53,0x79,0x73,0x74,0x65,0x6d,0x45,0x76,0x65,0x6e,0x74,0x54,0x65,0x78,0x74,0,
        206,14,0x45,0x76,0x65,0x6e,0x74,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 032: CLIENT_EVENT_REPORT_REQ
    (List((MessageTypeTag, Some(CLIENT_EVENT_REPORT_REQ)), (InvokeID, 0x03040505),
      (ClientEventStateTag, Some(ClientEventReportState.Warning)),
      (OBJECT_NAME, "ObjectName"), (TEXT, "Text")),
      ByteString(0,0,0,32, 3,4,5,5, 0,1,
        66,11,0x4f, 0x62, 0x6a, 0x65, 0x63, 0x74, 0x4e, 0x61, 0x6d, 0x65,0,
        7,5,0x54, 0x65, 0x78, 0x74,0)),

    // 033: CLIENT_EVENT_REPORT_CONF
    (List((MessageTypeTag, Some(CLIENT_EVENT_REPORT_CONF)), (InvokeID, 0x03040505)),
      ByteString(0,0,0,33, 3,4,5,5)),

    // 034: CALL_REACHED_NETWORK_EVENT
    (List((MessageTypeTag, Some(CALL_REACHED_NETWORK_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)), (ConnectionCallID, 0x03040506),
      (LineHandle, 0x0405: Short), (LineTypeTag, Some(LineType.SUPERVISOR)),
      (TrunkUsedDeviceType, Some(DeviceIDType.TRUNK_IDENTIFIER)), (CalledDeviceType, Some(DeviceIDType.ROUTE_POINT)),
      (LocalConnectionStateTag, Some(LocalConnectionState.CONNECT)), (EventCauseTag, Some(EventCause.NEW_CALL)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (TRUNK_USED_DEVID, "TrunkUsedDeviceID"), (CALLED_DEVID, "CalledDeviceID"),
      (TRUNK_NUMBER, 0x04050607), (TRUNK_GROUP_NUMBER, 0x05060708)),
      ByteString(0,0,0,34, 1,2,3,4, 2,3,4,5, 0,17, 0,1, 3,4,5,6, 4,5, 0,4,
        0,70, 0,74, 0,3, 0,22,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        48,18,0x54,0x72,0x75,0x6e,0x6b,0x55,0x73,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        28,15,0x43,0x61,0x6c,0x6c,0x65,0x64,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        121,4,4,5,6,7, 122,4,5,6,7,8)),

    // 035: CONTROL_FAILURE_CONF
    (List((MessageTypeTag, Some(CONTROL_FAILURE_CONF)), (InvokeID, 0x01020304),
      (FailureCode, Some(ControlFailureCode.REQUEST_TIMEOUT_REJECTION)), (PeripheralErrorCode, 0x02030405)),
      ByteString(0,0,0,35, 1,2,3,4, 0,78, 2,3,4,5)),

    // 036: QUERY_AGENT_STATE_REQ
    (List((MessageTypeTag, Some(QUERY_AGENT_STATE_REQ)), (InvokeID, 0x01020304),
      (PeripheralID, 0x02030405), (MRDID, 0x03040506), (ICMAgentID, 0x04050607),
      (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001")),
      ByteString(0,0,0,36, 1,2,3,4, 2,3,4,5, 3,4,5,6, 4,5,6,7,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0)),

    // 037: QUERY_AGENT_STATE_CONF
    (List((MessageTypeTag, Some(QUERY_AGENT_STATE_CONF)), (InvokeID, 0x01020304),
      (AgentStateTag, Some(AgentState.HOLD)), (NumSkillGroups, 0x0203: Short), (MRDID, 0x03040506),
      (NumTasks, 0x04050607), (AgentMode, false), (MaxTaskLimit, 0x05060708), (ICMAgentID, 0x06070809),
      (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.APPLICATION_AVAILABLE)),
      (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"), (SKILL_GROUP_NUMBER, 0x0708090a),
      (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a: Short),
      (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER))),
      ByteString(0,0,0,37, 1,2,3,4, 0,10, 2,3, 3,4,5,6, 4,5,6,7, 0,0, 5,6,7,8, 6,7,8,9, 0,0,0,2,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        62,4,7,8,9,10, 63,4,8,9,10,11, 64,2,9,10, 65,2,0,7)),

    // 038: SET_AGENT_STATE_REQ
    (List((MessageTypeTag, Some(SET_AGENT_STATE_REQ)), (InvokeID, 0x06070809),
      (PeripheralID, 0x05060708), (AgentStateTag, Some(AgentState.LOGIN)),
      (AgentWorkModeTag, Some(AgentWorkMode.AUTO_IN)), (NumSkillGroups, 0x0102: Short),
      (EventReasonCode, 0x0203: Short), (ForcedFlagTag, Some(ForcedFlag.TRUE)), (AgentServiceReq, BitSet.empty),
      (AGENT_INSTRUMENT, "3001"), (AGENT_ID, "1001"), (AGENT_PASSWORD, "Password"), (POSITION_ID, "PositionID"),
      (SUPERVISOR_ID, "SupervisorID"), (SKILL_GROUP_NUMBER, 0x04050607), (SKILL_GROUP_PRIORITY, 0x0304: Short)),
      ByteString(0,0,0,38, 6,7,8,9, 5,6,7,8, 0,0, 0,1, 1,2, 2,3, 1, 0,0,0,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        49,9,0x50, 0x61, 0x73, 0x73, 0x77, 0x6f, 0x72, 0x64,0,
        68,11,0x50, 0x6f, 0x73, 0x69, 0x74, 0x69, 0x6f, 0x6e, 0x49, 0x44,0,
        69,13,0x53, 0x75, 0x70, 0x65, 0x72, 0x76, 0x69, 0x73, 0x6f, 0x72, 0x49, 0x44,0,
        62,4,4,5,6,7, 64,2,3,4)),

    // 039: SET_AGENT_STATE_CONF"
    (List((MessageTypeTag, Some(SET_AGENT_STATE_CONF)), (InvokeID, 0x06070809)),
      ByteString(0,0,0,39, 6,7,8,9)),

    // 041: ALTERNATE_CALL_CONF
    (List((MessageTypeTag, Some(ALTERNATE_CALL_CONF)), (InvokeID, 0x01020305)),
      ByteString(0,0,0,41, 1,2,3,5)),

    // 043: ANSWER_CALL_CONF
    (List((MessageTypeTag, Some(ANSWER_CALL_CONF)), (InvokeID, 0x01020405)),
      ByteString(0,0,0,43, 1,2,4,5)),

    // 045: CLEAR_CALL_CONF
    (List((MessageTypeTag, Some(CLEAR_CALL_CONF)), (InvokeID, 0x03030405)),
      ByteString(0,0,0,45, 3,3,4,5)),

    // 047: CLEAR_CONNECTION_CONF
    (List((MessageTypeTag, Some(CLEAR_CONNECTION_CONF)), (InvokeID, 0x03040405)),
      ByteString(0,0,0,47, 3,4,4,5)),

    // 049: CONFERENCE_CALL_CONF
    (List((MessageTypeTag, Some(CONFERENCE_CALL_CONF)), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.DYNAMIC)),
      (NumParties, 0x0304: Short), (LineHandle, 0x0405: Short), (LineTypeTag, Some(LineType.INSIDE)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID"), (PARTY_CALLID, 0x03040506),
      (PARTY_DEVID_TYPE, Some(DeviceIDType.AGENT_DEVICE)), (PARTY_DEVID, "ConnectedPartyDeviceID")),
      ByteString(0,0,0,49, 1,2,3,4, 2,3,4,5, 0,1, 3,4, 4,5, 0,2,
        47,22,
        0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
        0x44,0,
        39,4,3,4,5,6, 40,2,0,76,
        41,23,
        0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,
        0x49,0x44,0)),

    // 051: CONSULTATION_CALL_CONF
    (List((MessageTypeTag, Some(CONSULTATION_CALL_CONF)), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)),
      (LineHandle, 0x0304: Short), (LineTypeTag, Some(LineType.OUTBOUND_ACD)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID")),
      ByteString(0,0,0,51, 1,2,3,4, 2,3,4,5, 0,0, 3,4, 0,1,
        47,22,
        0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
        0x44,0)),

    // 053: DEFLECT_CALL_CONF
    (List((MessageTypeTag, Some(DEFLECT_CALL_CONF)), (InvokeID, 0x03040507)),
      ByteString(0,0,0,53, 3,4,5,7)),

    // 055: HOLD_CALL_CONF
    (List((MessageTypeTag, Some(HOLD_CALL_CONF)), (InvokeID, 0x03050607)),
      ByteString(0,0,0,55, 3,5,6,7)),

    // 057: MAKE_CALL_CONF
    (List((MessageTypeTag, Some(MAKE_CALL_CONF)), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)),
      (LineHandle, 0x0304: Short), (LineTypeTag, Some(LineType.OUTBOUND_ACD)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID")),
      ByteString(0,0,0,57, 1,2,3,4, 2,3,4,5, 0,0, 3,4, 0,1,
        47,22,
        0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
        0x44,0)),

    // 059: MAKE_PREDICTIVE_CALL_CONF
    (List((MessageTypeTag, Some(MAKE_PREDICTIVE_CALL_CONF)), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)),
      (LineHandle, 0x0304: Short), (LineTypeTag, Some(LineType.OUTBOUND_ACD)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID")),
      ByteString(0,0,0,59, 1,2,3,4, 2,3,4,5, 0,0, 3,4, 0,1,
        47,22,
        0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
        0x44,0)),

    // 061: RECONNECT_CALL_CONF
    (List((MessageTypeTag, Some(RECONNECT_CALL_CONF)), (InvokeID, 0x05060607)),
      ByteString(0,0,0,61, 5,6,6,7)),

    // 063: RETRIEVE_CALL_CONF
    (List((MessageTypeTag, Some(RETRIEVE_CALL_CONF)), (InvokeID, 0x05060709)),
      ByteString(0,0,0,63, 5,6,7,9)),

    // 065: TRANSFER_CALL_CONF
    (List((MessageTypeTag, Some(TRANSFER_CALL_CONF)), (InvokeID, 0x01020304),
      (NewConnectionCallID, 0x02030405), (NewConnectionDeviceIDType, Some(ConnectionDeviceIDType.STATIC)),
      (NumParties, 0x0304: Short), (LineHandle, 0x0405: Short), (LineTypeTag, Some(LineType.INBOUND_ACD)),
      (NEW_CONNECTION_DEVID, "NewConnectionDeviceID"), (PARTY_CALLID, 0x03040506),
      (PARTY_DEVID_TYPE, Some(DeviceIDType.AGENT_DEVICE)), (PARTY_DEVID, "ConnectedPartyDeviceID")),
      ByteString(0,0,0,65, 1,2,3,4, 2,3,4,5, 0,0, 3,4, 4,5, 0,0,
        47,22,
        0x4e,0x65,0x77,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,
        0x44,0,
        39,4,3,4,5,6, 40,2,0,76,
        41,23,
        0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x65,0x64,0x50,0x61,0x72,0x74,0x79,0x44,0x65,0x76,0x69,0x63,0x65,
        0x49,0x44,0)),

    // 079: QUERY_DEVICE_INFO_CONF
    (List((MessageTypeTag, Some(QUERY_DEVICE_INFO_CONF)), (InvokeID, 0x01020304),
      (PeripheralTypeTag, Some(PeripheralType.VRU)), (TypeOfDeviceTag, Some(TypeOfDevice.TRUNK_GROUP)),
      (ClassOfDeviceTag, BitSet.empty + ClassOfDevice.VOICE), (NumLines, 0x0203: Short), (Reserved16, 0x0304: Short),
      (MaxActiveCalls, 0x0405: Short), (MaxHeldCalls, 0x0506: Short), (MaxDeviceInConference, 0x0607: Short),
      (MakeCallSetup, BitSet.empty + AgentStateMask.AGENT_LOGIN + AgentStateMask.AGENT_RESERVED),
      (TransferConferenceSetup, BitSet.empty + TransferConferenceSetupMask.CONF_SETUP_CONSULT_ANY),
      (CallEventsSupported, BitSet.empty + CallEventMessageMask.CALL_DELIVERED + CallEventMessageMask.CALL_CONFERENCED),
      (CallControlSupported, BitSet.empty + CallControlMask.QUERY_AGENT_STATE + CallControlMask.MAKE_CALL),
      (OtherFeaturesSupported, BitSet.empty + OtherFeatureMask.POST_ROUTE + OtherFeatureMask.UNIQUE_CONSULT_CALLID),
      (LINE_HANDLE, 0x0708: Short), (LINE_TYPE, Some(LineType.SUPERVISOR))),
      ByteString(0,0,0,79, 1,2,3,4, 0,8, 0,20, 0,0x80, 2,3, 3,4, 4,5, 5,6, 6,7,
        0,0,1,1, 0,0,0,2, 0,0,1,1, 0,0,4,1, 0,0,0,3, 70,2,7,8, 71,2,0,4)),

    // 083: SNAPSHOT_CALL_CONF
    (List((MessageTypeTag, Some(SNAPSHOT_CALL_CONF)), (InvokeID, 0x01020304),
      (CallTypeTag, Some(CallType.PREROUTE_ACD_IN)), (NumCTIClients, 0x0203: Short), (NumCallDevices, 0x0304: Short),
      (NumNamedVariables, 0x0405: Short), (NumNamedArrays, 0x0506: Short),
      (CalledPartyDisposition, Some(DispositionCodeValue.Abandoned_Ring)),
      (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)), (DNIS, "0398765432"),
      (DIALED_NUMBER, "0120123123"), (CED, "321"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"), (CALL_WRAPUP_DATA, "Wrapup"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal"))),
      ByteString(0,0,0,83, 1,2,3,4, 0,2, 2,3, 3,4, 4,5, 5,6, 0,3,
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
        83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0)),

    // 085 SNAPSHOT_DEVICE_CONF
    (List((MessageTypeTag, Some(SNAPSHOT_DEVICE_CONF)), (InvokeID, 0x01020304),
      (NumCalls, 0x0203: Short), (CALL_CONN_CALLID, 0x03040506),
      (CALL_CONN_DEVID_TYPE, Some(ConnectionDeviceIDType.STATIC)), (CALL_CONN_DEVID, "CallConnectionDeviceID"),
      (CALL_STATE, Some(LocalConnectionState.CONNECT)), (SILENT_MONITOR_STATUS, Some(SilentMonitorStatus.TARGET))),
      ByteString(0,0,0,85, 1,2,3,4, 2,3,
        56,4,3,4,5,6,
        57,2,0,0,
        58,23,
        0x43,0x61,0x6c,0x6c,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,
        0x49,0x44,0,
        75,2,0,3,
        218,2,0,2)),

    // 086: CALL_DEQUEUED_EVENT
    (List((MessageTypeTag, Some(CALL_DEQUEUED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (QueueDeviceType, Some(DeviceIDType.QUEUE)), (ServiceNumber, 0x04050607), (ServiceID, 0x05060708),
      (NumQueued, 0x0607: Short), (NumSkillGroups, 0x0708: Short),
      (LocalConnectionStateTag, Some(LocalConnectionState.NULL)), (EventCauseTag, Some(EventCause.NONE)),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (SKILL_GROUP_NUMBER, 0x08090a0b),
      (SKILL_GROUP_ID, 0x090a0b0c),
      (SKILL_GROUP_PRIORITY, 0x0a0b: Short)),
      ByteString(0,0,0,86, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 0,77,
        4,5,6,7, 5,6,7,8, 6,7, 7,8, 0,0, 0xff,0xff,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        62,4,8,9,10,11,
        63,4,9,10,11,12,
        64,2,10,11)),

    // 092: SEND_DTMF_SIGNAL_CONF
    (List((MessageTypeTag, Some(SEND_DTMF_SIGNAL_CONF)), (InvokeID, 0x05060809)),
      ByteString(0,0,0,92, 5,6,8,9)),

    // 094: MONITOR_START_CONF
    (List((MessageTypeTag, Some(MONITOR_START_CONF)), (InvokeID, 0x02030405),
      (MonitorID, 0x06070809)),
      ByteString(0,0,0,94, 2,3,4,5, 6,7,8,9)),

    // 096: MONITOR_STOP_CONF
    (List((MessageTypeTag, Some(MONITOR_STOP_CONF)), (InvokeID, 0x05050607)),
      ByteString(0,0,0,96, 5,5,6,7)),

    // 098: CHANGE_MONITOR_MASK_CONF
    (List((MessageTypeTag, Some(CHANGE_MONITOR_MASK_CONF)), (InvokeID, 0x02030405)),
      ByteString(0,0,0,98, 2,3,4,5)),

    // 099: CLIENT_SESSION_OPENED_EVENT
    (List((MessageTypeTag, Some(CLIENT_SESSION_OPENED_EVENT)), (SessionID, 0x01020304),
      (PeripheralID, 0x02030405),
      (ServiceGranted, BitSet.empty + CtiServiceMask.ALL_EVENTS),
      (CallMsgMask, BitSet.empty + CallEventMessageMask.CALL_DELIVERED + CallEventMessageMask.CALL_CONFERENCED),
      (AgentStateMaskTag, BitSet.empty + AgentStateMask.AGENT_LOGIN + AgentStateMask.AGENT_RESERVED),
      (ClientPort, 0x03040506),
      (CLIENT_ADDRESS, "ClientAddress"), (CLIENT_ID, "ClientID"), (CLIENT_SIGNATURE, "ClientSignature"),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6")),
      ByteString(0,0,0,99, 1,2,3,4, 2,3,4,5, 0,0,0,0x10, 0,0,1,1, 0,0,1,1, 3,4,5,6,
        81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
        1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
        3,16,0x43,0x6c,0x69,0x65,0x6e,0x74,0x53,0x69,0x67,0x6e,0x61,0x74,0x75,0x72,0x65,0,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)),

    // 100: CLIENT_SESSION_CLOSED_EVENT
    (List((MessageTypeTag, Some(CLIENT_SESSION_CLOSED_EVENT)), (SessionID, 0x01020304),
      (PeripheralID, 0x02030405), (StatusCodeTag, Some(StatusCode.UNSPECIFIED_FAILURE)), (ClientPort, 0x03040506),
      (CLIENT_ADDRESS, "ClientAddress"), (CLIENT_ID, "ClientID"), (CLIENT_SIGNATURE, "ClientSignature"),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6")),
      ByteString(0,0,0,100, 1,2,3,4, 2,3,4,5, 0,0,0,17, 3,4,5,6,
        81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
        1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
        3,16,0x43,0x6c,0x69,0x65,0x6e,0x74,0x53,0x69,0x67,0x6e,0x61,0x74,0x75,0x72,0x65,0,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)),

    // 102: SESSION_MONITOR_START_CONF
    (List((MessageTypeTag, Some(SESSION_MONITOR_START_CONF)), (InvokeID, 0x03040506),
      (MonitorID, 0x0708090a)),
      ByteString(0,0,0,102, 3,4,5,6, 7,8,9,10)),

    // 104: SESSION_MONITOR_STOP_CONF
    (List((MessageTypeTag, Some(SESSION_MONITOR_STOP_CONF)), (InvokeID, 0x05070809)),
      ByteString(0,0,0,104, 5,7,8,9)),

    // 105: AGENT_PRE_CALL_EVENT
    (List((MessageTypeTag, Some(AGENT_PRE_CALL_EVENT)), (MonitorID, 0x01020304),
      (NumNamedVariables, 0x0203: Short), (NumNamedArrays, 0x0304: Short), (ServiceNumber, 0x04050607),
      (ServiceID, 0x05060708), (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a),
      (SkillGroupPriority, 0x0809: Short), (MRDID, 0x090a0b0c),
      (AGENT_INSTRUMENT, "3001"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a), (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x08090a0b),
      (ANI, "09012345678"), (UUI, ByteString(0,1,2,3,4,5,6,7,8,9)),
      (DIALED_NUMBER, "0120123123"), (CED, "321"),
      (CALL_VAR_1, "CV1"), (CALL_VAR_2, "CV2"), (CALL_VAR_3, "CV3"), (CALL_VAR_4, "CV4"),
      (CALL_VAR_5, "CV5"), (CALL_VAR_6, "CV6"), (CALL_VAR_7, "CV7"), (CALL_VAR_8, "CV8"),
      (CALL_VAR_9, "CV9"), (CALL_VAR_10, "CV10"),
      (NAMED_VARIABLE, ("ECCVar", "ECCVal")), (NAMED_ARRAY, (1, "ECCArr", "ECCArrVal"))),
      ByteString(0,0,0,105, 1,2,3,4, 2,3, 3,4, 4,5,6,7, 5,6,7,8, 6,7,8,9, 7,8,9,10, 8,9, 9,10,11,12,
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
        83,18,1,0x45,0x43,0x43,0x41,0x72,0x72,0,0x45,0x43,0x43,0x41,0x72,0x72,0x56,0x61,0x6c,0)),

    // 106: AGENT_PRE_CALL_ABORT_EVENT
    (List((MessageTypeTag, Some(AGENT_PRE_CALL_ABORT_EVENT)), (MonitorID, 0x01020304),
      (MRDID, 0x02030405), (AGENT_INSTRUMENT, "3001"), (ROUTER_CALL_KEY_DAY, 0x06070809),
      (ROUTER_CALL_KEY_CALLID, 0x0708090a), (ROUTER_CALL_KEY_SEQUENCE_NUM, 0x08090a0b)),
      ByteString(0,0,0,106, 1,2,3,4, 2,3,4,5,
        6,5,0x33,0x30,0x30,0x31,0,
        72,4,6,7,8,9, 73,4,7,8,9,10, 110,4,8,9,10,11)),

    // 108: USER_MESSAGE_CONF
    (List((MessageTypeTag, Some(USER_MESSAGE_CONF)), (InvokeID, 0x07080a0b)),
      ByteString(0,0,0,108, 7,8,10,11)),

    // 109: USER_MESSAGE_EVENT
    (List((MessageTypeTag, Some(USER_MESSAGE_EVENT)),
      (ICMCentralControllerTime, 0x01020304), (Distribution, Some(DistributionValue.TEAM)),
      (CLIENT_ID, "ClientID"), (TEXT, "UserMessageEventText")),
      ByteString(0,0,0,109, 1,2,3,4, 0,2,
        1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
        7,21,0x55,0x73,0x65,0x72,0x4d,0x65,0x73,0x73,0x61,0x67,0x65,0x45,0x76,0x65,0x6e,0x74,0x54,0x65,0x78,0x74,0)),

    // 111: REGISTER_VARIABLES_CONF
    (List((MessageTypeTag, Some(REGISTER_VARIABLES_CONF)), (InvokeID, 0x05060707)),
      ByteString(0,0,0,111, 5,6,7,7)),

    // 116: RTP_STARTED_EVENT
    (List((MessageTypeTag, Some(RTP_STARTED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (ClientPort, 0x03040506), (RTPDirectionTag, Some(RTPDirection.BiDirectional)),
      (RTPTypeTag, Some(RTPType.Audio)), (BitRate, 0x04050607), (EchoCancellation, false),
      (PacketSize, 0x05060708), (PayloadType, 0x0607: Short),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (CLIENT_ADDRESS, "ClientAddress"),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"),
      (SENDING_ADDRESS, "SendingAddress"), (SENDING_PORT, "SendingPort"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6"), (SENDING_ADDRESS_IPV6, "SendingAddressIPv6")),
      ByteString(0,0,0,116, 1,2,3,4, 2,3,4,5, 3,4,5,6, 0,2, 0,0, 4,5,6,7, 0,0, 5,6,7,8, 6,7,
        0,0, 6,7,8,9,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        125,15,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
        126,12,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x50,0x6f,0x72,0x74,0,
        226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0,
        227,19,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)),

    // 117: RTP_STOPPED_EVENT
    (List((MessageTypeTag, Some(RTP_STOPPED_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (ClientPort, 0x03040506), (RTPDirectionTag, Some(RTPDirection.BiDirectional)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (CLIENT_ADDRESS, "ClientAddress"),
      (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
      (SENDING_ADDRESS, "SendingAddress"), (SENDING_PORT, "SendingPort"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6"), (SENDING_ADDRESS_IPV6, "SendingAddressIPv6")),
      ByteString(0,0,0,117, 1,2,3,4, 2,3,4,5, 3,4,5,6, 0,2, 0,0, 6,7,8,9,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        125,15,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
        126,12,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x50,0x6f,0x72,0x74,0,
        226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0,
        227,19,0x53,0x65,0x6e,0x64,0x69,0x6e,0x67,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)),

    // 119: SUPERVISOR_ASSIST_CONF
    (List((MessageTypeTag, Some(SUPERVISOR_ASSIST_CONF)), (InvokeID, 0x02030405),
      (ConnectionCallID, 0x01020304), (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)),
      (LineHandle, 0x0304: Short), (LineTypeTag, Some(LineType.DID)), (CONNECTION_DEVID, "ConnectionDeviceID")),
      ByteString(0,0,0,119, 2,3,4,5, 1,2,3,4, 0,1, 3,4, 0,8,
        25,19,
        0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 122: EMERGENCY_CALL_CONF
    (List((MessageTypeTag, Some(EMERGENCY_CALL_CONF)), (InvokeID, 0x02030405),
      (ConnectionCallID, 0x01020304), (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)),
      (LineHandle, 0x0304: Short), (LineTypeTag, Some(LineType.DID)), (CONNECTION_DEVID, "ConnectionDeviceID")),
      ByteString(0,0,0,122, 2,3,4,5, 1,2,3,4, 0,1, 3,4, 0,8,
        25,19,
        0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 123: EMERGENCY_CALL_EVENT
    (List((MessageTypeTag, Some(EMERGENCY_CALL_EVENT)), (PeripheralID, 0x01020304),
      (ConnectionCallID, 0x03040506), (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)),
      (SessionID, 0x02030405),
      (CONNECTION_DEVID, "ConnectionDeviceID"),
      (CLIENT_ID, "ClientID"), (CLIENT_ADDRESS, "ClientAddress"),
      (AGENT_EXTENSION, "3001"), (AGENT_ID, "1001"), (AGENT_INSTRUMENT, "3001"),
      (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6")),
      ByteString(0,0,0,123, 1,2,3,4, 3,4,5,6, 0,1, 2,3,4,5,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
        81,14,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0,
        4,5,0x33, 0x30, 0x30, 0x31,0,
        5,5,0x31, 0x30, 0x30, 0x31,0,
        6,5,0x33, 0x30, 0x30, 0x31,0,
        226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)),

    // 125: SUPERVISE_CALL_CONF
    (List((MessageTypeTag, Some(SUPERVISE_CALL_CONF)), (InvokeID, 0x01020304),
      (ConnectionCallID, 0x02030405), (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.DYNAMIC)),
      (CONNECTION_DEVID, "ConnectionDeviceID")),
      ByteString(0,0,0,125, 1,2,3,4, 2,3,4,5, 0,1,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 128: AGENT_TEAM_CONFIG_EVENT
    (List((MessageTypeTag, Some(AGENT_TEAM_CONFIG_EVENT)), (PeripheralID, 0x01020304),
      (TeamID, 0x02030405), (NumberOfAgents, 0x0304: Short), (ConfigOperationTag, Some(ConfigOperation.RemoveAgent)),
      (AGENT_TEAM_NAME, "AgentTeamName"), (ATC_AGENT_ID, "1001"), (AGENT_FLAGS, BitSet.empty + AgentFlags.Supervisor),
      (ATC_AGENT_STATE, Some(AgentState.HOLD)), (ATC_STATE_DURATION, 0x04050607)),
      ByteString(0,0,0,128, 1,2,3,4, 2,3,4,5, 3,4, 0,2,
        243,14,0x41,0x67,0x65,0x6e,0x74,0x54,0x65,0x61,0x6d,0x4e,0x61,0x6d,0x65,0,
        86,5,0x31,0x30,0x30,0x31,0,
        87,2,0,4, 88,2,0,10, 89,4,4,5,6,7)),

    // 130: SET_APP_DATA_CONF
    (List((MessageTypeTag, Some(SET_APP_DATA_CONF)), (InvokeID, 0x07070809)),
      ByteString(0,0,0,130, 7,7,8,9)),

    // 134: LIST_AGENT_TEAM_CONF
    (List((MessageTypeTag, Some(LIST_AGENT_TEAM_CONF)), (InvokeID, 0x02030405),
      (NumberOfAgentTeams, 64: Short), (SegmentNumber, 2: Short), (More, true), (LIST_TEAM_ID, 0x03040506)),
      ByteString(0,0,0,134, 2,3,4,5, 0,64, 0,2, 0,1, 92,4,3,4,5,6)),

    // 136: MONITOR_AGENT_TEAM_START_CONF
    (List((MessageTypeTag, Some(MONITOR_AGENT_TEAM_START_CONF)), (InvokeID, 0x01020304),
      (MonitorID, 0x05060708)),
      ByteString(0,0,0,136, 1,2,3,4, 5,6,7,8)),

    // 138: MONITOR_AGENT_TEAM_STOP_CONF
    (List((MessageTypeTag, Some(MONITOR_AGENT_TEAM_STOP_CONF)), (InvokeID, 0x04050607)),
      ByteString(0,0,0,138, 4,5,6,7)),

    // 140: BAD_CALL_CONF
    (List((MessageTypeTag, Some(BAD_CALL_CONF)), (InvokeID, 0x01030405)),
      ByteString(0,0,0,140, 1,3,4,5)),

    // 142: SET_DEVICE_ATTRIBUTES_CONF
    (List((MessageTypeTag, Some(SET_DEVICE_ATTRIBUTES_CONF)), (InvokeID, 0x07080909)),
      ByteString(0,0,0,142, 7,8,9,9)),

    // 144: REGISTER_SERVICE_CONF
    (List((MessageTypeTag, Some(REGISTER_SERVICE_CONF)), (InvokeID, 0x01020304),
      (RegisteredServiceID, 0x02030405)),
      ByteString(0,0,0,144, 1,2,3,4, 2,3,4,5)),

    // 146: UNREGISTER_SERVICE_CONF
    (List((MessageTypeTag, Some(UNREGISTER_SERVICE_CONF)), (InvokeID, 0x0708090b)),
      ByteString(0,0,0,146, 7,8,9,11)),

    // 148: START_RECORDING_CONF
    (List((MessageTypeTag, Some(START_RECORDING_CONF)), (InvokeID, 0x01020304),
      (SessionID, 0x02030405), (ServerData, 0x03040506),
      (CLIENT_ID, "ClientID"), (CLIENT_ADDRESS_IPV6, "ClientAddressIPv6")),
      ByteString(0,0,0,148, 1,2,3,4, 2,3,4,5, 3,4,5,6,
        1,9,0x43,0x6c,0x69,0x65,0x6e,0x74,0x49,0x44,0,
        226,18,0x43,0x6c,0x69,0x65,0x6e,0x74,0x41,0x64,0x64,0x72,0x65,0x73,0x73,0x49,0x50,0x76,0x36,0)),

    // 150: STOP_RECORDING_CONF
    (List((MessageTypeTag, Some(STOP_RECORDING_CONF)), (InvokeID, 0x0708090a)),
      ByteString(0,0,0,150, 7,8,9,10)),

    // 240: CALL_ATTRIBUTE_CHANGE_EVENT
    (List((MessageTypeTag, Some(CALL_ATTRIBUTE_CHANGE_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405), (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (CallTypeID, 0x06070809),
      (ServiceNumber, 0x03040506),
      (CONNECTION_DEVID, "ConnectionDeviceID")),
      ByteString(0,0,0,240, 1,2,3,4, 2,3,4,5, 0,17, 0,0, 6,7,8,9, 3,4,5,6,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0)),

    // 248: CALL_AGENT_GREETING_EVENT
    (List((MessageTypeTag, Some(CALL_AGENT_GREETING_EVENT)), (MonitorID, 0x01020304),
      (PeripheralID, 0x02030405),
      (ConnectionDeviceIDTypeTag, Some(ConnectionDeviceIDType.STATIC)), (ConnectionCallID, 0x06070809),
      (EventCodeTag, Some(EventCode.GreetingHasEndedWithSuccess)), (PeripheralErrorCode, 0x03040506),
      (CONNECTION_DEVID, "ConnectionDeviceID"), (AGENT_ID, "1001")),
      ByteString(0,0,0,248, 1,2,3,4, 2,3,4,5, 0,0, 6,7,8,9, 0,1, 3,4,5,6,
        25,19,0x43,0x6f,0x6e,0x6e,0x65,0x63,0x74,0x69,0x6f,0x6e,0x44,0x65,0x76,0x69,0x63,0x65,0x49,0x44,0,
        5,5,0x31,0x30,0x30,0x31,0)),

    // 250: AGENT_GREETING_CONTROL_CONF
    (List((MessageTypeTag, Some(AGENT_GREETING_CONTROL_CONF)), (InvokeID, 0x01020304)),
      ByteString(0,0,0,250, 1,2,3,4))
  )

}

object InvalidMessageSamples {
  val samples = List(
    // undefined message type: 254
    (List((MessageTypeTag, None), (RawBytes, ByteString(0,1,2,3,4,5,6,7,8,9))),
      ByteString(0,0,0,254, 0,1,2,3,4,5,6,7,8,9)),

    // undefined message type: 255
    (List((MessageTypeTag, None), (RawBytes, ByteString(1,2,3,4,5,6,7,8,9,0))),
      ByteString(0,0,0,255, 1,2,3,4,5,6,7,8,9,0))
    )
}

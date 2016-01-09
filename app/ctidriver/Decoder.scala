package ctidriver

import akka.util.ByteString
import ctidriver.Tag._
import scala.annotation.{switch, tailrec}
import scala.collection.immutable.BitSet

/**
 * Message extractor functions
 */
object Decoder {

  /*
  def decodeNamedVar(buf: ByteString, len: Int): (String, String) = {
    val delim = { val l = buf.take(len).indexOf(0x00); if (l < 0) len else l }
    (decodeString(buf, delim), decodeString(buf.drop(delim + 1), len - (delim + 1)))
  }

  def decodeNamedArray(buf: ByteString, len: Int): (Int, String, String) = {
    val index = buf(0).toInt & 0xff
    val name = buf drop 1
    val len2 = len - 1
    val delim = { val l = name.take(len2).indexOf(0x00); if (l < 0) len2 else l }
    (index, decodeString(name, delim), decodeString(name.drop(delim + 1), len2 - (delim + 1)))
  }
*/
  def decode(buf: ByteString): Message = {
    val msgType = MessageType fromInt buf.toInt
    val body = buf.drop(4)
    val (fixed: Message, next_offset)
    = decodeFixedPart(List((MessageTypeTag, msgType)), MessageType.getFixedPartList(msgType), body)
/*
    println(fixed)
    print("next_offset:"); println(next_offset)
    print("body.size:"); println(body.size)
    print("pass to floating decoder:") ; println(body.drop(next_offset).to[List])
*/
    decodeFloatingPart(fixed, body.drop(next_offset)).reverse
  }

  @tailrec
  def decodeFixedPart(decoded: Message, seq: List[Tag.Value], buf: ByteString, done: Int = 0): (Message, Int) = {

/*
    println("===================")
    println(decoded)
    println(seq)
*/

    if (seq.isEmpty)
      (decoded, done)
    else {
      val tag = seq.head

      val (result, next_offset): ((Tag.Value, Any), Int) = tag match {
        // Unknown message body
        case RawBytes => ((tag, buf), buf.size)

        // Int
        case
          AgentOutCallsHeldSession |
          AgentOutCallsHeldTimeSession |
          AgentOutCallsHeldTimeTo5 |
          AgentOutCallsHeldTimeToHalf |
          AgentOutCallsHeldTimeToday |
          AgentOutCallsHeldTo5 |
          AgentOutCallsHeldToHalf |
          AgentOutCallsHeldToday |
          AgentOutCallsSession |
          AgentOutCallsTalkTimeSession |
          AgentOutCallsTalkTimeTo5 |
          AgentOutCallsTalkTimeToHalf |
          AgentOutCallsTalkTimeToday |
          AgentOutCallsTimeSession |
          AgentOutCallsTimeTo5 |
          AgentOutCallsTimeToHalf |
          AgentOutCallsTimeToday |
          AgentOutCallsTo5 |
          AgentOutCallsToHalf |
          AgentOutCallsToday |
          AgentsApplicationAvailable |
          AgentsAvail |
          AgentsBusyOther |
          AgentsHold |
          AgentsICMAvailable |
          AgentsLoggedOn |
          AgentsNotReady |
          AgentsReady |
          AgentsReserved |
          AgentsTalkingAutoOut |
          AgentsTalkingIn |
          AgentsTalkingOther |
          AgentsTalkingOut |
          AgentsTalkingPreview |
          AgentsTalkingReservation |
          AgentsWorkNotReady |
          AgentsWorkReady |
          AutoOutCallsHeldSession |
          AutoOutCallsHeldTimeSession |
          AutoOutCallsHeldTimeTo5 |
          AutoOutCallsHeldTimeToHalf |
          AutoOutCallsHeldTimeToday |
          AutoOutCallsHeldTo5 |
          AutoOutCallsHeldToHalf |
          AutoOutCallsHeldToday |
          AutoOutCallsSession |
          AutoOutCallsTalkTimeSession |
          AutoOutCallsTalkTimeTo5 |
          AutoOutCallsTalkTimeToHalf |
          AutoOutCallsTalkTimeToday |
          AutoOutCallsTimeSession |
          AutoOutCallsTimeTo5 |
          AutoOutCallsTimeToHalf |
          AutoOutCallsTimeToday |
          AutoOutCallsTo5 |
          AutoOutCallsToHalf |
          AutoOutCallsToday |
          AvailTimeSession |
          AvailTimeTo5 |
          AvailTimeToHalf |
          AvailTimeToday |
          BargeInCallsSession |
          BargeInCallsTo5 |
          BargeInCallsToHalf |
          BargeInCallsToday |
          BitRate |
          CallTypeID |
          CallsQ5 |
          CallsQHalf |
          CallsQNow |
          CallsQTime5 |
          CallsQTimeHalf |
          CallsQTimeNow |
          CallsQTimeToday |
          CallsQToday |
          CampaignID |
          ClientPort |
          ConnectionCallID |
          EmergencyCallsSession |
          EmergencyCallsTo5 |
          EmergencyCallsToHalf |
          EmergencyCallsToday |
          HandledCallsAfterCallTimeSession |
          HandledCallsAfterCallTimeTo5 |
          HandledCallsAfterCallTimeToHalf |
          HandledCallsAfterCallTimeToday |
          HandledCallsSession |
          HandledCallsTalkTimeSession |
          HandledCallsTalkTimeTo5 |
          HandledCallsTalkTimeToHalf |
          HandledCallsTalkTimeToday |
          HandledCallsTimeSession |
          HandledCallsTimeTo5 |
          HandledCallsTimeToHalf |
          HandledCallsTimeToday |
          HandledCallsTo5 |
          HandledCallsToHalf |
          HandledCallsToday |
          ICMAgentID |
          ICMAvailableTimeSession |
          ICMAvailableTimeToday |
          ICMCentralControllerTime |
          IncomingCallsHeldSession |
          IncomingCallsHeldTimeSession |
          IncomingCallsHeldTimeTo5 |
          IncomingCallsHeldTimeToHalf |
          IncomingCallsHeldTimeToday |
          IncomingCallsHeldTo5 |
          IncomingCallsHeldToHalf |
          IncomingCallsHeldToday |
          InterceptCallsSession |
          InterceptCallsTo5 |
          InterceptCallsToHalf |
          InterceptCallsToday |
          InternalCallsHeldSession |
          InternalCallsHeldTimeSession |
          InternalCallsHeldTimeTo5 |
          InternalCallsHeldTimeToHalf |
          InternalCallsHeldTimeToday |
          InternalCallsHeldTo5 |
          InternalCallsHeldToHalf |
          InternalCallsHeldToday |
          InternalCallsRcvdSession |
          InternalCallsRcvdTimeSession |
          InternalCallsRcvdTimeTo5 |
          InternalCallsRcvdTimeToHalf |
          InternalCallsRcvdTimeToday |
          InternalCallsRcvdTo5 |
          InternalCallsRcvdToHalf |
          InternalCallsRcvdToday |
          InternalCallsSession |
          InternalCallsTimeSession |
          InternalCallsTimeToday |
          InternalCallsToday |
          InvokeID |
          LoggedOnTimeSession |
          LoggedOnTimeTo5 |
          LoggedOnTimeToHalf |
          LoggedOnTimeToday |
          LogoutNonActivityTime |
          LongestCallQ5 |
          LongestCallQHalf |
          LongestCallQNow |
          LongestCallQToday |
          LongestRouterCallQNow |
          MRDID |
          MaxTaskLimit |
          MonitorCallsSession |
          MonitorCallsTo5 |
          MonitorCallsToHalf |
          MonitorCallsToday |
          MonitorID |
          NewConnectionCallID |
          NotReadyTimeSession |
          NotReadyTimeTo5 |
          NotReadyTimeToHalf |
          NotReadyTimeToday |
          NumTasks |
          PacketSize |
          PeripheralErrorCode |
          PeripheralID |
          PreviewCallsHeldSession |
          PreviewCallsHeldTimeSession |
          PreviewCallsHeldTimeTo5 |
          PreviewCallsHeldTimeToHalf |
          PreviewCallsHeldTimeToday |
          PreviewCallsHeldTo5 |
          PreviewCallsHeldToHalf |
          PreviewCallsHeldToday |
          PreviewCallsSession |
          PreviewCallsTalkTimeSession |
          PreviewCallsTalkTimeTo5 |
          PreviewCallsTalkTimeToHalf |
          PreviewCallsTalkTimeToday |
          PreviewCallsTimeSession |
          PreviewCallsTimeTo5 |
          PreviewCallsTimeToHalf |
          PreviewCallsTimeToday |
          PreviewCallsTo5 |
          PreviewCallsToHalf |
          PreviewCallsToday |
          PrimaryCallID |
          QualityRecordingRate |
          QueryRuleID |
          RegisteredServiceID |
          ReservationCallsHeldSession |
          ReservationCallsHeldTimeSession |
          ReservationCallsHeldTimeTo5 |
          ReservationCallsHeldTimeToHalf |
          ReservationCallsHeldTimeToday |
          ReservationCallsHeldTo5 |
          ReservationCallsHeldToHalf |
          ReservationCallsHeldToday |
          ReservationCallsSession |
          ReservationCallsTalkTimeSession |
          ReservationCallsTalkTimeTo5 |
          ReservationCallsTalkTimeToHalf |
          ReservationCallsTalkTimeToday |
          ReservationCallsTimeSession |
          ReservationCallsTimeTo5 |
          ReservationCallsTimeToHalf |
          ReservationCallsTimeToday |
          ReservationCallsTo5 |
          ReservationCallsToHalf |
          ReservationCallsToday |
          RingNoAnswerDN |
          RingNoAnswerTime |
          RoutableTimeSession |
          RoutableTimeToday |
          RouterCallsQNow |
          SecondaryCallID |
          ServerData |
          ServiceID |
          ServiceNumber |
          SessionID |
          SkillGroupID |
          SkillGroupNumber |
          StateDuration |
          SystemEventArg1 |
          SystemEventArg2 |
          SystemEventArg3 |
          TeamID |
          WhisperCallsSession |
          WhisperCallsTo5 |
          WhisperCallsToHalf |
          WhisperCallsToday |
          WorkModeTimer
        => ((tag, buf.toInt), 4)

        // Short
        case
          EventReasonCode |
          LineHandle |
          MaxActiveCalls |
          MaxDeviceInConference |
          MaxHeldCalls |
          NumCTIClients |
          NumCallDevices |
          NumCalls |
          NumFltSkillGroups |
          NumLines |
          NumNamedArrays |
          NumNamedVariables |
          NumParties |
          NumQueued |
          NumSkillGroups |
          NumberOfAgentTeams |
          NumberOfAgents |
          PayloadType |
          Reserved16 |
          SegmentNumber |
          SkillGroupPriority
        => ((tag, buf.toShort), 2)

        // Boolean (Int)
        case
          SilentMonitorWarningMessage |
          SilentMonitorAudibleIndication |
          SupervisorAssistCallMethod |
          EmergencyCallMethod |
          AutoRecordOnEmergency |
          RecordingMode
        => ((tag, buf.toInt != 0), 4)

        // Boolean (Short)
        case
          AgentMode |
          EchoCancellation |
          More |
          PeripheralOnline
        => ((tag, buf.toShort != 0), 2)

        // Int wide BitSet
        case
          AgentStateMaskTag |
          CallControlSupported |
          CallEventsSupported |
          CallMsgMask |
          DeskSettingsMaskTag |
          MakeCallSetup |
          OtherFeaturesSupported |
          PGStatus |
          ServiceGranted |
          TransferConferenceSetup
        => ((tag, BitSet fromBitMask Array(buf.toInt.toLong)), 4)

        // Short wide BitSet
        case ClassOfDeviceTag => ((tag, BitSet fromBitMask Array(buf.toShort.toLong)), 2)

        // Int enumeration
        case WrapupDataIncomingMode | WrapupDataOutgoingMode => WrapupDataMode.decodeWithLen(tag, buf)
        case AgentAvailabilityStatusTag => AgentAvailabilityStatus.decodeWithLen(tag, buf)
        case Status => StatusCode.decodeWithLen(tag, buf)

        // Short enumeration
        case CalledPartyDisposition => DispositionCodeValue.decodeWithLen(tag, buf)
        case CallTypeTag => CallType.decodeWithLen(tag, buf)
        case ConfigOperationTag => ConfigOperation.decodeWithLen(tag, buf)
        case Distribution => DistributionValue.decodeWithLen(tag, buf)
        case FailureCode => ControlFailureCode.decodeWithLen(tag, buf)
        case EventCodeTag => EventCode.decodeWithLen(tag, buf)
        case EventCauseTag => EventCause.decodeWithLen(tag, buf)
        case LineTypeTag => LineType.decodeWithLen(tag, buf)
        case LocalConnectionStateTag => LocalConnectionState.decodeWithLen(tag, buf)
        case PeripheralTypeTag => PeripheralType.decodeWithLen(tag, buf)
        case RTPDirectionTag => RTPDirection.decodeWithLen(tag, buf)
        case RTPTypeTag => RTPType.decodeWithLen(tag, buf)
        case SystemEventIDTag => SystemEventID.decodeWithLen(tag, buf)
        case TypeOfDeviceTag => TypeOfDevice.decodeWithLen(tag, buf)

        case AgentStateTag | SkillGroupState => AgentState.decodeWithLen(tag, buf)

        case ConnectionDeviceIDTypeTag | NewConnectionDeviceIDType => ConnectionDeviceIDType.decodeWithLen(tag, buf)

        case
          AddedPartyDeviceType |
          AlertingDeviceType |
          AnsweringDeviceType |
          CallingDeviceType |
          CalledDeviceType |
          ControllerDeviceType |
          DivertingDeviceType |
          EventDeviceType |
          FailingDeviceType |
          HoldingDeviceType |
          LastRedirectDeviceType |
          PrimaryDeviceIDType |
          QueueDeviceType |
          ReleasingDeviceType |
          RetrievingDeviceType |
          SecondaryDeviceIDType |
          TransferringDeviceType |
          TransferredDeviceType |
          TrunkUsedDeviceType
        => DeviceIDType.decodeWithLen(tag, buf)
      }

      decodeFixedPart(result +: decoded, seq.tail, buf.drop(next_offset), done + next_offset)
    }
  }

  def decFlStr(tag: Tag.Value, len: Int, body: ByteString): (Tag.Value, Any) = (tag, body.toString(len))
  def decFlInt(tag: Tag.Value, len: Int, body: ByteString): (Tag.Value, Any) = (tag, body.toInt)
  def decFlShort(tag: Tag.Value, len: Int, body: ByteString): (Tag.Value, Any) = (tag, body.toShort)
  def decFlRaw(tag: Tag.Value, len: Int, body: ByteString): (Tag.Value, Any) = (tag, body take len)

  val flTagToDecodeFunc: Array[(Tag.Value, Int, ByteString) => (Tag.Value, Any)] = Array(
    //   0: Invalid
    decFlRaw,
    //   1: CLIENT_ID
    decFlStr,
    //   2: CLIENT_PASSWORD
    decFlRaw,
    //   3: CLIENT_SIGNATURE
    decFlStr,
    //   4: AGENT_EXTENSION
    decFlStr,
    //   5: AGENT_ID
    decFlStr,
    //   6: AGENT_INSTRUMENT
    decFlStr,
    //   7: TEXT
    decFlStr,
    //   8: ANI
    decFlStr,
    //   9: UUI
    decFlRaw,
    //  10: DNIS
    decFlStr,
    //  11: DIALED_NUMBER
    decFlStr,
    //  12: CED
    decFlStr,
    //  13: CALL_VAR_1
    decFlStr,
    //  14: CALL_VAR_2
    decFlStr,
    //  15: CALL_VAR_3
    decFlStr,
    //  16: CALL_VAR_4
    decFlStr,
    //  17: CALL_VAR_5
    decFlStr,
    //  18: CALL_VAR_6
    decFlStr,
    //  19: CALL_VAR_7
    decFlStr,
    //  20: CALL_VAR_8
    decFlStr,
    //  21: CALL_VAR_9
    decFlStr,
    //  22: CALL_VAR_10
    decFlStr,
    //  23: CTI_CLIENT_SIGNATURE
    decFlStr,
    //  24: CTI_CLIENT_TIMESTAMP
    decFlInt,
    //  25: CONNECTION_DEVID
    decFlStr,
    //  26: ALERTING_DEVID
    decFlStr,
    //  27: CALLING_DEVID
    decFlStr,
    //  28: CALLED_DEVID
    decFlStr,
    //  29: LAST_REDIRECT_DEVID
    decFlStr,
    //  30: ANSWERING_DEVID
    decFlStr,
    //  31: HOLDING_DEVID
    decFlStr,
    //  32: RETRIEVING_DEVID
    decFlStr,
    //  33: RELEASING_DEVID
    decFlStr,
    //  34: FAILING_DEVID
    decFlStr,
    //  35: PRIMARY_DEVID
    decFlStr,
    //  36: SECONDARY_DEVID
    decFlStr,
    //  37: CONTROLLER_DEVID
    decFlStr,
    //  38: ADDED_PARTY_DEVID
    decFlStr,
    //  39: PARTY_CALLID
    decFlInt,
    //  40: PARTY_DEVID_TYPE
    (tag, len, body) => DeviceIDType.decode(tag, body),
    //  41: PARTY_DEVID
    decFlStr,
    //  42: TRANSFERRING_DEVID
    decFlStr,
    //  43: TRANSFERRED_DEVID
    decFlStr,
    //  44: DIVERTING_DEVID
    decFlStr,
    //  45: QUEUE_DEVID
    decFlStr,
    //  46: CALL_WRAPUP_DATA
    decFlStr,
    //  47: NEW_CONNECTION_DEVID
    decFlStr,
    //  48: TRUNK_USED_DEVID
    decFlStr,
    //  49: AGENT_PASSWORD
    decFlStr,
    //  50: ACTIVE_CONN_DEVID
    decFlStr,
    //  51: FACILITY_CODE
    decFlStr,
    //  52: OTHER_CONN_DEVID
    decFlStr,
    //  53: HELD_CONN_DEVID
    decFlStr,
    //  54: RESERVED_54
    decFlRaw,
    //  55: RESERVED_55
    decFlRaw,
    //  56: CALL_CONN_CALLID
    decFlInt,
    //  57: CALL_CONN_DEVID_TYPE
    (tag, len, body) => ConnectionDeviceIDType.decode(tag, body),
    //  58: CALL_CONN_DEVID
    decFlStr,
    //  59: CALL_DEVID_TYPE
    decFlShort,
    //  60: CALL_DEVID
    decFlStr,
    //  61: CALL_DEV_CONN_STATE
    decFlShort,
    //  62: SKILL_GROUP_NUMBER
    decFlInt,
    //  63: SKILL_GROUP_ID
    decFlInt,
    //  64: SKILL_GROUP_PRIORITY
    decFlShort,
    //  65: SKILL_GROUP_STATE
    (tag, len, body) => AgentState.decode(tag, body),
    //  66: OBJECT_NAME
    decFlStr,
    //  67: DTMF_STRING
    decFlStr,
    //  68: POSITION_ID
    decFlStr,
    //  69: SUPERVISOR_ID
    decFlStr,
    //  70: LINE_HANDLE
    decFlShort,
    //  71: LINE_TYPE
    (tag, len, body) => LineType.decode(tag, body),
    //  72: ROUTER_CALL_KEY_DAY
    decFlInt,
    //  73: ROUTER_CALL_KEY_CALLID
    decFlInt,
    //  74: RESERVED_74
    decFlRaw,
    //  75: CALL_STATE
    (tag, len, body) => LocalConnectionState.decode(tag, body),
    //  76: MONITORED_DEVID
    decFlStr,
    //  77: AUTHORIZATION_CODE
    decFlStr,
    //  78: ACCOUNT_CODE
    decFlStr,
    //  79: ORIGINATING_DEVID
    decFlStr,
    //  80: ORIGINATING_LINE_ID
    decFlStr,
    //  81: CLIENT_ADDRESS
    decFlStr,
    //  82: NAMED_VARIABLE
    (tag, len, body) => (tag, body.toNamedVar(len)),
    //  83: NAMED_ARRAY
    (tag, len, body) => (tag, body.toNamedArray(len)),
    //  84: CALL_CONTROL_TABLE
    decFlStr,
    //  85: SUPERVISOR_INSTRUMENT
    decFlStr,
    //  86: ATC_AGENT_ID
    decFlStr,
    //  87: AGENT_FLAGS
    (tag, len, body) => (tag, BitSet fromBitMask Array(body.toShort.toLong)),
    //  88: ATC_AGENT_STATE
    (tag, len, body) => AgentState.decode(tag, body),
    //  89: ATC_STATE_DURATION
    decFlInt,
    //  90: AGENT_CONNECTION_DEVID
    decFlStr,
    //  91: SUPERVISOR_CONNECTION_DEVID
    decFlStr,
    //  92: LIST_TEAM_ID
    decFlInt,
    //  93: DEFAULT_DEVICE_PORT_ADDRESS
    decFlStr,
    //  94: SERVICE_NAME
    decFlStr,
    //  95: CUSTOMER_PHONE_NUMBER
    decFlStr,
    //  96: CUSTOMER_ACCOUNT_NUMBER
    decFlStr,
    //  97: APP_PATH_ID
    decFlRaw,
    //  98: RESERVED_98
    decFlRaw,
    //  99: RESERVED_99
    decFlRaw,
    // 100: RESERVED_100
    decFlRaw,
    // 101: RESERVED_101
    decFlRaw,
    // 102: RESERVED_102
    decFlRaw,
    // 103: RESERVED_103
    decFlRaw,
    // 104: RESERVED_104
    decFlRaw,
    // 105: RESERVED_105
    decFlRaw,
    // 106: RESERVED_106
    decFlRaw,
    // 107: RESERVED_107
    decFlRaw,
    // 108: RESERVED_108
    decFlRaw,
    // 109: RESERVED_109
    decFlRaw,
    // 110: ROUTER_CALL_KEY_SEQUENCE_NUM
    decFlInt,
    // 111: RESERVED_111
    decFlRaw,
    // 112: RESERVED_112
    decFlRaw,
    // 113: RESERVED_113
    decFlRaw,
    // 114: RESERVED_114
    decFlRaw,
    // 115: RESERVED_115
    decFlRaw,
    // 116: RESERVED_116
    decFlRaw,
    // 117: RESERVED_117
    decFlRaw,
    // 118: RESERVED_118
    decFlRaw,
    // 119: RESERVED_119
    decFlRaw,
    // 120: RESERVED_120
    decFlRaw,
    // 121: TRUNK_NUMBER
    decFlInt,
    // 122: TRUNK_GROUP_NUMBER
    decFlInt,
    // 123: NEXT_AGENT_STATE
    (tag, len, body) => AgentState.decode(tag, body),
    // 124: DEQUEUE_TYPE
    decFlRaw,
    // 125: SENDING_ADDRESS
    decFlStr,
    // 126: SENDING_PORT
    decFlStr,
    // 127: RESERVED_127
    decFlRaw,
    // 128: RESERVED_128
    decFlRaw,
    // 129: MAX_QUEUED
    decFlRaw,
    // 130: QUEUE_ID
    decFlRaw,
    // 131: CUSTOMER_ID
    decFlRaw,
    // 132: SERVICE_SKILL_TARGET_ID
    decFlRaw,
    // 133: PERIPHERAL_NAME
    decFlRaw,
    // 134: DESCRIPTION
    decFlRaw,
    // 135: SERVICE_MEMBER_ID
    decFlRaw,
    // 136: SERVICE_MEMBER_PRIORITY
    decFlRaw,
    // 137: FIRST_NAME
    decFlRaw,
    // 138: LAST_NAME
    decFlRaw,
    // 139: SKILL_GROUP
    decFlRaw,
    // 140: RESERVED_140
    decFlRaw,
    // 141: AGENT_SKILL_TARGET_ID
    decFlRaw,
    // 142: SERVICE
    decFlRaw,
    // 143: RESERVED_143
    decFlRaw,
    // 144: RESERVED_144
    decFlRaw,
    // 145: RESERVED_145
    decFlRaw,
    // 146: RESERVED_146
    decFlRaw,
    // 147: RESERVED_147
    decFlRaw,
    // 148: RESERVED_148
    decFlRaw,
    // 149: RESERVED_149
    decFlRaw,
    // 150: DURATION
    decFlInt,
    // 151: RESERVED_151
    decFlRaw,
    // 152: RESERVED_152
    decFlRaw,
    // 153: RESERVED_153
    decFlRaw,
    // 154: RESERVED_154
    decFlRaw,
    // 155: RESERVED_155
    decFlRaw,
    // 156: RESERVED_156
    decFlRaw,
    // 157: RESERVED_157
    decFlRaw,
    // 158: RESERVED_158
    decFlRaw,
    // 159: RESERVED_159
    decFlRaw,
    // 160: RESERVED_160
    decFlRaw,
    // 161: RESERVED_161
    decFlRaw,
    // 162: RESERVED_162
    decFlRaw,
    // 163: RESERVED_163
    decFlRaw,
    // 164: RESERVED_164
    decFlRaw,
    // 165: RESERVED_165
    decFlRaw,
    // 166: RESERVED_166
    decFlRaw,
    // 167: RESERVED_167
    decFlRaw,
    // 168: RESERVED_168
    decFlRaw,
    // 169: RESERVED_169
    decFlRaw,
    // 170: RESERVED_170
    decFlRaw,
    // 171: RESERVED_171
    decFlRaw,
    // 172: RESERVED_172
    decFlRaw,
    // 173: EXTENSION
    decFlRaw,
    // 174: SERVICE_LEVEL_THRESHOLD
    decFlRaw,
    // 175: SERVICE_LEVEL_TYPE
    decFlRaw,
    // 176: CONFIG_PARAM
    decFlRaw,
    // 177: SERVICE_CONFIG_KEY
    decFlRaw,
    // 178: SKILL_GROUP_CONFIG_KEY
    decFlRaw,
    // 179: AGENT_CONFIG_KEY
    decFlRaw,
    // 180: DEVICE_CONFIG_KEY
    decFlRaw,
    // 181: RESERVED_181
    decFlRaw,
    // 182: RESERVED_182
    decFlRaw,
    // 183: RECORD_TYPE
    decFlRaw,
    // 184: PERIPHERAL_NUMBER
    decFlRaw,
    // 185: CONFIG_AGENT_SKILL_TARGET_ID
    decFlRaw,
    // 186: NUM_SERVICE_MEMBERS
    decFlRaw,
    // 187: SERVICE_MEMBER
    decFlStr,
    // 188: SERVICE_PRIORITY
    decFlRaw,
    // 189: AGENT_TYPE
    decFlRaw,
    // 190: LOGIN_ID
    decFlRaw,
    // 191: NUM_SKILLS
    decFlRaw,
    // 192: SKILL_GROUP_SKILL_TARGET_ID
    decFlRaw,
    // 193: SERVICE_ID
    decFlInt,
    // 194: AGENT_ID_LONG
    decFlRaw,
    // 195: DEVICE_TYPE
    decFlRaw,
    // 196: RESERVED_196
    decFlRaw,
    // 197: RESERVED_197
    decFlRaw,
    // 198: ENABLE
    decFlRaw,
    // 199: DEVICEID
    decFlRaw,
    // 200: TIMEOUT
    decFlRaw,
    // 201: CURRENT_ROUTE
    decFlRaw,
    // 202: SECONDARY_CONNECTION_CALL_ID
    decFlInt,
    // 203: PRIORITY_QUEUE_NUMBER
    decFlRaw,
    // 204: TEAM_NAME
    decFlRaw,
    // 205: MEMBER_TYPE
    decFlRaw,
    // 206: EVENT_DEVICE_ID
    decFlStr,
    // 207: LOGIN_NAME_V11
    decFlRaw,
    // 208: PERIPHERAL_ID_V11
    decFlRaw,
    // 209: CALL_TYPE_KEY_CONFIG_V11
    decFlRaw,
    // 210: CALL_TYPE_ID_V11
    decFlRaw,
    // 211: CUSTOMER_DEFINITION_ID_V11
    decFlRaw,
    // 212: ENTERPRISE_NAME_V11
    decFlRaw,
    // 213: CUR_PERIPHERAL_NUMBER
    decFlRaw,
    // 214: CUR_LOGIN_ID
    decFlRaw,
    // 215: ANI_II
    decFlStr,
    // 216: MR_DOMAIN_ID
    decFlRaw,
    // 217: CTIOS_CIL_CLIENT_ID
    decFlStr,
    // 218: SILENT_MONITOR_STATUS
    (tag, len, body) => SilentMonitorStatus.decode(tag, body),
    // 219: REQUESTING_DEVICE_ID
    decFlStr,
    // 220: REQUESTING_DEVICE_ID_TYPE
    decFlRaw,
    // 221: PRE_CALL_INVOKE_ID
    decFlInt,
    // 222: ENTERPRISE_QUEUE_TIME
    decFlRaw,
    // 223: CALL_REFERENCE_ID
    decFlRaw,
    // 224: MULTI_LINE_AGENT_CONTROL
    (tag, len, body) => (tag, body.toShort != 0),
    // 225: NETWORK_CONTROLLED
    decFlRaw,
    // 226: CLIENT_ADDRESS_IPV6
    decFlStr,
    // 227: SENDING_ADDRESS_IPV6
    decFlStr,
    // 228: NUM_PERIPHERALS
    decFlShort,
    // 229: COC_CONNECTION_CALL_ID
    decFlInt,
    // 230: COC_CONNECTION_DEVICE_ID_TYPE
    decFlShort,
    // 231: COC_CONNECTION_DEVICE_ID
    decFlStr,
    // 232: CALL_ORIGINATED_FROM
    (tag, len, body) => (tag, body(0)),
    // 233: SET_APPDATA_CALLID
    decFlRaw,
    // 234: CLIENT_SHARE_KEY
    decFlRaw,
    // 235: RESERVED_235
    decFlRaw,
    // 236: RESERVED_236
    decFlRaw,
    // 237: RESERVED_237
    decFlRaw,
    // 238: RESERVED_238
    decFlRaw,
    // 239: RESERVED_239
    decFlRaw,
    // 240: RESERVED_240
    decFlRaw,
    // 241: RESERVED_241
    decFlRaw,
    // 242: RESERVED_242
    decFlRaw,
    // 243: AGENT_TEAM_NAME
    decFlStr,
    // 244: DIRECTION
    (tag, len, body) => CallDirection.decode(tag, body),
    // 245: RESERVED_245
    decFlRaw,
    // 246: RESERVED_246
    decFlRaw,
    // 247: RESERVED_247
    decFlRaw,
    // 248: RESERVED_248
    decFlRaw,
    // 249: RESERVED_249
    decFlRaw,
    // 250: RESERVED_250
    decFlRaw,
    // 251: RESERVED_251
    decFlRaw,
    // 252: RESERVED_252
    decFlRaw,
    // 253: RESERVED_253
    decFlRaw,
    // 254: RESERVED_254
    decFlRaw,
    // 255: RESERVED_255
    decFlRaw
  )

  @tailrec
  def decodeFloatingPart(decoded: Message, buf: ByteString): Message = {
    if (buf.size <= 0)
      decoded
    else {
      val tag = Tag(buf(0).toInt & 0xff)
      val len = buf(1).toInt & 0xff
      val body = buf.drop(2)
      val rest = body.drop(len)


      val result = flTagToDecodeFunc(tag.id)(tag, len, body)

      decodeFloatingPart(result +: decoded, rest)
    }
  }

    @tailrec
  def decodeFloatingPart_old_do_not_use(decoded: Message, buf: ByteString): Message = {
/*
        println("+++++++++++++++")
        println(decoded)
        println(buf.to[List].map(n => n.toInt.toHexString))
*/
    if (buf.size <= 0)
      decoded
    else {
      val tag = Tag(buf(0).toInt & 0xff)
      val len = buf(1).toInt & 0xff
      val body = buf.drop(2)
      val rest = body.drop(len)

      val result: (Tag.Value, Any) = tag match {
          // String type tags
        case
          ACCOUNT_CODE |
          ACTIVE_CONN_DEVID |
          ADDED_PARTY_DEVID |
          AGENT_CONNECTION_DEVID |
          AGENT_EXTENSION |
          AGENT_ID |
          AGENT_INSTRUMENT |
          AGENT_PASSWORD |
          AGENT_TEAM_NAME |
          ALERTING_DEVID |
          ANI |
          ANI_II |
          ANSWERING_DEVID |
          ATC_AGENT_ID |
          AUTHORIZATION_CODE |
          CALLED_DEVID |
          CALLING_DEVID |
          CALL_CONN_DEVID |
          CALL_CONTROL_TABLE |
          CALL_DEVID |
          CALL_VAR_1 |
          CALL_VAR_10 |
          CALL_VAR_2 |
          CALL_VAR_3 |
          CALL_VAR_4 |
          CALL_VAR_5 |
          CALL_VAR_6 |
          CALL_VAR_7 |
          CALL_VAR_8 |
          CALL_VAR_9 |
          CALL_WRAPUP_DATA |
          CED |
          CLIENT_ADDRESS |
          CLIENT_ADDRESS_IPV6 |
          CLIENT_ID |
          CLIENT_SIGNATURE |
          COC_CONNECTION_DEVICE_ID |
          CONNECTION_DEVID |
          CONTROLLER_DEVID |
          CTIOS_CIL_CLIENT_ID |
          CTI_CLIENT_SIGNATURE |
          CUSTOMER_ACCOUNT_NUMBER |
          CUSTOMER_ACCOUNT_NUMBER |
          CUSTOMER_PHONE_NUMBER |
          CUSTOMER_PHONE_NUMBER |
          DEFAULT_DEVICE_PORT_ADDRESS |
          DIALED_NUMBER |
          DIVERTING_DEVID |
          DNIS |
          DTMF_STRING |
          EVENT_DEVICE_ID |
          FACILITY_CODE |
          FAILING_DEVID |
          HELD_CONN_DEVID |
          HOLDING_DEVID |
          LAST_REDIRECT_DEVID |
          MONITORED_DEVID |
          NEW_CONNECTION_DEVID |
          NEW_CONNECTION_DEVID |
          OBJECT_NAME |
          ORIGINATING_DEVID |
          ORIGINATING_LINE_ID |
          OTHER_CONN_DEVID |
          PARTY_DEVID |
          POSITION_ID |
          PRIMARY_DEVID |
          QUEUE_DEVID |
          RELEASING_DEVID |
          REQUESTING_DEVICE_ID |
          RETRIEVING_DEVID |
          SECONDARY_DEVID |
          SENDING_ADDRESS |
          SENDING_ADDRESS_IPV6 |
          SENDING_PORT |
          SERVICE_NAME |
          SUPERVISOR_CONNECTION_DEVID |
          SUPERVISOR_ID |
          SUPERVISOR_INSTRUMENT |
          TEXT |
          TRANSFERRED_DEVID |
          TRANSFERRING_DEVID |
          TRUNK_USED_DEVID
        => (tag, body.toString(len))

        // Byte type tags
        case CALL_ORIGINATED_FROM => (tag, body(0))

        // Short type tags
        case
          CALL_DEVID_TYPE |
          CALL_DEV_CONN_STATE |
          COC_CONNECTION_DEVICE_ID_TYPE |
          LINE_HANDLE |
          NUM_PERIPHERALS |
          SKILL_GROUP_PRIORITY
        => (tag, body.toShort)

        // Int type tags
        case
          ATC_STATE_DURATION |
          CALL_CONN_CALLID |
          COC_CONNECTION_CALL_ID |
          CTI_CLIENT_TIMESTAMP |
          DURATION |
          LIST_TEAM_ID |
          PARTY_CALLID |
          PRE_CALL_INVOKE_ID |
          ROUTER_CALL_KEY_CALLID |
          ROUTER_CALL_KEY_DAY |
          ROUTER_CALL_KEY_SEQUENCE_NUM |
          SECONDARY_CONNECTION_CALL_ID |
          SERVICE_ID |
          SERVICE_MEMBER |
          SKILL_GROUP_ID |
          SKILL_GROUP_NUMBER |
          TRUNK_GROUP_NUMBER |
          TRUNK_NUMBER
        => (tag, body.toInt)

        // Short boolean type tags
        case MULTI_LINE_AGENT_CONTROL => (tag, body.toShort != 0)

        // Short BitSet type tags
        case AGENT_FLAGS => (tag, BitSet fromBitMask Array(body.toShort.toLong))

        // Named Variable type tags
        case NAMED_VARIABLE => (tag, body.toNamedVar(len))

        // Named Array type tags
        case NAMED_ARRAY => (tag, body.toNamedArray(len))

        // Short enumeration tags
        case
          PARTY_DEVID_TYPE
        => DeviceIDType.decode(tag, body)

        case ATC_AGENT_STATE => AgentState.decode(tag, body)
        case CALL_CONN_DEVID_TYPE => ConnectionDeviceIDType.decode(tag, body)
        case CALL_STATE => LocalConnectionState.decode(tag, body)
        case DIRECTION => CallDirection.decode(tag, body)
        case LINE_TYPE => LineType.decode(tag, body)
        case NEXT_AGENT_STATE => AgentState.decode(tag, body)
        case SILENT_MONITOR_STATUS => SilentMonitorStatus.decode(tag, body)
        case SKILL_GROUP_STATE => AgentState.decode(tag, body)


        // Raw byte array type tags
        case
          CALL_REFERENCE_ID |
          UUI |
          _
        => (tag, body take len)
      }

      decodeFloatingPart_old_do_not_use(result +: decoded, rest)
    }
  }
}

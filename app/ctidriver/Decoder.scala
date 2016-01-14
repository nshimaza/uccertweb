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
  def decodeFixedPart(decoded: Message, seq: List[Tag], buf: ByteString, done: Int = 0): (Message, Int) = {

/*
    println("===================")
    println(decoded)
    println(seq)
*/

    if (seq.isEmpty)
      (decoded, done)
    else {
      val tag = seq.head

      val (result, next_offset): ((Tag, Any), Int) = tag match {
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


  @tailrec
  def decodeFloatingPart(decoded: Message, buf: ByteString): Message = {
    if (buf.size <= 0)
      decoded
    else {
      val tag = Tag(buf(0).toInt & 0xff)
      val len = buf(1).toInt & 0xff
      val body = buf.drop(2)
      val rest = body.drop(len)

      decodeFloatingPart(decodeFloatingField(tag, len, body) +: decoded, rest)
    }
  }

  /*
  @tailrec
  def decodeFloatingPart_old_do_not_use(decoded: Message, buf: ByteString): Message = {

//        println("+++++++++++++++")
//        println(decoded)
//        println(buf.to[List].map(n => n.toInt.toHexString))

    if (buf.size <= 0)
      decoded
    else {
      val tag = Tag(buf(0).toInt & 0xff)
      val len = buf(1).toInt & 0xff
      val body = buf.drop(2)
      val rest = body.drop(len)

      val result: (Tag, Any) = tag match {
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
  */
}

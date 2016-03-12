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
import scala.collection.immutable.BitSet

/**
 * Tag identifier for floating part of CTI server protocol message.
 * THis enumeration is also used for key which identify fixed part field in extracted map.
 * 
 */
object Tag extends Enumeration {
  type Tag = Value
  //
  // Tag value 0 through 255 are directly mapped to Tag field in
  // floating part of CTI message.
  //
  val CLIENT_ID                     = Value(  1)
  val CLIENT_PASSWORD               = Value(  2)
  val CLIENT_SIGNATURE              = Value(  3)
  val AGENT_EXTENSION               = Value(  4)
  val AGENT_ID                      = Value(  5)
  val AGENT_INSTRUMENT              = Value(  6)
  val TEXT                          = Value(  7)
  val ANI                           = Value(  8)
  val UUI                           = Value(  9)
  val DNIS                          = Value( 10)
  val DIALED_NUMBER                 = Value( 11)
  val CED                           = Value( 12)
  val CALL_VAR_1                    = Value( 13)
  val CALL_VAR_2                    = Value( 14)
  val CALL_VAR_3                    = Value( 15)
  val CALL_VAR_4                    = Value( 16)
  val CALL_VAR_5                    = Value( 17)
  val CALL_VAR_6                    = Value( 18)
  val CALL_VAR_7                    = Value( 19)
  val CALL_VAR_8                    = Value( 20)
  val CALL_VAR_9                    = Value( 21)
  val CALL_VAR_10                   = Value( 22)
  val CTI_CLIENT_SIGNATURE          = Value( 23)
  val CTI_CLIENT_TIMESTAMP          = Value( 24)
  val CONNECTION_DEVID              = Value( 25)
  val ALERTING_DEVID                = Value( 26)
  val CALLING_DEVID                 = Value( 27)
  val CALLED_DEVID                  = Value( 28)
  val LAST_REDIRECT_DEVID           = Value( 29)
  val ANSWERING_DEVID               = Value( 30)
  val HOLDING_DEVID                 = Value( 31)
  val RETRIEVING_DEVID              = Value( 32)
  val RELEASING_DEVID               = Value( 33)
  val FAILING_DEVID                 = Value( 34)
  val PRIMARY_DEVID                 = Value( 35)
  val SECONDARY_DEVID               = Value( 36)
  val CONTROLLER_DEVID              = Value( 37)
  val ADDED_PARTY_DEVID             = Value( 38)
  val PARTY_CALLID                  = Value( 39)
  val PARTY_DEVID_TYPE              = Value( 40)
  val PARTY_DEVID                   = Value( 41)
  val TRANSFERRING_DEVID            = Value( 42)
  val TRANSFERRED_DEVID             = Value( 43)
  val DIVERTING_DEVID               = Value( 44)
  val QUEUE_DEVID                   = Value( 45)
  val CALL_WRAPUP_DATA              = Value( 46)
  val NEW_CONNECTION_DEVID          = Value( 47)
  val TRUNK_USED_DEVID              = Value( 48)
  val AGENT_PASSWORD                = Value( 49)
  val ACTIVE_CONN_DEVID             = Value( 50)
  val FACILITY_CODE                 = Value( 51)
  val OTHER_CONN_DEVID              = Value( 52)
  val HELD_CONN_DEVID               = Value( 53)
  val RESERVED_54                   = Value( 54)
  val RESERVED_55                   = Value( 55)
  val CALL_CONN_CALLID              = Value( 56)
  val CALL_CONN_DEVID_TYPE          = Value( 57)
  val CALL_CONN_DEVID               = Value( 58)
  val CALL_DEVID_TYPE               = Value( 59)
  val CALL_DEVID                    = Value( 60)
  val CALL_DEV_CONN_STATE           = Value( 61)
  val SKILL_GROUP_NUMBER            = Value( 62)
  val SKILL_GROUP_ID                = Value( 63)
  val SKILL_GROUP_PRIORITY          = Value( 64)
  val SKILL_GROUP_STATE             = Value( 65)
  val OBJECT_NAME                   = Value( 66)
  val DTMF_STRING                   = Value( 67)
  val POSITION_ID                   = Value( 68)
  val SUPERVISOR_ID                 = Value( 69)
  val LINE_HANDLE                   = Value( 70)
  val LINE_TYPE                     = Value( 71)
  val ROUTER_CALL_KEY_DAY           = Value( 72)
  val ROUTER_CALL_KEY_CALLID        = Value( 73)
  val RESERVED_74                   = Value( 74)
  val CALL_STATE                    = Value( 75)
  val MONITORED_DEVID               = Value( 76)
  val AUTHORIZATION_CODE            = Value( 77)
  val ACCOUNT_CODE                  = Value( 78)
  val ORIGINATING_DEVID             = Value( 79)
  val ORIGINATING_LINE_ID           = Value( 80)
  val CLIENT_ADDRESS                = Value( 81)
  val NAMED_VARIABLE                = Value( 82)
  val NAMED_ARRAY                   = Value( 83)
  val CALL_CONTROL_TABLE            = Value( 84)
  val SUPERVISOR_INSTRUMENT         = Value( 85)
  val ATC_AGENT_ID                  = Value( 86)
  val AGENT_FLAGS                   = Value( 87)
  val ATC_AGENT_STATE               = Value( 88)
  val ATC_STATE_DURATION            = Value( 89)
  val AGENT_CONNECTION_DEVID        = Value( 90)
  val SUPERVISOR_CONNECTION_DEVID   = Value( 91)
  val LIST_TEAM_ID                  = Value( 92)
  val DEFAULT_DEVICE_PORT_ADDRESS   = Value( 93)
  val SERVICE_NAME                  = Value( 94)
  val CUSTOMER_PHONE_NUMBER         = Value( 95)
  val CUSTOMER_ACCOUNT_NUMBER       = Value( 96)
  val APP_PATH_ID                   = Value( 97)
  val RESERVED_98                   = Value( 98)
  val RESERVED_99                   = Value( 99)
  val RESERVED_100                  = Value(100)
  val RESERVED_101                  = Value(101)
  val RESERVED_102                  = Value(102)
  val RESERVED_103                  = Value(103)
  val RESERVED_104                  = Value(104)
  val RESERVED_105                  = Value(105)
  val RESERVED_106                  = Value(106)
  val RESERVED_107                  = Value(107)
  val RESERVED_108                  = Value(108)
  val RESERVED_109                  = Value(109)
  val ROUTER_CALL_KEY_SEQUENCE_NUM  = Value(110)
  val RESERVED_111                  = Value(111)
  val RESERVED_112                  = Value(112)
  val RESERVED_113                  = Value(113)
  val RESERVED_114                  = Value(114)
  val RESERVED_115                  = Value(115)
  val RESERVED_116                  = Value(116)
  val RESERVED_117                  = Value(117)
  val RESERVED_118                  = Value(118)
  val RESERVED_119                  = Value(119)
  val RESERVED_120                  = Value(120)
  val TRUNK_NUMBER                  = Value(121)
  val TRUNK_GROUP_NUMBER            = Value(122)
  val NEXT_AGENT_STATE              = Value(123)
  val DEQUEUE_TYPE                  = Value(124)
  val SENDING_ADDRESS               = Value(125)
  val SENDING_PORT                  = Value(126)
  val RESERVED_127                  = Value(127)
  val RESERVED_128                  = Value(128)
  val MAX_QUEUED                    = Value(129)
  val QUEUE_ID                      = Value(130)
  val CUSTOMER_ID                   = Value(131)
  val SERVICE_SKILL_TARGET_ID       = Value(132)
  val PERIPHERAL_NAME               = Value(133)
  val DESCRIPTION                   = Value(134)
  val SERVICE_MEMBER_ID             = Value(135)
  val SERVICE_MEMBER_PRIORITY       = Value(136)
  val FIRST_NAME                    = Value(137)
  val LAST_NAME                     = Value(138)
  val SKILL_GROUP                   = Value(139)
  val RESERVED_140                  = Value(140)
  val AGENT_SKILL_TARGET_ID         = Value(141)
  val SERVICE                       = Value(142)
  val RESERVED_143                  = Value(143)
  val RESERVED_144                  = Value(144)
  val RESERVED_145                  = Value(145)
  val RESERVED_146                  = Value(146)
  val RESERVED_147                  = Value(147)
  val RESERVED_148                  = Value(148)
  val RESERVED_149                  = Value(149)
  val DURATION                      = Value(150)
  val RESERVED_151                  = Value(151)
  val RESERVED_152                  = Value(152)
  val RESERVED_153                  = Value(153)
  val RESERVED_154                  = Value(154)
  val RESERVED_155                  = Value(155)
  val RESERVED_156                  = Value(156)
  val RESERVED_157                  = Value(157)
  val RESERVED_158                  = Value(158)
  val RESERVED_159                  = Value(159)
  val RESERVED_160                  = Value(160)
  val RESERVED_161                  = Value(161)
  val RESERVED_162                  = Value(162)
  val RESERVED_163                  = Value(163)
  val RESERVED_164                  = Value(164)
  val RESERVED_165                  = Value(165)
  val RESERVED_166                  = Value(166)
  val RESERVED_167                  = Value(167)
  val RESERVED_168                  = Value(168)
  val RESERVED_169                  = Value(169)
  val RESERVED_170                  = Value(170)
  val RESERVED_171                  = Value(171)
  val RESERVED_172                  = Value(172)
  val EXTENSION                     = Value(173)
  val SERVICE_LEVEL_THRESHOLD       = Value(174)
  val SERVICE_LEVEL_TYPE            = Value(175)
  val CONFIG_PARAM                  = Value(176)
  val SERVICE_CONFIG_KEY            = Value(177)
  val SKILL_GROUP_CONFIG_KEY        = Value(178)
  val AGENT_CONFIG_KEY              = Value(179)
  val DEVICE_CONFIG_KEY             = Value(180)
  val RESERVED_181                  = Value(181)
  val RESERVED_182                  = Value(182)
  val RECORD_TYPE                   = Value(183)
  val PERIPHERAL_NUMBER             = Value(184)
  val CONFIG_AGENT_SKILL_TARGET_ID  = Value(185)
  val NUM_SERVICE_MEMBERS           = Value(186)
  val SERVICE_MEMBER                = Value(187)
  val SERVICE_PRIORITY              = Value(188)
  val AGENT_TYPE                    = Value(189)
  val LOGIN_ID                      = Value(190)
  val NUM_SKILLS                    = Value(191)
  val SKILL_GROUP_SKILL_TARGET_ID   = Value(192)
  val SERVICE_ID                    = Value(193)
  val AGENT_ID_LONG                 = Value(194)
  val DEVICE_TYPE                   = Value(195)
  val RESERVED_196                  = Value(196)
  val RESERVED_197                  = Value(197)
  val ENABLE                        = Value(198)
  val DEVICEID                      = Value(199)
  val TIMEOUT                       = Value(200)
  val CURRENT_ROUTE                 = Value(201)
  val SECONDARY_CONNECTION_CALL_ID  = Value(202)
  val PRIORITY_QUEUE_NUMBER         = Value(203)
  val TEAM_NAME                     = Value(204)
  val MEMBER_TYPE                   = Value(205)
  val EVENT_DEVICE_ID               = Value(206)
  val LOGIN_NAME_V11                = Value(207)
  val PERIPHERAL_ID_V11             = Value(208)
  val CALL_TYPE_KEY_CONFIG_V11      = Value(209)
  val CALL_TYPE_ID_V11              = Value(210)
  val CUSTOMER_DEFINITION_ID_V11    = Value(211)
  val ENTERPRISE_NAME_V11           = Value(212)
  val CUR_PERIPHERAL_NUMBER         = Value(213)
  val CUR_LOGIN_ID                  = Value(214)
  val ANI_II                        = Value(215)
  val MR_DOMAIN_ID                  = Value(216)
  val CTIOS_CIL_CLIENT_ID           = Value(217)
  val SILENT_MONITOR_STATUS         = Value(218)
  val REQUESTING_DEVICE_ID          = Value(219)
  val REQUESTING_DEVICE_ID_TYPE     = Value(220)
  val PRE_CALL_INVOKE_ID            = Value(221)
  val ENTERPRISE_QUEUE_TIME         = Value(222)
  val CALL_REFERENCE_ID             = Value(223)
  val MULTI_LINE_AGENT_CONTROL      = Value(224)
  val NETWORK_CONTROLLED            = Value(225)
  val CLIENT_ADDRESS_IPV6           = Value(226)
  val SENDING_ADDRESS_IPV6          = Value(227)
  val NUM_PERIPHERALS               = Value(228)
  val COC_CONNECTION_CALL_ID        = Value(229)
  val COC_CONNECTION_DEVICE_ID_TYPE = Value(230)
  val COC_CONNECTION_DEVICE_ID      = Value(231)
  val CALL_ORIGINATED_FROM          = Value(232)
  val SET_APPDATA_CALLID            = Value(233)
  val CLIENT_SHARE_KEY              = Value(234)
  val RESERVED_235                  = Value(235)
  val RESERVED_236                  = Value(236)
  val RESERVED_237                  = Value(237)
  val RESERVED_238                  = Value(238)
  val RESERVED_239                  = Value(239)
  val RESERVED_240                  = Value(240)
  val RESERVED_241                  = Value(241)
  val RESERVED_242                  = Value(242)
  val AGENT_TEAM_NAME               = Value(243)
  val DIRECTION                     = Value(244)
  val RESERVED_245                  = Value(245)
  val RESERVED_246                  = Value(246)
  val RESERVED_247                  = Value(247)
  val RESERVED_248                  = Value(248)
  val RESERVED_249                  = Value(249)
  val RESERVED_250                  = Value(250)
  val RESERVED_251                  = Value(251)
  val RESERVED_252                  = Value(252)
  val RESERVED_253                  = Value(253)
  val RESERVED_254                  = Value(254)
  val RESERVED_255                  = Value(255)

  //
  // Tag values greater than 255 are internal use only.
  // It determines field in fixed part of CTI message.
  //
  // Unlike values from 1 through 255, actual id itself
  // doesn't have meaning.
  //
  val MessageTypeTag = Value(256)

  //
  // DON'T EDIT THIS LIST!!
  // This list is generated from TagDecoderFuncTbl.txt using gen-tag-list.sh
  //
  val
  // script generated code starts here
  ActiveConnectionCallID,
  ActiveConnectionDeviceIDType,
  AddedPartyDeviceType,
  AgentActionTag,
  AgentAvailabilityStatusTag,
  AgentConnectionCallID,
  AgentConnectionDeviceIDType,
  AgentMode,
  AgentOutCallsHeldSession,
  AgentOutCallsHeldTimeSession,
  AgentOutCallsHeldTimeTo5,
  AgentOutCallsHeldTimeToHalf,
  AgentOutCallsHeldTimeToday,
  AgentOutCallsHeldTo5,
  AgentOutCallsHeldToHalf,
  AgentOutCallsHeldToday,
  AgentOutCallsSession,
  AgentOutCallsTalkTimeSession,
  AgentOutCallsTalkTimeTo5,
  AgentOutCallsTalkTimeToHalf,
  AgentOutCallsTalkTimeToday,
  AgentOutCallsTimeSession,
  AgentOutCallsTimeTo5,
  AgentOutCallsTimeToHalf,
  AgentOutCallsTimeToday,
  AgentOutCallsTo5,
  AgentOutCallsToHalf,
  AgentOutCallsToday,
  AgentServiceReq,
  AgentStateMaskTag,
  AgentStateTag,
  AgentWorkModeTag,
  AgentsApplicationAvailable,
  AgentsAvail,
  AgentsBusyOther,
  AgentsHold,
  AgentsICMAvailable,
  AgentsLoggedOn,
  AgentsNotReady,
  AgentsReady,
  AgentsReserved,
  AgentsTalkingAutoOut,
  AgentsTalkingIn,
  AgentsTalkingOther,
  AgentsTalkingOut,
  AgentsTalkingPreview,
  AgentsTalkingReservation,
  AgentsWorkNotReady,
  AgentsWorkReady,
  AlertRings,
  AlertingDeviceType,
  AllocationStateTag,
  AnswerDetectControl1,
  AnswerDetectControl2,
  AnswerDetectModeTag,
  AnswerDetectTime,
  AnsweringDeviceType,
  AnsweringMachineTag,
  AutoOutCallsHeldSession,
  AutoOutCallsHeldTimeSession,
  AutoOutCallsHeldTimeTo5,
  AutoOutCallsHeldTimeToHalf,
  AutoOutCallsHeldTimeToday,
  AutoOutCallsHeldTo5,
  AutoOutCallsHeldToHalf,
  AutoOutCallsHeldToday,
  AutoOutCallsSession,
  AutoOutCallsTalkTimeSession,
  AutoOutCallsTalkTimeTo5,
  AutoOutCallsTalkTimeToHalf,
  AutoOutCallsTalkTimeToday,
  AutoOutCallsTimeSession,
  AutoOutCallsTimeTo5,
  AutoOutCallsTimeToHalf,
  AutoOutCallsTimeToday,
  AutoOutCallsTo5,
  AutoOutCallsToHalf,
  AutoOutCallsToday,
  AutoRecordOnEmergency,
  AvailTimeSession,
  AvailTimeTo5,
  AvailTimeToHalf,
  AvailTimeToday,
  BargeInCallsSession,
  BargeInCallsTo5,
  BargeInCallsToHalf,
  BargeInCallsToday,
  BitRate,
  CallControlSupported,
  CallEventsSupported,
  CallMannerTypeTag,
  CallMsgMask,
  CallOptionTag,
  CallPlacementTypeTag,
  CallTypeID,
  CallTypeTag,
  CallVariableMaskTag,
  CalledDeviceType,
  CalledPartyDisposition,
  CallingDeviceType,
  CallsQ5,
  CallsQHalf,
  CallsQNow,
  CallsQTime5,
  CallsQTimeHalf,
  CallsQTimeNow,
  CallsQTimeToday,
  CallsQToday,
  CampaignID,
  ClassOfDeviceTag,
  ClientPort,
  ConfigMsgMask,
  ConfigOperationTag,
  ConnectionCallID,
  ConnectionDeviceIDTypeTag,
  ConsultTypeTag,
  ControllerDeviceType,
  DeskSettingsMaskTag,
  DestinationCountryTag,
  Distribution,
  DivertingDeviceType,
  EchoCancellation,
  EmergencyCallMethod,
  EmergencyCallsSession,
  EmergencyCallsTo5,
  EmergencyCallsToHalf,
  EmergencyCallsToday,
  EventCauseTag,
  EventCodeTag,
  EventDeviceType,
  EventReasonCode,
  FacilityTypeTag,
  FailingDeviceType,
  FailureCode,
  ForcedFlagTag,
  HandledCallsAfterCallTimeSession,
  HandledCallsAfterCallTimeTo5,
  HandledCallsAfterCallTimeToHalf,
  HandledCallsAfterCallTimeToday,
  HandledCallsSession,
  HandledCallsTalkTimeSession,
  HandledCallsTalkTimeTo5,
  HandledCallsTalkTimeToHalf,
  HandledCallsTalkTimeToday,
  HandledCallsTimeSession,
  HandledCallsTimeTo5,
  HandledCallsTimeToHalf,
  HandledCallsTimeToday,
  HandledCallsTo5,
  HandledCallsToHalf,
  HandledCallsToday,
  HeldConnectionCallID,
  HeldConnectionDeviceIDType,
  HoldingDeviceType,
  ICMAgentID,
  ICMAvailableTimeSession,
  ICMAvailableTimeToday,
  ICMCentralControllerTime,
  IdleTimeout,
  IncomingCallsHeldSession,
  IncomingCallsHeldTimeSession,
  IncomingCallsHeldTimeTo5,
  IncomingCallsHeldTimeToHalf,
  IncomingCallsHeldTimeToday,
  IncomingCallsHeldTo5,
  IncomingCallsHeldToHalf,
  IncomingCallsHeldToday,
  InterceptCallsSession,
  InterceptCallsTo5,
  InterceptCallsToHalf,
  InterceptCallsToday,
  InternalCallsHeldSession,
  InternalCallsHeldTimeSession,
  InternalCallsHeldTimeTo5,
  InternalCallsHeldTimeToHalf,
  InternalCallsHeldTimeToday,
  InternalCallsHeldTo5,
  InternalCallsHeldToHalf,
  InternalCallsHeldToday,
  InternalCallsRcvdSession,
  InternalCallsRcvdTimeSession,
  InternalCallsRcvdTimeTo5,
  InternalCallsRcvdTimeToHalf,
  InternalCallsRcvdTimeToday,
  InternalCallsRcvdTo5,
  InternalCallsRcvdToHalf,
  InternalCallsRcvdToday,
  InternalCallsSession,
  InternalCallsTimeSession,
  InternalCallsTimeToday,
  InternalCallsToday,
  InvokeID,
  LastRedirectDeviceType,
  LineHandle,
  LineTypeTag,
  LocalConnectionStateTag,
  LoggedOnTimeSession,
  LoggedOnTimeTo5,
  LoggedOnTimeToHalf,
  LoggedOnTimeToday,
  LogoutNonActivityTime,
  LongestCallQ5,
  LongestCallQHalf,
  LongestCallQNow,
  LongestCallQToday,
  LongestRouterCallQNow,
  MRDID,
  MakeCallSetup,
  MaxActiveCalls,
  MaxDeviceInConference,
  MaxHeldCalls,
  MaxTaskLimit,
  MonitorCallsSession,
  MonitorCallsTo5,
  MonitorCallsToHalf,
  MonitorCallsToday,
  MonitorID,
  MonitoredDeviceType,
  More,
  NewConnectionCallID,
  NewConnectionDeviceIDType,
  NotReadyTimeSession,
  NotReadyTimeTo5,
  NotReadyTimeToHalf,
  NotReadyTimeToday,
  NumCTIClients,
  NumCallDevices,
  NumCalls,
  NumFltSkillGroups,
  NumLines,
  NumNamedArrays,
  NumNamedVariables,
  NumParties,
  NumQueued,
  NumSkillGroups,
  NumTasks,
  NumberOfAgentTeams,
  NumberOfAgents,
  OtherConnectionCallID,
  OtherConnectionDeviceIDType,
  OtherFeaturesSupported,
  PGStatus,
  PacketSize,
  PauseDuration,
  PayloadType,
  PeripheralErrorCode,
  PeripheralID,
  PeripheralOnline,
  PeripheralTypeTag,
  PostRoute,
  PreviewCallsHeldSession,
  PreviewCallsHeldTimeSession,
  PreviewCallsHeldTimeTo5,
  PreviewCallsHeldTimeToHalf,
  PreviewCallsHeldTimeToday,
  PreviewCallsHeldTo5,
  PreviewCallsHeldToHalf,
  PreviewCallsHeldToday,
  PreviewCallsSession,
  PreviewCallsTalkTimeSession,
  PreviewCallsTalkTimeTo5,
  PreviewCallsTalkTimeToHalf,
  PreviewCallsTalkTimeToday,
  PreviewCallsTimeSession,
  PreviewCallsTimeTo5,
  PreviewCallsTimeToHalf,
  PreviewCallsTimeToday,
  PreviewCallsTo5,
  PreviewCallsToHalf,
  PreviewCallsToday,
  PrimaryCallID,
  PrimaryDeviceIDType,
  Priority,
  QualityRecordingRate,
  QueryRuleID,
  QueueDeviceType,
  RTPDirectionTag,
  RTPTypeTag,
  RawBytes,
  RecordingMode,
  RegisteredServiceID,
  ReleasingDeviceType,
  Reservation,
  ReservationCallsHeldSession,
  ReservationCallsHeldTimeSession,
  ReservationCallsHeldTimeTo5,
  ReservationCallsHeldTimeToHalf,
  ReservationCallsHeldTimeToday,
  ReservationCallsHeldTo5,
  ReservationCallsHeldToHalf,
  ReservationCallsHeldToday,
  ReservationCallsSession,
  ReservationCallsTalkTimeSession,
  ReservationCallsTalkTimeTo5,
  ReservationCallsTalkTimeToHalf,
  ReservationCallsTalkTimeToday,
  ReservationCallsTimeSession,
  ReservationCallsTimeTo5,
  ReservationCallsTimeToHalf,
  ReservationCallsTimeToday,
  ReservationCallsTo5,
  ReservationCallsToHalf,
  ReservationCallsToday,
  Reserved1,
  Reserved2,
  Reserved3,
  Reserved16,
  RetrievingDeviceType,
  RingNoAnswerDN,
  RingNoAnswerTime,
  RoutableTimeSession,
  RoutableTimeToday,
  RouterCallsQNow,
  SecondaryCallID,
  SecondaryConnectionCallID,
  SecondaryDeviceIDType,
  SegmentNumber,
  ServerData,
  ServerModeTag,
  ServiceGranted,
  ServiceID,
  ServiceNumber,
  ServiceRequested,
  SessionID,
  SilentMonitorAudibleIndication,
  SilentMonitorWarningMessage,
  SkillGroupID,
  SkillGroupNumber,
  SkillGroupPriority,
  SkillGroupState,
  SnapshotDeviceType,
  ClientEventStateTag,
  StateDuration,
  StatusCodeTag,
  SupervisorAssistCallMethod,
  SupervisorConnectionCallID,
  SupervisorConnectionDeviceIDType,
  SupervisorID,
  SupervisoryActionTag,
  SystemEventArg1,
  SystemEventArg2,
  SystemEventArg3,
  SystemEventIDTag,
  TeamID,
  ToneDuration,
  TransferConferenceSetup,
  TransferredDeviceType,
  TransferringDeviceType,
  TrunkUsedDeviceType,
  TypeOfDeviceTag,
  VersionNumber,
  WhisperCallsSession,
  WhisperCallsTo5,
  WhisperCallsToHalf,
  WhisperCallsToday,
  WorkModeTimer,
  WrapupDataIncomingMode,
  WrapupDataOutgoingMode,
  // script generated code ends here

  ZZZZInternalUseOnly
  = Value


  //
  // Decode a fixed part field
  //
  def decodeFixedField(tag: Tag, body: ByteString): ((Tag, Any), Int) =
    fxTagToDecodeFunc(tag.id - ActiveConnectionCallID.id)(tag, body)

  //
  // Decoder functions of fixed part for simple types
  //
  private def decFxRaw(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body), body.size)

  private def decFxInt(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.toInt), 4)

  private def decFxShort(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.toShort), 2)

  private def decFxByte(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.head), 1)

  private def decFxIntBool(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.toInt != 0), 4)

  private def decFxShortBool(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.toShort != 0), 2)

  private def decFxIntBitSet(tag: Tag, body: ByteString): ((Tag, Any), Int) =
    ((tag, BitSet fromBitMask Array(body.toInt.toLong)), 4)

  private def decFxShortBitSet(tag: Tag, body: ByteString): ((Tag, Any), Int) =
    ((tag, BitSet fromBitMask Array(body.toShort.toLong)), 2)

  //
  // DON'T EDIT THIS TABLE!!
  // This table is generated from TagFuncTbl.txt using gen-dec-table.sh
  //
  // Jump table from Tag to decoding function for floating part
  //
  private val fxTagToDecodeFunc: Array[(Tag, ByteString) => ((Tag, Any), Int)] = Array(
  // script generated code starts here
    decFxInt, 	// ActiveConnectionCallID
    ConnectionDeviceIDType.decode, 	// ActiveConnectionDeviceIDType
    DeviceIDType.decode, 	// AddedPartyDeviceType
    AgentGreetingAction.decode, 	// AgentActionTag
    AgentAvailabilityStatus.decode, 	// AgentAvailabilityStatusTag
    decFxInt, 	// AgentConnectionCallID
    ConnectionDeviceIDType.decode, 	// AgentConnectionDeviceIDType
    decFxShortBool, 	// AgentMode
    decFxInt, 	// AgentOutCallsHeldSession
    decFxInt, 	// AgentOutCallsHeldTimeSession
    decFxInt, 	// AgentOutCallsHeldTimeTo5
    decFxInt, 	// AgentOutCallsHeldTimeToHalf
    decFxInt, 	// AgentOutCallsHeldTimeToday
    decFxInt, 	// AgentOutCallsHeldTo5
    decFxInt, 	// AgentOutCallsHeldToHalf
    decFxInt, 	// AgentOutCallsHeldToday
    decFxInt, 	// AgentOutCallsSession
    decFxInt, 	// AgentOutCallsTalkTimeSession
    decFxInt, 	// AgentOutCallsTalkTimeTo5
    decFxInt, 	// AgentOutCallsTalkTimeToHalf
    decFxInt, 	// AgentOutCallsTalkTimeToday
    decFxInt, 	// AgentOutCallsTimeSession
    decFxInt, 	// AgentOutCallsTimeTo5
    decFxInt, 	// AgentOutCallsTimeToHalf
    decFxInt, 	// AgentOutCallsTimeToday
    decFxInt, 	// AgentOutCallsTo5
    decFxInt, 	// AgentOutCallsToHalf
    decFxInt, 	// AgentOutCallsToday
    decFxIntBitSet, 	// AgentServiceReq
    decFxIntBitSet, 	// AgentStateMaskTag
    AgentState.decode, 	// AgentStateTag
    AgentWorkMode.decode, 	// AgentWorkModeTag
    decFxInt, 	// AgentsApplicationAvailable
    decFxInt, 	// AgentsAvail
    decFxInt, 	// AgentsBusyOther
    decFxInt, 	// AgentsHold
    decFxInt, 	// AgentsICMAvailable
    decFxInt, 	// AgentsLoggedOn
    decFxInt, 	// AgentsNotReady
    decFxInt, 	// AgentsReady
    decFxInt, 	// AgentsReserved
    decFxInt, 	// AgentsTalkingAutoOut
    decFxInt, 	// AgentsTalkingIn
    decFxInt, 	// AgentsTalkingOther
    decFxInt, 	// AgentsTalkingOut
    decFxInt, 	// AgentsTalkingPreview
    decFxInt, 	// AgentsTalkingReservation
    decFxInt, 	// AgentsWorkNotReady
    decFxInt, 	// AgentsWorkReady
    decFxShort, 	// AlertRings
    DeviceIDType.decode, 	// AlertingDeviceType
    AllocationState.decode, 	// AllocationStateTag
    decFxInt, 	// AnswerDetectControl1
    decFxInt, 	// AnswerDetectControl2
    AnswerDetectMode.decode, 	// AnswerDetectModeTag
    decFxShort, 	// AnswerDetectTime
    DeviceIDType.decode, 	// AnsweringDeviceType
    AnsweringMachine.decode, 	// AnsweringMachineTag
    decFxInt, 	// AutoOutCallsHeldSession
    decFxInt, 	// AutoOutCallsHeldTimeSession
    decFxInt, 	// AutoOutCallsHeldTimeTo5
    decFxInt, 	// AutoOutCallsHeldTimeToHalf
    decFxInt, 	// AutoOutCallsHeldTimeToday
    decFxInt, 	// AutoOutCallsHeldTo5
    decFxInt, 	// AutoOutCallsHeldToHalf
    decFxInt, 	// AutoOutCallsHeldToday
    decFxInt, 	// AutoOutCallsSession
    decFxInt, 	// AutoOutCallsTalkTimeSession
    decFxInt, 	// AutoOutCallsTalkTimeTo5
    decFxInt, 	// AutoOutCallsTalkTimeToHalf
    decFxInt, 	// AutoOutCallsTalkTimeToday
    decFxInt, 	// AutoOutCallsTimeSession
    decFxInt, 	// AutoOutCallsTimeTo5
    decFxInt, 	// AutoOutCallsTimeToHalf
    decFxInt, 	// AutoOutCallsTimeToday
    decFxInt, 	// AutoOutCallsTo5
    decFxInt, 	// AutoOutCallsToHalf
    decFxInt, 	// AutoOutCallsToday
    decFxIntBool, 	// AutoRecordOnEmergency
    decFxInt, 	// AvailTimeSession
    decFxInt, 	// AvailTimeTo5
    decFxInt, 	// AvailTimeToHalf
    decFxInt, 	// AvailTimeToday
    decFxInt, 	// BargeInCallsSession
    decFxInt, 	// BargeInCallsTo5
    decFxInt, 	// BargeInCallsToHalf
    decFxInt, 	// BargeInCallsToday
    decFxInt, 	// BitRate
    decFxIntBitSet, 	// CallControlSupported
    decFxIntBitSet, 	// CallEventsSupported
    CallMannerType.decode, 	// CallMannerTypeTag
    decFxIntBitSet, 	// CallMsgMask
    CallOption.decode, 	// CallOptionTag
    CallPlacementType.decode, 	// CallPlacementTypeTag
    decFxInt, 	// CallTypeID
    CallType.decode, 	// CallTypeTag
    decFxShortBitSet, 	// CallVariableMaskTag
    DeviceIDType.decode, 	// CalledDeviceType
    DispositionCodeValue.decode, 	// CalledPartyDisposition
    DeviceIDType.decode, 	// CallingDeviceType
    decFxInt, 	// CallsQ5
    decFxInt, 	// CallsQHalf
    decFxInt, 	// CallsQNow
    decFxInt, 	// CallsQTime5
    decFxInt, 	// CallsQTimeHalf
    decFxInt, 	// CallsQTimeNow
    decFxInt, 	// CallsQTimeToday
    decFxInt, 	// CallsQToday
    decFxInt, 	// CampaignID
    decFxShortBitSet, 	// ClassOfDeviceTag
    decFxInt, 	// ClientPort
    decFxIntBitSet, 	// ConfigMsgMask
    ConfigOperation.decode, 	// ConfigOperationTag
    decFxInt, 	// ConnectionCallID
    ConnectionDeviceIDType.decode, 	// ConnectionDeviceIDTypeTag
    ConsultType.decode, 	// ConsultTypeTag
    DeviceIDType.decode, 	// ControllerDeviceType
    decFxIntBitSet, 	// DeskSettingsMaskTag
    DestinationCountry.decode, 	// DestinationCountryTag
    DistributionValue.decode, 	// Distribution
    DeviceIDType.decode, 	// DivertingDeviceType
    decFxShortBool, 	// EchoCancellation
    decFxIntBool, 	// EmergencyCallMethod
    decFxInt, 	// EmergencyCallsSession
    decFxInt, 	// EmergencyCallsTo5
    decFxInt, 	// EmergencyCallsToHalf
    decFxInt, 	// EmergencyCallsToday
    EventCause.decode, 	// EventCauseTag
    EventCode.decode, 	// EventCodeTag
    DeviceIDType.decode, 	// EventDeviceType
    decFxShort, 	// EventReasonCode
    FacilityType.decode, 	// FacilityTypeTag
    DeviceIDType.decode, 	// FailingDeviceType
    ControlFailureCode.decode, 	// FailureCode
    ForcedFlag.decode, 	// ForcedFlagTag
    decFxInt, 	// HandledCallsAfterCallTimeSession
    decFxInt, 	// HandledCallsAfterCallTimeTo5
    decFxInt, 	// HandledCallsAfterCallTimeToHalf
    decFxInt, 	// HandledCallsAfterCallTimeToday
    decFxInt, 	// HandledCallsSession
    decFxInt, 	// HandledCallsTalkTimeSession
    decFxInt, 	// HandledCallsTalkTimeTo5
    decFxInt, 	// HandledCallsTalkTimeToHalf
    decFxInt, 	// HandledCallsTalkTimeToday
    decFxInt, 	// HandledCallsTimeSession
    decFxInt, 	// HandledCallsTimeTo5
    decFxInt, 	// HandledCallsTimeToHalf
    decFxInt, 	// HandledCallsTimeToday
    decFxInt, 	// HandledCallsTo5
    decFxInt, 	// HandledCallsToHalf
    decFxInt, 	// HandledCallsToday
    decFxInt, 	// HeldConnectionCallID
    ConnectionDeviceIDType.decode, 	// HeldConnectionDeviceIDType
    DeviceIDType.decode, 	// HoldingDeviceType
    decFxInt, 	// ICMAgentID
    decFxInt, 	// ICMAvailableTimeSession
    decFxInt, 	// ICMAvailableTimeToday
    decFxInt, 	// ICMCentralControllerTime
    decFxInt, 	// IdleTimeout
    decFxInt, 	// IncomingCallsHeldSession
    decFxInt, 	// IncomingCallsHeldTimeSession
    decFxInt, 	// IncomingCallsHeldTimeTo5
    decFxInt, 	// IncomingCallsHeldTimeToHalf
    decFxInt, 	// IncomingCallsHeldTimeToday
    decFxInt, 	// IncomingCallsHeldTo5
    decFxInt, 	// IncomingCallsHeldToHalf
    decFxInt, 	// IncomingCallsHeldToday
    decFxInt, 	// InterceptCallsSession
    decFxInt, 	// InterceptCallsTo5
    decFxInt, 	// InterceptCallsToHalf
    decFxInt, 	// InterceptCallsToday
    decFxInt, 	// InternalCallsHeldSession
    decFxInt, 	// InternalCallsHeldTimeSession
    decFxInt, 	// InternalCallsHeldTimeTo5
    decFxInt, 	// InternalCallsHeldTimeToHalf
    decFxInt, 	// InternalCallsHeldTimeToday
    decFxInt, 	// InternalCallsHeldTo5
    decFxInt, 	// InternalCallsHeldToHalf
    decFxInt, 	// InternalCallsHeldToday
    decFxInt, 	// InternalCallsRcvdSession
    decFxInt, 	// InternalCallsRcvdTimeSession
    decFxInt, 	// InternalCallsRcvdTimeTo5
    decFxInt, 	// InternalCallsRcvdTimeToHalf
    decFxInt, 	// InternalCallsRcvdTimeToday
    decFxInt, 	// InternalCallsRcvdTo5
    decFxInt, 	// InternalCallsRcvdToHalf
    decFxInt, 	// InternalCallsRcvdToday
    decFxInt, 	// InternalCallsSession
    decFxInt, 	// InternalCallsTimeSession
    decFxInt, 	// InternalCallsTimeToday
    decFxInt, 	// InternalCallsToday
    decFxInt, 	// InvokeID
    DeviceIDType.decode, 	// LastRedirectDeviceType
    decFxShort, 	// LineHandle
    LineType.decode, 	// LineTypeTag
    LocalConnectionState.decode, 	// LocalConnectionStateTag
    decFxInt, 	// LoggedOnTimeSession
    decFxInt, 	// LoggedOnTimeTo5
    decFxInt, 	// LoggedOnTimeToHalf
    decFxInt, 	// LoggedOnTimeToday
    decFxInt, 	// LogoutNonActivityTime
    decFxInt, 	// LongestCallQ5
    decFxInt, 	// LongestCallQHalf
    decFxInt, 	// LongestCallQNow
    decFxInt, 	// LongestCallQToday
    decFxInt, 	// LongestRouterCallQNow
    decFxInt, 	// MRDID
    decFxIntBitSet, 	// MakeCallSetup
    decFxShort, 	// MaxActiveCalls
    decFxShort, 	// MaxDeviceInConference
    decFxShort, 	// MaxHeldCalls
    decFxInt, 	// MaxTaskLimit
    decFxInt, 	// MonitorCallsSession
    decFxInt, 	// MonitorCallsTo5
    decFxInt, 	// MonitorCallsToHalf
    decFxInt, 	// MonitorCallsToday
    decFxInt, 	// MonitorID
    DeviceIDType.decode, 	// MonitoredDeviceType
    decFxShortBool, 	// More
    decFxInt, 	// NewConnectionCallID
    ConnectionDeviceIDType.decode, 	// NewConnectionDeviceIDType
    decFxInt, 	// NotReadyTimeSession
    decFxInt, 	// NotReadyTimeTo5
    decFxInt, 	// NotReadyTimeToHalf
    decFxInt, 	// NotReadyTimeToday
    decFxShort, 	// NumCTIClients
    decFxShort, 	// NumCallDevices
    decFxShort, 	// NumCalls
    decFxShort, 	// NumFltSkillGroups
    decFxShort, 	// NumLines
    decFxShort, 	// NumNamedArrays
    decFxShort, 	// NumNamedVariables
    decFxShort, 	// NumParties
    decFxShort, 	// NumQueued
    decFxShort, 	// NumSkillGroups
    decFxInt, 	// NumTasks
    decFxShort, 	// NumberOfAgentTeams
    decFxShort, 	// NumberOfAgents
    decFxInt, 	// OtherConnectionCallID
    ConnectionDeviceIDType.decode, 	// OtherConnectionDeviceIDType
    decFxIntBitSet, 	// OtherFeaturesSupported
    decFxIntBitSet, 	// PGStatus
    decFxInt, 	// PacketSize
    decFxInt, 	// PauseDuration
    decFxShort, 	// PayloadType
    decFxInt, 	// PeripheralErrorCode
    decFxInt, 	// PeripheralID
    decFxShortBool, 	// PeripheralOnline
    PeripheralType.decode, 	// PeripheralTypeTag
    decFxShortBool, 	// PostRoute
    decFxInt, 	// PreviewCallsHeldSession
    decFxInt, 	// PreviewCallsHeldTimeSession
    decFxInt, 	// PreviewCallsHeldTimeTo5
    decFxInt, 	// PreviewCallsHeldTimeToHalf
    decFxInt, 	// PreviewCallsHeldTimeToday
    decFxInt, 	// PreviewCallsHeldTo5
    decFxInt, 	// PreviewCallsHeldToHalf
    decFxInt, 	// PreviewCallsHeldToday
    decFxInt, 	// PreviewCallsSession
    decFxInt, 	// PreviewCallsTalkTimeSession
    decFxInt, 	// PreviewCallsTalkTimeTo5
    decFxInt, 	// PreviewCallsTalkTimeToHalf
    decFxInt, 	// PreviewCallsTalkTimeToday
    decFxInt, 	// PreviewCallsTimeSession
    decFxInt, 	// PreviewCallsTimeTo5
    decFxInt, 	// PreviewCallsTimeToHalf
    decFxInt, 	// PreviewCallsTimeToday
    decFxInt, 	// PreviewCallsTo5
    decFxInt, 	// PreviewCallsToHalf
    decFxInt, 	// PreviewCallsToday
    decFxInt, 	// PrimaryCallID
    DeviceIDType.decode, 	// PrimaryDeviceIDType
    decFxShortBool, 	// Priority
    decFxInt, 	// QualityRecordingRate
    decFxInt, 	// QueryRuleID
    DeviceIDType.decode, 	// QueueDeviceType
    RTPDirection.decode, 	// RTPDirectionTag
    RTPType.decode, 	// RTPTypeTag
    decFxRaw, 	// RawBytes
    decFxIntBool, 	// RecordingMode
    decFxInt, 	// RegisteredServiceID
    DeviceIDType.decode, 	// ReleasingDeviceType
    decFxShortBool, 	// Reservation
    decFxInt, 	// ReservationCallsHeldSession
    decFxInt, 	// ReservationCallsHeldTimeSession
    decFxInt, 	// ReservationCallsHeldTimeTo5
    decFxInt, 	// ReservationCallsHeldTimeToHalf
    decFxInt, 	// ReservationCallsHeldTimeToday
    decFxInt, 	// ReservationCallsHeldTo5
    decFxInt, 	// ReservationCallsHeldToHalf
    decFxInt, 	// ReservationCallsHeldToday
    decFxInt, 	// ReservationCallsSession
    decFxInt, 	// ReservationCallsTalkTimeSession
    decFxInt, 	// ReservationCallsTalkTimeTo5
    decFxInt, 	// ReservationCallsTalkTimeToHalf
    decFxInt, 	// ReservationCallsTalkTimeToday
    decFxInt, 	// ReservationCallsTimeSession
    decFxInt, 	// ReservationCallsTimeTo5
    decFxInt, 	// ReservationCallsTimeToHalf
    decFxInt, 	// ReservationCallsTimeToday
    decFxInt, 	// ReservationCallsTo5
    decFxInt, 	// ReservationCallsToHalf
    decFxInt, 	// ReservationCallsToday
    decFxInt, 	// Reserved1
    decFxInt, 	// Reserved2
    decFxInt, 	// Reserved3
    decFxShort, 	// Reserved16
    DeviceIDType.decode, 	// RetrievingDeviceType
    decFxInt, 	// RingNoAnswerDN
    decFxInt, 	// RingNoAnswerTime
    decFxInt, 	// RoutableTimeSession
    decFxInt, 	// RoutableTimeToday
    decFxInt, 	// RouterCallsQNow
    decFxInt, 	// SecondaryCallID
    decFxInt, 	// SecondaryConnectionCallID
    DeviceIDType.decode, 	// SecondaryDeviceIDType
    decFxShort, 	// SegmentNumber
    decFxInt, 	// ServerData
    ServerMode.decode, 	// ServerModeTag
    decFxIntBitSet, 	// ServiceGranted
    decFxInt, 	// ServiceID
    decFxInt, 	// ServiceNumber
    decFxIntBitSet, 	// ServiceRequested
    decFxInt, 	// SessionID
    decFxIntBool, 	// SilentMonitorAudibleIndication
    decFxIntBool, 	// SilentMonitorWarningMessage
    decFxInt, 	// SkillGroupID
    decFxInt, 	// SkillGroupNumber
    decFxShort, 	// SkillGroupPriority
    AgentState.decode, 	// SkillGroupState
    DeviceIDType.decode, 	// SnapshotDeviceType
    ClientEventReportState.decode, 	// ClientEventStateTag
    decFxInt, 	// StateDuration
    StatusCode.decode, 	// StatusCodeTag
    decFxIntBool, 	// SupervisorAssistCallMethod
    decFxInt, 	// SupervisorConnectionCallID
    ConnectionDeviceIDType.decode, 	// SupervisorConnectionDeviceIDType
    decFxInt, 	// SupervisorID
    SupervisoryAction.decode, 	// SupervisoryActionTag
    decFxInt, 	// SystemEventArg1
    decFxInt, 	// SystemEventArg2
    decFxInt, 	// SystemEventArg3
    SystemEventID.decode, 	// SystemEventIDTag
    decFxInt, 	// TeamID
    decFxShort, 	// ToneDuration
    decFxIntBitSet, 	// TransferConferenceSetup
    DeviceIDType.decode, 	// TransferredDeviceType
    DeviceIDType.decode, 	// TransferringDeviceType
    DeviceIDType.decode, 	// TrunkUsedDeviceType
    TypeOfDevice.decode, 	// TypeOfDeviceTag
    decFxInt, 	// VersionNumber
    decFxInt, 	// WhisperCallsSession
    decFxInt, 	// WhisperCallsTo5
    decFxInt, 	// WhisperCallsToHalf
    decFxInt, 	// WhisperCallsToday
    decFxInt, 	// WorkModeTimer
    WrapupDataMode.decode, 	// WrapupDataIncomingMode
    WrapupDataMode.decode, 	// WrapupDataOutgoingMode
    // script generated code ends here

    decFxRaw // ZZZZInternalUseOnly
 )


  //
  // Decode a floating part field
  //
  def decodeFloatingField(tag: Tag, body: ByteString): ((Tag, Any), Int) = flTagToDecodeFunc(tag.id)(tag, body)

  //
  // Decoder functions of floating part for simple types
  //
  private def decFlRaw(tag: Tag, body: ByteString): ((Tag, Any), Int) = {
    val len = body.head
    ((tag, body.tail.take(len)), len + 1)
  }

  private def decFlStr(tag: Tag, body: ByteString): ((Tag, Any), Int) = {
    val len = body.head
    ((tag, body.tail.toString(len)), len + 1)
  }

  private def decFlInt(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.tail.toInt), 5)

  private def decFlShort(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.tail.toShort), 3)

  private def decFlByte(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body(1)), 2)

  private def decFlShortBool(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.tail.toShort != 0), 3)

  private def decFlShortMask(tag: Tag, body: ByteString): ((Tag, Any), Int) = {
    ((tag, BitSet fromBitMask Array(body.tail.toShort.toLong)), 3)
  }

  private def decFlNamedVar(tag: Tag, body: ByteString): ((Tag, Any), Int) = {
    val len = body.head
    ((tag, body.tail.toNamedVar(len)), len + 1)
  }

  private def decFlNamedArr(tag: Tag, body: ByteString): ((Tag, Any), Int) = {
    val len = body.head
    ((tag, body.tail.toNamedArray(len)), len + 1)
  }

  //
  // Jump table from Tag to decoding function for floating part
  //
  private val flTagToDecodeFunc: Array[(Tag, ByteString) => ((Tag, Any), Int)] = Array(
    decFlRaw,       //   0: Invalid
    decFlStr,       //   1: CLIENT_ID
    decFlRaw,       //   2: CLIENT_PASSWORD
    decFlStr,       //   3: CLIENT_SIGNATURE
    decFlStr,       //   4: AGENT_EXTENSION
    decFlStr,       //   5: AGENT_ID
    decFlStr,       //   6: AGENT_INSTRUMENT
    decFlStr,       //   7: TEXT
    decFlStr,       //   8: ANI
    decFlRaw,       //   9: UUI
    decFlStr,       //  10: DNIS
    decFlStr,       //  11: DIALED_NUMBER
    decFlStr,       //  12: CED
    decFlStr,       //  13: CALL_VAR_1
    decFlStr,       //  14: CALL_VAR_2
    decFlStr,       //  15: CALL_VAR_3
    decFlStr,       //  16: CALL_VAR_4
    decFlStr,       //  17: CALL_VAR_5
    decFlStr,       //  18: CALL_VAR_6
    decFlStr,       //  19: CALL_VAR_7
    decFlStr,       //  20: CALL_VAR_8
    decFlStr,       //  21: CALL_VAR_9
    decFlStr,       //  22: CALL_VAR_10
    decFlStr,       //  23: CTI_CLIENT_SIGNATURE
    decFlInt,       //  24: CTI_CLIENT_TIMESTAMP
    decFlStr,       //  25: CONNECTION_DEVID
    decFlStr,       //  26: ALERTING_DEVID
    decFlStr,       //  27: CALLING_DEVID
    decFlStr,       //  28: CALLED_DEVID
    decFlStr,       //  29: LAST_REDIRECT_DEVID
    decFlStr,       //  30: ANSWERING_DEVID
    decFlStr,       //  31: HOLDING_DEVID
    decFlStr,       //  32: RETRIEVING_DEVID
    decFlStr,       //  33: RELEASING_DEVID
    decFlStr,       //  34: FAILING_DEVID
    decFlStr,       //  35: PRIMARY_DEVID
    decFlStr,       //  36: SECONDARY_DEVID
    decFlStr,       //  37: CONTROLLER_DEVID
    decFlStr,       //  38: ADDED_PARTY_DEVID
    decFlInt,       //  39: PARTY_CALLID
    DeviceIDType.decodeFloat,       //  40: PARTY_DEVID_TYPE
    decFlStr,       //  41: PARTY_DEVID
    decFlStr,       //  42: TRANSFERRING_DEVID
    decFlStr,       //  43: TRANSFERRED_DEVID
    decFlStr,       //  44: DIVERTING_DEVID
    decFlStr,       //  45: QUEUE_DEVID
    decFlStr,       //  46: CALL_WRAPUP_DATA
    decFlStr,       //  47: NEW_CONNECTION_DEVID
    decFlStr,       //  48: TRUNK_USED_DEVID
    decFlStr,       //  49: AGENT_PASSWORD
    decFlStr,       //  50: ACTIVE_CONN_DEVID
    decFlStr,       //  51: FACILITY_CODE
    decFlStr,       //  52: OTHER_CONN_DEVID
    decFlStr,       //  53: HELD_CONN_DEVID
    decFlRaw,       //  54: RESERVED_54
    decFlRaw,       //  55: RESERVED_55
    decFlInt,       //  56: CALL_CONN_CALLID
    ConnectionDeviceIDType.decodeFloat,     //  57: CALL_CONN_DEVID_TYPE
    decFlStr,       //  58: CALL_CONN_DEVID
    decFlShort,     //  59: CALL_DEVID_TYPE
    decFlStr,       //  60: CALL_DEVID
    decFlShort,     //  61: CALL_DEV_CONN_STATE
    decFlInt,       //  62: SKILL_GROUP_NUMBER
    decFlInt,       //  63: SKILL_GROUP_ID
    decFlShort,     //  64: SKILL_GROUP_PRIORITY
    AgentState.decodeFloat, //  65: SKILL_GROUP_STATE
    decFlStr,       //  66: OBJECT_NAME
    decFlStr,       //  67: DTMF_STRING
    decFlStr,       //  68: POSITION_ID
    decFlStr,       //  69: SUPERVISOR_ID
    decFlShort,     //  70: LINE_HANDLE
    LineType.decodeFloat,   //  71: LINE_TYPE
    decFlInt,       //  72: ROUTER_CALL_KEY_DAY
    decFlInt,       //  73: ROUTER_CALL_KEY_CALLID
    decFlRaw,       //  74: RESERVED_74
    LocalConnectionState.decodeFloat,       //  75: CALL_STATE
    decFlStr,       //  76: MONITORED_DEVID
    decFlStr,       //  77: AUTHORIZATION_CODE
    decFlStr,       //  78: ACCOUNT_CODE
    decFlStr,       //  79: ORIGINATING_DEVID
    decFlStr,       //  80: ORIGINATING_LINE_ID
    decFlStr,       //  81: CLIENT_ADDRESS
    decFlNamedVar,  //  82: NAMED_VARIABLE
    decFlNamedArr,  //  83: NAMED_ARRAY
    decFlStr,       //  84: CALL_CONTROL_TABLE
    decFlStr,       //  85: SUPERVISOR_INSTRUMENT
    decFlStr,       //  86: ATC_AGENT_ID
    decFlShortMask, //  87: AGENT_FLAGS
    AgentState.decodeFloat, //  88: ATC_AGENT_STATE
    decFlInt,       //  89: ATC_STATE_DURATION
    decFlStr,       //  90: AGENT_CONNECTION_DEVID
    decFlStr,       //  91: SUPERVISOR_CONNECTION_DEVID
    decFlInt,       //  92: LIST_TEAM_ID
    decFlStr,       //  93: DEFAULT_DEVICE_PORT_ADDRESS
    decFlStr,       //  94: SERVICE_NAME
    decFlStr,       //  95: CUSTOMER_PHONE_NUMBER
    decFlStr,       //  96: CUSTOMER_ACCOUNT_NUMBER
    decFlInt,       //  97: APP_PATH_ID
    decFlRaw,       //  98: RESERVED_98
    decFlRaw,       //  99: RESERVED_99
    decFlRaw,       // 100: RESERVED_100
    decFlRaw,       // 101: RESERVED_101
    decFlRaw,       // 102: RESERVED_102
    decFlRaw,       // 103: RESERVED_103
    decFlRaw,       // 104: RESERVED_104
    decFlRaw,       // 105: RESERVED_105
    decFlRaw,       // 106: RESERVED_106
    decFlRaw,       // 107: RESERVED_107
    decFlRaw,       // 108: RESERVED_108
    decFlRaw,       // 109: RESERVED_109
    decFlInt,       // 110: ROUTER_CALL_KEY_SEQUENCE_NUM
    decFlRaw,       // 111: RESERVED_111
    decFlRaw,       // 112: RESERVED_112
    decFlRaw,       // 113: RESERVED_113
    decFlRaw,       // 114: RESERVED_114
    decFlRaw,       // 115: RESERVED_115
    decFlRaw,       // 116: RESERVED_116
    decFlRaw,       // 117: RESERVED_117
    decFlRaw,       // 118: RESERVED_118
    decFlRaw,       // 119: RESERVED_119
    decFlRaw,       // 120: RESERVED_120
    decFlInt,       // 121: TRUNK_NUMBER
    decFlInt,       // 122: TRUNK_GROUP_NUMBER
    AgentState.decodeFloat, // 123: NEXT_AGENT_STATE
    decFlRaw,       // 124: DEQUEUE_TYPE
    decFlStr,       // 125: SENDING_ADDRESS
    decFlStr,       // 126: SENDING_PORT
    decFlRaw,       // 127: RESERVED_127
    decFlRaw,       // 128: RESERVED_128
    decFlRaw,       // 129: MAX_QUEUED
    decFlRaw,       // 130: QUEUE_ID
    decFlRaw,       // 131: CUSTOMER_ID
    decFlRaw,       // 132: SERVICE_SKILL_TARGET_ID
    decFlRaw,       // 133: PERIPHERAL_NAME
    decFlRaw,       // 134: DESCRIPTION
    decFlRaw,       // 135: SERVICE_MEMBER_ID
    decFlRaw,       // 136: SERVICE_MEMBER_PRIORITY
    decFlRaw,       // 137: FIRST_NAME
    decFlRaw,       // 138: LAST_NAME
    decFlRaw,       // 139: SKILL_GROUP
    decFlRaw,       // 140: RESERVED_140
    decFlRaw,       // 141: AGENT_SKILL_TARGET_ID
    decFlRaw,       // 142: SERVICE
    decFlRaw,       // 143: RESERVED_143
    decFlRaw,       // 144: RESERVED_144
    decFlRaw,       // 145: RESERVED_145
    decFlRaw,       // 146: RESERVED_146
    decFlRaw,       // 147: RESERVED_147
    decFlRaw,       // 148: RESERVED_148
    decFlRaw,       // 149: RESERVED_149
    decFlInt,       // 150: DURATION
    decFlRaw,       // 151: RESERVED_151
    decFlRaw,       // 152: RESERVED_152
    decFlRaw,       // 153: RESERVED_153
    decFlRaw,       // 154: RESERVED_154
    decFlRaw,       // 155: RESERVED_155
    decFlRaw,       // 156: RESERVED_156
    decFlRaw,       // 157: RESERVED_157
    decFlRaw,       // 158: RESERVED_158
    decFlRaw,       // 159: RESERVED_159
    decFlRaw,       // 160: RESERVED_160
    decFlRaw,       // 161: RESERVED_161
    decFlRaw,       // 162: RESERVED_162
    decFlRaw,       // 163: RESERVED_163
    decFlRaw,       // 164: RESERVED_164
    decFlRaw,       // 165: RESERVED_165
    decFlRaw,       // 166: RESERVED_166
    decFlRaw,       // 167: RESERVED_167
    decFlRaw,       // 168: RESERVED_168
    decFlRaw,       // 169: RESERVED_169
    decFlRaw,       // 170: RESERVED_170
    decFlRaw,       // 171: RESERVED_171
    decFlRaw,       // 172: RESERVED_172
    decFlRaw,       // 173: EXTENSION
    decFlRaw,       // 174: SERVICE_LEVEL_THRESHOLD
    decFlRaw,       // 175: SERVICE_LEVEL_TYPE
    decFlRaw,       // 176: CONFIG_PARAM
    decFlRaw,       // 177: SERVICE_CONFIG_KEY
    decFlRaw,       // 178: SKILL_GROUP_CONFIG_KEY
    decFlRaw,       // 179: AGENT_CONFIG_KEY
    decFlRaw,       // 180: DEVICE_CONFIG_KEY
    decFlRaw,       // 181: RESERVED_181
    decFlRaw,       // 182: RESERVED_182
    decFlRaw,       // 183: RECORD_TYPE
    decFlRaw,       // 184: PERIPHERAL_NUMBER
    decFlRaw,       // 185: CONFIG_AGENT_SKILL_TARGET_ID
    decFlRaw,       // 186: NUM_SERVICE_MEMBERS
    decFlStr,       // 187: SERVICE_MEMBER
    decFlRaw,       // 188: SERVICE_PRIORITY
    decFlRaw,       // 189: AGENT_TYPE
    decFlRaw,       // 190: LOGIN_ID
    decFlRaw,       // 191: NUM_SKILLS
    decFlRaw,       // 192: SKILL_GROUP_SKILL_TARGET_ID
    decFlInt,       // 193: SERVICE_ID
    decFlRaw,       // 194: AGENT_ID_LONG
    decFlRaw,       // 195: DEVICE_TYPE
    decFlRaw,       // 196: RESERVED_196
    decFlRaw,       // 197: RESERVED_197
    decFlRaw,       // 198: ENABLE
    decFlRaw,       // 199: DEVICEID
    decFlRaw,       // 200: TIMEOUT
    decFlRaw,       // 201: CURRENT_ROUTE
    decFlInt,       // 202: SECONDARY_CONNECTION_CALL_ID
    decFlRaw,       // 203: PRIORITY_QUEUE_NUMBER
    decFlRaw,       // 204: TEAM_NAME
    decFlRaw,       // 205: MEMBER_TYPE
    decFlStr,       // 206: EVENT_DEVICE_ID
    decFlRaw,       // 207: LOGIN_NAME_V11
    decFlInt,       // 208: PERIPHERAL_ID_V11
    decFlRaw,       // 209: CALL_TYPE_KEY_CONFIG_V11
    decFlRaw,       // 210: CALL_TYPE_ID_V11
    decFlRaw,       // 211: CUSTOMER_DEFINITION_ID_V11
    decFlRaw,       // 212: ENTERPRISE_NAME_V11
    decFlRaw,       // 213: CUR_PERIPHERAL_NUMBER
    decFlRaw,       // 214: CUR_LOGIN_ID
    decFlStr,       // 215: ANI_II
    decFlRaw,       // 216: MR_DOMAIN_ID
    decFlStr,       // 217: CTIOS_CIL_CLIENT_ID
    SilentMonitorStatus.decodeFloat,        // 218: SILENT_MONITOR_STATUS
    decFlStr,       // 219: REQUESTING_DEVICE_ID
    decFlRaw,       // 220: REQUESTING_DEVICE_ID_TYPE
    decFlInt,       // 221: PRE_CALL_INVOKE_ID
    decFlRaw,       // 222: ENTERPRISE_QUEUE_TIME
    decFlRaw,       // 223: CALL_REFERENCE_ID
    decFlShortBool, // 224: MULTI_LINE_AGENT_CONTROL
    decFlRaw,       // 225: NETWORK_CONTROLLED
    decFlStr,       // 226: CLIENT_ADDRESS_IPV6
    decFlStr,       // 227: SENDING_ADDRESS_IPV6
    decFlShort,     // 228: NUM_PERIPHERALS
    decFlInt,       // 229: COC_CONNECTION_CALL_ID
    decFlShort,     // 230: COC_CONNECTION_DEVICE_ID_TYPE
    decFlStr,       // 231: COC_CONNECTION_DEVICE_ID
    decFlByte,      // 232: CALL_ORIGINATED_FROM
    decFlRaw,       // 233: SET_APPDATA_CALLID
    decFlRaw,       // 234: CLIENT_SHARE_KEY
    decFlRaw,       // 235: RESERVED_235
    decFlRaw,       // 236: RESERVED_236
    decFlRaw,       // 237: RESERVED_237
    decFlRaw,       // 238: RESERVED_238
    decFlRaw,       // 239: RESERVED_239
    decFlRaw,       // 240: RESERVED_240
    decFlRaw,       // 241: RESERVED_241
    decFlRaw,       // 242: RESERVED_242
    decFlStr,       // 243: AGENT_TEAM_NAME
    CallDirection.decodeFloat,      // 244: DIRECTION
    decFlRaw,       // 245: RESERVED_245
    decFlRaw,       // 246: RESERVED_246
    decFlRaw,       // 247: RESERVED_247
    decFlRaw,       // 248: RESERVED_248
    decFlRaw,       // 249: RESERVED_249
    decFlRaw,       // 250: RESERVED_250
    decFlRaw,       // 251: RESERVED_251
    decFlRaw,       // 252: RESERVED_252
    decFlRaw,       // 253: RESERVED_253
    decFlRaw,       // 254: RESERVED_254
    decFlRaw        // 255: RESERVED_255
  )


  //
  // Encode a field
  //
  def encodeField(tag: Tag, field_value: Any): ByteString = tag.id match {
    case i if i == MessageTypeTag.id => MessageType.encode(tag, field_value)
    case i if i > MessageTypeTag.id => encFixedField(tag, field_value)
    case _ => encFloatingField(tag, field_value)
  }

  //
  // Encode a fixed part field
  //
  private def encFixedField(tag: Tag, field_value: Any): ByteString =
    fxTagToEncodeFunc(tag.id - ActiveConnectionCallID.id)(tag, field_value)

  //
  // Encoder functions of fixed part for simple types
  //
  private def encFxRaw(tag: Tag, a: Any) = a match { case b: ByteString => b }

  private def encFxInt(tag: Tag, a: Any) = a match { case i: Int => encodeByteString(i) }

  private def encFxShort(tag: Tag, a: Any) = a match { case s: Short => encodeByteString(s) }

  private def encFxIntBool(tag: Tag, a: Any) = a match { case b: Boolean => encodeByteString(if (b) 1 else 0) }

  private def encFxShortBool(tag: Tag, a: Any) =
    a match { case b: Boolean => encodeByteString((if (b) 1 else 0).toShort) }

  private def encFxIntBitSet(tag: Tag, a: Any) = a match { case m: BitSet => encodeByteString(m.toInt) }

  private def encFxShortBitSet(tag: Tag, a: Any) = a match { case m: BitSet => encodeByteString(m.toShort) }

  //
  // DON'T EDIT THIS TABLE!!
  // This table is generated from TagFuncTbl.txt using gen-enc-table.sh
  //
  // Jump table from Tag to decoding function for fixed part
  //
  private val fxTagToEncodeFunc: Array[(Tag, Any) => ByteString] = Array(
    // script generated code starts here
    encFxInt, 	// ActiveConnectionCallID
    ConnectionDeviceIDType.encode, 	// ActiveConnectionDeviceIDType
    DeviceIDType.encode, 	// AddedPartyDeviceType
    AgentGreetingAction.encode, 	// AgentActionTag
    AgentAvailabilityStatus.encode, 	// AgentAvailabilityStatusTag
    encFxInt, 	// AgentConnectionCallID
    ConnectionDeviceIDType.encode, 	// AgentConnectionDeviceIDType
    encFxShortBool, 	// AgentMode
    encFxInt, 	// AgentOutCallsHeldSession
    encFxInt, 	// AgentOutCallsHeldTimeSession
    encFxInt, 	// AgentOutCallsHeldTimeTo5
    encFxInt, 	// AgentOutCallsHeldTimeToHalf
    encFxInt, 	// AgentOutCallsHeldTimeToday
    encFxInt, 	// AgentOutCallsHeldTo5
    encFxInt, 	// AgentOutCallsHeldToHalf
    encFxInt, 	// AgentOutCallsHeldToday
    encFxInt, 	// AgentOutCallsSession
    encFxInt, 	// AgentOutCallsTalkTimeSession
    encFxInt, 	// AgentOutCallsTalkTimeTo5
    encFxInt, 	// AgentOutCallsTalkTimeToHalf
    encFxInt, 	// AgentOutCallsTalkTimeToday
    encFxInt, 	// AgentOutCallsTimeSession
    encFxInt, 	// AgentOutCallsTimeTo5
    encFxInt, 	// AgentOutCallsTimeToHalf
    encFxInt, 	// AgentOutCallsTimeToday
    encFxInt, 	// AgentOutCallsTo5
    encFxInt, 	// AgentOutCallsToHalf
    encFxInt, 	// AgentOutCallsToday
    encFxIntBitSet, 	// AgentServiceReq
    encFxIntBitSet, 	// AgentStateMaskTag
    AgentState.encode, 	// AgentStateTag
    AgentWorkMode.encode, 	// AgentWorkModeTag
    encFxInt, 	// AgentsApplicationAvailable
    encFxInt, 	// AgentsAvail
    encFxInt, 	// AgentsBusyOther
    encFxInt, 	// AgentsHold
    encFxInt, 	// AgentsICMAvailable
    encFxInt, 	// AgentsLoggedOn
    encFxInt, 	// AgentsNotReady
    encFxInt, 	// AgentsReady
    encFxInt, 	// AgentsReserved
    encFxInt, 	// AgentsTalkingAutoOut
    encFxInt, 	// AgentsTalkingIn
    encFxInt, 	// AgentsTalkingOther
    encFxInt, 	// AgentsTalkingOut
    encFxInt, 	// AgentsTalkingPreview
    encFxInt, 	// AgentsTalkingReservation
    encFxInt, 	// AgentsWorkNotReady
    encFxInt, 	// AgentsWorkReady
    encFxShort, 	// AlertRings
    DeviceIDType.encode, 	// AlertingDeviceType
    AllocationState.encode, 	// AllocationStateTag
    encFxInt, 	// AnswerDetectControl1
    encFxInt, 	// AnswerDetectControl2
    AnswerDetectMode.encode, 	// AnswerDetectModeTag
    encFxShort, 	// AnswerDetectTime
    DeviceIDType.encode, 	// AnsweringDeviceType
    AnsweringMachine.encode, 	// AnsweringMachineTag
    encFxInt, 	// AutoOutCallsHeldSession
    encFxInt, 	// AutoOutCallsHeldTimeSession
    encFxInt, 	// AutoOutCallsHeldTimeTo5
    encFxInt, 	// AutoOutCallsHeldTimeToHalf
    encFxInt, 	// AutoOutCallsHeldTimeToday
    encFxInt, 	// AutoOutCallsHeldTo5
    encFxInt, 	// AutoOutCallsHeldToHalf
    encFxInt, 	// AutoOutCallsHeldToday
    encFxInt, 	// AutoOutCallsSession
    encFxInt, 	// AutoOutCallsTalkTimeSession
    encFxInt, 	// AutoOutCallsTalkTimeTo5
    encFxInt, 	// AutoOutCallsTalkTimeToHalf
    encFxInt, 	// AutoOutCallsTalkTimeToday
    encFxInt, 	// AutoOutCallsTimeSession
    encFxInt, 	// AutoOutCallsTimeTo5
    encFxInt, 	// AutoOutCallsTimeToHalf
    encFxInt, 	// AutoOutCallsTimeToday
    encFxInt, 	// AutoOutCallsTo5
    encFxInt, 	// AutoOutCallsToHalf
    encFxInt, 	// AutoOutCallsToday
    encFxIntBool, 	// AutoRecordOnEmergency
    encFxInt, 	// AvailTimeSession
    encFxInt, 	// AvailTimeTo5
    encFxInt, 	// AvailTimeToHalf
    encFxInt, 	// AvailTimeToday
    encFxInt, 	// BargeInCallsSession
    encFxInt, 	// BargeInCallsTo5
    encFxInt, 	// BargeInCallsToHalf
    encFxInt, 	// BargeInCallsToday
    encFxInt, 	// BitRate
    encFxIntBitSet, 	// CallControlSupported
    encFxIntBitSet, 	// CallEventsSupported
    CallMannerType.encode, 	// CallMannerTypeTag
    encFxIntBitSet, 	// CallMsgMask
    CallOption.encode, 	// CallOptionTag
    CallPlacementType.encode, 	// CallPlacementTypeTag
    encFxInt, 	// CallTypeID
    CallType.encode, 	// CallTypeTag
    encFxShortBitSet, 	// CallVariableMaskTag
    DeviceIDType.encode, 	// CalledDeviceType
    DispositionCodeValue.encode, 	// CalledPartyDisposition
    DeviceIDType.encode, 	// CallingDeviceType
    encFxInt, 	// CallsQ5
    encFxInt, 	// CallsQHalf
    encFxInt, 	// CallsQNow
    encFxInt, 	// CallsQTime5
    encFxInt, 	// CallsQTimeHalf
    encFxInt, 	// CallsQTimeNow
    encFxInt, 	// CallsQTimeToday
    encFxInt, 	// CallsQToday
    encFxInt, 	// CampaignID
    encFxShortBitSet, 	// ClassOfDeviceTag
    encFxInt, 	// ClientPort
    encFxIntBitSet, 	// ConfigMsgMask
    ConfigOperation.encode, 	// ConfigOperationTag
    encFxInt, 	// ConnectionCallID
    ConnectionDeviceIDType.encode, 	// ConnectionDeviceIDTypeTag
    ConsultType.encode, 	// ConsultTypeTag
    DeviceIDType.encode, 	// ControllerDeviceType
    encFxIntBitSet, 	// DeskSettingsMaskTag
    DestinationCountry.encode, 	// DestinationCountryTag
    DistributionValue.encode, 	// Distribution
    DeviceIDType.encode, 	// DivertingDeviceType
    encFxShortBool, 	// EchoCancellation
    encFxIntBool, 	// EmergencyCallMethod
    encFxInt, 	// EmergencyCallsSession
    encFxInt, 	// EmergencyCallsTo5
    encFxInt, 	// EmergencyCallsToHalf
    encFxInt, 	// EmergencyCallsToday
    EventCause.encode, 	// EventCauseTag
    EventCode.encode, 	// EventCodeTag
    DeviceIDType.encode, 	// EventDeviceType
    encFxShort, 	// EventReasonCode
    FacilityType.encode, 	// FacilityTypeTag
    DeviceIDType.encode, 	// FailingDeviceType
    ControlFailureCode.encode, 	// FailureCode
    ForcedFlag.encode, 	// ForcedFlagTag
    encFxInt, 	// HandledCallsAfterCallTimeSession
    encFxInt, 	// HandledCallsAfterCallTimeTo5
    encFxInt, 	// HandledCallsAfterCallTimeToHalf
    encFxInt, 	// HandledCallsAfterCallTimeToday
    encFxInt, 	// HandledCallsSession
    encFxInt, 	// HandledCallsTalkTimeSession
    encFxInt, 	// HandledCallsTalkTimeTo5
    encFxInt, 	// HandledCallsTalkTimeToHalf
    encFxInt, 	// HandledCallsTalkTimeToday
    encFxInt, 	// HandledCallsTimeSession
    encFxInt, 	// HandledCallsTimeTo5
    encFxInt, 	// HandledCallsTimeToHalf
    encFxInt, 	// HandledCallsTimeToday
    encFxInt, 	// HandledCallsTo5
    encFxInt, 	// HandledCallsToHalf
    encFxInt, 	// HandledCallsToday
    encFxInt, 	// HeldConnectionCallID
    ConnectionDeviceIDType.encode, 	// HeldConnectionDeviceIDType
    DeviceIDType.encode, 	// HoldingDeviceType
    encFxInt, 	// ICMAgentID
    encFxInt, 	// ICMAvailableTimeSession
    encFxInt, 	// ICMAvailableTimeToday
    encFxInt, 	// ICMCentralControllerTime
    encFxInt, 	// IdleTimeout
    encFxInt, 	// IncomingCallsHeldSession
    encFxInt, 	// IncomingCallsHeldTimeSession
    encFxInt, 	// IncomingCallsHeldTimeTo5
    encFxInt, 	// IncomingCallsHeldTimeToHalf
    encFxInt, 	// IncomingCallsHeldTimeToday
    encFxInt, 	// IncomingCallsHeldTo5
    encFxInt, 	// IncomingCallsHeldToHalf
    encFxInt, 	// IncomingCallsHeldToday
    encFxInt, 	// InterceptCallsSession
    encFxInt, 	// InterceptCallsTo5
    encFxInt, 	// InterceptCallsToHalf
    encFxInt, 	// InterceptCallsToday
    encFxInt, 	// InternalCallsHeldSession
    encFxInt, 	// InternalCallsHeldTimeSession
    encFxInt, 	// InternalCallsHeldTimeTo5
    encFxInt, 	// InternalCallsHeldTimeToHalf
    encFxInt, 	// InternalCallsHeldTimeToday
    encFxInt, 	// InternalCallsHeldTo5
    encFxInt, 	// InternalCallsHeldToHalf
    encFxInt, 	// InternalCallsHeldToday
    encFxInt, 	// InternalCallsRcvdSession
    encFxInt, 	// InternalCallsRcvdTimeSession
    encFxInt, 	// InternalCallsRcvdTimeTo5
    encFxInt, 	// InternalCallsRcvdTimeToHalf
    encFxInt, 	// InternalCallsRcvdTimeToday
    encFxInt, 	// InternalCallsRcvdTo5
    encFxInt, 	// InternalCallsRcvdToHalf
    encFxInt, 	// InternalCallsRcvdToday
    encFxInt, 	// InternalCallsSession
    encFxInt, 	// InternalCallsTimeSession
    encFxInt, 	// InternalCallsTimeToday
    encFxInt, 	// InternalCallsToday
    encFxInt, 	// InvokeID
    DeviceIDType.encode, 	// LastRedirectDeviceType
    encFxShort, 	// LineHandle
    LineType.encode, 	// LineTypeTag
    LocalConnectionState.encode, 	// LocalConnectionStateTag
    encFxInt, 	// LoggedOnTimeSession
    encFxInt, 	// LoggedOnTimeTo5
    encFxInt, 	// LoggedOnTimeToHalf
    encFxInt, 	// LoggedOnTimeToday
    encFxInt, 	// LogoutNonActivityTime
    encFxInt, 	// LongestCallQ5
    encFxInt, 	// LongestCallQHalf
    encFxInt, 	// LongestCallQNow
    encFxInt, 	// LongestCallQToday
    encFxInt, 	// LongestRouterCallQNow
    encFxInt, 	// MRDID
    encFxIntBitSet, 	// MakeCallSetup
    encFxShort, 	// MaxActiveCalls
    encFxShort, 	// MaxDeviceInConference
    encFxShort, 	// MaxHeldCalls
    encFxInt, 	// MaxTaskLimit
    encFxInt, 	// MonitorCallsSession
    encFxInt, 	// MonitorCallsTo5
    encFxInt, 	// MonitorCallsToHalf
    encFxInt, 	// MonitorCallsToday
    encFxInt, 	// MonitorID
    DeviceIDType.encode, 	// MonitoredDeviceType
    encFxShortBool, 	// More
    encFxInt, 	// NewConnectionCallID
    ConnectionDeviceIDType.encode, 	// NewConnectionDeviceIDType
    encFxInt, 	// NotReadyTimeSession
    encFxInt, 	// NotReadyTimeTo5
    encFxInt, 	// NotReadyTimeToHalf
    encFxInt, 	// NotReadyTimeToday
    encFxShort, 	// NumCTIClients
    encFxShort, 	// NumCallDevices
    encFxShort, 	// NumCalls
    encFxShort, 	// NumFltSkillGroups
    encFxShort, 	// NumLines
    encFxShort, 	// NumNamedArrays
    encFxShort, 	// NumNamedVariables
    encFxShort, 	// NumParties
    encFxShort, 	// NumQueued
    encFxShort, 	// NumSkillGroups
    encFxInt, 	// NumTasks
    encFxShort, 	// NumberOfAgentTeams
    encFxShort, 	// NumberOfAgents
    encFxInt, 	// OtherConnectionCallID
    ConnectionDeviceIDType.encode, 	// OtherConnectionDeviceIDType
    encFxIntBitSet, 	// OtherFeaturesSupported
    encFxIntBitSet, 	// PGStatus
    encFxInt, 	// PacketSize
    encFxInt, 	// PauseDuration
    encFxShort, 	// PayloadType
    encFxInt, 	// PeripheralErrorCode
    encFxInt, 	// PeripheralID
    encFxShortBool, 	// PeripheralOnline
    PeripheralType.encode, 	// PeripheralTypeTag
    encFxShortBool, 	// PostRoute
    encFxInt, 	// PreviewCallsHeldSession
    encFxInt, 	// PreviewCallsHeldTimeSession
    encFxInt, 	// PreviewCallsHeldTimeTo5
    encFxInt, 	// PreviewCallsHeldTimeToHalf
    encFxInt, 	// PreviewCallsHeldTimeToday
    encFxInt, 	// PreviewCallsHeldTo5
    encFxInt, 	// PreviewCallsHeldToHalf
    encFxInt, 	// PreviewCallsHeldToday
    encFxInt, 	// PreviewCallsSession
    encFxInt, 	// PreviewCallsTalkTimeSession
    encFxInt, 	// PreviewCallsTalkTimeTo5
    encFxInt, 	// PreviewCallsTalkTimeToHalf
    encFxInt, 	// PreviewCallsTalkTimeToday
    encFxInt, 	// PreviewCallsTimeSession
    encFxInt, 	// PreviewCallsTimeTo5
    encFxInt, 	// PreviewCallsTimeToHalf
    encFxInt, 	// PreviewCallsTimeToday
    encFxInt, 	// PreviewCallsTo5
    encFxInt, 	// PreviewCallsToHalf
    encFxInt, 	// PreviewCallsToday
    encFxInt, 	// PrimaryCallID
    DeviceIDType.encode, 	// PrimaryDeviceIDType
    encFxShortBool, 	// Priority
    encFxInt, 	// QualityRecordingRate
    encFxInt, 	// QueryRuleID
    DeviceIDType.encode, 	// QueueDeviceType
    RTPDirection.encode, 	// RTPDirectionTag
    RTPType.encode, 	// RTPTypeTag
    encFxRaw, 	// RawBytes
    encFxIntBool, 	// RecordingMode
    encFxInt, 	// RegisteredServiceID
    DeviceIDType.encode, 	// ReleasingDeviceType
    encFxShortBool, 	// Reservation
    encFxInt, 	// ReservationCallsHeldSession
    encFxInt, 	// ReservationCallsHeldTimeSession
    encFxInt, 	// ReservationCallsHeldTimeTo5
    encFxInt, 	// ReservationCallsHeldTimeToHalf
    encFxInt, 	// ReservationCallsHeldTimeToday
    encFxInt, 	// ReservationCallsHeldTo5
    encFxInt, 	// ReservationCallsHeldToHalf
    encFxInt, 	// ReservationCallsHeldToday
    encFxInt, 	// ReservationCallsSession
    encFxInt, 	// ReservationCallsTalkTimeSession
    encFxInt, 	// ReservationCallsTalkTimeTo5
    encFxInt, 	// ReservationCallsTalkTimeToHalf
    encFxInt, 	// ReservationCallsTalkTimeToday
    encFxInt, 	// ReservationCallsTimeSession
    encFxInt, 	// ReservationCallsTimeTo5
    encFxInt, 	// ReservationCallsTimeToHalf
    encFxInt, 	// ReservationCallsTimeToday
    encFxInt, 	// ReservationCallsTo5
    encFxInt, 	// ReservationCallsToHalf
    encFxInt, 	// ReservationCallsToday
    encFxInt, 	// Reserved1
    encFxInt, 	// Reserved2
    encFxInt, 	// Reserved3
    encFxShort, 	// Reserved16
    DeviceIDType.encode, 	// RetrievingDeviceType
    encFxInt, 	// RingNoAnswerDN
    encFxInt, 	// RingNoAnswerTime
    encFxInt, 	// RoutableTimeSession
    encFxInt, 	// RoutableTimeToday
    encFxInt, 	// RouterCallsQNow
    encFxInt, 	// SecondaryCallID
    encFxInt, 	// SecondaryConnectionCallID
    DeviceIDType.encode, 	// SecondaryDeviceIDType
    encFxShort, 	// SegmentNumber
    encFxInt, 	// ServerData
    ServerMode.encode, 	// ServerModeTag
    encFxIntBitSet, 	// ServiceGranted
    encFxInt, 	// ServiceID
    encFxInt, 	// ServiceNumber
    encFxIntBitSet, 	// ServiceRequested
    encFxInt, 	// SessionID
    encFxIntBool, 	// SilentMonitorAudibleIndication
    encFxIntBool, 	// SilentMonitorWarningMessage
    encFxInt, 	// SkillGroupID
    encFxInt, 	// SkillGroupNumber
    encFxShort, 	// SkillGroupPriority
    AgentState.encode, 	// SkillGroupState
    DeviceIDType.encode, 	// SnapshotDeviceType
    ClientEventReportState.encode, 	// ClientEventStateTag
    encFxInt, 	// StateDuration
    StatusCode.encode, 	// StatusCodeTag
    encFxIntBool, 	// SupervisorAssistCallMethod
    encFxInt, 	// SupervisorConnectionCallID
    ConnectionDeviceIDType.encode, 	// SupervisorConnectionDeviceIDType
    encFxInt, 	// SupervisorID
    SupervisoryAction.encode, 	// SupervisoryActionTag
    encFxInt, 	// SystemEventArg1
    encFxInt, 	// SystemEventArg2
    encFxInt, 	// SystemEventArg3
    SystemEventID.encode, 	// SystemEventIDTag
    encFxInt, 	// TeamID
    encFxShort, 	// ToneDuration
    encFxIntBitSet, 	// TransferConferenceSetup
    DeviceIDType.encode, 	// TransferredDeviceType
    DeviceIDType.encode, 	// TransferringDeviceType
    DeviceIDType.encode, 	// TrunkUsedDeviceType
    TypeOfDevice.encode, 	// TypeOfDeviceTag
    encFxInt, 	// VersionNumber
    encFxInt, 	// WhisperCallsSession
    encFxInt, 	// WhisperCallsTo5
    encFxInt, 	// WhisperCallsToHalf
    encFxInt, 	// WhisperCallsToday
    encFxInt, 	// WorkModeTimer
    WrapupDataMode.encode, 	// WrapupDataIncomingMode
    WrapupDataMode.encode, 	// WrapupDataOutgoingMode
    // script generated code ends here

    encFxInt // Dummy entry.  Not used
  )



  //
  // Encode a floating part field
  //
  private def encFloatingField(tag: Tag, field_value: Any): ByteString = flTagToEncodeFunc(tag.id)(tag, field_value)

  //
  // Encoder functions of floating part for simple types
  //
  private def encFlRaw(tag: Tag, a: Any) = a match {
    case b: ByteString =>
      val body = b.take(MaxFloatStringLen - 1)
      ByteString(tag.id, body.size) ++ body
  }

  private def encFlStr(tag: Tag, a: Any) = a match {
    case s: String =>
      val body = ByteString(s).take(MaxFloatStringLen - 1) ++ ByteString(0.toByte)
      ByteString(tag.id, body.size) ++ body
  }

  private def encFlInt(tag: Tag, a: Any) = a match { case i: Int => ByteString(tag.id, 4) ++ i }

  private def encFlShort(tag: Tag, a: Any) = a match { case s: Short => ByteString(tag.id, 2) ++ s }

  private def encFlByte(tag: Tag, a: Any) = a match { case b: Byte => ByteString(tag.id, 1, b & 0xff) }

  private def encFlShortBool(tag: Tag, a: Any) = a match {
    case b: Boolean => ByteString(tag.id, 2) ++ (if (b) 1 else 0).toShort
  }

  private def encFlShortMask(tag: Tag, a: Any) = a match { case m: BitSet => encFlShort(tag, m.toInt.toShort) }

  private def encFlNamedVar(tag: Tag, a: Any) = a match {
    case t: (Any, Any) =>
      val (x, y) = t
      x match {
        case name: String => y match {
          case body: String =>
            val n = ByteString(name).take(MaxECCNameLen) ++ ByteString(0.toByte)
            val b = ByteString(body).take(MaxECCValueLen) ++ ByteString(0.toByte)
            ByteString(tag.id, n.size + b.size) ++ n ++ b
        }
      }
  }

  private def encFlNamedArr(tag: Tag, a: Any) = a match {
    case t: (Any, Any, Any) =>
      val (x, y, z) = t
      x match {
        case index: Int => y match {
          case name: String => z match {
            case body: String =>
              val n = ByteString(name).take(MaxECCNameLen) ++ ByteString(0.toByte)
              val b = ByteString(body).take(MaxECCValueLen) ++ ByteString(0.toByte)
              ByteString(tag.id, n.size + b.size + 1, index) ++ n ++ b
          }
        }
      }
  }

  //
  // Jump table from Tag to encoding function for floating part
  //
  private val flTagToEncodeFunc: Array[(Tag, Any) => ByteString] = Array(
    encFlRaw,       //   0: Invalid
    encFlStr,       //   1: CLIENT_ID
    encFlRaw,       //   2: CLIENT_PASSWORD
    encFlStr,       //   3: CLIENT_SIGNATURE
    encFlStr,       //   4: AGENT_EXTENSION
    encFlStr,       //   5: AGENT_ID
    encFlStr,       //   6: AGENT_INSTRUMENT
    encFlStr,       //   7: TEXT
    encFlStr,       //   8: ANI
    encFlRaw,       //   9: UUI
    encFlStr,       //  10: DNIS
    encFlStr,       //  11: DIALED_NUMBER
    encFlStr,       //  12: CED
    encFlStr,       //  13: CALL_VAR_1
    encFlStr,       //  14: CALL_VAR_2
    encFlStr,       //  15: CALL_VAR_3
    encFlStr,       //  16: CALL_VAR_4
    encFlStr,       //  17: CALL_VAR_5
    encFlStr,       //  18: CALL_VAR_6
    encFlStr,       //  19: CALL_VAR_7
    encFlStr,       //  20: CALL_VAR_8
    encFlStr,       //  21: CALL_VAR_9
    encFlStr,       //  22: CALL_VAR_10
    encFlStr,       //  23: CTI_CLIENT_SIGNATURE
    encFlInt,       //  24: CTI_CLIENT_TIMESTAMP
    encFlStr,       //  25: CONNECTION_DEVID
    encFlStr,       //  26: ALERTING_DEVID
    encFlStr,       //  27: CALLING_DEVID
    encFlStr,       //  28: CALLED_DEVID
    encFlStr,       //  29: LAST_REDIRECT_DEVID
    encFlStr,       //  30: ANSWERING_DEVID
    encFlStr,       //  31: HOLDING_DEVID
    encFlStr,       //  32: RETRIEVING_DEVID
    encFlStr,       //  33: RELEASING_DEVID
    encFlStr,       //  34: FAILING_DEVID
    encFlStr,       //  35: PRIMARY_DEVID
    encFlStr,       //  36: SECONDARY_DEVID
    encFlStr,       //  37: CONTROLLER_DEVID
    encFlStr,       //  38: ADDED_PARTY_DEVID
    encFlInt,       //  39: PARTY_CALLID
    DeviceIDType.encodeFloat,       //  40: PARTY_DEVID_TYPE
    encFlStr,       //  41: PARTY_DEVID
    encFlStr,       //  42: TRANSFERRING_DEVID
    encFlStr,       //  43: TRANSFERRED_DEVID
    encFlStr,       //  44: DIVERTING_DEVID
    encFlStr,       //  45: QUEUE_DEVID
    encFlStr,       //  46: CALL_WRAPUP_DATA
    encFlStr,       //  47: NEW_CONNECTION_DEVID
    encFlStr,       //  48: TRUNK_USED_DEVID
    encFlStr,       //  49: AGENT_PASSWORD
    encFlStr,       //  50: ACTIVE_CONN_DEVID
    encFlStr,       //  51: FACILITY_CODE
    encFlStr,       //  52: OTHER_CONN_DEVID
    encFlStr,       //  53: HELD_CONN_DEVID
    encFlRaw,       //  54: RESERVED_54
    encFlRaw,       //  55: RESERVED_55
    encFlInt,       //  56: CALL_CONN_CALLID
    ConnectionDeviceIDType.encodeFloat,     //  57: CALL_CONN_DEVID_TYPE
    encFlStr,       //  58: CALL_CONN_DEVID
    encFlShort,     //  59: CALL_DEVID_TYPE
    encFlStr,       //  60: CALL_DEVID
    encFlShort,     //  61: CALL_DEV_CONN_STATE
    encFlInt,       //  62: SKILL_GROUP_NUMBER
    encFlInt,       //  63: SKILL_GROUP_ID
    encFlShort,     //  64: SKILL_GROUP_PRIORITY
    AgentState.encodeFloat, //  65: SKILL_GROUP_STATE
    encFlStr,       //  66: OBJECT_NAME
    encFlStr,       //  67: DTMF_STRING
    encFlStr,       //  68: POSITION_ID
    encFlStr,       //  69: SUPERVISOR_ID
    encFlShort,     //  70: LINE_HANDLE
    LineType.encodeFloat,   //  71: LINE_TYPE
    encFlInt,       //  72: ROUTER_CALL_KEY_DAY
    encFlInt,       //  73: ROUTER_CALL_KEY_CALLID
    encFlRaw,       //  74: RESERVED_74
    LocalConnectionState.encodeFloat,       //  75: CALL_STATE
    encFlStr,       //  76: MONITORED_DEVID
    encFlStr,       //  77: AUTHORIZATION_CODE
    encFlStr,       //  78: ACCOUNT_CODE
    encFlStr,       //  79: ORIGINATING_DEVID
    encFlStr,       //  80: ORIGINATING_LINE_ID
    encFlStr,       //  81: CLIENT_ADDRESS
    encFlNamedVar,  //  82: NAMED_VARIABLE
    encFlNamedArr,  //  83: NAMED_ARRAY
    encFlStr,       //  84: CALL_CONTROL_TABLE
    encFlStr,       //  85: SUPERVISOR_INSTRUMENT
    encFlStr,       //  86: ATC_AGENT_ID
    encFlShortMask, //  87: AGENT_FLAGS
    AgentState.encodeFloat, //  88: ATC_AGENT_STATE
    encFlInt,       //  89: ATC_STATE_DURATION
    encFlStr,       //  90: AGENT_CONNECTION_DEVID
    encFlStr,       //  91: SUPERVISOR_CONNECTION_DEVID
    encFlInt,       //  92: LIST_TEAM_ID
    encFlStr,       //  93: DEFAULT_DEVICE_PORT_ADDRESS
    encFlStr,       //  94: SERVICE_NAME
    encFlStr,       //  95: CUSTOMER_PHONE_NUMBER
    encFlStr,       //  96: CUSTOMER_ACCOUNT_NUMBER
    encFlInt,       //  97: APP_PATH_ID
    encFlRaw,       //  98: RESERVED_98
    encFlRaw,       //  99: RESERVED_99
    encFlRaw,       // 100: RESERVED_100
    encFlRaw,       // 101: RESERVED_101
    encFlRaw,       // 102: RESERVED_102
    encFlRaw,       // 103: RESERVED_103
    encFlRaw,       // 104: RESERVED_104
    encFlRaw,       // 105: RESERVED_105
    encFlRaw,       // 106: RESERVED_106
    encFlRaw,       // 107: RESERVED_107
    encFlRaw,       // 108: RESERVED_108
    encFlRaw,       // 109: RESERVED_109
    encFlInt,       // 110: ROUTER_CALL_KEY_SEQUENCE_NUM
    encFlRaw,       // 111: RESERVED_111
    encFlRaw,       // 112: RESERVED_112
    encFlRaw,       // 113: RESERVED_113
    encFlRaw,       // 114: RESERVED_114
    encFlRaw,       // 115: RESERVED_115
    encFlRaw,       // 116: RESERVED_116
    encFlRaw,       // 117: RESERVED_117
    encFlRaw,       // 118: RESERVED_118
    encFlRaw,       // 119: RESERVED_119
    encFlRaw,       // 120: RESERVED_120
    encFlInt,       // 121: TRUNK_NUMBER
    encFlInt,       // 122: TRUNK_GROUP_NUMBER
    AgentState.encodeFloat, // 123: NEXT_AGENT_STATE
    encFlRaw,       // 124: DEQUEUE_TYPE
    encFlStr,       // 125: SENDING_ADDRESS
    encFlStr,       // 126: SENDING_PORT
    encFlRaw,       // 127: RESERVED_127
    encFlRaw,       // 128: RESERVED_128
    encFlRaw,       // 129: MAX_QUEUED
    encFlRaw,       // 130: QUEUE_ID
    encFlRaw,       // 131: CUSTOMER_ID
    encFlRaw,       // 132: SERVICE_SKILL_TARGET_ID
    encFlRaw,       // 133: PERIPHERAL_NAME
    encFlRaw,       // 134: DESCRIPTION
    encFlRaw,       // 135: SERVICE_MEMBER_ID
    encFlRaw,       // 136: SERVICE_MEMBER_PRIORITY
    encFlRaw,       // 137: FIRST_NAME
    encFlRaw,       // 138: LAST_NAME
    encFlRaw,       // 139: SKILL_GROUP
    encFlRaw,       // 140: RESERVED_140
    encFlRaw,       // 141: AGENT_SKILL_TARGET_ID
    encFlRaw,       // 142: SERVICE
    encFlRaw,       // 143: RESERVED_143
    encFlRaw,       // 144: RESERVED_144
    encFlRaw,       // 145: RESERVED_145
    encFlRaw,       // 146: RESERVED_146
    encFlRaw,       // 147: RESERVED_147
    encFlRaw,       // 148: RESERVED_148
    encFlRaw,       // 149: RESERVED_149
    encFlInt,       // 150: DURATION
    encFlRaw,       // 151: RESERVED_151
    encFlRaw,       // 152: RESERVED_152
    encFlRaw,       // 153: RESERVED_153
    encFlRaw,       // 154: RESERVED_154
    encFlRaw,       // 155: RESERVED_155
    encFlRaw,       // 156: RESERVED_156
    encFlRaw,       // 157: RESERVED_157
    encFlRaw,       // 158: RESERVED_158
    encFlRaw,       // 159: RESERVED_159
    encFlRaw,       // 160: RESERVED_160
    encFlRaw,       // 161: RESERVED_161
    encFlRaw,       // 162: RESERVED_162
    encFlRaw,       // 163: RESERVED_163
    encFlRaw,       // 164: RESERVED_164
    encFlRaw,       // 165: RESERVED_165
    encFlRaw,       // 166: RESERVED_166
    encFlRaw,       // 167: RESERVED_167
    encFlRaw,       // 168: RESERVED_168
    encFlRaw,       // 169: RESERVED_169
    encFlRaw,       // 170: RESERVED_170
    encFlRaw,       // 171: RESERVED_171
    encFlRaw,       // 172: RESERVED_172
    encFlRaw,       // 173: EXTENSION
    encFlRaw,       // 174: SERVICE_LEVEL_THRESHOLD
    encFlRaw,       // 175: SERVICE_LEVEL_TYPE
    encFlRaw,       // 176: CONFIG_PARAM
    encFlRaw,       // 177: SERVICE_CONFIG_KEY
    encFlRaw,       // 178: SKILL_GROUP_CONFIG_KEY
    encFlRaw,       // 179: AGENT_CONFIG_KEY
    encFlRaw,       // 180: DEVICE_CONFIG_KEY
    encFlRaw,       // 181: RESERVED_181
    encFlRaw,       // 182: RESERVED_182
    encFlRaw,       // 183: RECORD_TYPE
    encFlRaw,       // 184: PERIPHERAL_NUMBER
    encFlRaw,       // 185: CONFIG_AGENT_SKILL_TARGET_ID
    encFlRaw,       // 186: NUM_SERVICE_MEMBERS
    encFlStr,       // 187: SERVICE_MEMBER
    encFlRaw,       // 188: SERVICE_PRIORITY
    encFlRaw,       // 189: AGENT_TYPE
    encFlRaw,       // 190: LOGIN_ID
    encFlRaw,       // 191: NUM_SKILLS
    encFlRaw,       // 192: SKILL_GROUP_SKILL_TARGET_ID
    encFlInt,       // 193: SERVICE_ID
    encFlRaw,       // 194: AGENT_ID_LONG
    encFlRaw,       // 195: DEVICE_TYPE
    encFlRaw,       // 196: RESERVED_196
    encFlRaw,       // 197: RESERVED_197
    encFlRaw,       // 198: ENABLE
    encFlRaw,       // 199: DEVICEID
    encFlRaw,       // 200: TIMEOUT
    encFlRaw,       // 201: CURRENT_ROUTE
    encFlInt,       // 202: SECONDARY_CONNECTION_CALL_ID
    encFlRaw,       // 203: PRIORITY_QUEUE_NUMBER
    encFlRaw,       // 204: TEAM_NAME
    encFlRaw,       // 205: MEMBER_TYPE
    encFlStr,       // 206: EVENT_DEVICE_ID
    encFlRaw,       // 207: LOGIN_NAME_V11
    encFlInt,       // 208: PERIPHERAL_ID_V11
    encFlRaw,       // 209: CALL_TYPE_KEY_CONFIG_V11
    encFlRaw,       // 210: CALL_TYPE_ID_V11
    encFlRaw,       // 211: CUSTOMER_DEFINITION_ID_V11
    encFlRaw,       // 212: ENTERPRISE_NAME_V11
    encFlRaw,       // 213: CUR_PERIPHERAL_NUMBER
    encFlRaw,       // 214: CUR_LOGIN_ID
    encFlStr,       // 215: ANI_II
    encFlRaw,       // 216: MR_DOMAIN_ID
    encFlStr,       // 217: CTIOS_CIL_CLIENT_ID
    SilentMonitorStatus.encodeFloat,        // 218: SILENT_MONITOR_STATUS
    encFlStr,       // 219: REQUESTING_DEVICE_ID
    encFlRaw,       // 220: REQUESTING_DEVICE_ID_TYPE
    encFlInt,       // 221: PRE_CALL_INVOKE_ID
    encFlRaw,       // 222: ENTERPRISE_QUEUE_TIME
    encFlRaw,       // 223: CALL_REFERENCE_ID
    encFlShortBool, // 224: MULTI_LINE_AGENT_CONTROL
    encFlRaw,       // 225: NETWORK_CONTROLLED
    encFlStr,       // 226: CLIENT_ADDRESS_IPV6
    encFlStr,       // 227: SENDING_ADDRESS_IPV6
    encFlShort,     // 228: NUM_PERIPHERALS
    encFlInt,       // 229: COC_CONNECTION_CALL_ID
    encFlShort,     // 230: COC_CONNECTION_DEVICE_ID_TYPE
    encFlStr,       // 231: COC_CONNECTION_DEVICE_ID
    encFlByte,      // 232: CALL_ORIGINATED_FROM
    encFlRaw,       // 233: SET_APPDATA_CALLID
    encFlRaw,       // 234: CLIENT_SHARE_KEY
    encFlRaw,       // 235: RESERVED_235
    encFlRaw,       // 236: RESERVED_236
    encFlRaw,       // 237: RESERVED_237
    encFlRaw,       // 238: RESERVED_238
    encFlRaw,       // 239: RESERVED_239
    encFlRaw,       // 240: RESERVED_240
    encFlRaw,       // 241: RESERVED_241
    encFlRaw,       // 242: RESERVED_242
    encFlStr,       // 243: AGENT_TEAM_NAME
    CallDirection.encodeFloat,      // 244: DIRECTION
    encFlRaw,       // 245: RESERVED_245
    encFlRaw,       // 246: RESERVED_246
    encFlRaw,       // 247: RESERVED_247
    encFlRaw,       // 248: RESERVED_248
    encFlRaw,       // 249: RESERVED_249
    encFlRaw,       // 250: RESERVED_250
    encFlRaw,       // 251: RESERVED_251
    encFlRaw,       // 252: RESERVED_252
    encFlRaw,       // 253: RESERVED_253
    encFlRaw,       // 254: RESERVED_254
    encFlRaw        // 255: RESERVED_255
  )
}

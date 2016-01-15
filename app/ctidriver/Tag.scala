/**
 *
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

  // script generated code starts here
  val
  ActiveConnectionCallID,
  ActiveConnectionDeviceIDType,
  AddedPartyDeviceType,
  AgentAction,
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
  CallVariableMask,
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
  ForcedFlag,
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
  Status,
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

/*
  val
  // 257
  ActiveConnectionCallID,
  ActiveConnectionDeviceIDType,
  AddedPartyDeviceType,
  // 260
  AgentAction,
  AgentAvailabilityStatusTag,
  AgentConnectionCallID,
  AgentConnectionDeviceIDType,
  AgentDeskSettingTag,  // Bad Tag.  Not in original document.
  AgentMode,
  AgentOutCallsHeldSession,
  AgentOutCallsHeldTimeSession,
  AgentOutCallsHeldTimeTo5,
  AgentOutCallsHeldTimeToHalf,
  // 270
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
  // 280
  AgentOutCallsTimeTo5,
  AgentOutCallsTimeToHalf,
  AgentOutCallsTimeToday,
  AgentOutCallsTo5,
  AgentOutCallsToHalf,
  AgentOutCallsToday,
  AgentServiceReq,
  AgentStateMaskTag,
  AgentStateTag,
  AgentStatisticsTag,
  // 290
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
  // 300
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
  // 310
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
  // 320
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
  // 330
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
  // 340
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
  // 350
  CallMsgMask,
  CallOptionTag,
  CallPlacementTypeTag,
  CallTypeID,
  CallTypeTag,
  CallVariableMask,
  CalledDeviceType,
  CalledPartyDisposition,
  CallingDeviceType,
  CallsQ5,
  // 360
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
  // 370
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
  // 380
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
  // 390
  FacilityTypeTag,
  FailingDeviceType,
  FailureCode,
  ForcedFlag,
  HandledCallsAfterCallTimeSession,
  HandledCallsAfterCallTimeTo5,
  HandledCallsAfterCallTimeToHalf,
  HandledCallsAfterCallTimeToday,
  HandledCallsSession,
  HandledCallsTalkTimeSession,
  // 400
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
  // 410
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
  // 420
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
  // 430
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
  // 440
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
  // 450
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
  // 460
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
  // 470
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
  // 480
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
  // 490
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
  // 500
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
  // 510
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
  // 520
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
  // 530
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
  // 540
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
  // 550
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
  // 560
  ReservationCallsToHalf,
  ReservationCallsToday,
  Reserved16,
  RetrievingDeviceType,
  RingNoAnswerDN,
  RingNoAnswerTime,
  RoutableTimeSession,
  RoutableTimeToday,
  RouterCallsQNow,
  SecondaryCallID,
  // 570
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
  // 580
  SilentMonitorWarningMessage,
  SkillGroupID,
  SkillGroupNumber,
  SkillGroupPriority,
  SkillGroupState,
  SkillGroupStatisticsTag,
  SnapshotDeviceType,
  State,
  StateDuration,
  Status,
  // 590
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
  // 600
  ToneDuration,
  TransferConferenceSetup,
  TransferredDeviceType,
  TransferringDeviceType,
  TrunkUsedDeviceType,
  TypeOfDeviceTag,
  UnknownTag,
  VersionNumber,
  WhisperCallsSession,
  WhisperCallsTo5,
  // 610
  WhisperCallsToHalf,
  WhisperCallsToday,
  WorkModeTimer,
  WrapupDataIncomingMode,
  WrapupDataOutgoingMode,
  ZZZZInternalUseOnly
  = Value
*/


  //
  // Decode a fixed part field
  //
  def decodeFixedField(tag: Tag, body: ByteString): ((Tag, Any), Int) =
    fxTagToDecodeFunc(tag.id - ActiveConnectionCallID.id)(tag, body)

  //
  // Decoder functions of fixed part for simple types
  //
  def decFxRaw(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body), body.size)
  def decFxInt(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.toInt), 4)
  def decFxShort(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.toShort), 2)
  def decFxByte(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body(0)), 1)
  def decFxIntBool(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.toInt != 0), 4)
  def decFxShortBool(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body.toShort != 0), 2)
  def decFxIntBitSet(tag: Tag, body: ByteString): ((Tag, Any), Int) =
    ((tag, BitSet fromBitMask Array(body.toInt.toLong)), 4)
  def decFxShortBitSet(tag: Tag, body: ByteString): ((Tag, Any), Int) =
    ((tag, BitSet fromBitMask Array(body.toShort.toLong)), 2)
  def decFxMsgType(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, MessageType fromInt body.toInt), 4)
  def decFxNotYetImplementedInt(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body), 4)
  def decFxNotYetImplementedShort(tag: Tag, body: ByteString): ((Tag, Any), Int) = ((tag, body), 2)

  //
  // DON'T EDIT THIS TABLE!!
  // This table is generated from TagDecoderFuncTbl.txt using gen-func-table.sh
  //
  // Jump table from Tag to decoding function for floating part
  //
  val fxTagToDecodeFunc: Array[(Tag, ByteString) => ((Tag, Any), Int)] = Array(
    decFxInt, 	// ActiveConnectionCallID
    ConnectionDeviceIDType.decodeWithLen, 	// ActiveConnectionDeviceIDType
    DeviceIDType.decodeWithLen, 	// AddedPartyDeviceType
    decFxNotYetImplementedShort, 	// AgentAction
    AgentAvailabilityStatus.decodeWithLen, 	// AgentAvailabilityStatusTag
    decFxInt, 	// AgentConnectionCallID
    ConnectionDeviceIDType.decodeWithLen, 	// AgentConnectionDeviceIDType
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
    AgentState.decodeWithLen, 	// AgentStateTag
    AgentWorkMode.decodeWithLen, 	// AgentWorkModeTag
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
    DeviceIDType.decodeWithLen, 	// AlertingDeviceType
    AllocationState.decodeWithLen, 	// AllocationStateTag
    decFxInt, 	// AnswerDetectControl1
    decFxInt, 	// AnswerDetectControl2
    AnswerDetectMode.decodeWithLen, 	// AnswerDetectModeTag
    decFxShort, 	// AnswerDetectTime
    DeviceIDType.decodeWithLen, 	// AnsweringDeviceType
    AnsweringMachine.decodeWithLen, 	// AnsweringMachineTag
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
    CallMannerType.decodeWithLen, 	// CallMannerTypeTag
    decFxIntBitSet, 	// CallMsgMask
    CallOption.decodeWithLen, 	// CallOptionTag
    CallPlacementType.decodeWithLen, 	// CallPlacementTypeTag
    decFxInt, 	// CallTypeID
    CallType.decodeWithLen, 	// CallTypeTag
    decFxShortBitSet, 	// CallVariableMask
    DeviceIDType.decodeWithLen, 	// CalledDeviceType
    DispositionCodeValue.decodeWithLen, 	// CalledPartyDisposition
    DeviceIDType.decodeWithLen, 	// CallingDeviceType
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
    ConfigOperation.decodeWithLen, 	// ConfigOperationTag
    decFxInt, 	// ConnectionCallID
    ConnectionDeviceIDType.decodeWithLen, 	// ConnectionDeviceIDTypeTag
    ConsultType.decodeWithLen, 	// ConsultTypeTag
    DeviceIDType.decodeWithLen, 	// ControllerDeviceType
    decFxIntBitSet, 	// DeskSettingsMaskTag
    DestinationCountry.decodeWithLen, 	// DestinationCountryTag
    DistributionValue.decodeWithLen, 	// Distribution
    DeviceIDType.decodeWithLen, 	// DivertingDeviceType
    decFxShortBool, 	// EchoCancellation
    decFxIntBool, 	// EmergencyCallMethod
    decFxInt, 	// EmergencyCallsSession
    decFxInt, 	// EmergencyCallsTo5
    decFxInt, 	// EmergencyCallsToHalf
    decFxInt, 	// EmergencyCallsToday
    EventCause.decodeWithLen, 	// EventCauseTag
    EventCode.decodeWithLen, 	// EventCodeTag
    DeviceIDType.decodeWithLen, 	// EventDeviceType
    decFxShort, 	// EventReasonCode
    FacilityType.decodeWithLen, 	// FacilityTypeTag
    DeviceIDType.decodeWithLen, 	// FailingDeviceType
    ControlFailureCode.decodeWithLen, 	// FailureCode
    decFxByte, 	// ForcedFlag
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
    ConnectionDeviceIDType.decodeWithLen, 	// HeldConnectionDeviceIDType
    DeviceIDType.decodeWithLen, 	// HoldingDeviceType
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
    DeviceIDType.decodeWithLen, 	// LastRedirectDeviceType
    decFxShort, 	// LineHandle
    LineType.decodeWithLen, 	// LineTypeTag
    LocalConnectionState.decodeWithLen, 	// LocalConnectionStateTag
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
    DeviceIDType.decodeWithLen, 	// MonitoredDeviceType
    decFxShortBool, 	// More
    decFxInt, 	// NewConnectionCallID
    ConnectionDeviceIDType.decodeWithLen, 	// NewConnectionDeviceIDType
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
    ConnectionDeviceIDType.decodeWithLen, 	// OtherConnectionDeviceIDType
    decFxIntBitSet, 	// OtherFeaturesSupported
    decFxIntBitSet, 	// PGStatus
    decFxInt, 	// PacketSize
    decFxInt, 	// PauseDuration
    decFxShort, 	// PayloadType
    decFxInt, 	// PeripheralErrorCode
    decFxInt, 	// PeripheralID
    decFxShortBool, 	// PeripheralOnline
    PeripheralType.decodeWithLen, 	// PeripheralTypeTag
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
    DeviceIDType.decodeWithLen, 	// PrimaryDeviceIDType
    decFxShortBool, 	// Priority
    decFxInt, 	// QualityRecordingRate
    decFxInt, 	// QueryRuleID
    DeviceIDType.decodeWithLen, 	// QueueDeviceType
    RTPDirection.decodeWithLen, 	// RTPDirectionTag
    RTPType.decodeWithLen, 	// RTPTypeTag
    decFxRaw, 	// RawBytes
    decFxIntBool, 	// RecordingMode
    decFxInt, 	// RegisteredServiceID
    DeviceIDType.decodeWithLen, 	// ReleasingDeviceType
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
    decFxShort, 	// Reserved16
    DeviceIDType.decodeWithLen, 	// RetrievingDeviceType
    decFxInt, 	// RingNoAnswerDN
    decFxInt, 	// RingNoAnswerTime
    decFxInt, 	// RoutableTimeSession
    decFxInt, 	// RoutableTimeToday
    decFxInt, 	// RouterCallsQNow
    decFxInt, 	// SecondaryCallID
    decFxInt, 	// SecondaryConnectionCallID
    DeviceIDType.decodeWithLen, 	// SecondaryDeviceIDType
    decFxShort, 	// SegmentNumber
    decFxInt, 	// ServerData
    ServerMode.decodeWithLen, 	// ServerModeTag
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
    AgentState.decodeWithLen, 	// SkillGroupState
    DeviceIDType.decodeWithLen, 	// SnapshotDeviceType
    ClientEventReportState.decodeWithLen, 	// ClientEventStateTag
    decFxInt, 	// StateDuration
    StatusCode.decodeWithLen, 	// Status
    decFxIntBool, 	// SupervisorAssistCallMethod
    decFxInt, 	// SupervisorConnectionCallID
    ConnectionDeviceIDType.decodeWithLen, 	// SupervisorConnectionDeviceIDType
    decFxInt, 	// SupervisorID
    SupervisoryAction.decodeWithLen, 	// SupervisoryActionTag
    decFxInt, 	// SystemEventArg1
    decFxInt, 	// SystemEventArg2
    decFxInt, 	// SystemEventArg3
    SystemEventID.decodeWithLen, 	// SystemEventIDTag
    decFxInt, 	// TeamID
    decFxShort, 	// ToneDuration
    decFxIntBitSet, 	// TransferConferenceSetup
    DeviceIDType.decodeWithLen, 	// TransferredDeviceType
    DeviceIDType.decodeWithLen, 	// TransferringDeviceType
    DeviceIDType.decodeWithLen, 	// TrunkUsedDeviceType
    TypeOfDevice.decodeWithLen, 	// TypeOfDeviceTag
    decFxInt, 	// VersionNumber
    decFxInt, 	// WhisperCallsSession
    decFxInt, 	// WhisperCallsTo5
    decFxInt, 	// WhisperCallsToHalf
    decFxInt, 	// WhisperCallsToday
    decFxInt, 	// WorkModeTimer
    WrapupDataMode.decodeWithLen, 	// WrapupDataIncomingMode
    WrapupDataMode.decodeWithLen 	// WrapupDataOutgoingMode
  )


  //
  // Decode a floating part field
  //
  def decodeFloatingField(tag: Tag, len: Int, body: ByteString): (Tag, Any) =
    flTagToDecodeFunc(tag.id)(tag, len, body)

  //
  // Decoder functions of floating part for simple types
  //
  def decFlRaw(tag: Tag, len: Int, body: ByteString): (Tag, Any) = (tag, body take len)
  def decFlStr(tag: Tag, len: Int, body: ByteString): (Tag, Any) = (tag, body.toString(len))
  def decFlInt(tag: Tag, len: Int, body: ByteString): (Tag, Any) = (tag, body.toInt)
  def decFlShort(tag: Tag, len: Int, body: ByteString): (Tag, Any) = (tag, body.toShort)

  //
  // Jump table from Tag to decoding function for floating part
  //
  val flTagToDecodeFunc: Array[(Tag, Int, ByteString) => (Tag, Any)] = Array(
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
    (tag, len, body) => DeviceIDType.decode(tag, body),             //  40: PARTY_DEVID_TYPE
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
    (tag, len, body) => ConnectionDeviceIDType.decode(tag, body),   //  57: CALL_CONN_DEVID_TYPE
    decFlStr,       //  58: CALL_CONN_DEVID
    decFlShort,     //  59: CALL_DEVID_TYPE
    decFlStr,       //  60: CALL_DEVID
    decFlShort,     //  61: CALL_DEV_CONN_STATE
    decFlInt,       //  62: SKILL_GROUP_NUMBER
    decFlInt,       //  63: SKILL_GROUP_ID
    decFlShort,     //  64: SKILL_GROUP_PRIORITY
    (tag, len, body) => AgentState.decode(tag, body),               //  65: SKILL_GROUP_STATE
    decFlStr,       //  66: OBJECT_NAME
    decFlStr,       //  67: DTMF_STRING
    decFlStr,       //  68: POSITION_ID
    decFlStr,       //  69: SUPERVISOR_ID
    decFlShort,     //  70: LINE_HANDLE
    (tag, len, body) => LineType.decode(tag, body), //  71: LINE_TYPE
    decFlInt,       //  72: ROUTER_CALL_KEY_DAY
    decFlInt,       //  73: ROUTER_CALL_KEY_CALLID
    decFlRaw,       //  74: RESERVED_74
    (tag, len, body) => LocalConnectionState.decode(tag, body),     //  75: CALL_STATE
    decFlStr,       //  76: MONITORED_DEVID
    decFlStr,       //  77: AUTHORIZATION_CODE
    decFlStr,       //  78: ACCOUNT_CODE
    decFlStr,       //  79: ORIGINATING_DEVID
    decFlStr,       //  80: ORIGINATING_LINE_ID
    decFlStr,       //  81: CLIENT_ADDRESS
    (tag, len, body) => (tag, body.toNamedVar(len)),                //  82: NAMED_VARIABLE
    (tag, len, body) => (tag, body.toNamedArray(len)),              //  83: NAMED_ARRAY
    decFlStr,       //  84: CALL_CONTROL_TABLE
    decFlStr,       //  85: SUPERVISOR_INSTRUMENT
    decFlStr,       //  86: ATC_AGENT_ID
    (tag, len, body) => (tag, BitSet fromBitMask Array(body.toShort.toLong)),       //  87: AGENT_FLAGS
    (tag, len, body) => AgentState.decode(tag, body),               //  88: ATC_AGENT_STATE
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
    (tag, len, body) => AgentState.decode(tag, body),               // 123: NEXT_AGENT_STATE
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
    (tag, len, body) => SilentMonitorStatus.decode(tag, body),      // 218: SILENT_MONITOR_STATUS
    decFlStr,       // 219: REQUESTING_DEVICE_ID
    decFlRaw,       // 220: REQUESTING_DEVICE_ID_TYPE
    decFlInt,       // 221: PRE_CALL_INVOKE_ID
    decFlRaw,       // 222: ENTERPRISE_QUEUE_TIME
    decFlRaw,       // 223: CALL_REFERENCE_ID
    (tag, len, body) => (tag, body.toShort != 0),   // 224: MULTI_LINE_AGENT_CONTROL
    decFlRaw,       // 225: NETWORK_CONTROLLED
    decFlStr,       // 226: CLIENT_ADDRESS_IPV6
    decFlStr,       // 227: SENDING_ADDRESS_IPV6
    decFlShort,     // 228: NUM_PERIPHERALS
    decFlInt,       // 229: COC_CONNECTION_CALL_ID
    decFlShort,     // 230: COC_CONNECTION_DEVICE_ID_TYPE
    decFlStr,       // 231: COC_CONNECTION_DEVICE_ID
    (tag, len, body) => (tag, body(0)),             // 232: CALL_ORIGINATED_FROM
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
    (tag, len, body) => CallDirection.decode(tag, body),            // 244: DIRECTION
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
}

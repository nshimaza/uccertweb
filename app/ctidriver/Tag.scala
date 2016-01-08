/**
 *
 */
package ctidriver

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
  val
  ActiveConnectionCallID,
  ActiveConnectionDeviceIDType,
  AddedPartyDeviceType,
  AgentAction,
  AgentAvailabilityStatusTag,
  AgentConnectionCallID,
  AgentConnectionDeviceIDType,
  AgentDeskSettingTag,
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
  AgentStatisticsTag,
  AgentWorkMode,
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
  AllocationState,
  AnswerDetectControl1,
  AnswerDetectControl2,
  AnswerDetectMode,
  AnswerDetectTime,
  AnsweringDeviceType,
  AnsweringMachine,
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
  CallMannerType,
  CallMsgMask,
  CallOption,
  CallPlacementType,
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
  ConsultType,
  ControllerDeviceType,
  DeskSettingsMaskTag,
  DestinationCountry,
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
  FacilityType,
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
  MessageTypeTag,
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
  SecondaryDeviceIDType,
  SegmentNumber,
  ServerData,
  ServerMode,
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
  SkillGroupStatisticsTag,
  SnapshotDeviceType,
  State,
  StateDuration,
  Status,
  SupervisorAssistCallMethod,
  SupervisorConnectionCallID,
  SupervisorConnectionDeviceIDType,
  SupervisorID,
  SupervisoryAction,
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
  UnknownTag,
  VersionNumber,
  WhisperCallsSession,
  WhisperCallsTo5,
  WhisperCallsToHalf,
  WhisperCallsToday,
  WorkModeTimer,
  WrapupDataIncomingMode,
  WrapupDataOutgoingMode
  = Value
}

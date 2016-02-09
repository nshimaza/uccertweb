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

import ctidriver.Tag._

/**
 * Message types and message type specific field structures
 *
 */

object MessageType extends IntEnum {
  type MessageType = Value
  val UNKNOWN_TYPE                          = Value(  0)
  val FAILURE_CONF                          = Value(  1)
  val FAILURE_EVENT                         = Value(  2)
  val OPEN_REQ                              = Value(  3)
  val OPEN_CONF                             = Value(  4)
  val HEARTBEAT_REQ                         = Value(  5)
  val HEARTBEAT_CONF                        = Value(  6)
  val CLOSE_REQ                             = Value(  7)
  val CLOSE_CONF                            = Value(  8)
  val CALL_DELIVERED_EVENT                  = Value(  9)
  val CALL_ESTABLISHED_EVENT                = Value( 10)
  val CALL_HELD_EVENT                       = Value( 11)
  val CALL_RETRIEVED_EVENT                  = Value( 12)
  val CALL_CLEARED_EVENT                    = Value( 13)
  val CALL_CONNECTION_CLEARED_EVENT         = Value( 14)
  val CALL_ORIGINATED_EVENT                 = Value( 15)
  val CALL_FAILED_EVENT                     = Value( 16)
  val CALL_CONFERENCED_EVENT                = Value( 17)
  val CALL_TRANSFERRED_EVENT                = Value( 18)
  val CALL_DIVERTED_EVENT                   = Value( 19)
  val CALL_SERVICE_INITIATED_EVENT          = Value( 20)
  val CALL_QUEUED_EVENT                     = Value( 21)
  val CALL_TRANSLATION_ROUTE_EVENT          = Value( 22)
  val BEGIN_CALL_EVENT                      = Value( 23)
  val END_CALL_EVENT                        = Value( 24)
  val CALL_DATA_UPDATE_EVENT                = Value( 25)
  val SET_CALL_DATA_REQ                     = Value( 26)
  val SET_CALL_DATA_CONF                    = Value( 27)
  val RELEASE_CALL_REQ                      = Value( 28)
  val RELEASE_CALL_CONF                     = Value( 29)
  val AGENT_STATE_EVENT                     = Value( 30)
  val SYSTEM_EVENT                          = Value( 31)
  val CLIENT_EVENT_REPORT_REQ               = Value( 32)
  val CLIENT_EVENT_REPORT_CONF              = Value( 33)
  val CALL_REACHED_NETWORK_EVENT            = Value( 34)
  val CONTROL_FAILURE_CONF                  = Value( 35)
  val QUERY_AGENT_STATE_REQ                 = Value( 36)
  val QUERY_AGENT_STATE_CONF                = Value( 37)
  val SET_AGENT_STATE_REQ                   = Value( 38)
  val SET_AGENT_STATE_CONF                  = Value( 39)
  val ALTERNATE_CALL_REQ                    = Value( 40)
  val ALTERNATE_CALL_CONF                   = Value( 41)
  val ANSWER_CALL_REQ                       = Value( 42)
  val ANSWER_CALL_CONF                      = Value( 43)
  val CLEAR_CALL_REQ                        = Value( 44)
  val CLEAR_CALL_CONF                       = Value( 45)
  val CLEAR_CONNECTION_REQ                  = Value( 46)
  val CLEAR_CONNECTION_CONF                 = Value( 47)
  val CONFERENCE_CALL_REQ                   = Value( 48)
  val CONFERENCE_CALL_CONF                  = Value( 49)
  val CONSULTATION_CALL_REQ                 = Value( 50)
  val CONSULTATION_CALL_CONF                = Value( 51)
  val DEFLECT_CALL_REQ                      = Value( 52)
  val DEFLECT_CALL_CONF                     = Value( 53)
  val HOLD_CALL_REQ                         = Value( 54)
  val HOLD_CALL_CONF                        = Value( 55)
  val MAKE_CALL_REQ                         = Value( 56)
  val MAKE_CALL_CONF                        = Value( 57)
  val MAKE_PREDICTIVE_CALL_REQ              = Value( 58)
  val MAKE_PREDICTIVE_CALL_CONF             = Value( 59)
  val RECONNECT_CALL_REQ                    = Value( 60)
  val RECONNECT_CALL_CONF                   = Value( 61)
  val RETRIEVE_CALL_REQ                     = Value( 62)
  val RETRIEVE_CALL_CONF                    = Value( 63)
  val TRANSFER_CALL_REQ                     = Value( 64)
  val TRANSFER_CALL_CONF                    = Value( 65)
  val RESERVED_66                           = Value( 66)
  val RESERVED_67                           = Value( 67)
  val RESERVED_68                           = Value( 68)
  val RESERVED_69                           = Value( 69)
  val RESERVED_70                           = Value( 70)
  val RESERVED_71                           = Value( 71)
  val RESERVED_72                           = Value( 72)
  val RESERVED_73                           = Value( 73)
  val RESERVED_74                           = Value( 74)
  val RESERVED_75                           = Value( 75)
  val RESERVED_76                           = Value( 76)
  val RESERVED_77                           = Value( 77)
  val QUERY_DEVICE_INFO_REQ                 = Value( 78)
  val QUERY_DEVICE_INFO_CONF                = Value( 79)
  val RESERVED_80                           = Value( 80)
  val RESERVED_81                           = Value( 81)
  val SNAPSHOT_CALL_REQ                     = Value( 82)
  val SNAPSHOT_CALL_CONF                    = Value( 83)
  val SNAPSHOT_DEVICE_REQ                   = Value( 84)
  val SNAPSHOT_DEVICE_CONF                  = Value( 85)
  val CALL_DEQUEUED_EVENT                   = Value( 86)
  val RESERVED_87                           = Value( 87)
  val RESERVED_88                           = Value( 88)
  val RESERVED_89                           = Value( 89)
  val RESERVED_90                           = Value( 90)
  val SEND_DTMF_SIGNAL_REQ                  = Value( 91)
  val SEND_DTMF_SIGNAL_CONF                 = Value( 92)
  val MONITOR_START_REQ                     = Value( 93)
  val MONITOR_START_CONF                    = Value( 94)
  val MONITOR_STOP_REQ                      = Value( 95)
  val MONITOR_STOP_CONF                     = Value( 96)
  val CHANGE_MONITOR_MASK_REQ               = Value( 97)
  val CHANGE_MONITOR_MASK_CONF              = Value( 98)
  val CLIENT_SESSION_OPENED_EVENT           = Value( 99)
  val CLIENT_SESSION_CLOSED_EVENT           = Value(100)
  val SESSION_MONITOR_START_REQ             = Value(101)
  val SESSION_MONITOR_START_CONF            = Value(102)
  val SESSION_MONITOR_STOP_REQ              = Value(103)
  val SESSION_MONITOR_STOP_CONF             = Value(104)
  val AGENT_PRE_CALL_EVENT                  = Value(105)
  val AGENT_PRE_CALL_ABORT_EVENT            = Value(106)
  val USER_MESSAGE_REQ                      = Value(107)
  val USER_MESSAGE_CONF                     = Value(108)
  val USER_MESSAGE_EVENT                    = Value(109)
  val REGISTER_VARIABLES_REQ                = Value(110)
  val REGISTER_VARIABLES_CONF               = Value(111)
  val QUERY_AGENT_STATISTICS_REQ            = Value(112)
  val QUERY_AGENT_STATISTICS_CONF           = Value(113)
  val QUERY_SKILL_GROUP_STATISTICS_REQ      = Value(114)
  val QUERY_SKILL_GROUP_STATISTICS_CONF     = Value(115)
  val RTP_STARTED_EVENT                     = Value(116)
  val RTP_STOPPED_EVENT                     = Value(117)
  val SUPERVISOR_ASSIST_REQ                 = Value(118)
  val SUPERVISOR_ASSIST_CONF                = Value(119)
  val SUPERVISOR_ASSIST_EVENT               = Value(120)
  val EMERGENCY_CALL_REQ                    = Value(121)
  val EMERGENCY_CALL_CONF                   = Value(122)
  val EMERGENCY_CALL_EVENT                  = Value(123)
  val SUPERVISE_CALL_REQ                    = Value(124)
  val SUPERVISE_CALL_CONF                   = Value(125)
  val AGENT_TEAM_CONFIG_REQ                 = Value(126)
  val AGENT_TEAM_CONFIG_CONF                = Value(127)
  val AGENT_TEAM_CONFIG_EVENT               = Value(128)
  val SET_APP_DATA_REQ                      = Value(129)
  val SET_APP_DATA_CONF                     = Value(130)
  val AGENT_DESK_SETTINGS_REQ               = Value(131)
  val AGENT_DESK_SETTINGS_CONF              = Value(132)
  val LIST_AGENT_TEAM_REQ                   = Value(133)
  val LIST_AGENT_TEAM_CONF                  = Value(134)
  val MONITOR_AGENT_TEAM_START_REQ          = Value(135)
  val MONITOR_AGENT_TEAM_START_CONF         = Value(136)
  val MONITOR_AGENT_TEAM_STOP_REQ           = Value(137)
  val MONITOR_AGENT_TEAM_STOP_CONF          = Value(138)
  val BAD_CALL_REQ                          = Value(139)
  val BAD_CALL_CONF                         = Value(140)
  val SET_DEVICE_ATTRIBUTES_REQ             = Value(141)
  val SET_DEVICE_ATTRIBUTES_CONF            = Value(142)
  val REGISTER_SERVICE_REQ                  = Value(143)
  val REGISTER_SERVICE_CONF                 = Value(144)
  val UNREGISTER_SERVICE_REQ                = Value(145)
  val UNREGISTER_SERVICE_CONF               = Value(146)
  val START_RECORDING_REQ                   = Value(147)
  val START_RECORDING_CONF                  = Value(148)
  val STOP_RECORDING_REQ                    = Value(149)
  val STOP_RECORDING_CONF                   = Value(150)
  val MEDIA_LOGIN_REQ                       = Value(151)
  val MEDIA_LOGIN_RESP                      = Value(152)
  val MEDIA_LOGOUT_IND                      = Value(153)
  val MAKE_AGENT_ROUTABLE_IND               = Value(154)
  val MAKE_AGENT_NOT_ROUTABLE_REQ           = Value(155)
  val MAKE_AGENT_NOT_ROUTABLE_RESP          = Value(156)
  val MAKE_AGENT_READY_IND                  = Value(157)
  val MAKE_AGENT_NOT_READY_REQ              = Value(158)
  val MAKE_AGENT_NOT_READY_RESP             = Value(159)
  val OFFER_TASK_IND                        = Value(160)
  val OFFER_APPLICATION_TASK_REQ            = Value(161)
  val OFFER_APPLICATION_TASK_RESP           = Value(162)
  val START_TASK_IND                        = Value(163)
  val START_APPLICATION_TASK_REQ            = Value(164)
  val START_APPLICATION_TASK_RESP           = Value(165)
  val PAUSE_TASK_IND                        = Value(166)
  val RESUME_TASK_IND                       = Value(167)
  val WRAPUP_TASK_IND                       = Value(168)
  val END_TASK_IND                          = Value(169)
  val AGENT_MADE_NOT_ROUTABLE_EVENT         = Value(170)
  val AGENT_INTERRUPT_ADVISORY_EVENT        = Value(171)
  val AGENT_INTERRUPT_ACCEPTED_IND          = Value(172)
  val AGENT_INTERRUPT_UNACCEPTED_IND        = Value(173)
  val AGENT_INTERRUPT_DONE_ADVISORY_EVENT   = Value(174)
  val AGENT_INTERRUPT_DONE_ACCEPTED_IND     = Value(175)
  val CHANGE_MAX_TASK_LIMIT_REQ             = Value(176)
  val CHANGE_MAX_TASK_LIMIT_RESP            = Value(177)
  val OVERRIDE_LIMIT_REQ                    = Value(178)
  val OVERRIDE_LIMIT_RESP                   = Value(179)
  val UPDATE_TASK_CONTEXT_IND               = Value(180)
  val BEGIN_AGENT_INIT_IND                  = Value(181)
  val AGENT_INIT_REQ                        = Value(182)
  val AGENT_INIT_RESP                       = Value(183)
  val END_AGENT_INIT_IND                    = Value(184)
  val TASK_INIT_IND                         = Value(185)
  val AGENT_INIT_READY_EVENT                = Value(186)
  val GET_PRECALL_MESSAGES_REQ              = Value(187)
  val GET_PRECALL_MESSAGES_RESP             = Value(188)
  val AGENT_LEGACY_PRE_CALL_EVENT           = Value(189)
  val FAILURE_RESP                          = Value(190)
  val BEGIN_TASK_EVENT                      = Value(191)
  val QUEUED_TASK_EVENT                     = Value(192)
  val DEQUEUED_TASK_EVENT                   = Value(193)
  val OFFER_TASK_EVENT                      = Value(194)
  val START_TASK_EVENT                      = Value(195)
  val PAUSE_TASK_EVENT                      = Value(196)
  val RESUME_TASK_EVENT                     = Value(197)
  val WRAPUP_TASK_EVENT                     = Value(198)
  val END_TASK_EVENT                        = Value(199)
  val TASK_DATA_UPDATE_EVENT                = Value(200)
  val TASK_MONITOR_START_REQ                = Value(201)
  val TASK_MONITOR_START_CONF               = Value(202)
  val TASK_MONITOR_STOP_REQ                 = Value(203)
  val TASK_MONITOR_STOP_CONF                = Value(204)
  val CHANGE_TASK_MONITOR_MASK_REQ          = Value(205)
  val CHANGE_TASK_MONITOR_MASK_CONF         = Value(206)
  val MAX_TASK_LIFETIME_EXCEEDED_EVENT      = Value(207)
  val SET_APP_PATH_DATA_IND                 = Value(208)
  val TASK_INIT_REQ                         = Value(209)
  val TASK_INIT_RESP                        = Value(210)
  val ROUTE_REGISTER_EVENT                  = Value(211)
  val ROUTE_REGISTER_REPLY_EVENT            = Value(212)
  val ROUTE_REQUEST_EVENT                   = Value(213)
  val ROUTE_SELECT                          = Value(214)
  val ROUTE_END                             = Value(215)
  val RESERVED_216                          = Value(216)
  val RESERVED_217                          = Value(217)
  val RESERVED_218                          = Value(218)
  val RESERVED_219                          = Value(219)
  val RESERVED_220                          = Value(220)
  val RESERVED_221                          = Value(221)
  val RESERVED_222                          = Value(222)
  val RESERVED_223                          = Value(223)
  val RESERVED_224                          = Value(224)
  val RESERVED_225                          = Value(225)
  val RESERVED_226                          = Value(226)
  val RESERVED_227                          = Value(227)
  val RESERVED_228                          = Value(228)
  val RESERVED_229                          = Value(229)
  val RESERVED_230                          = Value(230)
  val RESERVED_231                          = Value(231)
  val RESERVED_232                          = Value(232)
  val RESERVED_233                          = Value(233)
  val RESERVED_234                          = Value(234)
  val RESERVED_235                          = Value(235)
  val TEAM_CONFIG_REQ                       = Value(236)
  val TEAM_CONFIG_EVENT                     = Value(237)
  val TEAM_CONFIG_CONF                      = Value(238)
  val RESERVED_239                          = Value(239)
  val CALL_ATTRIBUTE_CHANGE_EVENT           = Value(240)
  val RESERVED_241                          = Value(241)
  val RESERVED_242                          = Value(242)
  val RESERVED_243                          = Value(243)
  val RESERVED_244                          = Value(244)
  val RESERVED_245                          = Value(245)
  val RESERVED_246                          = Value(246)
  val CALL_TERMINATION_EVENT                = Value(247)
  val CALL_AGENT_GREETING_EVENT             = Value(248)
  val AGENT_GREETING_CONTROL_REQ            = Value(249)
  val AGENT_GREETING_CONTROL_CONF           = Value(250)


  private val fixedPartListTable: Array[List[Tag]] = Array(
    //   0: UNKNOWN_TYPE
    List(RawBytes),
    //   1: FAILURE_CONF
    List(InvokeID, Status),
    //   2: FAILURE_EVENT
    List(Status),
    //   3: OPEN_REQ
    List(InvokeID, VersionNumber, IdleTimeout, PeripheralID, ServiceRequested, CallMsgMask, AgentStateMaskTag,
      ConfigMsgMask, Reserved1, Reserved2, Reserved3),
    //   4: OPEN_CONF
    List(InvokeID, ServiceGranted, MonitorID, PGStatus, ICMCentralControllerTime, PeripheralOnline,
      PeripheralTypeTag, AgentStateTag),
    //   5: HEARTBEAT_REQ
    List(InvokeID),
    //   6: HEARTBEAT_CONF
    List(InvokeID),
    //   7: CLOSE_REQ
    List(Status),
    //   8: CLOSE_CONF
    List(InvokeID),
    //   9: CALL_DELIVERED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID, LineHandle,
      LineTypeTag, ServiceNumber, ServiceID, SkillGroupNumber, SkillGroupID, SkillGroupPriority,
      AlertingDeviceType, CallingDeviceType, CalledDeviceType, LastRedirectDeviceType, LocalConnectionStateTag,
      EventCauseTag, NumNamedVariables, NumNamedArrays),
    //  10: CALL_ESTABLISHED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID, LineHandle,
      LineTypeTag, ServiceNumber, ServiceID, SkillGroupNumber, SkillGroupID, SkillGroupPriority,
      AnsweringDeviceType, CallingDeviceType, CalledDeviceType, LastRedirectDeviceType, LocalConnectionStateTag,
      EventCauseTag),
    //  11: CALL_HELD_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID,
      HoldingDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  12: CALL_RETRIEVED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID,
      RetrievingDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  13: CALL_CLEARED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID,
      LocalConnectionStateTag, EventCauseTag),
    //  14: CALL_CONNECTION_CLEARED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID,
      ReleasingDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  15: CALL_ORIGINATED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID, LineHandle,
      LineTypeTag, ServiceNumber, ServiceID, SkillGroupNumber, SkillGroupID, SkillGroupPriority,
      CallingDeviceType, CalledDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  16: CALL_FAILED_EVENT
    List(PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID, FailingDeviceType,
      CalledDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  17: CALL_CONFERENCED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, PrimaryDeviceIDType, PrimaryCallID, LineHandle,
      LineTypeTag, SkillGroupNumber, SkillGroupID, SkillGroupPriority, NumParties, SecondaryDeviceIDType,
      SecondaryCallID, ControllerDeviceType, AddedPartyDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  18: CALL_TRANSFERRED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, PrimaryDeviceIDType, PrimaryCallID, LineHandle,
      LineTypeTag, SkillGroupNumber, SkillGroupID, SkillGroupPriority, NumParties, SecondaryDeviceIDType,
      SecondaryCallID, TransferringDeviceType, TransferredDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  19: CALL_DIVERTED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID,
      ServiceNumber, ServiceID, DivertingDeviceType, CalledDeviceType, LocalConnectionStateTag,
      EventCauseTag),
    //  20: CALL_SERVICE_INITIATED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID, LineHandle,
      LineTypeTag, ServiceNumber, ServiceID, SkillGroupNumber, SkillGroupID, SkillGroupPriority,
      CallingDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  21: CALL_QUEUED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID,
      ServiceNumber, ServiceID, QueueDeviceType, CallingDeviceType, CalledDeviceType, LastRedirectDeviceType,
      NumQueued, NumSkillGroups, LocalConnectionStateTag, EventCauseTag),
    //  22: CALL_TRANSLATION_ROUTE_EVENT
    List(NumNamedVariables, NumNamedArrays),
    //  23: BEGIN_CALL_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, NumCTIClients, NumNamedVariables, NumNamedArrays,
      CallTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID, CalledPartyDisposition),
    //  24: END_CALL_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID),
    //  25: CALL_DATA_UPDATE_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, NumCTIClients, NumNamedVariables, NumNamedArrays,
      CallTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID, NewConnectionDeviceIDType, NewConnectionCallID,
      CalledPartyDisposition, CampaignID, QueryRuleID),
    //  26: SET_CALL_DATA_REQ
    List(InvokeID, PeripheralID, ConnectionDeviceIDTypeTag, ConnectionCallID, NumNamedVariables, NumNamedArrays,
      CallTypeTag, CalledPartyDisposition, CampaignID, QueryRuleID),
    //  27: SET_CALL_DATA_CONF
    List(InvokeID),
    //  28: RELEASE_CALL_REQ
    List(InvokeID, PeripheralID, ConnectionDeviceIDTypeTag, ConnectionCallID),
    //  29: RELEASE_CALL_CONF
    List(InvokeID),
    //  30: AGENT_STATE_EVENT
    List(MonitorID, PeripheralID, SessionID, PeripheralTypeTag, SkillGroupState, StateDuration,
      SkillGroupNumber, SkillGroupID, SkillGroupPriority, AgentStateTag, EventReasonCode, MRDID,
      NumTasks, AgentMode, MaxTaskLimit, ICMAgentID, AgentAvailabilityStatusTag, NumFltSkillGroups),
    //  31: SYSTEM_EVENT
    List(PGStatus, ICMCentralControllerTime, SystemEventIDTag, SystemEventArg1, SystemEventArg2,
      SystemEventArg3, EventDeviceType),
    //  32: CLIENT_EVENT_REPORT_REQ
    List(InvokeID, ClientEventStateTag),
    //  33: CLIENT_EVENT_REPORT_CONF
    List(InvokeID),
    //  34: CALL_REACHED_NETWORK_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID, LineHandle,
      LineTypeTag, TrunkUsedDeviceType, CalledDeviceType, LocalConnectionStateTag, EventCauseTag),
    //  35: CONTROL_FAILURE_CONF
    List(InvokeID, FailureCode, PeripheralErrorCode),
    //  36: QUERY_AGENT_STATE_REQ
    List(InvokeID, PeripheralID, MRDID, ICMAgentID),
    //  37: QUERY_AGENT_STATE_CONF
    List(InvokeID, AgentStateTag, NumSkillGroups, MRDID, NumTasks, AgentMode, MaxTaskLimit, ICMAgentID,
      AgentAvailabilityStatusTag),
    //  38: SET_AGENT_STATE_REQ
    List(InvokeID, PeripheralID, AgentStateTag, AgentWorkModeTag, NumSkillGroups, EventReasonCode, ForcedFlagTag,
      AgentServiceReq),
    //  39: SET_AGENT_STATE_CONF
    List(InvokeID),
    //  40: ALTERNATE_CALL_REQ
    List(InvokeID, PeripheralID, ActiveConnectionCallID, OtherConnectionCallID, ActiveConnectionDeviceIDType,
      OtherConnectionDeviceIDType),
    //  41: ALTERNATE_CALL_CONF
    List(InvokeID),
    //  42: ANSWER_CALL_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag),
    //  43: ANSWER_CALL_CONF
    List(InvokeID),
    //  44: CLEAR_CALL_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag),
    //  45: CLEAR_CALL_CONF
    List(InvokeID),
    //  46: CLEAR_CONNECTION_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag),
    //  47: CLEAR_CONNECTION_CONF
    List(InvokeID),
    //  48: CONFERENCE_CALL_REQ
    List(InvokeID, PeripheralID, HeldConnectionCallID, ActiveConnectionCallID, HeldConnectionDeviceIDType,
      ActiveConnectionDeviceIDType, CallPlacementTypeTag, CallMannerTypeTag, AlertRings, CallOptionTag,
      FacilityTypeTag, AnsweringMachineTag, Priority, PostRoute, NumNamedVariables, NumNamedArrays),
    //  49: CONFERENCE_CALL_CONF
    List(InvokeID, NewConnectionCallID, NewConnectionDeviceIDType, NumParties, LineHandle, LineTypeTag),
    //  50: CONSULTATION_CALL_REQ
    List(InvokeID, PeripheralID, ActiveConnectionCallID, ActiveConnectionDeviceIDType, CallPlacementTypeTag,
      CallMannerTypeTag, ConsultTypeTag, AlertRings, CallOptionTag, FacilityTypeTag, AnsweringMachineTag,
      Priority, PostRoute, NumNamedVariables, NumNamedArrays),
    //  51: CONSULTATION_CALL_CONF
    List(InvokeID, NewConnectionCallID, NewConnectionDeviceIDType, LineHandle, LineTypeTag),
    //  52: DEFLECT_CALL_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag, CalledDeviceType),
    //  53: DEFLECT_CALL_CONF
    List(InvokeID),
    //  54: HOLD_CALL_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag, Reservation),
    //  55: HOLD_CALL_CONF
    List(InvokeID),
    //  56: MAKE_CALL_REQ
    List(InvokeID, PeripheralID, CallPlacementTypeTag, CallMannerTypeTag, AlertRings, CallOptionTag, FacilityTypeTag,
      AnsweringMachineTag, Priority, PostRoute, NumNamedVariables, NumNamedArrays, SkillGroupNumber),
    //  57: MAKE_CALL_CONF
    List(InvokeID, NewConnectionCallID, NewConnectionDeviceIDType, LineHandle, LineTypeTag),
    //  58: MAKE_PREDICTIVE_CALL_REQ
    List(InvokeID, PeripheralID, CallPlacementTypeTag, CallMannerTypeTag, AlertRings, CallOptionTag, FacilityTypeTag,
      AnsweringMachineTag, Priority, AllocationStateTag, DestinationCountryTag, AnswerDetectModeTag, AnswerDetectTime,
      AnswerDetectControl1, AnswerDetectControl2, NumNamedVariables, NumNamedArrays),
    //  59: MAKE_PREDICTIVE_CALL_CONF
    List(InvokeID, NewConnectionCallID, NewConnectionDeviceIDType, LineHandle, LineTypeTag),
    //  60: RECONNECT_CALL_REQ
    List(InvokeID, PeripheralID, ActiveConnectionCallID, HeldConnectionCallID, ActiveConnectionDeviceIDType,
      HeldConnectionDeviceIDType),
    //  61: RECONNECT_CALL_CONF
    List(InvokeID),
    //  62: RETRIEVE_CALL_REQ
    List(InvokeID, PeripheralID, HeldConnectionCallID, HeldConnectionDeviceIDType),
    //  63: RETRIEVE_CALL_CONF
    List(InvokeID),
    //  64: TRANSFER_CALL_REQ
    List(InvokeID, PeripheralID, ActiveConnectionCallID, HeldConnectionCallID, ActiveConnectionDeviceIDType,
      HeldConnectionDeviceIDType, CallPlacementTypeTag, CallMannerTypeTag, AlertRings, CallOptionTag, FacilityTypeTag,
      AnsweringMachineTag, Priority, PostRoute, NumNamedVariables, NumNamedArrays),
    //  65: TRANSFER_CALL_CONF
    List(InvokeID, NewConnectionCallID, NewConnectionDeviceIDType, NumParties, LineHandle, LineTypeTag),
    //  66: RESERVED_66
    List(RawBytes),
    //  67: RESERVED_67
    List(RawBytes),
    //  68: RESERVED_68
    List(RawBytes),
    //  69: RESERVED_69
    List(RawBytes),
    //  70: RESERVED_70
    List(RawBytes),
    //  71: RESERVED_71
    List(RawBytes),
    //  72: RESERVED_72
    List(RawBytes),
    //  73: RESERVED_73
    List(RawBytes),
    //  74: RESERVED_74
    List(RawBytes),
    //  75: RESERVED_75
    List(RawBytes),
    //  76: RESERVED_76
    List(RawBytes),
    //  77: RESERVED_77
    List(RawBytes),
    //  78: QUERY_DEVICE_INFO_REQ
    List(InvokeID, PeripheralID, Reserved1),
    //  79: QUERY_DEVICE_INFO_CONF
    List(InvokeID, PeripheralTypeTag, TypeOfDeviceTag, ClassOfDeviceTag, NumLines, Reserved16,
      MaxActiveCalls, MaxHeldCalls, MaxDeviceInConference, MakeCallSetup, TransferConferenceSetup,
      CallEventsSupported, CallControlSupported, OtherFeaturesSupported),
    //  80: RESERVED_80
    List(RawBytes),
    //  81: RESERVED_81
    List(RawBytes),
    //  82: SNAPSHOT_CALL_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag),
    //  83: SNAPSHOT_CALL_CONF
    List(InvokeID, CallTypeTag, NumCTIClients, NumCallDevices, NumNamedVariables, NumNamedArrays,
      CalledPartyDisposition),
    //  84: SNAPSHOT_DEVICE_REQ
    List(InvokeID, PeripheralID, SnapshotDeviceType),
    //  85: SNAPSHOT_DEVICE_CONF
    List(InvokeID, NumCalls),
    //  86: CALL_DEQUEUED_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, ConnectionCallID,
      QueueDeviceType, ServiceNumber, ServiceID, NumQueued, NumSkillGroups, LocalConnectionStateTag,
      EventCauseTag),
    //  87: RESERVED_87
    List(RawBytes),
    //  88: RESERVED_88
    List(RawBytes),
    //  89: RESERVED_89
    List(RawBytes),
    //  90: RESERVED_90
    List(RawBytes),
    //  91: SEND_DTMF_SIGNAL_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag, ToneDuration, PauseDuration),
    //  92: SEND_DTMF_SIGNAL_CONF
    List(InvokeID),
    //  93: MONITOR_START_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, CallMsgMask, AgentStateMaskTag, ConnectionDeviceIDTypeTag,
      MonitoredDeviceType),
    //  94: MONITOR_START_CONF
    List(InvokeID, MonitorID),
    //  95: MONITOR_STOP_REQ
    List(InvokeID, MonitorID),
    //  96: MONITOR_STOP_CONF
    List(InvokeID),
    //  97: CHANGE_MONITOR_MASK_REQ
    List(InvokeID, MonitorID, CallMsgMask, AgentStateMaskTag),
    //  98: CHANGE_MONITOR_MASK_CONF
    List(InvokeID),
    //  99: CLIENT_SESSION_OPENED_EVENT
    List(SessionID, PeripheralID, ServiceGranted, CallMsgMask, AgentStateMaskTag, ClientPort),
    // 100: CLIENT_SESSION_CLOSED_EVENT
    List(SessionID, PeripheralID, Status, ClientPort),
    // 101: SESSION_MONITOR_START_REQ
    List(InvokeID, SessionID),
    // 102: SESSION_MONITOR_START_CONF
    List(InvokeID, MonitorID),
    // 103: SESSION_MONITOR_STOP_REQ
    List(InvokeID, SessionID),
    // 104: SESSION_MONITOR_STOP_CONF
    List(InvokeID),
    // 105: AGENT_PRE_CALL_EVENT
    List(MonitorID, NumNamedVariables, NumNamedArrays, ServiceNumber, ServiceID, SkillGroupNumber,
      SkillGroupID, SkillGroupPriority, MRDID),
    // 106: AGENT_PRE_CALL_ABORT_EVENT
    List(MonitorID, MRDID),
    // 107: USER_MESSAGE_REQ
    List(InvokeID, PeripheralID, Distribution),
    // 108: USER_MESSAGE_CONF
    List(InvokeID),
    // 109: USER_MESSAGE_EVENT
    List(ICMCentralControllerTime, Distribution),
    // 110: REGISTER_VARIABLES_REQ
    List(InvokeID, CallVariableMaskTag, NumNamedVariables, NumNamedArrays),
    // 111: REGISTER_VARIABLES_CONF
    List(InvokeID),
    // 112: QUERY_AGENT_STATISTICS_REQ
    List(InvokeID, PeripheralID),
    // 113: QUERY_AGENT_STATISTICS_CONF
    List(InvokeID, PeripheralID,
      // Total 84 counters
      // 1-10
      AvailTimeSession,
      LoggedOnTimeSession,
      NotReadyTimeSession,
      ICMAvailableTimeSession,
      RoutableTimeSession,
      AgentOutCallsSession,
      AgentOutCallsTalkTimeSession,
      AgentOutCallsTimeSession,
      AgentOutCallsHeldSession,
      AgentOutCallsHeldTimeSession,
      // 11-20
      HandledCallsSession,
      HandledCallsTalkTimeSession,
      HandledCallsAfterCallTimeSession,
      HandledCallsTimeSession,
      IncomingCallsHeldSession,
      IncomingCallsHeldTimeSession,
      InternalCallsSession,
      InternalCallsTimeSession,
      InternalCallsRcvdSession,
      InternalCallsRcvdTimeSession,
      // 21-30
      InternalCallsHeldSession,
      InternalCallsHeldTimeSession,
      AutoOutCallsSession,
      AutoOutCallsTalkTimeSession,
      AutoOutCallsTimeSession,
      AutoOutCallsHeldSession,
      AutoOutCallsHeldTimeSession,
      PreviewCallsSession,
      PreviewCallsTalkTimeSession,
      PreviewCallsTimeSession,
      // 31-40
      PreviewCallsHeldSession,
      PreviewCallsHeldTimeSession,
      ReservationCallsSession,
      ReservationCallsTalkTimeSession,
      ReservationCallsTimeSession,
      ReservationCallsHeldSession,
      ReservationCallsHeldTimeSession,
      BargeInCallsSession,
      InterceptCallsSession,
      MonitorCallsSession,
      // 41-50
      WhisperCallsSession,
      EmergencyCallsSession,
      AvailTimeToday,
      LoggedOnTimeToday,
      NotReadyTimeToday,
      ICMAvailableTimeToday,
      RoutableTimeToday,
      AgentOutCallsToday,
      AgentOutCallsTalkTimeToday,
      AgentOutCallsTimeToday,
      // 51-60
      AgentOutCallsHeldToday,
      AgentOutCallsHeldTimeToday,
      HandledCallsToday,
      HandledCallsTalkTimeToday,
      HandledCallsAfterCallTimeToday,
      HandledCallsTimeToday,
      IncomingCallsHeldToday,
      IncomingCallsHeldTimeToday,
      InternalCallsToday,
      InternalCallsTimeToday,
      // 61-70
      InternalCallsRcvdToday,
      InternalCallsRcvdTimeToday,
      InternalCallsHeldToday,
      InternalCallsHeldTimeToday,
      AutoOutCallsToday,
      AutoOutCallsTalkTimeToday,
      AutoOutCallsTimeToday,
      AutoOutCallsHeldToday,
      AutoOutCallsHeldTimeToday,
      PreviewCallsToday,
      // 71-80
      PreviewCallsTalkTimeToday,
      PreviewCallsTimeToday,
      PreviewCallsHeldToday,
      PreviewCallsHeldTimeToday,
      ReservationCallsToday,
      ReservationCallsTalkTimeToday,
      ReservationCallsTimeToday,
      ReservationCallsHeldToday,
      ReservationCallsHeldTimeToday,
      BargeInCallsToday,
      // 81-84
      InterceptCallsToday,
      MonitorCallsToday,
      WhisperCallsToday,
      EmergencyCallsToday),
    // 114: QUERY_SKILL_GROUP_STATISTICS_REQ
    List(InvokeID, PeripheralID, SkillGroupNumber, SkillGroupID),
    // 115: QUERY_SKILL_GROUP_STATISTICS_CONF
    List(InvokeID, PeripheralID, SkillGroupNumber, SkillGroupID,
      // Total 145 counters
      // 1-10
      AgentsLoggedOn,
      AgentsAvail,
      AgentsNotReady,
      AgentsReady,
      AgentsTalkingIn,
      AgentsTalkingOut,
      AgentsTalkingOther,
      AgentsWorkNotReady,
      AgentsWorkReady,
      AgentsBusyOther,
      // 11-20
      AgentsReserved,
      AgentsHold,
      AgentsICMAvailable,
      AgentsApplicationAvailable,
      AgentsTalkingAutoOut,
      AgentsTalkingPreview,
      AgentsTalkingReservation,
      RouterCallsQNow,
      LongestRouterCallQNow,
      CallsQNow,
      // 21-30
      CallsQTimeNow,
      LongestCallQNow,
      AvailTimeTo5,
      LoggedOnTimeTo5,
      NotReadyTimeTo5,
      AgentOutCallsTo5,
      AgentOutCallsTalkTimeTo5,
      AgentOutCallsTimeTo5,
      AgentOutCallsHeldTo5,
      AgentOutCallsHeldTimeTo5,
      // 31-40
      HandledCallsTo5,
      HandledCallsTalkTimeTo5,
      HandledCallsAfterCallTimeTo5,
      HandledCallsTimeTo5,
      IncomingCallsHeldTo5,
      IncomingCallsHeldTimeTo5,
      InternalCallsRcvdTo5,
      InternalCallsRcvdTimeTo5,
      InternalCallsHeldTo5,
      InternalCallsHeldTimeTo5,
      // 41-50
      AutoOutCallsTo5,
      AutoOutCallsTalkTimeTo5,
      AutoOutCallsTimeTo5,
      AutoOutCallsHeldTo5,
      AutoOutCallsHeldTimeTo5,
      PreviewCallsTo5,
      PreviewCallsTalkTimeTo5,
      PreviewCallsTimeTo5,
      PreviewCallsHeldTo5,
      PreviewCallsHeldTimeTo5,
      // 51-60
      ReservationCallsTo5,
      ReservationCallsTalkTimeTo5,
      ReservationCallsTimeTo5,
      ReservationCallsHeldTo5,
      ReservationCallsHeldTimeTo5,
      BargeInCallsTo5,
      InterceptCallsTo5,
      MonitorCallsTo5,
      WhisperCallsTo5,
      EmergencyCallsTo5,
      // 61-70
      CallsQ5,
      CallsQTime5,
      LongestCallQ5,
      AvailTimeToHalf,
      LoggedOnTimeToHalf,
      NotReadyTimeToHalf,
      AgentOutCallsToHalf,
      AgentOutCallsTalkTimeToHalf,
      AgentOutCallsTimeToHalf,
      AgentOutCallsHeldToHalf,
      // 71-80
      AgentOutCallsHeldTimeToHalf,
      HandledCallsToHalf,
      HandledCallsTalkTimeToHalf,
      HandledCallsAfterCallTimeToHalf,
      HandledCallsTimeToHalf,
      IncomingCallsHeldToHalf,
      IncomingCallsHeldTimeToHalf,
      InternalCallsRcvdToHalf,
      InternalCallsRcvdTimeToHalf,
      InternalCallsHeldToHalf,
      // 81-90
      InternalCallsHeldTimeToHalf,
      AutoOutCallsToHalf,
      AutoOutCallsTalkTimeToHalf,
      AutoOutCallsTimeToHalf,
      AutoOutCallsHeldToHalf,
      AutoOutCallsHeldTimeToHalf,
      PreviewCallsToHalf,
      PreviewCallsTalkTimeToHalf,
      PreviewCallsTimeToHalf,
      PreviewCallsHeldToHalf,
      // 91-100
      PreviewCallsHeldTimeToHalf,
      ReservationCallsToHalf,
      ReservationCallsTalkTimeToHalf,
      ReservationCallsTimeToHalf,
      ReservationCallsHeldToHalf,
      ReservationCallsHeldTimeToHalf,
      BargeInCallsToHalf,
      InterceptCallsToHalf,
      MonitorCallsToHalf,
      WhisperCallsToHalf,
      // 101-110
      EmergencyCallsToHalf,
      CallsQHalf,
      CallsQTimeHalf,
      LongestCallQHalf,
      AvailTimeToday,
      LoggedOnTimeToday,
      NotReadyTimeToday,
      AgentOutCallsToday,
      AgentOutCallsTalkTimeToday,
      AgentOutCallsTimeToday,
      // 111-120
      AgentOutCallsHeldToday,
      AgentOutCallsHeldTimeToday,
      HandledCallsToday,
      HandledCallsTalkTimeToday,
      HandledCallsAfterCallTimeToday,
      HandledCallsTimeToday,
      IncomingCallsHeldToday,
      IncomingCallsHeldTimeToday,
      InternalCallsRcvdToday,
      InternalCallsRcvdTimeToday,
      // 121-130
      InternalCallsHeldToday,
      InternalCallsHeldTimeToday,
      AutoOutCallsToday,
      AutoOutCallsTalkTimeToday,
      AutoOutCallsTimeToday,
      AutoOutCallsHeldToday,
      AutoOutCallsHeldTimeToday,
      PreviewCallsToday,
      PreviewCallsTalkTimeToday,
      PreviewCallsTimeToday,
      // 131-140
      PreviewCallsHeldToday,
      PreviewCallsHeldTimeToday,
      ReservationCallsToday,
      ReservationCallsTalkTimeToday,
      ReservationCallsTimeToday,
      ReservationCallsHeldToday,
      ReservationCallsHeldTimeToday,
      BargeInCallsToday,
      InterceptCallsToday,
      MonitorCallsToday,
      // 141-145
      WhisperCallsToday,
      EmergencyCallsToday,
      CallsQToday,
      CallsQTimeToday,
      LongestCallQToday),
    // 116: RTP_STARTED_EVENT
    List(MonitorID, PeripheralID, ClientPort, RTPDirectionTag, RTPTypeTag, BitRate, EchoCancellation,
      PacketSize, PayloadType, ConnectionDeviceIDTypeTag, ConnectionCallID),
    // 117: RTP_STOPPED_EVENT
    List(MonitorID, PeripheralID, ClientPort, RTPDirectionTag, ConnectionDeviceIDTypeTag, ConnectionCallID),
    // 118: SUPERVISOR_ASSIST_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag),
    // 119: SUPERVISOR_ASSIST_CONF
    List(InvokeID, ConnectionCallID, ConnectionDeviceIDTypeTag, LineHandle, LineTypeTag),
    // 120: SUPERVISOR_ASSIST_EVENT
    List(ZZZZInternalUseOnly),
    // 121: EMERGENCY_CALL_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag),
    // 122: EMERGENCY_CALL_CONF
    List(InvokeID, ConnectionCallID, ConnectionDeviceIDTypeTag, LineHandle, LineTypeTag),
    // 123: EMERGENCY_CALL_EVENT
    List(PeripheralID, ConnectionCallID, ConnectionDeviceIDTypeTag, SessionID),
    // 124: SUPERVISE_CALL_REQ
    List(InvokeID, PeripheralID, AgentConnectionCallID, SupervisorConnectionCallID, AgentConnectionDeviceIDType,
      SupervisorConnectionDeviceIDType, SupervisoryActionTag),
    // 125: SUPERVISE_CALL_CONF
    List(InvokeID, ConnectionCallID, ConnectionDeviceIDTypeTag),
    // 126: AGENT_TEAM_CONFIG_REQ
    List(ZZZZInternalUseOnly),
    // 127: AGENT_TEAM_CONFIG_CONF
    List(RawBytes),
    // 128: AGENT_TEAM_CONFIG_EVENT
    List(PeripheralID, TeamID, NumberOfAgents, ConfigOperationTag),
    // 129: SET_APP_DATA_REQ
    List(InvokeID),
    // 130: SET_APP_DATA_CONF
    List(InvokeID),
    // 131: AGENT_DESK_SETTINGS_REQ
    List(InvokeID, PeripheralID),
    // 132: AGENT_DESK_SETTINGS_CONF
    List(InvokeID, PeripheralID,
      DeskSettingsMaskTag,
      WrapupDataIncomingMode,
      WrapupDataOutgoingMode,
      LogoutNonActivityTime,
      QualityRecordingRate,
      RingNoAnswerTime,
      SilentMonitorWarningMessage,
      SilentMonitorAudibleIndication,
      SupervisorAssistCallMethod,
      EmergencyCallMethod,
      AutoRecordOnEmergency,
      RecordingMode,
      WorkModeTimer,
      RingNoAnswerDN),
    // 133: LIST_AGENT_TEAM_REQ
    List(InvokeID, SupervisorID),
    // 134: LIST_AGENT_TEAM_CONF
    List(InvokeID, NumberOfAgentTeams, SegmentNumber, More),
    // 135: MONITOR_AGENT_TEAM_START_REQ
    List(InvokeID, TeamID),
    // 136: MONITOR_AGENT_TEAM_START_CONF
    List(InvokeID, MonitorID),
    // 137: MONITOR_AGENT_TEAM_STOP_REQ
    List(InvokeID, MonitorID),
    // 138: MONITOR_AGENT_TEAM_STOP_CONF
    List(InvokeID),
    // 139: BAD_CALL_REQ
    List(InvokeID, PeripheralID, ConnectionDeviceIDTypeTag, ConnectionCallID),
    // 140: BAD_CALL_CONF
    List(InvokeID),
    // 141: SET_DEVICE_ATTRIBUTES_REQ
    List(InvokeID, PeripheralID, ServiceNumber, ServiceID, SkillGroupNumber, SkillGroupID, SkillGroupPriority,
      CallTypeTag, CallingDeviceType),
    // 142: SET_DEVICE_ATTRIBUTES_CONF
    List(InvokeID),
    // 143: REGISTER_SERVICE_REQ
    List(InvokeID, ServerModeTag),
    // 144: REGISTER_SERVICE_CONF
    List(InvokeID, RegisteredServiceID),
    // 145: UNREGISTER_SERVICE_REQ
    List(InvokeID, RegisteredServiceID),
    // 146: UNREGISTER_SERVICE_CONF
    List(InvokeID),
    // 147: START_RECORDING_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ClientPort, BitRate, PacketSize, ConnectionDeviceIDTypeTag,
      RTPDirectionTag, RTPTypeTag, EchoCancellation, PayloadType),
    // 148: START_RECORDING_CONF
    List(InvokeID, SessionID, ServerData),
    // 149: STOP_RECORDING_REQ
    List(InvokeID, PeripheralID, ConnectionCallID, ClientPort, SessionID, ServerData, ConnectionDeviceIDTypeTag,
      RTPDirectionTag),
    // 150: STOP_RECORDING_CONF
    List(InvokeID),
    // 151: MEDIA_LOGIN_REQ
    List(ZZZZInternalUseOnly),
    // 152: MEDIA_LOGIN_RESP
    List(RawBytes),
    // 153: MEDIA_LOGOUT_IND
    List(RawBytes),
    // 154: MAKE_AGENT_ROUTABLE_IND
    List(RawBytes),
    // 155: MAKE_AGENT_NOT_ROUTABLE_REQ
    List(ZZZZInternalUseOnly),
    // 156: MAKE_AGENT_NOT_ROUTABLE_RESP
    List(RawBytes),
    // 157: MAKE_AGENT_READY_IND
    List(RawBytes),
    // 158: MAKE_AGENT_NOT_READY_REQ
    List(ZZZZInternalUseOnly),
    // 159: MAKE_AGENT_NOT_READY_RESP
    List(RawBytes),
    // 160: OFFER_TASK_IND
    List(RawBytes),
    // 161: OFFER_APPLICATION_TASK_REQ
    List(ZZZZInternalUseOnly),
    // 162: OFFER_APPLICATION_TASK_RESP
    List(RawBytes),
    // 163: START_TASK_IND
    List(RawBytes),
    // 164: START_APPLICATION_TASK_REQ
    List(ZZZZInternalUseOnly),
    // 165: START_APPLICATION_TASK_RESP
    List(RawBytes),
    // 166: PAUSE_TASK_IND
    List(RawBytes),
    // 167: RESUME_TASK_IND
    List(RawBytes),
    // 168: WRAPUP_TASK_IND
    List(RawBytes),
    // 169: END_TASK_IND
    List(RawBytes),
    // 170: AGENT_MADE_NOT_ROUTABLE_EVENT
    List(RawBytes),
    // 171: AGENT_INTERRUPT_ADVISORY_EVENT
    List(RawBytes),
    // 172: AGENT_INTERRUPT_ACCEPTED_IND
    List(RawBytes),
    // 173: AGENT_INTERRUPT_UNACCEPTED_IND
    List(RawBytes),
    // 174: AGENT_INTERRUPT_DONE_ADVISORY_EVENT
    List(RawBytes),
    // 175: AGENT_INTERRUPT_DONE_ACCEPTED_IND
    List(RawBytes),
    // 176: CHANGE_MAX_TASK_LIMIT_REQ
    List(ZZZZInternalUseOnly),
    // 177: CHANGE_MAX_TASK_LIMIT_RESP
    List(RawBytes),
    // 178: OVERRIDE_LIMIT_REQ
    List(ZZZZInternalUseOnly),
    // 179: OVERRIDE_LIMIT_RESP
    List(RawBytes),
    // 180: UPDATE_TASK_CONTEXT_IND
    List(RawBytes),
    // 181: BEGIN_AGENT_INIT_IND
    List(RawBytes),
    // 182: AGENT_INIT_REQ
    List(ZZZZInternalUseOnly),
    // 183: AGENT_INIT_RESP
    List(RawBytes),
    // 184: END_AGENT_INIT_IND
    List(RawBytes),
    // 185: TASK_INIT_IND
    List(RawBytes),
    // 186: AGENT_INIT_READY_EVENT
    List(RawBytes),
    // 187: GET_PRECALL_MESSAGES_REQ
    List(ZZZZInternalUseOnly),
    // 188: GET_PRECALL_MESSAGES_RESP
    List(RawBytes),
    // 189: AGENT_LEGACY_PRE_CALL_EVENT
    List(RawBytes),
    // 190: FAILURE_RESP
    List(RawBytes),
    // 191: BEGIN_TASK_EVENT
    List(RawBytes),
    // 192: QUEUED_TASK_EVENT
    List(RawBytes),
    // 193: DEQUEUED_TASK_EVENT
    List(RawBytes),
    // 194: OFFER_TASK_EVENT
    List(RawBytes),
    // 195: START_TASK_EVENT
    List(RawBytes),
    // 196: PAUSE_TASK_EVENT
    List(RawBytes),
    // 197: RESUME_TASK_EVENT
    List(RawBytes),
    // 198: WRAPUP_TASK_EVENT
    List(RawBytes),
    // 199: END_TASK_EVENT
    List(RawBytes),
    // 200: TASK_DATA_UPDATE_EVENT
    List(RawBytes),
    // 201: TASK_MONITOR_START_REQ
    List(ZZZZInternalUseOnly),
    // 202: TASK_MONITOR_START_CONF
    List(RawBytes),
    // 203: TASK_MONITOR_STOP_REQ
    List(ZZZZInternalUseOnly),
    // 204: TASK_MONITOR_STOP_CONF
    List(RawBytes),
    // 205: CHANGE_TASK_MONITOR_MASK_REQ
    List(ZZZZInternalUseOnly),
    // 206: CHANGE_TASK_MONITOR_MASK_CONF
    List(RawBytes),
    // 207: MAX_TASK_LIFETIME_EXCEEDED_EVENT
    List(RawBytes),
    // 208: SET_APP_PATH_DATA_IND
    List(RawBytes),
    // 209: TASK_INIT_REQ
    List(ZZZZInternalUseOnly),
    // 210: TASK_INIT_RESP
    List(RawBytes),
    // 211: ROUTE_REGISTER_EVENT
    List(RawBytes),
    // 212: ROUTE_REGISTER_REPLY_EVENT
    List(RawBytes),
    // 213: ROUTE_REQUEST_EVENT
    List(RawBytes),
    // 214: ROUTE_SELECT
    List(RawBytes),
    // 215: ROUTE_END
    List(RawBytes),
    // 216: RESERVED_216
    List(RawBytes),
    // 217: RESERVED_217
    List(RawBytes),
    // 218: RESERVED_218
    List(RawBytes),
    // 219: RESERVED_219
    List(RawBytes),
    // 220: RESERVED_220
    List(RawBytes),
    // 221: RESERVED_221
    List(RawBytes),
    // 222: RESERVED_222
    List(RawBytes),
    // 223: RESERVED_223
    List(RawBytes),
    // 224: RESERVED_224
    List(RawBytes),
    // 225: RESERVED_225
    List(RawBytes),
    // 226: RESERVED_226
    List(RawBytes),
    // 227: RESERVED_227
    List(RawBytes),
    // 228: RESERVED_228
    List(RawBytes),
    // 229: RESERVED_229
    List(RawBytes),
    // 230: RESERVED_230
    List(RawBytes),
    // 231: RESERVED_231
    List(RawBytes),
    // 232: RESERVED_232
    List(RawBytes),
    // 233: RESERVED_233
    List(RawBytes),
    // 234: RESERVED_234
    List(RawBytes),
    // 235: RESERVED_235
    List(RawBytes),
    // 236: TEAM_CONFIG_REQ
    List(ZZZZInternalUseOnly),
    // 237: TEAM_CONFIG_EVENT
    List(RawBytes),
    // 238: TEAM_CONFIG_CONF
    List(RawBytes),
    // 239: RESERVED_239
    List(RawBytes),
    // 240: CALL_ATTRIBUTE_CHANGE_EVENT
    List(MonitorID, PeripheralID, PeripheralTypeTag, ConnectionDeviceIDTypeTag, CallTypeID, ServiceNumber),
    // 241: RESERVED_241
    List(RawBytes),
    // 242: RESERVED_242
    List(RawBytes),
    // 243: RESERVED_243
    List(RawBytes),
    // 244: RESERVED_244
    List(RawBytes),
    // 245: RESERVED_245
    List(RawBytes),
    // 246: RESERVED_246
    List(RawBytes),
    // 247: CALL_TERMINATION_EVENT
    List(RawBytes),
    // 248: CALL_AGENT_GREETING_EVENT
    List(MonitorID, PeripheralID, ConnectionDeviceIDTypeTag, ConnectionCallID, EventCodeTag,
      PeripheralErrorCode),
    // 249: AGENT_GREETING_CONTROL_REQ
    List(InvokeID, PeripheralID, AgentActionTag),
    // 250: AGENT_GREETING_CONTROL_CONF
    List(InvokeID)
  )

  def getFixedPartList(t: Option[MessageType]): List[Tag] = fixedPartListTable(t.getOrElse(UNKNOWN_TYPE).id)
}

/**
 *
 */
package ctidriver

/**
 * Special constants of CTI Server Protocol
 * 
 */
object SpecialValue {
  val MAX_NUM_CTI_CLIENTS: Int    = 16
  val MAX_NUM_PARTIES: Int        = 16
  val MAX_NUM_DEVICES: Int        = 16
  val MAX_NUM_CALLS: Int          = 16
  val MAX_NUM_SKILL_GROUPS: Int   = 20
  val MAX_NUM_LINES: Int          = 10
  val NULL_CALL_ID: Int           = 0xffffffff
  val NULL_PERIPHERAL_ID: Int     = 0xffffffff
  val NULL_SERVICE: Int           = 0xffffffff
  val NULL_SKILL_GROUP: Int       = 0xffffffff
  val NULL_CALLTYPE: Int          = 0xffff
}

object PeripheralType extends ShortEnum {
  type PeripheralType = Value
  val NONE                    = Value(0xffff)
  val ASPECT                  = Value( 1)
  val MERIDIAN                = Value( 2)
  val G2                      = Value( 3)
  val DEFINITY_ECS_NON_EAS    = Value( 4)
  val DEFINITY_ECS_EAS        = Value( 5)
  val GALAXY                  = Value( 6)
  val SPECTRUM                = Value( 7)
  val VRU                     = Value( 8)
  val VRU_POLLED              = Value( 9)
  val DMS100                  = Value(10)
  val SIEMENS_9006            = Value(11)
  val SIEMENS_9005            = Value(12)
  val ALCATEL                 = Value(13)
  val NEC_NEAX_2x00           = Value(14)
  val ACP_1000                = Value(15)
  val SYMPOSIUM               = Value(16)
  val ENTERPRISE_AGENT        = Value(17)
  val MD110                   = Value(18)
  val MEDIA_ROUTING           = Value(19)
  val GENERIC                 = Value(20)
  val ACMI_CRS                = Value(21)
  val ACMI_IPCC               = Value(22)
  val SIMPLIFIED_IPCC         = Value(23)
  val ARS                     = Value(24)
  val ACMI_ERS                = Value(25)
  val ACMI_EXPERT_ADVISOR     = Value(26)
  val RESERVED                = Value(27)
}

object AgentState extends ShortEnum {
  type AgentState = Value
  val LOGIN           = Value( 0)
  val LOGOUT          = Value( 1)
  val NOT_READY       = Value( 2)
  val AVAILABLE       = Value( 3)
  val TALKING         = Value( 4)
  val WORK_NOT_READY  = Value( 5)
  val WORK_READY      = Value( 6)
  val BUSY_OTHER      = Value( 7)
  val RESERVED        = Value( 8)
  val UNKNOWN         = Value( 9)
  val HOLD            = Value(10)
  val ACTIVE          = Value(11)
  val PAUSED          = Value(12)
  val INTERRUPTED     = Value(13)
  val NOT_ACTIVE      = Value(14)
}

object CallType extends ShortEnum {
  type CallType = Value
  val ACD_IN                        = Value( 1)
  val PREROUTE_ACD_IN               = Value( 2)
  val PREROUTE_DIRECT_AGENT         = Value( 3)
  val TRANSFER_IN                   = Value( 4)
  val OVERFLOW_IN                   = Value( 5)
  val OTHER_IN                      = Value( 6)
  val AUTO_OUT                      = Value( 7)
  val AGENT_OUT                     = Value( 8)
  val OUT                           = Value( 9)
  val AGENT_INSIDE                  = Value(10)
  val OFFERED                       = Value(11)
  val CONSULT                       = Value(12)
  val CONSULT_OFFERRED              = Value(13)
  val CONSULT_CONFERENCE            = Value(14)
  val CONFERENCE                    = Value(15)
  val UNMONITORED                   = Value(16)
  val PREVIEW                       = Value(17)
  val RESERVATION                   = Value(18)
  val ASSIST                        = Value(19)
  val EMERGENCY                     = Value(20)
  val SUPERVISOR_MONITOR            = Value(21)
  val SUPERVISOR_WHISPER            = Value(22)
  val SUPERVISOR_BARGEIN            = Value(23)
  val SUPERVISOR_INTERCEPT          = Value(24)
  val TASK_ROUTED_BY_ICM            = Value(25)
  val TASK_ROUTED_BY_APPLICATION    = Value(26)
  val RESERVATION_PREVIEW           = Value(27)
  val RESERVATION_PREVIEW_DIRECT    = Value(28)
  val RESERVATION_PREDICTIVE        = Value(29)
  val RESERVATION_CALLBACK          = Value(30)
  val RESERVATION_PERSONAL_CALLBACK = Value(31)
  val CUSTOMER_PREVIEW              = Value(32)
  val CUSTOMER_PREVIEW_DIRECT       = Value(33)
  val CUSTOMER_PREDICTIVE           = Value(34)
  val CUSTOMER_CALLBACK             = Value(35)
  val CUSTOMER_PERSONAL             = Value(36)
  val CUSTOMER_IVR                  = Value(37)
  val NON_ACD                       = Value(38)
  val PLAY_AGENT_GREETING           = Value(39)
  val RECORD_AGENT_GREETING         = Value(40)
  val VOICE_CALL_BACK               = Value(41)
}

object ConnectionDeviceIDType extends ShortEnum {
  type ConnectionDeviceIDType = Value
  val NONE        = Value(0xffff)
  val STATIC      = Value(0)
  val DYNAMIC     = Value(1)
}

object LineType extends ShortEnum {
  type LineType = Value
  val INBOUND_ACD     = Value( 0)
  val OUTBOUND_ACD    = Value( 1)
  val INSIDE          = Value( 2)
  val UNKNOWN         = Value( 3)
  val SUPERVISOR      = Value( 4)
  val MESSAGE         = Value( 5)
  val HELP            = Value( 6)
  val OUTBOUND        = Value( 7)
  val DID             = Value( 8)
  val SILENT_MONITOR  = Value( 9)
  val NON_ACD_IN      = Value(10)
  val NON_ACD_OUT     = Value(11)
}

object DeviceIDType extends ShortEnum {
  type DeviceIDType = Value
  val NONE                        = Value(0xffff)
  val DEVICE_IDENTIFIER           = Value( 0)
  val TRUNK_IDENTIFIER            = Value(70)
  val TRUNK_GROUP_IDENTIFIER      = Value(71)
  val IP_PHONE_MAC_IDENTIFIER     = Value(72)
  val CTI_PORT                    = Value(73)
  val ROUTE_POINT                 = Value(74)
  val EXTERNAL                    = Value(75)
  val AGENT_DEVICE                = Value(76)
  val QUEUE                       = Value(77)
  val NON_ACD_DEVICE_IDENTIFIER   = Value(78)
  val SHARED_DEVICE_IDENTIFIER    = Value(79)
}

object LocalConnectionState extends ShortEnum {
  type LocalConnectionState = Value
  val NONE        = Value(0xffff)
  val NULL        = Value(0)
  val INITIATE    = Value(1)
  val ALERTING    = Value(2)
  val CONNECT     = Value(3)
  val HOLD        = Value(4)
  val QUEUED      = Value(5)
  val FAIL        = Value(6)
}


object AgentAvailabilityStatus extends IntEnum {
  val NOT_AVAILABLE           = Value(0)
  val ICM_AVAILABLE           = Value(1)
  val APPLICATION_AVAILABLE   = Value(2)
}

object CallDirection extends IntEnum {
  val None                = Value(0)
  val In                  = Value(1)
  val Out                 = Value(2)
  val OtherIn             = Value(3)
  val OtherOut            = Value(4)
  val OutboundReserve     = Value(5)
  val OutboundPreview     = Value(6)
  val OutboundPredictive  = Value(7)
}
object RTPDirection extends ShortEnum {
  val Input           = Value(0)
  val Output          = Value(1)
  val BiDirectional   = Value(2)
}

object RTPType extends ShortEnum {
  val Audio   = Value(0)
  val Video   = Value(1)
  val Data    = Value(2)
}

object SupervisoryAction extends ShortEnum {
  val CLEAR		= Value(0)
  val MONITOR     = Value(1)
  val WHISPER     = Value(2)
  val BARGE_IN    = Value(3)
  val INTERCEPT   = Value(4)
}

object ConfigOperation extends ShortEnum {
  val RestorePermanentConfig  = Value(0)
  val AddAgent                = Value(1)
  val RemoveAgent             = Value(2)
}

object ClientEventReportState extends ShortEnum {
  val Normal      = Value(0)
  val Warning     = Value(1)
  val Error       = Value(2)
}

object DistributionValue extends ShortEnum {
  val CLIENT      = Value(0)
  val SUPERVISOR  = Value(1)
  val TEAM        = Value(2)
  val ALL         = Value(3)
}

object WrapupDataMode extends IntEnum {
  type WrapupDataMode = WrapupDataMode.Value
  val Required                = Value(0)
  val Optional                = Value(1)
  val NotAllowed              = Value(2)
  val RequiredWithWrapupData  = Value(3)
}

object AgentWorkMode extends ShortEnum {
  type AgentWorkMode = Value
  val UNSPECIFIED   = Value(0)
  val AUTO_IN       = Value(1)
  val MANUAL_IN     = Value(2)
}

object ForcedFlag extends ByteEnum {
  val FALSE	                  = Value(0)
  val TRUE                    = Value(1)
  val AgentAuthenticationOnly	= Value(2)
}

object AgentServiceRequestMask {
  val OUTBOUND_SUPPORT: Int     = 0x00000001
}

object CallPlacementType extends ShortEnum {
  type CallPlacementType = Value
  val UNSPECIFIED               = Value(0)
  val LINE_CALL                 = Value(1)
  val OUTBOUND                  = Value(2)
  val OUTBOUND_NO_ACCESS_CODE   = Value(3)
  val DIRECT_POSITION           = Value(4)
  val DIRECT_AGENT              = Value(5)
  val SUPERVISOR_ASSIST         = Value(6)
}

object CallMannerType extends ShortEnum {
  type CallMannerType = Value
  val UNSPECIFIED   = Value(0)
  val POLITE        = Value(1)
  val BELLIGERENT   = Value(2)
  val SEMI_POLITE   = Value(3)
  val RESERVED      = Value(4)
}

object CallOption extends ShortEnum {
  type CallOption = Value
  val UNSPECIFIED                   = Value(0)
  val CALLING_AGENT_ONLINE          = Value(1)
  val CALLING_AGENT_RESERVED        = Value(2)
  val CALLING_AGENT_NOT_RESERVED    = Value(3)
  val CALLING_AGENT_BUZZ_BASE       = Value(4)
  val CALLING_AGENT_BEEP_HSET       = Value(5)
  val SERVIE_CIRCUIT_ON             = Value(6)
}

object FacilityType extends ShortEnum {
  type FacilityType = Value
  val UNSPECIFIED   = Value(0)
  val TRUNK_GROUP   = Value(1)
  val SKILL_GROUP   = Value(2)
}

object AnsweringMachine extends ShortEnum {
  type AnsweringMachine = Value
  val UNSPECIFIED       = Value(0)
  val CONNECT           = Value(1)
  val DISCONNECT        = Value(2)
  val NONE              = Value(3)
  val NONE_NO_MODEM     = Value(4)
  val CONNECT_NO_MODEM  = Value(5)
}

object ConsultType extends ShortEnum {
  type ConsultType = Value
  val UNSPECIFIED   = Value(0)
  val TRANSFER      = Value(1)
  val CONFERENCE    = Value(2)
}

object AllocationState extends ShortEnum {
  type AllocationState = Value
  val CALL_DELIVERED    = Value(0)
  val CALL_ESTABLISHED  = Value(1)
}

object DestinationCountry extends ShortEnum {
  type DestinationCountry = Value
  val UNSPECIFIED       = Value(0)
  val US_AND_CANADA     = Value(1)
}

object AnswerDetectMode extends ShortEnum {
  type AnswerDetectMode = Value
  val UNSPECIFIED       = Value(0)
  val MOICE_THRESHOLD   = Value(1)
  val VOICE_END         = Value(2)
  val VOICE_END_DELAY   = Value(3)
  val VOICE_AND_BEEP    = Value(4)
  val BEEP              = Value(5)
}

object TypeOfDevice extends ShortEnum {
  type TypeOfDevice     = Value
  val STATION           = Value( 0)
  val LINE              = Value( 1)
  val BUTTON            = Value( 2)
  val ACD               = Value( 3)
  val TRUNK             = Value( 4)
  val OPERATOR          = Value( 5)
  val RESERVED_6        = Value( 6)
  val RESERVED_7        = Value( 7)
  val RESERVED_8        = Value( 8)
  val RESERVED_9        = Value( 9)
  val RESERVED_10       = Value(10)
  val RESERVED_11       = Value(11)
  val RESERVED_12       = Value(12)
  val RESERVED_13       = Value(13)
  val RESERVED_14       = Value(14)
  val RESERVED_15       = Value(15)
  val STATION_GROUP     = Value(16)
  val LINE_GROUP        = Value(17)
  val BUTTON_GROUP      = Value(18)
  val ACD_GROUP         = Value(19)
  val TRUNK_GROUP       = Value(20)
  val OPERATOR_GROUP    = Value(21)
  val CTI_PORT_SCCP     = Value(22)
  val CTI_PORT_SIP      = Value(23)
  val OTHER             = Value(255)
}

object AgentGreetingAction extends ShortEnum {
  val StopGreeting    = Value(0)
  val Disable         = Value(1)
  val Enable          = Value(2)
}

object ServerMode extends ShortEnum {
  val Exclusive   = Value(0)
  val RoundRobin  = Value(1)
  val Parallel    = Value(2)
}

object ForwardType extends Enumeration {
  type ForwardType = Value
  val IMMEDIATE     = Value(0)
  val BUSY          = Value(1)
  val NO_ANS        = Value(2)
  val BUSY_INT      = Value(3)
  val BUSY_EXT      = Value(4)
  val NO_ANS_INT    = Value(5)
  val NO_ANS_EXT    = Value(6)
}

object SilentMonitorStatus extends ShortEnum {
  type SilentMonitorStatus = Value
  val NONE          = Value(0)
  val INITIATOR     = Value(1)
  val TARGET        = Value(2)
}

object StatusCode extends IntEnum {
  type StatusCode = Value
  val NO_ERROR                                  = Value( 0)
  val INVALID_VERSION                           = Value( 1)
  val INVALID_MESSAGE_TYPE                      = Value( 2)
  val INVALID_FIELD_TAG                         = Value( 3)
  val SESSION_NOT_OPEN                          = Value( 4)
  val SESSION_ALREADY_OPEN                      = Value( 5)
  val REQUIRED_DATA_MISSING                     = Value( 6)
  val INVALID_PERIPHERAL_ID                     = Value( 7)
  val INVALID_AGENT_DATA                        = Value( 8)
  val AGENT_NOT_LOGGED_ON                       = Value( 9)
  val DEVICE_IN_USE                             = Value(10)
  val NEW_SESSION_OPENED                        = Value(11)
  val FUNCTION_NOT_AVAILABLE                    = Value(12)
  val INVALID_CALLID                            = Value(13)
  val PROTECTED_VARIABLE                        = Value(14)
  val CTI_SERVER_OFFLINE                        = Value(15)
  val TIMEOUT                                   = Value(16)
  val UNSPECIFIED_FAILURE                       = Value(17)
  val INVALID_TIMEOUT                           = Value(18)
  val INVALID_SERVICE_MASK                      = Value(19)
  val INVALID_CALL_MSG_MASK                     = Value(20)
  val INVALID_AGENT_STATE_MASK                  = Value(21)
  val INVALID_RESERVED_FIELD                    = Value(22)
  val INVALID_FIELD_LENGTH                      = Value(23)
  val INVALID_DIGITS                            = Value(24)
  val BAD_MESSAGE_FORMAT                        = Value(25)
  val INVALID_TAG_FOR_MSG_TYPE                  = Value(26)
  val INVALID_DEVICE_ID_TYPE                    = Value(27)
  val INVALID_LCL_CONN_STATE                    = Value(28)
  val INVALID_EVENT_CAUSE                       = Value(29)
  val INVALID_NUM_PARTIES                       = Value(30)
  val INVALID_SYS_EVENT_ID                      = Value(31)
  val INCONSISTENT_AGENT_DATA                   = Value(32)
  val INVALID_CONNECTION_ID_TYPE                = Value(33)
  val INVALID_CALL_TYPE                         = Value(34)
  val NOT_CALL_PARTY                            = Value(35)
  val INVALID_PASSWORD                          = Value(36)
  val CLIENT_DISCONNECTED                       = Value(37)
  val INVALID_OBJECT_STATE                      = Value(38)
  val INVALID_NUM_SKILL_GROUPS                  = Value(39)
  val INVALID_NUM_LINES                         = Value(40)
  val INVALID_LINE_TYPE                         = Value(41)
  val INVALID_ALLOCATION_STATE                  = Value(42)
  val INVALID_ANSWERING_MACHINE                 = Value(43)
  val INVALID_CALL_MANNER_TYPE                  = Value(44)
  val INVALID_CALL_PLACEMENT_TYPE               = Value(45)
  val INVALID_CONSULT_TYPE                      = Value(46)
  val INVALID_FACILITY_TYPE                     = Value(47)
  val INVALID_MSG_TYPE_FOR_VERSION              = Value(48)
  val INVALID_TAG_FOR_VERSION                   = Value(49)
  val INVALID_AGENT_WORK_MODE                   = Value(50)
  val INVALID_CALL_OPTION                       = Value(51)
  val INVALID_DESTINATION_COUNTRY               = Value(52)
  val INVALID_ANSWER_DETECT_MODE                = Value(53)
  val MUTUALLY_EXCLUS_DEVICEID_TYPES            = Value(54)
  val INVALID_MONITORID                         = Value(55)
  val SESSION_MONITOR_ALREADY_EXISTS            = Value(56)
  val SESSION_MONITOR_IS_CLIENTS                = Value(57)
  val INVALID_CALL_CONTROL_MASK                 = Value(58)
  val INVALID_FEATURE_MASK                      = Value(59)
  val INVALID_TRANSFER_CONFERENCE_SETUP_MASK    = Value(60)
  val INVALID_ARRAY_INDEX                       = Value(61)
  val INVALID_CHARACTER                         = Value(62)
  val CLIENT_NOT_FOUND                          = Value(63)
  val SUPERVISOR_NOT_FOUND                      = Value(64)
  val TEAM_NOT_FOUND                            = Value(65)
  val NO_CALL_ACTIVE                            = Value(66)
  val NAMED_VARIABLE_NOT_CONFIGURED             = Value(67)
  val NAMED_ARRAY_NOT_CONFIGURED                = Value(68)
  val INVALID_CALL_VARIABLE_MASK                = Value(69)
  val ELEMENT_NOT_FOUND                         = Value(70)
  val INVALID_DISTRIBUTION_TYPE                 = Value(71)
  val INVALID_SKILL_GROUP                       = Value(72)
  val TOO_MUCH_DATA                             = Value(73)
  val VALUE_TOO_LONG                            = Value(74)
  val SCALAR_FUNCTION_ON_ARRAY                  = Value(75)
  val ARRAY_FUNCTION_ON_SCALAR                  = Value(76)
  val INVALID_NUM_NAMED_VARIABLES               = Value(77)
  val INVALID_NUM_NAMED_ARRAYS                  = Value(78)
  val INVALID_RTP_DIRECTION                     = Value(79)
  val INVALID_RTP_TYPE                          = Value(80)
  val CALLED_PARTY_DISPOSITION                  = Value(81)
  val INVALID_SUPERVISORY_ACTION                = Value(82)
  val AGENT_TEAM_MONITOR_ALREADY_EXISTS         = Value(83)
  val INVALID_SERVICE                           = Value(84)
  val SERVICE_CONFLICT                          = Value(85)
  val SKILL_GROUP_CONFLICT                      = Value(86)
  val INVALID_DEVICE                            = Value(87)
  val INVALID_MR_DOMAIN                         = Value(88)
  val MONITOR_ALREADY_EXISTS                    = Value(89)
  val MONITOR_TERMINATED                        = Value(90)
  val INVALID_TASK_MSG_MASK                     = Value(91)
  val SERVER_NOT_MASTER                         = Value(92)
  val INVALID_CSD                               = Value(93)
  val JTAPI_CCM_PROBLEM                         = Value(94)
  val INVALID_CONFIG_MSG_MASK                   = Value(95)
  val AUTO_CONFIG_RESET                         = Value(96)
  val INVALID_MONITOR_STATUS                    = Value(97)
  val INVALID_REQUEST_TYPE                      = Value(98)
}

object ControlFailureCode extends ShortEnum {
  type ControlFailureCode = Value
  val GENERIC_UNSPECIFIED                               = Value(   0)
  val GENERIC_OPERATION                                 = Value(   1)
  val REQUEST_INCOMPATIBLE_WITH_OBJECT                  = Value(   2)
  val VALUE_OUT_OF_RANGE                                = Value(   3)
  val OBJECT_NOT_KNOWN                                  = Value(   4)
  val INVALID_CALLING_DEVICE                            = Value(   5)
  val INVALID_CALLED_DEVICE                             = Value(   6)
  val INVALID_FORWARDING_DESTINATION                    = Value(   7)
  val PRIVILEGE_VIOLATION_ON_SPECIFIED_DEVICE           = Value(   8)
  val PRIVILEGE_VIOLATION_ON_CALLED_DEVICE              = Value(   9)
  val PRIVILEGE_VIOLATION_ON_CALLING_DEVICE             = Value(  10)
  val INVALID_CSTA_CALL_IDENTIFIER                      = Value(  11)
  val INVALID_CSTA_DEVICE_IDENTIFIER                    = Value(  12)
  val INVALID_CSTA_CONNECTION_IDENTIFIER                = Value(  13)
  val INVALID_DESTINATION                               = Value(  14)
  val INVALID_FEATURE                                   = Value(  15)
  val INVALID_ALLOCATION_STATE                          = Value(  16)
  val INVALID_CROSS_REF_ID                              = Value(  17)
  val INVALID_OBJECT_TYPE                               = Value(  18)
  val SECURITY_VIOLATION                                = Value(  19)
  val RESERVED_20                                       = Value(  20)
  val GENERIC_STATE_INCOMPATIBILITY                     = Value(  21)
  val INVALID_OBJECT_STATE                              = Value(  22)
  val INVALID_CONNECTION_ID_FOR_ACTIVE_CALL             = Value(  23)
  val NO_ACTIVE_CALL                                    = Value(  24)
  val NO_HELD_CALL                                      = Value(  25)
  val NO_CALL_TO_CLEAR                                  = Value(  26)
  val NO_CONNECTION_TO_CLEAR                            = Value(  27)
  val NO_CALL_TO_ANSWER                                 = Value(  28)
  val NO_CALL_TO_COMPLETE                               = Value(  29)
  val RESERVED_30                                       = Value(  30)
  val GENERIC_SYSTEM_RESOURCE_AVAILABILITY              = Value(  31)
  val SERVICE_BUSY                                      = Value(  32)
  val RESOURCE_BUSY                                     = Value(  33)
  val RESOURCE_OUT_OF_SERVICE                           = Value(  34)
  val NETWORK_BUSY                                      = Value(  35)
  val NETWORK_OUT_OF_SERVICE                            = Value(  36)
  val OVERALL_MONITOR_LIMIT_EXCEEDED                    = Value(  37)
  val CONFERENCE_MEMBER_LIMIT_EXCEEDED                  = Value(  38)
  val RESERVED_39                                       = Value(  39)
  val RESERVED_40                                       = Value(  40)
  val GENERIC_SUBSCRIBED_RESOURCE_AVAILABILITY          = Value(  41)
  val OBJECT_MONITOR_LIMIT_EXCEEDED                     = Value(  42)
  val EXTERNAL_TRUNK_LIMIT_EXCEEDED                     = Value(  43)
  val OUTSTANDING_REQUEST_LIMIT_EXCEEDED                = Value(  44)
  val RESERVED_45                                       = Value(  45)
  val RESERVED_46                                       = Value(  46)
  val RESERVED_47                                       = Value(  47)
  val RESERVED_48                                       = Value(  48)
  val RESERVED_49                                       = Value(  49)
  val RESERVED_50                                       = Value(  50)
  val GENERIC_PERFORMANCE_MANAGEMENT                    = Value(  51)
  val PERFORMANCE_LIMIT_EXCEEDED                        = Value(  52)
  val RESERVED_53                                       = Value(  53)
  val RESERVED_54                                       = Value(  54)
  val RESERVED_55                                       = Value(  55)
  val RESERVED_56                                       = Value(  56)
  val RESERVED_57                                       = Value(  57)
  val RESERVED_58                                       = Value(  58)
  val RESERVED_59                                       = Value(  59)
  val RESERVED_60                                       = Value(  60)
  val SEQUENCE_NUMBER_VIOLATED                          = Value(  61)
  val TIME_STAMP_VIOLATED                               = Value(  62)
  val PAC_VIOLATED                                      = Value(  63)
  val SEAL_VIOLATED                                     = Value(  64)
  val RESERVED_65                                       = Value(  65)
  val RESERVED_66                                       = Value(  66)
  val RESERVED_67                                       = Value(  67)
  val RESERVED_68                                       = Value(  68)
  val RESERVED_69                                       = Value(  69)
  val GENERIC_UNSPECIFIED_REJECTION                     = Value(  70)
  val GENERIC_OPERATION_REJECTION                       = Value(  71)
  val DUPLICATE_INVOCATION_REJECTION                    = Value(  72)
  val UNRECOGNIZED_OPERATION_REJECTION                  = Value(  73)
  val MISTYPED_ARGUMENT_REJECTION                       = Value(  74)
  val RESOURCE_LIMITATION_REJECTION                     = Value(  75)
  val ACS_HANDLE_TERMINATION_REJECTION                  = Value(  76)
  val SERVICE_TERMINATION_REJECTION                     = Value(  77)
  val REQUEST_TIMEOUT_REJECTION                         = Value(  78)
  val REQUESTS_ON_DEVICE_EXCEEDED_REJECTION             = Value(  79)
  val INVALID_AGENT_ID_SPECIFIED                        = Value( 256)
  val INVALID_PASSWORD_SPECIFIED                        = Value( 257)
  val INVALID_AGENT_ID_OR_PASSWORD_SPECIFIED            = Value( 258)
  val SPECIFIED_AGENT_ALREADY_SIGNED_ON                 = Value( 259)
  val INVALID_LOGON_DEVICE_SPECIFIED                    = Value( 260)
  val INVALID_ANSWERING_DEVICE_SPECIFIED                = Value( 261)
  val INVALID_SKILL_GROUP_SPECIFIED                     = Value( 262)
  val INVALID_CLASS_OF_SERVICE_SPECIFIED                = Value( 263)
  val INVALID_TEAM_SPECIFIED                            = Value( 264)
  val INVALID_AGENT_WORKMODE                            = Value( 265)
  val INVALID_AGENT_REASON_CODE                         = Value( 266)
  val ADJUNCT_SWITCH_COMM_ERROR                         = Value( 267)
  val AGENT_NOT_PARTY_ON_CALL                           = Value( 268)
  val INTERNAL_PROCESSING_ERROR                         = Value( 269)
  val TAKE_CALL_CONTROL_REJECTION                       = Value( 270)
  val TAKE_DOMAIN_CONTROL_REJECTION                     = Value( 271)
  val REQUESTED_SERVICE_NOT_REGISTERED                  = Value( 272)
  val INVALID_CONSULT_TYPE                              = Value( 273)
  val ANSMAP_OR_ADPARAM_FIELD_NOT_VALID                 = Value( 274)
  val INVALID_CALL_CONTROL_TABLE_SPECIFIED              = Value( 275)
  val INVALID_DIGITS_RNATIMEOUT_AMSDELAY_OR_COUNTRY     = Value( 276)
  val ANSWER_DETECT_PORT_UNAVAILABLE                    = Value( 277)
  val VIRTUAL_AGENT_UNAVAILABLE                         = Value( 278)
  val TAKEBACK_N_XFER_ROUTE_END                         = Value( 279)
  val WRAPUP_DATA_REQUIRED                              = Value( 280)
  val REASON_CODE_REQUIRED                              = Value( 281)
  val INVALID_TRUNK_ID_SPECIFIED                        = Value( 282)
  val SPECIFIED_EXTENSION_ALREADY_IN_USE                = Value( 283)
  val ARBITRARY_CONF_OR_XFER_NOT_SUPPORTED              = Value( 284)
  val NETWORK_TRANSFER_OR_CONSULT                       = Value( 285)
  val NETWORK_TRANSFER_OR_CONSULT_FAILED                = Value( 286)
  val DEVICE_RESTRICTED                                 = Value( 287)
  val LINE_RESTRICTED                                   = Value( 288)
  val AGENT_ACCOUNT_LOCKED_OUT                          = Value( 289)
  val DROP_ANY_PARTY_NOT_ENABLED_CTI                    = Value( 290)
  val MAXIMUM_LINE_LIMIT_EXCEEDED                       = Value( 291)
  val SHARED_LINES_NOT_SUPPORTED                        = Value( 292)
  val EXTENSION_NOT_UNIQUE                              = Value( 293)
  val UNKNOWN_INTERFACE_CTRLR_ID                        = Value(1001)
  val INVALID_INTERFACE_CTRLR_TYPE                      = Value(1002)
  val SOFTWARE_REV_NO_SUPPORTED                         = Value(1003)
  val UNKNOWN_PID                                       = Value(1004)
  val INVALID_TABLE_SPECIFIED                           = Value(1005)
  val PD_SERVICE_INACTIVE                               = Value(1006)
  val UNKNOWN_ROUTING_CLIENT_ID                         = Value(1007)
  val RC_SERVICE_INACTIVATE                             = Value(1008)
  val INVALID_DIALED_NUMBER                             = Value(1009)
  val INVALID_PARAMETER                                 = Value(1010)
  val UNKNOWN_ROUTING_PROBLEM                           = Value(1011)
  val UNSUPPORTED_PD_MESSAGE_REVISION                   = Value(1012)
  val UNSUPPORTED_RC_MESSAGE_REVISION                   = Value(1013)
  val UNSUPPORTED_IC_MESSAGE_REVISION                   = Value(1014)
  val RC_SERVICE_INACTIVATE_PIM                         = Value(1015)
  val AGENT_GREETING_CONTROL_OPERATION_FAILURE          = Value(1016)
}

object DispositionCodeValue extends ShortEnum {
  type DispositionCodeValue = Value
  val Abandoned_in_Network                      = Value( 1)
  val Abandoned_in_Local_Queue                  = Value( 2)
  val Abandoned_Ring                            = Value( 3)
  val Abandoned_Delay                           = Value( 4)
  val Abandoned_Interflow                       = Value( 5)
  val Abandoned_Agent_Terminal                  = Value( 6)
  val Short                                     = Value( 7)
  val Busy                                      = Value( 8)
  val Forced_Busy                               = Value( 9)
  val Disconnect_drop_no_answer                 = Value(10)
  val Disconnect_drop_busy                      = Value(11)
  val Disconnect_drop_reorder                   = Value(12)
  val Disconnect_drop_handled_primary_route     = Value(13)
  val Disconnect_drop_handled_other             = Value(14)
  val Redirected                                = Value(15)
  val Cut_Through                               = Value(16)
  val Intraflow                                 = Value(17)
  val Interflow                                 = Value(18)
  val Ring_No_Answer                            = Value(19)
  val Intercept_reorder                         = Value(20)
  val Intercept_denial                          = Value(21)
  val Time_Out                                  = Value(22)
  val Voice_Energy                              = Value(23)
  val Non_classified_Energy_Detected            = Value(24)
  val No_Cut_Through                            = Value(25)
  val U_Abort                                   = Value(26)
  val Failed_Software                           = Value(27)
  val Blind_Transfer                            = Value(28)
  val Announced_Transfer                        = Value(29)
  val Conferenced                               = Value(30)
  val Duplicate_Transfer                        = Value(31)
  val Unmonitored_Device                        = Value(32)
  val Answering_Machine                         = Value(33)
  val Network_Blind_Transfer                    = Value(34)
  val Task_Abandoned_in_Router                  = Value(35)
  val Task_Abandoned_Before_Offered             = Value(36)
  val Task_Abandoned_While_Offered              = Value(37)
  val Normal_End_Task                           = Value(38)
  val Cant_Obtain_Task_ID                       = Value(39)
  val Agent_Logged_Out_During_Task              = Value(40)
  val Maximum_Task_Lifetime_Exceeded            = Value(41)
  val Application_Path_Went_Down                = Value(42)
  val Unified_CCE_Routing_Complete              = Value(43)
  val Unified_CCE_Routing_Disabled              = Value(44)
  val Application_Invalid_MRD_ID                = Value(45)
  val Application_Invalid_Dialogue_ID           = Value(46)
  val Application_Duplicate_Dialogue_ID         = Value(47)
  val Application_Invalid_Invoke_ID             = Value(48)
  val Application_Invalid_Script_Selector       = Value(49)
  val Application_Terminate_Dialogue            = Value(50)
  val Task_Ended_During_Application_Init        = Value(51)
  val Called_Party_Disconnected                 = Value(52)
}

object EventCause extends ShortEnum {
  type EventCause = Value
  val NONE                            = Value(0xffff)
  val ACTIVE_MONITOR                  = Value(   1)
  val ALTERNATE                       = Value(   2)
  val BUSY                            = Value(   3)
  val CALL_BACK                       = Value(   4)
  val CALL_CANCELLED                  = Value(   5)
  val CALL_FORWARD_ALWAYS             = Value(   6)
  val CALL_FORWARD_BUSY               = Value(   7)
  val CALL_FORWARD_NO_ANSWER          = Value(   8)
  val CALL_FORWARD                    = Value(   9)
  val CALL_NOT_ANSWERED               = Value(  10)
  val CALL_PICKUP                     = Value(  11)
  val CAMP_ON                         = Value(  12)
  val DEST_NOT_OBTAINABLE             = Value(  13)
  val DO_NOT_DISTURB                  = Value(  14)
  val INCOMPATIBLE_DESTINATION        = Value(  15)
  val INVALID_ACCOUNT_CODE            = Value(  16)
  val KEY_CONFERENCE                  = Value(  17)
  val LOCKOUT                         = Value(  18)
  val MAINTENANCE                     = Value(  19)
  val NETWORK_CONGESTION              = Value(  20)
  val NETWORK_NOT_OBTAINABLE          = Value(  21)
  val NEW_CALL                        = Value(  22)
  val NO_AVAILABLE_AGENTS             = Value(  23)
  val OVERRIDE                        = Value(  24)
  val PARK                            = Value(  25)
  val OVERFLOW                        = Value(  26)
  val RECALL                          = Value(  27)
  val REDIRECTED                      = Value(  28)
  val REORDER_TONE                    = Value(  29)
  val RESOURCES_NOT_AVAILABLE         = Value(  30)
  val SILENT_MONITOR                  = Value(  31)
  val TRANSFER                        = Value(  32)
  val TRUNKS_BUSY                     = Value(  33)
  val VOICE_UNIT_INITIATOR            = Value(  34)
  val TIME_OUT                        = Value(  35)
  val NEW_CALL_INTERFLOW              = Value(  36)
  val SIMULATION_INIT_REQUEST         = Value(  37)
  val SIMULATION_RESET_REQUEST        = Value(  38)
  val CTI_LINK_DOWN                   = Value(  39)
  val PERIPHERAL_RESET_REQUEST        = Value(  40)
  val MD110_CONFERENCE_TRANSFER       = Value(  41)
  val REMAINS_IN_Q                    = Value(  42)
  val SUPERVISOR_ASSIST               = Value(  43)
  val EMERGENCY_CALL                  = Value(  44)
  val SUPERVISOR_CLEAR                = Value(  45)
  val SUPERVISOR_MONITOR              = Value(  46)
  val SUPERVISOR_WHISPER              = Value(  47)
  val SUPERVISOR_BARGE_IN             = Value(  48)
  val SUPERVISOR_INTERCEPT            = Value(  49)
  val CALL_PARTY_UPDATE_IND           = Value(  50)
  val CONSULT                         = Value(  51)
  val NIC_CALL_CLEAR                  = Value(  52)
  val ABAND_NETWORK                   = Value(1001)
  val ABAND_LOCAL_QUEUE               = Value(1002)
  val ABAND_RING                      = Value(1003)
  val ABAND_DELAY                     = Value(1004)
  val ABAND_INTERFLOW                 = Value(1005)
  val ABAND_AGENT_TERMINAL            = Value(1006)
  val SHORT                           = Value(1007)
  val CECX_BUSY                       = Value(1008)
  val FORCED_BUSY                     = Value(1009)
  val DROP_NO_ANSWER                  = Value(1010)
  val DROP_BUSY                       = Value(1011)
  val DROP_REORDER                    = Value(1012)
  val DROP_HANDLED_PRIMARY_ROUTE      = Value(1013)
  val DROP_HANDLED_OTHER              = Value(1014)
  val CECX_REDIRECTED                 = Value(1015)
  val CUT_THROUGH                     = Value(1016)
  val INTRAFLOW                       = Value(1017)
  val INTERFLOW                       = Value(1018)
  val RING_NO_ANSWER                  = Value(1019)
  val INTERCEPT_REORDER               = Value(1020)
  val INTERCEPT_DENIAL                = Value(1021)
  val CECX_TIME_OUT                   = Value(1022)
  val VOICE_ENERGY                    = Value(1023)
  val NONCLASSIFIED_ENERGY_DETECT     = Value(1024)
  val NO_CUT_THROUGH                  = Value(1025)
  val UABORT                          = Value(1026)
  val FAILED_SOFTWARE                 = Value(1027)
  val BLIND_TRANSFER                  = Value(1028)
  val ANNOUNCED_TRANSFER              = Value(1029)
  val CONFERENCED                     = Value(1030)
  val DUPLICATE_TRANSFER              = Value(1031)
  val UNMONITORED_DEVICE              = Value(1032)
  val ANSWERING_MACHINE               = Value(1033)
  val NETWORK_BLIND_TRANSFER          = Value(1034)
  val TASK_ABANDONED_IN_ROUTER        = Value(1035)
  val TASK_ABANDONED_BEFORE_OFFERED   = Value(1036)
  val TASK_ABANDONED_WHILE_OFFERED    = Value(1037)
  val NORMAL_END_TASK                 = Value(1038)
  val CANT_OBTAIN_TASK_ID             = Value(1039)
  val AGENT_LOGGED_OUT_DURING_TASK    = Value(1040)
  val MAX_TASK_LIFETIME_EXCEEDED      = Value(1041)
  val APPLICATION_PATH_WENT_DOWN      = Value(1042)
  val ICM_ROUTING_COMPLETE            = Value(1043)
  val ICM_ROUTING_DISABLED            = Value(1044)
  val APPL_INVALID_MRD_ID             = Value(1045)
  val APPL_INVALID_DIALOGUE_ID        = Value(1046)
  val APPL_DUPLICATE_DIALOGUE_ID      = Value(1047)
  val APPL_INVALID_INVOKE_ID          = Value(1048)
  val APPL_INVALID_SCRIPT_SELECTOR    = Value(1049)
  val APPL_TERMINATE_DIALOGUE         = Value(1050)
  val TASK_ENDED_DURING_APP_INIT      = Value(1051)
  val CALLED_PARTY_DISCONNECTED       = Value(1052)
  val PARTIAL_CALL                    = Value(1053)
  val DROP_NETWORK_CONSULT            = Value(1054)
  val NETWORK_CONSULT_TRANSFER        = Value(1055)
  val NETWORK_CONFERENCE              = Value(1056)
  val ABAND_NETWORK_CONSULT           = Value(1057)
}

object EventCode extends ShortEnum {
  val GreetingHasStarted              = Value(0)
  val GreetingHasEndedWithSuccess     = Value(1)
  val FailedToPlayGreeting            = Value(2)
}

object SystemEventID extends IntEnum {
  type SystemEventID = Value
  val CENTRAL_CONTROLLER_ONLINE       = Value( 1)
  val CENTRAL_CONTROLLER_OFFLINE      = Value( 2)
  val PERIPHERAL_ONLINE               = Value( 3)
  val PERIPHERAL_OFFLINE              = Value( 4)
  val TEXT_FYI                        = Value( 5)
  val PERIPHERAL_GATEWAY_OFFLINE      = Value( 6)
  val CTI_SERVER_OFFLINE              = Value( 7)
  val CTI_SERVER_ONLINE               = Value( 8)
  val HALF_HOUR_CHANGE                = Value( 9)
  val INSTRUMENT_OUT_OF_SERVICE       = Value(10)
  val INSTRUMENT_BACK_IN_SERVICE      = Value(11)
}

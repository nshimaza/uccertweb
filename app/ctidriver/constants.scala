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
  val ACD_IN                      = Value( 1)
  val PREROUTE_ACD_IN             = Value( 2)
  val PREROUTE_DIRECT_AGENT       = Value( 3)
  val TRANSFER_IN                 = Value( 4)
  val OVERFLOW_IN                 = Value( 5)
  val OTHER_IN                    = Value( 6)
  val AUTO_OUT                    = Value( 7)
  val AGENT_OUT                   = Value( 8)
  val OUT                         = Value( 9)
  val AGENT_INSIDE                = Value(10)
  val OFFERED                     = Value(11)
  val CONSULT                     = Value(12)
  val CONSULT_OFFERRED            = Value(13)
  val CONSULT_CONFERENCE          = Value(14)
  val CONFERENCE                  = Value(15)
  val UNMONITORED                 = Value(16)
  val PREVIEW                     = Value(17)
  val RESERVATION                 = Value(18)
  val ASSIST                      = Value(19)
  val EMERGENCY                   = Value(20)
  val SUPERVISOR_MONITOR          = Value(21)
  val SUPERVISOR_WHISPER          = Value(22)
  val SUPERVISOR_BARGEIN          = Value(23)
  val SUPERVISOR_INTERCEPT        = Value(24)
  val TASK_ROUTED_BY_ICM          = Value(25)
  val TASK_ROUTED_BY_APPLICATION  = Value(26)
  val NON_ACD                     = Value(27)
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

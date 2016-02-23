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

/**
 *
 */
object CtiServiceMask {
  val DEBUG: Int                        = 31
  val CLIENT_EVENTS: Int                =  0
  val CALL_DATA_UPDATE: Int             =  1
  val CLIENT_CONTROL: Int               =  2
  val CONNECTION_MONITOR: Int           =  3
  val ALL_EVENTS: Int                   =  4
  val PERIPHERAL_MONITOR: Int           =  5
  val CLIENT_MONITOR: Int               =  6
  val SUPERVISOR: Int                   =  7
  val SERVER: Int                       =  8
  val UNUSED_0x00000200: Int            =  9
  val AGENT_REPORTING: Int              = 10
  val ALL_TASK_EVENTS: Int              = 11
  val TASK_MONITOR: Int                 = 12
  val CTI_AGENT_STATE_CONTROL_ONLY: Int = 13
  val UNUSED_0x00004000: Int            = 14
  val CTI_DEVICE_STATE_CONTROL: Int     = 15
  val UPDATE_EVENTS: Int                = 19
  val IGNORE_DUPLICATE_AGENT_EVENT: Int = 20
  val IGNORE_CONF: Int                  = 21
  val ACD_LINE_ONLY: Int                = 22
}

object CallEventMessageMask {
  val CALL_DELIVERED: Int           =  0
  val CALL_QUEUED: Int              =  1
  val CALL_ESTABLISHED: Int         =  2
  val CALL_HELD: Int                =  3
  val CALL_RETRIEVED: Int           =  4
  val CALL_CLEARED: Int             =  5
  val CALL_CONNECTION_CLEARED: Int  =  6
  val CALL_ORIGINATED: Int          =  7
  val CALL_CONFERENCED: Int         =  8
  val CALL_TRANSFERRED: Int         =  9
  val CALL_DIVERTED: Int            = 10
  val CALL_SERVICE_INITIATED: Int   = 11
  val CALL_TRANSLATION_ROUTE: Int   = 12
  val BEGIN_CALL: Int               = 13
  val END_CALL: Int                 = 14
  val CALL_DATA_UPDATE: Int         = 15
  val CALL_FAILED: Int              = 16
  val CALL_REACHED_NETWORK: Int     = 17
  val CALL_DEQUEUED: Int            = 18
  val AGENT_PRE_CALL: Int           = 19
  val AGENT_PRE_CALL_ABORT: Int     = 20
  val RTP_STARTED: Int              = 21
  val RTP_STOPPED: Int              = 22
  val AGENT_TEAM_CONFIG             = 23
  val AGENT_LEGACY_PRE_CALL         = 24
  val CALL_ATTRIBUTE_CHANGE         = 25
  val CALL_TERMINATION              = 26
  val CALL_AGENT_GREETING           = 27
}

object AgentStateMask {
  val AGENT_LOGIN: Int          = 0
  val AGENT_LOGOUT: Int         = 1
  val AGENT_NOT_READY: Int      = 2
  val AGENT_AVAILABLE: Int      = 3
  val AGENT_TALKING: Int        = 4
  val AGENT_WORK_NOT_READY: Int	= 5
  val AGENT_WORK_READY: Int     = 6
  val AGENT_BUSY_OTHER: Int     = 7
  val AGENT_RESERVED: Int       = 8
  val AGENT_HOLD: Int           = 9
}

object DeskSettingMask {
  val AVAIL_AFTER_INCOMING: Int               =  0
  val AVAIL_AFTER_OUTGOING: Int               =  1
  val AUTO_ANSWER_ENABLED: Int                =  2
  val IDLE_REASON_REQUIRED: Int               =  3
  val LOGOUT_REASON_REQUIRED: Int             =  4
  val SUPERVISOR_CALLS_ALLOWED: Int           =  5
  val AGENT_TO_AGENT_CALLS_ALLOWED: Int       =  6
  val OUTBOUND_ACCESS_INTERNATIONAL: Int      =  7
  val OUTBOUND_ACCESS_PUBLIC_NET: Int         =  8
  val OUTBOUND_ACCESS_PRIVATE_NET: Int        =  9
  val OUTBOUND_ACCESS_OPERATOR_ASSISTED: Int  = 10
  val OUTBOUND_ACCESS_PBX: Int                = 11
  val NON_ACD_CALLS_ALLOWED: Int              = 12
  val AGENT_CAN_SELECT_GROUP: Int             = 13
}

object CallVariableMask {
  val CALL_VER_1: Int   = 0
  val CALL_VER_2: Int   = 1
  val CALL_VER_3: Int   = 2
  val CALL_VER_4: Int   = 3
  val CALL_VER_5: Int   = 4
  val CALL_VER_6: Int   = 5
  val CALL_VER_7: Int   = 6
  val CALL_VER_8: Int   = 7
  val CALL_VER_9: Int   = 8
  val CALL_VER_10: Int  = 9
}

object TransferConferenceSetupMask {
  val CONF_SETUP_CONSULT_SPECIFIC: Int  = 0
  val CONF_SETUP_CONSULT_ANY: Int       = 1
  val CONF_SETUP_CONN_HELD: Int         = 2
  val CONF_SETUP_ANY_TWO_CALLS: Int     = 3
  val CONF_SETUP_SINGLE_ACD_CALL: Int   = 4
  val TRANS_SETUP_SINGLE_ACD_CALL: Int  = 5
  val CONF_SETUP_ANY_SINGLE_CALL: Int   = 6
  val TRANS_SETUP_ANY_SINGLE_CALL: Int  = 7
}

object CallControlMask {
  val QUERY_AGENT_STATE: Int      =  0
  val SET_AGENT_STATE: Int        =  1
  val ALTERNATE_CALL: Int         =  2
  val ANSWER_CALL: Int            =  3
  val CLEAR_CALL: Int             =  4
  val CLEAR_CONNECTION: Int       =  5
  val CONFERENCE_CALL: Int        =  6
  val CONSULTATION_CALL: Int      =  7
  val DEFLECT_CALL: Int           =  8
  val HOLD_CALL: Int              =  9
  val MAKE_CALL: Int              = 10
  val MAKE_PREDICTIVE_CALL: Int   = 11
  val RECONNECT_CALL: Int         = 12
  val RETRIEVE_CALL: Int          = 13
  val TRANSFER_CALL: Int          = 14
  val QUERY_DEVICE_INFO: Int      = 15
  val SNAPSHOT_CALL: Int          = 16
  val SNAPSHOT_DEVICE: Int        = 17
  val SEND_DTMF_SIGNAL: Int       = 18
}

object OtherFeatureMask {
  val POST_ROUTE: Int             = 0
  val UNIQUE_CONSULT_CALLID: Int  = 1
}

object ClassOfDevice {
  val OTHER: Int    = 4
  val IMAGE: Int    = 5
  val DATA: Int     = 6
  val VOICE: Int    = 7
}

object AgentFlags {
  val PrimarySupervisor: Int  = 0
  val TemporaryAgent: Int     = 1
  val Supervisor: Int         = 2
}

object PGStatusCode {
  val OPC_DOWN: Int               = 0
  val CC_DOWN: Int                = 1
  val PERIPHERAL_OFFLINE: Int     = 2
  val CTI_SERVER_OFFLINE: Int     = 3
  val LIMITED_FUNCTION: Int       = 4
}
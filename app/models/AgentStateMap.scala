package models

import akka.agent.Agent
import ctidriver.AgentState._
import ctidriver.{ AgentState, Message }
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by nshimaza on 2016/01/31.
  */
case class AgentStateMap(extension_range: Range) {
  val agent_map: Map[Int, Agent[(AgentState, Int)]] = extension_range.map(n => (n, Agent((LOGOUT, 0)))).toMap

  def get(extension: Int): Option[(AgentState, Int)] = for (agent <- agent_map.get(extension)) yield agent.get
  def future(extension: Int) = for (agent <- agent_map.get(extension)) yield agent.future

  def receive(msg: Message) = {
    import ctidriver.Tag._
    import ctidriver.MessageType._

    for (
      msgType <- msg.findEnum[MessageType](MessageTypeTag).find(_ == AGENT_STATE_EVENT);
      ext <- msg.findStrInt(AGENT_EXTENSION);
      agent <- agent_map.get(ext);
      state <- msg.findEnum[AgentState.AgentState](AgentStateTag);
      reason <- msg.findT[Short](EventReasonCode)
    ) yield {
      agent send (state, reason)
    }
  }
}

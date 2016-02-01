package models

import akka.actor.{ Actor, ActorRef, Props }
import akka.agent.Agent
import ctidriver.AgentState
import ctidriver.AgentState.AgentState
import ctidriver.{ AgentState, Message }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * Created by nshimaza on 2016/01/31.
  */
case class AgentStateMap(extension_range: Range) {
  val agent_map: Map[Int, Agent[(AgentState.Value, Int)]] = extension_range.map(n => (n, Agent((AgentState.LOGOUT, 0)))).toMap

  def get(extension: Int) = {
    agent_map.get(extension) match {
      case Some(agent) => Some(agent.get)
      case None => None
    }
  }

  def future(extension: Int) = {
    agent_map.get(extension) match {
      case Some(agent) => Some(agent.future)
      case None => None
    }
  }

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

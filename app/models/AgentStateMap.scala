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

package models

import javax.inject._

import akka.agent.Agent
import ctidriver._
import ctidriver.AgentState._
import com.google.inject.{ImplementedBy, AbstractModule}
import com.google.inject.assistedinject.{Assisted, FactoryModuleBuilder}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by nshimaza on 2016/02/11.
  */
trait AgentStateMap {
  def get(extension: Int): Option[(AgentState, Int)]

  def future(extension: Int): Option[Future[(AgentState, Int)]]

  def receive(msg: Message): Unit
}

trait AgentStateMapFactory {
  def apply(extensionRange: Range): AgentStateMap
}

/*
trait UsesAgentStateMap {
  def agentStateMapFactory: AgentStateMapFactory
}

trait MixInAgentStateMapImplFactory {
  def agentStateMapFactory = new AgentStateMapImplFactory
}

class AgentStateMapImplFactory extends AgentStateMapFactory {
  def apply(extensionRange: Range) = new AgentStateMapImpl(extensionRange)
}
*/

class AgentStateMapImpl @Inject()(@Assisted extensionRange: Range) extends AgentStateMap {
  val stateMap: Map[Int, Agent[(AgentState, Int)]] = extensionRange.map(n => (n, Agent((LOGOUT, 0)))).toMap

  def get(ext: Int) = for (agent <- stateMap.get(ext)) yield agent.get

  def future(ext: Int) = for (agent <- stateMap.get(ext)) yield agent.future

  def receive(msg: Message) = {
    import ctidriver.Tag._
    import ctidriver.MessageType._

    for (
      msgType <- msg.findEnum[MessageType](MessageTypeTag).find(_ == AGENT_STATE_EVENT);
      ext <- msg.findStrInt(AGENT_EXTENSION);
      agent <- stateMap.get(ext);
      state <- msg.findEnum[AgentState.AgentState](AgentStateTag);
      reason <- msg.findT[Short](EventReasonCode)
    ) {
      agent send (state, reason)
    }

    /*
    import ctidriver.Tag._
    import ctidriver.MessageType._

    for (msgType <- msg.findEnum[MessageType](MessageTypeTag).find(_ == AGENT_STATE_EVENT);
         ext <- msg.findStrInt(AGENT_EXTENSION);
         state <- msg.findEnum[AgentState.AgentState](AgentStateTag);
         reason <- msg.findT[Short](EventReasonCode)) {
      state_map.get(ext) match {
        case None => state_map = state_map + (ext -> Agent((state, reason.toInt)))
        case Some(agent) => agent send (state, reason)
      }
    }
    */
  }
}

class AgentStateMapModule extends AbstractModule {
  def configure() = {
    install(new FactoryModuleBuilder()
      .implement(classOf[AgentStateMap], classOf[AgentStateMapImpl])
      .build(classOf[AgentStateMapFactory]))

  }
}
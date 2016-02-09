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

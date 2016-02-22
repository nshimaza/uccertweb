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

import akka.actor.{Props, Actor, ActorSystem}
import akka.testkit.{ TestActorRef, TestActors, TestKit, TestProbe }
import ctidriver.{CallDirection, AgentAvailabilityStatus, PeripheralType, AgentState}
import ctidriver.MessageType._
import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ WordSpecLike, MustMatchers, BeforeAndAfterAll }

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Tests for AgentStateMap.
  */
/*
@RunWith(classOf[JUnitRunner])
class AgentStateMapOldSpec(_system: ActorSystem) extends TestKit(_system)
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("AgentStateMapSpec"))

  "AgentStateMap" must {
    "be constructed with extension range" in {
      val pool = AgentStateMapOld(1000 to 1999)
    }

    "have get(ext: Int): Option method to retrieve agent state of given extension" in {
      val pool = AgentStateMapOld(1000 to 1999)
      pool.get(1000)
    }

    "return Some for existing estension" in {
      val pool = AgentStateMapOld(1000 to 1999)
      pool.get(1000).getClass must be(classOf[Some[(AgentState.Value, String)]])
    }

    "return None for unknown extension" in {
      val pool = AgentStateMapOld(1000 to 1999)
      pool.get(2000) must be(None)
    }

    "must be initialized with LOGOUT with reason code zero for all configured extensions" in {
      val tested_range = 1000 to 1999
      val pool = AgentStateMapOld(tested_range)
      tested_range.foreach(i => pool.get(i) must be(Some(AgentState.LOGOUT, 0)))
    }

    "must receive AGENT_STATE_EVENT message" in {
      val pool = AgentStateMapOld(3000 to 3030)
      val msg =List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304),
        (PeripheralID, 0x02030405), (SessionID, 0x03040506),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
        (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
        (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809:Short),
        (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a:Short), (MRDID, 0x090a0b0c),
        (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
        (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706:Short),
        (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
        (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
        (DIRECTION, Some(CallDirection.In)),
        (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a:Short),
        (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))
      pool.receive(msg)
    }

    "must reflect last received AGENT_STATE_EVENT" in {
      val pool = AgentStateMapOld(3000 to 3030)
      val msg =List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304),
        (PeripheralID, 0x02030405), (SessionID, 0x03040506),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
        (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
        (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809:Short),
        (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a:Short), (MRDID, 0x090a0b0c),
        (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
        (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706:Short),
        (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
        (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
        (DIRECTION, Some(CallDirection.In)),
        (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a:Short),
        (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))
      pool.receive(msg)
      Await.ready(pool.future(3001).get, 3.second)
      pool.get(3001) must be (Some((AgentState.HOLD, 0x090a)))
    }
  }
}
*/
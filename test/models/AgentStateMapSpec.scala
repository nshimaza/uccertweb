package models

import javax.inject._

import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.{AbstractModule, Guice, Injector}
import ctidriver.{AgentAvailabilityStatus, AgentState, CallDirection, PeripheralType}
import ctidriver.MessageType._
import ctidriver.Tag._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{MustMatchers, WordSpec}

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Mock @Inject()(agentStateMapFactory: AgentStateMapFactory) {
  val testedRange = 1000 to 1999
  val agentStateMap = agentStateMapFactory(testedRange)
}

/**
  * Tests for AgentStateMap.
  */
@RunWith(classOf[JUnitRunner])
class AgentStateMapSpec extends WordSpec with MustMatchers {

  "AgentStateMapImpl" must {
    "be constructed with extension range" in {
      val pool = new AgentStateMapImpl(1000 to 1999)
    }

    "have apply(ext: Int): Option method to retrieve agent state of given extension" in {
      val pool = new AgentStateMapImpl(1000 to 1999)
      pool(1000)
    }

    "return Some for existing estension" in {
      val pool = new AgentStateMapImpl(1000 to 1999)
      pool(1000).getClass mustBe classOf[Some[(AgentState.Value, String)]]
    }

    "return None for unknown extension" in {
      val pool = new AgentStateMapImpl(1000 to 1999)
      pool(2000) mustBe None
    }

    "must be initialized with LOGOUT with reason code zero for all configured extensions" in {
      val tested_range = 1000 to 1999
      val pool = new AgentStateMapImpl(tested_range)
      tested_range.foreach { pool(_) mustBe Some(AgentState.LOGOUT, 0) }
    }

    "must receive AGENT_STATE_EVENT message" in {
      val pool = new AgentStateMapImpl(3000 to 3030)
      val msg = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304),
        (PeripheralID, 0x02030405), (SessionID, 0x03040506),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
        (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
        (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809: Short),
        (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a: Short), (MRDID, 0x090a0b0c),
        (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
        (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706: Short),
        (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
        (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
        (DIRECTION, Some(CallDirection.In)),
        (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a: Short),
        (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))
      pool.receive(msg)
    }

    "must reflect last received AGENT_STATE_EVENT" in {
      val pool = new AgentStateMapImpl(3000 to 3030)
      val msg = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304),
        (PeripheralID, 0x02030405), (SessionID, 0x03040506),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
        (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
        (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809: Short),
        (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a: Short), (MRDID, 0x090a0b0c),
        (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
        (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706: Short),
        (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
        (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
        (DIRECTION, Some(CallDirection.In)),
        (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a: Short),
        (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))
      pool.receive(msg)
      Await.ready(pool.future(3001).get, 3.second)
      pool(3001) mustBe Some((AgentState.HOLD, 0x090a))
    }
  }

  "AgentStateMap" must {
    "be instanciated via dependency injector" in {
      val mock: Mock = Guice.createInjector(new AgentStateMapSpecModule).getInstance(classOf[Mock])
    }

    "have get(ext: Int): Option method to retrieve agent state of given extension" in {
      val mock: Mock = Guice.createInjector(new AgentStateMapSpecModule).getInstance(classOf[Mock])
      mock.agentStateMap(1000)
    }

    "return Some for existing estension" in {
      val mock: Mock = Guice.createInjector(new AgentStateMapSpecModule).getInstance(classOf[Mock])
      mock.agentStateMap(1000).getClass mustBe classOf[Some[(AgentState.Value, String)]]
    }

    "return None for unknown extension" in {
      val mock: Mock = Guice.createInjector(new AgentStateMapSpecModule).getInstance(classOf[Mock])
      mock.agentStateMap(2000) mustBe None
    }

    "must be initialized with LOGOUT with reason code zero for all configured extensions" in {
      val mock: Mock = Guice.createInjector(new AgentStateMapSpecModule).getInstance(classOf[Mock])
      mock.testedRange.foreach { mock.agentStateMap(_) mustBe Some(AgentState.LOGOUT, 0) }
    }

    "must receive AGENT_STATE_EVENT message" in {
      val mock: Mock = Guice.createInjector(new AgentStateMapSpecModule).getInstance(classOf[Mock])
      val msg = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304),
        (PeripheralID, 0x02030405), (SessionID, 0x03040506),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
        (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
        (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809: Short),
        (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a: Short), (MRDID, 0x090a0b0c),
        (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
        (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706: Short),
        (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_ID, "1001"), (AGENT_EXTENSION, "3001"), (AGENT_INSTRUMENT, "3001"),
        (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
        (DIRECTION, Some(CallDirection.In)),
        (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a: Short),
        (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))
      mock.agentStateMap.receive(msg)
    }

    "must reflect last received AGENT_STATE_EVENT" in {
      val mock: Mock = Guice.createInjector(new AgentStateMapSpecModule).getInstance(classOf[Mock])
      val msg = List((MessageTypeTag, Some(AGENT_STATE_EVENT)), (MonitorID, 0x01020304),
        (PeripheralID, 0x02030405), (SessionID, 0x03040506),
        (PeripheralTypeTag, Some(PeripheralType.ENTERPRISE_AGENT)),
        (SkillGroupState, Some(AgentState.BUSY_OTHER)), (StateDuration, 0x04050607),
        (SkillGroupNumber, 0x06070809), (SkillGroupID, 0x0708090a), (SkillGroupPriority, 0x0809: Short),
        (AgentStateTag, Some(AgentState.HOLD)), (EventReasonCode, 0x090a: Short), (MRDID, 0x090a0b0c),
        (NumTasks, 0x0a0b0c0d), (AgentMode, false), (MaxTaskLimit, 0x09080706), (ICMAgentID, 0x08070605),
        (AgentAvailabilityStatusTag, Some(AgentAvailabilityStatus.ICM_AVAILABLE)), (NumFltSkillGroups, 0x0706: Short),
        (CLIENT_SIGNATURE, "ClientSignature"),
        (AGENT_ID, "3001"), (AGENT_EXTENSION, "1001"), (AGENT_INSTRUMENT, "2001"),
        (DURATION, 0x06050403), (NEXT_AGENT_STATE, Some(AgentState.TALKING)),
        (DIRECTION, Some(CallDirection.In)),
        (SKILL_GROUP_NUMBER, 0x0708090a), (SKILL_GROUP_ID, 0x08090a0b), (SKILL_GROUP_PRIORITY, 0x090a: Short),
        (SKILL_GROUP_STATE, Some(AgentState.BUSY_OTHER)))
      mock.agentStateMap.receive(msg)
      Await.ready(mock.agentStateMap.future(1001).get, 3.second)
      mock.agentStateMap(1001) mustBe Some((AgentState.HOLD, 0x090a))
    }
  }
}

class AgentStateMapSpecModule extends AbstractModule {
  def configure() = {
    install(new FactoryModuleBuilder()
      .implement(classOf[AgentStateMap], classOf[AgentStateMapImpl])
      .build(classOf[AgentStateMapFactory]))
  }
}
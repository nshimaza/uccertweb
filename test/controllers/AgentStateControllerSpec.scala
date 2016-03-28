package controllers

import java.net.InetSocketAddress
import javax.inject._

import akka.actor.{Actor, Props}
import com.google.inject.{AbstractModule, Guice}
import com.google.inject.assistedinject.{Assisted, FactoryModuleBuilder}
import ctidriver.AgentState._
import ctidriver.{AgentState => _, _}
import models.{AgentStateMap, AgentStateMapFactory}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatestplus.play._
import play.api.Configuration
import play.api.mvc.{Result, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.{ExecutionContext, Future}


/**
  * Created by nshimaza on 2016/02/21.
  */
@RunWith(classOf[JUnitRunner])
class AgentStateControllerSpec extends PlaySpec with Results {
  val extensionRange = 100 to 200
  val agentStateMapMockFactory = new AgentStateMapMockFactory(new AgentStateMapMock(extensionRange))





  "AgentStateController" must {
    "return 404 on unconfigured extension" in {
      val injector = Guice.createInjector(new RTWebSpecModule(extensionRange, agentStateMapMockFactory))
      val controller = injector.getInstance(classOf[AgentStateController])

      status(controller.extension(extensionRange.start - 1).apply(FakeRequest())) mustBe NOT_FOUND
      status(controller.extension(extensionRange.end + 1).apply(FakeRequest())) mustBe NOT_FOUND
    }

    "return LOGOUT as initial state" in {
      val controller = Guice.createInjector(new RTWebSpecModule(extensionRange, agentStateMapMockFactory))
        .getInstance(classOf[AgentStateController])

      for (i <- extensionRange) {
        val jasonResult = contentAsJson(controller.extension(i).apply(FakeRequest()))
        (jasonResult \ "state").as[String] mustBe "LOGOUT"
      }
    }

    "return current agent state" in {
    }
  }

}

class AgentStateMapMock @Inject()(@Assisted extensionRange: Range) extends AgentStateMap {

  var stateMap: Map[Int, (AgentState, Int)] = extensionRange.map(n => (n, (LOGOUT, 0))).toMap

  def apply(extension: Int): Option[(AgentState, Int)] = Some(stateMap(extension))

  def future(extension: Int): Option[Future[(AgentState, Int)]] = None

  def receive(msg: Message): Unit = Unit

  def setState(extension: Int, state: AgentState, reason: Int) = {
    stateMap = stateMap + (extension -> (state, reason))
  }
}

class AgentStateMapMockFactory(agentStateMapMock: AgentStateMapMock) extends AgentStateMapFactory {
  def apply(extensionRange: Range)(implicit context: ExecutionContext) = {
    agentStateMapMock
  }
}

class RTWebSpecModule(extensionRange: Range,
                      agentStateMapFactory: AgentStateMapFactory)
  extends AbstractModule {
  def configure() = {
    bind(classOf[Configuration]).toInstance(Configuration(
      "uccertweb.ext.low" -> extensionRange.start,
      "uccertweb.ext.high" -> extensionRange.end
    ))
    bind(classOf[AgentStateMapFactory]).toInstance(agentStateMapFactory)
//    install(new FactoryModuleBuilder()
//      .implement(classOf[models.AgentStateMap], classOf[AgentStateMapMock])
//      .build(classOf[models.AgentStateMapFactory]))

  }
}

class HAActorMock(filter: MessageFilter) extends Actor {
  def receive = {
    case _ =>
  }
}
class HAActorMockPropsFactory extends HAActorPropsFactory {
  def apply(serverA: InetSocketAddress, serverB: InetSocketAddress, filter: MessageFilter)
           (implicit executor: ExecutionContext) = {
    Props(classOf[HAActorMock], filter)
  }
}


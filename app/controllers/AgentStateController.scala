package controllers

import javax.inject._

import ctidriver.HAActorPropsFactory
import models.{AgentStateMap, AgentStateMapFactory}
import play.api.{Configuration, Play}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._

/**
  * Created by nshimaza on 2016/02/21.
  */
//class AgentStateController @Inject()(agentStateMapFactory: AgentStateMapFactory,
//                                     hAActorPropsFactory: HAActorPropsFactory)
@Singleton
class AgentStateController @Inject()(configuration: Configuration,
                                     agentStateMapFactory: AgentStateMapFactory)
  extends Controller {

  val extLow = configuration.getInt("uccertweb.ext.low").get
  val extHigh = configuration.getInt("uccertweb.ext.high").get
  val agentMap = agentStateMapFactory(extLow to extHigh)

/*

  val handler: ctidriver.Message => Unit = { m => agentMap.receive(m) }
  val entry = ctidriver.MessageFilterEntry(Set(ctidriver.MessageType.AGENT_STATE_EVENT), handler)
  val filter = ctidriver.MessageFilter(Traversable(entry))
*/


  def extension(ext: Int) = Action {
    if (extLow <= ext && ext <= extHigh) {
      Ok(Json.obj("state" -> "LOGOUT", "reason" -> "0"))
    } else {
      NotFound
    }

    //    Ok("hello, world")
/*
    try {
      val (state, reason) = agentMap.apply(ext).get
      val json = Json.obj("state" -> state.toString, "reason" -> reason)
      Ok(json.toString)
    } catch {
      case e:  NoSuchElementException => NotFound("404 No such extension")
    }
*/
  }
}

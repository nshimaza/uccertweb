package controllers

import javax.inject._

import models.{AgentStateMap, AgentStateMapFactory}
//import models.{MixInAgentStateMapImplFactory, AgentStateMap, AgentStateMapFactory}
import play.api.Play
import play.api.libs.json.Json
import play.api.mvc._

/**
  * Created by nshimaza on 2016/02/21.
  */
@Singleton
class AgentStateController @Inject()(agentStateMapFactory: AgentStateMapFactory) extends Controller {
//class AgentStateController
//  extends Controller
//    with MixInAgentStateMapImplFactory {
  val extLow = Play.current.configuration.getInt("uccertweb.ext.low").get
  val extHigh = Play.current.configuration.getInt("uccertweb.ext.high").get

  val agentMap = agentStateMapFactory(extLow to extHigh)

  def extension(ext: Int) = Action {
    try {
      val (state, reason) = agentMap.get(ext).get
      val json = Json.obj("state" -> state.toString, "reason" -> reason)
      Ok(json.toString)
    } catch {
      case e:  NoSuchElementException => NotFound("404 No such extension")
    }
  }
}

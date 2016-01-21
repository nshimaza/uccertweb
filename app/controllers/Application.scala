package controllers

import akka.actor.ActorSystem
import ctidriver.AgentState
import ctidriver.AgentState.AgentState
import akka.agent.Agent
import play.api.libs.json.Json
//import play.api._
import play.api.Play
//import play.api.libs.concurrent.Akka
import play.api.Logger
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

class Application extends Controller {
  val ext_low = Play.current.configuration.getInt("uccertweb.ext.low").get
  val ext_high = Play.current.configuration.getInt("uccertweb.ext.high").get

  val agent_map = (ext_low to ext_high).map(n => (n, Agent((AgentState.LOGOUT, "")))).toMap

  for (i <- ext_low to ext_high) {
    setAgentState(i, AgentState(i % 15), i.toString + i)
  }


  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def extension(ext: Int) = Action {
    try {
      val (state, reason) = agent_map(ext).get
      val json = Json.obj("state" -> state.toString, "reason" -> reason)
      Ok(json.toString)
//      Ok(state.id + ": " + state.toString)
    } catch {
      case e:  NoSuchElementException => NotFound("404 No such extension")
    }
  }

  def setAgentState(ext: Int, state: AgentState, reason: String): Unit = {
    try {
      agent_map(ext) send (state, reason)
    }
    catch {
      case e: NoSuchElementException =>
        Logger.error("Tried to set agent state on unconfigured extension (ext: " + ext + ").  Request ignored.")
    }
  }
}

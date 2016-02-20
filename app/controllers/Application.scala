package controllers

import javax.inject._

import akka.actor.ActorSystem
import ctidriver.AgentState
import ctidriver.AgentState.AgentState

import models.{AgentStateMapFactory, AgentStateMap}

import akka.agent.Agent
import play.api.libs.json.Json
//import play.api._
import play.api.Play
//import play.api.libs.concurrent.Akka
import play.api.Logger
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject()(agentStateMapFactory: AgentStateMapFactory) extends Controller {
  val ext_low = Play.current.configuration.getInt("uccertweb.ext.low").get
  val ext_high = Play.current.configuration.getInt("uccertweb.ext.high").get

  val agent_map = agentStateMapFactory(ext_low to ext_high)

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def extension(ext: Int) = Action {
    try {
      ctidriver.MessageType.decode(ctidriver.Tag.MessageTypeTag, akka.util.ByteString(0,0,1,1))

      val (state, reason) = agent_map.get(ext).get
      val json = Json.obj("state" -> state.toString, "reason" -> reason)
      Ok(json.toString)
//      Ok(state.id + ": " + state.toString)
    } catch {
      case e:  NoSuchElementException => NotFound("404 No such extension")
    }
  }

//  def setAgentState(ext: Int, state: AgentState, reason: String): Unit = {
//    try {
//      agent_map.get(ext) send (state, reason)
//    }
//    catch {
//      case e: NoSuchElementException =>
//        Logger.error("Tried to set agent state on unconfigured extension (ext: " + ext + ").  Request ignored.")
//    }
//  }
}

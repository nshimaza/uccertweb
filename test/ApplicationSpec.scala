import com.google.inject.AbstractModule
import org.junit.runner.RunWith
import org.scalatest.TestData
import org.scalatest.junit.JUnitRunner
import org.scalatestplus.play._
import play.api.Application
import play.api.inject.guice.{GuiceApplicationBuilder, GuiceInjectorBuilder}
import play.api.test._
import play.api.test.Helpers._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends PlaySpec with OneAppPerTest {

  override def newAppForTest(td: TestData): Application = new GuiceApplicationBuilder()
    .bindings(new models.AgentStateMapModule)
    .configure("uccertweb.ext.low" -> "123")
    .configure("uccertweb.ext.high" -> "234")
    .build()

  "Routes" must {
//    val application = new GuiceApplicationBuilder()
//      .configure("uccertweb.ext.llow" -> "123")
//      .build()

//    val injector = new GuiceInjectorBuilder().injector()
//    val application = injector.instanceOf(classOf[Application])

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

/*
  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Your new application is ready.")
    }

  }
*/

  "AgentStateController" must {
    "return logout agent state in JSON format" in {
      val jsonResult = contentAsJson(route(app, FakeRequest(GET, "/ext/200")).get)

      (jsonResult \ "state").as[String] mustBe "LOGOUT"
    }
  }

//  "CountController" should {
//
//    "return an increasing count" in {
//      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "0"
//      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "1"
//      contentAsString(route(app, FakeRequest(GET, "/count")).get) mustBe "2"
//    }
//
//  }

}

class ApplicationSpecModule extends AbstractModule {
  def configure() = {

  }
}
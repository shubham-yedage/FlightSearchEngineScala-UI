package controllers

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller}
import utilties.FlightParametersExtracter

class FlightController extends Controller {

  def index = Action {
      request =>
      val i = request.body.asFormUrlEncoded.get
      val list = FlightParametersExtracter.getFlightsList(request.body.asFormUrlEncoded.get)
        val json: JsValue=Json.obj("flights"->Json.arr(
          Json.obj("name"->"name","departure"->"arrival","depDate"->"depDate","depTime"->"depTime","0"->"0","fare"->"0"),
          Json.obj("name"->"name","departure"->"arrival","depDate"->"depDate","depTime"->"depTime","0"->"0","fare"->"0"),
          Json.obj("name"->"name","departure"->"arrival","depDate"->"depDate","depTime"->"depTime","0"->"0","fare"->"0"),
          Json.obj("name"->"name","departure"->"arrival","depDate"->"depDate","depTime"->"depTime","0"->"0","fare"->"0")
        ))

        Ok(json)
    }

  }

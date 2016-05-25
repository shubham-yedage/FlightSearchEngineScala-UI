package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import utilties.FlightParametersExtracter

class FlightController extends Controller {
  def index = Action { request =>
val i = request.body.asFormUrlEncoded.get
    FlightParametersExtracter.getFlightsList(i)
    //    request.body.asFormUrlEncoded match {
//      case map => {val flightList=FlightParametersExtracter.getFlightsList(map.get)}
//        val i=map.get
//    }
    Ok("fed")
  }

}

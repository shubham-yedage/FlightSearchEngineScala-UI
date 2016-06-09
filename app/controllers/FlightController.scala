package controllers

import javax.inject.Inject

import model.{Flight, FlightList}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}
import utilties.{FlightParametersExtracter, LoadCSV}

class FlightController extends Controller {


  def index = Action {
    request => {
      val i = request.body.asFormUrlEncoded.get
      try {
        val list: List[Flight] = FlightParametersExtracter.getFlightsList(request.body.asFormUrlEncoded.get)
        val fl = new FlightList(list)
        Ok(Json.toJson(fl))
      }
      catch {
        case a => InternalServerError(a.getMessage)
      }
    }
  }

  def dbOperation(operationToDo:String)={
    LoadCSV.operateOnDb(operationToDo)
  }

}



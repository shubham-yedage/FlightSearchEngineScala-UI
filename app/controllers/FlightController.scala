package controllers

import model.{Flight, FlightList}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import utilties.{FlightParametersExtracter, ListFromDbApproach2, LoadCSV}

class FlightController extends Controller {


  def index = Action {
    request => {
      try {
        val list: List[Flight] = ListFromDbApproach2.flightParameterExtarcter(request.body.asFormUrlEncoded.get)
        val fl =  FlightList(list)
        Ok(Json.toJson(fl))
      }
      catch {
        case a => InternalServerError("BAD REQUEST!")
      }
    }
  }

  def dbOperation(operationToDo: String)=Action {
    try {

      LoadCSV.operateOnDb(operationToDo.trim)
      Ok("Done!")
    }
    catch {
      case a => InternalServerError("No Operations Allowed")
    }
  }
//
//  def connFlightList = Action {
//    request => {
//      val i = request.body.asFormUrlEncoded.get.filterKeys(_.equalsIgnoreCase("name")).map(a => a._2.head).head
//      println(i)
//      try {
//        Ok("done!")
//      }
//      catch {
//        case a => InternalServerError(a.getMessage)
//      }
//    }
//  }

}



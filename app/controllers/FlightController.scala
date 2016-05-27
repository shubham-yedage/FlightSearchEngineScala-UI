package controllers

import javax.inject.Inject

import model.{Flight, FlightList}
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.mvc.{Action, Controller}
import utilties.FlightParametersExtracter

import scala.collection.mutable.ListBuffer
import scala.util.parsing.json.{JSONArray, JSONObject}

class FlightController @Inject() (ws: WSClient) extends Controller {



  def index  = Action {
    request => {
      val i = request.body.asFormUrlEncoded.get
      try {
        val list: List[Flight] = FlightParametersExtracter.getFlightsList(request.body.asFormUrlEncoded.get)
        val fl = new FlightList(list)
        Ok(Json.toJson(fl))
      }
catch{
  case a=>InternalServerError(a.getMessage)
}
    }
  }
}



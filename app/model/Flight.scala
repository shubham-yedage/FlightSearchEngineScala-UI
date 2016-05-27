package model

import play.api.libs.json.Json
case class Flight(name: String, departure: String, arrival: String, depDate: String, depTime: Int, duration: Float, fare: Float)
object Flight {
  implicit val flightFormat = Json.format[Flight]
}

case class FlightList(list: List[Flight])

object FlightList {
  implicit val flightListFormat = Json.format[FlightList]
}
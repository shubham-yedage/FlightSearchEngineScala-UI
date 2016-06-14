package model

import play.api.libs.json.Json
case class Flight(name: String, depLoc: String, arrLoc: String, date: String, time: Int, duration: Int, fare: Double)
object Flight {
  implicit val flightFormat = Json.format[Flight]
}

case class FlightList(list: List[Flight])

object FlightList {
  implicit val flightListFormat = Json.format[FlightList]
}
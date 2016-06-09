package utilties

import model.Flight
import org.neo4j.driver.v1._

import scala.collection.mutable.ListBuffer

object ListFromDb {
//  def filterConnectingFlights(fList: List[Flight], depLoc: String, arrLoc: String): List[Flight] = {
//    def filterDirectFlights(flight: Flight): Boolean = return flight.depLoc.equalsIgnoreCase(depLoc) && flight.arrLoc.equalsIgnoreCase(arrLoc)
//
//
//
//    val connFlights1 = fList.filter(_.depLoc.equalsIgnoreCase(depLoc)).filterNot(filterDirectFlights)
//    val conFlights2 = fList.filter(_.arrLoc.equalsIgnoreCase(arrLoc)).filterNot(filterDirectFlights)
//    var connFlights = ListBuffer[Flight]()
//    var tempConnFlights = ListBuffer[Flight]()
//    connFlights1.foreach { flight1 =>
//      def filterForConnectingFlights(flight2: Flight): Boolean = return (flight1.time + (flight1.duration * 100).toInt) > (flight2.time)
//      var tempList = conFlights2.filter(_.depLoc.equalsIgnoreCase(flight1.arrLoc)).filter(filterForConnectingFlights)
//      if (tempList.length > 0) {
//        tempList.foreach { flight2 =>
//          tempConnFlights += getMyConnFlight(flight1, flight2)
//          connFlights += getMyConnFlight(flight1, flight2)
//          connFlights += flight1
//          connFlights += flight2
//        }
//      }
//    }
//    tempConnFlights.toList
//  }

  def getFlights(depLoc: String, arrLoc: String, date: String, sortchoice: String, flightType: Boolean): List[Flight] = {
    val driver: Driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "shubh"))
    val session: Session = driver.session
    val rs = session.run(s"MATCH p=(from:Location{locCode:$depLoc})-[r:Flight*1{date:$date}]->(to:Location{locCode:$arrLoc}) RETURN r")
    val finalList = getFlightsList(rs)

    if (flightType) {
      //      val rs = session.run(s"MATCH p=(from:Location{locCode:$depLoc})-[r:Flight*1..4{date:$date}]->(to:Location{locCode:$arrLoc}) RETURN r")
      val rs2 = GraphDBConfig.runQuery(s"MATCH (:Location{locCode:$depLoc})-[r1:FlyingTo{date:$date}]->(:Location), (:Location)-[r2:FlyingTo{date:$date}]->(:Location{locCode:$date}) WITH collect(r1) AS flights1, collect(r2) AS flights2 WITH distinct filter(n in flights1 where not n in flights2) as connFlights1, filter(m in flights2 where not m in flights1) as connFlights2 RETURN connFlights2")
      val connFlights2 = getFlightsList(rs2)
      val rs1 = GraphDBConfig.runQuery(s"MATCH (:Location{locCode:$depLoc})-[r1:FlyingTo{date:$date}]->(:Location), (:Location)-[r2:FlyingTo{date:$date}]->(:Location{locCode:$date}) WITH collect(r1) AS flights1, collect(r2) AS flights2 WITH distinct filter(n in flights1 where not n in flights2) as connFlights1, filter(m in flights2 where not m in flights1) as connFlights2 RETURN connFlights1")
      val connFlights1 = getFlightsList(rs1)


      def getMyConnFlight(flight1: Flight, flight2: Flight): Flight = {
        Flight(flight1.name, flight1.depLoc, flight2.arrLoc, flight1.date, flight1.time, flight1.duration + flight2.duration, flight1.fare + flight2.fare)
      }

      var connFlights = ListBuffer[Flight]()
      var tempConnFlights = ListBuffer[Flight]()
      connFlights1.foreach { flight1 =>
        def filterForConnectingFlights(flight2: Flight): Boolean = return (flight1.time + (flight1.duration * 100).toInt) > (flight2.time)
        var tempList = connFlights2.filter(_.depLoc.equalsIgnoreCase(flight1.arrLoc)).filter(filterForConnectingFlights)
        if (tempList.length > 0) {
          tempList.foreach { flight2 =>
            tempConnFlights += getMyConnFlight(flight1, flight2)
            connFlights += getMyConnFlight(flight1, flight2)
            connFlights += flight1
            connFlights += flight2
          }
        }
      }
            return finalList.:::(connFlights.toList)
    }
    finalList
  }

  def getFlightsList(rs: StatementResult): List[Flight] = {
    val list = new ListBuffer[Flight]
    while (rs.hasNext) {
      val records = rs.next()
      list += new Flight(records.get("name") asString(), records.get("depLoc") asString(), records.get("arrLoc") asString(), records.get("date") asString(), records.get("time") asInt(), records.get("duration") asDouble(), records.get("fare") asDouble())
    }
    list.toList
  }
}
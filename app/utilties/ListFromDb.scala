//package utilties
//
//import model.Flight
//import org.neo4j.driver.v1._
//
//import scala.collection.mutable.{ListBuffer, Map}
//
//object ListFromDb {
//  def getFlights(depLoc: String, arrLoc: String, date: String, sortchoice: Int, flightType: Boolean): List[Flight] = {
//    def getFlightsList(rs: StatementResult): List[Flight] = {
//      val list = new ListBuffer[Flight]
//      while (rs.hasNext) {
//        val records = rs.next()
//        list += Flight(records.get("name") asString(), records.get("depLoc") asString(), records.get("arrLoc") asString(), records.get("date") asString(), records.get("time") asInt(), records.get("duration") asDouble(), records.get("fare") asDouble())
//      }
//      list.toList
//    }
//    val rs = GraphDBConfig.runQuery("MATCH (from:Location{locCode:\"" + depLoc + "\"})-[r:FlyingTo{date:\"" + date + "\"}]->(to:Location{locCode:\"" + arrLoc + "\"}) RETURN r.name as name, r.depLoc as depLoc, r.arrLoc as arrLoc, r.date as date, toInt(r.time) as time, toFloat(r.duration) as duration, toFloat(r.fare) as fare")
//    val finalList = getFlightsList(rs)
//
//    if (flightType) {
//      val rs2 = GraphDBConfig.runQuery("MATCH (:Location{locCode:\"" + depLoc + "\"})-[r1:FlyingTo{date:\"" + date + "\"}]->(:Location), (:Location)-[r2:FlyingTo{date:\"" + date + "\"}]->(:Location{locCode:\"" + arrLoc + "\"}) WITH collect(r1) AS flights1, collect(r2) AS flights2 WITH distinct filter(m in flights2 where not m in flights1) as connFlight2 UNWIND connFlight2 as connFlights2 RETURN DISTINCT connFlights2.name as name, connFlights2.depLoc as depLoc, connFlights2.arrLoc as arrLoc, connFlights2.date as date, toInt(connFlights2.time) as time, toFloat(connFlights2.duration) as duration, toFloat(connFlights2.fare) as fare")
//      val connFlights2 = getFlightsList(rs2)
//      val rs1 = GraphDBConfig.runQuery("MATCH (:Location{locCode:\"" + depLoc + "\"})-[r1:FlyingTo{date:\"" + date + "\"}]->(:Location), (:Location)-[r2:FlyingTo{date:\"" + date + "\"}]->(:Location{locCode:\"" + arrLoc + "\"}) WITH collect(r1) AS flights1, collect(r2) AS flights2 WITH distinct filter(n in flights1 where not n in flights2) as connFlight1 UNWIND connFlight1 as connFlights1 RETURN DISTINCT connFlights1.name as name, connFlights1.depLoc as depLoc, connFlights1.arrLoc as arrLoc, connFlights1.date as date, toInt(connFlights1.time) as time, toFloat(connFlights1.duration) as duration, toFloat(connFlights1.fare) as fare")
//      val connFlights1 = getFlightsList(rs1)
//
//      var connFlightMap = Map[Flight, List[Flight]]()
//      connFlights1.foreach { flight1 =>
//        def getMyConnFlight(flight1: Flight, flight2: Flight): Flight = {
//          Flight(flight1.name, flight1.depLoc, flight2.arrLoc, flight1.date, flight1.time, flight1.duration + flight2.duration, flight1.fare + flight2.fare)
//        }
//        def filterForConnectingFlights(flight2: Flight): Boolean = return (flight1.time + (flight1.duration * 100).toInt) < (flight2.time)
//        var tempList = connFlights2.filter(_.depLoc.equalsIgnoreCase(flight1.arrLoc)).filter(filterForConnectingFlights)
//        if (tempList.length > 0) {
//          tempList.foreach { flight2 =>
//            var li = ListBuffer[Flight]()
//            li += flight1
//            li += flight2
//            connFlightMap += ((getMyConnFlight(flight1, flight2), li.toList))
//          }
//        }
//      }
//      def getSortedConnFlights(toList: List[Flight], sortChoice: Int): List[Flight] = sortChoice match {
//        case 1 => toList.sortBy(_.fare).sortBy(_.duration)
//        case _ => toList.sortBy(_.duration)
//      }
//      var flightFinalList = ListBuffer[Flight]()
//      getSortedConnFlights(finalList.:::(connFlightMap.keys.toList), sortchoice).foreach { flight =>
//        if (connFlightMap.get(flight).isEmpty) {
//          flightFinalList += (flight)
//        }
//        else {
//          connFlightMap.get(flight).get.foreach {
//            flight2 =>
//              flightFinalList += (flight2)
//          }
//        }
//      }
//      return flightFinalList.toList
//    }
//    finalList
//  }
//}
//
//
//
//

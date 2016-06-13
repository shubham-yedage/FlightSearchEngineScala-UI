package utilties

import model.Flight
import org.neo4j.driver.v1.StatementResult

import scala.collection.mutable.ListBuffer

object ListFromDbApproach2 {

  def getFlights(depLoc: String, arrLoc: String, date: String, sortchoice: Int, flightType: Boolean): List[Flight] = {

    def getConnFlightsList(rs: StatementResult): List[Flight] = {
      val connFlightlist = new ListBuffer[Flight]
      while (rs.hasNext) {
        val records = rs.next()
        connFlightlist += Flight(records.get("fl1Name") asString(), records.get("fl1DepLoc") asString(), records.get("fl1ArrLoc") asString(), records.get("fl1Date") asString(), records.get("fl1Time") asInt(), records.get("fl1Duration") asDouble(), records.get("fl1Fare") asDouble())
        connFlightlist += Flight(records.get("fl2Name") asString(), records.get("fl2DepLoc") asString(), records.get("fl2ArrLoc") asString(), records.get("fl2Date") asString(), records.get("fl2Time") asInt(), records.get("fl2Duration") asDouble(), records.get("fl2Fare") asDouble())
      }
      connFlightlist.toList
    }

    def getFlightsList(rs: StatementResult): List[Flight] = {
      val list = new ListBuffer[Flight]
      while (rs.hasNext) {
        val records = rs.next()
        list += Flight(records.get("name") asString(), records.get("depLoc") asString(), records.get("arrLoc") asString(), records.get("date") asString(), records.get("time") asInt(), records.get("duration") asDouble(), records.get("fare") asDouble())
      }
      list.toList
    }


    val rs = GraphDBConfig.runQuery("MATCH (from:Location{locCode:\"" + depLoc + "\"})-[r:FlyingTo{date:\"" + date + "\"}]->(to:Location{locCode:\"" + arrLoc + "\"})"
      + " RETURN r.name as name, r.depLoc as depLoc, r.arrLoc as arrLoc, r.date as date, toInt(r.time) as time, toFloat(r.duration) as duration, toFloat(r.fare) as fare")
    val finalList = getFlightsList(rs)

    if (flightType) {
      val rs = GraphDBConfig.runQuery("MATCH path=(from:Location{locCode:\"" + depLoc + "\"})-[rel1:FlyingTo{date:\"" + date + "\"}]->(conn:Location)-[rel2:FlyingTo{date:\"" + date + "\"}]->(to:Location{locCode:\"" + arrLoc + "\"})"
        + " WHERE (toInt(rel1.time)+toFloat(rel1.duration)*100) < toInt(rel2.time)"
        + " RETURN rel1.name as fl1Name, rel1.depLoc as fl1DepLoc, rel1.arrLoc as fl1ArrLoc, rel1.date as fl1Date, toInt(rel1.time) as fl1Time, toFloat(rel1.duration) as fl1Duration, toFloat(rel1.fare) as fl1Fare,"
        + " rel2.name as fl2Name, rel2.depLoc as fl2DepLoc, rel2.arrLoc as fl2ArrLoc, rel2.date as fl2Date, toInt(rel2.time) as fl2Time, toFloat(rel2.duration) as fl2Duration, toFloat(rel2.fare) as fl2Fare"
        + " ORDER BY (rel1.duration+rel2.duration)")

      return finalList.:::(getConnFlightsList(rs))
    }
    finalList
  }
}

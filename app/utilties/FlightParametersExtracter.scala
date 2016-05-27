package utilties

import java.io.File

import model.Flight

import scala.collection.mutable.ListBuffer
import scala.io.Source

class FlightParametersExtracter {
}

object FlightParametersExtracter {

  def getFlightsList(form: Map[String, Seq[String]]): List[Flight] = {

    //Initiate Files
    val loader = classOf[FlightParametersExtracter].getClassLoader
    val fileList = new File(loader.getResource("model/resources").getPath).list()

    val depLoc = form.filterKeys(_.equalsIgnoreCase("depLoc")).map(a => a._2.head).head
    val arrLoc = form.filterKeys(_.equalsIgnoreCase("arrLoc")).map(a => a._2.head).head
    val choice = form.filterKeys(_.equalsIgnoreCase("sortchoice")).map(a => a._2.head).head
    val date = form.filterKeys(_.equalsIgnoreCase("date")).map(a => a._2.head).head
    val flightType = form.filterKeys(_.equalsIgnoreCase("connflightstatus")).map(a => a._2.head).head

    //=========================================================================
    var tempFlightListBuff = new ListBuffer[Flight]
    fileList.foreach { flightFileName =>
      val path = classOf[FlightParametersExtracter].getClassLoader.getResource("model/resources/" + flightFileName).getPath
      val lines = Source.fromFile(path).getLines
      lines.next()
      lines.foreach {
        line =>
          val cols = line.split(",").map(_.trim)
          tempFlightListBuff.+=(new Flight(cols(0), cols(1), cols(2), cols(3), cols(4).toInt, cols(5).toFloat, cols(6).toFloat))
      }
    }
    if (fileList.isEmpty) {
      throw new Exception("Resources Files Not Found")
    }

    def filterToGetAllRelatedFlights(flight: Flight): Boolean = return flight.departure.equalsIgnoreCase(depLoc) || flight.arrival.equalsIgnoreCase(arrLoc)
    val flightList = tempFlightListBuff.toList.filter(_.depDate.equalsIgnoreCase(date)).filter(filterToGetAllRelatedFlights)
    var fList = flightList.toSeq

    if (choice.toInt == 1) {
      fList = flightList.sortBy(_.fare)
    }
    else {
      fList = flightList.sortBy(_.duration)
    }
    if (fList.isEmpty) {
      throw new Exception("No Flights Found")
    }
    //Direct Flights
    def filterDirectFlights(flight: Flight): Boolean = return flight.departure.equalsIgnoreCase(depLoc) && flight.arrival.equalsIgnoreCase(arrLoc)
    val directFlights: List[Flight] = fList.toList.filter(filterDirectFlights)
    if (directFlights.isEmpty) {
      throw new Exception("No Flights Found")
    }
    //Connecting Flights
    val connFlights1 = fList.toList.filter(_.departure.equalsIgnoreCase(depLoc)).filterNot(filterDirectFlights)
    val conFlights2 = fList.toList.filter(_.arrival.equalsIgnoreCase(arrLoc)).filterNot(filterDirectFlights)
    var connFlights = new scala.collection.mutable.ListBuffer[Flight]()
    var tempConnFlights = new scala.collection.mutable.ListBuffer[Double]()
    connFlights1.foreach { flight1 =>
      def filterForConnectingFlights(flight2: Flight): Boolean = return (flight1.depTime + (flight1.duration * 100).toInt) > (flight2.depTime)
      var tempList = conFlights2.filter(_.departure.equalsIgnoreCase(flight1.arrival)).filter(filterForConnectingFlights)
      if (tempList.length > 0) {
        tempList.foreach { flight2 =>
          connFlights += flight1
          connFlights += flight2

        }
      }
    }
    if (connFlights.isEmpty) {
      throw new Exception("No Flights Found")
    }
    if (flightType.equalsIgnoreCase("true")) {

      //==================================

      //======================================

      val finalList = directFlights.:::(connFlights.toList)
      finalList
    }
    else {
      directFlights
    }
  }
}
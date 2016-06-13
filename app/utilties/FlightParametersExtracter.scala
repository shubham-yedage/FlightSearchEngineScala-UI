package utilties

import model.Flight

class FlightParametersExtracter {
}

object FlightParametersExtracter {

  //  def matchSortChoice(sortChoice: Int): String = sortChoice match {
  //    case 1 => "fare"
  //    case 2 => "duration"
  //    case _ => "fare,duration"
  //  }

  def getFlightsList(form: Map[String, Seq[String]]): List[Flight] = {
    //Initiate Files
    //    val loader = classOf[FlightParametersExtracter].getClassLoader
    //    val fileList = new File(loader.getResource("model/resources").getPath).list()

    val depLoc = form.filterKeys(_.equalsIgnoreCase("depLoc")).map(a => a._2.head).head
    val arrLoc = form.filterKeys(_.equalsIgnoreCase("arrLoc")).map(a => a._2.head).head
    val sortChoice = form.filterKeys(_.equalsIgnoreCase("sortchoice")).map(a => a._2.head).head
    val date = form.filterKeys(_.equalsIgnoreCase("date")).map(a => a._2.head).head
    val flightType = form.filterKeys(_.equalsIgnoreCase("connflightstatus")).map(a => a._2.head).head

    ListFromDbApproach2.getFlights(depLoc.toUpperCase, arrLoc.toUpperCase, date, sortChoice.toInt, flightType.toBoolean)
    //    //=========================================================================
    //    val tempFlightListBuff = new ListBuffer[Flight]
    //    fileList.foreach { flightFileName =>
    //      val path = classOf[FlightParametersExtracter].getClassLoader.getResource("model/resources/" + flightFileName).getPath
    //      val lines = Source.fromFile(path).getLines
    //      lines.next()
    //      lines.foreach {
    //        line =>
    //          val cols = line.split(",").map(_.trim)
    //          //tempFlightListBuff.+=(new Flight((cols(0)+cols(1)).trim, cols(2), cols(4), cols(3), cols(4).toInt, cols(5).toFloat, cols(6).toFloat))
    //          tempFlightListBuff.+=(new Flight((cols(0)+cols(1)).trim, cols(2), cols(4), "-", 0, 0, 0))
    ///*
    //         0 Airline	2-letter (IATA) or 3-letter (ICAO) code of the airline.
    //         1 Airline ID	Unique OpenFlights identifier for airline (see Airline).
    //         2 Source airport	3-letter (IATA) or 4-letter (ICAO) code of the source airport.
    //       3 Source airport ID	Unique OpenFlights identifier for source airport (see Airport)
    //       4 Destination airport	3-letter (IATA) or 4-letter (ICAO) code of the destination airport.
    //       5 Destination airport ID	Unique OpenFlights identifier for destination airport (see Airport)
    //       6 Codeshare	"Y" if this flight is a codeshare (that is, not operated by Airline, but another carrier), empty otherwise.
    //       7 Stops	Number of stops on this flight ("0" for direct)
    //       8 Equipment	3-letter codes for plane type(s) generally used on this flight, separated by spaces*/
    //
    //      }
    //    }
    //    if (fileList.isEmpty) {
    //      throw new Exception("Resources Files Not Found")
    //    }
    //    def filterToGetAllRelatedFlights(flight: Flight): Boolean = return flight.departure.equalsIgnoreCase(depLoc) || flight.arrival.equalsIgnoreCase(arrLoc)
    //    //val flightList = tempFlightListBuff.toList.filter(_.depDate.equalsIgnoreCase(date)).filter(filterToGetAllRelatedFlights)
    //    val flightList = tempFlightListBuff.toList.filter(filterToGetAllRelatedFlights)
    //    var fList = flightList
    //
    //    if (choice.toInt == 1) {
    //      fList = flightList.sortBy(_.fare)
    //    }
    //    else {
    //      fList = flightList.sortBy(_.duration)
    //    }
    //    if (fList.isEmpty) {
    //      throw new Exception("No Flights Found")
    //    }
    //    //Direct Flights
    //    def filterDirectFlights(flight: Flight): Boolean = return flight.departure.equalsIgnoreCase(depLoc) && flight.arrival.equalsIgnoreCase(arrLoc)
    //    val directFlights: List[Flight] = fList.filter(filterDirectFlights)
    //    if (directFlights.isEmpty) {
    //      throw new Exception("No Flights Found")
    //    }
    //    //Connecting Flights
    //    val connFlights1 = fList.toList.filter(_.departure.equalsIgnoreCase(depLoc)).filterNot(filterDirectFlights)
    //    val conFlights2 = fList.toList.filter(_.arrival.equalsIgnoreCase(arrLoc)).filterNot(filterDirectFlights)
    //    var connFlights = new scala.collection.mutable.ListBuffer[Flight]()
    //    var tempConnFlights = new scala.collection.mutable.ListBuffer[Flight]()
    //    connFlights1.foreach { flight1 =>
    //      def filterForConnectingFlights(flight2: Flight): Boolean = return (flight1.depTime + (flight1.duration * 100).toInt) > (flight2.depTime)
    //      var tempList = conFlights2.filter(_.departure.equalsIgnoreCase(flight1.arrival)).filter(filterForConnectingFlights)
    //      if (tempList.length > 0) {
    //        tempList.foreach { flight2 =>
    //          tempConnFlights += getMyConnFlight(flight1, flight2)
    //          connFlights += getMyConnFlight(flight1, flight2)
    //          connFlights += flight1
    //          connFlights += flight2
    //        }
    //      }
    //    }
    //    if (flightType.equalsIgnoreCase("true")) {
    //
    //      var finalList = directFlights.:::(tempConnFlights.toList).sortBy(_.fare)
    //      if(choice!=1)
    //      {
    //        finalList = directFlights.:::(tempConnFlights.toList).sortBy(_.duration)
    //      }
    //      finalList
    //    }
    //    else {
    //      directFlights
    //    }
    //  }
    //  def getMyConnFlight(flight1: Flight, flight2: Flight): Flight = {
    //    Flight(flight1.name, flight1.departure, flight2.arrival, flight1.depDate, flight1.depTime, flight1.duration + flight2.duration, flight1.fare + flight2.fare)
    //  }
  }
}
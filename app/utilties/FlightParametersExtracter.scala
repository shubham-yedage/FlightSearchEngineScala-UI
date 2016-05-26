package utilties

import java.io.{File, FileNotFoundException}

import com.fasterxml.jackson.databind.JsonSerializable.Base
import model.Flight

import scala.collection.mutable.ListBuffer
import scala.io.Source

class FlightParametersExtracter {
}

object FlightParametersExtracter {
  def getFlightsList(form: Map[String, Seq[String]]) = {

    try {
      //Initiate Files
      val loader = classOf[FlightParametersExtracter].getClassLoader
val fileList=new File(loader.getResource("model/resources").getPath).list()
      //Initiate scanning for inputs
      //      val keyList = form.keySet
      //
      //      val list = for {f <- form
      //                      key <- keyList
      //                      if f._1.equalsIgnoreCase(key)
      //      } yield f._2.head
      val depLoc = form.filterKeys(_.equalsIgnoreCase("depLoc")).map(a => a._2.head).head
      val arrLoc = form.filterKeys(_.equalsIgnoreCase("arrLoc")).map(a => a._2.head).head
      val choice = form.filterKeys(_.equalsIgnoreCase("sortchoice")).map(a => a._2.head).head
      val date = form.filterKeys(_.equalsIgnoreCase("date")).map(a => a._2.head).head
      val flightType = form.filterKeys(_.equalsIgnoreCase("connflightstatus")).map(a => a._2.head).head
      //val finallist= searchFlight.getFlights("fra", "lhr", "20/11/2010", 1, "true")
      // fileList


      //=========================================================================

      var tempFlightListBuff = new ListBuffer[Flight]
      fileList.foreach { flightFileName =>
        val resourcesStream = getClass.getResourceAsStream(flightFileName)
        val lines = Source.fromInputStream(resourcesStream).getLines
        lines.next()

        lines.foreach {
          line =>
            val cols = line.split(",").map(_.trim)
            tempFlightListBuff.+=(new Flight(cols(0), cols(1), cols(2), cols(3), cols(4).toInt, cols(5).toFloat, cols(6).toFloat))
        }
      }

      def filterToGetAllRelatedFlights(flight: Flight): Boolean = return flight.departure.equalsIgnoreCase(depLoc) || flight.arrival.equalsIgnoreCase(arrLoc)
      val flightList = tempFlightListBuff.toList.filter(_.depDate.equalsIgnoreCase(date)).filter(filterToGetAllRelatedFlights)

      if (flightList.isEmpty) {
        throw new NoSuchFieldException("Results for Current Search Not Found!")
      }
      //Direct Flights
      def filterDirectFlights(flight: Flight): Boolean = return flight.departure.equalsIgnoreCase(depLoc) && flight.arrival.equalsIgnoreCase(arrLoc)
      val directFlights = flightList.filter(filterDirectFlights)

      //Connecting Flights
      val connFlights1 = flightList.filter(_.departure.equalsIgnoreCase(depLoc)).filterNot(filterDirectFlights)
      val conFlights2 = flightList.filter(_.arrival.equalsIgnoreCase(arrLoc)).filterNot(filterDirectFlights)
      var connFlights = new scala.collection.mutable.ListBuffer[Flight]()
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
      if (flightType.equalsIgnoreCase("true")) {
        directFlights.:::(connFlights.toList)
      }
      else
        directFlights


      //====================================================================


    }
    catch {
      case ex: NoSuchFieldException => {
        println(ex.getMessage)
      }
      case ex: FileNotFoundException => {
        println("Missing file exception")
      }
      case ex: NullPointerException => {
        println("Null Pointer Exception")
      }
      case ex: NumberFormatException => {
        println("Improper Fields Exception")
      }
    }
  }
}
package utilties

import java.io.File

import model.Flight

import scala.collection.mutable

object FlightParametersExtracter {
  def getFlightsList(form: Map[String, Seq[String]]) = {

    //    try {
    //Initiate Files
    val dir = new File("/home/synerzip/codebase/FlightSearchEngineScala/src/main/resources")
    val files = dir.listFiles
    val fileList = for {
      file <- files
    } yield file.getName
    val searchFlight = new SearchFlightsApproach2(fileList.toList)

    //Initiate scanning for inputs
    val keyList=form.keySet

    val list=for{ f <- form
    key <- keyList
      if f._1.equalsIgnoreCase(key)
    }yield f._2.head

     val depLoc= form.filterKeys(_.equalsIgnoreCase("depLoc")).map(a => a._2.head).head
      val arrLoc = form.filterKeys(_.equalsIgnoreCase("arrLoc")).map(a => a._2.head).head
    val choice = form.filterKeys(_.equalsIgnoreCase("sortchoice")).map(a => a._2.head).head
      val date= form.filterKeys(_.equalsIgnoreCase("date")).map(a => a._2.head).head
      searchFlight.getFlights(depLoc, arrLoc, date, choice.toInt).toList

    //    }
    //    catch {
    //      case ex: NoSuchFieldException => {
    //        println(ex.getMessage)
    //      }
    //      case ex: FileNotFoundException => {
    //        println("Missing file exception")
    //      }
    //      case ex: NullPointerException => {
    //        println("Null Pointer Exception")
    //      }
    //      case ex: NumberFormatException => {
    //        println("Improper Fields Exception")
    //      }
  }
}
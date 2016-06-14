import model.Flight
import org.junit.Assert._
import org.junit.Test
import utilties.ListFromDbApproach2

class MyTest1 {


  @Test def testGetFlights2: Unit = {
    val flightsList = ListFromDbApproach2.getFlights("FRA", "LHR", "20/11/2010", 1, false)
    assertFalse(flightsList.isEmpty)
    val testFlightsList = List(Flight("AF281", "FRA", "LHR", "20/11/2010", 800, 4, 200f),
      Flight("LH929", "FRA", "LHR", "20/11/2010", 100, 4, 800.0f),
      Flight("BA999", "FRA", "LHR", "20/11/2010", 2400, 5, 1000.0f))
    assertEquals(flightsList, testFlightsList)
  }

  @Test def testGetFlights3: Unit = {
    val flightsList = ListFromDbApproach2.getFlights("FRA", "LHR", "20/11/2010", 2, false)
    assertFalse(flightsList.isEmpty)
    val testFlightsList = List(Flight("AF281", "FRA", "LHR", "20/11/2010", 800, 4, 200f),
      Flight("LH929", "FRA", "LHR", "20/11/2010", 100, 4, 800.0f),
      Flight("BA999", "FRA", "LHR", "20/11/2010", 2400, 5, 1000.0f))
    assertEquals(flightsList, testFlightsList)
  }

  @Test def testGetFlights1: Unit = {
    val flightsList = ListFromDbApproach2.getFlights("FRA", "LHR", "20/11/2010", 1, true)
    assertFalse(flightsList.isEmpty)

    val testFlightsList = List(Flight("AF281", "FRA", "LHR", "20/11/2010", 800, 4, 200f),
      Flight("LH929", "FRA", "LHR", "20/11/2010", 100, 4, 800.0f),
      Flight("BA999", "FRA", "LHR", "20/11/2010", 2400, 5, 1000.0f),
      Flight("AF299", "FRA", "CDG", "20/11/2010", 600, 4, 1000.0f),
      Flight("AF288", "CDG", "LHR", "20/11/2010", 2000, 3, 100.0f),
      Flight("LH999", "FRA", "CDG", "20/11/2010", 600, 4, 1000.0f),
      Flight("AF288", "CDG", "LHR", "20/11/2010", 2000, 3, 100.0f))
    assertEquals(flightsList, testFlightsList)
  }

}

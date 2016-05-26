package model

class Flight(private var _name: String, var _departure: String, var _arrival: String, var _depDate: String, var _depTime: Int, var _duration: Float, var _fare: Float) {

  //Default Constructor
  //  private var _name=""
  //  private var _departure=""
  //  private var _arrival=""
  //  private var _depDate=""
  //  private var _depTime=0
  //  private var _duration=0.0f
  //  private var _fare=0.0f


  //Getter
  def name = _name

  def departure = _departure

  def arrival = _arrival

  def depDate = _depDate

  def depTime = _depTime

  def duration = _duration

  def fare = _fare

  //Setter
  def name_=(value: String): Unit = _name = value

  def departure_=(value: String): Unit = _departure = value

  def arrival_=(value: String): Unit = _arrival = value

  def depDate_=(value: String): Unit = _depDate = value

  def depTime_=(value: Int): Unit = _depTime = value

  def duration_=(value: Float): Unit = _duration = value

  def fare_=(value: Float): Unit = _fare = value

  //Equals
  def canEqual(other: Any): Boolean = other.isInstanceOf[Flight]

  override def toString = {
    name + " " + departure + " " + arrival + " " + depDate + " " + depTime + " " + duration + " " + fare
  }

  override def equals(other: Any): Boolean = other match {
    case that: Flight =>
      (that canEqual this) &&
        _name == that._name &&
        _departure == that._departure &&
        _arrival == that._arrival &&
        _depDate == that._depDate &&
        _depTime == that._depTime &&
        _duration == that._duration &&
        _fare == that._fare
    case _ => false
  }

  //Hash Code
  override def hashCode(): Int = {
    val state = Seq(_name, _departure, _arrival, _depDate, _depTime, _duration, _fare)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
//object Flights {
//    implicit val flightWrites:OWrites[Flights]=(
//      (JsPath \ "name").write[String] and
//        (JsPath \ "departure").write[String] and
//        (JsPath \ "arrival").write[String] and
//        (JsPath \ "depDate").write[String] and
//        (JsPath \ "depTime").write[Int] and
//        (JsPath \ "duration").write[Float] and
//        (JsPath \ "fare").write[Float]
//
//      )(unlift(Flights.unapply)
//    )
//  }
//
//case class Flights(private var _name: String, var _departure: String, var _arrival: String, var _depDate: String, var _depTime: Int, var _duration: Float, var _fare: Float)
package code
package snippet

import model._

import com.foursquare.rogue.Rogue._
import com.foursquare.rogue.{LatLong, Degrees}
import net.liftweb.util.Helpers._

class GeoSnippet {


  implicit def string2city(name: String) = new {
    def at(ll: (Double,Double)) = {
      City.where(_.name eqs name).get getOrElse {
        val pos = LatLong(ll._1, ll._2)
        City.createRecord.name(name).loc(pos).save(true)
      }
    }
  }

  val london = "London, UK" at (51.5, -0.166667)
  val brighton = "Brighton, UK" at (50.819059, -0.136642)
  val paris = "Paris, France" at (48.866667, 2.333333)
  val berlin = "Berlin, Germany" at (52.533333, 13.416667)
  val sydney = "Sydney, Australia" at (-33.867387,151.207629)
  val newYork = "New York, USA" at (40.714623,-74.006605)


  val centre = london.loc.is
  val radius = Degrees( (500 / 6378.137).toDegrees )

  def near = "#cities" #> (
    for (city <- City.where( _.loc near (centre.lat ,centre.long, radius) ).fetch()) yield
      ".name *" #> city.name
    )

}


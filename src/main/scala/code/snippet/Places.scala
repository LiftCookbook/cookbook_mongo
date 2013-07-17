package code
package snippet

import model._

import net.liftweb.util.Helpers._

class Places {

  lazy val uk : Country =
    Country.find("uk") openOr {

      val info = Map(
        "Brighton" -> 134293,
        "Birmingham" -> 970892,
        "Liverpool" -> 469017)

      // An embedded document:
      val unionJack =
        Image.createRecord.url("http://bit.ly/unionflag200").width(200).height(100)

      // Another document we will reference:
      val earth = Planet.createRecord.id("earth").review("Harmless").save

      // A list of embedded documents:
      val meals =
        Food.createRecord.name("Fish & Chips") ::
        Food.createRecord.name("Cream Tea") ::
        Food.createRecord.name("Roast Beef") ::
        Nil

      Country.createRecord.id("uk").
        population(info).
        planet(earth.id.is).
        flag(unionJack).
        foods(meals).
        save
  }


  def facts = {

    // uk.population(uk.population.is + ("Westminster"->81766)).update

    "#facts" #> (
    for { (name,pop) <- uk.population.is } yield
        ".name *" #> name &
          ".pop *" #> pop &
          ".planet" #> uk.planet.obj.map { p =>
            ".planet-name *" #> p.id &
              ".planet-review *" #> p.review }
      )
  }

}

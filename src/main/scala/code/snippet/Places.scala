package code
package snippet

import model._

import net.liftweb.util.Helpers._

class Places {

  val uk = Country.find("uk") openOr {

    val info = Map(
      "Brighton" -> 134293,
      "Birmingham" -> 970892,
      "Liverpool" -> 469017)

    Country.createRecord.id("uk").population(info).save
  }


  def facts = {

    // uk.population(uk.population.is + ("Westminster"->81766)).update

    "#facts" #> (
    for { (name,pop) <- uk.population.is } yield
        ".name *" #> name & ".pop *" #> pop
      )
  }

}

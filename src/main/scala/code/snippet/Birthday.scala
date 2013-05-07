package code.snippet

import net.liftweb.util.Helpers._
import code.model.{DayOfWeek, Birthday}

class BirthdaySnippet {

  import DayOfWeek._

  val facts =
    "Albert Einstein" -> Fri ::
    "Richard Feynman" -> Sat ::
    "Isaac Newton" -> Sun ::
    Nil

  val records = facts.collect{ case (name, dow) =>
    Birthday.createRecord.id(name).dow(dow).save
  }

  def render = ".birthday" #> Birthday.findAll.map { b =>
    ".name" #> b.id &
    ".dow" #> b.dow
  }

}

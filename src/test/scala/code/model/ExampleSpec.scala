package code.model

import org.specs2.mutable._

class ExampleSpec extends Specification with MongoTestKit {

  sequential

  "Planet" should {

    "be able to create records" in MongoContext {

      val mercury = Planet.createRecord.id("mercury").review("Hot!").save

      Planet.find("mercury").isDefined must beTrue

    }

  }
}

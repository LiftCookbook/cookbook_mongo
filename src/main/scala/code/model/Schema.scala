package code
package model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._

class Country private () extends MongoRecord[Country] with StringPk[Country] {
  override def meta = Country
  object population extends MongoMapField[Country,Int](this) {
   // override def optional_? = true
  }
}

object Country extends Country with MongoMetaRecord[Country] {
  override def collectionName = "example.earth"
 // override def mongoIdentifier = bootstrap.liftweb.OtherMongoIdentifier
}

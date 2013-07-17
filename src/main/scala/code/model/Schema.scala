package code
package model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field.{EnumNameField, IntField, StringField}
import com.foursquare.rogue.LatLong

class Country private () extends MongoRecord[Country] with StringPk[Country] {

  override def meta = Country

  // A Map:
  object population extends MongoMapField[Country,Int](this) {
   // override def optional_? = true
  }

  // An embedded document:
  object flag extends BsonRecordField(this, Image)

  // An embedded list:
  object foods extends BsonRecordListField(this, Food)

  // A reference to another document:
  object planet extends StringRefField(this, Planet, 128)

}

object Country extends Country with MongoMetaRecord[Country] {
  override def collectionName = "example.earth"
  // If you used an alternative Mongo connection name:
  // override def mongoIdentifier = bootstrap.liftweb.OtherMongoIdentifier
}

class Food private () extends BsonRecord[Food] {
  def meta = Food
  object name extends StringField(this, 1024)
}

object Food extends Food with BsonMetaRecord[Food]


class Image private () extends BsonRecord[Image] {
  def meta = Image
  object url extends StringField(this, 1024)
  object width extends IntField(this)
  object height extends IntField(this)
}

object Image extends Image with BsonMetaRecord[Image]



class Planet private() extends MongoRecord[Planet] with StringPk[Planet] {
  override def meta = Planet
  object review extends StringField(this,1024)
}

object Planet extends Planet with MongoMetaRecord[Planet] {
  override def collectionName = "example.planet"
}


class City private () extends MongoRecord[City] with ObjectIdPk[City] {
  override def meta = City

  object name extends StringField(this, 60)

  object loc extends MongoCaseClassField[City, LatLong](this)
}

object City extends City with MongoMetaRecord[City] {
  import net.liftweb.mongodb.BsonDSL._
  ensureIndex(loc.name -> "2d", unique=true)

  override def collectionName = "example.city"
}


object DayOfWeek extends Enumeration {
  type DayOfWeek = Value
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
}

class Birthday private () extends MongoRecord[Birthday] with StringPk[Birthday] {
  override def meta = Birthday
  object dow extends EnumNameField(this, DayOfWeek)
}

object Birthday extends Birthday with MongoMetaRecord[Birthday] {
}


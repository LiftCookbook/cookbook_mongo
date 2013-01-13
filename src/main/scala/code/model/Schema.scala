package code
package model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field.{IntField,StringField}

class Country private () extends MongoRecord[Country] with StringPk[Country] {

  override def meta = Country

  object population extends MongoMapField[Country,Int](this) {
   // override def optional_? = true
  }

  object flag extends BsonRecordField(this, Image)

  object planet extends StringRefField(this, Planet, 128)

}

object Country extends Country with MongoMetaRecord[Country] {
  override def collectionName = "example.earth"
 // override def mongoIdentifier = bootstrap.liftweb.OtherMongoIdentifier
}



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
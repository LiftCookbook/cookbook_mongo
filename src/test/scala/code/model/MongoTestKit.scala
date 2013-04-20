package code.model

import net.liftweb.http.{Req, S, LiftSession}
import net.liftweb.util.StringHelpers
import net.liftweb.common.Empty
import net.liftweb.mongodb._
import com.mongodb.ServerAddress
import com.mongodb.Mongo
import org.specs2.mutable.Around
import org.specs2.execute.Result

trait MongoTestKit {

  val server = new Mongo(new ServerAddress("127.0.0.1", 27017))

  def dbName = "test_"+this.getClass.getName
    .replace(".", "_")
    .toLowerCase

  def initDb() : Unit = MongoDB.defineDb(DefaultMongoIdentifier, server, dbName)

  def destroyDb() : Unit = {
    MongoDB.use(DefaultMongoIdentifier) { d => d.dropDatabase() }
    MongoDB.close
  }

  trait TestLiftSession {
    def session = new LiftSession("", StringHelpers.randomString(20), Empty)
    def inSession[T](a: => T): T = S.init(Req.nil, session) { a }
  }

  object MongoContext extends Around with TestLiftSession {
    def around[T <% Result](testToRun: =>T) = {
      initDb()
      try {
        inSession {
          testToRun
        }
      } finally {
        destroyDb()
      }
    }
  }

}
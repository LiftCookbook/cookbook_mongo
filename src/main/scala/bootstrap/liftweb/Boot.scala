package bootstrap.liftweb

import net.liftweb._
import mongodb.{DefaultMongoIdentifier, MongoDB, MongoIdentifier}

import common._
import http._
import sitemap._
import Loc._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery._
import java.net.URI
import com.mongodb.{ServerAddress, Mongo}


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {

  System.setProperty("DEBUG.MONGO", "true")
  System.setProperty("DB.TRACE", "true")

  def boot {
    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu

      Menu.i("Hash Map") / "hashmap",

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Static Content")))

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery=JQueryModule.JQuery172
    JQueryModule.init()

    MongoUrl.defineDb(DefaultMongoIdentifier, "mongodb://127.0.0.1:27017/cookbook")

  }

}



object MongoUrl {

  def defineDb(id: MongoIdentifier, url: String) {

    val uri = new URI(url)

    val db = uri.getPath drop 1
    val server = new Mongo(new ServerAddress(uri.getHost, uri.getPort))

    Option(uri.getUserInfo).map(_.split(":")) match {
      case Some(Array(user,pass)) => MongoDB.defineDbAuth(id, server, db, user, pass)
      case _ => MongoDB.defineDb(id, server, db)
    }
  }

}

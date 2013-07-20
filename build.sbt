name := "Cookbook Mongo"

version := "1.1.0"

organization := "net.liftweb"

scalaVersion := "2.10.2"

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )

seq(com.github.siasia.WebPlugin.webSettings :_*)

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= {
  val liftVersion = "2.5"
  val rogueVersion = "2.2.0"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftmodules"   %% "lift-jquery-module_2.5" % "2.4",
    "net.liftweb" %% "lift-mongodb-record" % liftVersion,
    "com.foursquare" %% "rogue-field" % rogueVersion intransitive(),
    "com.foursquare" %% "rogue-core"  % rogueVersion intransitive(),
    "com.foursquare" %% "rogue-lift"  % rogueVersion intransitive(),
    "com.foursquare" %% "rogue-index" % rogueVersion intransitive(),
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.7.v20120910"  % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "org.specs2"        %% "specs2"             % "2.1"           % "test"
  )
}


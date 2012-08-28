name := "ScrapeRecipes"

version := "1.0"

scalaVersion := "2.9.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor" % "2.0.3",
  "com.typesafe.akka" % "akka-kernel" % "2.0.3",
  "org.jsoup" % "jsoup" % "1.6.3",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  "com.mongodb.casbah" % "casbah_2.9.1" % "2.1.5-1"
)


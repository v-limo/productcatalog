name := """backend-project"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test

libraryDependencies ++= Seq(
	guice,
	"com.typesafe.play" %% "play-slick" % "5.3.1",
	"com.typesafe.play" %% "play-slick-evolutions" % "5.3.1",
	"org.postgresql" % "postgresql" % "42.7.5",
	"org.flywaydb" %% "flyway-play" % "9.1.0",
	"org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
)

libraryDependencies += guice
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

ThisBuild / useSuperShell := false

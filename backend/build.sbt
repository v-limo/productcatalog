name := """backend-project"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test

libraryDependencies ++= Seq(
	guice,
	"com.typesafe.slick" %% "slick" % "3.6.0",
	"com.typesafe.play" %% "play-slick" % "5.3.1",
	"com.typesafe.play" %% "play-slick-evolutions" % "5.3.1",
	"org.postgresql" % "postgresql" % "42.7.5",
	"com.typesafe.slick" %% "slick-hikaricp" % "3.6.0",
	"org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
)


ThisBuild / useSuperShell := false
name := "backend-project"
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies ++= Seq(
	guice,
	"org.playframework" %% "play-slick" % "6.1.0",
	"org.playframework" %% "play-slick-evolutions" % "6.1.0",
	"com.typesafe.slick" %% "slick-hikaricp" % "3.5.0",
	"org.postgresql" % "postgresql" % "42.7.5",
	"org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
)

libraryDependencies += "org.scalatestplus" %% "mockito-4-6" % "3.2.18.0" % Test
libraryDependencies += "com.h2database" % "h2" % "2.3.232" % Test

ThisBuild / useSuperShell := false

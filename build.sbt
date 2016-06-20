name := """uccertweb"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.typesafe.akka" %% "akka-agent" % "2.4.4",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
//  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.apache.httpcomponents" % "httpcore" % "4.4.4" % Test,
  "org.apache.httpcomponents" % "httpclient" % "4.5.2" % Test,
  "com.typesafe.akka" %% "akka-testkit" % "2.4.4" % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.4" % Test,
  "org.mockito" % "mockito-core" % "1.10.19" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


fork in run := true
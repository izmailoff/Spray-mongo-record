
import SonatypeKeys._

sonatypeSettings

name := "Spray-mongo-record"

version := "0.0.1"

scalaVersion := "2.10.4"

//crossScalaVersions := Seq("2.10.0", "2.10.4")  11????

organization := "com.github.izmailoff"

publishArtifact in Test := false

pomExtra := {
  <url>https://github.com/izmailoff/Spray-mongo-record</url>
    <licenses>
      <license>
        <name>The MIT License (MIT)</name>
        <url>https://raw.githubusercontent.com/izmailoff/Spray-mongo-record/master/LICENSE</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/https://github.com/izmailoff/Spray-mongo-record</connection>
      <developerConnection>scm:git:git@github.com:https://github.com/izmailoff/Spray-mongo-record</developerConnection>
      <url>github.com/https://github.com/izmailoff/Spray-mongo-record</url>
    </scm>
    <developers>
      <developer>
        <id>izmailoff</id>
        <name>Aleksey Izmailov</name>
        <url>http://izmailoff.github.io/</url>
      </developer>
    </developers>
}

libraryDependencies ++= {
  val liftVersion = "2.5-M4" // parameterize based on build or otherwise
  val sprayVersion = "1.3.1"
  val akkaVersion = "2.3.0"
  Seq(
    // Lift:
    "net.liftweb" %% "lift-json" % liftVersion, // add: % liftVersion % "compile" intransitive()
    "net.liftweb" %% "lift-common" % liftVersion,
    "net.liftweb" %% "lift-mongodb-record" % liftVersion,
    // Spray:
    "io.spray" % "spray-can" % sprayVersion,
    "io.spray" % "spray-routing" % sprayVersion,
    "io.spray" % "spray-testkit" % sprayVersion, // % "test",
    // Test: // what scope should it be compile or test?
    "org.specs2" %% "specs2" % "2.3.12", // % "test", TODO: These have to be extracted into a separate project for testing
    "com.github.fakemongo" % "fongo" % "1.5.5", // % "test",
    // Akka: //needed by Spray
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
  )
}

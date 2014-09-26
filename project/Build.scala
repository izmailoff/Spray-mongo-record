package com.github.izmailoff

import sbt._
import sbt.Keys._
import sbt.Tests
import xerial.sbt.Sonatype._
import xerial.sbt.Sonatype.SonatypeKeys._

object SprayMongoRecord extends Build {

  // ===========  PROJECTS  ============ //

  lazy val root = Project(id = "root",
    base = file("."))
    .aggregate(marshallers, testutils, mongoconnect)

  lazy val marshallers = Project(id = "marshallers",
    base = file("marshallers"))
    .settings(marshallersSettings: _*)
    .dependsOn(mongoconnect)

  lazy val testutils = Project(id = "testutils",
    base = file("testutils"))
    .settings(testutilsSettings: _*)
    .dependsOn(mongoconnect, marshallers)

  lazy val mongoconnect = Project(id = "mongoconnect",
    base = file("mongoconnect"))
    .settings(mongoconnectSettings: _*)


  // ========= PROJECT SETTINGS =========//

  lazy val testutilsSettings =
    settings ++
      Seq(libraryDependencies ++=
        Dependencies.lift ++
          Dependencies.akka ++
          Dependencies.spray ++
          Dependencies.testKit)

  lazy val marshallersSettings =
    settings ++
      Seq(libraryDependencies ++=
        Dependencies.lift ++
        Dependencies.spray ++
          Dependencies.akka ++
          Dependencies.testKit)

  lazy val mongoconnectSettings =
    settings ++
      Seq(libraryDependencies ++=
        Dependencies.lift ++
          Dependencies.akka) //++
          //Dependencies.testKit)

  // ========= GENERAL SETTINGS =========//

  override lazy val settings =
    super.settings ++
      buildSettings ++
      defaultSettings ++
      sonatypeSettings

  lazy val buildSettings = Seq(
    organization := "com.github.izmailoff",
    version := "0.0.2",
    scalaVersion := "2.10.4"
  )

  lazy val defaultSettings = Seq(
    publishArtifact in Test := false,
    profileName := "com.github.izmailoff",
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
    },
      // Compile options
    scalacOptions in Compile ++=
      Seq("-encoding",
        "UTF-8",
        "-target:jvm-1.7",
        "-deprecation",
        "-feature",
        "-unchecked",
        "-language:_"),
    /* Uncomment to see reflection uses: "-Xlog-reflective-calls",
     This generates lots of noise in the build: "-Ywarn-adapted-args",
    */
    scalacOptions in Compile in doc ++=
      Seq("-diagrams",
        "-implicits")
  )


  //=========== DEPENDENCIES =============//

  object Dependencies {

    object Compile {

      // MONGO DRIVER
      val mongoDriver = "org.mongodb" % "mongo-java-driver" % "2.12.3" % "test" // may cause conflicts, now there are 2!!! drivers

      // MONGO SCHEMA MIGRATION
      val mongeez = "org.mongeez" % "mongeez" % "0.9.4"

      // LIFT
      val liftVersion = "2.5.1"
      val liftJson = "net.liftweb" %% "lift-json" % liftVersion
      val liftCommon = "net.liftweb" %% "lift-common" % liftVersion
      val liftRecord = "net.liftweb" %% "lift-mongodb-record" % liftVersion

      // ROGUE
      val rogueField = "com.foursquare" %% "rogue-field" % "2.4.0" intransitive()
      val rogueCore = "com.foursquare" %% "rogue-core" % "2.4.0" intransitive()
      val rogueLift = "com.foursquare" %% "rogue-lift" % "2.4.0" intransitive()
      val rogueIndex = "com.foursquare" %% "rogue-index" % "2.4.0" intransitive()

      // SPRAY
      val sprayVersion = "1.3.1"
      val sprayCan = "io.spray" % "spray-can" % sprayVersion
      val sprayRouting = "io.spray" % "spray-routing" % sprayVersion
      val sprayTestkit = "io.spray" % "spray-testkit" % sprayVersion

      // AKKA
      val akkaVersion = "2.3.0"
      val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
      val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test" // TODO: maybe not needed

      // TEST
      object Test {
        val specs2 = "org.specs2" %% "specs2" % "2.3.12" //% "test"
        val testDb = "com.github.fakemongo" % "fongo" % "1.5.6" //% "test"
      }
    }

    import Compile._

    val rogue = Seq(rogueField, rogueCore, rogueLift, rogueIndex)
    val lift = Seq(mongoDriver, liftCommon, liftRecord, liftJson, mongeez)
    val akka = Seq(akkaActor, akkaTestkit)
    val spray = Seq(sprayCan, sprayRouting)
    val testKit = Seq(sprayTestkit, Test.specs2, Test.testDb)
    val testKitScoped = Seq(sprayTestkit % "test", Test.specs2 % "test", Test.testDb % "test")
  }

}


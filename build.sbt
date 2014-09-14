
import SonatypeKeys._

sonatypeSettings

name := "Spray-mongo-record"

version := "0.0.1"

scalaVersion := "2.10.4"

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


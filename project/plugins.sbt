
// Publishes artifacts to Sonatype.
// Most useful commands:
// publishSigned  // Publish a GPG-signed artifact to Sonatype
// sonatypeRelease // Do close and promote at once
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")


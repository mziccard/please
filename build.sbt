// Project name
name := """please"""

// Group id
organization := "me.mziccard"

// project version
version := "0.0.1"

// project description
description := "Easily add dependencies to your Maven, Gradle or Sbt projects"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies ++= Seq(
  "org.apache.httpcomponents" % "httpclient" % "4.5",
  "com.google.code.gson" % "gson" % "2.3",
  "commons-cli" % "commons-cli" % "1.3.1",
  "org.apache.maven" % "maven-repository-metadata" % "3.3.3"
)

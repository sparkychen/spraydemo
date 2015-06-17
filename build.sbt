import sbt._
import NativePackagerHelper._

//packageArchetype.java_server

net.virtualvoid.sbt.graph.Plugin.graphSettings

deploymentSettings

name := "spraydemo"

version := "0.1"

scalaVersion := "2.10.5"

organization := "com.dododa"

libraryDependencies ++= {
  val scalaV = "2.10.5"
  val akkaV = "2.3.11"
  val sprayV = "1.3.3"
  val json4sV = "3.2.11"
  val scalazV = "7.1.2"
  Seq(
    "junit" % "junit" % "4.12" % "test",
    "org.specs2" %% "specs2" % "3.3.1" % "test"
      exclude("org.scalaz.stream","*"),
    "org.scalatest" %% "scalatest" % "2.2.5" % "test",
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "com.typesafe.akka" %%  "akka-testkit" % akkaV % "test",
    "org.scala-lang" % "scala-library" % scalaV,
    "org.scala-lang" % "scala-compiler" % scalaV,
    "org.json4s" %% "json4s-native" % json4sV,
    "org.scalaz" %% "scalaz-core" % scalazV,
    "org.scalaz" %% "scalaz-concurrent" % scalazV,
    "io.spray" %% "spray-json" % "1.3.2",
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-caching" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-slf4j" % akkaV,
    "com.typesafe.akka" %% "akka-cluster" % akkaV,
    "com.typesafe" % "config" % "1.3.0",
    "net.debasishg" %% "redisclient" % "3.0",
    "mysql" % "mysql-connector-java" % "5.1.35",
    "it.unimi.dsi" % "fastutil" % "7.0.6",
    "ch.qos.logback" % "logback-classic" % "1.1.3"
  )
}

Revolver.enableDebugging(port = 9999, suspend = true)

javaOptions in Revolver.reStart += "-Xmx512m"

crossScalaVersions := Seq("2.10.4","2.10.5","2.11.6")

scalaBinaryVersion <<= scalaVersion(sV => if (CrossVersion.isStable(sV)) CrossVersion.binaryScalaVersion(sV) else sV)

mainClass in Compile := Some("org.dododa.demo.Main")

Revolver.settings : Seq[sbt.Setting[_]]

enablePlugins(JavaServerAppPackaging, JDebPackaging, RpmPlugin)

rpmVendor in Rpm := "typesafe"

linuxPackageMappings in Rpm := linuxPackageMappings.value

maintainer := "Chen Junfeng <cjf_junfeng@163.com>"

packageSummary := " Native Packager demo for scala"

packageDescription := """A fun package description of our software,with multiple lines."""

// Enable forking (see sbt docs) because our full build (including tests) uses many threads.
fork := true

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

scalacOptions in Compile ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature",  // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xlint", // Enable recommended additional warnings.
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code",
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
)

bashScriptConfigLocation := Some("${app_home}/../conf/jvmopts")

bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.conf""""

credentials += Credentials("Sonatype Nexus Repository Manager", "172.16.26.9", "admin", "admin123")

publishTo := {
  val nexus = "http://172.16.26.9:8081/"
  if (isSnapshot.value || version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "nexus/content/repositories/snapshots")
  else
    Some("releases"  at nexus + "nexus/content/repositories/Optimad/")
}

//// Note: If you need to pass options to the JVM used by sbt (i.e. the "parent" JVM), then you should modify `.sbtopts`.
//javaOptions in Universal ++= Seq(
//  "-source", "1.7",
//  "-target", "1.7",
//  "-Xlint:unchecked",
//  "-Xlint:deprecation",
//  "-Xms256m",
//  "-Xmx512m",
//  "-XX:+UseG1GC",
//  "-XX:MaxGCPauseMillis=50",
//  "-XX:InitiatingHeapOccupancyPercent=35",
//  "-Djava.awt.headless=true",
//  "-Djava.net.preferIPv4Stack=true"
//)

parallelExecution := true

testOptions in Test += Tests.Argument("-h","target/html-test-report")

testOptions in Test += Tests.Argument("-u","target/test-reports")

testOptions in Test += Tests.Argument("-o")
import sbt._
import scalariform.formatter.preferences._

scalariformPreferences := scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentConstructorArguments, true)
  .setPreference(DanglingCloseParenthesis, Preserve)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import"
)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.mycaule",
      scalaVersion := "2.11.12",
      version      := "0.5.0-SNAPSHOT"
    )),
    name := "Spark Sandbox",
    libraryDependencies ++= Seq(
      "org.apache.spark" %%  "spark-core" % "2.3.1",
      "org.apache.spark" %%  "spark-sql" % "2.3.1",
      "org.jsoup" %  "jsoup" % "1.11.2", // FIXME Deprecated
      "net.ruippeixotog" %% "scala-scraper" % "2.1.0",
      "com.fasterxml.jackson.dataformat" %  "jackson-dataformat-yaml" % "2.6.7",
      "org.scalatest" %%  "scalatest" % "3.0.5" % Test,
      "com.chuusai" %% "shapeless" % "2.3.3",
      "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
    ),
    resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
  )

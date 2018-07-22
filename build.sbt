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
  enablePlugins(BuildInfoPlugin).
  settings(
    inThisBuild(List(
      organization := "com.sandbox",
      scalaVersion := "2.11.12",
      version      := "0.5.0-SNAPSHOT"
    )),
    name := "spark-sandbox",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.sandbox",
    buildInfoOptions += BuildInfoOption.BuildTime,
    libraryDependencies ++= Seq(
      "org.apache.spark" %%  "spark-core" % "2.3.1",
      "org.apache.spark" %%  "spark-sql" % "2.3.1",
      "org.jsoup" %  "jsoup" % "1.11.2", // FIXME Deprecated
      "net.ruippeixotog" %% "scala-scraper" % "2.1.0",
      "io.circe" %% "circe-generic" % "0.9.3",
      "io.circe" %% "circe-yaml" % "0.8.0",
      "org.scalatest" %%  "scalatest" % "3.0.5" % Test,
      "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
      "com.chuusai" %% "shapeless" % "2.3.3"
    )
  )

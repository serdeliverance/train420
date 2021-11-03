import Dependencies._

ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.trains420"
ThisBuild / organizationName := "trains420"

lazy val root = (project in file("."))
  .settings(
    name := "trains420"
  )

val http4sVersion = "0.23.4"
val log4catsVersion = "2.1.1"
val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.2.9",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe"     %% "circe-generic-extras" % circeVersion,
  "eu.timepit" %% "fs2-cron-cron4s" % "0.7.1",
  "eu.timepit" %% "fs2-cron-calev" % "0.7.1",
  "com.github.pureconfig" %% "pureconfig" % "0.17.0",
  // logging
  "org.typelevel" %% "log4cats-core"    % log4catsVersion,
  "org.typelevel" %% "log4cats-slf4j"   % log4catsVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.5",
  // testing
  "org.mockito"            % "mockito-core"        % "3.5.13" % Test,
  "org.mockito"            %% "mockito-scala"        % "1.16.42" % Test,
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test,
  scalaTest % Test,
)

Test / parallelExecution := false

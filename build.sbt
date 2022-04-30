import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.trains420"
ThisBuild / organizationName := "trains420"

lazy val root = (project in file("."))
  .settings(
    name := "trains420"
  )

libraryDependencies ++= Seq(
  catsEffect,
  http4sDsl,
  http4sCirce,
  // TODO change for ember server
  http4sBlazeServer,
  circeGeneric,
  circeGenericExtras,
  // TODO review if fs2Cron and fs2CronCalev are really required (maybe CE timer brings the funcitonallity)
  fs2Cron,
  fs2CronCalev,
  pureConfig,
  log4cats,
  log4catsSlf4j,
  logback,
  // testing
  mockitoCore     % Test,
  mockitoScala    % Test,
  munitCatsEffect % Test,
  scalaTest       % Test
)

Test / parallelExecution := false

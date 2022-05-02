import sbt._

object Dependencies {

  object V {
    val catsEffect = "3.3.11"
    val http4s     = "0.23.4"
    val log4cats   = "2.3.0"
    val circe      = "0.14.1"
    val fs2Cron    = "0.7.1"
    val pureConfig = "0.17.0"
    val logback    = "1.2.5"

    val munitCatsEffect = "1.0.7"
    val scalaTest       = "3.2.12"
    val mockitoCore     = "3.5.13"
    val mockitoScala    = "1.16.42"
    val organizeImports = "0.6.0"
  }

  val catsEffect         = "org.typelevel"         %% "cats-effect"          % V.catsEffect
  val http4sDsl          = "org.http4s"            %% "http4s-dsl"           % V.http4s
  val http4sBlazeServer  = "org.http4s"            %% "http4s-blaze-server"  % V.http4s
  val http4sCirce        = "org.http4s"            %% "http4s-circe"         % V.http4s
  val circeGeneric       = "io.circe"              %% "circe-generic"        % V.circe
  val circeGenericExtras = "io.circe"              %% "circe-generic-extras" % V.circe
  val fs2Cron            = "eu.timepit"            %% "fs2-cron-cron4s"      % V.fs2Cron
  val fs2CronCalev       = "eu.timepit"            %% "fs2-cron-calev"       % V.fs2Cron
  val pureConfig         = "com.github.pureconfig" %% "pureconfig"           % V.pureConfig
  val organizeImports    = "com.github.liancheng"  %% "organize-imports"     % V.organizeImports
  val log4cats           = "org.typelevel"         %% "log4cats-core"        % V.log4cats
  val log4catsSlf4j      = "org.typelevel"         %% "log4cats-slf4j"       % V.log4cats
  val logback            = "ch.qos.logback"         % "logback-classic"      % V.logback
  // testing
  val mockitoCore     = "org.mockito"    % "mockito-core"        % V.mockitoCore
  val mockitoScala    = "org.mockito"   %% "mockito-scala"       % V.mockitoScala
  val munitCatsEffect = "org.typelevel" %% "munit-cats-effect-3" % V.munitCatsEffect
  val scalaTest       = "org.scalatest" %% "scalatest"           % V.scalaTest
}

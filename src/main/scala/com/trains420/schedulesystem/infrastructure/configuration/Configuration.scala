package com.trains420.schedulesystem.infrastructure.configuration

import cats.effect.IO
import pureconfig._
import pureconfig.generic.auto._

object Configuration {
  case class SchedulerConf(
    host: String,
    port: Int,
    terminalsCount: Int,
    linesCount: Int,
    schedulePeriod: String
  )

  def loadConfiguration: IO[SchedulerConf] =
    ConfigSource.default.load[SchedulerConf] match {
      case Right(conf) => IO(conf)
      case Left(err)   => IO.raiseError(new IllegalArgumentException(s"Error loading configuration. $err"))
    }
}

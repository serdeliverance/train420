package com.trains420.schedulesystem.infrastructure.jobs

import cats.effect.IO
import cron4s.Cron
import eu.timepit.fs2cron.cron4s.Cron4sScheduler
import fs2._

object CronedTaskFactory {

  def createCronTask(task: IO[Any], period: String) = {
    val cronScheduler = Cron4sScheduler.systemDefault[IO]
    val evenSeconds   = Cron.unsafeParse(period)
    val scheduled     = cronScheduler.awakeEvery(evenSeconds) >> Stream.eval(task)
    scheduled.compile.drain
  }
}

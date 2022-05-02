package com.trains420.schedulesystem

import cats.effect.{ ExitCode, IO, IOApp }
import com.trains420.schedulesystem.domain.entities.MinorDistanceLineResolver
import com.trains420.schedulesystem.domain.services.{ PickupService, SchedulerService, TrainStatusService }
import com.trains420.schedulesystem.application.config.Config._
import com.trains420.schedulesystem.infrastructure.jobs.CronedTaskFactory.createCronTask
import com.trains420.schedulesystem.infrastructure.loaders.{ LineLoader, TerminalLoader, TrainStatusLoader }
import com.trains420.schedulesystem.infrastructure.repositories.{
  InmemoryLineRepository,
  InmemoryPickupQueueRepository,
  InmemoryTerminalRepository,
  InmemoryTrainStatusRepository
}
import com.trains420.schedulesystem.infrastructure.routes.{ PickupRoutes, TrainRoutes }
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Server extends IOApp {

  def createServer = {

    implicit val logger = Slf4jLogger.getLogger[IO]

    val terminalRepository    = new InmemoryTerminalRepository
    val trainStatusRepository = new InmemoryTrainStatusRepository
    val lineRepository        = new InmemoryLineRepository
    val pickupRepository      = new InmemoryPickupQueueRepository

    val lineResolver = new MinorDistanceLineResolver

    val pickupService      = new PickupService(terminalRepository, lineRepository, pickupRepository, lineResolver)
    val trainStatusService = new TrainStatusService(trainStatusRepository)
    val schedulerService   = new SchedulerService(lineRepository, pickupRepository, trainStatusService)

    val pickupRoutes = new PickupRoutes(pickupService)
    val trainRoutes  = new TrainRoutes(trainStatusService)

    val httpApp = Router(
      "pickups" -> pickupRoutes.routes,
      "trains"  -> trainRoutes.routes
    ).orNotFound

    val terminalLoader    = new TerminalLoader(terminalRepository)
    val trainStatusLoader = new TrainStatusLoader(trainStatusRepository)
    val lineLoader        = new LineLoader(lineRepository, trainStatusRepository)

    for {
      config <- loadConfiguration
      _      <- terminalLoader.load(config.terminalsCount)
      _      <- trainStatusLoader.load(config.linesCount)
      _      <- lineLoader.load(config.linesCount, config.terminalsCount)
      _      <- createCronTask(schedulerService.schedule, config.schedulePeriod).start
      server <- BlazeServerBuilder[IO]
        .bindHttp(config.port, config.host)
        .withHttpApp(httpApp)
        .resource
        .use(_ => IO.never)
    } yield server
  }

  override def run(args: List[String]): IO[ExitCode] =
    createServer.as(ExitCode.Success)

}

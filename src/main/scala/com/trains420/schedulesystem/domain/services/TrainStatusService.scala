package com.trains420.schedulesystem.domain.services

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.TrainStatus
import com.trains420.schedulesystem.application.ports.out.TrainStatusRepository
import org.typelevel.log4cats.Logger

class TrainStatusService(trainStatusRepository: TrainStatusRepository)(implicit logger: Logger[IO]) {

  def getLatestStatuses: IO[List[TrainStatus]] =
    for {
      _              <- logger.info("Getting latest status of all trains")
      latestStatuses <- trainStatusRepository.getLatestStatuses
    } yield latestStatuses

  def save(status: TrainStatus): IO[TrainStatus] =
    for {
      _           <- logger.info(s"Save train status: $status")
      trainStatus <- trainStatusRepository.save(status)
    } yield trainStatus
}

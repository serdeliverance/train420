package com.trains420.schedulesystem.infrastructure.loaders

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.{Finished, Stopped, TrainStatus}
import com.trains420.schedulesystem.domain.repositories.TrainStatusRepository
import org.typelevel.log4cats.Logger

import cats.instances.list._
import cats.syntax.parallel._

class TrainStatusLoader(trainStatusRepository: TrainStatusRepository) {

  def load(count: Int)(implicit logger: Logger[IO]): IO[List[TrainStatus]] =
    logger.info("Load train statuses") *>
    (1 to count)
      .map(i => trainStatusRepository.save(TrainStatus(i, 0, 0, 0, Finished, Stopped)))
      .toList
      .parSequence
}

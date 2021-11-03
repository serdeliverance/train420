package com.trains420.schedulesystem.infrastructure.loaders

import cats.effect.IO
import cats.instances.list._
import cats.syntax.parallel._
import com.trains420.schedulesystem.domain.entities.{Line, Train}
import com.trains420.schedulesystem.domain.repositories.{LineRepository, TrainStatusRepository}
import org.typelevel.log4cats.Logger

class LineLoader(lineRepository: LineRepository, trainStatusRepository: TrainStatusRepository) {

  def load(linesCount: Int, terminalsCount: Int)(implicit logger: Logger[IO]) =
    logger.info("Loading lines") *>
    (1 to linesCount)
      .map(i => loadLine(i, terminalsCount))
      .toList
      .parSequence

  def loadLine(i: Int, terminalsCount: Int): IO[Line] =
    trainStatusRepository
      .getLastStatusByTrainId(i)
      .flatMap(status => lineRepository.save(Line(i, Train(i, s"Train ${i}"), status, terminalsCount)))
}

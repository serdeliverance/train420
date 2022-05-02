package com.trains420.schedulesystem.application.ports.out

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.TrainStatus

trait TrainStatusRepository {
  def getLatestStatuses: IO[List[TrainStatus]]
  def getAll: IO[List[TrainStatus]]
  def getLastStatusByTrainId(lineId: Int): IO[TrainStatus]
  def save(status: TrainStatus): IO[TrainStatus]

}

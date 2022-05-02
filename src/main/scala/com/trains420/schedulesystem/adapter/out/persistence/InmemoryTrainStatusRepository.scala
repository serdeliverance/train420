package com.trains420.schedulesystem.adapter.out.persistence

import java.util.concurrent.atomic.AtomicInteger

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.TrainStatus
import com.trains420.schedulesystem.application.ports.out.TrainStatusRepository

class InmemoryTrainStatusRepository extends TrainStatusRepository {

  private val lastId = new AtomicInteger(0)

  private val repository = new GenericInmemoryRepository[TrainStatus]

  private val nextId = () => Some(lastId.addAndGet(1)).map(_.toLong)

  override def getAll: IO[List[TrainStatus]] = repository.getAll

  override def save(status: TrainStatus): IO[TrainStatus] = repository.save(status.copy(id = nextId()))

  override def getLatestStatuses: IO[List[TrainStatus]] =
    repository.getAll.map(result =>
      result
        .filter(_.id.nonEmpty)
        .sortBy(_.id)
        .reverse
        .distinctBy(_.trainId)
    )

  override def getLastStatusByTrainId(trainId: Int): IO[TrainStatus] =
    repository.getAll.map(_.filter(_.trainId == trainId).sortBy(_.id).reverse.head)
}

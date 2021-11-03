package com.trains420.schedulesystem.infrastructure.repositories

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.PickupRequest
import com.trains420.schedulesystem.domain.repositories.PickupQueueRepository

import java.util.concurrent.atomic.AtomicInteger
import scala.collection.mutable.{Queue => MQueue}
import scala.util.Try

class InmemoryPickupQueueRepository extends PickupQueueRepository {

  private var linePickupQueueMap: Map[Int, MQueue[PickupRequest]] = Map.empty

  private var lastId = 0

  override def find(id: Long): IO[PickupRequest] =
    linePickupQueueMap.values.flatMap(_.toList).find(_.id.contains(id)) match {
      case Some(pickupRequest) => IO.pure(pickupRequest)
      case _                   => IO.raiseError(new IllegalArgumentException(s"Not found pickup with id: $id on repository"))
    }

  override def queue(lineId: Int, pickupRequest: PickupRequest): IO[PickupRequest] =
    linePickupQueueMap.get(lineId) match {
      case Some(queue) =>
        lastId += 1
        val pickupToInsert = pickupRequest.copy(id = Some(lastId))
        queue.enqueue(pickupToInsert)
        IO(pickupToInsert)
      case _ =>
        linePickupQueueMap = linePickupQueueMap + (lineId -> new MQueue[PickupRequest])
        queue(lineId, pickupRequest)
    }

  override def dequeue(lineId: Int): IO[Option[PickupRequest]] =
    linePickupQueueMap.get(lineId) match {
      case Some(queue) => IO(Try(queue.dequeue).toOption)
      case None        => IO.pure(None)
    }
}

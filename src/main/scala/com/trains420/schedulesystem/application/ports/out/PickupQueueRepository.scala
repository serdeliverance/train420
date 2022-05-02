package com.trains420.schedulesystem.application.ports.out

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.PickupRequest

trait PickupQueueRepository {

  def find(id: Long): IO[PickupRequest]
  def queue(lineId: Int, pickupRequest: PickupRequest): IO[PickupRequest]
  def dequeue(lineId: Int): IO[Option[PickupRequest]]

}

package com.trains420.schedulesystem.adapter.out.persistence

import com.trains420.schedulesystem.domain.entities.PickupRequest
import com.trains420.schedulesystem.utils.TestIOUtils
import munit.CatsEffectSuite

class InmemoryPickupQueueRepositorySpec extends CatsEffectSuite with TestIOUtils {

  test("find on queue success") {

    val pickupQueueRepository = new InmemoryPickupQueueRepository

    for {
      _      <- pickupQueueRepository.queue(1, PickupRequest(1, 2, 6))
      result <- pickupQueueRepository.find(1)
    } yield assert(result.id.contains(1))
  }

  test("not found on queue must fail") {
    val pickupQueueRepository = new InmemoryPickupQueueRepository

    val findResult = evalUnsafeSync {
      for {
        _      <- pickupQueueRepository.queue(1, PickupRequest(1, 2, 6))
        result <- pickupQueueRepository.find(4)
      } yield result
    }

    assertEquals(findResult.isFailure, true)

  }

  test("queue pickup when lineId is already set on map") {
    val pickupQueueRepository = new InmemoryPickupQueueRepository

    val result = pickupQueueRepository.queue(1, PickupRequest(1, 2, 6))
    result.map(pickup => assert(pickup.id.contains(1)))
  }

  test("dequeue success") {
    val pickupQueueRepository = new InmemoryPickupQueueRepository

    for {
      _              <- pickupQueueRepository.queue(1, PickupRequest(1, 2, 6))
      dequeuedPickup <- pickupQueueRepository.dequeue(1)
    } yield assert(dequeuedPickup.nonEmpty)
  }

  test("dequeue when line id is not backend by repository must return None") {
    val pickupQueueRepository = new InmemoryPickupQueueRepository

    for {
      _      <- pickupQueueRepository.queue(2, PickupRequest(1, 2, 6))
      _      <- pickupQueueRepository.queue(3, PickupRequest(1, 2, 7))
      result <- pickupQueueRepository.dequeue(1)

    } yield assert(result.isEmpty)
  }

  test("dequeue when there is no elements must return None") {
    val pickupQueueRepository = new InmemoryPickupQueueRepository

    val result = pickupQueueRepository.dequeue(1)

    result.map(pickup => assert(pickup.isEmpty))
  }
}

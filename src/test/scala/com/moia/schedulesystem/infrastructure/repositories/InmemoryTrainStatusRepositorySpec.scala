package com.trains420.schedulesystem.adapter.out.persistence

import com.trains420.schedulesystem.stubs.TrainStubs
import munit.CatsEffectSuite

class InmemoryTrainStatusRepositorySpec extends CatsEffectSuite with TrainStubs {

  private var trainStatusRepository: InmemoryTrainStatusRepository = _

  test("Save train status success") {
    cleanUp()

    val result = trainStatusRepository.save(aTrainStatus.copy(trainId = 1))

    result.map(persistedTrainStatus => assert(persistedTrainStatus.id.contains(1)))
  }

  test("Retrieve all train status") {
    cleanUp()

    for {
      _      <- trainStatusRepository.save(aTrainStatus.copy(trainId = 1))
      _      <- trainStatusRepository.save(aTrainStatus.copy(trainId = 2))
      _      <- trainStatusRepository.save(aTrainStatus.copy(trainId = 3))
      result <- trainStatusRepository.getAll
    } yield assert(result.length == 3)
  }

  test("Retrieve all train status return empty when no status were loaded") {
    cleanUp()

    val result = trainStatusRepository.getAll

    result.map(statuses => assert(statuses.isEmpty))
  }

  test("Retrieve all latest train status") {
    cleanUp()

    val firstStatusTrain1   = aTrainStatus.copy(trainId = 1, currentLocation = 2)
    val lastestStatusTrain1 = aTrainStatus.copy(trainId = 1, currentLocation = 5)

    val result = for {
      _      <- trainStatusRepository.save(firstStatusTrain1)
      _      <- trainStatusRepository.save(aTrainStatus.copy(trainId = 2))
      _      <- trainStatusRepository.save(aTrainStatus.copy(trainId = 3))
      _      <- trainStatusRepository.save(lastestStatusTrain1)
      result <- trainStatusRepository.getLatestStatuses

    } yield result

    result.map(lastStatuses => {
      assert(lastStatuses.length == 3)
      assert(lastStatuses.find(_.trainId == 1).get.currentLocation == 5)
    })
  }

  test("Get last train status by train id") {
    cleanUp()

    val firstStatusTrain1   = aTrainStatus.copy(trainId = 1, currentLocation = 2)
    val lastestStatusTrain1 = aTrainStatus.copy(trainId = 1, currentLocation = 5)

    for {
      _      <- trainStatusRepository.save(firstStatusTrain1)
      _      <- trainStatusRepository.save(aTrainStatus.copy(trainId = 2))
      _      <- trainStatusRepository.save(aTrainStatus.copy(trainId = 3))
      _      <- trainStatusRepository.save(lastestStatusTrain1)
      result <- trainStatusRepository.getLastStatusByTrainId(1)

    } yield assert(result.currentLocation == 5)
  }

  def cleanUp() = trainStatusRepository = new InmemoryTrainStatusRepository
}

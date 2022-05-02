package com.trains420.schedulesystem.domain.services

import cats.effect.IO
import cats.implicits._
import com.trains420.schedulesystem.domain.entities.Direction.resolveDirection
import com.trains420.schedulesystem.domain.entities._
import com.trains420.schedulesystem.application.ports.out.{ LineRepository, PickupQueueRepository }
import org.typelevel.log4cats.Logger

class SchedulerService(
    lineRepository: LineRepository,
    pickupRepository: PickupQueueRepository,
    statusService: TrainStatusService
)(implicit logger: Logger[IO]) {

  def schedule: IO[List[Line]] =
    for {
      _            <- logger.info("scheduling trains and updating train locations")
      lines        <- lineRepository.getAll
      updatedLines <- lines.traverse(updateAndScheduleLine)
    } yield updatedLines

  private def updateAndScheduleLine(line: Line): IO[Line] = {
    val trainWithUpdatedPosition = line.updateTrainPosition()
    val result =
      if (trainWithUpdatedPosition.currentLocation == trainWithUpdatedPosition.destinyLocation)
        updatePickupStatus(trainWithUpdatedPosition, line)
      else IO(trainWithUpdatedPosition)

    result.map(newTrainStatus => line.copy(trainStatus = newTrainStatus))
  }

  def updatePickupStatus(currentTrainStatus: TrainStatus, line: Line): IO[TrainStatus] =
    for {
      _ <- logger.info(
        s"train: ${line.train.id} of line: ${line.id} arrive to destinity position: terminal ${currentTrainStatus.currentLocation} on stage: ${currentTrainStatus.rideStatus} of ride: ${currentTrainStatus.pickupId}"
      )
      pickup <- pickupRepository.find(currentTrainStatus.pickupId)
      updatedRideStatus <- currentTrainStatus.rideStatus match {
        case PickingUp =>
          IO.pure(
            currentTrainStatus.copy(
              rideStatus = Riding,
              destinyLocation = pickup.to,
              direction = resolveDirection(currentTrainStatus.currentLocation, pickup.to)
            )
          )
        case Riding =>
          IO.pure(currentTrainStatus.copy(rideStatus = Finished))
        case _ => IO.raiseError[TrainStatus](new IllegalArgumentException("Invalid pickup status when updating status"))
      }
      _               <- statusService.save(updatedRideStatus)
      maybeNextPickup <- dequeNextPickupConditionally(line.id, updatedRideStatus.rideStatus == Finished)
      updatedStatus <- maybeNextPickup match {
        case Some(nextPickup) =>
          val finalStatus = updatedRideStatus.copy(
            pickupId = nextPickup.id.get,
            destinyLocation = nextPickup.from,
            rideStatus = PickingUp,
            direction = resolveDirection(updatedRideStatus.currentLocation, nextPickup.to)
          )
          logger.info(
            s"train: ${line.train.id} of line: ${line.id} will attend pickup request: $nextPickup. Current status: $finalStatus"
          ) *> statusService
            .save(finalStatus)
        case None => logger.info(s"line: ${line.id} current status: $updatedRideStatus") *> IO(updatedRideStatus)
      }
    } yield updatedStatus

  private def dequeNextPickupConditionally(lineId: Int, condition: Boolean): IO[Option[PickupRequest]] =
    if (condition)
      logger.info(s"Dequeue next pickup for line: $lineId") *> pickupRepository.dequeue(lineId)
    else IO(None)

}

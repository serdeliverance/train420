package com.trains420.schedulesystem.domain.entities

case class TrainStatus(
  trainId: Long,
  pickupId: Long,
  currentLocation: Int,
  destinyLocation: Int,
  rideStatus: RideStatus,
  direction: Direction,
  id: Option[Long] = None
) {
  def updatePosition(bound: Int): TrainStatus = {
    val newLocation = this.direction match {
      case Stopped => currentLocation
      case Back    => currentLocation - 1
      case Forth   => currentLocation + 1
    }

    if (newLocation > 0 && newLocation <= bound) this.copy(currentLocation = newLocation)
    else if (newLocation > 0) this.copy(currentLocation = bound, direction = Stopped)
    else this.copy(currentLocation = 0, direction = Stopped)
  }

  def distance(terminal: Terminal): Int =
    Math.abs(currentLocation - terminal.location)
}

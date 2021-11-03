package com.trains420.schedulesystem.domain.entities

sealed trait RideStatus

case object PickingUp extends RideStatus
case object Riding    extends RideStatus
case object Finished  extends RideStatus

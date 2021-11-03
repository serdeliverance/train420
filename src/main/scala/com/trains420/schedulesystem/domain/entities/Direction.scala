package com.trains420.schedulesystem.domain.entities

sealed trait Direction

case object Stopped extends Direction
case object Back    extends Direction
case object Forth   extends Direction

object Direction {
  def resolveDirection(currentLocation: Int, destinyLocation: Int): Direction = {
    if (currentLocation == destinyLocation) Stopped
    if (currentLocation < destinyLocation) Forth
    else Back
  }
}

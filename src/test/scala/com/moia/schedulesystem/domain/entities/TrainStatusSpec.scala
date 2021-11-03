package com.trains420.schedulesystem.domain.entities

import org.scalatest.funsuite.AnyFunSuite

class TrainStatusSpec extends AnyFunSuite {
  test("Position must be updated successfully") {
    val currentPosition = 1
    val destinyPosition = 3
    val trainStatus     = TrainStatus(1, 1, currentPosition, destinyPosition, Riding, Forth)

    val result = trainStatus.updatePosition(5)

    assert(result.currentLocation == 2)
    assert(result.direction == Forth)
  }

  test("Going back direction success") {
    val currentPosition = 5
    val destinyPosition = 3
    val trainStatus     = TrainStatus(1, 1, currentPosition, destinyPosition, Riding, Back)

    val result = trainStatus.updatePosition(5)

    assert(result.currentLocation == 4)
    assert(result.direction == Back)
  }

  test("position must not be updated when train is stopped") {
    val trainStatus = TrainStatus(1, 1, 1, 1, Riding, Stopped)

    val result = trainStatus.updatePosition(5)

    assert(result.direction == Stopped)
  }

  test("when attempt to moving on negative rail position it should stop train and set it into 0 position") {
    val trainStatus = TrainStatus(1, 1, 0, 1, Riding, Back)

    val status = trainStatus.updatePosition(5)

    assert(status.direction == Stopped)
    assert(status.currentLocation == 0)
  }

  test(
    "when attempt to moving further than max terminal (upper bound) it should stop train and set it into bound position"
  ) {
    val trainStatus = TrainStatus(1, 1, 5, 1, Riding, Forth)

    val result = trainStatus.updatePosition(5)

    assert(result.direction == Stopped)
    assert(result.currentLocation == 5)
  }

  test("Distance from current to terminal on back direction") {
    val currentPosition = 5
    val trainStatus     = TrainStatus(1, 1, currentPosition, 1, Riding, Forth)
    val terminal        = Terminal(3, "terminal", 3)

    val result = trainStatus.distance(terminal)

    assert(result == 2)
  }

  test("Distance from current to terminal on forth direction") {
    val currentPosition = 3
    val trainStatus     = TrainStatus(1, 1, currentPosition, 1, Riding, Forth)
    val terminal        = Terminal(1, "terminal", 5)

    val result = trainStatus.distance(terminal)

    assert(result == 2)
  }

  test("Distance to a terminal which is in the same position as current train position") {
    val currentPosition = 1
    val trainStatus     = TrainStatus(1, 1, currentPosition, 1, Riding, Forth)
    val terminal        = Terminal(1, "terminal", currentPosition)

    val result = trainStatus.distance(terminal)

    assert(result == 0)
  }
}

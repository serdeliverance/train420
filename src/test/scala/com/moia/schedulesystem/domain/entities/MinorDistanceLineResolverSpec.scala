package com.trains420.schedulesystem.domain.entities

import com.trains420.schedulesystem.utils.RandomUtils
import org.scalatest.funsuite.AnyFunSuite

class MinorDistanceLineResolverSpec extends AnyFunSuite with RandomUtils {

  test("minor distance resolver must choose right line for pickup request") {
    val lineResolver = new MinorDistanceLineResolver

    val terminal = Terminal(1, "terminal", 1)

    val lines = (3 to 5)
      .map(i => {
        val status = TrainStatus(i, randomInt(), i, 0, Riding, Back)
        Line(i, Train(randomInt(), "train"), status, 5)
      })
      .toList

    val result = lineResolver.chooseLine(terminal, lines)

    assert(result.id == 3)

  }
}

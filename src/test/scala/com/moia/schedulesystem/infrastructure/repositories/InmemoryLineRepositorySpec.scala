package com.trains420.schedulesystem.infrastructure.repositories

import com.trains420.schedulesystem.domain.entities._
import munit.CatsEffectSuite

class InmemoryLineRepositorySpec extends CatsEffectSuite {

  private var lineRepository: InmemoryLineRepository = _

  test("create line successfully") {
    cleanUp()

    val line   = Line(1, Train(1, "train 1"), TrainStatus(1, 1, 0, 0, Finished, Forth, None), 4)
    val result = lineRepository.save(line)

    result.map(createdLine => assertEquals(createdLine, line))
  }

  test("retrieve lines successfully") {
    cleanUp()

    (1 to 3).foreach(
      i => lineRepository.save(Line(i, Train(i, s"train ${i}"), TrainStatus(1, 1, 0, 0, Finished, Forth, None), 4))
    )

    lineRepository.getAll.map(lines => assertEquals(lines.length, 3))
  }

  def cleanUp() = lineRepository = new InmemoryLineRepository
}

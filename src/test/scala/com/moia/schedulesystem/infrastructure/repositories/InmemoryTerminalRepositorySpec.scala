package com.trains420.schedulesystem.adapter.out.persistence

import com.trains420.schedulesystem.domain.entities._
import munit.CatsEffectSuite

class InmemoryTerminalRepositorySpec extends CatsEffectSuite {
  private var terminalRepository: InmemoryTerminalRepository = _

  test("create terminal successfully") {
    cleanUp()

    val terminal = Terminal(1, "Terminal 1", 0)
    val result   = terminalRepository.save(terminal)

    result.map(createdTerminal => assertEquals(createdTerminal, terminal))
  }

  test("find terminal successfully") {
    cleanUp()

    val terminal = Terminal(1, "Terminal 1", 0)
    terminalRepository.save(terminal)

    terminalRepository.find(1).map(foundTerminal => assertEquals(foundTerminal, terminal))
  }

  private def cleanUp() =
    terminalRepository = new InmemoryTerminalRepository
}

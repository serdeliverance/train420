package com.trains420.schedulesystem.infrastructure.loaders

import cats.effect.IO
import cats.instances.list._
import cats.syntax.parallel._
import com.trains420.schedulesystem.domain.entities.Terminal
import com.trains420.schedulesystem.application.ports.out.TerminalRepository
import org.typelevel.log4cats.Logger

// TODO delete or move to another layer
class TerminalLoader(terminalRepository: TerminalRepository) {

  def load(terminalsCount: Int)(implicit logger: Logger[IO]) =
    logger.info("Loading terminals") *>
      (1 to terminalsCount)
        .map(i => Terminal(i, s"Terminal ${i}", i - 1))
        .map(terminal => terminalRepository.save(terminal))
        .toList
        .parSequence
}

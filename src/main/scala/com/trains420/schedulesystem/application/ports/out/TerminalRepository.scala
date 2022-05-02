package com.trains420.schedulesystem.application.ports.out

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.Terminal

trait TerminalRepository {

  def save(terminal: Terminal): IO[Terminal]
  def find(id: Int): IO[Terminal]

}

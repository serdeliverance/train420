package com.trains420.schedulesystem.domain.repositories

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.Terminal

trait TerminalRepository {

  def save(terminal: Terminal): IO[Terminal]
  def find(id: Int): IO[Terminal]

}

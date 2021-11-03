package com.trains420.schedulesystem.infrastructure.repositories

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.Terminal
import com.trains420.schedulesystem.domain.repositories.TerminalRepository

class InmemoryTerminalRepository extends TerminalRepository {

  private val repository = new GenericInmemoryRepository[Terminal]

  override def save(terminal: Terminal): IO[Terminal] = repository.save(terminal)

  override def find(id: Int): IO[Terminal] = repository.getAll.flatMap {
    _.find(_.id == id) match {
      case Some(terminal) => IO.pure(terminal)
      case _ =>
        IO.raiseError(new IllegalArgumentException(s"Terminal with id: $id does not exists")) // I know it is ugly, but this case will not happen on this challenge
    }
  }
}

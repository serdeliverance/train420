package com.trains420.schedulesystem.adapter.out.persistence

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.Line
import com.trains420.schedulesystem.application.ports.out.LineRepository

class InmemoryLineRepository extends LineRepository {

  private val repository = new GenericInmemoryRepository[Line]

  override def getAll: IO[List[Line]] = repository.getAll

  override def save(line: Line): IO[Line] = repository.save(line)
}

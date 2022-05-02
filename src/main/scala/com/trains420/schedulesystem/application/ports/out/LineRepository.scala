package com.trains420.schedulesystem.application.ports.out

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.Line

trait LineRepository {

  def save(line: Line): IO[Line]

  def getAll: IO[List[Line]]

}

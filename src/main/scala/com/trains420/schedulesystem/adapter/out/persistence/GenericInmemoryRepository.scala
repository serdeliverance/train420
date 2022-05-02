package com.trains420.schedulesystem.adapter.out.persistence

import cats.effect.IO

class GenericInmemoryRepository[T] {

  private var entities: List[T] = List.empty

  def getAll: IO[List[T]] = IO.pure(entities)

  def save(entity: T): IO[T] = {
    entities = entity :: entities
    IO.pure(entity)
  }
}

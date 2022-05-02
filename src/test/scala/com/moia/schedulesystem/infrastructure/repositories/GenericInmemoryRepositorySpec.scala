package com.trains420.schedulesystem.adapter.out.persistence

import munit.CatsEffectSuite

class GenericInmemoryRepositorySpec extends CatsEffectSuite {

  final case class DummyEntity(id: Int, name: String)

  private var repository: GenericInmemoryRepository[DummyEntity] = _

  test("create entity successfully") {
    cleanUp()

    val entity = DummyEntity(1, "name 1")
    val result = repository.save(entity)

    result.map(createdEntity => assertEquals(createdEntity, entity))
  }

  test("retrieve entities successfully") {
    cleanUp()

    (1 to 3).foreach(i => repository.save(DummyEntity(i, s"name ${i}")))

    repository.getAll.map(entities => assertEquals(entities.length, 3))
  }

  def cleanUp() = repository = new GenericInmemoryRepository[DummyEntity]
}

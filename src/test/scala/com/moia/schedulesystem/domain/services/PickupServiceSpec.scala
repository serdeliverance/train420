package com.trains420.schedulesystem.domain.services

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.trains420.schedulesystem.domain.entities.{ LineResolver, PickupRequest }
import com.trains420.schedulesystem.application.ports.out.{ LineRepository, PickupQueueRepository, TerminalRepository }
import com.trains420.schedulesystem.stubs.TrainStubs
import com.trains420.schedulesystem.utils.TestIOUtils
import org.mockito.MockitoSugar
import org.scalatest.funsuite.AnyFunSuite
import org.typelevel.log4cats.slf4j.Slf4jLogger

class PickupServiceSpec extends AnyFunSuite with TrainStubs with MockitoSugar with TestIOUtils {

  // Dependencies
  implicit val logger = Slf4jLogger.getLogger[IO]

  val terminalRepository = mock[TerminalRepository]
  val lineRepository     = mock[LineRepository]
  val pickupRepository   = mock[PickupQueueRepository]
  val lineResolver       = mock[LineResolver]

  // Subject
  private val pickupService: PickupService =
    new PickupService(terminalRepository, lineRepository, pickupRepository, lineResolver)

  test("Pickup creation success") {

    val pickupRequest = PickupRequest(1, 2, 4)
    val selectedLine  = lines.head

    when(terminalRepository.find(1)).thenReturn(IO.pure(aTerminal))
    when(lineRepository.getAll).thenReturn(IO.pure(lines))
    when(lineResolver.chooseLine(aTerminal, lines)).thenReturn(selectedLine)
    when(pickupRepository.queue(selectedLine.id, pickupRequest)).thenReturn(IO(pickupRequest.copy(id = Some(1))))

    val result = pickupService.registerPickup(pickupRequest)

    result.map(createdPickupRequest => assert(createdPickupRequest.id.contains(1)))
  }

  test("Pickup creation fail") {
    val pickupRequest = PickupRequest(1, 2, 4)
    val selectedLine  = lines.head

    when(terminalRepository.find(1)).thenReturn(IO.pure(aTerminal))
    when(lineRepository.getAll).thenReturn(IO.pure(lines))
    when(lineResolver.chooseLine(aTerminal, lines)).thenReturn(selectedLine)
    when(pickupRepository.queue(selectedLine.id, pickupRequest))
      .thenReturn(IO.raiseError(new IllegalArgumentException("error connecting with DB")))

    val result = evalUnsafeSync(pickupService.registerPickup(pickupRequest))

    assert(result.isFailure == true)
  }
}

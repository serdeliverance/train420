package com.trains420.schedulesystem.domain.services

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.{LineResolver, PickupRequest}
import com.trains420.schedulesystem.domain.repositories.{LineRepository, PickupQueueRepository, TerminalRepository}
import org.typelevel.log4cats.Logger

class PickupService(
  terminalRepository: TerminalRepository,
  lineRepository: LineRepository,
  pickupRepository: PickupQueueRepository,
  lineResolver: LineResolver,
)(implicit logger: Logger[IO]) {

  def registerPickup(request: PickupRequest): IO[PickupRequest] =
    for {
      _            <- logger.info(s"Registering pickup request: $request")
      fromTerminal <- terminalRepository.find(request.from)
      lines        <- lineRepository.getAll
      selectedLine = lineResolver.chooseLine(fromTerminal, lines)
      pickup <- pickupRepository.queue(selectedLine.id, request)
      _      <- logger.info(s"line: ${selectedLine.id} will take pickup request: ${pickup.id}")
    } yield pickup
}

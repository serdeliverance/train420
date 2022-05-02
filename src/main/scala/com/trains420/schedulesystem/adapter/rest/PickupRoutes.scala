package com.trains420.schedulesystem.adapter.rest

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.PickupRequest
import com.trains420.schedulesystem.domain.services.PickupService
import io.circe.generic.auto._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.{ EntityDecoder, HttpRoutes }

class PickupRoutes(pickupService: PickupService) {
  implicit val orderDecoder: EntityDecoder[IO, PickupRequest] = jsonOf

  def routes =
    HttpRoutes.of[IO] { case req @ POST -> Root =>
      for {
        pickupRequest <- req.as[PickupRequest]
        _             <- pickupService.registerPickup(pickupRequest)
        resp          <- Created()
      } yield resp
    }
}

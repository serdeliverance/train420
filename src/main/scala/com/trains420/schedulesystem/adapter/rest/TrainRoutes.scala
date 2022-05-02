package com.trains420.schedulesystem.adapter.rest

import cats.effect.IO
import com.trains420.schedulesystem.domain.entities.TrainStatus
import com.trains420.schedulesystem.domain.services.TrainStatusService
import com.trains420.schedulesystem.infrastructure.json.JsonParsing._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.{ EntityDecoder, HttpRoutes }

class TrainRoutes(trainStatusService: TrainStatusService) {
  implicit val orderDecoder: EntityDecoder[IO, TrainStatus] = jsonOf

  def routes =
    HttpRoutes.of[IO] {
      case GET -> Root / "status" =>
        trainStatusService.getLatestStatuses
          .flatMap(latestStatuses => Ok(latestStatuses.asJson))
      case req @ POST -> Root / IntVar(trainId) / "status" =>
        for {
          newStatus <- req.as[TrainStatus]
          _         <- validation(trainId)
          _         <- trainStatusService.save(newStatus)
          resp      <- Ok()
        } yield resp
    }

  // TODO implement validation
  private def validation(trainId: Int): IO[Unit] = IO.pure(())
}

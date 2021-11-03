package com.trains420.schedulesystem.infrastructure.json

import com.trains420.schedulesystem.domain.entities._
import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}

object JsonParsing {

  implicit val customPrinter: Printer      = Printer.noSpaces.copy(dropNullValues = true)
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

  implicit val encoder: Encoder[TrainStatus] = deriveConfiguredEncoder
  implicit val decoder: Decoder[TrainStatus] = deriveConfiguredDecoder

  implicit val decodeRideStatus: Decoder[RideStatus] = Decoder[String].emap {
    case "picking up" => Right(PickingUp)
    case "riding"     => Right(Riding)
    case "finished"   => Right(Finished)
    case _            => Left(s"Invalid ride status")
  }

  implicit val encodeRideStatus: Encoder[RideStatus] = Encoder[String].contramap {
    case PickingUp => "picking up"
    case Riding    => "riding"
    case Finished  => "finished"
  }

  implicit val decodeDirection: Decoder[Direction] = Decoder[String].emap {
    case "stopped" => Right(Stopped)
    case "back"    => Right(Back)
    case "forth"   => Right(Forth)
    case _         => Left(s"Invalid direction")
  }

  implicit val encodeDirection: Encoder[Direction] = Encoder[String].contramap {
    case Stopped => "stopped"
    case Back    => "back"
    case Forth   => "forth"
  }
}

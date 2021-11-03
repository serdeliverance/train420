package com.trains420.schedulesystem.domain.entities

case class PickupRequest(
  from: Int,
  to: Int,
  createdAt: Int,
  passenger: String = "anonymous passenger",
  id: Option[Long] = None
)

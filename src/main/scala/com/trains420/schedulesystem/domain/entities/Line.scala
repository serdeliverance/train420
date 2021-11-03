package com.trains420.schedulesystem.domain.entities

case class Line(
  id: Int,
  train: Train,
  trainStatus: TrainStatus,
  terminalsCount: Int
) {
  def updateTrainPosition() = trainStatus.updatePosition(terminalsCount)

}

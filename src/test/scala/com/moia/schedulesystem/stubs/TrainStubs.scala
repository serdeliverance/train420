package com.trains420.schedulesystem.stubs

import com.trains420.schedulesystem.domain.entities._

trait TrainStubs {
  val aTrainStatus = TrainStatus(1, 1, 1, 3, Riding, Forth)

  val aTerminal = Terminal(1, "Terminal 1", 0)

  val lines = List(
    Line(1, Train(1, "train 1"), TrainStatus(1, 1, 0, 0, Finished, Forth, None), 4),
    Line(2, Train(2, "train 2"), TrainStatus(1, 1, 2, 0, Finished, Forth, None), 4),
    Line(2, Train(2, "train 3"), TrainStatus(1, 1, 3, 0, Finished, Forth, None), 4)
  )
}

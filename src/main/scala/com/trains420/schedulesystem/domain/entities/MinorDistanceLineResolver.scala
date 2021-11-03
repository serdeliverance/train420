package com.trains420.schedulesystem.domain.entities

/**
  * Strategy implementation that choose the line which has the nearest train to the pickup terminal.
  */
class MinorDistanceLineResolver extends LineResolver {
  override def chooseLine(terminal: Terminal, lines: List[Line]): Line =
    lines.reduce(
      (line1, line2) =>
        if (line1.trainStatus.distance(terminal) < line2.trainStatus.distance(terminal)) line1 else line2
    )

}

package com.trains420.schedulesystem.domain.entities

trait LineResolver {
  def chooseLine(terminal: Terminal, lines: List[Line]): Line

}

package com.trains420.schedulesystem.utils

import scala.util.Random

trait RandomUtils {

  private val random = new Random

  // it is not pure, I know
  def randomInt(bound: Int = 10) = random.nextInt(bound)
}

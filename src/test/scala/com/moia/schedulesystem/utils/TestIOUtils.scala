package com.trains420.schedulesystem.utils

import cats.effect.IO
import cats.effect.unsafe.IORuntime

import scala.util.Try

trait TestIOUtils {

  def evalUnsafeSync[T](comp: IO[T])(implicit runtime: IORuntime): Try[T] =
    Try(comp.unsafeRunSync())

}

package com.trains420.schedulesystem.utils

import scala.util.Try

import cats.effect.IO
import cats.effect.unsafe.IORuntime

trait TestIOUtils {

  def evalUnsafeSync[T](comp: IO[T])(implicit runtime: IORuntime): Try[T] =
    Try(comp.unsafeRunSync())

}

package flatmap

import cats.effect._
import fs2._
import cats.implicits._

object FlatMapExample extends IOApp {
  val source: Stream[IO, Int] = Stream[IO, Int](0 to 100: _*)

  override def run(args: List[String]): IO[ExitCode] =
    source
      .flatMap(i => Stream(i - 1, i, i + 1))
      .showLinesStdOut
      .compile
      .drain
      .as(ExitCode.Success)
}

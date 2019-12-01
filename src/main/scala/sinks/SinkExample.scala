package sinks

import cats.effect.{ExitCode, IO, IOApp}
import fs2._
import cats.implicits._

object SinkExample extends IOApp {
  val source: Stream[IO, Int] = Stream[IO, Int](0 to 100: _*)

  val sink: Pipe[IO, Int, Unit] =
    _.evalMap(i => IO(println(i)))

  override def run(args: List[String]): IO[ExitCode] =
    source
      .through(sink)
      .compile
      .drain
      .as(ExitCode.Success)
}

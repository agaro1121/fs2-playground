package parallel

import cats.effect._

import scala.concurrent.duration._
import scala.language.postfixOps
import fs2._
import cats.implicits._

object ParallelExample extends IOApp {
  val source: Stream[IO, Int] = Stream(1 to 100: _*)
  val slowOperation: IO[Unit] = IO.sleep(1 second)

  val startTime = System.currentTimeMillis()

  override def run(args: List[String]): IO[ExitCode] =
    source
      .mapAsync(Runtime.getRuntime.availableProcessors() * 4) {
        i =>
          slowOperation.as(i)
      }
      .showLinesStdOut
      .compile
      .drain
      .guarantee(IO(println(s"Time taken: ${(System.currentTimeMillis() - startTime) / 1000 seconds}")))
      .as(ExitCode.Success)
}

object ParallelExample2 extends IOApp {
  val source: Stream[IO, Int] = Stream(1 to 100: _*)
  val slowOperation: IO[Unit] = IO.sleep(1 second)
  val startTime = System.currentTimeMillis()

  override def run(args: List[String]): IO[ExitCode] =
    source
      .mapAsyncUnordered(Runtime.getRuntime.availableProcessors() * 4) {
        i =>
          slowOperation.as(i)
      }
      .showLinesStdOut
      .compile
      .drain
      .guarantee(IO(println(s"Time taken: ${(System.currentTimeMillis() - startTime) / 1000 seconds}")))
      .as(ExitCode.Success)
}


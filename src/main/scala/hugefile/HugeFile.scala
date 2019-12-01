package hugefile

import java.nio.file.Paths

import cats.effect.{Blocker, ExitCode, IO, IOApp}
import fs2._
import cats.implicits._

import scala.concurrent.duration._
import scala.language.postfixOps

object HugeFile extends IOApp {

  val source: Stream[IO, Byte] =
    Stream.resource(Blocker[IO]).flatMap { blocker =>
      io.file.readAll[IO](
        Paths.get("src/main/resources/bitcoinData.csv"),
        blocker,
        4096
      )
    }


  override def run(args: List[String]): IO[ExitCode] = {
    val startTime = System.currentTimeMillis()
    source
      .through(text.utf8Decode)
      .through(text.lines)
      .handleErrorWith(t => Stream(s"Problem fetching file: ${t.getMessage}"))
      .showLinesStdOut
      .compile
      .drain
      .guarantee(IO(println(s"Time taken: ${(System.currentTimeMillis() - startTime) / 1000 seconds}")))
      .as(ExitCode.Success)
  }
}

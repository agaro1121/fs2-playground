name := "fs2-playground"

version := "0.1"

scalaVersion := "2.12.10"

// available for Scala 2.11, 2.12, 2.13
libraryDependencies += "co.fs2" %% "fs2-core" % "2.1.0" // For cats 2 and cats-effect 2

// optional I/O library
libraryDependencies += "co.fs2" %% "fs2-io" % "2.1.0"

// optional reactive streams interop
libraryDependencies += "co.fs2" %% "fs2-reactive-streams" % "2.1.0"
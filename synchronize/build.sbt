import scalariform.formatter.preferences._

name := """synchronize"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.4"

lazy val root = project in file(".")

libraryDependencies ++= Seq(
  "net.debasishg" %% "redisclient" % "2.13",
  "io.spray" %% "spray-json" % "1.3.1",
  "org.scalaj" %% "scalaj-http" % "1.1.0"
)

mainClass in (Compile, run) := Some("Synchronize")
scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
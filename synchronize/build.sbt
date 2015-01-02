import scalariform.formatter.preferences._

name := "synchronize"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "io.spray"     %% "spray-json"  % "1.3.1",
  "org.scalaj"   %% "scalaj-http" % "1.1.0",
  "com.chuusai"  %% "shapeless"   % "2.0.0",
  "com.typesafe" %  "config"      % "1.2.1"
)

mainClass in (Compile, run) := Some("com.github.otterviews.Synchronize")

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
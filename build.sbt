name := "beard-cli"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "org.mongodb" %% "casbah" % "3.1.0",
  "org.reactivemongo" %% "reactivemongo" % "0.11.9",
  "com.cloudphysics" % "jerkson_2.10" % "0.6.3",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.6.4",
  "com.github.athieriot" %% "specs2-embedmongo" % "0.7.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

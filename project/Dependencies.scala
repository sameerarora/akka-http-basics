import sbt._

object Dependencies {

  private val alpakkaVersion = "2.0.2"
  private val akkaVersion = "2.6.14"
  private val akkaHttpVersion = "10.2.9"
  private val akkaHttpCirceVersion = "1.38.2"
  private val circeVersion = "0.14.1"


  lazy val coreDeps = Seq(
    "com.typesafe" % "config" % "1.4.1",
    "net.logstash.logback" % "logstash-logback-encoder" % "5.2"
  )

  lazy val testDeps = Seq(
    "org.scalatest" %% "scalatest" % "3.2.2" % Test,
    "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.14" % Test,
    "org.mockito" %% "mockito-scala" % "1.13.6" % Test,
    "org.mockito" %% "mockito-scala-scalatest" % "1.13.6" % Test,
    "com.softwaremill.sttp.client3" %% "core" % "3.3.11" % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion
  )

  lazy val circeDeps = Seq(
    "io.circe" %% "circe-yaml" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-generic-extras" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion
  )

  lazy val akkaDeps = Seq(
    "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % alpakkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion)

  lazy val akkaHttpDeps = Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirceVersion,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion
  )

}

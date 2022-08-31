import Dependencies._
import scala.sys.process._

name := "akka-http-basics"

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0"
ThisBuild / organization := "com.ecomexpress"
ThisBuild / organizationName := "EcomExpress"
ThisBuild / useCoursier := false

resolvers ++= Seq(
  Resolver.mavenLocal,
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
)

lazy val dockerComposeUp = taskKey[Unit]("Start the docker containers!!")

dockerComposeUp := {
  "./start-containers.sh" !
}

lazy val root = (project in file("."))
  .settings(
    name := "akka-http-basics",
    Test / parallelExecution := false,
    assembly / assemblyMergeStrategy := {
      case ps if ps.contains("version.conf") => MergeStrategy.concat
      case x if x.contains("io.netty.versions.properties") => MergeStrategy.discard
      case x if x.contains("version.properties") => MergeStrategy.discard
      case x if x.contains("git.properties") => MergeStrategy.discard
      case _@PathList("META-INF", "maven", "org.webjars", "swagger-ui", "pom.properties") =>
        MergeStrategy.first
      case "module-info.class" => MergeStrategy.discard
      case PathList(ps@_*) if ps.last endsWith "StaticLoggerBinder.class" => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith "Resource$AuthenticationType.class" => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith "StaticMDCBinder.class" => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith "StaticMarkerBinder.class" => MergeStrategy.first
      case x => {
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
      }
    },
    assembly / assemblyJarName := s"${name.value}.jar",
    assembly / test := {},
    publish := {},
    publishLocal := {},
    publishArtifact := false,
    libraryDependencies ++=
      coreDeps ++ testDeps ++ akkaDeps ++ circeDeps ++ akkaHttpDeps
  )

(Test / test) := (Test / test).dependsOn(dockerComposeUp).value

Test / testOptions += Tests.Cleanup(() => {
  val shell: Seq[String] = if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c") else Seq("bash", "-c")
  shell :+ "docker-compose -f docker-compose-it.yml down" !
})

updateOptions := updateOptions.value.withLatestSnapshots(false)

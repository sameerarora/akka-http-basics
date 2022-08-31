package com.ecomexpress

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.ecomexpress.commons.LoggingTarget
import com.ecomexpress.http.HealthController
import com.ecomexpress.service.IncomingMessageHandler
import com.ecomexpress.stream.IncomingMessageNotificationPublisher
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends LoggingTarget {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    implicit val system = ActorSystem("akka-http-basics")

    val incomingMessageHandler = new IncomingMessageHandler(IncomingMessageNotificationPublisher)

    val controller = new HealthController(incomingMessageHandler)

    val routes: Route = controller.health ~ controller.postExample

    val bindingFuture = Http()
      .newServerAt(config.getString("http.interface"), config.getInt("http.port"))
      .bindFlow(routes)

    logger.info(s"Server now online. Please navigate to http://${config.getString("http.interface")}:${config.getInt("http.port")}/health")

    Await.result(bindingFuture, Duration.Inf)
  }

}

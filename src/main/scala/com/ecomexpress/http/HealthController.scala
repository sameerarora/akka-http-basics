package com.ecomexpress.http

import akka.Done
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import com.ecomexpress.commons.LoggingTarget
import com.ecomexpress.config.ApplicationConfig.versionInfo
import com.ecomexpress.model.DeliveryNotification
import com.ecomexpress.service.IncomingMessageHandler
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success}

class HealthController(incomingMessageHandler: IncomingMessageHandler) extends Directives with LoggingTarget {

  val health: Route =
    path("health") {
      get {
        complete {
          StatusCodes.OK -> versionInfo
        }
      }
    }

  val postExample: Route =
    pathPrefix("bandwidth") {
      pathPrefix("notification") {
        extractActorSystem {
          actorSystem => {
            import actorSystem.dispatcher
            path("message") {
              post {
                entity(as[DeliveryNotification]) {
                  notification =>
                    incomingMessageHandler.handleIncomingMessageNotification(notification)(actorSystem) onComplete {
                      case Success(Done) => logger.debug(s"Successfully handled incoming message $notification")
                      case Failure(exception) => logger.error(s"Exception occurred while publishing message $notification", exception)
                    }
                    complete(StatusCodes.OK)
                }
              }
            }
          }
        }
      }
    }
}
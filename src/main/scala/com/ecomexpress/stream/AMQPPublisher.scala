package com.ecomexpress.stream

import akka.actor.ActorSystem
import akka.stream.alpakka.amqp.WriteMessage
import akka.stream.scaladsl.{Flow, Source}
import akka.util.ByteString
import com.ecomexpress.config.ApplicationConfig.{amqpRootConfig, workQueueConfig}
import com.ecomexpress.config.RmqConfig
import com.ecomexpress.model.IncomingMessageNotification
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

sealed abstract class AMQPPublisher[T] extends Publisher[T, WriteMessage] {

  private val publisher = PrestaProducer(RmqConfig(amqpRootConfig, workQueueConfig))

  protected implicit val encoder: Encoder[T]

  override def publish(notification: T)(implicit actorSystem: ActorSystem) = {
    Source.single(notification)
      .via(Flow.fromFunction(map))
      .runWith(publisher)
  }

  override def map(t: T): WriteMessage = WriteMessage(ByteString(encoder(t).noSpaces))

}

object IncomingMessageNotificationPublisher extends AMQPPublisher[IncomingMessageNotification] {
  implicit override val encoder: Encoder[IncomingMessageNotification] = deriveEncoder[IncomingMessageNotification]
}

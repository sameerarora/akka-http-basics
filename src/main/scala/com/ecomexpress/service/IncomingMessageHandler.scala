package com.ecomexpress.service

import akka.actor.ActorSystem
import akka.stream.alpakka.amqp.WriteMessage
import com.ecomexpress.model.{DeliveryNotification, IncomingMessageNotification}
import com.ecomexpress.stream.Publisher

class IncomingMessageHandler(publisher: Publisher[IncomingMessageNotification, WriteMessage]) {


  def handleIncomingMessageNotification(deliveryNotification: DeliveryNotification)(implicit actorSystem: ActorSystem) = {
    val notification = toIncomingMessageNotification(deliveryNotification)
    publisher.publish(notification)
  }

  private def toIncomingMessageNotification(notification: DeliveryNotification) = {
    IncomingMessageNotification(notification.message)
  }
}

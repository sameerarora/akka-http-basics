package com.ecomexpress.service

import akka.Done
import akka.actor.ActorSystem
import akka.stream.alpakka.amqp.WriteMessage
import com.ecomexpress.model.{DeliveryNotification, IncomingMessageNotification}
import com.ecomexpress.stream.Publisher
import org.mockito.MockitoSugar.{mock, verify, when}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.time.Instant
import scala.concurrent.Future

class IncomingMessageHandlerSpec extends AnyWordSpec with Matchers {
  val instant = Instant.parse("2021-04-14T18:35:50.526478093Z")

  "Incoming Message handler" should {
    "transform delivery notification to incoming message notification and publish" in {
      val publisher = mock[Publisher[IncomingMessageNotification, WriteMessage]]
      implicit val actorSystem = mock[ActorSystem]
      val incomingMessageHandler = new IncomingMessageHandler(publisher)

      val incomingMessageNotification = IncomingMessageNotification("Some message text")
      when(publisher.publish(incomingMessageNotification)(actorSystem)).thenReturn(Future.successful(Done))

      val deliveryNotification = DeliveryNotification("message‐received","some‐id","Some message text")

      incomingMessageHandler.handleIncomingMessageNotification(deliveryNotification)

      verify(publisher).publish(incomingMessageNotification)
    }

  }

}

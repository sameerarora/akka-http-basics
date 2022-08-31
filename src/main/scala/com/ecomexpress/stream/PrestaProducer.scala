package com.ecomexpress.stream

import akka.Done
import akka.stream.alpakka.amqp.scaladsl.AmqpSink
import akka.stream.alpakka.amqp.{AmqpWriteSettings, WriteMessage}
import akka.stream.scaladsl.Sink
import com.ecomexpress.commons.LoggingTarget
import com.ecomexpress.config.RmqConfig

import scala.concurrent.Future

object PrestaProducer extends LoggingTarget {

  def apply(rmqConfig: RmqConfig): Sink[WriteMessage, Future[Done]] = {
    val sinkSettings = AmqpWriteSettings(rmqConfig.amqpDetails)
      .withRoutingKey(rmqConfig.routingKey)
      .withExchange(rmqConfig.exchangeName)
      .withDeclarations(Seq(rmqConfig.queueDeclaration, rmqConfig.exchangeDeclaration))
    logger.info(s"Creating RMQ sink based on settings: ${sinkSettings.declarations}")
    AmqpSink(sinkSettings)
  }

}

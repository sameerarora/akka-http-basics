package com.ecomexpress.rmq

import akka.NotUsed
import akka.stream.alpakka.amqp._
import akka.stream.alpakka.amqp.scaladsl.AmqpSource
import akka.stream.scaladsl.Source
import com.ecomexpress.commons.LoggingTarget
import com.ecomexpress.config.RmqConfig

object MessageQueue extends LoggingTarget {

  def createQueue(rmqConfig: RmqConfig): Source[ReadResult, NotUsed] = {
    val namedQueueSourceSettings = readSettings(rmqConfig)
    logger.info(s"Creating RMQ source based on rmq settings: ${namedQueueSourceSettings.declarations}")
    AmqpSource.atMostOnceSource(namedQueueSourceSettings, bufferSize = rmqConfig.bufferSize)
  }


  def readSettings(rmqConfig: RmqConfig): NamedQueueSourceSettings = {
    val rmqConnection = getDetailsConnectionProvider(rmqConfig)

    val queueDeclaration = QueueDeclaration(rmqConfig.queueName).withDurable(true)

    val exchangeDeclaration = ExchangeDeclaration(rmqConfig.exchangeName, rmqConfig.exchangeType)
      .withDurable(true)

    val bindingDeclaration = BindingDeclaration(rmqConfig.queueName, rmqConfig.exchangeName)
      .withRoutingKey(rmqConfig.routingKey)

    NamedQueueSourceSettings(rmqConnection, rmqConfig.queueName)
      .withDeclarations(Seq(queueDeclaration, bindingDeclaration, exchangeDeclaration))
      .withAckRequired(false)
  }

  def getDetailsConnectionProvider(rmqConfig: RmqConfig): AmqpDetailsConnectionProvider =
    AmqpDetailsConnectionProvider(rmqConfig.host, rmqConfig.port)
      .withCredentials(AmqpCredentials(rmqConfig.username, rmqConfig.password))
      .withVirtualHost(rmqConfig.virtualHost)

}

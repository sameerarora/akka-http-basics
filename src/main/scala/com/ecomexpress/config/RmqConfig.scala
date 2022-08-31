package com.ecomexpress.config

import akka.stream.alpakka.amqp.{AmqpCredentials, AmqpDetailsConnectionProvider, ExchangeDeclaration, QueueDeclaration}
import com.typesafe.config.Config

case class RmqConfig(
                      host: String,
                      port: Int,
                      username: String,
                      password: String,
                      virtualHost: String,
                      bufferSize: Int,
                      queueName: String,
                      exchangeName: String,
                      exchangeType: String,
                      routingKey: String
                    ) {

  lazy val amqpDetails = AmqpDetailsConnectionProvider(host, port)
    .withCredentials(AmqpCredentials(username, password))
    .withVirtualHost(virtualHost)

  lazy val queueDeclaration = QueueDeclaration(queueName).withDurable(true)

  lazy val exchangeDeclaration = ExchangeDeclaration(exchangeName, exchangeType)
    .withDurable(true)
}

object RmqConfig {
  def apply(serverConfig: Config, queueConfig: Config): RmqConfig = RmqConfig(
    serverConfig.getString("host"),
    serverConfig.getInt("port"),
    serverConfig.getString("username"),
    serverConfig.getString("password"),
    serverConfig.getString("virtualHost"),
    serverConfig.getInt("bufferSize"),
    queueConfig.getString("queue"),
    queueConfig.getString("exchangeName"),
    queueConfig.getString("exchangeType"),
    queueConfig.getString("routingKey")
  )
}
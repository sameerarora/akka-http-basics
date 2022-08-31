package com.ecomexpress.rmq

import akka.stream.alpakka.amqp.{AmqpCredentials, AmqpDetailsConnectionProvider}
import com.ecomexpress.config.RmqConfig

object RmqConnectionProvider {

  def getDetailsConnectionProvider(rmqConfig: RmqConfig): AmqpDetailsConnectionProvider =
    AmqpDetailsConnectionProvider(rmqConfig.host, rmqConfig.port)
      .withCredentials(AmqpCredentials(rmqConfig.username, rmqConfig.password))
      .withVirtualHost(rmqConfig.virtualHost)

}

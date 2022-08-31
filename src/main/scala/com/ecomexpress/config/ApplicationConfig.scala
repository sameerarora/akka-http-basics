package com.ecomexpress.config

import com.ecomexpress.model.VersionInfo
import com.typesafe.config.ConfigFactory

object ApplicationConfig {

  private val config = ConfigFactory.load()

  lazy val versionInfo: VersionInfo = VersionInfo(config.getString("version.info"))

  val amqpRootConfig = config.getConfig("amqp.root")

  val workQueueConfig = config.getConfig("amqp.workqueue-internal")

}

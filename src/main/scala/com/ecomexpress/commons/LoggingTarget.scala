package com.ecomexpress.commons

import org.slf4j.{Logger, LoggerFactory, MarkerFactory}

trait LoggingTarget {
  self =>
  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  implicit class LoggerWithFatal(lgr: Logger) {

    private lazy val fatal = MarkerFactory.getMarker("FATAL")

    def fatal(msg: String, ex: Exception): Unit = {
      logger.error(fatal, msg, ex)
    }

    def fatal(msg: String): Unit = {
      logger.error(fatal, msg)
    }
  }
}
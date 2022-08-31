package com.ecomexpress.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.ecomexpress.service.IncomingMessageHandler
import com.ecomexpress.stream.IncomingMessageNotificationPublisher
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HealthControllerTest extends AnyWordSpec with Matchers with ScalatestRouteTest {

  "Health Controller" should {
    "return the version info" in {
      val incomingMessageHandler = new IncomingMessageHandler(IncomingMessageNotificationPublisher)
      val healthController = new HealthController(incomingMessageHandler)
      Get("/health") ~> healthController.health ~> check {
        responseAs[String] shouldEqual
          """{"version":"test-version"}""".stripMargin
      }
    }
  }

}

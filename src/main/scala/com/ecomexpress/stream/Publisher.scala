package com.ecomexpress.stream

import akka.Done
import akka.actor.ActorSystem

import scala.concurrent.Future

trait Publisher[A, B] {

  def publish(notification: A)(implicit actorSystem: ActorSystem): Future[Done]

  def map(a: A): B

}
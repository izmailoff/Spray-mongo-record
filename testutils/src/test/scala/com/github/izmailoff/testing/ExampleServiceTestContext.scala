package com.github.izmailoff.testing

import com.github.izmailoff.mongo.connection.RandomDbConnectionIdentifier
import com.github.izmailoff.service.RestService

trait ExampleServiceTestContext
  extends RestServiceMongoDbTestContext[RestService] {

  def serviceContext = new AroundOutsideRestService[RestService](system) {
    override val service = new RestService
      with RandomDbConnectionIdentifier {
      def actorRefFactory = system

      val globalSystem = system

      val route =
        path("") {
          get {
            complete("Hello World")
          } ~
            post {
              entity(as[String]) { data =>
                complete(data)
              }
            }
        }
    }
  }
}

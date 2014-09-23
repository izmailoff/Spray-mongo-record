package com.github.izmailoff.testing

import com.github.izmailoff.service.RestService

class RestServiceMongoDbTestContextSpec
  extends ExampleServiceTestContext {

  "REST Service" should {

    """return "Hello World" for GET requests to the root path""" in serviceContext { (service: RestService) =>
      import service._
      Get() ~> route ~> check {
        handled must beTrue
      }
    }
  }

}

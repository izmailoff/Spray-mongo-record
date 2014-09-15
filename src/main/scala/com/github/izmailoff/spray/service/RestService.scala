package com.github.izmailoff.spray.service

import com.github.izmailoff.mongo.connection.DbConnectionIdentifier
import com.github.izmailoff.spray.marshalling.mongo.MongoMarshallingSupport
import spray.routing._

/**
 * An [[spray.routing.HttpService]] like trait that requires database connection identifier and defines route member
 * that has to be implemented.
 */
trait RestService
  extends HttpService
  with DbConnectionIdentifier {
  def route: Route
}

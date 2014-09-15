package com.github.izmailoff.service

import com.github.izmailoff.marshalling.MongoMarshallingSupport
import com.github.izmailoff.mongo.connection.DbConnectionIdentifier
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

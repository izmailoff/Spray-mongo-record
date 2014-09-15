package com.github.izmailoff.mongo.connection

import net.liftweb.mongodb.MongoIdentifier
import net.liftweb.util.StringHelpers

/**
 * Provides database name to be used in MetaRecords.
 * This will usually initialize before MetaRecords do.
 */
trait DbConnectionIdentifier {

  implicit def currentMongoId: MongoIdentifier
}

/**
 * Default implementation that provides DB name for production
 * and dev purposes. Unit tests will provide random or test
 * DB name instead.
 */
trait DefaultDbConnectionIdentifier
  extends DbConnectionIdentifier {

  override implicit val currentMongoId = MongoConfig.createMongoId()
}

/**
 * Provides random DB name for each instance of test suite.
 */
trait RandomDbConnectionIdentifier
  extends DbConnectionIdentifier {

  override implicit val currentMongoId = MongoConfig.createMongoId(StringHelpers.randomString(10))
}

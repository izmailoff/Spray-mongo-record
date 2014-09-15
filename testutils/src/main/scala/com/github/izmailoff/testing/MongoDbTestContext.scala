package com.github.izmailoff.testing

import com.github.izmailoff.logging.AkkaLoggingHelper
import com.mongodb.Mongo
import net.liftweb.mongodb.{MongoDB, MongoIdentifier}
import com.github.fakemongo.Fongo
import org.specs2.mutable.Around
import org.specs2.execute.{AsResult, Result}

/**
 * Defines a helper test trait that provides DB connection for tests.
 */
trait MongoDbTestContext
  extends AkkaLoggingHelper {

  /**
   * Override this if you want to define real MongoDB database connection or faked in-memory Fongo database.
   * @return
   */
  def mongo: Mongo = {
    val fongo = new Fongo("testserver")
    fongo.getMongo
  }

  /**
   * Sets Mongo connection, drops all previous collections, creates empty collections.
   *
   * Prints "delimiter" between each DB reset so that it's easier to read logs.
   */
  def databaseContext(implicit mongoId: MongoIdentifier) = new Around {
    override def around[T: AsResult](t: => T): Result = {
      val dbName = mongoId.jndiName
      log.debug("\n" + "*" * 20 + s" SPINNING UP MONGO DATABASE [$dbName] " + "*" * 20)
      MongoDB.defineDb(mongoId, mongo, dbName)
      val currentDb = MongoDB.getDb(mongoId).get
      //createAllEmptyCollections(currentDb)
      //dropAllCollections(currentDb)
      //createAllEmptyCollections(currentDb)
      val result = AsResult(t)
      log.debug("*" * 20 + s"  SHUTTING DOWN MONGO DATABASE [$dbName]  " + "*" * 20 + "\n")
      currentDb.dropDatabase()
      result
    }
  }
}

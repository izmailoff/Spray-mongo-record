package com.github.izmailoff.testing

import com.github.fakemongo.Fongo
import com.mongodb.{DB, Mongo}
import net.liftweb.mongodb.{MongoDB, MongoIdentifier}
import org.specs2.execute.{AsResult, Result}
import org.specs2.mutable.Around

import scala.collection.JavaConversions._

/**
 * Defines a helper test trait that provides DB connection for tests.
 */
trait MongoDbTestContext {

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
      MongoDB.defineDb(mongoId, mongo, dbName)
      val currentDb = MongoDB.getDb(mongoId).get
//      createAllEmptyCollections(currentDb)
//      dropAllCollections(currentDb)
//      createAllEmptyCollections(currentDb)
      val result = AsResult(t)
      currentDb.dropDatabase()
      result
    }
  }

//  /**
//   * Just referencing a collection should create it.
//   */
//  def createEmptyCollection(db: DB, name: String): Unit =
//    db.getCollection(name)
//
//  /**
//   * Drops all collections keeping DB.
//   */
//  def dropAllCollections(db: DB): Unit =
//    db.getCollectionNames foreach { db.getCollection(_).drop() }
//
//  /**
//   * Creates/touches all empty collections in our DB. This avoids errors with index creation
//   * if collection does not exist or in tests.
//   */
//  def createAllEmptyCollections(db: DB): Unit =
//    db.getCollectionNames foreach { createEmptyCollection(db, _) }

}

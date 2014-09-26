package com.github.izmailoff.mongo.schema

import net.liftweb.mongodb.{MongoIdentifier, MongoDB}
import org.springframework.core.io.{Resource, ClassPathResource}
import org.mongeez.Mongeez

/**
 * Defines all MongoDB schema migrations using Mongeez.
 * Requires: default MongoDB connection to be initialized prior to use.
 */
trait DbSchemaMigration {

  def mainFile: Resource = new ClassPathResource("mongeez.xml")

  /**
   * Runs all migrations that were not run previously.
   */
  def run(mongoId: MongoIdentifier) {
    val db = MongoDB.getDb(mongoId).get.getMongo
    val mongeez = new Mongeez
    mongeez.setFile(mainFile)
    mongeez.setMongo(db)
    mongeez.setDbName(mongoId.jndiName)
    mongeez.process()
  }
}

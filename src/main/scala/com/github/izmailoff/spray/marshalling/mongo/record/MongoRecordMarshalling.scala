package com.github.izmailoff.spray.marshalling.mongo.record

import net.liftweb.json._
import net.liftweb.mongodb.record.BsonRecord
import spray.http.ContentTypes._
import spray.httpx.LiftJsonSupport
import spray.httpx.marshalling.Marshaller

trait MongoRecordMarshalling
extends LiftJsonSupport {

  implicit val BsonRecordMarshaller =
    Marshaller.delegate[BsonRecord[_], JValue](`application/json`)(_.asJValue)

  implicit val BsonRecordCollectionMarshaller =
    Marshaller.delegate[Traversable[BsonRecord[_]], JValue](`application/json`) { records =>
      JArray(records.map(_.asJValue).toList)
    }
}

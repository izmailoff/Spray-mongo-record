package com.github.izmailoff.marshalling.objectid

import org.bson.types.ObjectId
import spray.httpx.unmarshalling.{Deserializer, MalformedContent}
import spray.routing.PathMatcher

trait ObjectIdSupport {

  implicit val String2ObjectIdDeserializer = new Deserializer[String, ObjectId] {
    def apply(value: String) =
      if (ObjectId.isValid(value))
        Right(new ObjectId(value))
      else
        Left(MalformedContent("'" + value + "' is not a valid ObjectId value"))
  }

  /**
   * A PathMatcher that matches and extracts an ObjectId instance.
   */
  val ObjectIdSegment = PathMatcher( """^[0-9a-fA-F]{24}$""".r).flatMap { str => // TODO: no need for regex! - double check
    if (ObjectId.isValid(str)) Some(new ObjectId(str))
    else None
  }
}

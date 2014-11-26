package com.github.izmailoff.marshalling

import java.text.SimpleDateFormat

import com.github.izmailoff.logging.AkkaLoggingHelper
import com.github.izmailoff.marshalling.box.BoxMarshalling
import com.github.izmailoff.marshalling.mongo.record.{MongoRecordMarshalling, MongoRecordUnmarshalling}
import com.github.izmailoff.marshalling.objectid.ObjectIdSupport
import net.liftweb.json._
import spray.http.ContentTypes._
import spray.http._
import spray.httpx.marshalling._
import spray.httpx.unmarshalling._

/**
 * A single trait that brings all marshalling together. ...
 */
trait MongoMarshallingSupport
  extends MongoRecordMarshalling
  with MongoRecordUnmarshalling
  with BoxMarshalling
  with ObjectIdSupport
  with AkkaLoggingHelper {

  override implicit def liftJsonFormats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  }

  implicit val String2JsonUnmarshaller =
    Unmarshaller[JValue](MediaTypes.`application/json`) {
      case HttpEntity.NonEmpty(contentType, data) =>
        parse(new String(data.toByteArray.map(_.toChar)))
    }

  implicit val JsonMarshaller = jsonMarshaller(`application/json`)

  def jsonMarshaller(contentType: ContentType, more: ContentType*): Marshaller[JValue] =
    Marshaller.of[JValue](contentType +: more: _*) { (value, contentType, ctx) =>
      ctx.marshalTo(HttpEntity(contentType, pretty(render(value))))
    }
}

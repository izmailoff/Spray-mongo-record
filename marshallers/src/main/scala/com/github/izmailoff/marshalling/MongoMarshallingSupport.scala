package com.github.izmailoff.marshalling

import java.text.SimpleDateFormat

import com.github.izmailoff.logging.AkkaLoggingHelper
import com.github.izmailoff.marshalling.box.BoxMarshalling
import com.github.izmailoff.marshalling.mongo.record.{MongoRecordMarshalling, MongoRecordUnmarshalling}
import com.github.izmailoff.marshalling.objectid.ObjectIdSupport
import net.liftweb.json.DefaultFormats
import spray.httpx.LiftJsonSupport

/**
 * A single trait that brings all marshalling together. ...
 */
trait MongoMarshallingSupport
  extends LiftJsonSupport
  with MongoRecordMarshalling
  with MongoRecordUnmarshalling
  with BoxMarshalling
  with ObjectIdSupport
  with AkkaLoggingHelper {

  override implicit def liftJsonFormats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  }
}

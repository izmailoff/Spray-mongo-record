package com.github.izmailoff.marshalling.mongo.record

import java.text.SimpleDateFormat

import akka.actor.ActorSystem
import com.github.izmailoff.logging.AkkaLoggingHelper
import net.liftweb.json._
import org.specs2.mutable.Specification
import spray.http.ContentTypes
import spray.http.HttpEntity.NonEmpty
import spray.httpx.marshalling._

class MongoRecordMarshallingSpec
  extends Specification
  with MongoRecordMarshalling
  with AkkaLoggingHelper {

  implicit val globalSystem = ActorSystem()

  override implicit def liftJsonFormats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  }

  val testRecordOne = TestRecord.createRecord.testName("some name ONE")
  val testRecordOneJson = pretty(render(testRecordOne.asJValue))
  val testRecordTwo = TestRecord.createRecord.testName("some name TWO")

  "Record Marshaller" should {
    "properly marshall a record to JSON" in {
      marshal(testRecordOne) match {
        case Right(NonEmpty(contentType, data)) =>
          contentType === ContentTypes.`application/json`
          parse(data.asString) === parse(testRecordOneJson)
        case _ =>
          ko("Unexpected content type or missing HTTP entity data")
      }
    }

    "properly marshall a collection of records to JSON array" in {
      val expectedJson = JArray(testRecordOne.asJValue :: testRecordTwo.asJValue :: Nil)

      marshal(testRecordOne :: testRecordTwo :: Nil) match {
        case Right(NonEmpty(contentType, data)) =>
          contentType === ContentTypes.`application/json`
          parse(data.asString) === expectedJson
        case _ =>
          ko("Unexpected content type or missing HTTP entity data")
      }
    }
  }
}


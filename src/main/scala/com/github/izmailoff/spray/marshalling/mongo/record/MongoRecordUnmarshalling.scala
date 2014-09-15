package com.github.izmailoff.spray.marshalling.mongo.record

import net.liftweb.common.{Failure, Full, Box}
import net.liftweb.json._
import net.liftweb.record.{MetaRecord, Record}
import net.liftweb.util.ControlHelpers._
import spray.http.{HttpEntity, HttpRequest}
import spray.httpx.unmarshalling._


trait MongoRecordUnmarshalling {

  def requestToRecordUnmarshaller[T <: Record[T]](metaRec: T with MetaRecord[T]) =
    new FromRequestUnmarshaller[T] {
      def apply(req: HttpRequest): Deserialized[T] =
        req.entity match {
          case HttpEntity.NonEmpty(contentType, data) =>
            val res = for {
              jsonData <- tryo(parse(data.asString))
              result <- jsonToRecordUnpickler(jsonData, metaRec)
            } yield result
            res toRight (MalformedContent(s"'${data.asString}' is not valid"))
          case HttpEntity.Empty =>
            Left(ContentExpected)
        }
    }

  /**
   * Converts JSON to a DB object (Record) by setting all fields provided in JSON document.
   * It's expected that users perform validation of the result before they use it.
   * @param json
   * @param metaRec
   * @tparam T
   * @return A full box with DB object is returned if all fields could be set.
   */
  private def jsonToRecordUnpickler[T <: Record[T]](json: JValue, metaRec: T with MetaRecord[T]): Box[T] = {
    val record = metaRec.createRecord
    record.setFieldsFromJValue(json) match {
      case Full(()) => Full(record)
      case _ => Failure("Bad request format.")
    }
  }
}

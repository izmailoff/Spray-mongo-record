package com.github.izmailoff.marshalling.box

import akka.actor.ActorSystem
import com.github.izmailoff.logging.AkkaLoggingHelper
import net.liftweb.common.{Box, Full}
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import org.specs2.mutable.Specification
import spray.http.{ContentTypes, HttpEntity}
import spray.httpx.marshalling._

class BoxMarshallingSpec
  extends Specification
  with BoxMarshalling
  with AkkaLoggingHelper {

  implicit val globalSystem = ActorSystem()

  "The Box Marshaller" should {
    "properly marshall an empty box instance to JSON" in {
      val expectedResponse = pretty(render(("status" -> "SUCCESSFUL")))
      marshal[Box[_]](Full(())) === Right(HttpEntity(ContentTypes.`application/json`, expectedResponse))
    }

    "properly marshall a full box instance to JSON" in {
      val expectedResponse = pretty(render(("status" -> "SUCCESSFUL") ~ ("value" -> "123")))
      marshal[Box[_]](Full(123)) === Right(HttpEntity(ContentTypes.`application/json`, expectedResponse))
    }

    // TODO: add tests for all cases here
  }

}

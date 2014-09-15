package com.github.izmailoff.spray.testing

import akka.actor.ActorSystem
import com.github.izmailoff.mongo.connection.RandomDbConnectionIdentifier
import com.github.izmailoff.spray.service.RestService
import net.liftweb.json.JsonAST.JField
import net.liftweb.json._
import org.specs2.execute.{Result, AsResult}
import org.specs2.mutable.Specification
import org.specs2.specification.AroundOutside
import spray.http.HttpHeaders.`Content-Encoding`
import spray.http.{HttpEncoding, HttpResponse}
import spray.routing.Route
import spray.testkit.Specs2RouteTest
import spray.util._

/**
 * Provides [[RestService]] and [[MongoDbTestContext]] context for each specification so that the client
 * does not need to setup MongoDB explicitly or provide all dependencies for the REST service.
 */
trait RestServiceMongoDbTestContext
  extends Specification
  with Specs2RouteTest {

  //val globalSystem = system

  def serviceContext: AroundOutsideRestService

  // TODO: put these helpers somewhere or remove completely:
  //  def haveContentEncoding(encoding: HttpEncoding) =
  //    beEqualTo(Some(`Content-Encoding`(encoding))) ^^ { (_: HttpResponse).headers.findByType[`Content-Encoding`] }

  //  /**
  //   * Removes auto generated fields from JSON response. This makes it
  //   * easier to compare JSON afterwards in tests.
  //   */
  //  def removeAutogenFields(json: JValue): JValue =
  //    parse(compact(render(
  //      json remove {
  //        case JField("_id", _) | JField("when", _) => true
  //        case _ => false
  //      })))

}

/**
 * An Around and Outside context for tests. You need to plug in/implement your service
 *
 * @param system A test Actor system provided by Spray TestKit. Use it to connect to actorRefFactory in your HTTP service.
 */
abstract class AroundOutsideRestService(system: ActorSystem)
  extends AroundOutside[RestService]
  with MongoDbTestContext {

  def around[T: AsResult](t: => T): Result =
    databaseContext(service.currentMongoId).around(t)

  def outside: RestService = service

  val service: RestService

  val globalSystem = system

  //      val service = new RestServiceImpl
  //        with RandomDbConnectionIdentifier
  //        with DbCrudProviderImpl {
  //        def actorRefFactory = system
  //        val globalSystem = system
}
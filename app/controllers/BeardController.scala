package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.libs.Json._
import reactivemongo.bson._
import service.BeardService
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.{Failure, Success, Random}
import scala.util.parsing.json.JSONObject

class BeardController extends Controller {

  val beardService = new BeardService

  def index = Action { request =>
    Ok(views.html.index("dream.explore.die"))
  }

  implicit val reader = (__ \ 'query)
                          .read[String]

  def query = Action.async { request =>

    implicit
    object ImplicitDocumentReader extends BSONReader[BSONValue, String] {
      def read(v: BSONValue) =
        v match {
          case oid: BSONObjectID => oid.stringify
          case BSONInteger(integerValue) => integerValue.toString
          case BSONLong(longValue) => longValue.toString
          case BSONDouble(doubleValue) => doubleValue.toString
          case BSONString(stringValue) => stringValue.toString
        }
    }

    var requestedQuery : Option[JsValue] = Option.empty

    request.body.asJson.foreach(lookForQuery => {
      requestedQuery = (lookForQuery \ "query").toOption
    })

    val result : Future[List[BSONDocument]] = beardService.query(requestedQuery.get.toString())

//    if(requestedQuery.isDefined) {
//
//      val result : Future[List[BSONDocument]] = beardService.query(requestedQuery.get.toString())
//
//      val listBuffer = new ListBuffer[Map[String, Any]]
//      result.onComplete {
//        case Success(value : List[BSONDocument]) => {
//          value.foreach(document => {
//            println(document.get("type"))
//            Ok(document.get("type").get.toString)
//          })
//        }
//        case Failure(exception) => println(exception)
//      }
//
//      Ok(listBuffer.size+"")
//    } else {
//      BadRequest(s"Missing parameter [query] ${asJson}")
//    }

    var listBuffer = new ListBuffer[mutable.Map[String, String]]

    result.map(list => {
      list.foreach(document => {
        val map = mutable.Map[String, String]().empty
        document.elements.foreach(element => {
              map+= element._1 -> element._2.as[String] //as uses implicit reader
        })
        listBuffer+= map
      })
      val jsonString = com.codahale.jerkson.Json.generate(Map("result" -> listBuffer.toList))
      Ok(jsonString)
    })
  }

  def intensiveComputation(): JsObject = {
      Thread.sleep(Random.nextInt(500))
        Json.obj("value" -> 88)
  }

  def sayAsyncBeard = Action.async { request =>
    val futureInt = Future {
      intensiveComputation()
    }
    futureInt.map(result =>
      Ok(result)
    )
  }

  def sayAsyncBeards = Action.async { request =>
    val futureInt = Future {
      intensiveComputation()
    }
    futureInt.map(result =>
      Ok(result)
    )
  }
}

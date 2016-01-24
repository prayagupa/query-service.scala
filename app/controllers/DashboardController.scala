package controllers

import com.mongodb.DBObject
import play.api.libs.json.Json
import play.api.libs.json.{JsString, JsObject, JsValue, Writes}
import play.api.mvc.{Action, Controller}
import service.DashboardService
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable


/**
 * Created by prayagupd
 * on 1/23/16.
 */

class DashboardController extends Controller {

  val service = new DashboardService

  implicit val defaultDbObjectToJsonWriter = new Writes[DBObject] {

    override def writes(givenDBObject: DBObject): JsValue = {
      //scalaz way of mathematical mapping
      // http://stackoverflow.com/a/9140038/432903

      val map = givenDBObject.keySet().asScala
                             .map(key => key -> JsString(givenDBObject.get(key).toString))
                             .toMap
      JsObject(map)
    }
  }

  def index = Action { request =>
    Ok(Json.toJson(service.listDocuments("EventStream")))
  }
}

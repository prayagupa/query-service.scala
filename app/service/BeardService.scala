package service

import play.api.libs.json.{Json, JsObject}
import service.mongo.{MongoResponse, Mongo, Query}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

/**
 * Created by prayagupd
 * on 12/26/15.
 */

class BeardService {

    val dbQuery : Query = new MongoFindQuery

    def query(queryType : String, query: String) : MongoResponse = {
        Mongo.actions.get(queryType).get.queryDatabase(query)
    }

    def process(): Future[JsObject] = {
        Future {
            intensiveComputation()
        }
    }

    private def intensiveComputation(): JsObject = {
        Thread.sleep(Random.nextInt(5000))
        Json.obj("value" -> "beard")
    }
}

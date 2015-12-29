package service

import play.api.libs.json.JsObject
import reactivemongo.bson.BSONDocument
import service.mongo.{MongoResponse, Mongo, Query}

import scala.concurrent.Future

/**
 * Created by prayagupd
 * on 12/26/15.
 */

class BeardService {

    val dbQuery : Query = new MongoFindQuery

    def query(queryType : String, query: String) : MongoResponse = {
        Mongo.actions.get(queryType).get.queryDatabase(query)}
}

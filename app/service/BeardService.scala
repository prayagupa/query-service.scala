package service

import play.api.libs.json.JsObject
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
 * Created by prayagupd
 * on 12/26/15.
 */

class BeardService {

    val dbQuery : Query = new MongoQuery

    def query(query : String) : Future[List[BSONDocument]] = {
      dbQuery.find(query)
    }
}

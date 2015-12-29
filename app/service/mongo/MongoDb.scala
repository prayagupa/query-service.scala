package service.mongo

import reactivemongo.bson.BSONDocument
import service.{MongoInsertQuery, MongoFindQuery}

import scala.concurrent.Future

/**
 * Created by prayagupd
 * on 12/28/15.
 */

case class MongoResponse(result : Any)

trait Query {
  def queryDatabase(query: String) : MongoResponse
}

object Mongo {
  val actions = Map[String, Query] (
      "find" -> new MongoFindQuery,
      "insert" -> new MongoInsertQuery
  )
}

package service


import com.mongodb.{CursorType, BasicDBObject}
import com.mongodb.casbah.{MongoCollection, MongoClient}
import service.mongo.{MongoResponse, Query}

import scala.concurrent.Future

import play.api.libs.json._
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{Collection, MongoDriver}
import reactivemongo.bson._
import scala.concurrent.ExecutionContext.Implicits.global

import com.fasterxml.jackson.databind.ObjectMapper

import scala.collection.JavaConverters._

/**
 * Created by prayagupd
 * on 12/27/15.
 */

class MongoFindQuery extends Query {
  val DbName = "events-db"
  val CollectionName = "Events"

  val connection = MongoClient("localhost", 27017)

  override def queryDatabase(queryString: String): MongoResponse = {
    val db = connection(DbName)
    val collection = db(CollectionName)

    val mapper = new ObjectMapper()
    val queryMap = mapper.readValue(queryString, classOf[java.util.Map[String, Object]]).asScala

    val query = new BasicDBObject()
    queryMap.foreach(entry => {
      query.append(entry._1.toString , entry._2.toString)
    })
    println(s"finding $query")
    val futureList : collection.CursorType = collection.find(query)
    new MongoResponse(result = futureList)
  }
}

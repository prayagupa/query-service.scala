package service

import reactivemongo.api.commands.WriteResult
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

class MongoInsertQuery extends Query {
  val DbName = "events-db"
  val CollectionName = "EventStream"

  val driver = new MongoDriver
  val connection = driver.connection(List("127.0.0.1:27018"))

  override def queryDatabase(queryString: String): MongoResponse = {
    val db = connection(DbName)
    val collection : BSONCollection = db(CollectionName)

    val mapper = new ObjectMapper()
    val queryMap = mapper.readValue(queryString, classOf[java.util.Map[String, Object]]).asScala

    var query = BSONDocument()
    queryMap.foreach(entry => {
      query = BSONDocument(entry._1.toString -> entry._2.toString)
    })
    println(s"inserting ${BSONDocument.pretty(query)}")
    val eventualWriteResult: Future[WriteResult] = collection.insert(query)

    new MongoResponse(result = eventualWriteResult)
  }
}

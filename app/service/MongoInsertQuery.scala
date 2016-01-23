package service

import com.mongodb.BasicDBObject
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.TypeImports._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoCollection, MongoDB, MongoClient}
import service.mongo.{MongoResponse, Query}
import scala.concurrent.ExecutionContext.Implicits.global

import com.fasterxml.jackson.databind.ObjectMapper

import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Created by prayagupd
 * on 12/27/15.
 */

class MongoInsertQuery extends Query {
  val DbName = "events-db"
  val CollectionName = "Events"

  val connection = MongoClient("localhost", 27017)

  def insert(queryString: String) : Future[WriteResult] = {
    Future {
      val db = getDatabase(connection)
      val collection = getCollection(db)

      val mapper = new ObjectMapper()
      val queryMap = mapper.readValue(queryString, classOf[java.util.Map[String, Object]]).asScala
      println(s"querying $queryMap")
      val query = new BasicDBObject()
      queryMap.foreach(entry => {
        query.append(entry._1.toString , entry._2)
      })
      println(s"inserting ${query}")
      val eventualWriteResult : WriteResult= collection.insert(query)
      eventualWriteResult
    }
  }

  override def queryDatabase(queryString: String): MongoResponse = {
    val eventualWriteResult = insert(queryString)
    new MongoResponse(result = eventualWriteResult)
  }

  def getDatabase(client: MongoClient) : MongoDB = {
    client(DbName)
  }

  def getCollection(db: MongoDB) : MongoCollection = {
    db(CollectionName)
  }
}

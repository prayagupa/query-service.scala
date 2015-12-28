package service

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import com.fasterxml.jackson.annotation.JsonValue
import play.api.libs.iteratee.Iteratee
import play.api.libs.json._
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{Collection, MongoDriver}
import reactivemongo.bson._
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * Created by prayagupd
 * on 12/27/15.
 */

trait Query {
  def find(query: String) : Future[List[BSONDocument]]
}

class MongoQuery extends Query {
  val DbName = "events-db"
  val CollectionName = "EventStream"

  val driver = new MongoDriver
  val connection = driver.connection(List("127.0.0.1:27018"))

  override def find(query: String): Future[List[BSONDocument]] = {
    val db = connection(DbName)
    val collection : BSONCollection = db(CollectionName)

    val query = BSONDocument("type" -> "Product_Received")
    val futureList: Future[List[BSONDocument]] =
      collection
      .find(query)
      .cursor[BSONDocument]
      .collect[List]()

    //    var list = new ListBuffer[BSONDocument]
//
//    futureList.map { documentList =>
//      documentList.foreach { doc =>
//        list+=doc
//      }
//    }
//
//    println(list.toList.size)
//    Json.obj("result" -> list.toList.size)
    futureList
  }
}

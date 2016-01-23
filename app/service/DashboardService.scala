package service

import com.mongodb.{DBObject, BasicDBObject}
import com.mongodb.casbah.MongoClient
import reactivemongo.bson.BSONDocument

/**
 * Created by prayagupd
 * on 1/23/16.
 */

class DashboardService {
  val DbName = "events-db"
  val CollectionName = "EventStream"

  val connection = MongoClient("localhost", 27017)

  def listDocuments(): List[DBObject] = {
    val db = connection(DbName)
    val collection = db(CollectionName)
    val docs = collection.find(new BasicDBObject()).toList
    docs
  }
}

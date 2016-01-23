import java.util.Date

import com.github.athieriot.{CleanAfterExample, EmbedConnection}
import com.mongodb.BasicDBObject
import com.mongodb.casbah.{MongoCollection, MongoDB, MongoClient}
import org.junit.runner.RunWith
import org.specs2.specification.Contexts
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import service.DashboardService

/**
 * Created by prayagupd
 * on 1/23/16.
 */

@RunWith(classOf[JUnitRunner])
class DashboardServiceSpec extends Specification with EmbedConnection
                                                 with CleanAfterExample {
  val DbName = "events-db"
  val CollectionName = "Events"

  "listDocuments" should {
    Contexts.doBefore {
      val connection = MongoClient("localhost", 27017)
      val db = getDatabase(connection)
      val collection = getCollection(db)

      val query = new BasicDBObject("_id", "abc")
        .append("eventType", "some-eventType")
        .append("created", new Date().getTime)
      collection.insert(query)
    }

    "return one document" in {
      val dashboardService = new DashboardService
      dashboardService.listDocuments().size mustEqual 1
    }
  }

  def getDatabase(client: MongoClient): MongoDB = {
    client(DbName)
  }

  def getCollection(db: MongoDB): MongoCollection = {
    db(CollectionName)
  }
}

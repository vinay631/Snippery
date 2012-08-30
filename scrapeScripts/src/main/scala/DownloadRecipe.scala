import scala.io.Source
import com.mongodb.casbah.Imports._

class DownloadRecipe(links:List[(String, String)]) {
  def download() = {
    val mongoConn = MongoConnection()
    val mongoDB = mongoConn("CodeSNP")("SNP")
    val mongoObj = MongoDBObject() 
    
    links foreach(l => 
      try {    
        mongoDB.insert(MongoDBObject("title" -> l._2, "code" -> getData(l._1), "lang" -> "python"))
      } catch {
        case _ => println("bad url : " + l._1) 
      }
    )
  }

  def getData(url:String):String = {
    val splits = url.split("/")
    val downloadURL = (splits slice(0, splits.length - 1)).reduceLeft(_ + "/" + _) + "/download/1"
    val source = Source.fromURL(new java.net.URL(downloadURL))
    val data = source.getLines().mkString("\n")
    
    data
  }
}


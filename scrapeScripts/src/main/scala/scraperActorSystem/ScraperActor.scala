package scraperActorSystem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import scala.collection.JavaConversions._
import akka.actor._

case class Message(url:String)

class ScraperActor(listener:ActorRef) extends Actor {

  def getLinks(url:String):List[(String, String)] = {
    val doc = Jsoup.connect(url).timeout(0).get()
    val links = doc.select("span.recipe-title").map(x => (x.select("a[href]").attr("abs:href"), x.select("a[href]").text)).toList
    links
  }
  
  def receive = {
    case Message(url) =>
      val lnks = getLinks(url).toList  
      listener ! "Done"      
  }
}


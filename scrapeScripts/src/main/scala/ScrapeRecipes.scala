import akka.actor._
import akka.dispatch.Await
import akka.dispatch.Future
import akka.pattern.ask
import akka.util.duration._
import akka.util.Timeout
import com.mongodb.casbah.Imports._
import scraperActorSystem.ListenerActor

class ScrapeRecipes() {
  implicit val timeout = Timeout(200 seconds)
  
  def scrape() = {
    val system = ActorSystem()
    val listener = system.actorOf(Props[ListenerActor])
    val future = listener ? "Start" 
    val result = Await.result(future, timeout.duration).asInstanceOf[List[(String, String)]]   
    system.shutdown()
    println("Exporting to mongo")
    val importMongo = new DownloadRecipe(result)
    importMongo.download
  }
}

object ScrapeLnks extends App{
  val app = new ScrapeRecipes
  app.scrape()
}

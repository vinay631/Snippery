import scraperActorSystem.Message
import scraperActorSystem.ScraperActor
import scraperActorSystem.ListenerActor
import akka.actor._
import akka.routing.RoundRobinRouter

class ScrapeRecipes(numPages:Int = 190) {
  val activeStateURL = "http://code.activestate.com/recipes/langs/python/?page="
  val nrOfWorkers = 3

  def scrape() = {
    val system = ActorSystem()
    val listener = system.actorOf(Props(new ListenerActor(numPages)))
    val scraper = system.actorOf(Props(new ScraperActor(listener)).withRouter(RoundRobinRouter(nrOfWorkers)))  

    (1 to numPages).foreach(i => scraper ! Message(activeStateURL + i.toString))
  }
}

object ScrapeLnks extends App{
  val app = new ScrapeRecipes
  app.scrape()
}

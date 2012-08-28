package scraperActorSystem
import akka.actor._
import akka.routing.RoundRobinRouter

class ListenerActor() extends Actor {
  var senderAct:ActorRef = _
  val numPages = 190
  var msgRemain = numPages
  val activeStateURL = "http://code.activestate.com/recipes/langs/python/?page="
  val nrOfWorkers = 3
  var linksList:List[(String, String)] = List() 

  def receive = {
    
    case "Start" =>
      val scraper = context.actorOf(Props[ScraperActor].withRouter(RoundRobinRouter(nrOfWorkers)))
      senderAct = sender
      (1 to numPages).foreach(i => scraper ! Message(activeStateURL + i.toString))
    
    case Result(links) =>
      linksList :::= links
      msgRemain -= 1
      if(msgRemain == 0) 
        senderAct ! linksList  
  }
}


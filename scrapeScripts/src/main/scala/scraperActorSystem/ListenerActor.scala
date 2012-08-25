package scraperActorSystem
import akka.actor._

class ListenerActor(numOfMsg:Int) extends Actor {
  var msgRemain = numOfMsg
  
  def receive = {
    case _ =>
      msgRemain -= 1
      if(msgRemain == 0)
        context.system.shutdown()
  }
}


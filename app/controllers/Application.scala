package controllers

import actors.TwitterStreamer
import play.api.mvc._
import play.api.Play.current
import play.api.libs.json._

class Application extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index("Tweets"))
  }

  def tweets = WebSocket.acceptWithActor[String, JsValue] {
    request => out => TwitterStreamer.props(out)
  }

  def replicatedFeed = Action { implicit request =>
    Ok.feed(TwitterStreamer.subscribeNode)
  }
}

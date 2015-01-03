package com.github.otterviews

import java.text.SimpleDateFormat

import spray.json._

object CustomJsonProtocol extends DefaultJsonProtocol {

  implicit object PostJsonFormat extends RootJsonFormat[Post] {
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")

    override def write(post: Post): JsValue = {
      JsObject("title" -> JsString(post.title),
        "content" -> JsString(post.content),
        "date" -> JsString(dateFormat.format(post.date)))
    }

    override def read(json: JsValue): Post = {
      json.asJsObject.getFields("title", "content", "date") match {
        case Seq(JsString(title), JsString(content), JsString(date)) =>
          new Post(title, content, dateFormat.parse(date))
        case _ => throw new DeserializationException("Invalid post data")

      }
    }
  }

//  implicit object MapJsonFormat extends RootJsonFormat[Map[String, Post]] {
//    override def write(obj: Map[String, Post]): JsValue = ???
//
//    override def read(json: JsValue): Map[String, Post] = ???
//  }

}

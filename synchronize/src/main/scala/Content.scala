import java.util.Date

import spray.json.{ JsObject, JsString }

class Content(title: String, content: String, date: Date) {
  val this.title: String = title
  val this.content: String = content
  val this.date: Date = date

  def toJson(): String = {
    JsObject(
      "title" -> JsString(title),
      "content" -> JsString(content),
      "date" -> JsString(date.toString)
    ).toString()
  }
}
package com.github.otterviews.jsonprotocol

import java.text.SimpleDateFormat
import java.util.Date

import spray.json._

trait DateJsonProtocol extends DefaultJsonProtocol {

  implicit object DateJsonFormat extends RootJsonFormat[Date] {
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-M-d")

    def write(date: Date) = {
      JsString(dateFormat.format(date))
    }

    def read(dateValue: JsValue) = dateValue match {
      case JsString(date) =>
        dateFormat.parse(date)
      case _ => deserializationError("Invalid Date")
    }
  }

}

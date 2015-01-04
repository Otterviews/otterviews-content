package com.github.otterviews.jsonprotocol

import com.github.otterviews.model.Post

object PostJsonProtocol extends DateJsonProtocol {
  implicit val postFormat = jsonFormat3(Post)

}

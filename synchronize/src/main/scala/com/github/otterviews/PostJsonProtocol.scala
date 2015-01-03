package com.github.otterviews


object PostJsonProtocol extends DateJsonProtocol {
  implicit val postFormat = jsonFormat3(Post.apply)
}

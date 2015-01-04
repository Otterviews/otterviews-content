package com.github.otterviews.service

import com.github.otterviews.jsonprotocol.PostJsonProtocol._
import com.github.otterviews.model.Post
import spray.json._

import scala.collection.immutable.List
import scalaj.http.Http

case class PostService() {
  def updatePosts(posts: List[Post], uri: String) =
    getNewPosts(posts, uri).map(post => postToFirebase(post, uri))

  private[this] def getNewPosts(posts: List[Post], uri: String): Set[Post] = {
    posts.toSet -- getAllFromFirebase(uri).toSet
  }

  private[this] def getAllFromFirebase(uri: String): Iterable[Post] =
    Http(uri).method("GET").asString.body.parseJson.asJsObject.fields.map {
      case (key, value) =>
        value.convertTo[Post]
    }

  private[this] def postToFirebase(postData: Post, postURI: String) =
    Http(postURI).postData(postData.toJson.toString).header("content-type", "application/json").method("POST").asString.code

}

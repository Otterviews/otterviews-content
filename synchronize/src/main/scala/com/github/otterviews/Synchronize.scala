// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.github.otterviews

import com.github.otterviews.PostJsonProtocol._
import com.typesafe.config.{ Config, ConfigFactory }
import shapeless._
import shapeless.record._
import shapeless.syntax.singleton._
import spray.json._

import scala.collection.JavaConversions._
import scala.collection.immutable.List
import scalaj.http._

object Synchronize {

  def main(args: Array[String]): Unit =
    ConfigFactory.load.getConfigList("endpoints")
      .map(conf => recordFor(conf))
      .map(info => info + ("files" ->> Utils.getListOfFiles(info("path"))))
      .map(info => info + ("posts" ->> Utils.createPosts(info("files"))))
      .foreach(info => updateFirebase(info("posts"), info("uri")))

  private[this] def recordFor(conf: Config) =
    ("path" ->> conf.getString("path")) ::
      ("uri" ->> conf.getString("uri")) :: HNil

  private[this] def updateFirebase(posts: List[Post], uri: String) =
    (posts.toSet -- getAllFromFirebase(uri).toSet).map(post => postToFirebase(post, uri))

  private[this] def getAllFromFirebase(uri: String): Iterable[Post] =
    Http(uri).method("GET").asString.body.parseJson.asJsObject.fields.map {
      case (key, value) =>
        value.convertTo[Post]
    }

  private[this] def postToFirebase(postData: Post, postURI: String) =
    Http(postURI).postData(postData.toJson.toString).header("content-type", "application/json").method("POST").asString.code
}

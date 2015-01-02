package com.github.otterviews

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

import com.typesafe.config.{ Config, ConfigFactory }
import shapeless._
import shapeless.record._
import shapeless.syntax.singleton._

import scalaj.http._

object Synchronize {

  val config: Config = ConfigFactory.load()

  def main(args: Array[String]): Unit = {
    Seq(configFor("posts"), configFor("drafts"))
      .map(info => info + ("files" ->> Utils.getListOfFiles(info("path"))))
      .map(info => info + ("contents" ->> Utils.createJsonFor(info("files"))))
      .foreach(info => updateFirebase(info("contents"), info("uri")))
  }

  private[this] def configFor(name: String) =
    ("path" ->> config.getConfig(name).getString("path")) ::
      ("uri" ->> config.getConfig(name).getString("uri")) :: HNil

  private[this] def updateFirebase(contents: List[String], uri: String) = {
    deleteFromFirebase(uri)
    postAllToFirebase(contents, uri)
  }

  private[this] def postAllToFirebase(content: List[String], uri: String) =
    content.map(postToFirebase(_, uri))

  private[this] def postToFirebase(postData: String, postURI: String) =
    Http(postURI).postData(postData).header("content-type", "application/json").method("POST").asString.code

  private[this] def deleteFromFirebase(deleteURI: String) =
    Http(deleteURI).method("DELETE").asString.code

}
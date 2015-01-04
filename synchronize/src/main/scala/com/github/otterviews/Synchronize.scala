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

import com.github.otterviews.mapper.PostMapper
import com.github.otterviews.service.PostService
import com.typesafe.config.{ Config, ConfigFactory }
import shapeless._
import shapeless.record._
import shapeless.syntax.singleton._

import scala.collection.JavaConversions._

object Synchronize {

  def main(args: Array[String]): Unit =
    ConfigFactory.load.getConfigList("endpoints")
      .map(conf => recordFor(conf))
      .map(info => info + ("files" ->> PostMapper.getListOfFiles(info("path"))))
      .map(info => info + ("posts" ->> PostMapper.createPosts(info("files"))))
      .foreach(info => PostService().updatePosts(info("posts"), info("uri")))

  private[this] def recordFor(conf: Config) =
    ("path" ->> conf.getString("path")) ::
      ("uri" ->> conf.getString("uri")) :: HNil

}

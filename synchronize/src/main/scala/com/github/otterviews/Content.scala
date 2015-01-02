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

import java.text.SimpleDateFormat
import java.util.Date

import spray.json.{ JsObject, JsString }

case class Content(title: String, content: String, date: Date) {

  def toJson: String = JsObject(
    "title" -> JsString(title),
    "content" -> JsString(content),
    "date" -> JsString(new SimpleDateFormat("yyyy-MM-dd").format(date))
  ).toString()

}

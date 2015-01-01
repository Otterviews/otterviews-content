import java.io.File

import scalaj.http._

object Synchronize {

  def postToFirebase(postData: String, postURI: String): Int = {
    Http(postURI).postData(postData).header("content-type", "application/json").method("POST").asString.code
  }

  def deleteFromFirebase(deleteURI: String) = {
    Http(deleteURI).method("DELETE").asString.code
  }

  def main(args: Array[String]) = {
    val posts: List[File] = Utils.getListOfFiles("../posts")
    val drafts: List[File] = Utils.getListOfFiles("../drafts")
    val postsJson: List[String] = Utils.createJsonFor(posts)
    val draftsJson: List[String] = Utils.createJsonFor(drafts)
    deleteFromFirebase(System.getenv("POST_URI"))
    deleteFromFirebase(System.getenv("DRAFT_URI"))
    postsJson.map(postJson => postToFirebase(postJson, System.getenv("POST_URI")))
    draftsJson.map(postJson => postToFirebase(postJson, System.getenv("DRAFT_URI")))
  }
}
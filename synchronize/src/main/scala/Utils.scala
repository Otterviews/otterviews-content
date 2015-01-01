import java.io.File
import java.util.Date

import scala.io.Source

object Utils {
  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def createContent(file: File): String = {
    new Content(file.getName, Source.fromFile(file.getAbsolutePath).mkString, new Date()).toJson()
  }

  def createJsonFor(files: List[File]) = {
    files.map(file => createContent(file))

  }
}

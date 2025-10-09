package cli

import scala.io.Source
import java.io.File
import java.nio.file.{Paths, Files, Path}

object CommandLineInterface {
  def main(args: Array[String]): Unit = {
    if(args.length < 1) {
      println("Usage: {Keyword}")
      return 1
    }

    val keyword = args.toSeq(0).toLowerCase.strip
    val homeFolder = System.getProperty("user.home")
    val note_dir = Paths.get(homeFolder).resolve(".cache/note-grep")
    val fileList = new File(note_dir.toString())
      .listFiles
      .filter(_.getName.endsWith(".yml"))

    fileList.foreach(f => {
      val src = Source.fromFile(f)
      val fileContents = src.getLines.mkString("\n")
      val splitfile = fileContents.split("---")

      for (l <- splitfile){
        if(l.toLowerCase.contains(keyword)){
          println("|----------")
          println(l)
          println("|----------")
        }
      }
      src.close()
    })

    return 0
  }
}

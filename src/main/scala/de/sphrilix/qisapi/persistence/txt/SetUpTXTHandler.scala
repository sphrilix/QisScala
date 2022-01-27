package de.sphrilix.qisapi.persistence.txt
import java.io.File
import scala.io.Source
import scala.util.Try

object SetUpTXTHandler extends TXTHandler[Map[String, String]]:

  override def readFrom(file: File): Map[String, String] =
    val source = Source.fromFile(file)
    val lines = source.getLines()
    val content = lines.flatMap(l => Try(l.split("=")(0).trim() -> l.split("=")(1).trim()).toOption)
      .toMap
    source.close()
    content
  end readFrom

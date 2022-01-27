package de.sphrilix.qisapi.api.parser

import de.sphrilix.qisapi.api.AuthenticationException
import de.sphrilix.qisapi.dto.Course
import org.jsoup.Jsoup

import scala.jdk.CollectionConverters.*
import scala.util.Try

object QisParser {

  private val AsiREG = "asi=(.+?)\"".r

  def parseLinks(html: String): Iterable[String] =
    Jsoup.parse(html).select("div").asScala
      .filter(_.hasClass("content"))
      .map(_.select("a"))
      .flatMap(_.asScala)
      .filter(_.attr("title").nonEmpty)
      .map(_.attr("href"))
  end parseLinks

  def parseGrades(html: String): Iterable[Course] =
    Jsoup.parse(html).select("table").asScala
      .filter(_.attr("summary").isEmpty)
      .flatMap(_.select("tr").asScala)
      .map(_.select("td"))
      .filter(_.size() == 9)
      .map(_.asScala.map(_.text()).toList).flatMap(cells => Try(Course(courseNumber = cells.headOption.get,
      name = cells.lift(1).get,
      grade = cells.lift(3).get,
      ects = cells.lift(5).get,
      passed = cells.lift(4).get)).toOption)
  end parseGrades

  def parseASI(html: String): String =
    AsiREG.findFirstIn(html)
      .getOrElse(throw new AuthenticationException)
      .replace("asi=", "")
      .replace("\"", "")
  end parseASI
}

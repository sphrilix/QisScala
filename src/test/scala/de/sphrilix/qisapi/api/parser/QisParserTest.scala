package de.sphrilix.qisapi.api.parser

import de.sphrilix.qisapi.dto.Course
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class QisParserTest extends AnyFlatSpec with Matchers {

  private val linksHtml =
    """
      |<div class="content">
      | <a title="asdf" href="test.de">
      |</div>
      |""".stripMargin

  "Parsing links" should "return List['test.de']" in {
    QisParser.parseLinks(linksHtml).toList shouldBe List("test.de")
  }

  private val asiHtml =
    """
      |<html>
      | <a asi="1dsads234sdada">
      |</html>
      |""".stripMargin

  "Parsing asi" should "return '1dsads234sdada'" in {
    QisParser.parseASI(asiHtml) shouldBe "1dsads234sdada"
  }

  private val gradesHtml =
    """
      |<table>
      | <tr>
      | <td>42</td>
      | <td>Scala</td>
      | <td></td>
      | <td>1,0</td>
      | <td>bestanden</td>
      | <td>9,0</td>
      | <td></td>
      | <td></td>
      | <td></td>
      | </tr>
      |</table>
      |""".stripMargin

  "Parsing grades" should "return a list with exactly one course" in {
    QisParser.parseGrades(gradesHtml).toList shouldBe List(Course("42", "Scala", "1,0", "9,0", "bestanden"))
  }
}

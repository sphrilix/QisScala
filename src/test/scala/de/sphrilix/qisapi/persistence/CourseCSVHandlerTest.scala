package de.sphrilix.qisapi.persistence

import de.sphrilix.qisapi.dto.Course
import de.sphrilix.qisapi.persistence.csv.CourseCSVHandler
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File

class CourseCSVHandlerTest extends AnyFlatSpec with Matchers {

  "The List of Courses" should "be the same after saving and reading" in {
    val f = File("/tmp/qis_scala.csv")
    val l = List(Course("1", "name", "2,0", "9,0", "bestanden"), Course("2", "na", "5,0", "9,0", "nicht bestanden"))
    CourseCSVHandler.writeTo(l, f)
    val lAfter = CourseCSVHandler.readFrom(f)
    lAfter shouldBe l
  }

}

package de.sphrilix.qisapi.dto

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CourseTest extends AnyFlatSpec with Matchers {
  private val exampleCourse: Course = Course("42", "Scala", "1,0", "9,0", "bestanden")
  private val exampleCourseStringRepr: String = "                  42|                                               " 
    + "                            Scala|       1.0|       9.0\n"

  "toCSV" should "return '42,Scala,1.0,9.0,true'" in {
    println(exampleCourse)
    exampleCourse.toCSV shouldBe "42,Scala,1.0,9.0,true\n"
  }

  "toString" should "return a nicely formatted string" in {
    exampleCourse.toString shouldBe exampleCourseStringRepr
  }

}

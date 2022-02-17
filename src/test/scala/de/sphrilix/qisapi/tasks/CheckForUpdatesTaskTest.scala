package de.sphrilix.qisapi.tasks

import de.sphrilix.qisapi.api.{QisAPI, QisAPIImpl}
import de.sphrilix.qisapi.dto.Course
import de.sphrilix.qisapi.persistence.csv.CourseCSVHandler
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.File

class CheckForUpdatesTaskTest extends AnyFlatSpec with Matchers {

  "Check update" should "return false" in {
    val f = File("/tmp/qis_scala.csv")
    val l = List(Course("1", "name", "2,0", "9,0", "bestanden"), Course("2", "na", "5,0", "9,0", "nicht bestanden"))
    CourseCSVHandler.writeTo(l, f)
    CheckForUpdate(QisAPIMock(), f).run() shouldBe false
  }

  "Check update" should "first return true then false"  in {
    val f = File("/tmp/qis_scala.csv")
    val l = List(Course("1", "name", "2,0", "9,0", "bestanden"))
    CourseCSVHandler.writeTo(l, f)
    CheckForUpdate(QisAPIMock(), f).run() shouldBe true
    CheckForUpdate(QisAPIMock(), f).run() shouldBe false
  }

 class QisAPIMock extends QisAPI {
   override def getGrades: List[Course] = List(Course("1", "name", "2,0", "9,0", "bestanden"),
     Course("2", "na", "5,0", "9,0", "nicht bestanden"))
 }
}

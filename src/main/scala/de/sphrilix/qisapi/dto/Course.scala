package de.sphrilix.qisapi.dto

import de.sphrilix.qisapi.dto.Course.{MaxCourseNrLength, MaxEctsLength, MaxGradeLength, MaxNameLength, shorten}
import de.sphrilix.qisapi.utils.PrettyPrint.nTimesSpace

case class Course private (courseNumber: String,
                  name: String,
                  grade: Double,
                  ects: Double,
                  passed: Boolean):

  override def toString: String =
    val ppName = shorten(name, MaxNameLength)
    s"${nTimesSpace(MaxCourseNrLength - courseNumber.length) + courseNumber}|" +
      s"${nTimesSpace(MaxNameLength - ppName.length) + ppName}|" +
      s"${nTimesSpace(MaxGradeLength - grade.toString.length) + grade}|" +
      s"${nTimesSpace(MaxEctsLength - ects.toString.length) + ects}\n"
  end toString

  def toCSV: String =
    s"$courseNumber,$name,$grade,$ects,$passed\n"


object Course:
  private val MaxCourseNrLength = 20
  private val MaxNameLength = 80
  private val MaxGradeLength = 10
  private val MaxEctsLength = 10

  def apply(courseNumber: String, name: String, grade: String,ects: String, passed: String): Course =
    Course(courseNumber,
      name,
      grade.replace(",", ".").toDouble,
      ects.replace(",", ".").toDouble,
      parsePassed(passed))
  end apply


  private def shorten(str: String, maxLength: Int): String =
    if (str.length >= maxLength)
      str.substring(0, maxLength - 5) + "..."
    else
      str
    end if
  end shorten

  private def parsePassed(str: String): Boolean = str == "bestanden" || str == "true"

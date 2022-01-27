package de.sphrilix.qisapi.persistence.csv

import de.sphrilix.qisapi.dto.Course
import de.sphrilix.qisapi.persistence.csv.CSVHandler

import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.Try

object CourseCSVHandler extends CSVHandler[List[Course]]:

  override def readFrom(file: File): List[Course] =
    val source = Source.fromFile(file)
    val lines = source.getLines()
    val content = lines.map(_.split(",")).flatMap(rawCourse => Try(Course(courseNumber = rawCourse(0),
      name = rawCourse(1),
      grade = rawCourse(2),
      ects = rawCourse(3),
      passed = rawCourse(4))).toOption)
      .toList
    source.close()
    content
  end readFrom

  override def writeTo(content: List[Course], file: File): Boolean =
    val csvCourses = content.map(_.toCSV).mkString("\n")
    val writer = PrintWriter(file)
    writer.write(csvCourses)
    writer.close()
    writer.checkError()
  end writeTo

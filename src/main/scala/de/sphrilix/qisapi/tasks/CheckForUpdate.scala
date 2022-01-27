package de.sphrilix.qisapi.tasks

import de.sphrilix.qisapi.api.QisAPI
import de.sphrilix.qisapi.dto.Course
import de.sphrilix.qisapi.persistence.csv.CourseCSVHandler
import de.sphrilix.qisapi.tasks.QisTask

import java.io.File
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class CheckForUpdate(api: QisAPI, file: File) extends QisTask[Boolean]:

  override def run(): Boolean = 
    val savedGrades = CourseCSVHandler.readFrom(file)
    val apiGrades = api.getGrades
    val updated = savedGrades.size != apiGrades.size
    CourseCSVHandler.writeTo(apiGrades, file)
    updated
  end run


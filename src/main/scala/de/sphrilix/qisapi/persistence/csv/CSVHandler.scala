package de.sphrilix.qisapi.persistence.csv

import de.sphrilix.qisapi.persistence.FileReader

import java.io.File

trait CSVHandler[T] extends FileReader[File, T]:

  def writeTo(content: T, file: File): Boolean

  override def readFrom(file: File): T


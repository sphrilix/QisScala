package de.sphrilix.qisapi.persistence.txt

import de.sphrilix.qisapi.persistence.FileReader

import java.io.File

trait TXTHandler[T] extends FileReader[File, T]:
  override def readFrom(file: File): T

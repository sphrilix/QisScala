package de.sphrilix.qisapi.persistence

trait FileReader[T, U]:
  def readFrom(t: T): U

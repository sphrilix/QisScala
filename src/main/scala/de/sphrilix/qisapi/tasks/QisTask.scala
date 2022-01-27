package de.sphrilix.qisapi.tasks

import scala.concurrent.Future

trait QisTask[T]:
  def run(): T

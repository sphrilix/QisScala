package de.sphrilix.qisapi.api

import de.sphrilix.qisapi.dto.Course

trait QisAPI {

  def getGrades: List[Course]
}

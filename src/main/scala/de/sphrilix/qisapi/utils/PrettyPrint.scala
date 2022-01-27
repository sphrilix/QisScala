package de.sphrilix.qisapi.utils

import de.sphrilix.qisapi.dto.Course

object PrettyPrint:
  def nTimesChar(c: Char, n: Int): String = 
    if n < 2 then
      ""
    else
      LazyList.range(0, n).map(_ => c.toString).reduce(_ + _)
    end if
  end nTimesChar
  

  def nTimesSpace(n: Int): String = nTimesChar(' ', n)

  def ppCourses(courses: Iterable[Course]): String = 
    if courses.isEmpty then
      "No grades found!"
    else 
      val coursesStr: String = courses.map(_.toString).reduce(_ + _)
      val border = nTimesChar('=', 123) + "\n"
      val head = s"Number${nTimesSpace(15)}Course${nTimesSpace(75)}Grade${nTimesSpace(6)}ECTS\n"
      head + border + coursesStr + border
    end if
  end ppCourses

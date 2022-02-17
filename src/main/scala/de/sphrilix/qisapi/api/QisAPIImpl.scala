package de.sphrilix.qisapi.api

import QisAPIImpl.{AsiEXT, LoginEXT, OverviewEXT}
import de.sphrilix.qisapi.api.http.QisURL
import de.sphrilix.qisapi.api.parser.QisParser.{parseASI, parseGrades, parseLinks}
import de.sphrilix.qisapi.dto.Course
import scalaj.http.HttpResponse


case class QisAPIImpl private(private val loginFormParams: Seq[(String, String)]) extends QisAPI:

  private val cookies = login.cookies

  private val asi = extractASI

  private def retrieveGradeURLs: Iterable[QisURL] = 
    QisURL(OverviewEXT, asi).get(cookies).links.map(QisURL(_))

  private def login: HttpResponse[String] = 
    QisURL(LoginEXT).postForm(loginFormParams)

  private def extractASI: String = 
    QisURL(AsiEXT).get(cookies).asi
   

  def getGrades: List[Course] =
    retrieveGradeURLs.flatMap(url => url.get(cookies).grades).toList

object QisAPIImpl:

  private val LoginEXT = "rds?state=user&type=1&category=auth.login&startpage=portal.vm&breadCrumbSource=portal"

  private val AsiEXT = "rds?state=change&type=1&moduleParameter=studyPOSMenu&nextdir=change" +
    "&next=menu.vm&subdir=applications&xml=menu&purge=y" +
    "&navigationPosition=functions%2CstudyPOSMenu&breadcrumb=studyPOSMenu&topitem=functions&subitem=studyPOSMenu"

  private val OverviewEXT = f"rds?state=notenspiegelStudent&struct=auswahlBaum&navigation=Y&next=tree.vm" +
    "&nextdir=qispos/notenspiegel/student&nodeID=auswahlBaum%7Cabschluss%3Aabschl%3DMR%2Cstgnr%3D1" +
    "&expand=0&lastState=notenspiegelStudent&asi="

  def apply(username: String, pwd: String): QisAPIImpl = new QisAPIImpl(Seq("asdf" -> username, "fdsa" -> pwd))


extension (response: HttpResponse[String]) {
  def asi: String = parseASI(response.body)

  def links: Iterable[String] = parseLinks(response.body)

  def grades: Iterable[Course] = parseGrades(response.body)
}
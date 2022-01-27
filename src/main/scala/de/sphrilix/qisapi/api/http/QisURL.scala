package de.sphrilix.qisapi.api.http

import de.sphrilix.qisapi.api.http
import scalaj.http.{Http, HttpResponse}

import java.net.HttpCookie

case class QisURL private (URL: String):

  def postForm(params: Seq[(String, String)]): HttpResponse[String] = 
    Http(URL).postForm(params).asString

  def get(cookies: Seq[HttpCookie]): HttpResponse[String] = 
    Http(URL).cookies(cookies).asString

object QisURL:

  private val BaseURL = "https://qisserver.uni-passau.de/qisserver/"

  def apply(extensions: String*): QisURL =
    if (!extensions.exists(_.contains(BaseURL)))
      new QisURL(BaseURL + extensions.reduce(_ + _))
    else
      new QisURL(extensions.reduce(_ + _))
    end if
  end apply
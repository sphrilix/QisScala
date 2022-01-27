package de.sphrilix.qisapi.dto

case class SMTPConfig private (username: String, password: String, smtp: String, port: Int)

object SMTPConfig:
  def apply(args: Map[String, String]): SMTPConfig =
    SMTPConfig(args("mailUsername"),
      args("mailPassword"),
      args("smtp"),
      args("port").toInt)
  end apply

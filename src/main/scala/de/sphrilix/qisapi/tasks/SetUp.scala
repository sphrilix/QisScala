package de.sphrilix.qisapi.tasks

import de.sphrilix.qisapi.dto.{Message, SMTPConfig}

import scala.concurrent.Future
import java.io.File
import scala.io.Source

case class SetUp(file: File, receiver: String, conf: SMTPConfig) extends QisTask[Future[Unit]]:
  
  override def run(): Future[Unit] =
    createFileForSaving()
    notifyOfInit()
  end run

  private def createFileForSaving(): Unit =
    if !file.exists() then
      file.createNewFile()
  end createFileForSaving
  
  private def notifyOfInit(): Future[Unit] =
    val msg = Message("Init", "Initialized for checking on updates.", receiver)
    NotifyWithMsg(conf, msg).run()
  end notifyOfInit


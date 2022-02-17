package de.sphrilix.qisapi.shell

import de.sphrilix.qisapi.api.{AuthenticationException, QisAPI, QisAPIImpl}
import de.sphrilix.qisapi.dto.{Course, Message, SMTPConfig}
import de.sphrilix.qisapi.logging.{Level, QisLogger}
import de.sphrilix.qisapi.persistence.txt.SetUpTXTHandler
import de.sphrilix.qisapi.tasks.{CheckForUpdate, NotifyWithMsg, SetUp}
import de.sphrilix.qisapi.utils.PrettyPrint.ppCourses

import scala.util.{Failure, Success}
import scala.annotation.tailrec
import scala.sys.exit
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.{File, FileNotFoundException}

object Shell:

  def main(args: Array[String]): Unit =
    val argList = args.toList
    val clArgMap = filterOutCLArgs(argList, Map())

    try
      val initFileArgs = SetUpTXTHandler.readFrom(File(clArgMap.getOrElse("f",
        logAndExit("No init File found")).toString))
      val username = initFileArgs.getOrElse("qisUsername", logAndExit("No username found."))
      val pwd = initFileArgs.getOrElse("qisPassword", logAndExit("No password found."))
      val apiSup = () => QisAPIImpl(username, pwd)
      val smtpSup = () => SMTPConfig(initFileArgs)
      val file = File("./saveFile.csv")
      val receiver = initFileArgs.getOrElse("receiver", logAndExit("No receiver found!"))
      if clArgMap.contains("p") then
        QisLogger.log(Level.INFO, "Just print grades.")
        println(ppCourses(apiSup.apply().getGrades))
      else
        SetUp(file, receiver, smtpSup.apply()).run().onComplete {
          case Success(_) => QisLogger.log(Level.INFO, "Initialized for checking on updates.")
          case Failure(_) => logAndExit("Your mail credentials or smtp-config are wrong.")
        }
        QisLogger.log(Level.INFO, "Check for updates periodically.")
        updateAndNotify(apiSup, file, smtpSup, receiver)
    catch
      case e: AuthenticationException => logAndExit("Qis credentials are wrong")
      case e: FileNotFoundException => logAndExit("Init file not found.")
      case _ => logAndExit("Some unknown error occured.")
    end try

  end main

  @tailrec
  private def filterOutCLArgs(argList: List[String], map: Map[String, Any]): Map[String, Any] =
    argList match
      case "-a" :: tail => filterOutCLArgs(tail, Map("a" -> true) ++ map)
      case "-p" :: tail => filterOutCLArgs(tail, Map("p" -> true) ++ map)
      case "-f" :: value :: tail => filterOutCLArgs(tail, Map("f" -> value) ++  map)
      case Nil => map
      case _ => Map()
    end match
  end filterOutCLArgs

  private def logAndExit(cause: String): Nothing =
    QisLogger.log(Level.ERROR, cause)
    exit(1)
  end logAndExit

  @tailrec
  private def updateAndNotify(apiSup: () => QisAPI, file: File, confSup: () => SMTPConfig, receiver: String): Unit =
    if CheckForUpdate(apiSup.apply(), file).run() then
      QisLogger.log(Level.INFO, "Found new grade.")
      val message = Message("Update", "You got a new grade! Check: 'https://qisserver.uni-passau.de/'", receiver)
      NotifyWithMsg(confSup.apply(), message).run()
    else
      QisLogger.log(Level.INFO, "No new grade found.")
    end if
    Thread.sleep(15 * 60_000)
    updateAndNotify(apiSup, file, confSup, receiver)
  end updateAndNotify

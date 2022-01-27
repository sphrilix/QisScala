package de.sphrilix.qisapi.logging

import de.sphrilix.qisapi.logging
import de.sphrilix.qisapi.logging.Level

import de.sphrilix.qisapi.logging.Logger

object QisLogger extends Logger:
  
  
  override def log(lvl: Level, msg: String): Unit =
    lvl match 
      case Level.INFO => info(msg)
      case Level.WARNING => warning(msg)
      case Level.ERROR => error(msg)
    end match
  end log

  private def info(msg: String): Unit =
    out(s"[QisScala] INFO: $msg")

  private def warning(msg: String): Unit =
    out(s"[QisScala] WARNING: $msg")

  private def error(msg: String): Unit =
    out(s"[QisScala] ERROR: $msg")


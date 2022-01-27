package de.sphrilix.qisapi.logging

trait Logger:

  protected var out: String => Unit = println
  
  def log(lvl: Level, msg: String): Unit

  def injectOut(out: String => Unit): Unit = this.out = out

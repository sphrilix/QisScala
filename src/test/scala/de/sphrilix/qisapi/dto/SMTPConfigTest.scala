package de.sphrilix.qisapi.dto

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SMTPConfigTest extends AnyFlatSpec with Matchers {
  val initArgsExample = Map("mailPassword" -> "1234", "mailUsername" -> "admin", "smtp" -> "smtp", "port" -> "42")

  "Apply whit initArgsExample" should "return a smtpConfig and not throw an eror" in {
    val conf = SMTPConfig(initArgsExample)
    conf.username shouldBe "admin"
    conf.password shouldBe "1234"
    conf.smtp shouldBe "smtp"
    conf.port shouldBe 42
  }
}

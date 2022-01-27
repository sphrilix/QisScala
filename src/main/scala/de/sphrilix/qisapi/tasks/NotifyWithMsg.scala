package de.sphrilix.qisapi.tasks

import courier.{Envelope, Mailer, Text, addr}
import de.sphrilix.qisapi.dto.{Message, SMTPConfig}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class NotifyWithMsg(private val smtpConfig: SMTPConfig,
                         private val msg: Message) extends QisTask[Future[Unit]]:


  private val mailer = Mailer(smtpConfig.smtp, smtpConfig.port)
    .auth(true)
    .as(smtpConfig.username, smtpConfig.password)
    .startTls(true)()

  override def run(): Future[Unit] =
    mailer(Envelope.from("update" `@` "qisscala.de")
     .to(msg.receiver.split("@")(0) `@` msg.receiver.split("@")(1))
     .subject(msg.subject)
     .content(Text(msg.msg)))
  end run

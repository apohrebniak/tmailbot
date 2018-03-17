package com.github.apohrebniak.tmail.mail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.server.SMTPServer;

@Slf4j
@AllArgsConstructor
public class MailServerStarter implements InitializingBean {

  private MessageHandlerFactory messageHandlerFactory;
  private int port;

  @Override
  public void afterPropertiesSet() throws Exception {
    startServer(messageHandlerFactory, port);
  }

  private void startServer(MessageHandlerFactory factory, int port) {
    SMTPServer smtpServer = new SMTPServer(factory);
    smtpServer.setPort(port);
    log.info("SMTP started");
    smtpServer.start();
  }
}

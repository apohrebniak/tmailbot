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
  private SMTPProperties properties;

  @Override
  public void afterPropertiesSet() throws Exception {
    startServer();
  }

  private void startServer() {
    SMTPServer smtpServer = new SMTPServer(messageHandlerFactory);
    smtpServer.setPort(properties.getPort());
    smtpServer.setMaxMessageSize(properties.getMaxMessageSize());
    log.info("SMTP server started on port: " + properties.getPort());
    smtpServer.start();
  }
}

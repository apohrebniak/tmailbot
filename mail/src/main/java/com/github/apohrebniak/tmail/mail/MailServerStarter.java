package com.github.apohrebniak.tmail.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.server.SMTPServer;

@Slf4j
@Component
public class MailServerStarter implements InitializingBean {

  @Autowired
  private MessageHandlerFactory messageHandlerFactory;
  @Autowired
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

package com.gethatch.apohrebniak.tmail.mail;

import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.server.SMTPServer;

public class MailServerStarter {

  public void startServer(MessageHandlerFactory factory) {
    SMTPServer smtpServer = new SMTPServer(factory);
    smtpServer.setPort(2222);
    System.out.println("Started at thread: " + Thread.currentThread().getName());
    smtpServer.start();
  }

  public static void main(String[] args) {
    MailServerStarter serverStarter = new MailServerStarter();
    serverStarter.startServer(new MessageHandlerFactoryImpl());
  }
}

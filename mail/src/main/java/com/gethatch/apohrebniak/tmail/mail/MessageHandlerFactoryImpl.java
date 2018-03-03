package com.gethatch.apohrebniak.tmail.mail;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;

public class MessageHandlerFactoryImpl implements MessageHandlerFactory {

  @Override
  public MessageHandler create(MessageContext ctx) {
    System.out.println("Factory at thread: " + Thread.currentThread().getName());
    return new MessageHandlerImpl();
  }
}

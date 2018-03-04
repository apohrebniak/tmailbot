package com.github.apohrebniak.tmail.mail;

import com.github.apohrebniak.tmail.core.RecipientRegistry;
import lombok.AllArgsConstructor;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;

@AllArgsConstructor
public class MessageHandlerFactoryImpl implements MessageHandlerFactory {

  private RecipientRegistry recipientRegistry;

  @Override
  public MessageHandler create(MessageContext ctx) {
    return new MessageHandlerImpl(recipientRegistry);
  }
}

package com.github.apohrebniak.tmail.mail;

import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;

@AllArgsConstructor
public class MessageHandlerFactoryImpl implements MessageHandlerFactory {

  private MailboxRegistry mailboxRegistry;
  private EventBus eventBus;

  @Override
  public MessageHandler create(MessageContext ctx) {
    return new MessageHandlerImpl(mailboxRegistry, eventBus);
  }
}

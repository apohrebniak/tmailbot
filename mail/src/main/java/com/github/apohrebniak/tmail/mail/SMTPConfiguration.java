package com.github.apohrebniak.tmail.mail;

import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.subethamail.smtp.MessageHandlerFactory;

@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SMTPConfiguration {

  private MailboxRegistry mailboxRegistry;
  private EventBus eventBus;
  private SMTPProperties properties;


  @Bean
  public MailServerStarter mailServerStarter(MessageHandlerFactory factory) {
    return new MailServerStarter(factory, properties);
  }

  @Bean
  public MessageHandlerFactory messageHandlerFactory() {
    return new MessageHandlerFactoryImpl(mailboxRegistry, eventBus, properties);
  }
}

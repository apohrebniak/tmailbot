package com.github.apohrebniak.tmail.mail;

import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.subethamail.smtp.MessageHandlerFactory;

@Configuration
public class SMTPConfiguration {

  @Value("${tmail.smtp.port}")
  private Integer smtpPort;
  @Autowired
  private MailboxRegistry mailboxRegistry;
  @Autowired
  private EventBus eventBus;


  @Bean
  @Autowired
  public MailServerStarter mailServerStarter(MessageHandlerFactory factory) {
    return new MailServerStarter(factory, smtpPort);
  }

  @Bean
  public MessageHandlerFactory messageHandlerFactory() {
    return new MessageHandlerFactoryImpl(mailboxRegistry, eventBus);
  }
}

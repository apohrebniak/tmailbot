package com.github.apohrebniak.tmail.mail;

import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.google.common.eventbus.EventBus;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;

@Configuration
public class SMTPConfiguration {

  @Bean(name = "emailProcessingExecutor")
  public Executor executor() {
    return Executors.newCachedThreadPool();
  }

  @Bean
  @Scope("prototype")
  public MessageHandler messageHandler(MailboxRegistry mailboxRegistry,
      EventBus eventBus,
      SMTPProperties properties) {
    return new MessageHandlerImpl(mailboxRegistry, eventBus, properties);
  }

  @Bean
  public MessageHandlerFactory messageHandlerFactory(MailboxRegistry mailboxRegistry,
      EventBus eventBus,
      SMTPProperties properties) {
    return ctx -> messageHandler(mailboxRegistry, eventBus, properties);
  }

}

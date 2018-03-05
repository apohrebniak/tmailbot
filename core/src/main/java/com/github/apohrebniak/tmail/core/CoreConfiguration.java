package com.github.apohrebniak.tmail.core;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

  @Bean
  public EventBus eventBus() {
    return new EventBus();
  }

}

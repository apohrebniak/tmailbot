package com.github.apohrebniak.tmail.core;

import com.google.common.eventbus.EventBus;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

  @Bean
  public EventBus eventBus() {
    return new EventBus();
  }

  @Bean(name = "onExpiredEventExecutor")
  public Executor executor() {
    return Executors.newCachedThreadPool();
  }

}

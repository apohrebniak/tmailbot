package com.github.apohrebniak.tmail.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

  @Bean(name = "apiPool")
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(2);
  }
}

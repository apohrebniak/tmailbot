package com.github.apohrebniak.tmail;

import com.github.apohrebniak.tmail.core.command.CommandGateway;
import com.github.apohrebniak.tmail.core.command.CommandGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TmailConfiguration {

  @Bean
  public CommandGateway commandGateway() {
    return new CommandGatewayImpl();
  }
}

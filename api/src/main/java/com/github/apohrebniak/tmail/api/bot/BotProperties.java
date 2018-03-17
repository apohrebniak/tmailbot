package com.github.apohrebniak.tmail.api.bot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tmail.bot")
@Data
public class BotProperties {

  private String token;

  private String telegramUrlPattern;

  private Long timeout;
}
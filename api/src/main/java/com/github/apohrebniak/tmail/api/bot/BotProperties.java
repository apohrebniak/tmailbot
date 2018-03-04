package com.github.apohrebniak.tmail.api.bot;

import java.net.URI;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@ConfigurationProperties(prefix = "tmail.bot")
@Data
public class BotProperties {

  private String token;

  private String host;

  private String telegramUrl;

  public URI getTelegramUri() {
    return UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(telegramUrl)
        .pathSegment("/bot" + token)
        .build()
        .toUri();
  }

  public URI getUpdateUri() {
    return UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(host)
        .pathSegment(token)
        .build()
        .toUri();
  }
}
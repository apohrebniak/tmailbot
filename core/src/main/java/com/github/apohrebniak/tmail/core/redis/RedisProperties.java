package com.github.apohrebniak.tmail.core.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("tmail.redis")
public class RedisProperties {

  private String host;
  private Integer port;
}

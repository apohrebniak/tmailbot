package com.github.apohrebniak.tmail.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("tmail.api")
public class ApiProperties {

  private String mailboxHost;
  private String timeFormat;
}

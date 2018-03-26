package com.github.apohrebniak.tmail.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("tmail.smtp")
public class SMTPProperties {

  private Integer port;

  private Integer maxMessageSize;

  private String domain;
}

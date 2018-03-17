package com.github.apohrebniak.tmail.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("tmail.core")
public class CoreProperties {

  private Integer ttl;
}

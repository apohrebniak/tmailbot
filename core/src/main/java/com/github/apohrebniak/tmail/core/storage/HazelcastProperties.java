package com.github.apohrebniak.tmail.core.storage;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "tmail.hazelcast")
@Configuration
@Data
public class HazelcastProperties {

  public List<String> addresses;
}

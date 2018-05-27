package com.github.apohrebniak.tmail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication(exclude = HazelcastAutoConfiguration.class)
@EnableConfigurationProperties
public class TmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmailApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new LoggingHttpRequestInterceptor()));
        return restTemplate;
    }
}

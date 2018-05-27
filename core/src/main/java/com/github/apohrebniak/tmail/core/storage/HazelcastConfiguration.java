package com.github.apohrebniak.tmail.core.storage;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public ClientConfig clientConfig(HazelcastProperties properties) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().setAddresses(properties.getAddresses());
        return clientConfig;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Bean(name = "userToMailboxMap")
    public IMap<Long, String> userToMailboxMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("userToMailboxMap");
    }

    @Bean(name = "mailboxToUserMap")
    public IMap<String, Long> mailboxToUserMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("mailboxToUserMap");
    }
}

package com.github.apohrebniak.tmail.core.storage;

import com.github.apohrebniak.tmail.core.MailboxExpiredListener;
import com.github.apohrebniak.tmail.core.domain.MailboxUserPair;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.listener.EntryExpiredListener;
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
  public IMap<String, Long> mailboxToUserMap(HazelcastInstance hazelcastInstance,
      MailboxExpiredListener listener) {
    IMap<String, Long> mailboxToUserMap = hazelcastInstance.getMap("mailboxToUserMap");
    mailboxToUserMap.addEntryListener((EntryExpiredListener<String, Long>) event ->
        listener.onMailboxExpired(MailboxUserPair.of(event.getKey(), event.getOldValue())), true);
    return mailboxToUserMap;
  }
}

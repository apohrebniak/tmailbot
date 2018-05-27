package com.github.apohrebniak.tmail.core.storage;

import com.github.apohrebniak.tmail.core.CoreProperties;
import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.domain.MailboxUserPair;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.IMap;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HazelcastMailboxRegistry implements MailboxRegistry {

  @Autowired
  private CoreProperties coreProperties;
  @Autowired
  private IMap<String, Long> mailboxToUserMap;

  @Override
  public boolean exists(String id) {
    return mailboxToUserMap.containsKey(id);
  }

  @Override
  public void add(MailboxUserPair pair) {
    mailboxToUserMap.put(pair.getMailboxId(), pair.getUserId(),
        coreProperties.getTtl(), TimeUnit.MINUTES);
  }

  @Override
  public Optional<MailboxRecord> getMailboxById(String id) {
    EntryView<String, Long> entryView = mailboxToUserMap.getEntryView(id);
    return Optional.ofNullable(entryView.getValue())
        .map(m -> new MailboxRecord(id, m,
            expirationMillisToTtlLong(entryView.getExpirationTime())));
  }

  @Override
  public void removeById(String id) {
    mailboxToUserMap.remove(id);
  }

  private static long expirationMillisToTtlLong(Long expiration) {
    return Instant.ofEpochMilli(expiration).minus(Instant.now().toEpochMilli(), ChronoUnit.MILLIS)
        .getEpochSecond();
  }
}

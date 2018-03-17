package com.github.apohrebniak.tmail.core.redis;

import com.github.apohrebniak.tmail.core.CoreProperties;
import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.domain.MailboxUserIds;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RedisMailboxRegistry implements MailboxRegistry {

  private CoreProperties coreProperties;
  private RedisTemplate<String, Long> redis;

  @Override
  public boolean exists(String id) {
    return Optional.ofNullable(redis.boundValueOps(id).get()).isPresent();
  }

  @Override
  public void add(MailboxUserIds mailboxUserIds) {
    redis.boundValueOps(mailboxUserIds.getMailboxId())
        .set(mailboxUserIds.getTelegramId(), coreProperties.getTtl(), TimeUnit.MINUTES);
  }

  @Override
  public Optional<MailboxRecord> getMailboxById(String id) {
    return Optional.ofNullable(redis.boundValueOps(id).get())
        .map(u -> new MailboxRecord(id, u, redis.boundValueOps(id).getExpire()));
  }
}

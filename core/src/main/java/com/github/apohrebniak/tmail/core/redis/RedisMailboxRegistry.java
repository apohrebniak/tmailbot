package com.github.apohrebniak.tmail.core.redis;

import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.github.apohrebniak.tmail.core.MailboxUserPair;
import com.github.apohrebniak.tmail.core.exception.MailboxExpiredException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RedisMailboxRegistry implements MailboxRegistry {

  private Integer ttl;
  private RedisTemplate<String, Long> redis;

  @Override
  public boolean exists(String id) {
    return Optional.ofNullable(redis.boundValueOps(id).get()).isPresent();
  }

  @Override
  public void add(MailboxUserPair mailboxUserPair) {
    redis.boundValueOps(mailboxUserPair.getMailbox())
        .set(mailboxUserPair.getTelegramId(), ttl, TimeUnit.MINUTES);
  }

  @Override
  public Optional<Long> getUserIdById(String id) {
    return Optional.ofNullable(redis.boundValueOps(id).get());
  }

  @Override
  public Long getTimeLeftById(String id) {
    Long expire = redis.boundValueOps(id).getExpire();
    if (expire < 0) {
      throw new MailboxExpiredException(id);
    }
    return expire;
  }
}

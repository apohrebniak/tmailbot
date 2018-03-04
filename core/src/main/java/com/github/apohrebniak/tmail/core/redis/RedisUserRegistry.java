package com.github.apohrebniak.tmail.core.redis;

import com.github.apohrebniak.tmail.core.MailboxUserPair;
import com.github.apohrebniak.tmail.core.UserRegistry;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RedisUserRegistry implements UserRegistry {

  private RedisTemplate<Long, String> redis;

  @Override
  public void add(MailboxUserPair pair) {
    redis.boundValueOps(pair.getTelegramId())
        .set(pair.getMailbox());
  }

  @Override
  public boolean exists(Long userId) {
    return Optional.ofNullable(redis.boundValueOps(userId).get())
        .isPresent();
  }

  @Override
  public Optional<String> getMailboxById(Long id) {
    return Optional.ofNullable(redis.boundValueOps(id).get());
  }
}

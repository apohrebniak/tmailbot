package com.github.apohrebniak.tmail.core.redis;

import com.github.apohrebniak.tmail.core.Recipient;
import com.github.apohrebniak.tmail.core.RecipientRegistry;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RedisRecipientRegistry implements RecipientRegistry {

  private Integer ttl;
  private RedisTemplate<String, Long> redis;

  @Override
  public boolean exists(String id) {
    Long r = redis.boundValueOps(id).get();
    return Optional.ofNullable(redis.boundValueOps(id).get()).isPresent();
  }

  @Override
  public void add(Recipient recipient) {
    redis.boundValueOps(recipient.getId())
        .set(recipient.getTelegramId(), ttl, TimeUnit.MINUTES);
  }

  @Override
  public Optional<Long> getById(String id) {
    return Optional.ofNullable(redis.boundValueOps(id).get());
  }
}

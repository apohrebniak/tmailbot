package com.github.apohrebniak.tmail.core.redis;

import com.github.apohrebniak.tmail.core.UserRegistry;
import com.github.apohrebniak.tmail.core.domain.MailboxUserIds;
import com.github.apohrebniak.tmail.core.domain.UserRecord;
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
  public void add(MailboxUserIds pair) {
    redis.boundValueOps(pair.getTelegramId())
        .set(pair.getMailboxId());
  }

  @Override
  public boolean exists(Long userId) {
    return Optional.ofNullable(redis.boundValueOps(userId).get())
        .isPresent();
  }

  @Override
  public Optional<UserRecord> getUserRecordById(Long id) {
    Optional<String> optionalMailboxId = Optional.ofNullable(redis.boundValueOps(id).get());
    return optionalMailboxId.map(s -> new UserRecord(id, s));
  }
}

package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxUserIds;
import com.github.apohrebniak.tmail.core.domain.UserRecord;
import com.github.apohrebniak.tmail.core.event.MailboxExpiredEvent;
import com.google.common.eventbus.EventBus;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailboxExpiredNotificationService implements InitializingBean {

  @Value("${tmail.core.ttl}")
  private Integer ttl;
  private ScheduledExecutorService executorService;
  private EventBus eventBus;
  private UserRegistry userRegistry;

  @Autowired
  public MailboxExpiredNotificationService(
      ScheduledExecutorService executorService, EventBus eventBus) {
    this.executorService = executorService;
    this.eventBus = eventBus;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.executorService = Executors.newSingleThreadScheduledExecutor();
  }

  public void scheduleMailboxExpiration(final MailboxUserIds mailboxUserIds) {
    log.info("Schedule mailbox expiring for pair: [" + mailboxUserIds + "]");
    if (userStillHasMailbox(mailboxUserIds)) {
      executorService.schedule(() ->
              eventBus.post(MailboxExpiredEvent
                  .builder()
                  .mailboxId(mailboxUserIds.getMailboxId())
                  .userId(mailboxUserIds.getTelegramId())
                  .build()),
          ttl,
          TimeUnit.MINUTES);
    }
  }

  private boolean userStillHasMailbox(MailboxUserIds mailboxUserIds) {
    return mailboxUserIds.getMailboxId()
        .equals(userRegistry.getUserRecordById(mailboxUserIds.getTelegramId())
            .map(UserRecord::getMailboxId).orElse(null));
  }
}

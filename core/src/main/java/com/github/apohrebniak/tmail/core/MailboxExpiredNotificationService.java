package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxUserIds;
import com.github.apohrebniak.tmail.core.domain.UserRecord;
import com.github.apohrebniak.tmail.core.event.MailboxExpiredEvent;
import com.google.common.eventbus.EventBus;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MailboxExpiredNotificationService implements InitializingBean {

  private CoreProperties coreProperties;
  private ScheduledExecutorService executorService;
  private EventBus eventBus;
  private UserRegistry userRegistry;

  @Override
  public void afterPropertiesSet() throws Exception {
    this.executorService = Executors.newSingleThreadScheduledExecutor();
  }

  public void scheduleMailboxExpiration(final MailboxUserIds mailboxUserIds) {
    if (mailboxIsActive(mailboxUserIds)) {
      executorService.schedule(() -> {
            log.info("Mailbox {} has expired", mailboxUserIds.getMailboxId());
            eventBus.post(MailboxExpiredEvent
                .builder()
                .mailboxId(mailboxUserIds.getMailboxId())
                .userId(mailboxUserIds.getTelegramId())
                .build());
          },
          coreProperties.getTtl(),
          TimeUnit.MINUTES);
    }
  }

  private boolean mailboxIsActive(MailboxUserIds mailboxUserIds) {
    return mailboxUserIds.getMailboxId()
        .equals(userRegistry.getUserRecordById(mailboxUserIds.getTelegramId())
            .map(UserRecord::getMailboxId).orElse(null));
  }
}

package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.domain.MailboxUserPair;
import com.github.apohrebniak.tmail.core.domain.UserRecord;
import com.github.apohrebniak.tmail.core.event.MailboxExpiredEvent;
import com.github.apohrebniak.tmail.core.util.MailboxStringIdFactory;
import com.google.common.eventbus.EventBus;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailboxService implements MailboxExpiredListener {

  @Autowired
  private UserRegistry userRegistry;
  @Autowired
  private MailboxRegistry mailboxRegistry;
  @Autowired
  private EventBus eventBus;

  public MailboxRecord createNewMailboxForUser(Long userId) {
    MailboxUserPair mailboxUserPair =
        new MailboxUserPair(MailboxStringIdFactory.generateId(), userId);

    removePreviousMailbox(mailboxUserPair);
    userRegistry.add(mailboxUserPair);
    mailboxRegistry.add(mailboxUserPair);

    log.info("Created mailbox {} for user {}", mailboxUserPair.getMailboxId(),
        mailboxUserPair.getUserId());

    return getMailboxForUser(userId).orElse(null);
  }

  @Override
  @Async("onExpiredEventExecutor")
  public void onMailboxExpired(MailboxUserPair pair) {
    log.info("Mailbox {} has expired", pair.getMailboxId());
    eventBus.post(MailboxExpiredEvent
        .of(pair.getMailboxId(), pair.getUserId()));
  }

  public Optional<MailboxRecord> getMailboxForUser(Long userId) {
    Optional<String> optionalMailboxId = userRegistry
        .getUserRecordById(userId)
        .map(UserRecord::getMailboxId);
    return optionalMailboxId.isPresent()
        ? mailboxRegistry.getMailboxById(optionalMailboxId.get())
        : Optional.empty();
  }

  private void removePreviousMailbox(MailboxUserPair pair) {
    Optional<UserRecord> mailboxId = userRegistry.getUserRecordById(pair.getUserId());
    mailboxId.ifPresent(userRecord -> mailboxRegistry.removeById(userRecord.getMailboxId()));
  }

}

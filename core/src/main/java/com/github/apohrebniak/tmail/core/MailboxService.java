package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.domain.MailboxUserIds;
import com.github.apohrebniak.tmail.core.domain.UserRecord;
import com.github.apohrebniak.tmail.core.util.MailboxStringIdFactory;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MailboxService {

  private UserRegistry userRegistry;
  private MailboxRegistry mailboxRegistry;
  private MailboxExpiredNotificationService expiredNotificationService;

  public MailboxRecord createNewMailboxForUser(Long userId) {
    MailboxUserIds mailboxUserIds =
        new MailboxUserIds(MailboxStringIdFactory.generateId(), userId);

    userRegistry.add(mailboxUserIds);
    mailboxRegistry.add(mailboxUserIds);
    expiredNotificationService.scheduleMailboxExpiration(mailboxUserIds);

    return getMailboxForUser(userId).get();
  }

  public Optional<MailboxRecord> getMailboxForUser(Long userId) {
    Optional<String> optionalMailboxId = userRegistry
        .getUserRecordById(userId)
        .map(UserRecord::getMailboxId);
    return optionalMailboxId.isPresent()
        ? mailboxRegistry.getMailboxById(optionalMailboxId.get())
        : Optional.empty();
  }
}

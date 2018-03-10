package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.domain.MailboxUserIds;
import com.github.apohrebniak.tmail.core.domain.UserRecord;
import com.github.apohrebniak.tmail.core.exception.NoMailboxForUserException;
import com.github.apohrebniak.tmail.core.util.MailboxStringIdFactory;
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

  public String createNewMailboxForUser(Long userId) {
    MailboxUserIds mailboxUserIds =
        new MailboxUserIds(MailboxStringIdFactory.generateId(), userId);

    userRegistry.add(mailboxUserIds);
    mailboxRegistry.add(mailboxUserIds);
    expiredNotificationService.scheduleMailboxExpiration(mailboxUserIds);

    return mailboxUserIds.getMailboxId();
  }

  public MailboxRecord getMailboxForUser(Long userId) throws NoMailboxForUserException {
    String mailboxId = userRegistry.getUserRecordById(userId).map(UserRecord::getMailboxId)
        .orElseThrow(() -> new NoMailboxForUserException(userId));
    return mailboxRegistry.getMailboxById(mailboxId)
        .orElseThrow(() -> new NoMailboxForUserException(userId));
  }
}

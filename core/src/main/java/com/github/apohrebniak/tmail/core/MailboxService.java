package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.exception.MailboxExpiredException;
import com.github.apohrebniak.tmail.core.exception.NoMailboxForUserException;
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

  public String createNewMailboxForUser(Long userId) {
    MailboxUserPair mailboxUserPair =
        new MailboxUserPair(MailboxStringIdFactory.generateId(), userId);

    userRegistry.add(mailboxUserPair);
    mailboxRegistry.add(mailboxUserPair);

    return mailboxUserPair.getMailbox();
  }

  public Long getMailboxTimeLeft(Long userId)
      throws MailboxExpiredException, NoMailboxForUserException {
    String mailboxId = userRegistry.getMailboxById(userId)
        .orElseThrow(() -> new NoMailboxForUserException(userId));
    return mailboxRegistry.getTimeLeftById(mailboxId);
  }
}

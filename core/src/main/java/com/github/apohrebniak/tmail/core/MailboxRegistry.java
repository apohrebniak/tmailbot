package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.domain.MailboxUserIds;
import java.util.Optional;

public interface MailboxRegistry {

  boolean exists(String id);

  void add(MailboxUserIds mailboxUserIds);

  Optional<MailboxRecord> getMailboxById(String id);
}

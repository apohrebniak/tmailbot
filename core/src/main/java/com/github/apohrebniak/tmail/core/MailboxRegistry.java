package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.domain.MailboxUserPair;
import java.util.Optional;

public interface MailboxRegistry {

  boolean exists(String id);

  void add(MailboxUserPair mailboxUserPair);

  Optional<MailboxRecord> getMailboxById(String id);

  void removeById(String id);
}

package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.exception.MailboxExpiredException;
import java.util.Optional;

public interface MailboxRegistry {

  boolean exists(String id);

  void add(MailboxUserPair mailboxUserPair);

  Optional getUserIdById(String id);

  Long getTimeLeftById(String id) throws MailboxExpiredException;
}

package com.github.apohrebniak.tmail.core;

import java.util.Optional;

public interface UserRegistry {

  void add(MailboxUserPair pair);

  boolean exists(Long userId);

  Optional<String> getMailboxById(Long id);
}

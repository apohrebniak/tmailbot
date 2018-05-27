package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxUserPair;
import com.github.apohrebniak.tmail.core.domain.UserRecord;
import java.util.Optional;

public interface UserRegistry {

  void add(MailboxUserPair pair);

  boolean exists(Long userId);

  Optional<UserRecord> getUserRecordById(Long id);
}

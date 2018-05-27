package com.github.apohrebniak.tmail.core;

import com.github.apohrebniak.tmail.core.domain.MailboxUserPair;

public interface MailboxExpiredListener {

  void onMailboxExpired(MailboxUserPair pair);
}

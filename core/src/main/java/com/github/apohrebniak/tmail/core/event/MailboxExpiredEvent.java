package com.github.apohrebniak.tmail.core.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailboxExpiredEvent {

  private String mailboxId;
  private Long userId;

  public static MailboxExpiredEvent of(String mailboxId, Long userId) {
    return new MailboxExpiredEvent(mailboxId, userId);
  }
}

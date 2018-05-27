package com.github.apohrebniak.tmail.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MailboxUserPair {

  private String mailboxId;
  private Long userId;

  public static MailboxUserPair of(String mailboxId, Long telegramId) {
    return new MailboxUserPair(mailboxId, telegramId);
  }
}

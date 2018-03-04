package com.github.apohrebniak.tmail.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MailboxUserPair {

  private String mailbox;
  private Long telegramId;
}

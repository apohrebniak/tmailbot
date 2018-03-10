package com.github.apohrebniak.tmail.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MailboxUserIds {

  private String mailboxId;
  private Long telegramId;
}

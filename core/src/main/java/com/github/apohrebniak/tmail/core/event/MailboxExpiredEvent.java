package com.github.apohrebniak.tmail.core.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MailboxExpiredEvent {

  private String mailboxId;
  private Long userId;
}

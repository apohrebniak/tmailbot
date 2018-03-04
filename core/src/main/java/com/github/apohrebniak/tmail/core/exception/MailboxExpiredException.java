package com.github.apohrebniak.tmail.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailboxExpiredException extends RuntimeException {

  private String mailboxId;
}

package com.github.apohrebniak.tmail.core;

import java.util.UUID;

public class MailboxStringIdFactory {

  public static String generateId() {
    return UUID.randomUUID()
        .toString()
        .replaceAll("-", "")
        .substring(0, 12);
  }
}

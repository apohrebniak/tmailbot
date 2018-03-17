package com.github.apohrebniak.tmail.api.message;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Messages {
  UNKNOWN_COMMAND("unknown_command"),
  EXPIRED("expired"),
  NEW_EMAIL("new_email"),
  GET_TIME("get_time"),
  NEW_MAILBOX("mailbox"),
  NO_MAILBOX("no_mailbox"),
  ME("mailbox"),
  START("start");

  String value;


  @Override
  public String toString() {
    return this.value;
  }
}

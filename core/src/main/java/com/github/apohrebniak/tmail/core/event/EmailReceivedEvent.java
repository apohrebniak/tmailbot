package com.github.apohrebniak.tmail.core.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simplejavamail.email.Email;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailReceivedEvent {

  private Long userId;
  private Email email;

  public static EmailReceivedEvent of(Long userId, Email email) {
    return new EmailReceivedEvent(userId, email);
  }
}

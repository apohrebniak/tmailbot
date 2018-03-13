package com.github.apohrebniak.tmail.api.bot.message;

import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import org.simplejavamail.email.Email;
import org.springframework.stereotype.Component;

@Component
public class OutMessageFactory {


  public static OutMessage buildEmailReceivedMessage(Long userId, Email message) {
    return null;
  }

  public static OutMessage buildMailboxExpiredMessage(Long userId) {
    return null;
  }

  public static OutMessage buildMeMessage(Long userId, MailboxRecord e) {
    return null;
  }

  public static OutMessage buildNoMailboxMessage(Long id) {
    return null;
  }

  public static OutMessage buildNewMailboxMessage(Long id, MailboxRecord mailbox) {
    return null;
  }

  public static OutMessage buildTimeLeftMessage(Long userId, MailboxRecord e) {
    return null;
  }

  public static OutMessage buildUnknownCommandMessage(Long id) {
    return null;
  }
}

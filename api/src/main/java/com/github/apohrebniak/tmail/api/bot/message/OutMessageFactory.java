package com.github.apohrebniak.tmail.api.bot.message;

import static com.github.apohrebniak.tmail.api.bot.message.Messages.UNKNOWN_COMMAND;

import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import java.util.ResourceBundle;
import org.simplejavamail.email.Email;
import org.springframework.stereotype.Component;

@Component
public class OutMessageFactory {

  private final ResourceBundle bundle;

  public OutMessageFactory() {
    this.bundle = ResourceBundle.getBundle("messages");
  }

  public OutMessage buildEmailReceivedMessage(Long userId, Email message) {
    return new OutMessage(userId, "placeholder");
  }

  public OutMessage buildMailboxExpiredMessage(Long userId) {
    return new OutMessage(userId, "placeholder");
  }

  public OutMessage buildMeMessage(Long userId, MailboxRecord e) {
    return new OutMessage(userId, "placeholder");
  }

  public OutMessage buildNoMailboxMessage(Long userId) {
    return new OutMessage(userId, "placeholder");
  }

  public OutMessage buildNewMailboxMessage(Long userId, MailboxRecord mailbox) {
    return new OutMessage(userId, "placeholder");
  }

  public OutMessage buildTimeLeftMessage(Long userId, MailboxRecord e) {
    return new OutMessage(userId, "placeholder");
  }

  public OutMessage buildUnknownCommandMessage(Long id) {
    return new OutMessage(id, bundle.getString(UNKNOWN_COMMAND.toString()));
  }
}

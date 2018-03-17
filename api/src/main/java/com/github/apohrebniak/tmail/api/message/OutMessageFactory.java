package com.github.apohrebniak.tmail.api.message;

import com.github.apohrebniak.tmail.api.ApiProperties;
import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import java.time.Duration;
import java.util.ResourceBundle;
import org.simplejavamail.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutMessageFactory {

  private ResourceBundle bundle;
  private ApiProperties apiProperties;

  @Autowired
  public OutMessageFactory(ApiProperties apiProperties) {
    this.bundle = ResourceBundle.getBundle("messages");
    this.apiProperties = apiProperties;
  }

  public OutMessage buildEmailReceivedMessage(Long userId, Email message) {
    String text = String.format(bundle.getString(Messages.NEW_EMAIL.toString()),
        message.getFromRecipient().getAddress(),
        message.getSubject(),
        message.getPlainText());
    return new OutMessage(userId, text);
  }

  public OutMessage buildMailboxExpiredMessage(Long userId) {
    return new OutMessage(userId, bundle.getString(Messages.EXPIRED.toString()));
  }

  public OutMessage buildMeMessage(Long userId, MailboxRecord mailbox) {
    return new OutMessage(userId, String.format(bundle.getString(Messages.ME.toString()),
        formatEmailAddress(mailbox.getId()),
        formatPeriodOfSeconds(mailbox.getTtlSeconds())));
  }

  public OutMessage buildNoMailboxMessage(Long userId) {
    return new OutMessage(userId, bundle.getString(Messages.NO_MAILBOX.toString()));
  }

  public OutMessage buildNewMailboxMessage(Long userId, MailboxRecord mailbox) {
    return new OutMessage(userId, String.format(bundle.getString(Messages.NEW_MAILBOX.toString()),
        formatEmailAddress(mailbox.getId()),
        formatPeriodOfSeconds(mailbox.getTtlSeconds())));
  }

  public OutMessage buildUnknownCommandMessage(Long userId) {
    return new OutMessage(userId, bundle.getString(Messages.UNKNOWN_COMMAND.toString()));
  }

  public OutMessage buildStartMesssage(Long userId) {
    return new OutMessage(userId, bundle.getString(Messages.START.toString()));
  }

  private String formatEmailAddress(String mailboxId) {
    return mailboxId + "@" + apiProperties.getMailboxHost();
  }

  private String formatPeriodOfSeconds(Long seconds) {
    Duration duration = Duration.ofSeconds(seconds);
    return String.format(apiProperties.getTimeFormat(),
        duration.toMinutesPart(),
        duration.toSecondsPart());
  }
}

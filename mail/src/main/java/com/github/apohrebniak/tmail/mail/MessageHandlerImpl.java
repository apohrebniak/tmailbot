package com.github.apohrebniak.tmail.mail;

import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.event.EmailReceivedEvent;
import com.google.common.eventbus.EventBus;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.TooMuchDataException;

@Slf4j
public class MessageHandlerImpl implements MessageHandler {

  private MailboxRegistry mailboxRegistry;
  private EventBus eventBus;
  private SMTPProperties properties;

  private List<String> recipients = new LinkedList<>();

  @Autowired
  public MessageHandlerImpl(MailboxRegistry mailboxRegistry,
      EventBus eventBus, SMTPProperties properties) {
    this.mailboxRegistry = mailboxRegistry;
    this.eventBus = eventBus;
    this.properties = properties;
  }

  @Override
  public void from(String from) throws RejectException {
    log.info("From: " + from);
  }

  @Override
  public void recipient(String recipient) throws RejectException {
    log.info("Recipient: " + recipient);
    this.recipients.add(recipient);
  }

  @Override
  public void data(InputStream data) throws RejectException, TooMuchDataException, IOException {
    checkRecipientsDomain();
    processMessageStream(data);
  }

  @Override
  public void done() {
    log.info("Done");
  }

  private void processMessageStream(InputStream data) {
    try {
      MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()), data);
      final Email email = EmailConverter.mimeMessageToEmail(mimeMessage);
      email.getRecipients().stream()
          .map(Recipient::getAddress)
          .map(this::getUserFromEmailAddress)
          .filter(mailboxRegistry::exists)
          .forEach(m -> this.sendEmailReceivedEvent(m, email));
    } catch (Exception e) {
      log.error("Error while handling message! error={}", e);
    }
  }

  private void sendEmailReceivedEvent(String mailbox, Email email) {
    Optional optionalUserId = mailboxRegistry.getMailboxById(mailbox)
        .map(MailboxRecord::getUserId);
    if (optionalUserId.isPresent()) {
      eventBus.post(EmailReceivedEvent.of((Long) optionalUserId.get(), email));
    }
  }

  private void checkRecipientsDomain() throws RejectException {
    // check recipient domain
    Boolean hasValidRecipients = this.recipients.stream()
        .anyMatch(e -> this.getDomainFromEmailAddress(e).equals(properties.getDomain()));

    //check if recipient is bot's user
    hasValidRecipients &= this.recipients.stream()
        .map(this::getUserFromEmailAddress)
        .anyMatch(mailboxRegistry::exists);
    if (!hasValidRecipients) {
      log.info("Reject recipients={}", this.recipients);
      throw new RejectException();
    }
  }

  private String getDomainFromEmailAddress(String emailAddress) {
    return emailAddress.substring(emailAddress.indexOf('@') + 1);
  }

  private String getUserFromEmailAddress(String emailAddress) {
    return emailAddress.substring(0, emailAddress.indexOf('@'));
  }
}

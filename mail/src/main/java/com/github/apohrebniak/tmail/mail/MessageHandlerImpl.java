package com.github.apohrebniak.tmail.mail;

import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.event.EmailReceivedEvent;
import com.google.common.eventbus.EventBus;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.Recipient;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.TooMuchDataException;

@Slf4j
@AllArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

  private MailboxRegistry mailboxRegistry;
  private EventBus eventBus;


  @Override
  public void from(String from) throws RejectException {
    log.info("From: " + from);
  }

  @Override
  public void recipient(String recipient) throws RejectException {
    log.info("Recipient: " + recipient);
  }

  @Override
  public void data(InputStream data) throws RejectException, TooMuchDataException, IOException {
    try {
      MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()), data);
      final Email email = EmailConverter.mimeMessageToEmail(mimeMessage);
      email.getRecipients().stream()
          .map(Recipient::getAddress)
          .map(e -> e.substring(0, e.indexOf('@')))
          .filter(mailboxRegistry::exists)
          .forEach(mailbox -> {
            Optional optionalUserId = mailboxRegistry.getMailboxById(mailbox)
                .map(MailboxRecord::getUserId);
            if (optionalUserId.isPresent()) {
              eventBus.post(EmailReceivedEvent.of((Long) optionalUserId.get(), email));
            }
          });
    } catch (MessagingException e) {
      log.error("Error handling message! ", e);
    }
  }

  @Override
  public void done() {
    log.info("Done");
  }
}

package com.github.apohrebniak.tmail.mail;

import com.github.apohrebniak.tmail.core.MailboxRegistry;
import com.google.common.eventbus.EventBus;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simplejavamail.converter.EmailConverter;
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
    log.info("MailboxUserPair:" + recipient);
    String recipientId = recipient.substring(0, recipient.indexOf('@'));
    if (!mailboxRegistry.exists(recipientId)) {
      throw new RejectException();
    }
  }

  @Override
  public void data(InputStream data) throws RejectException, TooMuchDataException, IOException {
    try {
      MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()), data);
      eventBus.post(EmailConverter.mimeMessageToEmail(mimeMessage));
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void done() {
    log.info("Done");
  }
}

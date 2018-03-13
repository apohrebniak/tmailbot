package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.bot.message.OutMessageFactory;
import com.github.apohrebniak.tmail.api.telegram.Message;
import com.github.apohrebniak.tmail.api.telegram.Update;
import com.github.apohrebniak.tmail.api.telegram.User;
import com.github.apohrebniak.tmail.core.MailboxService;
import com.github.apohrebniak.tmail.core.domain.MailboxRecord;
import com.github.apohrebniak.tmail.core.event.EmailReceivedEvent;
import com.github.apohrebniak.tmail.core.event.MailboxExpiredEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Tmail implements InitializingBean {

  private Sender sender;
  private MailboxService mailboxService;
  private EventBus eventBus;

  @Override
  public void afterPropertiesSet() throws Exception {
    eventBus.register(this);
  }

  public void onUpdate(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      if (message.isCommand()) {
        processCommand(message);
      }
    }
  }

  private void processCommand(Message message) {
    switch (BotCommand.byValue(message.getText())) {
      case ME:
        processMeCommand(message.getUser());
        break;
      case NEW_MAILBOX:
        processCreateNewMailboxCommand(message.getUser());
        break;
      case GET_TIME:
        processGetTimeLeftCommand(message.getUser());
        break;
      default:
        processUnknownCommand(message.getUser());
    }
  }

  @Subscribe
  public void onEmailReceived(EmailReceivedEvent event) {
    log.debug("email = " + event.getMessage().getPlainText());
    sender.sendMessage(OutMessageFactory
        .buildEmailReceivedMessage(event.getUserId(), event.getMessage()));

  }

  @Subscribe
  public void onMailboxExpired(MailboxExpiredEvent event) {
    log.debug("expired = " + event);
    sender.sendMessage(OutMessageFactory
        .buildMailboxExpiredMessage(event.getUserId()));
  }

  private void processMeCommand(User user) {
    mailboxService.getMailboxForUser(user.getId())
        .ifPresentOrElse(
            e -> sender.sendMessage(OutMessageFactory
                .buildMeMessage(e.getUserId(), e)),
            () -> sender.sendMessage(OutMessageFactory
                .buildNoMailboxMessage(user.getId())));
  }

  private void processCreateNewMailboxCommand(User user) {
    MailboxRecord mailbox = mailboxService.createNewMailboxForUser(user.getId());

    log.debug("Mailbox  [" + mailbox.getId() + "] created");

    sender.sendMessage(OutMessageFactory
        .buildNewMailboxMessage(user.getId(), mailbox));
  }

  private void processGetTimeLeftCommand(User user) {
    mailboxService.getMailboxForUser(user.getId())
        .ifPresentOrElse(
            e -> sender.sendMessage(OutMessageFactory
                .buildTimeLeftMessage(e.getUserId(), e)),
            () -> sender.sendMessage(OutMessageFactory
                .buildNoMailboxMessage(user.getId())));
  }

  private void processUnknownCommand(User user) {
    log.debug("Unknown command.");
    sender.sendMessage(OutMessageFactory
        .buildUnknownCommandMessage(user.getId()));
  }

}

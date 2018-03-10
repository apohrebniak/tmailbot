package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.telegram.Message;
import com.github.apohrebniak.tmail.api.telegram.Update;
import com.github.apohrebniak.tmail.api.telegram.User;
import com.github.apohrebniak.tmail.core.MailboxService;
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

  private void sendMessage(Message message) {
    sender.sendMessage(message);
  }

  public void onUpdate(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      if (message.isCommand()) {
        processCommand(message);
      }
    }
  }

  @Subscribe
  public void onEmailReceived(EmailReceivedEvent event) {
    log.info("email = " + event.getMessage().getPlainText());
  }

  @Subscribe
  public void onMailboxExpired(MailboxExpiredEvent expiredEvent) {
    log.info("expired = " + expiredEvent);
  }


  private void processCommand(Message message) {
    switch (BotCommand.byValue(message.getText())) {
      case NEW_MAILBOX:
        createNewMailbox(message.getUser());
        break;
      case GET_TIME:
        getTimeLeft(message.getUser());
        break;
      default:
        replyUnknownCommand();
    }
  }

  private void createNewMailbox(User user) {
    String mailbox = mailboxService.createNewMailboxForUser(user.getId());
    log.info("Mailbox  [" + mailbox + "] created");
  }

  private void getTimeLeft(User user) {

  }

  private void replyUnknownCommand() {
    log.info("Unknown command.");
  }

}

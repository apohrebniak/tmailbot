package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.telegram.Message;
import com.github.apohrebniak.tmail.api.telegram.Update;
import com.github.apohrebniak.tmail.api.telegram.User;
import com.github.apohrebniak.tmail.core.MailboxService;
import com.github.apohrebniak.tmail.core.exception.MailboxExpiredException;
import com.github.apohrebniak.tmail.core.exception.NoMailboxForUserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Tmail {

  private Sender sender;
  private MailboxService mailboxService;

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
    try {
      mailboxService.getMailboxTimeLeft(user.getId());
    } catch (MailboxExpiredException e) {

    } catch (NoMailboxForUserException e) {

    }
  }

  private void replyUnknownCommand() {
    log.info("Unknown command.");
  }

}

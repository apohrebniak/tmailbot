package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.telegram.Message;
import com.github.apohrebniak.tmail.api.telegram.Update;
import com.github.apohrebniak.tmail.core.command.Command;
import com.github.apohrebniak.tmail.core.command.CommandGateway;
import com.github.apohrebniak.tmail.core.command.CreateMailBoxCommand;
import com.github.apohrebniak.tmail.core.command.GetTimeCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Tmail {

  private Sender sender;
  private CommandGateway commandGateway;

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
    try {
      commandGateway.sendCommandWithResult(fromMessage(message));
    } catch (CommandNotSupportedException e) {
      replyUnknownCommand();
    }
  }

  private void replyUnknownCommand() {
    log.debug("Unknown command.");
  }

  private Command fromMessage(Message message) {
    switch (BotCommand.valueOf(message.getText())) {
      case NEW_MAILBOX:
        CreateMailBoxCommand createMailBoxCommand = new CreateMailBoxCommand();
        createMailBoxCommand.setUserId(message.getUser().getId());
        return createMailBoxCommand;
      case GET_TIME:
        GetTimeCommand getTimeCommand = new GetTimeCommand();
        getTimeCommand.setUserId(message.getUser().getId());
        return getTimeCommand;
    }
    return null; //whatever
  }

}

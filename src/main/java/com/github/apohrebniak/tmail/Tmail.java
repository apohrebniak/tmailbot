package com.github.apohrebniak.tmail;

import com.github.apohrebniak.tmail.telegram.api.Message;
import com.github.apohrebniak.tmail.telegram.api.Update;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Tmail {

  private Sender sender;

  private void sendMessage(Message message) {
    sender.sendMessage(message);
  }

  public void onUpdate(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      if (message.isCommand()) {
        // process command
      } else {
        // I do not get it
      }
    }
  }


}

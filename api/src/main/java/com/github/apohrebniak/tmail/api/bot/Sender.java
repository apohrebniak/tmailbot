package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.telegram.Message;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Sender {

  private RestTemplate restTemplate;
  private BotProperties properties;

  public void sendMessage(Message message) {
  }

}

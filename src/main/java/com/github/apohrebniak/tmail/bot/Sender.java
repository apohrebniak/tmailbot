package com.github.apohrebniak.tmail.bot;

import com.github.apohrebniak.tmail.telegram.api.Message;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Sender {

  private RestTemplate restTemplate;
  private TmailProperties properties;

  public void sendMessage(Message message) {
  }

}

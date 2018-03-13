package com.github.apohrebniak.tmail.api.bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.apohrebniak.tmail.api.bot.message.OutMessage;
import com.github.apohrebniak.tmail.api.bot.message.ParseMode;
import com.github.apohrebniak.tmail.api.telegram.ApiMethod;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Sender {

  private BotProperties botProperties;
  private RestTemplate restTemplate;

  public void sendMessage(OutMessage msg) {
    SendMessageRequest request = new SendMessageRequest(msg.getChatId(),
        msg.getText(), msg.getParseMode());
    URI uri = URI.create(botProperties.getTelegramUrl() + botProperties.getToken()
        + "/" + ApiMethod.SEND_MESSAGE);
    restTemplate.exchange(RequestEntity.post(uri).body(request), String.class);
  }

  @Data
  static class SendMessageRequest {

    @JsonProperty("chatId")
    private final Long chatId;
    @JsonProperty("text")
    private final String formatedText;
    @JsonProperty("parse_mode")
    private final ParseMode parseMode;
  }

}

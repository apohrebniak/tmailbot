package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.message.OutMessage;
import com.github.apohrebniak.tmail.api.message.ParseMode;
import com.github.apohrebniak.tmail.api.telegram.ApiMethod;
import com.github.apohrebniak.tmail.api.telegram.template.SendMessageRequest;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessageSender {

  private BotProperties botProperties;
  private RestTemplate restTemplate;

  public void sendMessage(OutMessage msg) {

    SendMessageRequest request = SendMessageRequest.builder()
        .chatId(msg.getChatId())
        .formattedText(msg.getText())
        .parseMode(ParseMode.HTML)
        .build();

    String url = String.format(botProperties.getTelegramUrlPattern(),
        botProperties.getToken(), ApiMethod.SEND_MESSAGE.toString());

    restTemplate.exchange(RequestEntity
        .post(URI.create(url))
        .contentType(MediaType.APPLICATION_JSON)
        .body(request), String.class);
  }
}

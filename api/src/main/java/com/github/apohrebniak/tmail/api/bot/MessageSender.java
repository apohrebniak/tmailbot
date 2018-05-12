package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.message.OutMessage;
import com.github.apohrebniak.tmail.api.telegram.ApiMethod;
import com.github.apohrebniak.tmail.api.telegram.template.SendMessageRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessageSender {

  private BotProperties botProperties;
  private RestTemplate restTemplate;

  public void sendMessage(OutMessage msg) {

    SendMessageRequest request = SendMessageRequest.builder()
        .chatId(msg.getChatId())
        .formattedText(msg.getText())
        .disableWebPagePreview(true) //always disable page preview to avoid unexpected calls
        .build();

    String url = String.format(botProperties.getTelegramUrlPattern(),
        botProperties.getToken(), ApiMethod.SEND_MESSAGE.toString());

    executeRequest(url, request);
  }

  private void executeRequest(String url, SendMessageRequest request) {
    try {
      restTemplate.exchange(RequestEntity
          .post(URI.create(url))
          .contentType(MediaType.APPLICATION_JSON)
          .body(request), String.class);
    } catch (RestClientException e) {
      log.error("An error occurred while sending message!", e);
    }
  }
}

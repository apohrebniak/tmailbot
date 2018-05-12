package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.telegram.ApiMethod;
import com.github.apohrebniak.tmail.api.telegram.template.GetUpdatesRequest;
import com.github.apohrebniak.tmail.api.telegram.template.UpdatesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class UpdateReceiver implements ApplicationRunner {

  private final Tmail tmail;
  private final RestTemplate restTemplate;
  private final BotProperties botProperties;
  private final ExecutorService executorService;
  private Long offset = 0L;

  @Autowired
  public UpdateReceiver(Tmail tmail,
      RestTemplate restTemplate,
      BotProperties botProperties,
      @Qualifier("apiPool") ExecutorService executorService) {
    this.tmail = tmail;
    this.restTemplate = restTemplate;
    this.botProperties = botProperties;
    this.executorService = executorService;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    while (true) {
      UpdatesResponse updatesResponse = requestUpdates();

      if (updatesResponse != null && updatesResponse.getSuccess()) {
        updatesResponse.getUpdates().forEach(update -> {
          log.info("Update received: {}", update);
          offset = update.getId().longValue() + 1;
          executorService.submit(() -> tmail.onUpdate(update));
        });
      }
    }
  }

  private UpdatesResponse requestUpdates() {
    String url = String.format(botProperties.getTelegramUrlPattern(), botProperties.getToken(),
        ApiMethod.GET_UPDATES.toString());

    GetUpdatesRequest request = GetUpdatesRequest.builder()
            .offset(offset)
        .timeout(botProperties.getTimeout())
        .build();

    return executeRequest(url, request);
  }

  private UpdatesResponse executeRequest(String url, GetUpdatesRequest request) {
    try {
      return restTemplate.exchange(RequestEntity
          .post(URI.create(url))
          .contentType(MediaType.APPLICATION_JSON)
          .body(request), UpdatesResponse.class).getBody();
    } catch (Exception e) {
      return null;
    }
  }
}

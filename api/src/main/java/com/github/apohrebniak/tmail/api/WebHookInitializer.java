package com.github.apohrebniak.tmail.api;

import com.github.apohrebniak.tmail.api.bot.BotProperties;
import com.github.apohrebniak.tmail.api.telegram.ApiMethod;
import com.github.apohrebniak.tmail.api.telegram.ApiParam;
import com.github.apohrebniak.tmail.api.telegram.ApiValue;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebHookInitializer implements InitializingBean {

  private RestTemplate template;
  private BotProperties properties;

  @Override
  public void afterPropertiesSet() {
    deleteOldWebHook();
    setUpNewWebHook();
  }

  private void deleteOldWebHook() {
    log.info("Delete webhook");
    URI uri = UriComponentsBuilder.fromUri(properties.getTelegramUri())
        .pathSegment(ApiMethod.SET_WEBHOOK.getName())
        .queryParam(ApiParam.URL.getName(), "")
        .build()
        .toUri();
    String result = template.exchange(RequestEntity.post(uri).build(), String.class).getBody();
    log.info(result);
  }

  private void setUpNewWebHook() {
    log.info("Set up webhook");
    URI uri = UriComponentsBuilder.fromUri(properties.getTelegramUri())
        .pathSegment(ApiMethod.SET_WEBHOOK.getName())
        .queryParam(ApiParam.URL.getName(), properties.getUpdateUri())
        .queryParam(ApiParam.ALLOWED_UPDATES.getName(), ApiValue.MESSAGE.getValue())
        .build().toUri();
    String result = template.exchange(RequestEntity.get(uri).build(), String.class).getBody();
    log.info(result);
  }
}

package com.github.apohrebniak.tmail.api.bot;

import com.github.apohrebniak.tmail.api.telegram.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${tmail.bot.token}")
@Slf4j
public class UpdateController {

  @Autowired
  private Tmail tmail;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void update(Update update) {
    log.info(update.toString());
    tmail.onUpdate(update);
  }
}

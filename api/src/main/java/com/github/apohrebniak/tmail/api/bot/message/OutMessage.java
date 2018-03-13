package com.github.apohrebniak.tmail.api.bot.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OutMessage {

  @JsonProperty("chat_id")
  private final Long chatId;
  @JsonProperty("parse_mode")
  private ParseMode parseMode;
  @JsonProperty("text")
  private final String text;

}

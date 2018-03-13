package com.github.apohrebniak.tmail.api.bot.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OutMessage {

  @JsonProperty("chat_id")
  private Long chatId;
  @JsonProperty("parse_mode")
  private ParseMode parseMode;
  @JsonProperty("text")
  private String text;

}

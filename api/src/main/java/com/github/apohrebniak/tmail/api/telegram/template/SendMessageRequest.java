package com.github.apohrebniak.tmail.api.telegram.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.apohrebniak.tmail.api.bot.message.ParseMode;
import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public class SendMessageRequest {

  @JsonProperty("chat_id")
  private Long chatId;
  @JsonProperty("text")
  private String formattedText;
  @JsonProperty("parse_mode")
  private ParseMode parseMode;
}

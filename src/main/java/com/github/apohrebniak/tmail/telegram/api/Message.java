package com.github.apohrebniak.tmail.telegram.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

  @JsonProperty("message_id")
  private Long id;
  @JsonProperty("from")
  private User user;
  @JsonProperty("date")
  private Long timestamp;
  @JsonProperty("text")
  private String text;

  public boolean isCommand() {
    return this.hasText()
        && text.trim().startsWith("/");
  }

  public boolean hasText() {
    return text != null
        && !text.isEmpty();
  }
}

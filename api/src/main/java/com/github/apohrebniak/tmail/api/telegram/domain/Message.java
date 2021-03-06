package com.github.apohrebniak.tmail.api.telegram.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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

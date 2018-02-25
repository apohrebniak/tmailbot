package com.github.apohrebniak.tmail.telegram.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Update {

  @JsonProperty("update_id")
  private Integer id;
  @JsonProperty("message")
  private Message message;

  public boolean hasMessage() {
    return message != null;
  }
}

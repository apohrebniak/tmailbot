package com.github.apohrebniak.tmail.api.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Chat {

  @JsonProperty("id")
  private Long id;
}

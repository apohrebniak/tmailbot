package com.github.apohrebniak.tmail.telegram.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {

  private Long id;
  private String username;
  @JsonProperty("language_code")
  private String languageCode;
}

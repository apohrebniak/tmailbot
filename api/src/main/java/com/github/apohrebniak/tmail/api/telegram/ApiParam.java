package com.github.apohrebniak.tmail.api.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiParam {
  URL("url"),
  MAX_CONNECTIONS("max_connections"),
  ALLOWED_UPDATES("allowed_updates");

  String name;
}

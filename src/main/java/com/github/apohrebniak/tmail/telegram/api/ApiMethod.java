package com.github.apohrebniak.tmail.telegram.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiMethod {
  SET_WEBHOOK("setWebhook");

  String name;
}

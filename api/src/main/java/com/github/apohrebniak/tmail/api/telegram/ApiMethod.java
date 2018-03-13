package com.github.apohrebniak.tmail.api.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiMethod {
  SET_WEBHOOK("setWebhook"),
  SEND_MESSAGE("sendMessage");

  String name;
}

package com.github.apohrebniak.tmail.api.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiMethod {
  GET_UPDATES("getUpdates"),
  SEND_MESSAGE("sendMessage");

  String name;


  @Override
  public String toString() {
    return name;
  }
}

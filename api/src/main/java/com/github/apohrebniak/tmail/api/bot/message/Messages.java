package com.github.apohrebniak.tmail.api.bot.message;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Messages {
  UNKNOWN_COMMAND("unknown_command");

  String value;


  @Override
  public String toString() {
    return this.value;
  }
}

package com.github.apohrebniak.tmail;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotCommand {
  NEW_MAILBOX("new_mailbox"),
  GET_TIME("get_time");

  static Map<String, BotCommand> map;

  static {
    map = Arrays.stream(BotCommand.values())
        .collect(Collectors.toMap(k -> k.getValue(), v -> v));
  }

  String value;

  public BotCommand byValue(String value) {
    return Optional.ofNullable(map.get(value))
        .orElseThrow(CommandNotSupportedException::new);
  }
}

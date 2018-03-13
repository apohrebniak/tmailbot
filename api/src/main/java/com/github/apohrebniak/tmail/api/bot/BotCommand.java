package com.github.apohrebniak.tmail.api.bot;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotCommand {
  ME("/me"),
  NEW_MAILBOX("/new_mailbox"),
  GET_TIME("/get_time"),
  UNKNOWN("unknown");

  static Map<String, BotCommand> map;

  static {
    map = Arrays.stream(BotCommand.values())
        .collect(Collectors.toMap(k -> k.getValue(), v -> v));
  }

  String value;

  public static BotCommand byValue(String value) {
    return map.getOrDefault(value, UNKNOWN);
  }
}

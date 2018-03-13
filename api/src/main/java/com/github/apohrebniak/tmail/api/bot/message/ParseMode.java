package com.github.apohrebniak.tmail.api.bot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ParseMode {
  MARKDOWN("Markdown"),
  HTML("HTML");

  String value;
}

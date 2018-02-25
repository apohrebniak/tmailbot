package com.github.apohrebniak.tmail.telegram.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiValue {

  MESSAGE("message");

  String value;
}

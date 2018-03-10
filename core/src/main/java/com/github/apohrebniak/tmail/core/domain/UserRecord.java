package com.github.apohrebniak.tmail.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRecord {

  private final Long id;
  private final String mailboxId;
}

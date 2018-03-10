package com.github.apohrebniak.tmail.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MailboxRecord {

  private final String id;
  private final Long userId;
  private final Long ttlSeconds;
}

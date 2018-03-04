package com.github.apohrebniak.tmail.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoMailboxForUserException extends RuntimeException {

  private Long userId;
}

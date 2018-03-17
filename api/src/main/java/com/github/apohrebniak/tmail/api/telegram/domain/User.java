package com.github.apohrebniak.tmail.api.telegram.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {

  private Long id;
  private String username;
}

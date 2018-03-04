package com.github.apohrebniak.tmail.core;

import java.util.Optional;

public interface RecipientRegistry {

  boolean exists(String id);

  void add(Recipient recipient);

  Optional getById(String id);
}

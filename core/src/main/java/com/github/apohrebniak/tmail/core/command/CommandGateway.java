package com.github.apohrebniak.tmail.core.command;

public interface CommandGateway<T> {

  T sendCommandWithResult(Command command);
}

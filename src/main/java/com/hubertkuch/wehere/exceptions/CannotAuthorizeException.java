package com.hubertkuch.wehere.exceptions;

public class CannotAuthorizeException extends RuntimeException {
  public CannotAuthorizeException(String message) {
    super(message);
  }
}

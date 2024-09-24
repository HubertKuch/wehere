package com.hubertkuch.wehere.friends.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CannotMakeFriendshipException extends Exception {
    public CannotMakeFriendshipException(String message) {
        super(message);
    }
}

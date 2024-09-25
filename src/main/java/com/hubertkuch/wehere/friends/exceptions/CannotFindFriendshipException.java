package com.hubertkuch.wehere.friends.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CannotFindFriendshipException extends Exception{
    public CannotFindFriendshipException() {
        super("Could not find friendship");
    }
}

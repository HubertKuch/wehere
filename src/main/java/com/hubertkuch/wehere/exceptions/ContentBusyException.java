package com.hubertkuch.wehere.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ContentBusyException extends Exception {
    public ContentBusyException(String message) {
        super(message);
    }
}

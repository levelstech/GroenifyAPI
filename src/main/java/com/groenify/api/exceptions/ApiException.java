package com.groenify.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.NestedServletException;

public abstract class ApiException extends ResponseStatusException {


    public ApiException(HttpStatus status, String reason) {
        super(status, reason);
    }
}

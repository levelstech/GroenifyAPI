package com.groenify.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class ApiException extends ResponseStatusException {


    public ApiException(final HttpStatus status, final String reason) {
        super(status, reason);
    }
}

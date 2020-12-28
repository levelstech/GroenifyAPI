package com.groenify.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.util.NestedServletException;

public abstract class ApiException extends NestedServletException {

    private final HttpStatus status;

    protected ApiException(final String msg, final HttpStatus status) {
        super(msg);
        this.status = status;
    }

    protected ApiException(
            final String msg, final Throwable throwable,
            final HttpStatus status) {
        super(msg, throwable);
        this.status = status;
    }

    public HttpStatus getStatusCode() {
        return status;
    }
}

package com.groenify.api.exceptions;

import org.springframework.http.HttpStatus;

public class PathException extends ApiException {

    public PathException(final String msg, final HttpStatus status) {
        super(msg, status);
    }

    public static PathException notFoundWithId(
            final Class<?> clazz,
            final Long id) {
        final String msg = String.format("'%s' not found with id = '%s'",
                clazz.getSimpleName(), id);
        return new PathException(msg, HttpStatus.NOT_FOUND);
    }
}

package com.groenify.api.exceptions;

import org.springframework.http.HttpStatus;

public class PathException extends ApiException {

    public PathException(final HttpStatus status, final String msg) {
        super(status, msg);
    }

    public static PathException notFoundWithId(
            final Class<?> clazz,
            final Long id) {
        final String msg = String.format("'%s' not found with id = '%s'",
                clazz.getSimpleName(), id);
        return new PathException(HttpStatus.NOT_FOUND, msg);
    }
}

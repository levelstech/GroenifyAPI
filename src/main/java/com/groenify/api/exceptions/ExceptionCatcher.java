package com.groenify.api.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public final class ExceptionCatcher {

    @ExceptionHandler(value = {ApiException.class, PathException.class})
    protected ResponseEntity<ExceptionResMo> handleCustoms(
            final ApiException ex, final WebRequest request) {

        final String url =
                ((ServletWebRequest) request).getRequest().getRequestURI();

        final ExceptionResMo model = ExceptionResMo.ofException(url, ex);
        return new ResponseEntity<>(model, null, ex.getStatusCode());
    }
}

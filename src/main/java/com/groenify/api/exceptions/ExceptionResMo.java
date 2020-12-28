package com.groenify.api.exceptions;

import com.groenify.api.util.DateUtil;

public class ExceptionResMo {
    private final String endpoint;
    private final String time;
    private final String message;
    private final int code;

    public ExceptionResMo(final String getFrom, final ApiException ex) {
        this.endpoint = getFrom;
        this.time = DateUtil.toIsoNoMillis(DateUtil.now());
        this.message = ex.getMessage();
        this.code = ex.getStatusCode().value();

    }
    public static ExceptionResMo ofException(
            final String getFrom,
            final ApiException ex) {
        return new ExceptionResMo(getFrom, ex);
    }


    public String getEndpoint() {
        return endpoint;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}

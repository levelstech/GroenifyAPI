package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class CompanyReqMo {

    @JsonProperty("name")
    @NotNull(message = "'name' is a required field")
    @NotEmpty(message = "'name' cannot be an empty string")
    private String name;

    @JsonProperty("date")
    private String date;

    @JsonProperty("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String var) {
        this.date = var;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String var) {
        this.url = var;
    }
}

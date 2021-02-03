package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.company.CompanyMethods;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class CompanyReqMo implements CompanyMethods {

    @JsonProperty("name")
    @NotNull(message = "'name' is a required field")
    @NotEmpty(message = "'name' cannot be an empty string")
    private String name;

    @JsonProperty("date")
    private String date;

    @JsonProperty("url")
    private String url;

    @Override
    public String getCompanyName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }

    @Override
    public String getCompanyDateString() {
        return date;
    }

    public void setDate(final String var) {
        this.date = var;
    }

    @Override
    public String getCompanyUrl() {
        return url;
    }

    public void setUrl(final String var) {
        this.url = var;
    }
}

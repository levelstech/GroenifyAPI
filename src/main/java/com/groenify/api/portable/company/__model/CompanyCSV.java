package com.groenify.api.portable.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.company.CompanyMethods;

public final class CompanyCSV implements CompanyMethods {

    @JsonProperty("name")
    private String name;

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
        return "";
    }

    @Override
    public String getCompanyUrl() {
        return url;
    }

    public void setUrl(final String var) {
        this.url = var;
    }
}

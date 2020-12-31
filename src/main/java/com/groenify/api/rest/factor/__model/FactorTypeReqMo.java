package com.groenify.api.rest.factor.__model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FactorTypeReqMo {

    @JsonProperty("name")
    @NotNull(message = "'name' is a required field")
    @NotEmpty(message = "'name' cannot be an empty string")
    private String name;

    @JsonProperty("description")
    private String description;

    public void setName(final String var) {
        this.name = var;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }

    public String getName() {
        return name;
    }
}

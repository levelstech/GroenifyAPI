package com.groenify.api.rest.epole.__model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class EPoleBrandReqMo {

    @JsonProperty("name")
    @NotNull(message = "'name' is a required field")
    @NotEmpty(message = "'name' cannot be an empty string")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }
}

package com.groenify.api.rest.epole.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.epole.EPoleBrandMethods;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class EPoleBrandReqMo implements EPoleBrandMethods {

    @JsonProperty("name")
    @NotNull(message = "'name' is a required field")
    @NotEmpty(message = "'name' cannot be an empty string")
    private String name;

    public String getBrandName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }
}

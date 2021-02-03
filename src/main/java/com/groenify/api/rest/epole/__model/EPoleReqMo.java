package com.groenify.api.rest.epole.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.epole.EPoleMethods;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class EPoleReqMo implements EPoleMethods {

    @JsonProperty("type")
    @NotNull(message = "'type' is a required field")
    @NotEmpty(message = "'type' cannot be an empty string")
    private String type;

    @JsonProperty("description")
    private String description;


    public String getEPoleType() {
        return type;
    }

    public void setType(final String var) {
        this.type = var;
    }

    public String getEPoleDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }
}

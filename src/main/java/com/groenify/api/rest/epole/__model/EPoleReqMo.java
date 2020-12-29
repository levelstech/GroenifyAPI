package com.groenify.api.rest.epole.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.epole.EPole;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class EPoleReqMo {

    @JsonProperty("type")
    @NotNull(message = "'type' is a required field")
    @NotEmpty(message = "'type' cannot be an empty string")
    private String type;

    @JsonProperty("description")
    private String description;


    public String getType() {
        return type;
    }

    public void setType(final String var) {
        this.type = var;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }
}

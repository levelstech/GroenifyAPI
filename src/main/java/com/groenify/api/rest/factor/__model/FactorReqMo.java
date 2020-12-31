package com.groenify.api.rest.factor.__model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class FactorReqMo {

    @JsonProperty("name")
    @NotNull(message = "'name' is a required field")
    @NotEmpty(message = "'name' cannot be an empty string")
    private String name;

    @JsonProperty("question")
    @NotNull(message = "'question' is a required field")
    @NotEmpty(message = "'question' cannot be an empty string")
    private String question;

    @JsonProperty("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String var) {
        this.question = var;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }
}

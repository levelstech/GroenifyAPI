package com.groenify.api.rest.factor.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.methods.factor.FactorMethods;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class FactorReqMo implements FactorMethods {

    @JsonProperty("name")
    @NotNull(message = "'name' is a required field")
    @NotEmpty(message = "'name' cannot be an empty string")
    private String name;

    @JsonProperty("question")
    @NotNull(message = "'question' is a required field")
    @NotEmpty(message = "'question' cannot be an empty string")
    private String question;

    @JsonProperty("required")
    @NotNull(message = "'required' is a required field :)")
    private Boolean required;

    @JsonProperty("description")
    private String description;

    public FactorReqMo() {
    }

    protected FactorReqMo(final Factor factor) {
        this.name = factor.getName();
        this.question = factor.getQuestion();
        this.description = factor.getDescription();
    }

    @Override
    public String getFactorName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }

    @Override
    public String getFactorQuestion() {
        return question;
    }

    public void setQuestion(final String var) {
        this.question = var;
    }

    @Override
    public String getFactorDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }


    @Override
    public Boolean getFactorRequired() {
        return required;
    }

    public void setRequired(final Boolean var) {
        this.required = var;
    }
}

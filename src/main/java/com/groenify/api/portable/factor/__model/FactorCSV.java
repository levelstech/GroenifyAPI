package com.groenify.api.portable.factor.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.factor.FactorMethodsWithType;

public final class FactorCSV implements FactorMethodsWithType {

    @JsonProperty("name")
    private String name;

    @JsonProperty("question")
    private String question;

    @JsonProperty("description")
    private String description;

    @JsonProperty("required")
    private Boolean required;

    @JsonProperty("type")
    private Long type;

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

    @Override
    public Long getFactorType() {
        return type;
    }

    public void setType(final Long var) {
        this.type = var;
    }
}

package com.groenify.api.portable.factor.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.factor.FactorMethods;

public final class FactorCSV implements FactorMethods {

    @JsonProperty("name")
    private String name;

    @JsonProperty("question")
    private String question;

    @JsonProperty("description")
    private String description;

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

    public Long getType() {
        return type;
    }

    public void setType(final Long var) {
        this.type = var;
    }
}

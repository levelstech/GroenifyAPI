package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.answer.FactorAnswerBoolean;

public final class FactorAnswerBooleanResMo extends FactorAnswerResMo {

    private final Boolean answer;

    public FactorAnswerBooleanResMo(final FactorAnswerBoolean var) {
        super(var);
        this.answer = var.getAnswer();
    }

    public static FactorAnswerBooleanResMo mapFactorAnswerToResMo(
            final FactorAnswerBoolean factorAnswer) {
        return new FactorAnswerBooleanResMo(factorAnswer);
    }

    @JsonProperty("answer")
    public Boolean getAnswer() {
        return answer;
    }
}

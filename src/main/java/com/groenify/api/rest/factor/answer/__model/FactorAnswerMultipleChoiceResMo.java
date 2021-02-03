package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.answer.FactorAnswerMultipleChoice;

public final class FactorAnswerMultipleChoiceResMo extends FactorAnswerResMo {

    private final String answer;

    public FactorAnswerMultipleChoiceResMo(
            final FactorAnswerMultipleChoice var) {
        super(var);
        this.answer = var.getAnswer();
    }

    public static FactorAnswerMultipleChoiceResMo mapFactorAnswerToResMo(
            final FactorAnswerMultipleChoice factorAnswer) {
        return new FactorAnswerMultipleChoiceResMo(factorAnswer);
    }

    @JsonProperty("answer")
    public String getAnswer() {
        return answer;
    }
}

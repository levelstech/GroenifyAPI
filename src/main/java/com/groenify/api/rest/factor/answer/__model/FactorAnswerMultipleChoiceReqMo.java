package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.database.factor.answer.FactorAnswerMultipleChoice;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public final class FactorAnswerMultipleChoiceReqMo extends FactorAnswerReqMo {

    @NotNull(message = "'answer' is a required field")
    @NotEmpty(message = "'answer' cannot be an empty string")
    @JsonProperty("answer")
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String var) {
        this.answer = var;
    }

    @Override
    public FactorAnswer toToDatabaseModel(final Factor factor) {
        return FactorAnswerMultipleChoice.ofReqMo(factor, this);
    }
}

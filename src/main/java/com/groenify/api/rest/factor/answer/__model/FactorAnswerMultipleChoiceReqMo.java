package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.factor.answer.FactorAnswerMultipleChoice;

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
    public FactorAnswer mapToDatabaseModel(final Factor factor) {
        return FactorAnswerMultipleChoice.ofRequestModel(factor, this);
    }
}

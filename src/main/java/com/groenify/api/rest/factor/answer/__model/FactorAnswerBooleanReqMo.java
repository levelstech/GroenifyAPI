package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.factor.answer.FactorAnswerBoolean;

import javax.validation.constraints.NotNull;

public final class FactorAnswerBooleanReqMo extends FactorAnswerReqMo {

    @NotNull(message = "'answer' is a required field")
    @JsonProperty("answer")
    private Boolean answer;

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(final Boolean var) {
        this.answer = var;
    }

    @Override
    public FactorAnswer mapToDatabaseModel(final Factor factor) {
        return FactorAnswerBoolean.ofRequestModel(factor, this);
    }
}

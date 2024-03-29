package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.factor.answer.FactorAnswerNumber;
import com.groenify.api.framework.classes.Pair;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public final class FactorAnswerNumberReqMo extends FactorAnswerReqMo {

    @NotNull(message = "'answer' is a required field")
    @Size(min = 2, max = 2, message = "size of 'bounds' must be 2")
    @JsonProperty("answer")
    private List<Double> bounds;

    public List<Double> getBounds() {
        return bounds;
    }

    @JsonIgnore
    public Pair<Double> getBoundsAsPair() {
        return Pair.ofList(getBounds());
    }

    public void setBounds(final List<Double> var) {
        this.bounds = var;
    }

    @Override
    public FactorAnswer mapToDatabaseModel(final Factor factor) {
        return FactorAnswerNumber.ofRequestModel(factor, this);
    }

}

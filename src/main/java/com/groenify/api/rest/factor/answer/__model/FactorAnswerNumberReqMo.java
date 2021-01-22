package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.database.factor.answer.FactorAnswerNumber;
import com.groenify.api.framework.annotation.classes.Pair;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public final class FactorAnswerNumberReqMo extends FactorAnswerReqMo {

    @NotNull(message = "'bounds' is a required field")
    @Size(min = 2, max = 2, message = "size of 'bounds' must be 2")
    @JsonProperty("bounds")
    private List<Integer> bounds;

    public List<Integer> getBounds() {
        return bounds;
    }

    @JsonIgnore
    public Pair<Integer> getBoundsAsPair() {
        return Pair.ofList(getBounds());
    }

    public void setBounds(final List<Integer> var) {
        this.bounds = var;
    }

    @Override
    public FactorAnswer mapToDatabaseModel(final Factor factor) {
        return FactorAnswerNumber.ofRequestModel(factor, this);
    }

}

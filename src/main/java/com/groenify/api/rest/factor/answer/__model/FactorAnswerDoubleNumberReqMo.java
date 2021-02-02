package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.database.model.factor.answer.FactorAnswerDoubleNumber;
import com.groenify.api.framework.classes.Pair;
import com.groenify.api.util.ListUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public final class FactorAnswerDoubleNumberReqMo extends FactorAnswerReqMo {


    @NotNull(message = "'answer' is a required field")
    @Size(min = 2, max = 2, message = "size of 'bounds' must be 2")
    @JsonProperty("answer")
    private List<
            @NotNull(message = "'answer' is a required field")
            @Size(min = 2, max = 2, message = "size of 'bounds' must be 2")
                    List<Double>> bounds;

    public List<List<Double>> getBounds() {
        return bounds;
    }

    @JsonIgnore
    public Pair<Double> getBoundsAAsPair() {
        final List<Double> boundsA =
                ListUtil.findFirstOrElse(getBounds(), List.of());
        return Pair.ofList(boundsA);
    }

    @JsonIgnore
    public Pair<Double> getBoundsBAsPair() {
        final List<Double> boundsB =
                ListUtil.findSecondOrElse(getBounds(), List.of());
        return Pair.ofList(boundsB);
    }

    @Override
    public FactorAnswer mapToDatabaseModel(final Factor factor) {
        return FactorAnswerDoubleNumber.ofRequestModel(factor, this);
    }
}

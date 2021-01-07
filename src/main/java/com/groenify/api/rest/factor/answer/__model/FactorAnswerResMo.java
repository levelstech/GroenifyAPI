package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.rest.factor.__model.FactorResMo;

import java.util.List;
import java.util.stream.Collectors;

public abstract class FactorAnswerResMo {

    private final Long id;
    private final FactorResMo factor;
    private final FactorTypeEnum type;

    public FactorAnswerResMo(final FactorAnswer var) {
        this.id = var.getId();
        this.factor = FactorResMo.mapFactorToResMo(var.getFactor());
        this.type = var.getTypeEnum();
    }

    public static List<FactorAnswerResMo> mapFactorAnswerToResMoList(
            final List<FactorAnswer> factors) {
        return factors.stream()
                .map(FactorAnswer::mapToResponseModel)
                .collect(Collectors.toList());
    }

    @JsonProperty("id")
    public final Long getId() {
        return id;
    }

    @JsonProperty("factor")
    public final FactorResMo getFactor() {
        return factor;
    }

    @JsonProperty("type")
    public final Long getType() {
        return type.getNumber();
    }

    @JsonProperty("answer")
    public abstract Object getAnswer();
}

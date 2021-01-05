package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.database.factor.answer.FactorAnswer;
import com.groenify.api.rest.factor.__model.FactorResMo;

import java.util.List;
import java.util.stream.Collectors;

public final class FactorAnswerResMo {

    private final Long id;
    private final FactorResMo factor;
    private final FactorTypeEnum type;

    public FactorAnswerResMo(final FactorAnswer answer) {
        this.id = answer.getId();
        this.factor = FactorResMo.mapFactorToResMo(answer.getFactor());
        this.type = answer.getTypeEnum();
    }

    public static FactorAnswerResMo mapFactorAnswerToResMo(
            final FactorAnswer factorAnswer) {
        return new FactorAnswerResMo(factorAnswer);
    }

    public static List<FactorAnswerResMo> mapFactorAnswerToResMoList(
            final List<FactorAnswer> factors) {
        return factors.stream()
                .map(FactorAnswerResMo::mapFactorAnswerToResMo)
                .collect(Collectors.toList());
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("factor")
    public FactorResMo getFactor() {
        return factor;
    }

    @JsonProperty("type")
    public Long getType() {
        return type.getNumber();
    }
}

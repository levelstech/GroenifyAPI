package com.groenify.api.rest.factor.answer.__model;

import com.groenify.api.database.model.factor.answer.FactorAnswerDoubleNumber;
import com.groenify.api.framework.classes.Pair;

import java.util.List;
import java.util.stream.Collectors;

public final class FactorAnswerDoubleNumberResMo extends FactorAnswerResMo {

    private final List<List<Double>> answer;

    public FactorAnswerDoubleNumberResMo(final FactorAnswerDoubleNumber var) {
        super(var);
        this.answer = var.getAnswer().toImmutableList()
                .stream().map(Pair::toImmutableList)
                .collect(Collectors.toList());
    }

    public static FactorAnswerDoubleNumberResMo mapFactorAnswerToResMo(
            final FactorAnswerDoubleNumber factorAnswer) {
        return new FactorAnswerDoubleNumberResMo(factorAnswer);
    }

    @Override
    public List<List<Double>> getAnswer() {
        return answer;
    }
}

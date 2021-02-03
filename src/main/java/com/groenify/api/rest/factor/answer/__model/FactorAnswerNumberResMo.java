package com.groenify.api.rest.factor.answer.__model;

import com.groenify.api.database.model.factor.answer.FactorAnswerNumber;

import java.util.List;

public final class FactorAnswerNumberResMo extends FactorAnswerResMo {

    private final List<Double> answer;

    private FactorAnswerNumberResMo(final FactorAnswerNumber var) {
        super(var);
        this.answer = var.getAnswer().toImmutableList();
    }

    public static FactorAnswerNumberResMo mapFactorAnswerToResMo(
            final FactorAnswerNumber factorAnswer) {
        return new FactorAnswerNumberResMo(factorAnswer);
    }

    @Override
    public List<Double> getAnswer() {
        return answer;
    }
}

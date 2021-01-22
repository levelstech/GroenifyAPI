package com.groenify.api.rest.factor.answer.__model;

import com.groenify.api.database.factor.answer.FactorAnswerNumber;

import java.util.List;

public final class FactorAnswerNumberResMo extends FactorAnswerResMo {

    private final List<Integer> answer;

    private FactorAnswerNumberResMo(final FactorAnswerNumber var) {
        super(var);
        this.answer = var.getAnswer().toImmutableList();
    }

    public static FactorAnswerNumberResMo mapFactorAnswerToResMo(
            final FactorAnswerNumber factorAnswer) {
        return new FactorAnswerNumberResMo(factorAnswer);
    }

    @Override
    public List<Integer> getAnswer() {
        return answer;
    }
}

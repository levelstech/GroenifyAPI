package com.groenify.api.rest.factor.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;

import java.util.List;
import java.util.stream.Collectors;

public final class FactorResMo {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("type")
    private final FactorTypeResMo type;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("question")
    private final String question;

    @JsonProperty("required")
    private final Boolean required;

    @JsonProperty("description")
    private final String description;

    private FactorResMo(final Factor factor) {
        this.id = factor.getId();
        this.type = FactorTypeResMo.mapFactorTypeToResMo(factor.getType());
        this.name = factor.getName();
        this.question = factor.getQuestion();
        this.required = factor.getRequired();
        this.description = factor.getDescription();
    }

    public static FactorResMo mapFactorToResMo(final Factor factor) {
        return new FactorResMo(factor);
    }

    public static List<FactorResMo> mapFactorToResMoList(
            final List<Factor> factors) {
        return factors.stream()
                .map(FactorResMo::mapFactorToResMo)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public FactorTypeResMo getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getQuestion() {
        return question;
    }

    public Boolean getRequired() {
        return required;
    }

    public String getDescription() {
        return description;
    }
}

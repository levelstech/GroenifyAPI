package com.groenify.api.rest.factor.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.FactorType;

import java.util.List;
import java.util.stream.Collectors;

public final class FactorTypeResMo {

    private final Long id;
    private final String name;
    private final String description;

    private FactorTypeResMo(final FactorType type) {
        this.id = type.getId();
        this.name = type.getName();
        this.description = type.getDescription();
    }

    public static FactorTypeResMo mapFactorTypeToResMo(final FactorType type) {
        return new FactorTypeResMo(type);
    }

    public static List<FactorTypeResMo> mapFactorTypeToResMoList(
            final List<FactorType> list) {
        return list.stream().map(FactorTypeResMo::mapFactorTypeToResMo)
                .collect(Collectors.toList());
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }
}

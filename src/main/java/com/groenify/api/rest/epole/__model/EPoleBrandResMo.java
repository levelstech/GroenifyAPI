package com.groenify.api.rest.epole.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.epole.EPoleBrand;

import java.util.List;
import java.util.stream.Collectors;

public final class EPoleBrandResMo {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("name")
    private final String name;

    private EPoleBrandResMo(final EPoleBrand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }

    public static EPoleBrandResMo mapEPoleBrandToResMo(final EPoleBrand brand) {
        return new EPoleBrandResMo(brand);
    }

    public static List<EPoleBrandResMo> mapEPoleBrandToResMoList(
            final List<EPoleBrand> brand) {
        return brand.stream()
                .map(EPoleBrandResMo::mapEPoleBrandToResMo)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

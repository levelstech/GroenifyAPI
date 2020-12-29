package com.groenify.api.rest.epole.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.epole.EPole;
import com.groenify.api.database.epole.EPoleBrand;

import java.util.List;
import java.util.stream.Collectors;

public class EPoleResMo {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("brand")
    private final EPoleBrandResMo brand;

    @JsonProperty("type")
    private final String type;

    @JsonProperty("description")
    private final String description;

    private EPoleResMo(final EPole ePole) {
        this.id = ePole.getId();
        this.brand = EPoleBrandResMo.mapEPoleBrandToResMo(ePole.getBrand());
        this.type = ePole.getType();
        this.description = ePole.getDescription();
    }

    public static EPoleResMo mapEPoleBrandToResMo(final EPole ePole) {
        return new EPoleResMo(ePole);
    }

    public static List<EPoleResMo> mapEPoleBrandToResMoList(
            final List<EPole> ePoles) {
        return ePoles.stream()
                .map(EPoleResMo::mapEPoleBrandToResMo)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public EPoleBrandResMo getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}

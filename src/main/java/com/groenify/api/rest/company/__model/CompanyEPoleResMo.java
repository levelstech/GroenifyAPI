package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.company.CompanyEPole;
import com.groenify.api.rest.epole.__model.EPoleResMo;

import java.util.List;
import java.util.stream.Collectors;

public final class CompanyEPoleResMo {
    @JsonProperty("id")
    private final Long id;
    @JsonProperty("company")
    private final CompanyResMo company;
    @JsonProperty("epole")
    private final EPoleResMo ePole;
    @JsonProperty("base_price")
    private final Double basePrice;

    private CompanyEPoleResMo(final CompanyEPole pole) {
        this.id = pole.getId();
        this.company = CompanyResMo.mapCompanyToResMo(pole.getCompany());
        this.ePole = EPoleResMo.mapEPoleToResMo(pole.getEPole());
        this.basePrice = pole.getBasePrice();
    }

    public static List<CompanyEPoleResMo> mapCompanyEPoleToResMoList(
            final List<CompanyEPole> list) {
        return list.stream()
                .map(CompanyEPoleResMo::mapCompanyEPoleToResMo)
                .collect(Collectors.toList());
    }

    public static CompanyEPoleResMo mapCompanyEPoleToResMo(
            final CompanyEPole pole) {
        return new CompanyEPoleResMo(pole);
    }

    public Long getId() {
        return id;
    }

    public CompanyResMo getCompany() {
        return company;
    }

    public EPoleResMo getEPole() {
        return ePole;
    }

    public Double getBasePrice() {
        return basePrice;
    }
}

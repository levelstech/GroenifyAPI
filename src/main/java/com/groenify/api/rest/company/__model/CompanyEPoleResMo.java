package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.rest.epole.__model.EPoleResMo;

import java.util.List;
import java.util.stream.Collectors;

public final class CompanyEPoleResMo {
    private final Long id;
    private final CompanyResMo company;
    private final EPoleResMo ePole;
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

    @JsonProperty("company")
    public CompanyResMo getCompany() {
        return company;
    }

    @JsonProperty("epole")
    public EPoleResMo getEPole() {
        return ePole;
    }

    @JsonProperty("base_price")
    public Double getBasePrice() {
        return basePrice;
    }
}

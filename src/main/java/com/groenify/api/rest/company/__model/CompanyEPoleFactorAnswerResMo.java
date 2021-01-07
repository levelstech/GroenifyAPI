package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.company.CompanyEPoleFactorAnswer;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerResMo;

import java.util.List;
import java.util.stream.Collectors;

public final class CompanyEPoleFactorAnswerResMo {

    @JsonProperty("id")
    private final Long id;
    @JsonProperty("company_epole")
    private final CompanyEPoleResMo ePoleResMo;
    @JsonProperty("factor_answer")
    private final FactorAnswerResMo factorAnswerResMo;
    @JsonProperty("price")
    private final Double price;

    private CompanyEPoleFactorAnswerResMo(
            final CompanyEPoleFactorAnswer answer) {
        this.id = answer.getId();
        this.ePoleResMo = CompanyEPoleResMo.mapCompanyEPoleToResMo(
                answer.getEPole());
        this.factorAnswerResMo = answer.getFactorAnswer().mapToResponseModel();
        this.price = answer.getPrice();
    }

    private static CompanyEPoleFactorAnswerResMo mapCompanyEPoleToResMo(
            final CompanyEPoleFactorAnswer resMo) {
        return new CompanyEPoleFactorAnswerResMo(resMo);
    }

    public static List<CompanyEPoleFactorAnswerResMo> mapCompanyEPoleToResMoList(
            final List<CompanyEPoleFactorAnswer> list) {
        return list.stream()
                .map(CompanyEPoleFactorAnswerResMo::mapCompanyEPoleToResMo)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public CompanyEPoleResMo getEPoleResMo() {
        return ePoleResMo;
    }

    public FactorAnswerResMo getFactorAnswerResMo() {
        return factorAnswerResMo;
    }

    public Double getPrice() {
        return price;
    }
}

package com.groenify.api.rest.price.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.price.FactorAnswerPrice;
import com.groenify.api.rest.company.__model.CompanyEPoleResMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerResMo;

import java.util.List;
import java.util.stream.Collectors;

public final class FactorAnswerPriceResMo {

    private final Long id;
    private final CompanyEPoleResMo ePoleResMo;
    private final FactorAnswerResMo factorAnswerResMo;
    private final Double price;

    private FactorAnswerPriceResMo(
            final FactorAnswerPrice answer) {
        this.id = answer.getId();
        this.ePoleResMo = CompanyEPoleResMo.mapCompanyEPoleToResMo(
                answer.getPole());
        this.factorAnswerResMo = answer.getFactorAnswer().mapToResponseModel();
        this.price = answer.getPrice();
    }

    public static FactorAnswerPriceResMo mapCompanyEPoleToResMo(
            final FactorAnswerPrice resMo) {
        return new FactorAnswerPriceResMo(resMo);
    }

    public static List<FactorAnswerPriceResMo> mapCompanyEPoleToResMoList(
            final List<FactorAnswerPrice> list) {
        return list.stream()
                .map(FactorAnswerPriceResMo::mapCompanyEPoleToResMo)
                .collect(Collectors.toList());
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("company_epole")
    public CompanyEPoleResMo getCompanyEPole() {
        return ePoleResMo;
    }

    @JsonProperty("factor_answer")
    public FactorAnswerResMo getFactorAnswer() {
        return factorAnswerResMo;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }
}

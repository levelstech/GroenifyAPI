package com.groenify.api.portable.price.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.company.CompanyEPoleMethods;
import com.groenify.api.database.methods.company.CompanyMethods;
import com.groenify.api.database.methods.epole.EPoleBrandMethods;
import com.groenify.api.database.methods.epole.EPoleMethods;
import com.groenify.api.database.methods.factor.FactorMethods;
import com.groenify.api.database.methods.price.FactorAnswerPriceMethods;

public final class FactorAnswerPriceCSV implements
        CompanyMethods, EPoleBrandMethods, EPoleMethods,
        CompanyEPoleMethods, FactorMethods,
        FactorAnswerMethods, FactorAnswerPriceMethods {

    private String companyName;
    private String companyUrl;
    private String brandName;
    private String ePoleType;
    private Double companyEPolePrice;
    private String factorName;
    private String factorQuestion;
    private String factorDescription;
    private Long factorType;
    private String factorAnswer;
    private Double factorAnswerPrice;

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("company_name")
    public void setCompanyName(final String var) {
        this.companyName = var;
    }

    @Override
    public String getCompanyDate() {
        return "";
    }

    @Override
    public String getCompanyUrl() {
        return companyUrl;
    }

    @JsonProperty("company_url")
    public void setCompanyUrl(final String var) {
        this.companyUrl = var;
    }

    @Override
    public String getBrandName() {
        return brandName;
    }

    @JsonProperty("epole_brand")
    public void setBrandName(final String var) {
        this.brandName = var;
    }

    @Override
    public String getEPoleType() {
        return ePoleType;
    }

    @JsonProperty("epole_type")
    public void setEPoleType(final String var) {
        this.ePoleType = var;
    }


    @Override
    public String getEPoleDescription() {
        return "";
    }

    @Override
    public Double getCompanyEPolePrice() {
        return companyEPolePrice;
    }

    @JsonProperty("epole_price")
    public void setCompanyEPolePrice(final Double var) {
        this.companyEPolePrice = var;
    }

    @Override
    public String getFactorName() {
        return factorName;
    }

    @JsonProperty("factor_name")
    public void setFactorName(final String var) {
        this.factorName = var;
    }

    @Override
    public String getFactorQuestion() {
        return factorQuestion;
    }

    @JsonProperty("factor_question")
    public void setFactorQuestion(final String var) {
        this.factorQuestion = var;
    }

    public Long getFactorType() {
        return factorType;
    }

    @JsonProperty("factor_type")
    public void setFactorType(final Long var) {
        this.factorType = var;
    }

    @Override
    public Long getFactorAnswerType() {
        return getFactorType();
    }

    @Override
    public String getFactorDescription() {
        return factorDescription;
    }

    @JsonProperty("factor_description")
    public void setFactorDescription(final String var) {
        this.factorDescription = var;
    }
    @Override
    public String getFactorAnswer() {
        return factorAnswer;
    }

    @JsonProperty("factor_answer")
    public void setFactorAnswer(final String var) {
        this.factorAnswer = var;
    }


    @Override
    public Double getFactorAnswerPrice() {
        return factorAnswerPrice;
    }

    @JsonProperty("factor_answer_price")
    public void setFactorAnswerPrice(final Double var) {
        this.factorAnswerPrice = var;
    }
}

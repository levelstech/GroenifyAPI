package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.company.CompanyEPoleMethods;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class CompanyEPoleReqMo implements CompanyEPoleMethods {
    @JsonProperty("base_price")
    @NotNull(message = "'base_price' is a required field")
    @Min(value = 0, message = "'base_price' must be greater than zero")
    private Double companyEPolePrice;

    @Override
    public Double getCompanyEPolePrice() {
        return companyEPolePrice;
    }

    public void setCompanyEPolePrice(final Double var) {
        this.companyEPolePrice = var;
    }
}

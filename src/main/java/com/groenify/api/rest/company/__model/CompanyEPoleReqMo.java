package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CompanyEPoleReqMo {
    @JsonProperty("base_price")
    @NotNull(message = "'base_price' is a required field")
    @Min(value = 0, message = "'base_price' must be greater than zero")
    private Double basePrice;

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(final Double var) {
        this.basePrice = var;
    }
}

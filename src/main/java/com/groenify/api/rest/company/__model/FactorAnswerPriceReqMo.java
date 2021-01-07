package com.groenify.api.rest.company.__model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class FactorAnswerPriceReqMo {

    @JsonProperty("price")
    @NotNull(message = "'price' is a required field")
    @Min(value = 0, message = "'price' must be greater than zero")
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double var) {
        this.price = var;
    }
}

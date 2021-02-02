package com.groenify.api.rest.price.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.methods.price.FactorAnswerPriceMethods;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public final class FactorAnswerPriceReqMo implements FactorAnswerPriceMethods {

    @JsonProperty("price")
    @NotNull(message = "'price' is a required field")
    @Min(value = 0, message = "'price' must be greater than zero")
    private Double price;

    @Override
    public Double getFactorAnswerPrice() {
        return price;
    }

    public void setFactorAnswerPrice(final Double var) {
        this.price = var;
    }

}

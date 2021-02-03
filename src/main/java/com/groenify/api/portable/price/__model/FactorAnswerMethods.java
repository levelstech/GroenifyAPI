package com.groenify.api.portable.price.__model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.util.MapperUtil;


public interface FactorAnswerMethods {

    @JsonProperty("type")
    Long getFactorAnswerType();

    @JsonIgnore
    String getFactorAnswer();

    @JsonProperty("answer")
    default Object getAnswer() {
        return MapperUtil.mapStringToBasicObject(getFactorAnswer());
    }
}

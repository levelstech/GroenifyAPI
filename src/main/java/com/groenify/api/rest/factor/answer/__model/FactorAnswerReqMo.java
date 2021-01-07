package com.groenify.api.rest.factor.answer.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.database.factor.answer.FactorAnswer;

import javax.validation.constraints.NotNull;

public abstract class FactorAnswerReqMo {

    @NotNull(message = "'type' is a required field")
    @JsonProperty("type")
    private Long type;

    public static Class<? extends FactorAnswerReqMo> getClassOf(
            final FactorTypeEnum typeEnum) {
        return typeEnum.getFactorAnswerRequestModelClassRelated();
    }

    public final Long getType() {
        return this.type;
    }

    public final void setType(final Long var) {
        this.type = var;
    }

    public abstract FactorAnswer mapToDatabaseModel(Factor factor);
}

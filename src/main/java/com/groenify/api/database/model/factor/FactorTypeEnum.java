package com.groenify.api.database.model.factor;

import com.groenify.api.rest.factor.answer.__model.FactorAnswerBooleanReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerDoubleNumberReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerMultipleChoiceReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerNumberReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerReqMo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum FactorTypeEnum {

    BOOLEAN_QUESTION(FactorAnswerBooleanReqMo.class),
    MULTIPLE_CHOICE(FactorAnswerMultipleChoiceReqMo.class),
    NUMBER(FactorAnswerNumberReqMo.class),
    DOUBLE_NUMBER(FactorAnswerDoubleNumberReqMo.class);

    private static final Logger L =
            LoggerFactory.getLogger(FactorTypeEnum.class);

    private final Class<? extends FactorAnswerReqMo> clazz;
    private Long number;
    private FactorType mappedTo;

    FactorTypeEnum(final Class<? extends FactorAnswerReqMo> var) {
        this.clazz = var;
    }

    public static FactorTypeEnum valueOfFactorType(final FactorType type) {
        for (final FactorTypeEnum e : values())
            if (e.hasMappedTo(type)) return e;

        L.warn("Could not parse FactorType {} to a valid enum", type);
        if (type != null)
            L.debug("FactorType(id,name) {}, {}", type.getId(), type.getName());
        L.debug("Stored Enums {}", (Object) values());
        return null;
    }

    public static FactorTypeEnum valueOfFactorOfId(final Long type) {
        for (final FactorTypeEnum e : values())
            if (e.hasNumber(type)) return e;

        L.warn("Could not parse LongType {} to a valid enum", type);
        return null;
    }

    public void updateType(final FactorType var) {
        setMappedTo(var);
        setNumber(var.getId());
    }

    private Boolean hasMappedTo() {
        return this.getMappedTo() != null;
    }

    private Boolean hasMappedTo(final FactorType type) {
        return hasMappedTo() && this.getMappedTo().equalsId(type);
    }

    public FactorType getMappedTo() {
        return mappedTo;
    }

    private void setMappedTo(final FactorType var) {
        this.mappedTo = var;
    }

    private Boolean hasNumber(final Long type) {
        return this.getNumber().equals(type);
    }

    public Long getNumber() {
        return number;
    }

    private void setNumber(final Long var) {
        this.number = var;
    }

    public Class<? extends FactorAnswerReqMo>
    getFactorAnswerRequestModelClassRelated() {
        return clazz;
    }
}

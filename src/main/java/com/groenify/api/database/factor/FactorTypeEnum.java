package com.groenify.api.database.factor;

public enum FactorTypeEnum {

    BOOLEAN_QUESTION, MULTIPLE_CHOICE, NUMBER, DOUBLE_NUMBER;

    private FactorType mappedTo;

    public static FactorTypeEnum valueOfFactorType(final FactorType type) {
        for (final FactorTypeEnum e : values())
            if (e.hasMappedTo(type)) return e;

        return null;
    }

    private Boolean hasMappedTo(final FactorType type) {
        return this.getMappedTo().equalsId(type);
    }

    public FactorType getMappedTo() {
        return mappedTo;
    }

    public void setMappedTo(final FactorType var) {
        this.mappedTo = var;
    }

}

package com.groenify.api.database.factor;

public enum FactorTypeEnum {

    BOOLEAN_QUESTION, MULTIPLE_CHOICE, NUMBER, DOUBLE_NUMBER;

    private Long number;
    private FactorType mappedTo;

    public static FactorTypeEnum valueOfFactorType(final FactorType type) {
        for (final FactorTypeEnum e : values())
            if (e.hasMappedTo(type)) return e;

        return null;
    }

    public void updateType(final FactorType var) {
        setMappedTo(var);
        setNumber(var.getId());
    }

    private Boolean hasMappedTo(final FactorType type) {
        return this.getMappedTo().equalsId(type);
    }

    public FactorType getMappedTo() {
        return mappedTo;
    }

    private void setMappedTo(final FactorType var) {
        this.mappedTo = var;
    }

    public Long getNumber() {
        return number;
    }

    private void setNumber(final Long var) {
        this.number = var;
    }
}

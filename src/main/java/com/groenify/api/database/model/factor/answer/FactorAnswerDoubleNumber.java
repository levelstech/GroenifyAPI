package com.groenify.api.database.model.factor.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.framework.classes.Pair;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerDoubleNumberReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerDoubleNumberResMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerReqMo;
import com.groenify.api.util.MapperUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "factor_answer_double_number",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"factor_answer_factor_id", "min_number_a",
                        "min_number_b", "max_number_a", "max_number_b"}))
@PrimaryKeyJoinColumn(name = "factor_answer_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class FactorAnswerDoubleNumber extends FactorAnswer {

    @ManyToOne
    @JoinColumn(name = "factor_answer_factor_id", nullable = false)
    private Factor ownFactor;

    @JsonProperty("min_number_a")
    @Column(name = "min_number_a", nullable = false)
    private Double minNumberA;

    @JsonProperty("max_number_a")
    @Column(name = "max_number_a", nullable = false)
    private Double maxNumberA;

    @JsonProperty("min_number_b")
    @Column(name = "min_number_b", nullable = false)
    private Double minNumberB;

    @JsonProperty("max_number_b")
    @Column(name = "max_number_b", nullable = false)
    private Double maxNumberB;

    public FactorAnswerDoubleNumber() {
        super(FactorTypeEnum.DOUBLE_NUMBER);
    }

    public static FactorAnswer ofRequestModel(
            final Factor factor,
            final FactorAnswerDoubleNumberReqMo reqMo) {
        final FactorAnswerDoubleNumber answer = new FactorAnswerDoubleNumber();
        answer.setOwnFactor(factor);
        answer.setType(factor.getType());
        return answer.update(reqMo);
    }

    public static FactorAnswerDoubleNumber ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObjectFromJsonString(
                jsonStr, FactorAnswerDoubleNumber.class);
    }

    @Override
    public void setOwnFactor(final Factor var) {
        super.setFactor(var);
        super.setType(var.getType());
        this.ownFactor = var;
    }

    @Override
    public Factor getOwnFactor() {
        return ownFactor;
    }

    public Double getMinNumberA() {
        return minNumberA;
    }

    public void setMinNumberA(final Double var) {
        this.minNumberA = var;
    }

    public Double getMaxNumberA() {
        return maxNumberA;
    }

    public void setMaxNumberA(final Double var) {
        this.maxNumberA = var;
    }

    public Double getMinNumberB() {
        return minNumberB;
    }

    public void setMinNumberB(final Double var) {
        this.minNumberB = var;
    }

    public Double getMaxNumberB() {
        return maxNumberB;
    }

    public void setMaxNumberB(final Double var) {
        this.maxNumberB = var;
    }

    private Pair<Double> getBoundsA() {
        return Pair.of(getMinNumberA(), getMaxNumberA());
    }

    private void setBoundsA(final Pair<Double> var) {
        setMinNumberA(var.getLeft());
        setMaxNumberA(var.getRight());
    }

    private void setBoundsB(final Pair<Double> var) {
        setMinNumberB(var.getLeft());
        setMaxNumberB(var.getRight());
    }


    private Pair<Double> getBoundsB() {
        return Pair.of(getMinNumberB(), getMaxNumberB());
    }

    @Override
    public Pair<Pair<Double>> getAnswer() {
        return Pair.of(getBoundsA(), getBoundsB());
    }

    @Override
    public FactorAnswerDoubleNumber update(final FactorAnswerReqMo reqMo) {
        if (reqMo instanceof FactorAnswerDoubleNumberReqMo)
            return update((FactorAnswerDoubleNumberReqMo) reqMo);

        return this;
    }

    private FactorAnswerDoubleNumber update(
            final FactorAnswerDoubleNumberReqMo reqMo) {
        if (reqMo == null) return this;
        final Pair<Double> boundsA = reqMo.getBoundsAAsPair();
        setBoundsA(boundsA);
        final Pair<Double> boundsB = reqMo.getBoundsBAsPair();
        setBoundsB(boundsB);
        return this;
    }

    @Override
    public FactorAnswerDoubleNumberResMo mapToResponseModel() {
        return FactorAnswerDoubleNumberResMo.mapFactorAnswerToResMo(this);
    }
}

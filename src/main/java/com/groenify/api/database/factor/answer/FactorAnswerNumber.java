package com.groenify.api.database.factor.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.framework.annotation.classes.Pair;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerNumberReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerNumberResMo;
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


@Entity
@Table(name = "factor_answer_number")
@PrimaryKeyJoinColumn(name = "factor_answer_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class FactorAnswerNumber extends FactorAnswer {

    @ManyToOne
    @JoinColumn(name = "factor_answer_factor_id", nullable = false)
    private Factor ownFactor;

    @JsonProperty("min_number")
    @Column(name = "min_number", nullable = false)
    private Double minNumber;

    @JsonProperty("max_number")
    @Column(name = "max_number", nullable = false)
    private Double maxNumber;

    public FactorAnswerNumber() {
        super(FactorTypeEnum.NUMBER);
    }

    public static FactorAnswer ofRequestModel(
            final Factor factor,
            final FactorAnswerNumberReqMo reqMo) {
        final FactorAnswerNumber answer = new FactorAnswerNumber();
        answer.setOwnFactor(factor);
        answer.setType(factor.getType());
        return answer.update(reqMo);
    }

    public static FactorAnswerNumber ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObject(jsonStr, FactorAnswerNumber.class);
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

    public Double getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(final Double var) {
        this.minNumber = var;
    }

    public Double getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(final Double var) {
        this.maxNumber = var;
    }

    @Override
    public Pair<Double> getAnswer() {
        return Pair.of(getMinNumber(), getMaxNumber());
    }

    @Override
    public FactorAnswerNumber update(final FactorAnswerReqMo reqMo) {
        if (reqMo instanceof FactorAnswerNumberReqMo)
            return update((FactorAnswerNumberReqMo) reqMo);

        return this;
    }

    private FactorAnswerNumber update(final FactorAnswerNumberReqMo reqMo) {
        if (reqMo == null) return this;
        final Pair<Double> bounds = reqMo.getBoundsAsPair();
        this.setMinNumber(bounds.getLeft());
        this.setMaxNumber(bounds.getRight());
        return this;
    }

    @Override
    public FactorAnswerNumberResMo mapToResponseModel() {
        return FactorAnswerNumberResMo.mapFactorAnswerToResMo(this);
    }
}

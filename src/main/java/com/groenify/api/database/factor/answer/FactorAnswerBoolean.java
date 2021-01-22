package com.groenify.api.database.factor.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerBooleanReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerBooleanResMo;
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
@Table(name = "factor_answer_boolean")
@PrimaryKeyJoinColumn(name = "factor_answer_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class FactorAnswerBoolean extends FactorAnswer {

    @ManyToOne
    @JoinColumn(name = "factor_id", nullable = false)
    private Factor factor;

    @JsonProperty("answer_boolean")
    @Column(name = "answer_boolean", nullable = false)
    private Boolean answerBoolean;

    public FactorAnswerBoolean() {
        super(FactorTypeEnum.BOOLEAN_QUESTION);
    }

    public static FactorAnswer ofRequestModel(
            final Factor factor,
            final FactorAnswerBooleanReqMo reqMo) {
        final FactorAnswerBoolean answer = new FactorAnswerBoolean();
        answer.setFactor(factor);
        return answer.update(reqMo);
    }

    public static FactorAnswerBoolean ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObject(jsonStr, FactorAnswerBoolean.class);
    }

    public Boolean getAnswerBoolean() {
        return answerBoolean;
    }

    public void setAnswerBoolean(final Boolean var) {
        this.answerBoolean = var;
    }

    @Override
    public Boolean getAnswer() {
        return getAnswerBoolean();
    }

    @Override
    public FactorAnswerBoolean update(final FactorAnswerReqMo reqMo) {
        if (reqMo instanceof FactorAnswerBooleanReqMo)
            return update((FactorAnswerBooleanReqMo) reqMo);

        return this;
    }

    private FactorAnswerBoolean update(final FactorAnswerBooleanReqMo reqMo) {
        if (reqMo == null) return this;
        this.setAnswerBoolean(reqMo.getAnswer());
        return this;
    }

    @Override
    public FactorAnswerBooleanResMo mapToResponseModel() {
        return FactorAnswerBooleanResMo.mapFactorAnswerToResMo(this);
    }
}

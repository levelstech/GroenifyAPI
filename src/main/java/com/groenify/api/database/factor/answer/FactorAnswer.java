package com.groenify.api.database.factor.answer;

import com.groenify.api.database.IdModel;
import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerResMo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "factor_answer")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FactorAnswer implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "factor_id", nullable = false)
    private Factor factor;
    @ManyToOne
    @JoinColumn(name = "factor_type", nullable = false)
    private FactorType type;

    public FactorAnswer() {
    }

    protected FactorAnswer(final FactorTypeEnum typeEnum) {
        this.type = typeEnum.getMappedTo();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long var) {
        this.id = var;
    }

    public Factor getFactor() {
        return factor;
    }

    public void setFactor(final Factor var) {
        this.factor = var;
    }

    public abstract void setOwnFactor(final Factor var);

    public abstract Factor getOwnFactor();

    public FactorType getType() {
        return type;
    }

    public FactorTypeEnum getTypeEnum() {
        return getType().getAsEnum();
    }

    public void setType(final FactorType var) {
        this.type = var;
    }

    public abstract Object getAnswer();

    public abstract FactorAnswer update(FactorAnswerReqMo reqMo);

    public abstract FactorAnswerResMo mapToResponseModel();
}

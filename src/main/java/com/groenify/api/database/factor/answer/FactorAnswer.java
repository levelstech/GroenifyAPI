package com.groenify.api.database.factor.answer;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorType;

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
public abstract class FactorAnswer {
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

    public Long getId() {
        return id;
    }

    public void setId(final Long var) {
        this.id = var;
    }

    public Factor getFactor() {
        return factor;
    }

    public void setFactor(final Factor var) {
        this.factor = var;
    }

    public FactorType getType() {
        return type;
    }

    public void setType(final FactorType var) {
        this.type = var;
    }
}
package com.groenify.api.database.company;

import com.groenify.api.database.IdModel;
import com.groenify.api.database.factor.answer.FactorAnswer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`company_to_epole_to_factor_answer`")
public class CompanyEPoleFactorAnswer implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_to_epole_id")
    private CompanyEPole ePole;

    @ManyToOne
    @JoinColumn(name = "factor_answer_id")
    private FactorAnswer factorAnswer;

    @Column(name = "price")
    private Double price;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long var) {
        this.id = var;
    }

    public CompanyEPole getEPole() {
        return ePole;
    }

    public void setEPole(final CompanyEPole var) {
        this.ePole = var;
    }

    public FactorAnswer getFactorAnswer() {
        return factorAnswer;
    }

    public void setFactorAnswer(final FactorAnswer var) {
        this.factorAnswer = var;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double var) {
        this.price = var;
    }
}

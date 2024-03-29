package com.groenify.api.database.model.price;

import com.groenify.api.database.methods.price.FactorAnswerPriceMethods;
import com.groenify.api.database.model.IdModel;
import com.groenify.api.database.model.company.CompanyEPole;
import com.groenify.api.database.model.factor.answer.FactorAnswer;
import com.groenify.api.util.MapperUtil;

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
public class FactorAnswerPrice implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_to_epole_id")
    private CompanyEPole pole;

    @ManyToOne
    @JoinColumn(name = "factor_answer_id")
    private FactorAnswer factorAnswer;

    @Column(name = "price")
    private Double price;

    public static FactorAnswerPrice ofReqMo(
            final CompanyEPole companyEPole,
            final FactorAnswer factorAnswer,
            final FactorAnswerPriceMethods body) {
        return new FactorAnswerPrice().update(companyEPole, factorAnswer, body);
    }

    public static FactorAnswerPrice ofJsonObjSr(final String jsonStr) {
        return MapperUtil.readObjectFromJsonString(
                jsonStr, FactorAnswerPrice.class);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long var) {
        this.id = var;
    }

    public CompanyEPole getPole() {
        return pole;
    }

    public void setPole(final CompanyEPole var) {
        this.pole = var;
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

    private FactorAnswerPrice update(
            final CompanyEPole companyEPole,
            final FactorAnswer answer,
            final FactorAnswerPriceMethods body) {
        this.setPole(companyEPole);
        this.setFactorAnswer(answer);
        return this.update(body);
    }

    public FactorAnswerPrice update(final FactorAnswerPriceMethods body) {
        this.setPrice(body.getFactorAnswerPrice());
        return this;
    }
}

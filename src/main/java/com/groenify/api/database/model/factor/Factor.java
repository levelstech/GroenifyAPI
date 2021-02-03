package com.groenify.api.database.model.factor;

import com.groenify.api.database.methods.factor.FactorMethods;
import com.groenify.api.database.model.IdModel;
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
@Table(name = "`factor`")
public class Factor implements IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factor_type", nullable = false)
    private FactorType type;

    @Column(name = "name", nullable = false, columnDefinition = "mediumtext",
            unique = true)
    private String name;

    @Column(name = "question",
            nullable = false, columnDefinition = "mediumtext")
    private String question;

    @Column(name = "description", columnDefinition = "longtext")
    private String description;

    @ManyToOne
    @JoinColumn(name = "factor_answer_id")
    private FactorAnswer factorAnswer;


    public static Factor ofMethods(
            final FactorType type,
            final FactorMethods body) {
        return new Factor().update(type, body);
    }

    public static Factor ofJsonObjStr(final String jsonStr) {
        return MapperUtil.readObjectFromJsonString(jsonStr, Factor.class);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long var) {
        this.id = var;
    }

    public FactorType getType() {
        return type;
    }

    public Long getTypeValue() {
        return getType().getId();
    }

    public void setType(final FactorType var) {
        this.type = var;
    }

    public String getName() {
        return name;
    }

    public void setName(final String var) {
        this.name = var;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String var) {
        this.question = var;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String var) {
        this.description = var;
    }

    public FactorAnswer getFactorAnswer() {
        return factorAnswer;
    }

    public void setFactorAnswer(final FactorAnswer var) {
        this.factorAnswer = var;
    }

    private Factor update(final FactorType newType, final FactorMethods body) {
        this.setType(newType);
        return update(body);
    }

    public Factor update(final FactorMethods body) {
        this.setName(body.getFactorName());
        this.setQuestion(body.getFactorQuestion());
        this.setDescription(body.getFactorDescription());
        return this;
    }
}

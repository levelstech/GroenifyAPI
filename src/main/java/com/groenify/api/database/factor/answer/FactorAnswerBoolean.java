package com.groenify.api.database.factor.answer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "factor_answer_boolean")
@PrimaryKeyJoinColumn(name = "factor_answer_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class FactorAnswerBoolean extends FactorAnswer {

    @Column(name = "answer_boolean", nullable = false)
    private Boolean answerBoolean;

    public Boolean getAnswerBoolean() {
        return answerBoolean;
    }

    public void setAnswerBoolean(final Boolean var) {
        this.answerBoolean = var;
    }
}
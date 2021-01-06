package com.groenify.api.database.factor.answer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "factor_answer_multiple_choice")
@PrimaryKeyJoinColumn(name = "factor_answer_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class FactorAnswerMultipleChoice extends FactorAnswer {

    @Column(name = "answer_multiple", nullable = false,
            columnDefinition = "mediumtext")
    private String answerMultipleChoice;

    public String getAnswerMultipleChoice() {
        return answerMultipleChoice;
    }

    public void setAnswerMultipleChoice(final String var) {
        this.answerMultipleChoice = var;
    }

    @Override
    public String getAnswer() {
        return getAnswerMultipleChoice();
    }
}

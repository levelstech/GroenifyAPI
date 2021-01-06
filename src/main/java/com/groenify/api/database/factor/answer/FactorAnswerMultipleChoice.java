package com.groenify.api.database.factor.answer;

import com.groenify.api.database.factor.Factor;
import com.groenify.api.database.factor.FactorTypeEnum;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerMultipleChoiceReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerReqMo;

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

    public FactorAnswerMultipleChoice() {
        super(FactorTypeEnum.MULTIPLE_CHOICE);
    }

    public static FactorAnswer ofReqMo(
            final Factor factor,
            final FactorAnswerMultipleChoiceReqMo reqMo) {
        final FactorAnswerMultipleChoice answer =
                new FactorAnswerMultipleChoice();
        answer.setFactor(factor);
        return answer.update(reqMo);
    }

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

    @Override
    public FactorAnswerMultipleChoice update(final FactorAnswerReqMo reqMo) {
        if (reqMo instanceof FactorAnswerMultipleChoiceReqMo)
            return update((FactorAnswerMultipleChoiceReqMo) reqMo);

        return this;
    }

    public FactorAnswerMultipleChoice update(
            final FactorAnswerMultipleChoiceReqMo reqMo) {
        if (reqMo == null) return this;
        this.setAnswerMultipleChoice(reqMo.getAnswer());
        return this;
    }
}

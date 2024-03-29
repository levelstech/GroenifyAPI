package com.groenify.api.database.model.factor.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerMultipleChoiceReqMo;
import com.groenify.api.rest.factor.answer.__model.FactorAnswerMultipleChoiceResMo;
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
import javax.persistence.UniqueConstraint;
import java.util.Locale;

@Entity
@Table(name = "factor_answer_multiple_choice",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"factor_answer_factor_id", "lower_answer_hash"}))
@PrimaryKeyJoinColumn(name = "factor_answer_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class FactorAnswerMultipleChoice extends FactorAnswer {

    @ManyToOne
    @JoinColumn(name = "factor_answer_factor_id", nullable = false)
    private Factor ownFactor;

    @JsonProperty("answer_multiple")
    @Column(name = "answer_multiple", nullable = false,
            columnDefinition = "mediumtext")
    private String answerMultipleChoice;

    @Column(name = "lower_answer_hash", nullable = false)
    private String hash;

    public FactorAnswerMultipleChoice() {
        super(FactorTypeEnum.MULTIPLE_CHOICE);
    }

    public static FactorAnswer ofRequestModel(
            final Factor factor,
            final FactorAnswerMultipleChoiceReqMo reqMo) {
        final FactorAnswerMultipleChoice answer =
                new FactorAnswerMultipleChoice();
        answer.setOwnFactor(factor);
        return answer.update(reqMo);
    }

    public static FactorAnswerMultipleChoice ofJsonObjStr(
            final String jsonStr) {
        final FactorAnswerMultipleChoice multipleChoice =
                MapperUtil.readObjectFromJsonString(jsonStr,
                        FactorAnswerMultipleChoice.class);
        if (multipleChoice != null)
            multipleChoice.setAnswerMultipleChoiceWithHash(
                    multipleChoice.getAnswer());
        return multipleChoice;
    }

    public String getAnswerMultipleChoice() {
        return answerMultipleChoice;
    }

    public void setAnswerMultipleChoice(final String var) {
        this.answerMultipleChoice = var;
    }

    private void setAnswerMultipleChoiceWithHash(final String answer) {
        setAnswerMultipleChoice(answer);
        setHash(answer.toLowerCase(Locale.ROOT));
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

    public String getHash() {
        return hash;
    }

    public void setHash(final String var) {
        this.hash = var;
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
        this.setAnswerMultipleChoiceWithHash(reqMo.getAnswer());
        return this;
    }


    @Override
    public FactorAnswerMultipleChoiceResMo mapToResponseModel() {
        return FactorAnswerMultipleChoiceResMo.mapFactorAnswerToResMo(this);
    }

}

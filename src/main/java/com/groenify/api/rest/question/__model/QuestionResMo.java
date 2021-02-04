package com.groenify.api.rest.question.__model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groenify.api.database.model.factor.Factor;
import com.groenify.api.database.model.factor.FactorTypeEnum;
import com.groenify.api.database.model.factor.answer.FactorAnswer;

import java.util.List;
import java.util.stream.Collectors;

public final class QuestionResMo {

    private final Long id;
    private final String questionText;
    private final Boolean required;
    private final Long type;
    private final List<Object> answers;
    private final String description;

    private QuestionResMo(final Factor factor) {
        this.id = factor.getId();
        this.questionText = factor.getQuestion();
        this.required = factor.getRequired();
        this.type = factor.getTypeValue();
        this.answers = parseAnswerObjects(factor.getAnswerList());
        this.description = factor.getDescription();
    }

    private static Boolean matchingAnswerShower(final FactorAnswer answer) {
        return answer.hasTypeEnum(FactorTypeEnum.BOOLEAN_QUESTION)
                || answer.hasTypeEnum(FactorTypeEnum.MULTIPLE_CHOICE);
    }

    private static List<Object> parseAnswerObjects(
            final List<FactorAnswer> answers) {
        return answers.stream()
                .filter(QuestionResMo::matchingAnswerShower)
                .map(FactorAnswer::getAnswer)
                .collect(Collectors.toList());
    }

    public static List<QuestionResMo> ofList(final List<Factor> factors) {
        return factors.stream()
                .map(QuestionResMo::new)
                .collect(Collectors.toList());
    }


    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("question")
    public String getQuestionText() {
        return questionText;
    }

    @JsonProperty("required")
    public Boolean getRequired() {
        return required;
    }

    @JsonProperty("type")
    public Long getType() {
        return type;
    }

    @JsonProperty("answers")
    public List<Object> getAnswers() {
        return answers;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }
}

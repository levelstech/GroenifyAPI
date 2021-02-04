package com.groenify.api.database.model;

import com.groenify.api.database.model.factor.answer.FactorAnswer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public final class ModelMethods {

    private ModelMethods() {
    }

    public static Date getSaveDate(final Date date) {
        if (date == null) return null;

        return new Date(date.getTime());
    }

    public static <T> List<T> saveGetList(
            List<T> answerList,
            final Consumer<List<T>> setAnswerList) {
        if (answerList == null) {
            answerList = new ArrayList<>();
            setAnswerList.accept(answerList);
        }
        return answerList;
    }
}

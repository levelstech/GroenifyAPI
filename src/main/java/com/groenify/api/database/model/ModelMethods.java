package com.groenify.api.database.model;

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
            final List<T> var, final Consumer<List<T>> setter) {
        List<T> temp = var;
        if (temp == null) {
            temp = new ArrayList<>();
            setter.accept(temp);
        }
        return temp;
    }
}

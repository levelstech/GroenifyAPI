package com.groenify.api.database;

import java.util.Date;

public final class ModelMethods {

    private ModelMethods() {
    }

    public static Date getSaveDate(final Date date) {
        if (date == null) return null;

        return new Date(date.getTime());
    }
}

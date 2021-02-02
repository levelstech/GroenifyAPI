package com.groenify.api.database.repository;

import com.groenify.api.database.model.IdModel;

import java.util.Optional;
import java.util.function.Function;

public final class RepositoryMethods {
    private RepositoryMethods() {
    }

    public static <T extends IdModel> T findWithNotFound(
            final Long pathValue,
            final Function<Long, Optional<T>> findById) {
        final Optional<T> optionalT = findById.apply(pathValue);
        if (optionalT.isEmpty()) return null;
        return optionalT.get();
    }
}

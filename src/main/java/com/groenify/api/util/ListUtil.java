package com.groenify.api.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class ListUtil {

    private ListUtil() {
    }

    public static <T> List<T> iterableToList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}

package com.groenify.api.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ListUtil {

    private ListUtil() {
    }

    public static <T> List<T> iterableToList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public static <T> T findFirstOrElse(
            final List<T> list,
            final T returnIfListHasNoIndex) {
        return findIndexOrElse(list, 0, returnIfListHasNoIndex);
    }

    public static <T> T findSecondOrElse(
            final List<T> list,
            final T returnIfListHasNoIndex) {
        return findIndexOrElse(list, 1, returnIfListHasNoIndex);
    }

    public static <T> T findLastOrElse(
            final List<T> list,
            final T returnIfListHasNoIndex) {
        return findIndexOrElse(list, list.size(), returnIfListHasNoIndex);
    }

    public static <T> T findIndexOrElse(
            final List<T> list,
            final Integer index,
            final T returnIfListHasNoIndex) {
        if (list.size() > index) return list.get(index);

        else return returnIfListHasNoIndex;
    }

    public static List<?> parseFromString(final String string) {
        final String regex = string.contains("[") ? "(?<=]),(?=\\[)" : ",";
        final String[] items = string.split(regex);

        return Stream.of(items)
                .map(MapperUtil::mapToBasicObject)
                .collect(Collectors.toList());
    }
}

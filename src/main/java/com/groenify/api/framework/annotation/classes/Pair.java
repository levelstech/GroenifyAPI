package com.groenify.api.framework.annotation.classes;

import java.util.List;

public final class Pair<T> {
    private final T left;
    private final T right;


    private Pair(final T l, final T r) {
        this.left = l;
        this.right = r;
    }

    private Pair() {
        this(null, null);
    }

    public static <T> Pair<T> of(final T l, final T r) {
        return new Pair<>(l, r);
    }

    public static <T> Pair<T> ofList(final List<T> l) {
        if (l.size() == 0) return new Pair<>();
        if (l.size() == 1) return Pair.of(l.get(0), l.get(0));
        else return Pair.of(l.get(0), l.get(1));
    }

    public T getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }

    public List<T> toImmutableList() {
        return List.of(getLeft(), getRight());
    }
}

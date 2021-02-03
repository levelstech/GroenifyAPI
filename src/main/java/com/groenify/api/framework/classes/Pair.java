package com.groenify.api.framework.classes;

import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return String.format("[%s,%s]", getLeft(), getRight());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pair<?> pair = (Pair<?>) o;
        return Objects.equals(left, pair.left)
                && Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}

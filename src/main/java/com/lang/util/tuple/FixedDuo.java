package com.lang.util.tuple;

import java.util.Map;
import java.util.Objects;

public final class FixedDuo<L, R> extends Duo<L, R> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    private static final FixedDuo NULL = new FixedDuo<>(null, null);

    public final L left;
    public final R right;

    public FixedDuo(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> FixedDuo<L, R> of(final L left, final R right) {
        if (left == null && right == null) {
            return nullDuo();
        }
        return new FixedDuo<>(left, right);
    }

    public static <L, R> FixedDuo<L, R> of(final Map.Entry<L, R> entry) {
        if (entry == null) {
            return nullDuo();
        }
        return new FixedDuo<>(entry.getKey(), entry.getValue());
    }

    public static <L, R> FixedDuo<L, R> ofNonNull(final L left, final R right) {
        return of(Objects.requireNonNull(left, "left"), Objects.requireNonNull(right, "right"));
    }

    @SuppressWarnings("unchecked")
    public static <L, R> FixedDuo<L, R> nullDuo() {
        return NULL;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public R getRight() {
        return right;
    }

    @Override
    public R setValue(final R value) {
        throw new UnsupportedOperationException();
    }
}

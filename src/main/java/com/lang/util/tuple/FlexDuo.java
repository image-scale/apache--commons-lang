package com.lang.util.tuple;

import java.util.Map;
import java.util.Objects;

public class FlexDuo<L, R> extends Duo<L, R> {

    private static final long serialVersionUID = 1L;

    private L left;
    private R right;

    public FlexDuo() {
    }

    public FlexDuo(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> FlexDuo<L, R> of(final L left, final R right) {
        return new FlexDuo<>(left, right);
    }

    public static <L, R> FlexDuo<L, R> of(final Map.Entry<L, R> entry) {
        if (entry == null) {
            return new FlexDuo<>(null, null);
        }
        return new FlexDuo<>(entry.getKey(), entry.getValue());
    }

    public static <L, R> FlexDuo<L, R> ofNonNull(final L left, final R right) {
        return of(Objects.requireNonNull(left, "left"), Objects.requireNonNull(right, "right"));
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public R getRight() {
        return right;
    }

    public void setLeft(final L left) {
        this.left = left;
    }

    public void setRight(final R right) {
        this.right = right;
    }

    @Override
    public R setValue(final R value) {
        final R old = right;
        this.right = value;
        return old;
    }
}

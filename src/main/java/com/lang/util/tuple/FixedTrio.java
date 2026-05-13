package com.lang.util.tuple;

import java.util.Objects;

public final class FixedTrio<L, M, R> extends Trio<L, M, R> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    private static final FixedTrio NULL = new FixedTrio<>(null, null, null);

    public final L left;
    public final M middle;
    public final R right;

    public FixedTrio(final L left, final M middle, final R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public static <L, M, R> FixedTrio<L, M, R> of(final L left, final M middle, final R right) {
        if (left == null && middle == null && right == null) {
            return nullTrio();
        }
        return new FixedTrio<>(left, middle, right);
    }

    public static <L, M, R> FixedTrio<L, M, R> ofNonNull(final L left, final M middle, final R right) {
        return of(Objects.requireNonNull(left, "left"),
                Objects.requireNonNull(middle, "middle"),
                Objects.requireNonNull(right, "right"));
    }

    @SuppressWarnings("unchecked")
    public static <L, M, R> FixedTrio<L, M, R> nullTrio() {
        return NULL;
    }

    @Override
    public L getLeft() {
        return left;
    }

    @Override
    public M getMiddle() {
        return middle;
    }

    @Override
    public R getRight() {
        return right;
    }
}

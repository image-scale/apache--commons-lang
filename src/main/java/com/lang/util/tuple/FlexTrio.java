package com.lang.util.tuple;

import java.util.Objects;

public class FlexTrio<L, M, R> extends Trio<L, M, R> {

    private static final long serialVersionUID = 1L;

    private L left;
    private M middle;
    private R right;

    public FlexTrio() {
    }

    public FlexTrio(final L left, final M middle, final R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public static <L, M, R> FlexTrio<L, M, R> of(final L left, final M middle, final R right) {
        return new FlexTrio<>(left, middle, right);
    }

    public static <L, M, R> FlexTrio<L, M, R> ofNonNull(final L left, final M middle, final R right) {
        return of(Objects.requireNonNull(left, "left"),
                Objects.requireNonNull(middle, "middle"),
                Objects.requireNonNull(right, "right"));
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

    public void setLeft(final L left) {
        this.left = left;
    }

    public void setMiddle(final M middle) {
        this.middle = middle;
    }

    public void setRight(final R right) {
        this.right = right;
    }
}

package com.lang.util.tuple;

import java.io.Serializable;
import java.util.Objects;

public abstract class Trio<L, M, R> implements Comparable<Trio<L, M, R>>, Serializable {

    private static final long serialVersionUID = 1L;

    public static <L, M, R> Trio<L, M, R> of(final L left, final M middle, final R right) {
        return FixedTrio.of(left, middle, right);
    }

    public static <L, M, R> Trio<L, M, R> ofNonNull(final L left, final M middle, final R right) {
        return FixedTrio.ofNonNull(left, middle, right);
    }

    public abstract L getLeft();

    public abstract M getMiddle();

    public abstract R getRight();

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(final Trio<L, M, R> other) {
        int cmp = ((Comparable<Object>) getLeft()).compareTo(other.getLeft());
        if (cmp != 0) {
            return cmp;
        }
        cmp = ((Comparable<Object>) getMiddle()).compareTo(other.getMiddle());
        if (cmp != 0) {
            return cmp;
        }
        return ((Comparable<Object>) getRight()).compareTo(other.getRight());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Trio<?, ?, ?>) {
            final Trio<?, ?, ?> other = (Trio<?, ?, ?>) obj;
            return Objects.equals(getLeft(), other.getLeft())
                    && Objects.equals(getMiddle(), other.getMiddle())
                    && Objects.equals(getRight(), other.getRight());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLeft())
                ^ Objects.hashCode(getMiddle())
                ^ Objects.hashCode(getRight());
    }

    @Override
    public String toString() {
        return "(" + getLeft() + ',' + getMiddle() + ',' + getRight() + ')';
    }

    public String toString(final String format) {
        return String.format(format, getLeft(), getMiddle(), getRight());
    }
}

package com.lang.util.tuple;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public abstract class Duo<L, R> implements Map.Entry<L, R>, Comparable<Duo<L, R>>, Serializable {

    private static final long serialVersionUID = 1L;

    public static <L, R> Duo<L, R> of(final L left, final R right) {
        return FixedDuo.of(left, right);
    }

    public static <L, R> Duo<L, R> of(final Map.Entry<L, R> entry) {
        if (entry == null) {
            return FixedDuo.of(null, null);
        }
        return FixedDuo.of(entry.getKey(), entry.getValue());
    }

    public static <L, R> Duo<L, R> ofNonNull(final L left, final R right) {
        return FixedDuo.ofNonNull(left, right);
    }

    public abstract L getLeft();

    public abstract R getRight();

    @Override
    public final L getKey() {
        return getLeft();
    }

    @Override
    public R getValue() {
        return getRight();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(final Duo<L, R> other) {
        final int cmpLeft = ((Comparable<Object>) getLeft()).compareTo(other.getLeft());
        if (cmpLeft != 0) {
            return cmpLeft;
        }
        return ((Comparable<Object>) getRight()).compareTo(other.getRight());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Map.Entry<?, ?>) {
            final Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
            return Objects.equals(getKey(), other.getKey())
                    && Objects.equals(getValue(), other.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
        return "(" + getLeft() + ',' + getRight() + ')';
    }

    public String toString(final String format) {
        return String.format(format, getLeft(), getRight());
    }
}

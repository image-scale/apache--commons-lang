package com.lang.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Interval<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static final Comparator NATURAL = (a, b) -> ((Comparable) a).compareTo(b);

    private final T minimum;
    private final T maximum;
    private final Comparator<T> comparator;
    private final int hashCode;

    public static <T extends Comparable<? super T>> Interval<T> of(final T fromInclusive, final T toInclusive) {
        return of(fromInclusive, toInclusive, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> Interval<T> of(final T fromInclusive, final T toInclusive, final Comparator<T> comparator) {
        return new Interval<>(fromInclusive, toInclusive, comparator);
    }

    public static <T extends Comparable<? super T>> Interval<T> is(final T element) {
        return of(element, element, null);
    }

    @SuppressWarnings("unchecked")
    private Interval(final T element1, final T element2, final Comparator<T> comp) {
        Objects.requireNonNull(element1, "element1");
        Objects.requireNonNull(element2, "element2");
        this.comparator = comp != null ? comp : NATURAL;
        if (this.comparator.compare(element1, element2) <= 0) {
            this.minimum = element1;
            this.maximum = element2;
        } else {
            this.minimum = element2;
            this.maximum = element1;
        }
        this.hashCode = Objects.hash(minimum, maximum);
    }

    public T getMinimum() { return minimum; }

    public T getMaximum() { return maximum; }

    public Comparator<T> getComparator() { return comparator; }

    public boolean contains(final T element) {
        if (element == null) {
            return false;
        }
        return comparator.compare(element, minimum) >= 0 && comparator.compare(element, maximum) <= 0;
    }

    public boolean containsRange(final Interval<T> other) {
        if (other == null) {
            return false;
        }
        return contains(other.minimum) && contains(other.maximum);
    }

    public boolean isOverlappedBy(final Interval<T> other) {
        if (other == null) {
            return false;
        }
        return other.contains(minimum) || other.contains(maximum)
                || contains(other.minimum);
    }

    public Interval<T> intersectionWith(final Interval<T> other) {
        if (!isOverlappedBy(other)) {
            throw new IllegalArgumentException("Cannot calculate intersection for non-overlapping ranges");
        }
        final T newMin = comparator.compare(minimum, other.minimum) >= 0 ? minimum : other.minimum;
        final T newMax = comparator.compare(maximum, other.maximum) <= 0 ? maximum : other.maximum;
        return of(newMin, newMax, comparator);
    }

    public T fit(final T element) {
        Objects.requireNonNull(element, "element");
        if (comparator.compare(element, minimum) < 0) {
            return minimum;
        }
        if (comparator.compare(element, maximum) > 0) {
            return maximum;
        }
        return element;
    }

    public boolean isAfter(final T element) {
        if (element == null) {
            return false;
        }
        return comparator.compare(element, minimum) < 0;
    }

    public boolean isBefore(final T element) {
        if (element == null) {
            return false;
        }
        return comparator.compare(element, maximum) > 0;
    }

    public boolean isAfterRange(final Interval<T> other) {
        if (other == null) {
            return false;
        }
        return isAfter(other.maximum);
    }

    public boolean isBeforeRange(final Interval<T> other) {
        if (other == null) {
            return false;
        }
        return isBefore(other.minimum);
    }

    public int elementCompareTo(final T element) {
        Objects.requireNonNull(element, "element");
        if (isAfter(element)) {
            return -1;
        }
        if (isBefore(element)) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final Interval<T> other = (Interval<T>) obj;
        return minimum.equals(other.minimum) && maximum.equals(other.maximum);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "[" + minimum + ".." + maximum + "]";
    }
}

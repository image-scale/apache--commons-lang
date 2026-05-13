package com.lang.util.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OrderingComparator {

    private int comparison;

    public OrderingComparator() {
        this.comparison = 0;
    }

    public OrderingComparator appendSuper(final int superCompareTo) {
        if (comparison != 0) {
            return this;
        }
        comparison = superCompareTo;
        return this;
    }

    @SuppressWarnings("unchecked")
    public OrderingComparator append(final Object lhs, final Object rhs) {
        return append(lhs, rhs, null);
    }

    @SuppressWarnings("unchecked")
    public OrderingComparator append(final Object lhs, final Object rhs, final Comparator<?> comparator) {
        if (comparison != 0) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null) {
            comparison = -1;
            return this;
        }
        if (rhs == null) {
            comparison = 1;
            return this;
        }
        if (lhs.getClass().isArray()) {
            appendArray(lhs, rhs, comparator);
        } else if (comparator == null) {
            final Comparable<Object> comparable = (Comparable<Object>) lhs;
            comparison = comparable.compareTo(rhs);
        } else {
            final Comparator<Object> comp = (Comparator<Object>) comparator;
            comparison = comp.compare(lhs, rhs);
        }
        return this;
    }

    public OrderingComparator append(final long lhs, final long rhs) {
        if (comparison != 0) {
            return this;
        }
        comparison = Long.compare(lhs, rhs);
        return this;
    }

    public OrderingComparator append(final int lhs, final int rhs) {
        if (comparison != 0) {
            return this;
        }
        comparison = Integer.compare(lhs, rhs);
        return this;
    }

    public OrderingComparator append(final short lhs, final short rhs) {
        if (comparison != 0) {
            return this;
        }
        comparison = Short.compare(lhs, rhs);
        return this;
    }

    public OrderingComparator append(final char lhs, final char rhs) {
        if (comparison != 0) {
            return this;
        }
        comparison = Character.compare(lhs, rhs);
        return this;
    }

    public OrderingComparator append(final byte lhs, final byte rhs) {
        if (comparison != 0) {
            return this;
        }
        comparison = Byte.compare(lhs, rhs);
        return this;
    }

    public OrderingComparator append(final double lhs, final double rhs) {
        if (comparison != 0) {
            return this;
        }
        comparison = Double.compare(lhs, rhs);
        return this;
    }

    public OrderingComparator append(final float lhs, final float rhs) {
        if (comparison != 0) {
            return this;
        }
        comparison = Float.compare(lhs, rhs);
        return this;
    }

    public OrderingComparator append(final boolean lhs, final boolean rhs) {
        if (comparison != 0) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        comparison = lhs ? 1 : -1;
        return this;
    }

    public OrderingComparator append(final Object[] lhs, final Object[] rhs) {
        return append(lhs, rhs, null);
    }

    public OrderingComparator append(final Object[] lhs, final Object[] rhs, final Comparator<?> comparator) {
        if (comparison != 0) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null) {
            comparison = -1;
            return this;
        }
        if (rhs == null) {
            comparison = 1;
            return this;
        }
        if (lhs.length != rhs.length) {
            comparison = lhs.length < rhs.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < lhs.length && comparison == 0; i++) {
            append(lhs[i], rhs[i], comparator);
        }
        return this;
    }

    public OrderingComparator append(final int[] lhs, final int[] rhs) {
        if (comparison != 0) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null) {
            comparison = -1;
            return this;
        }
        if (rhs == null) {
            comparison = 1;
            return this;
        }
        if (lhs.length != rhs.length) {
            comparison = lhs.length < rhs.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < lhs.length && comparison == 0; i++) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public OrderingComparator append(final long[] lhs, final long[] rhs) {
        if (comparison != 0) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null) {
            comparison = -1;
            return this;
        }
        if (rhs == null) {
            comparison = 1;
            return this;
        }
        if (lhs.length != rhs.length) {
            comparison = lhs.length < rhs.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < lhs.length && comparison == 0; i++) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public OrderingComparator append(final double[] lhs, final double[] rhs) {
        if (comparison != 0) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null) {
            comparison = -1;
            return this;
        }
        if (rhs == null) {
            comparison = 1;
            return this;
        }
        if (lhs.length != rhs.length) {
            comparison = lhs.length < rhs.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < lhs.length && comparison == 0; i++) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public OrderingComparator append(final boolean[] lhs, final boolean[] rhs) {
        if (comparison != 0) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null) {
            comparison = -1;
            return this;
        }
        if (rhs == null) {
            comparison = 1;
            return this;
        }
        if (lhs.length != rhs.length) {
            comparison = lhs.length < rhs.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < lhs.length && comparison == 0; i++) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public int toComparison() {
        return comparison;
    }

    public Integer build() {
        return Integer.valueOf(toComparison());
    }

    private void appendArray(final Object lhs, final Object rhs, final Comparator<?> comparator) {
        if (lhs instanceof long[]) {
            append((long[]) lhs, (long[]) rhs);
        } else if (lhs instanceof int[]) {
            append((int[]) lhs, (int[]) rhs);
        } else if (lhs instanceof double[]) {
            append((double[]) lhs, (double[]) rhs);
        } else if (lhs instanceof boolean[]) {
            append((boolean[]) lhs, (boolean[]) rhs);
        } else {
            append((Object[]) lhs, (Object[]) rhs, comparator);
        }
    }

    public static int reflectionCompare(final Object lhs, final Object rhs, final String... excludeFields) {
        return reflectionCompare(lhs, rhs, false, null, excludeFields);
    }

    public static int reflectionCompare(final Object lhs, final Object rhs,
                                        final boolean compareTransients,
                                        final Class<?> reflectUpToClass,
                                        final String... excludeFields) {
        if (lhs == rhs) {
            return 0;
        }
        Objects.requireNonNull(lhs, "lhs");
        Objects.requireNonNull(rhs, "rhs");
        final Class<?> lhsClass = lhs.getClass();
        if (!lhsClass.isInstance(rhs)) {
            throw new ClassCastException("Cannot compare " + lhsClass.getName()
                    + " with " + rhs.getClass().getName());
        }
        final OrderingComparator comparator = new OrderingComparator();
        final Set<String> excludeSet = new HashSet<>(Arrays.asList(excludeFields));
        Class<?> clazz = lhsClass;
        while (clazz != null && clazz != reflectUpToClass) {
            for (final Field field : clazz.getDeclaredFields()) {
                if (excludeSet.contains(field.getName())) {
                    continue;
                }
                if (!compareTransients && Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    comparator.append(field.get(lhs), field.get(rhs));
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException("Unexpected IllegalAccessException", e);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return comparator.toComparison();
    }
}

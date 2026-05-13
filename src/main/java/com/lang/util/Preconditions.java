package com.lang.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Preconditions {

    private Preconditions() {
    }

    public static <T> T notNull(final T object) {
        return notNull(object, "The validated object is null");
    }

    public static <T> T notNull(final T object, final String message, final Object... values) {
        if (object == null) {
            throw new NullPointerException(String.format(message, values));
        }
        return object;
    }

    public static <T extends CharSequence> T notEmpty(final T chars) {
        return notEmpty(chars, "The validated character sequence is empty");
    }

    public static <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... values) {
        notNull(chars, message, values);
        if (chars.length() == 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }

    public static <T extends Collection<?>> T notEmpty(final T collection) {
        return notEmpty(collection, "The validated collection is empty");
    }

    public static <T extends Collection<?>> T notEmpty(final T collection, final String message, final Object... values) {
        notNull(collection, message, values);
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return collection;
    }

    public static <T extends Map<?, ?>> T notEmpty(final T map) {
        return notEmpty(map, "The validated map is empty");
    }

    public static <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... values) {
        notNull(map, message, values);
        if (map.isEmpty()) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return map;
    }

    public static <T> T[] notEmpty(final T[] array) {
        return notEmpty(array, "The validated array is empty");
    }

    public static <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
        notNull(array, message, values);
        if (array.length == 0) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return array;
    }

    public static <T extends CharSequence> T notBlank(final T chars) {
        return notBlank(chars, "The validated character sequence is blank");
    }

    public static <T extends CharSequence> T notBlank(final T chars, final String message, final Object... values) {
        notNull(chars, message, values);
        if (TextUtils.isBlank(chars)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return chars;
    }

    public static void isTrue(final boolean expression) {
        isTrue(expression, "The validated expression is false");
    }

    public static void isTrue(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static void isTrue(final boolean expression, final String message, final long value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, Long.valueOf(value)));
        }
    }

    public static void isTrue(final boolean expression, final String message, final double value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, Double.valueOf(value)));
        }
    }

    public static void validState(final boolean expression) {
        validState(expression, "The validated state is false");
    }

    public static void validState(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, values));
        }
    }

    public static <T extends CharSequence> T validIndex(final T chars, final int index) {
        return validIndex(chars, index, "The validated character sequence index is invalid: %d", Integer.valueOf(index));
    }

    public static <T extends CharSequence> T validIndex(final T chars, final int index, final String message, final Object... values) {
        notNull(chars);
        if (index < 0 || index >= chars.length()) {
            throw new IndexOutOfBoundsException(String.format(message, values));
        }
        return chars;
    }

    public static <T extends Collection<?>> T validIndex(final T collection, final int index) {
        return validIndex(collection, index, "The validated collection index is invalid: %d", Integer.valueOf(index));
    }

    public static <T extends Collection<?>> T validIndex(final T collection, final int index, final String message, final Object... values) {
        notNull(collection);
        if (index < 0 || index >= collection.size()) {
            throw new IndexOutOfBoundsException(String.format(message, values));
        }
        return collection;
    }

    public static <T> T[] validIndex(final T[] array, final int index) {
        return validIndex(array, index, "The validated array index is invalid: %d", Integer.valueOf(index));
    }

    public static <T> T[] validIndex(final T[] array, final int index, final String message, final Object... values) {
        notNull(array);
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException(String.format(message, values));
        }
        return array;
    }

    public static <T extends CharSequence> T matchesPattern(final T input, final String pattern) {
        return matchesPattern(input, pattern, "The string %s does not match the pattern %s", input, pattern);
    }

    public static <T extends CharSequence> T matchesPattern(final T input, final String pattern, final String message, final Object... values) {
        if (!Pattern.matches(pattern, input)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return input;
    }

    public static <T> T isInstanceOf(final Class<?> type, final T obj) {
        return isInstanceOf(type, obj, "Expected type: %s, actual: %s", type.getName(), obj == null ? "null" : obj.getClass().getName());
    }

    public static <T> T isInstanceOf(final Class<?> type, final T obj, final String message, final Object... values) {
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
        return obj;
    }

    public static void isAssignableFrom(final Class<?> superType, final Class<?> type) {
        isAssignableFrom(superType, type, "Cannot assign %s to %s", type == null ? "null" : type.getName(), superType.getName());
    }

    public static void isAssignableFrom(final Class<?> superType, final Class<?> type, final String message, final Object... values) {
        if (!superType.isAssignableFrom(type)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static <T> T[] noNullElements(final T[] array) {
        return noNullElements(array, "The validated array contains null element at index: %d");
    }

    public static <T> T[] noNullElements(final T[] array, final String message, final Object... values) {
        notNull(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException(String.format(message, Integer.valueOf(i)));
            }
        }
        return array;
    }

    public static <T extends Iterable<?>> T noNullElements(final T iterable) {
        return noNullElements(iterable, "The validated collection contains null element at index: %d");
    }

    public static <T extends Iterable<?>> T noNullElements(final T iterable, final String message, final Object... values) {
        notNull(iterable);
        int i = 0;
        for (final Object element : iterable) {
            if (element == null) {
                throw new IllegalArgumentException(String.format(message, Integer.valueOf(i)));
            }
            i++;
        }
        return iterable;
    }

    public static <T extends Comparable<T>> void inclusiveBetween(final T start, final T end, final T value) {
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", value, start, end));
        }
    }

    public static <T extends Comparable<T>> void inclusiveBetween(final T start, final T end, final T value, final String message) {
        if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void inclusiveBetween(final long start, final long end, final long value) {
        if (value < start || value > end) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", value, start, end));
        }
    }

    public static void inclusiveBetween(final double start, final double end, final double value) {
        if (value < start || value > end) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", value, start, end));
        }
    }

    public static <T extends Comparable<T>> void exclusiveBetween(final T start, final T end, final T value) {
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", value, start, end));
        }
    }

    public static <T extends Comparable<T>> void exclusiveBetween(final T start, final T end, final T value, final String message) {
        if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void exclusiveBetween(final long start, final long end, final long value) {
        if (value <= start || value >= end) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", value, start, end));
        }
    }

    public static void exclusiveBetween(final double start, final double end, final double value) {
        if (value <= start || value >= end) {
            throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", value, start, end));
        }
    }

    public static void finite(final double value) {
        finite(value, "The value is invalid: %f", value);
    }

    public static void finite(final double value, final String message, final Object... values) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static void notNaN(final double value) {
        notNaN(value, "The validated value is not a number");
    }

    public static void notNaN(final double value, final String message, final Object... values) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }
}

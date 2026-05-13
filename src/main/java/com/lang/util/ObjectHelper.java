package com.lang.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class ObjectHelper {

    private ObjectHelper() {
    }

    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    public static <T> T getIfNull(final T object, final Supplier<T> defaultSupplier) {
        return object != null ? object : (defaultSupplier != null ? defaultSupplier.get() : null);
    }

    @SafeVarargs
    public static <T> T firstNonNull(final T... values) {
        if (values != null) {
            for (final T val : values) {
                if (val != null) {
                    return val;
                }
            }
        }
        return null;
    }

    @SafeVarargs
    public static <T> T getFirstNonNull(final Supplier<T>... suppliers) {
        if (suppliers != null) {
            for (final Supplier<T> supplier : suppliers) {
                if (supplier != null) {
                    final T value = supplier.get();
                    if (value != null) {
                        return value;
                    }
                }
            }
        }
        return null;
    }

    public static boolean allNotNull(final Object... values) {
        if (values == null) {
            return false;
        }
        for (final Object val : values) {
            if (val == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean allNull(final Object... values) {
        if (values == null) {
            return true;
        }
        for (final Object val : values) {
            if (val != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean anyNotNull(final Object... values) {
        return firstNonNull(values) != null;
    }

    public static boolean anyNull(final Object... values) {
        if (values == null) {
            return true;
        }
        for (final Object val : values) {
            if (val == null) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<? super T>> int compare(final T c1, final T c2) {
        return compare(c1, c2, false);
    }

    public static <T extends Comparable<? super T>> int compare(final T c1, final T c2, final boolean nullGreater) {
        if (c1 == c2) {
            return 0;
        }
        if (c1 == null) {
            return nullGreater ? 1 : -1;
        }
        if (c2 == null) {
            return nullGreater ? -1 : 1;
        }
        return c1.compareTo(c2);
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(final T obj) {
        if (!(obj instanceof Cloneable)) {
            return null;
        }
        try {
            if (obj.getClass().isArray()) {
                final Class<?> componentType = obj.getClass().getComponentType();
                if (componentType.isPrimitive()) {
                    int length = Array.getLength(obj);
                    final Object result = Array.newInstance(componentType, length);
                    while (length-- > 0) {
                        Array.set(result, length, Array.get(obj, length));
                    }
                    return (T) result;
                } else {
                    return (T) ((Object[]) obj).clone();
                }
            }
            final java.lang.reflect.Method cloneMethod = obj.getClass().getMethod("clone");
            return (T) cloneMethod.invoke(obj);
        } catch (final Exception e) {
            return null;
        }
    }

    public static <T> T cloneIfPossible(final T obj) {
        final T clone = clone(obj);
        return clone != null ? clone : obj;
    }

    public static String identityToString(final Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(object));
    }

    public static String toString(final Object obj) {
        return toString(obj, "");
    }

    public static String toString(final Object obj, final String nullStr) {
        return obj == null ? nullStr : obj.toString();
    }

    public static boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        }
        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        }
        return false;
    }

    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

    @SafeVarargs
    public static <T extends Comparable<? super T>> T max(final T... values) {
        T result = null;
        if (values != null) {
            for (final T value : values) {
                if (value != null) {
                    if (result == null || value.compareTo(result) > 0) {
                        result = value;
                    }
                }
            }
        }
        return result;
    }

    @SafeVarargs
    public static <T extends Comparable<? super T>> T min(final T... values) {
        T result = null;
        if (values != null) {
            for (final T value : values) {
                if (value != null) {
                    if (result == null || value.compareTo(result) < 0) {
                        result = value;
                    }
                }
            }
        }
        return result;
    }

    @SafeVarargs
    public static <T extends Comparable<? super T>> T median(final T... items) {
        if (items == null || items.length == 0) {
            throw new IllegalArgumentException("Cannot find median of empty array");
        }
        final T[] sorted = items.clone();
        java.util.Arrays.sort(sorted);
        return sorted[sorted.length / 2];
    }

    @SafeVarargs
    public static <T> T mode(final T... items) {
        if (items == null || items.length == 0) {
            return null;
        }
        final Map<T, Integer> countMap = new HashMap<>();
        for (final T item : items) {
            countMap.merge(item, 1, Integer::sum);
        }
        T maxItem = null;
        int maxCount = 0;
        for (final Map.Entry<T, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxItem = entry.getKey();
            }
        }
        if (maxCount <= 1) {
            return null;
        }
        return maxItem;
    }

    public static <T> T requireNonEmpty(final T obj) {
        return requireNonEmpty(obj, "Object must not be empty");
    }

    public static <T> T requireNonEmpty(final T obj, final String message) {
        if (isEmpty(obj)) {
            throw new IllegalArgumentException(message);
        }
        return obj;
    }

    public static boolean notEqual(final Object object1, final Object object2) {
        return !Objects.equals(object1, object2);
    }

    public static int hashCode(final Object obj) {
        return Objects.hashCode(obj);
    }

    public static int hashCodeMulti(final Object... objects) {
        return Objects.hash(objects);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(final T object) {
        return object == null ? null : (Class<T>) object.getClass();
    }
}

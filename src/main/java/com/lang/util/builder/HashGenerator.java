package com.lang.util.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HashGenerator {

    private static final int DEFAULT_INITIAL = 17;
    private static final int DEFAULT_MULTIPLIER = 37;

    private final int multiplier;
    private int total;

    public HashGenerator() {
        this(DEFAULT_INITIAL, DEFAULT_MULTIPLIER);
    }

    public HashGenerator(final int initialOddNumber, final int multiplierOddNumber) {
        if (initialOddNumber % 2 == 0) {
            throw new IllegalArgumentException("HashGenerator requires an odd initial value");
        }
        if (multiplierOddNumber % 2 == 0) {
            throw new IllegalArgumentException("HashGenerator requires an odd multiplier");
        }
        this.total = initialOddNumber;
        this.multiplier = multiplierOddNumber;
    }

    public HashGenerator appendSuper(final int superHashCode) {
        total = total * multiplier + superHashCode;
        return this;
    }

    public HashGenerator append(final Object object) {
        if (object == null) {
            total = total * multiplier;
        } else if (object.getClass().isArray()) {
            appendArray(object);
        } else {
            total = total * multiplier + object.hashCode();
        }
        return this;
    }

    public HashGenerator append(final long value) {
        total = total * multiplier + (int) (value ^ (value >>> 32));
        return this;
    }

    public HashGenerator append(final int value) {
        total = total * multiplier + value;
        return this;
    }

    public HashGenerator append(final short value) {
        total = total * multiplier + value;
        return this;
    }

    public HashGenerator append(final char value) {
        total = total * multiplier + value;
        return this;
    }

    public HashGenerator append(final byte value) {
        total = total * multiplier + value;
        return this;
    }

    public HashGenerator append(final double value) {
        return append(Double.doubleToLongBits(value));
    }

    public HashGenerator append(final float value) {
        total = total * multiplier + Float.floatToIntBits(value);
        return this;
    }

    public HashGenerator append(final boolean value) {
        total = total * multiplier + (value ? 0 : 1);
        return this;
    }

    public HashGenerator append(final Object[] array) {
        if (array == null) {
            total = total * multiplier;
        } else {
            for (final Object element : array) {
                append(element);
            }
        }
        return this;
    }

    public HashGenerator append(final int[] array) {
        if (array == null) {
            total = total * multiplier;
        } else {
            for (final int element : array) {
                append(element);
            }
        }
        return this;
    }

    public HashGenerator append(final long[] array) {
        if (array == null) {
            total = total * multiplier;
        } else {
            for (final long element : array) {
                append(element);
            }
        }
        return this;
    }

    public int toHashCode() {
        return total;
    }

    public Integer build() {
        return Integer.valueOf(toHashCode());
    }

    private void appendArray(final Object array) {
        if (array instanceof long[]) {
            append((long[]) array);
        } else if (array instanceof int[]) {
            append((int[]) array);
        } else {
            append((Object[]) array);
        }
    }

    public static int reflectionHashCode(final Object object, final String... excludeFields) {
        return reflectionHashCode(DEFAULT_INITIAL, DEFAULT_MULTIPLIER, object, false, null, excludeFields);
    }

    public static int reflectionHashCode(final int initialOddNumber, final int multiplierOddNumber,
                                         final Object object, final boolean testTransients,
                                         final Class<?> reflectUpToClass, final String... excludeFields) {
        if (object == null) {
            throw new IllegalArgumentException("The object to build a hash code for must not be null");
        }
        final HashGenerator generator = new HashGenerator(initialOddNumber, multiplierOddNumber);
        final Set<String> excludeSet = new HashSet<>(Arrays.asList(excludeFields));
        Class<?> clazz = object.getClass();
        while (clazz != null && clazz != reflectUpToClass) {
            for (final Field field : clazz.getDeclaredFields()) {
                if (excludeSet.contains(field.getName())) {
                    continue;
                }
                if (!testTransients && Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    generator.append(field.get(object));
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException("Unexpected IllegalAccessException", e);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return generator.toHashCode();
    }
}

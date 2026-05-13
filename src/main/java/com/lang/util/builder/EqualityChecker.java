package com.lang.util.builder;

import java.util.Arrays;

public class EqualityChecker {

    private boolean result = true;

    public EqualityChecker() {
    }

    public EqualityChecker appendSuper(final boolean superEquals) {
        if (!result) {
            return this;
        }
        result = superEquals;
        return this;
    }

    public EqualityChecker append(final Object lhs, final Object rhs) {
        if (!result) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            result = false;
            return this;
        }
        final Class<?> lhsClass = lhs.getClass();
        if (lhsClass.isArray()) {
            appendArray(lhs, rhs);
        } else {
            result = lhs.equals(rhs);
        }
        return this;
    }

    public EqualityChecker append(final long lhs, final long rhs) {
        if (!result) {
            return this;
        }
        result = lhs == rhs;
        return this;
    }

    public EqualityChecker append(final int lhs, final int rhs) {
        if (!result) {
            return this;
        }
        result = lhs == rhs;
        return this;
    }

    public EqualityChecker append(final short lhs, final short rhs) {
        if (!result) {
            return this;
        }
        result = lhs == rhs;
        return this;
    }

    public EqualityChecker append(final char lhs, final char rhs) {
        if (!result) {
            return this;
        }
        result = lhs == rhs;
        return this;
    }

    public EqualityChecker append(final byte lhs, final byte rhs) {
        if (!result) {
            return this;
        }
        result = lhs == rhs;
        return this;
    }

    public EqualityChecker append(final double lhs, final double rhs) {
        if (!result) {
            return this;
        }
        result = Double.doubleToLongBits(lhs) == Double.doubleToLongBits(rhs);
        return this;
    }

    public EqualityChecker append(final float lhs, final float rhs) {
        if (!result) {
            return this;
        }
        result = Float.floatToIntBits(lhs) == Float.floatToIntBits(rhs);
        return this;
    }

    public EqualityChecker append(final boolean lhs, final boolean rhs) {
        if (!result) {
            return this;
        }
        result = lhs == rhs;
        return this;
    }

    public EqualityChecker append(final Object[] lhs, final Object[] rhs) {
        if (!result) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            result = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            result = false;
            return this;
        }
        for (int i = 0; i < lhs.length && result; i++) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public EqualityChecker append(final int[] lhs, final int[] rhs) {
        if (!result) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            result = false;
            return this;
        }
        result = Arrays.equals(lhs, rhs);
        return this;
    }

    public EqualityChecker append(final long[] lhs, final long[] rhs) {
        if (!result) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            result = false;
            return this;
        }
        result = Arrays.equals(lhs, rhs);
        return this;
    }

    public EqualityChecker append(final double[] lhs, final double[] rhs) {
        if (!result) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            result = false;
            return this;
        }
        result = Arrays.equals(lhs, rhs);
        return this;
    }

    public EqualityChecker append(final boolean[] lhs, final boolean[] rhs) {
        if (!result) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            result = false;
            return this;
        }
        result = Arrays.equals(lhs, rhs);
        return this;
    }

    public boolean isEquals() {
        return result;
    }

    public Boolean build() {
        return Boolean.valueOf(isEquals());
    }

    public void reset() {
        result = true;
    }

    private void appendArray(final Object lhs, final Object rhs) {
        if (lhs instanceof long[]) {
            append((long[]) lhs, (long[]) rhs);
        } else if (lhs instanceof int[]) {
            append((int[]) lhs, (int[]) rhs);
        } else if (lhs instanceof double[]) {
            append((double[]) lhs, (double[]) rhs);
        } else if (lhs instanceof boolean[]) {
            append((boolean[]) lhs, (boolean[]) rhs);
        } else {
            append((Object[]) lhs, (Object[]) rhs);
        }
    }

    public static boolean reflectionEquals(final Object lhs, final Object rhs, final String... excludeFields) {
        return reflectionEquals(lhs, rhs, false, null, excludeFields);
    }

    public static boolean reflectionEquals(final Object lhs, final Object rhs,
                                           final boolean testTransients, final Class<?> reflectUpToClass,
                                           final String... excludeFields) {
        if (lhs == rhs) {
            return true;
        }
        if (lhs == null || rhs == null) {
            return false;
        }
        final Class<?> lhsClass = lhs.getClass();
        final Class<?> rhsClass = rhs.getClass();
        if (!lhsClass.isAssignableFrom(rhsClass) && !rhsClass.isAssignableFrom(lhsClass)) {
            return false;
        }
        final EqualityChecker checker = new EqualityChecker();
        final java.util.Set<String> excludeSet = new java.util.HashSet<>(Arrays.asList(excludeFields));
        Class<?> clazz = lhsClass;
        while (clazz != null && clazz != reflectUpToClass) {
            for (final java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                if (excludeSet.contains(field.getName())) {
                    continue;
                }
                if (!testTransients && java.lang.reflect.Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    checker.append(field.get(lhs), field.get(rhs));
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException("Unexpected IllegalAccessException", e);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return checker.isEquals();
    }
}

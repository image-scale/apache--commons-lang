package com.lang.util;

import java.util.*;

public final class EnumHelper {

    private EnumHelper() {}

    public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName) {
        return getEnum(enumClass, enumName, null);
    }

    public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName,
                                                 final E defaultEnum) {
        if (enumClass == null || enumName == null) {
            return defaultEnum;
        }
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (final IllegalArgumentException e) {
            return defaultEnum;
        }
    }

    public static <E extends Enum<E>> E getEnumIgnoreCase(final Class<E> enumClass,
                                                           final String enumName) {
        return getEnumIgnoreCase(enumClass, enumName, null);
    }

    public static <E extends Enum<E>> E getEnumIgnoreCase(final Class<E> enumClass,
                                                           final String enumName,
                                                           final E defaultEnum) {
        if (enumClass == null || enumName == null) {
            return defaultEnum;
        }
        for (final E constant : enumClass.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(enumName)) {
                return constant;
            }
        }
        return defaultEnum;
    }

    public static <E extends Enum<E>> boolean isValidEnum(final Class<E> enumClass,
                                                           final String enumName) {
        return getEnum(enumClass, enumName) != null;
    }

    public static <E extends Enum<E>> boolean isValidEnumIgnoreCase(final Class<E> enumClass,
                                                                     final String enumName) {
        return getEnumIgnoreCase(enumClass, enumName) != null;
    }

    public static <E extends Enum<E>> List<E> getEnumList(final Class<E> enumClass) {
        return new ArrayList<>(Arrays.asList(enumClass.getEnumConstants()));
    }

    public static <E extends Enum<E>> Map<String, E> getEnumMap(final Class<E> enumClass) {
        final Map<String, E> map = new LinkedHashMap<>();
        for (final E constant : enumClass.getEnumConstants()) {
            map.put(constant.name(), constant);
        }
        return map;
    }

    public static <E extends Enum<E>> long generateBitVector(final Class<E> enumClass,
                                                              final Iterable<? extends E> values) {
        long vector = 0;
        for (final E value : values) {
            vector |= 1L << value.ordinal();
        }
        return vector;
    }

    @SafeVarargs
    public static <E extends Enum<E>> long generateBitVector(final Class<E> enumClass,
                                                              final E... values) {
        long vector = 0;
        for (final E value : values) {
            vector |= 1L << value.ordinal();
        }
        return vector;
    }

    public static <E extends Enum<E>> EnumSet<E> processBitVector(final Class<E> enumClass,
                                                                    final long value) {
        final EnumSet<E> result = EnumSet.noneOf(enumClass);
        for (final E constant : enumClass.getEnumConstants()) {
            if ((value & (1L << constant.ordinal())) != 0) {
                result.add(constant);
            }
        }
        return result;
    }
}

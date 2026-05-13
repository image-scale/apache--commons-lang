package com.lang.util.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringRepresentationBuilder {

    private final StringBuilder buffer;
    private final RepresentationStyle style;
    private final Object object;
    private boolean firstField = true;

    public StringRepresentationBuilder(final Object object) {
        this(object, RepresentationStyle.DEFAULT);
    }

    public StringRepresentationBuilder(final Object object, final RepresentationStyle style) {
        this.object = object;
        this.style = style != null ? style : RepresentationStyle.DEFAULT;
        this.buffer = new StringBuilder();
        this.style.appendStart(buffer, object);
    }

    public StringRepresentationBuilder append(final String fieldName, final Object value) {
        style.appendField(buffer, fieldName, value, firstField);
        firstField = false;
        return this;
    }

    public StringRepresentationBuilder append(final String fieldName, final int value) {
        return append(fieldName, Integer.valueOf(value));
    }

    public StringRepresentationBuilder append(final String fieldName, final long value) {
        return append(fieldName, Long.valueOf(value));
    }

    public StringRepresentationBuilder append(final String fieldName, final double value) {
        return append(fieldName, Double.valueOf(value));
    }

    public StringRepresentationBuilder append(final String fieldName, final float value) {
        return append(fieldName, Float.valueOf(value));
    }

    public StringRepresentationBuilder append(final String fieldName, final boolean value) {
        return append(fieldName, Boolean.valueOf(value));
    }

    public StringRepresentationBuilder append(final String fieldName, final short value) {
        return append(fieldName, Short.valueOf(value));
    }

    public StringRepresentationBuilder append(final String fieldName, final byte value) {
        return append(fieldName, Byte.valueOf(value));
    }

    public StringRepresentationBuilder append(final String fieldName, final char value) {
        return append(fieldName, Character.valueOf(value));
    }

    public StringRepresentationBuilder appendSuper(final String superToString) {
        if (superToString != null) {
            style.appendField(buffer, "super", superToString, firstField);
            firstField = false;
        }
        return this;
    }

    public String build() {
        style.appendEnd(buffer);
        return buffer.toString();
    }

    @Override
    public String toString() {
        final StringBuilder copy = new StringBuilder(buffer);
        style.appendEnd(copy);
        return copy.toString();
    }

    public static String reflectionToString(final Object object) {
        return reflectionToString(object, RepresentationStyle.DEFAULT);
    }

    public static String reflectionToString(final Object object, final RepresentationStyle style) {
        return reflectionToString(object, style, false, null);
    }

    public static String reflectionToString(final Object object, final RepresentationStyle style,
                                            final boolean includeTransients,
                                            final Class<?> reflectUpToClass,
                                            final String... excludeFields) {
        if (object == null) {
            return "<null>";
        }
        final RepresentationStyle effectiveStyle = style != null ? style : RepresentationStyle.DEFAULT;
        final StringRepresentationBuilder builder = new StringRepresentationBuilder(object, effectiveStyle);
        final Set<String> excludeSet = excludeFields != null
                ? new HashSet<>(Arrays.asList(excludeFields))
                : new HashSet<>();
        Class<?> clazz = object.getClass();
        while (clazz != null && clazz != reflectUpToClass) {
            for (final Field field : clazz.getDeclaredFields()) {
                if (excludeSet.contains(field.getName())) {
                    continue;
                }
                if (!includeTransients && Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    builder.append(field.getName(), field.get(object));
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException("Unexpected IllegalAccessException", e);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return builder.build();
    }
}

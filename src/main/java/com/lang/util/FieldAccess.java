package com.lang.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class FieldAccess {

    private FieldAccess() {}

    public static Field getField(final Class<?> cls, final String fieldName) {
        return getField(cls, fieldName, false);
    }

    public static Field getField(final Class<?> cls, final String fieldName, final boolean forceAccess) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        if (fieldName == null) {
            throw new IllegalArgumentException("The field name must not be null");
        }
        Class<?> current = cls;
        while (current != null) {
            try {
                final Field field = current.getDeclaredField(fieldName);
                if (forceAccess) {
                    field.setAccessible(true);
                }
                return field;
            } catch (final NoSuchFieldException e) {
                // continue
            }
            current = current.getSuperclass();
        }
        for (final Class<?> iface : cls.getInterfaces()) {
            try {
                return iface.getField(fieldName);
            } catch (final NoSuchFieldException e) {
                // continue
            }
        }
        return null;
    }

    public static Field getDeclaredField(final Class<?> cls, final String fieldName, final boolean forceAccess) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        if (fieldName == null) {
            throw new IllegalArgumentException("The field name must not be null");
        }
        try {
            final Field field = cls.getDeclaredField(fieldName);
            if (forceAccess) {
                field.setAccessible(true);
            }
            return field;
        } catch (final NoSuchFieldException e) {
            return null;
        }
    }

    public static List<Field> getAllFields(final Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        final List<Field> fields = new ArrayList<>();
        Class<?> current = cls;
        while (current != null) {
            for (final Field field : current.getDeclaredFields()) {
                fields.add(field);
            }
            current = current.getSuperclass();
        }
        return fields;
    }

    public static List<Field> getFieldsWithAnnotation(final Class<?> cls,
                                                       final Class<? extends java.lang.annotation.Annotation> annotation) {
        final List<Field> result = new ArrayList<>();
        for (final Field field : getAllFields(cls)) {
            if (field.isAnnotationPresent(annotation)) {
                result.add(field);
            }
        }
        return result;
    }

    public static Object readField(final Object target, final String fieldName) throws IllegalAccessException {
        return readField(target, fieldName, false);
    }

    public static Object readField(final Object target, final String fieldName, final boolean forceAccess)
            throws IllegalAccessException {
        if (target == null) {
            throw new IllegalArgumentException("target must not be null");
        }
        final Field field = getField(target.getClass(), fieldName, forceAccess);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + fieldName
                    + " on " + target.getClass().getName());
        }
        return field.get(target);
    }

    public static Object readStaticField(final Class<?> cls, final String fieldName) throws IllegalAccessException {
        return readStaticField(cls, fieldName, false);
    }

    public static Object readStaticField(final Class<?> cls, final String fieldName, final boolean forceAccess)
            throws IllegalAccessException {
        final Field field = getField(cls, fieldName, forceAccess);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + fieldName
                    + " on " + cls.getName());
        }
        return field.get(null);
    }

    public static void writeField(final Object target, final String fieldName, final Object value)
            throws IllegalAccessException {
        writeField(target, fieldName, value, false);
    }

    public static void writeField(final Object target, final String fieldName, final Object value,
                                   final boolean forceAccess) throws IllegalAccessException {
        if (target == null) {
            throw new IllegalArgumentException("target must not be null");
        }
        final Field field = getField(target.getClass(), fieldName, forceAccess);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + fieldName
                    + " on " + target.getClass().getName());
        }
        field.set(target, value);
    }

    public static void writeStaticField(final Class<?> cls, final String fieldName, final Object value,
                                         final boolean forceAccess) throws IllegalAccessException {
        final Field field = getField(cls, fieldName, forceAccess);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + fieldName
                    + " on " + cls.getName());
        }
        field.set(null, value);
    }
}

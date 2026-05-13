package com.lang.util;

import java.util.*;

public final class TypeUtils {

    private TypeUtils() {}

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<>();
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE = new HashMap<>();

    static {
        PRIMITIVE_TO_WRAPPER.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_WRAPPER.put(byte.class, Byte.class);
        PRIMITIVE_TO_WRAPPER.put(char.class, Character.class);
        PRIMITIVE_TO_WRAPPER.put(short.class, Short.class);
        PRIMITIVE_TO_WRAPPER.put(int.class, Integer.class);
        PRIMITIVE_TO_WRAPPER.put(long.class, Long.class);
        PRIMITIVE_TO_WRAPPER.put(float.class, Float.class);
        PRIMITIVE_TO_WRAPPER.put(double.class, Double.class);
        PRIMITIVE_TO_WRAPPER.put(void.class, Void.class);
        for (final Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_TO_WRAPPER.entrySet()) {
            WRAPPER_TO_PRIMITIVE.put(entry.getValue(), entry.getKey());
        }
    }

    public static String getName(final Class<?> cls) {
        return getName(cls, null);
    }

    public static String getName(final Class<?> cls, final String valueIfNull) {
        return cls == null ? valueIfNull : cls.getName();
    }

    public static String getName(final Object object) {
        return getName(object, null);
    }

    public static String getName(final Object object, final String valueIfNull) {
        return object == null ? valueIfNull : object.getClass().getName();
    }

    public static String getShortName(final Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return getShortName(cls.getName());
    }

    public static String getShortName(final String className) {
        if (className == null) {
            return null;
        }
        final int lastDot = className.lastIndexOf('.');
        final String nameWithoutPackage = lastDot < 0 ? className : className.substring(lastDot + 1);
        return nameWithoutPackage.replace('$', '.');
    }

    public static String getPackageName(final Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return getPackageName(cls.getName());
    }

    public static String getPackageName(final String className) {
        if (className == null) {
            return "";
        }
        final int lastDot = className.lastIndexOf('.');
        return lastDot < 0 ? "" : className.substring(0, lastDot);
    }

    public static boolean isPrimitiveOrWrapper(final Class<?> type) {
        return type != null && (type.isPrimitive() || WRAPPER_TO_PRIMITIVE.containsKey(type));
    }

    public static boolean isPrimitiveWrapper(final Class<?> type) {
        return WRAPPER_TO_PRIMITIVE.containsKey(type);
    }

    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        if (cls != null && cls.isPrimitive()) {
            return PRIMITIVE_TO_WRAPPER.get(cls);
        }
        return cls;
    }

    public static Class<?> wrapperToPrimitive(final Class<?> cls) {
        return WRAPPER_TO_PRIMITIVE.get(cls);
    }

    public static Class<?>[] primitivesToWrappers(final Class<?>... classes) {
        if (classes == null) {
            return null;
        }
        final Class<?>[] result = new Class<?>[classes.length];
        for (int i = 0; i < classes.length; i++) {
            result[i] = primitiveToWrapper(classes[i]);
        }
        return result;
    }

    public static Class<?>[] wrappersToPrimitives(final Class<?>... classes) {
        if (classes == null) {
            return null;
        }
        final Class<?>[] result = new Class<?>[classes.length];
        for (int i = 0; i < classes.length; i++) {
            result[i] = wrapperToPrimitive(classes[i]);
        }
        return result;
    }

    public static boolean isAssignable(final Class<?> cls, final Class<?> toClass) {
        return isAssignable(cls, toClass, true);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> toClass, final boolean autoboxing) {
        if (toClass == null) {
            return false;
        }
        if (cls == null) {
            return !toClass.isPrimitive();
        }
        if (autoboxing) {
            if (cls.isPrimitive() && !toClass.isPrimitive()) {
                cls = primitiveToWrapper(cls);
            }
            if (toClass.isPrimitive() && !cls.isPrimitive()) {
                toClass = primitiveToWrapper(toClass);
            }
        }
        return toClass.isAssignableFrom(cls);
    }

    public static List<Class<?>> getAllSuperclasses(final Class<?> cls) {
        if (cls == null) {
            return null;
        }
        final List<Class<?>> classes = new ArrayList<>();
        Class<?> superclass = cls.getSuperclass();
        while (superclass != null) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }

    public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
        if (cls == null) {
            return null;
        }
        final LinkedHashSet<Class<?>> set = new LinkedHashSet<>();
        getAllInterfaces(cls, set);
        return new ArrayList<>(set);
    }

    private static void getAllInterfaces(Class<?> cls, final Set<Class<?>> set) {
        while (cls != null) {
            for (final Class<?> iface : cls.getInterfaces()) {
                if (set.add(iface)) {
                    getAllInterfaces(iface, set);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public static List<Class<?>> hierarchy(final Class<?> type) {
        final List<Class<?>> classes = new ArrayList<>();
        Class<?> cls = type;
        while (cls != null) {
            classes.add(cls);
            cls = cls.getSuperclass();
        }
        return classes;
    }

    public static boolean isInnerClass(final Class<?> cls) {
        return cls != null && cls.getEnclosingClass() != null;
    }

    public static Class<?> getClass(final String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static Class<?> getClass(final String className, final boolean initialize)
            throws ClassNotFoundException {
        return Class.forName(className, initialize, Thread.currentThread().getContextClassLoader());
    }
}

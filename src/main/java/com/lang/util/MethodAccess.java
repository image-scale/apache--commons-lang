package com.lang.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MethodAccess {

    private MethodAccess() {}

    private static final Class<?>[][] PRIMITIVE_WIDENINGS = {
        {byte.class, short.class, int.class, long.class, float.class, double.class},
        {short.class, int.class, long.class, float.class, double.class},
        {char.class, int.class, long.class, float.class, double.class},
        {int.class, long.class, float.class, double.class},
        {long.class, float.class, double.class},
        {float.class, double.class},
    };

    public static Method getMatchingMethod(final Class<?> cls, final String methodName,
                                           final Class<?>... parameterTypes) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        if (methodName == null) {
            throw new IllegalArgumentException("The method name must not be null");
        }
        final Method exact = getAccessibleMethod(cls, methodName, parameterTypes);
        if (exact != null) {
            return exact;
        }
        Method bestMatch = null;
        for (final Method candidate : getMethodsIncludingInherited(cls)) {
            if (!candidate.getName().equals(methodName)) {
                continue;
            }
            if (isAssignable(parameterTypes, candidate.getParameterTypes())) {
                if (bestMatch == null || isMoreSpecific(candidate, bestMatch)) {
                    bestMatch = candidate;
                }
            }
        }
        if (bestMatch != null) {
            bestMatch.setAccessible(true);
        }
        return bestMatch;
    }

    public static Method getAccessibleMethod(final Class<?> cls, final String methodName,
                                             final Class<?>... parameterTypes) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        if (methodName == null) {
            throw new IllegalArgumentException("The method name must not be null");
        }
        try {
            final Method method = cls.getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (final NoSuchMethodException e) {
            return null;
        }
    }

    public static Method getMatchingAccessibleMethod(final Class<?> cls, final String methodName,
                                                     final Class<?>... parameterTypes) {
        return getMatchingMethod(cls, methodName, parameterTypes);
    }

    public static List<Method> getMethodsIncludingInherited(final Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        final List<Method> methods = new ArrayList<>();
        Class<?> current = cls;
        while (current != null) {
            methods.addAll(Arrays.asList(current.getDeclaredMethods()));
            current = current.getSuperclass();
        }
        for (final Class<?> iface : cls.getInterfaces()) {
            methods.addAll(Arrays.asList(iface.getMethods()));
        }
        return methods;
    }

    public static List<Method> getMethodsWithAnnotation(final Class<?> cls,
                                                         final Class<? extends java.lang.annotation.Annotation> annotation) {
        if (cls == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        if (annotation == null) {
            throw new IllegalArgumentException("The annotation must not be null");
        }
        final List<Method> result = new ArrayList<>();
        for (final Method method : getMethodsIncludingInherited(cls)) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }

    public static Object invokeMethod(final Object object, final String methodName,
                                      final Object... args) throws ReflectiveOperationException {
        return invokeMethod(object, false, methodName, args);
    }

    public static Object invokeMethod(final Object object, final boolean forceAccess,
                                      final String methodName,
                                      final Object... args) throws ReflectiveOperationException {
        if (object == null) {
            throw new IllegalArgumentException("The object must not be null");
        }
        final Class<?>[] parameterTypes = toParameterTypes(args);
        final Method method = getMatchingMethod(object.getClass(), methodName, parameterTypes);
        if (method == null) {
            throw new NoSuchMethodException("No such method: " + methodName
                    + " on " + object.getClass().getName()
                    + " with parameter types " + Arrays.toString(parameterTypes));
        }
        if (forceAccess) {
            method.setAccessible(true);
        }
        return method.invoke(object, args);
    }

    public static Object invokeExactMethod(final Object object, final String methodName,
                                           final Object... args) throws ReflectiveOperationException {
        if (object == null) {
            throw new IllegalArgumentException("The object must not be null");
        }
        final Class<?>[] parameterTypes = toParameterTypes(args);
        final Method method = getAccessibleMethod(object.getClass(), methodName, parameterTypes);
        if (method == null) {
            throw new NoSuchMethodException("No such method: " + methodName
                    + " on " + object.getClass().getName()
                    + " with exact parameter types " + Arrays.toString(parameterTypes));
        }
        return method.invoke(object, args);
    }

    public static Object invokeStaticMethod(final Class<?> cls, final String methodName,
                                            final Object... args) throws ReflectiveOperationException {
        final Class<?>[] parameterTypes = toParameterTypes(args);
        final Method method = getMatchingMethod(cls, methodName, parameterTypes);
        if (method == null) {
            throw new NoSuchMethodException("No such static method: " + methodName
                    + " on " + cls.getName()
                    + " with parameter types " + Arrays.toString(parameterTypes));
        }
        method.setAccessible(true);
        return method.invoke(null, args);
    }

    public static Object invokeExactStaticMethod(final Class<?> cls, final String methodName,
                                                 final Object... args) throws ReflectiveOperationException {
        final Class<?>[] parameterTypes = toParameterTypes(args);
        final Method method = getAccessibleMethod(cls, methodName, parameterTypes);
        if (method == null) {
            throw new NoSuchMethodException("No such static method: " + methodName
                    + " on " + cls.getName()
                    + " with exact parameter types " + Arrays.toString(parameterTypes));
        }
        return method.invoke(null, args);
    }

    private static Class<?>[] toParameterTypes(final Object... args) {
        if (args == null || args.length == 0) {
            return new Class<?>[0];
        }
        final Class<?>[] types = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i] == null ? null : args[i].getClass();
        }
        return types;
    }

    private static boolean isAssignable(final Class<?>[] from, final Class<?>[] to) {
        if (from.length != to.length) {
            return false;
        }
        for (int i = 0; i < from.length; i++) {
            if (!isAssignableSingle(from[i], to[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAssignableSingle(final Class<?> from, final Class<?> to) {
        if (from == null) {
            return !to.isPrimitive();
        }
        if (to.isAssignableFrom(from)) {
            return true;
        }
        if (to.isPrimitive()) {
            return isPrimitiveAssignable(from, to);
        }
        if (from.isPrimitive()) {
            return to.isAssignableFrom(wrapPrimitive(from));
        }
        return false;
    }

    private static boolean isPrimitiveAssignable(final Class<?> from, final Class<?> to) {
        final Class<?> unwrapped = unwrapPrimitive(from);
        if (unwrapped == null) {
            return false;
        }
        if (unwrapped == to) {
            return true;
        }
        for (final Class<?>[] widening : PRIMITIVE_WIDENINGS) {
            if (widening[0] == unwrapped) {
                for (int i = 1; i < widening.length; i++) {
                    if (widening[i] == to) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private static boolean isMoreSpecific(final Method a, final Method b) {
        return isAssignable(a.getParameterTypes(), b.getParameterTypes());
    }

    private static Class<?> wrapPrimitive(final Class<?> primitive) {
        if (primitive == int.class) return Integer.class;
        if (primitive == long.class) return Long.class;
        if (primitive == double.class) return Double.class;
        if (primitive == float.class) return Float.class;
        if (primitive == boolean.class) return Boolean.class;
        if (primitive == byte.class) return Byte.class;
        if (primitive == short.class) return Short.class;
        if (primitive == char.class) return Character.class;
        return primitive;
    }

    private static Class<?> unwrapPrimitive(final Class<?> wrapper) {
        if (wrapper == Integer.class || wrapper == int.class) return int.class;
        if (wrapper == Long.class || wrapper == long.class) return long.class;
        if (wrapper == Double.class || wrapper == double.class) return double.class;
        if (wrapper == Float.class || wrapper == float.class) return float.class;
        if (wrapper == Boolean.class || wrapper == boolean.class) return boolean.class;
        if (wrapper == Byte.class || wrapper == byte.class) return byte.class;
        if (wrapper == Short.class || wrapper == short.class) return short.class;
        if (wrapper == Character.class || wrapper == char.class) return char.class;
        return null;
    }
}

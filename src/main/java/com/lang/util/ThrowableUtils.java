package com.lang.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ThrowableUtils {

    private ThrowableUtils() {}

    public static Throwable getRootCause(final Throwable throwable) {
        final List<Throwable> chain = getThrowableList(throwable);
        return chain.isEmpty() ? null : chain.get(chain.size() - 1);
    }

    public static List<Throwable> getThrowableList(Throwable throwable) {
        final List<Throwable> list = new ArrayList<>();
        while (throwable != null) {
            list.add(throwable);
            throwable = throwable.getCause();
        }
        return list;
    }

    public static Throwable[] getThrowables(final Throwable throwable) {
        return getThrowableList(throwable).toArray(new Throwable[0]);
    }

    public static int getThrowableCount(final Throwable throwable) {
        return getThrowableList(throwable).size();
    }

    public static String getStackTrace(final Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        final StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

    public static String getMessage(final Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        final String className = throwable.getClass().getSimpleName();
        return className + ": " + throwable.getMessage();
    }

    public static String getRootCauseMessage(final Throwable throwable) {
        final Throwable root = getRootCause(throwable);
        return getMessage(root != null ? root : throwable);
    }

    public static int indexOfThrowable(final Throwable throwable,
                                       final Class<? extends Throwable> clazz) {
        return indexOfThrowable(throwable, clazz, 0);
    }

    public static int indexOfThrowable(final Throwable throwable,
                                       final Class<? extends Throwable> clazz,
                                       final int fromIndex) {
        Objects.requireNonNull(clazz, "clazz");
        if (throwable == null) {
            return -1;
        }
        final List<Throwable> chain = getThrowableList(throwable);
        for (int i = Math.max(fromIndex, 0); i < chain.size(); i++) {
            if (clazz.equals(chain.get(i).getClass())) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOfType(final Throwable throwable,
                                  final Class<? extends Throwable> type) {
        return indexOfType(throwable, type, 0);
    }

    public static int indexOfType(final Throwable throwable,
                                  final Class<? extends Throwable> type,
                                  final int fromIndex) {
        Objects.requireNonNull(type, "type");
        if (throwable == null) {
            return -1;
        }
        final List<Throwable> chain = getThrowableList(throwable);
        for (int i = Math.max(fromIndex, 0); i < chain.size(); i++) {
            if (type.isInstance(chain.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static boolean hasCause(Throwable chain,
                                   final Class<? extends Throwable> type) {
        if (type == null) {
            return false;
        }
        while (chain != null) {
            if (type.isInstance(chain)) {
                return true;
            }
            chain = chain.getCause();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <R> R rethrow(final Throwable throwable) {
        return ThrowableUtils.<R, RuntimeException>throwAs(throwable);
    }

    @SuppressWarnings("unchecked")
    private static <R, T extends Throwable> R throwAs(final Throwable throwable) throws T {
        throw (T) throwable;
    }

    public static boolean isChecked(final Throwable throwable) {
        return throwable != null
                && !(throwable instanceof RuntimeException)
                && !(throwable instanceof Error);
    }

    public static boolean isUnchecked(final Throwable throwable) {
        return throwable != null
                && (throwable instanceof RuntimeException || throwable instanceof Error);
    }
}

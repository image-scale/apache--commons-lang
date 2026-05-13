package com.lang.util;

public class BooleanHelper {

    private BooleanHelper() {
    }

    public static boolean isTrue(final Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean isFalse(final Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean isNotTrue(final Boolean bool) {
        return !isTrue(bool);
    }

    public static boolean isNotFalse(final Boolean bool) {
        return !isFalse(bool);
    }

    public static boolean toBoolean(final Boolean bool) {
        return bool != null && bool.booleanValue();
    }

    public static boolean toBooleanDefaultIfNull(final Boolean bool, final boolean defaultValue) {
        if (bool == null) {
            return defaultValue;
        }
        return bool.booleanValue();
    }

    public static boolean toBoolean(final String str) {
        return toBooleanObject(str) == Boolean.TRUE;
    }

    public static Boolean toBooleanObject(final String str) {
        if (str == null) {
            return null;
        }
        switch (str.length()) {
            case 1:
                final char ch0 = str.charAt(0);
                if (ch0 == 'y' || ch0 == 'Y' || ch0 == 't' || ch0 == 'T') {
                    return Boolean.TRUE;
                }
                if (ch0 == 'n' || ch0 == 'N' || ch0 == 'f' || ch0 == 'F') {
                    return Boolean.FALSE;
                }
                break;
            case 2:
                final char ch02 = str.charAt(0);
                final char ch12 = str.charAt(1);
                if ((ch02 == 'o' || ch02 == 'O') && (ch12 == 'n' || ch12 == 'N')) {
                    return Boolean.TRUE;
                }
                if ((ch02 == 'n' || ch02 == 'N') && (ch12 == 'o' || ch12 == 'O')) {
                    return Boolean.FALSE;
                }
                break;
            case 3:
                final char ch03 = str.charAt(0);
                final char ch13 = str.charAt(1);
                final char ch23 = str.charAt(2);
                if ((ch03 == 'y' || ch03 == 'Y') && (ch13 == 'e' || ch13 == 'E') && (ch23 == 's' || ch23 == 'S')) {
                    return Boolean.TRUE;
                }
                if ((ch03 == 'o' || ch03 == 'O') && (ch13 == 'f' || ch13 == 'F') && (ch23 == 'f' || ch23 == 'F')) {
                    return Boolean.FALSE;
                }
                break;
            case 4:
                final char ch04 = str.charAt(0);
                final char ch14 = str.charAt(1);
                final char ch24 = str.charAt(2);
                final char ch34 = str.charAt(3);
                if ((ch04 == 't' || ch04 == 'T') && (ch14 == 'r' || ch14 == 'R')
                        && (ch24 == 'u' || ch24 == 'U') && (ch34 == 'e' || ch34 == 'E')) {
                    return Boolean.TRUE;
                }
                break;
            case 5:
                final char ch05 = str.charAt(0);
                final char ch15 = str.charAt(1);
                final char ch25 = str.charAt(2);
                final char ch35 = str.charAt(3);
                final char ch45 = str.charAt(4);
                if ((ch05 == 'f' || ch05 == 'F') && (ch15 == 'a' || ch15 == 'A')
                        && (ch25 == 'l' || ch25 == 'L') && (ch35 == 's' || ch35 == 'S')
                        && (ch45 == 'e' || ch45 == 'E')) {
                    return Boolean.FALSE;
                }
                break;
            default:
                break;
        }
        return null;
    }

    public static boolean toBoolean(final int value) {
        return value != 0;
    }

    public static Boolean toBooleanObject(final int value) {
        return value == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static Boolean toBooleanObject(final Integer value) {
        if (value == null) {
            return null;
        }
        return value.intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static int toInteger(final boolean bool) {
        return bool ? 1 : 0;
    }

    public static Integer toIntegerObject(final boolean bool) {
        return bool ? Integer.valueOf(1) : Integer.valueOf(0);
    }

    public static Integer toIntegerObject(final Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool.booleanValue() ? Integer.valueOf(1) : Integer.valueOf(0);
    }

    public static String toStringTrueFalse(final Boolean bool) {
        return toString(bool, "true", "false", null);
    }

    public static String toStringYesNo(final Boolean bool) {
        return toString(bool, "yes", "no", null);
    }

    public static String toStringOnOff(final Boolean bool) {
        return toString(bool, "on", "off", null);
    }

    public static String toString(final Boolean bool, final String trueString,
                                  final String falseString, final String nullString) {
        if (bool == null) {
            return nullString;
        }
        return bool.booleanValue() ? trueString : falseString;
    }

    public static Boolean negate(final Boolean bool) {
        if (bool == null) {
            return null;
        }
        return bool.booleanValue() ? Boolean.FALSE : Boolean.TRUE;
    }

    public static boolean and(final boolean... array) {
        validateArray(array);
        for (final boolean element : array) {
            if (!element) {
                return false;
            }
        }
        return true;
    }

    public static Boolean and(final Boolean... array) {
        validateArray(array);
        for (final Boolean element : array) {
            if (!element.booleanValue()) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    public static boolean or(final boolean... array) {
        validateArray(array);
        for (final boolean element : array) {
            if (element) {
                return true;
            }
        }
        return false;
    }

    public static Boolean or(final Boolean... array) {
        validateArray(array);
        for (final Boolean element : array) {
            if (element.booleanValue()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static boolean xor(final boolean... array) {
        validateArray(array);
        int trueCount = 0;
        for (final boolean element : array) {
            if (element) {
                trueCount++;
            }
        }
        return trueCount % 2 != 0;
    }

    public static Boolean xor(final Boolean... array) {
        validateArray(array);
        return xor(toPrimitive(array)) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static boolean oneHot(final boolean... array) {
        validateArray(array);
        int trueCount = 0;
        for (final boolean element : array) {
            if (element) {
                trueCount++;
                if (trueCount > 1) {
                    return false;
                }
            }
        }
        return trueCount == 1;
    }

    public static int compare(final boolean x, final boolean y) {
        if (x == y) {
            return 0;
        }
        return x ? 1 : -1;
    }

    private static void validateArray(final Object array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (java.lang.reflect.Array.getLength(array) == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
    }

    private static boolean[] toPrimitive(final Boolean[] array) {
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].booleanValue();
        }
        return result;
    }
}

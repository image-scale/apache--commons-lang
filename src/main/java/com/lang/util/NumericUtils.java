package com.lang.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class NumericUtils {

    public static final Integer INTEGER_ZERO = Integer.valueOf(0);
    public static final Integer INTEGER_ONE = Integer.valueOf(1);
    public static final Integer INTEGER_MINUS_ONE = Integer.valueOf(-1);
    public static final Long LONG_ZERO = Long.valueOf(0L);
    public static final Long LONG_ONE = Long.valueOf(1L);
    public static final Long LONG_MINUS_ONE = Long.valueOf(-1L);
    public static final Short SHORT_ZERO = Short.valueOf((short) 0);
    public static final Short SHORT_MINUS_ONE = Short.valueOf((short) -1);
    public static final Byte BYTE_ZERO = Byte.valueOf((byte) 0);
    public static final Byte BYTE_MINUS_ONE = Byte.valueOf((byte) -1);
    public static final Double DOUBLE_ZERO = Double.valueOf(0.0d);
    public static final Double DOUBLE_ONE = Double.valueOf(1.0d);
    public static final Double DOUBLE_MINUS_ONE = Double.valueOf(-1.0d);
    public static final Float FLOAT_ZERO = Float.valueOf(0.0f);
    public static final Float FLOAT_ONE = Float.valueOf(1.0f);
    public static final Float FLOAT_MINUS_ONE = Float.valueOf(-1.0f);

    private NumericUtils() {
    }

    // --- Safe parsing ---

    public static int toInt(final String str) {
        return toInt(str, 0);
    }

    public static int toInt(final String str, final int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long toLong(final String str) {
        return toLong(str, 0L);
    }

    public static long toLong(final String str, final long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException e) {
            return defaultValue;
        }
    }

    public static float toFloat(final String str) {
        return toFloat(str, 0.0f);
    }

    public static float toFloat(final String str, final float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (final NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double toDouble(final String str) {
        return toDouble(str, 0.0d);
    }

    public static double toDouble(final String str, final double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (final NumberFormatException e) {
            return defaultValue;
        }
    }

    public static byte toByte(final String str) {
        return toByte(str, (byte) 0);
    }

    public static byte toByte(final String str, final byte defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Byte.parseByte(str);
        } catch (final NumberFormatException e) {
            return defaultValue;
        }
    }

    public static short toShort(final String str) {
        return toShort(str, (short) 0);
    }

    public static short toShort(final String str, final short defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Short.parseShort(str);
        } catch (final NumberFormatException e) {
            return defaultValue;
        }
    }

    public static BigDecimal toScaledBigDecimal(final BigDecimal value) {
        return toScaledBigDecimal(value, 2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(final BigDecimal value, final int scale, final RoundingMode roundingMode) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value.setScale(scale, roundingMode == null ? RoundingMode.HALF_EVEN : roundingMode);
    }

    public static BigDecimal toScaledBigDecimal(final Double value) {
        return toScaledBigDecimal(value, 2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(final Double value, final int scale, final RoundingMode roundingMode) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return toScaledBigDecimal(BigDecimal.valueOf(value), scale, roundingMode);
    }

    public static BigDecimal toScaledBigDecimal(final String value) {
        return toScaledBigDecimal(value, 2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(final String value, final int scale, final RoundingMode roundingMode) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return toScaledBigDecimal(createBigDecimal(value), scale, roundingMode);
    }

    // --- Create methods ---

    public static Number createNumber(final String str) {
        if (str == null) {
            return null;
        }
        if (str.isBlank()) {
            throw new NumberFormatException("A blank string is not a valid number");
        }

        final String[] hexPrefixes = {"0x", "0X", "-0x", "-0X", "#", "-#"};
        for (final String prefix : hexPrefixes) {
            if (str.startsWith(prefix)) {
                return createHexNumber(str, prefix);
            }
        }

        final char lastChar = str.charAt(str.length() - 1);

        if (lastChar == 'l' || lastChar == 'L') {
            final String numStr = str.substring(0, str.length() - 1);
            try {
                return Long.valueOf(numStr);
            } catch (final NumberFormatException e) {
                return new BigInteger(numStr);
            }
        }

        if (lastChar == 'f' || lastChar == 'F') {
            final String numStr = str.substring(0, str.length() - 1);
            try {
                final Float f = Float.valueOf(numStr);
                if (!f.isInfinite() && (f != 0.0f || isZeroNumericString(numStr))) {
                    return f;
                }
            } catch (final NumberFormatException ignored) {
            }
        }

        if (lastChar == 'd' || lastChar == 'D') {
            final String numStr = str.substring(0, str.length() - 1);
            try {
                final Double d = Double.valueOf(numStr);
                if (!d.isInfinite() && (d != 0.0d || isZeroNumericString(numStr))) {
                    return d;
                }
            } catch (final NumberFormatException ignored) {
            }
            throw new NumberFormatException(str + " is not a valid number.");
        }

        if (str.indexOf('.') != -1 || str.indexOf('e') != -1 || str.indexOf('E') != -1) {
            if (lastChar != 'f' && lastChar != 'F') {
                try {
                    final BigDecimal bd = new BigDecimal(str);
                    try {
                        final Double d = Double.valueOf(str);
                        if (!d.isInfinite() && bd.compareTo(BigDecimal.valueOf(d)) == 0) {
                            return d;
                        }
                    } catch (final NumberFormatException ignored) {
                    }
                    return bd;
                } catch (final NumberFormatException ignored) {
                }
            }
            try {
                final Float f = Float.valueOf(str);
                if (!f.isInfinite()) {
                    return f;
                }
            } catch (final NumberFormatException ignored) {
            }
            throw new NumberFormatException(str + " is not a valid number.");
        }

        if (str.charAt(0) == '0' && str.length() > 1) {
            boolean allOctal = true;
            for (int i = 1; i < str.length(); i++) {
                final char c = str.charAt(i);
                if (c < '0' || c > '7') {
                    allOctal = false;
                    break;
                }
            }
            if (allOctal) {
                try {
                    return Integer.valueOf(str, 8);
                } catch (final NumberFormatException ignored) {
                    try {
                        return Long.valueOf(str, 8);
                    } catch (final NumberFormatException ignored2) {
                        return new BigInteger(str.substring(1), 8);
                    }
                }
            }
        }

        try {
            return Integer.valueOf(str);
        } catch (final NumberFormatException ignored) {
        }
        try {
            return Long.valueOf(str);
        } catch (final NumberFormatException ignored) {
        }
        return new BigInteger(str);
    }

    private static Number createHexNumber(final String str, final String prefix) {
        final boolean negative = prefix.startsWith("-");
        final String hexStr = str.substring(prefix.length());
        if (hexStr.isEmpty()) {
            throw new NumberFormatException("Invalid hex number: " + str);
        }
        try {
            final int val = Integer.parseUnsignedInt(hexStr, 16);
            return negative ? Integer.valueOf(-val) : Integer.valueOf(val);
        } catch (final NumberFormatException ignored) {
        }
        try {
            final long val = Long.parseUnsignedLong(hexStr, 16);
            return negative ? Long.valueOf(-val) : Long.valueOf(val);
        } catch (final NumberFormatException ignored) {
        }
        final BigInteger bi = new BigInteger(hexStr, 16);
        return negative ? bi.negate() : bi;
    }

    private static boolean isZeroNumericString(final String s) {
        try {
            return new BigDecimal(s).compareTo(BigDecimal.ZERO) == 0;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public static Integer createInteger(final String str) {
        if (str == null) {
            return null;
        }
        return Integer.decode(str);
    }

    public static Long createLong(final String str) {
        if (str == null) {
            return null;
        }
        return Long.decode(str);
    }

    public static Float createFloat(final String str) {
        if (str == null) {
            return null;
        }
        return Float.valueOf(str);
    }

    public static Double createDouble(final String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }

    public static BigInteger createBigInteger(final String str) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        int pos = 0;
        int radix = 10;
        boolean negate = false;
        if (str.charAt(0) == '-') {
            negate = true;
            pos = 1;
        } else if (str.charAt(0) == '+') {
            pos = 1;
        }
        if (str.startsWith("0x", pos) || str.startsWith("0X", pos)) {
            radix = 16;
            pos += 2;
        } else if (str.startsWith("#", pos)) {
            radix = 16;
            pos += 1;
        } else if (str.startsWith("0", pos) && str.length() > pos + 1) {
            radix = 8;
            pos += 1;
        }
        final BigInteger value = new BigInteger(str.substring(pos), radix);
        return negate ? value.negate() : value;
    }

    public static BigDecimal createBigDecimal(final String str) {
        if (str == null) {
            return null;
        }
        if (str.isBlank()) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        return new BigDecimal(str);
    }

    // --- Validation ---

    public static boolean isCreatable(final String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        final char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;

        int start = (chars[0] == '-' || chars[0] == '+') ? 1 : 0;
        if (sz > start + 1 && chars[start] == '0') {
            if (chars[start + 1] == 'x' || chars[start + 1] == 'X') {
                int i = start + 2;
                if (i == sz) {
                    return false;
                }
                for (; i < sz; i++) {
                    if (!isHexDigit(chars[i])) {
                        return false;
                    }
                }
                return true;
            }
            if (chars[start + 1] == 'b' || chars[start + 1] == 'B') {
                int i = start + 2;
                if (i == sz) {
                    return false;
                }
                for (; i < sz; i++) {
                    if (chars[i] != '0' && chars[i] != '1') {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--;
        int i = start;
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;
            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                if (hasExp || !foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false;
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    return false;
                }
                return foundDigit;
            }
            if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l' || chars[i] == 'L') {
                return foundDigit && !hasExp && !hasDecPoint;
            }
            return false;
        }
        return !allowSigns && foundDigit;
    }

    public static boolean isParsable(final String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        if (str.charAt(str.length() - 1) == '.') {
            return false;
        }
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            return isAllDigitsOrDecimalPoint(str, 1);
        }
        return isAllDigitsOrDecimalPoint(str, 0);
    }

    private static boolean isAllDigitsOrDecimalPoint(final String str, final int startIndex) {
        boolean hasDecimal = false;
        for (int i = startIndex; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (c == '.') {
                if (hasDecimal) {
                    return false;
                }
                hasDecimal = true;
            } else if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDigits(final String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isHexDigit(final char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    // --- Min / Max ---

    public static int min(final int... array) {
        validateArray(array);
        int result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < result) {
                result = array[i];
            }
        }
        return result;
    }

    public static int max(final int... array) {
        validateArray(array);
        int result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > result) {
                result = array[i];
            }
        }
        return result;
    }

    public static long min(final long... array) {
        validateArray(array);
        long result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < result) {
                result = array[i];
            }
        }
        return result;
    }

    public static long max(final long... array) {
        validateArray(array);
        long result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > result) {
                result = array[i];
            }
        }
        return result;
    }

    public static float min(final float... array) {
        validateArray(array);
        float result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Float.isNaN(array[i])) {
                return Float.NaN;
            }
            if (array[i] < result) {
                result = array[i];
            }
        }
        return result;
    }

    public static float max(final float... array) {
        validateArray(array);
        float result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Float.isNaN(array[i])) {
                return Float.NaN;
            }
            if (array[i] > result) {
                result = array[i];
            }
        }
        return result;
    }

    public static double min(final double... array) {
        validateArray(array);
        double result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Double.isNaN(array[i])) {
                return Double.NaN;
            }
            if (array[i] < result) {
                result = array[i];
            }
        }
        return result;
    }

    public static double max(final double... array) {
        validateArray(array);
        double result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Double.isNaN(array[i])) {
                return Double.NaN;
            }
            if (array[i] > result) {
                result = array[i];
            }
        }
        return result;
    }

    public static short min(final short... array) {
        validateArray(array);
        short result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < result) {
                result = array[i];
            }
        }
        return result;
    }

    public static short max(final short... array) {
        validateArray(array);
        short result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > result) {
                result = array[i];
            }
        }
        return result;
    }

    public static byte min(final byte... array) {
        validateArray(array);
        byte result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < result) {
                result = array[i];
            }
        }
        return result;
    }

    public static byte max(final byte... array) {
        validateArray(array);
        byte result = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > result) {
                result = array[i];
            }
        }
        return result;
    }

    // --- Compare ---

    public static int compare(final int x, final int y) {
        return Integer.compare(x, y);
    }

    public static int compare(final long x, final long y) {
        return Long.compare(x, y);
    }

    public static int compare(final short x, final short y) {
        return Short.compare(x, y);
    }

    public static int compare(final byte x, final byte y) {
        return Byte.compare(x, y);
    }

    // --- Validation helpers ---

    private static void validateArray(final int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    private static void validateArray(final long[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    private static void validateArray(final float[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    private static void validateArray(final double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    private static void validateArray(final short[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    private static void validateArray(final byte[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
    }

    // --- Min/Max for 3 args ---

    public static int min(final int a, final int b, final int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int max(final int a, final int b, final int c) {
        return Math.max(Math.max(a, b), c);
    }

    public static long min(final long a, final long b, final long c) {
        return Math.min(Math.min(a, b), c);
    }

    public static long max(final long a, final long b, final long c) {
        return Math.max(Math.max(a, b), c);
    }

    public static double min(final double a, final double b, final double c) {
        return Math.min(Math.min(a, b), c);
    }

    public static double max(final double a, final double b, final double c) {
        return Math.max(Math.max(a, b), c);
    }

    public static float min(final float a, final float b, final float c) {
        return Math.min(Math.min(a, b), c);
    }

    public static float max(final float a, final float b, final float c) {
        return Math.max(Math.max(a, b), c);
    }
}

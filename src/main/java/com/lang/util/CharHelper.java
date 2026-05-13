package com.lang.util;

public class CharHelper {

    public static final char LF = '\n';
    public static final char CR = '\r';
    public static final char NUL = '\0';

    private static final String[] ASCII_CACHE = new String[128];

    static {
        for (int i = 0; i < ASCII_CACHE.length; i++) {
            ASCII_CACHE[i] = String.valueOf((char) i);
        }
    }

    private CharHelper() {
    }

    public static boolean isAscii(final char ch) {
        return ch < 128;
    }

    public static boolean isAsciiAlpha(final char ch) {
        return isAsciiAlphaUpper(ch) || isAsciiAlphaLower(ch);
    }

    public static boolean isAsciiAlphaUpper(final char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    public static boolean isAsciiAlphaLower(final char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    public static boolean isAsciiNumeric(final char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isAsciiAlphanumeric(final char ch) {
        return isAsciiAlpha(ch) || isAsciiNumeric(ch);
    }

    public static boolean isAsciiPrintable(final char ch) {
        return ch >= 32 && ch < 127;
    }

    public static boolean isAsciiControl(final char ch) {
        return ch < 32 || ch == 127;
    }

    public static boolean isHex(final char ch) {
        return isAsciiNumeric(ch) || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F');
    }

    public static boolean isOctal(final char ch) {
        return ch >= '0' && ch <= '7';
    }

    public static char toChar(final Character ch) {
        if (ch == null) {
            throw new IllegalArgumentException("The Character must not be null");
        }
        return ch.charValue();
    }

    public static char toChar(final Character ch, final char defaultValue) {
        if (ch == null) {
            return defaultValue;
        }
        return ch.charValue();
    }

    public static char toChar(final String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("The String must not be empty");
        }
        return str.charAt(0);
    }

    public static char toChar(final String str, final char defaultValue) {
        if (str == null || str.isEmpty()) {
            return defaultValue;
        }
        return str.charAt(0);
    }

    public static Character toCharacterObject(final String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return Character.valueOf(str.charAt(0));
    }

    public static int toIntValue(final char ch) {
        if (!isAsciiNumeric(ch)) {
            throw new IllegalArgumentException("The character " + ch + " is not in the range '0' - '9'");
        }
        return ch - '0';
    }

    public static int toIntValue(final char ch, final int defaultValue) {
        if (!isAsciiNumeric(ch)) {
            return defaultValue;
        }
        return ch - '0';
    }

    public static int toIntValue(final Character ch) {
        if (ch == null) {
            throw new IllegalArgumentException("The Character must not be null");
        }
        return toIntValue(ch.charValue());
    }

    public static int toIntValue(final Character ch, final int defaultValue) {
        if (ch == null) {
            return defaultValue;
        }
        return toIntValue(ch.charValue(), defaultValue);
    }

    public static String toString(final char ch) {
        if (ch < 128) {
            return ASCII_CACHE[ch];
        }
        return String.valueOf(ch);
    }

    public static String toString(final Character ch) {
        if (ch == null) {
            return null;
        }
        return toString(ch.charValue());
    }

    public static String unicodeEscaped(final char ch) {
        return "\\u" + String.format("%04x", (int) ch);
    }

    public static String unicodeEscaped(final Character ch) {
        if (ch == null) {
            return null;
        }
        return unicodeEscaped(ch.charValue());
    }

    public static int compare(final char x, final char y) {
        return Character.compare(x, y);
    }
}

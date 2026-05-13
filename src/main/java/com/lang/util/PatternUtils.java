package com.lang.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PatternUtils {

    private PatternUtils() {}

    public static String removeAll(final String text, final String regex) {
        return replaceAll(text, regex, "");
    }

    public static String removeAll(final String text, final Pattern pattern) {
        return replaceAll(text, pattern, "");
    }

    public static String removeFirst(final String text, final String regex) {
        return replaceFirst(text, regex, "");
    }

    public static String removeFirst(final String text, final Pattern pattern) {
        return replaceFirst(text, pattern, "");
    }

    public static String replaceAll(final String text, final String regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return text.replaceAll(regex, replacement);
    }

    public static String replaceAll(final String text, final Pattern pattern, final String replacement) {
        if (text == null || pattern == null || replacement == null) {
            return text;
        }
        return pattern.matcher(text).replaceAll(replacement);
    }

    public static String replaceFirst(final String text, final String regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return text.replaceFirst(regex, replacement);
    }

    public static String replaceFirst(final String text, final Pattern pattern, final String replacement) {
        if (text == null || pattern == null || replacement == null) {
            return text;
        }
        return pattern.matcher(text).replaceFirst(replacement);
    }

    public static String removePattern(final String text, final String regex) {
        return replacePattern(text, regex, "");
    }

    public static String replacePattern(final String text, final String regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return Pattern.compile(regex, Pattern.DOTALL).matcher(text).replaceAll(replacement);
    }
}

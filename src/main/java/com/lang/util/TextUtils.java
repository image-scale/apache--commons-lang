package com.lang.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class TextUtils {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final int NOT_FOUND = -1;

    private TextUtils() {
    }

    // --- Empty / Blank checks ---

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return true;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    // --- Trim / Strip ---

    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(final String str) {
        final String trimmed = trim(str);
        return isEmpty(trimmed) ? null : trimmed;
    }

    public static String trimToEmpty(final String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String strip(final String str) {
        return strip(str, null);
    }

    public static String strip(final String str, final String stripChars) {
        if (str == null) {
            return null;
        }
        final String result = stripStart(stripEnd(str, stripChars), stripChars);
        return result;
    }

    public static String stripToNull(final String str) {
        if (str == null) {
            return null;
        }
        final String stripped = strip(str, null);
        return stripped.isEmpty() ? null : stripped;
    }

    public static String stripToEmpty(final String str) {
        return str == null ? EMPTY : strip(str, null);
    }

    public static String stripStart(final String str, final String stripChars) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        int start = 0;
        if (stripChars == null) {
            while (start < len && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (start < len && stripChars.indexOf(str.charAt(start)) != NOT_FOUND) {
                start++;
            }
        }
        return str.substring(start);
    }

    public static String stripEnd(final String str, final String stripChars) {
        if (str == null) {
            return null;
        }
        int end = str.length();
        if (stripChars == null) {
            while (end > 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.isEmpty()) {
            return str;
        } else {
            while (end > 0 && stripChars.indexOf(str.charAt(end - 1)) != NOT_FOUND) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    // --- Equals ---

    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        return regionMatches(cs1, false, 0, cs2, 0, cs1.length());
    }

    public static boolean equalsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return regionMatches(cs1, true, 0, cs2, 0, cs1.length());
    }

    // --- Contains ---

    public static boolean contains(final CharSequence seq, final CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return false;
        }
        return indexOf(seq, searchSeq, 0) >= 0;
    }

    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        final int searchLen = searchStr.length();
        final int max = str.length() - searchLen;
        for (int i = 0; i <= max; i++) {
            if (regionMatches(str, true, i, searchStr, 0, searchLen)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsWhitespace(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (Character.isWhitespace(cs.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // --- IndexOf ---

    public static int indexOf(final CharSequence seq, final CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return NOT_FOUND;
        }
        return indexOf(seq, searchSeq, 0);
    }

    public static int indexOf(final CharSequence seq, final CharSequence searchSeq, final int startPos) {
        if (seq == null || searchSeq == null) {
            return NOT_FOUND;
        }
        if (seq instanceof String) {
            return ((String) seq).indexOf(searchSeq.toString(), startPos);
        }
        final int searchLen = searchSeq.length();
        final int max = seq.length() - searchLen;
        for (int i = Math.max(startPos, 0); i <= max; i++) {
            if (regionMatches(seq, false, i, searchSeq, 0, searchLen)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return indexOfIgnoreCase(str, searchStr, 0);
    }

    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return NOT_FOUND;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        final int searchLen = searchStr.length();
        final int max = str.length() - searchLen;
        for (int i = startPos; i <= max; i++) {
            if (regionMatches(str, true, i, searchStr, 0, searchLen)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public static int lastIndexOf(final CharSequence seq, final CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return NOT_FOUND;
        }
        return lastIndexOf(seq, searchSeq, seq.length());
    }

    public static int lastIndexOf(final CharSequence seq, final CharSequence searchSeq, final int startPos) {
        if (seq == null || searchSeq == null) {
            return NOT_FOUND;
        }
        if (seq instanceof String) {
            return ((String) seq).lastIndexOf(searchSeq.toString(), startPos);
        }
        final int searchLen = searchSeq.length();
        for (int i = Math.min(startPos, seq.length() - searchLen); i >= 0; i--) {
            if (regionMatches(seq, false, i, searchSeq, 0, searchLen)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    // --- Substring ---

    public static String substring(final String str, int start) {
        if (str == null) {
            return null;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY;
        }
        return str.substring(start);
    }

    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return null;
        }
        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return EMPTY;
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        return str.substring(start, end);
    }

    public static String substringBefore(final String str, final String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.isEmpty()) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    public static String substringAfter(final String str, final String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        final int pos = str.indexOf(separator);
        if (pos == NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    public static String substringBetween(final String str, final String open, final String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        final int start = str.indexOf(open);
        if (start != NOT_FOUND) {
            final int end = str.indexOf(close, start + open.length());
            if (end != NOT_FOUND) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    public static String substringBetween(final String str, final String tag) {
        return substringBetween(str, tag, tag);
    }

    // --- Split / Join ---

    public static String[] split(final String str) {
        return split(str, null, -1);
    }

    public static String[] split(final String str, final String separatorChars) {
        return split(str, separatorChars, -1);
    }

    public static String[] split(final String str, final String separatorChars, final int max) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return new String[0];
        }
        final List<String> list = new ArrayList<>();
        int count = 0;
        int i = 0;
        int start = 0;
        boolean match = false;
        if (separatorChars == null) {
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match) {
                        if (max > 0 && count + 1 >= max) {
                            i = len;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                        count++;
                    }
                    start = ++i;
                } else {
                    match = true;
                    i++;
                }
            }
        } else if (separatorChars.length() == 1) {
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match) {
                        if (max > 0 && count + 1 >= max) {
                            i = len;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                        count++;
                    }
                    start = ++i;
                } else {
                    match = true;
                    i++;
                }
            }
        } else {
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match) {
                        if (max > 0 && count + 1 >= max) {
                            i = len;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                        count++;
                    }
                    start = ++i;
                } else {
                    match = true;
                    i++;
                }
            }
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[0]);
    }

    public static String join(final Object[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(final Object[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(final Object[] array, final String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(final Object[] array, String separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }
        final StringBuilder buf = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(final Iterable<?> iterable, final char separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    public static String join(final Iterable<?> iterable, final String separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    public static String join(final Iterator<?> iterator, final char separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder();
        final Object first = iterator.next();
        if (first != null) {
            buf.append(first);
        }
        while (iterator.hasNext()) {
            buf.append(separator);
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static String join(final Iterator<?> iterator, final String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder();
        final Object first = iterator.next();
        if (first != null) {
            buf.append(first);
        }
        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    // --- Replace / Remove ---

    public static String replace(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(final String text, final String searchString, final String replacement, final int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == NOT_FOUND) {
            return text;
        }
        final int replLen = searchString.length();
        final StringBuilder buf = new StringBuilder(text.length());
        int count = 0;
        while (end != NOT_FOUND) {
            buf.append(text, start, end).append(replacement);
            start = end + replLen;
            count++;
            if (max > 0 && count >= max) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text, start, text.length());
        return buf.toString();
    }

    public static String replaceIgnoreCase(final String text, final String searchString, final String replacement) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null) {
            return text;
        }
        final String lowerText = text.toLowerCase();
        final String lowerSearch = searchString.toLowerCase();
        int start = 0;
        int end = lowerText.indexOf(lowerSearch, start);
        if (end == NOT_FOUND) {
            return text;
        }
        final int replLen = searchString.length();
        final StringBuilder buf = new StringBuilder(text.length());
        while (end != NOT_FOUND) {
            buf.append(text, start, end).append(replacement);
            start = end + replLen;
            end = lowerText.indexOf(lowerSearch, start);
        }
        buf.append(text, start, text.length());
        return buf.toString();
    }

    public static String remove(final String str, final String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        return replace(str, remove, EMPTY);
    }

    public static String remove(final String str, final char remove) {
        if (isEmpty(str) || str.indexOf(remove) == NOT_FOUND) {
            return str;
        }
        final StringBuilder buf = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != remove) {
                buf.append(str.charAt(i));
            }
        }
        return buf.toString();
    }

    public static String removeIgnoreCase(final String str, final String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        return replaceIgnoreCase(str, remove, EMPTY);
    }

    // --- Capitalize / Case ---

    public static String capitalize(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final int firstCodePoint = str.codePointAt(0);
        final int newCodePoint = Character.toTitleCase(firstCodePoint);
        if (firstCodePoint == newCodePoint) {
            return str;
        }
        final int[] newCodePoints = str.codePoints().toArray();
        newCodePoints[0] = newCodePoint;
        return new String(newCodePoints, 0, newCodePoints.length);
    }

    public static String uncapitalize(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final int firstCodePoint = str.codePointAt(0);
        final int newCodePoint = Character.toLowerCase(firstCodePoint);
        if (firstCodePoint == newCodePoint) {
            return str;
        }
        final int[] newCodePoints = str.codePoints().toArray();
        newCodePoints[0] = newCodePoint;
        return new String(newCodePoints, 0, newCodePoints.length);
    }

    public static String upperCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public static String lowerCase(final String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    // --- Padding ---

    public static String leftPad(final String str, final int size) {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        return repeat(padChar, pads).concat(str);
    }

    public static String leftPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads == padLen) {
            return padStr.concat(str);
        }
        final StringBuilder buf = new StringBuilder(size);
        for (int i = 0; i < pads; i++) {
            buf.append(padStr.charAt(i % padLen));
        }
        buf.append(str);
        return buf.toString();
    }

    public static String rightPad(final String str, final int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        return str.concat(repeat(padChar, pads));
    }

    public static String rightPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads == padLen) {
            return str.concat(padStr);
        }
        final StringBuilder buf = new StringBuilder(size);
        buf.append(str);
        for (int i = 0; i < pads; i++) {
            buf.append(padStr.charAt(i % padLen));
        }
        return buf.toString();
    }

    public static String center(final String str, final int size) {
        return center(str, size, ' ');
    }

    public static String center(String str, final int size, final char padChar) {
        if (str == null || size <= 0) {
            return str;
        }
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str;
        }
        str = leftPad(str, strLen + pads / 2, padChar);
        str = rightPad(str, size, padChar);
        return str;
    }

    // --- Reverse ---

    public static String reverse(final String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    // --- Abbreviate ---

    public static String abbreviate(final String str, final int maxWidth) {
        return abbreviate(str, "...", 0, maxWidth);
    }

    public static String abbreviate(final String str, final int offset, final int maxWidth) {
        return abbreviate(str, "...", offset, maxWidth);
    }

    public static String abbreviate(final String str, final String abbrevMarker, final int maxWidth) {
        return abbreviate(str, abbrevMarker, 0, maxWidth);
    }

    public static String abbreviate(final String str, String abbrevMarker, int offset, final int maxWidth) {
        if (isEmpty(str) || isEmpty(abbrevMarker)) {
            return str;
        }
        final int abbrevMarkerLen = abbrevMarker.length();
        final int minAbbrevWidth = abbrevMarkerLen + 1;
        if (maxWidth < minAbbrevWidth) {
            throw new IllegalArgumentException(String.format("Minimum abbreviation width is %d", minAbbrevWidth));
        }
        final int strLen = str.length();
        if (strLen <= maxWidth) {
            return str;
        }
        if (offset > strLen) {
            offset = strLen;
        }
        if (strLen - offset < maxWidth - abbrevMarkerLen) {
            offset = strLen - (maxWidth - abbrevMarkerLen);
        }
        if (offset <= abbrevMarkerLen + 1) {
            return str.substring(0, maxWidth - abbrevMarkerLen) + abbrevMarker;
        }
        if (maxWidth < minAbbrevWidth + abbrevMarkerLen) {
            throw new IllegalArgumentException(String.format("Minimum abbreviation width with offset is %d", minAbbrevWidth + abbrevMarkerLen));
        }
        if (offset + maxWidth - abbrevMarkerLen < strLen) {
            return abbrevMarker + abbreviate(str.substring(offset), abbrevMarker, maxWidth - abbrevMarkerLen);
        }
        return abbrevMarker + str.substring(strLen - (maxWidth - abbrevMarkerLen));
    }

    // --- Type checks ---

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlpha(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isLetter(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isLetterOrDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphaSpace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            final char c = cs.charAt(i);
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumericSpace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            final char c = cs.charAt(i);
            if (!Character.isDigit(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    // --- Count ---

    public static int countMatches(final CharSequence str, final CharSequence sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = indexOf(str, sub, idx)) != NOT_FOUND) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    public static int countMatches(final CharSequence str, final char ch) {
        if (isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (ch == str.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    // --- Default ---

    public static String defaultString(final String str) {
        return defaultString(str, EMPTY);
    }

    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }

    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    // --- Wrap / Unwrap ---

    public static String wrap(final String str, final char wrapWith) {
        if (isEmpty(str) || wrapWith == '\0') {
            return str;
        }
        return wrapWith + str + wrapWith;
    }

    public static String wrap(final String str, final String wrapWith) {
        if (isEmpty(str) || isEmpty(wrapWith)) {
            return str;
        }
        return wrapWith + str + wrapWith;
    }

    public static String wrapIfMissing(final String str, final char wrapWith) {
        if (isEmpty(str) || wrapWith == '\0') {
            return str;
        }
        final boolean wrapStart = str.charAt(0) != wrapWith;
        final boolean wrapEnd = str.charAt(str.length() - 1) != wrapWith;
        if (!wrapStart && !wrapEnd) {
            return str;
        }
        final StringBuilder builder = new StringBuilder(str.length() + 2);
        if (wrapStart) {
            builder.append(wrapWith);
        }
        builder.append(str);
        if (wrapEnd) {
            builder.append(wrapWith);
        }
        return builder.toString();
    }

    public static String unwrap(final String str, final String wrapToken) {
        if (isEmpty(str) || isEmpty(wrapToken) || str.length() < 2 * wrapToken.length()) {
            return str;
        }
        if (str.startsWith(wrapToken) && str.endsWith(wrapToken)) {
            return str.substring(wrapToken.length(), str.length() - wrapToken.length());
        }
        return str;
    }

    public static String unwrap(final String str, final char wrapChar) {
        if (isEmpty(str) || str.length() < 2 || str.charAt(0) != wrapChar || str.charAt(str.length() - 1) != wrapChar) {
            return str;
        }
        return str.substring(1, str.length() - 1);
    }

    // --- Normalize / Delete whitespace ---

    public static String normalizeSpace(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final String stripped = strip(str);
        final StringBuilder buf = new StringBuilder(stripped.length());
        boolean lastWasWhitespace = false;
        for (int i = 0; i < stripped.length(); i++) {
            final char c = stripped.charAt(i);
            if (Character.isWhitespace(c)) {
                if (!lastWasWhitespace) {
                    buf.append(' ');
                    lastWasWhitespace = true;
                }
            } else {
                buf.append(c);
                lastWasWhitespace = false;
            }
        }
        return buf.toString();
    }

    public static String deleteWhitespace(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final StringBuilder buf = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                buf.append(str.charAt(i));
            }
        }
        return buf.toString();
    }

    // --- Truncate ---

    public static String truncate(final String str, final int maxWidth) {
        return truncate(str, 0, maxWidth);
    }

    public static String truncate(final String str, final int offset, final int maxWidth) {
        if (str == null) {
            return null;
        }
        if (offset < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        }
        if (maxWidth < 0) {
            throw new IllegalArgumentException("maxWith cannot be negative");
        }
        if (offset > str.length()) {
            return EMPTY;
        }
        if (str.length() > maxWidth + offset) {
            return str.substring(offset, offset + maxWidth);
        }
        return str.substring(offset);
    }

    // --- Repeat ---

    public static String repeat(final String str, final int repeat) {
        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return EMPTY;
        }
        final StringBuilder buf = new StringBuilder(str.length() * repeat);
        for (int i = 0; i < repeat; i++) {
            buf.append(str);
        }
        return buf.toString();
    }

    public static String repeat(final char ch, final int repeat) {
        if (repeat <= 0) {
            return EMPTY;
        }
        final char[] buf = new char[repeat];
        java.util.Arrays.fill(buf, ch);
        return new String(buf);
    }

    public static String repeat(final String str, final String separator, final int repeat) {
        if (str == null || separator == null) {
            return repeat(str, repeat);
        }
        final String result = repeat(str + separator, repeat);
        return removeEnd(result, separator);
    }

    // --- StartsWith / EndsWith ---

    public static boolean startsWith(final CharSequence str, final CharSequence prefix) {
        return startsWith(str, prefix, false);
    }

    public static boolean startsWithIgnoreCase(final CharSequence str, final CharSequence prefix) {
        return startsWith(str, prefix, true);
    }

    private static boolean startsWith(final CharSequence str, final CharSequence prefix, final boolean ignoreCase) {
        if (str == null || prefix == null) {
            return str == prefix;
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        return regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length());
    }

    public static boolean endsWith(final CharSequence str, final CharSequence suffix) {
        return endsWith(str, suffix, false);
    }

    public static boolean endsWithIgnoreCase(final CharSequence str, final CharSequence suffix) {
        return endsWith(str, suffix, true);
    }

    private static boolean endsWith(final CharSequence str, final CharSequence suffix, final boolean ignoreCase) {
        if (str == null || suffix == null) {
            return str == suffix;
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        final int strOffset = str.length() - suffix.length();
        return regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length());
    }

    // --- Chomp / Chop ---

    public static String chomp(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() == 1) {
            final char ch = str.charAt(0);
            if (ch == '\r' || ch == '\n') {
                return EMPTY;
            }
            return str;
        }
        int lastIdx = str.length() - 1;
        final char last = str.charAt(lastIdx);
        if (last == '\n') {
            if (str.charAt(lastIdx - 1) == '\r') {
                lastIdx--;
            }
        } else if (last != '\r') {
            lastIdx++;
        }
        return str.substring(0, lastIdx);
    }

    public static String chop(final String str) {
        if (str == null) {
            return null;
        }
        final int strLen = str.length();
        if (strLen < 2) {
            return EMPTY;
        }
        final int lastIdx = strLen - 1;
        final String ret = str.substring(0, lastIdx);
        final char last = str.charAt(lastIdx);
        if (last == '\n' && ret.charAt(lastIdx - 1) == '\r') {
            return ret.substring(0, lastIdx - 1);
        }
        return ret;
    }

    // --- RemoveStart / RemoveEnd ---

    public static String removeStart(final String str, final String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.startsWith(remove)) {
            return str.substring(remove.length());
        }
        return str;
    }

    public static String removeEnd(final String str, final String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    // --- Overlay ---

    public static String overlay(final String str, String overlay, int start, int end) {
        if (str == null) {
            return null;
        }
        if (overlay == null) {
            overlay = EMPTY;
        }
        final int len = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > len) {
            start = len;
        }
        if (end < 0) {
            end = 0;
        }
        if (end > len) {
            end = len;
        }
        if (start > end) {
            final int temp = start;
            start = end;
            end = temp;
        }
        return str.substring(0, start) + overlay + str.substring(end);
    }

    // --- Left / Right / Mid ---

    public static String left(final String str, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static String right(final String str, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    public static String mid(final String str, int pos, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0 || pos > str.length()) {
            return EMPTY;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (str.length() <= pos + len) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    // --- Helpers ---

    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase,
                                 final int thisStart, final CharSequence substring,
                                 final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;
        final int srcLen = cs.length() - thisStart;
        final int otherLen = substring.length() - start;
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }
        if (srcLen < length || otherLen < length) {
            return false;
        }
        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);
            if (c1 == c2) {
                continue;
            }
            if (!ignoreCase) {
                return false;
            }
            final char u1 = Character.toUpperCase(c1);
            final char u2 = Character.toUpperCase(c2);
            if (u1 != u2 && Character.toLowerCase(u1) != Character.toLowerCase(u2)) {
                return false;
            }
        }
        return true;
    }

    // --- startsWithAny / endsWithAny / containsAny ---

    public static boolean startsWithAny(final CharSequence string, final CharSequence... searchStrings) {
        if (isEmpty(string) || searchStrings == null) {
            return false;
        }
        for (final CharSequence searchString : searchStrings) {
            if (startsWith(string, searchString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean endsWithAny(final CharSequence string, final CharSequence... searchStrings) {
        if (isEmpty(string) || searchStrings == null) {
            return false;
        }
        for (final CharSequence searchString : searchStrings) {
            if (endsWith(string, searchString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAny(final CharSequence cs, final char... searchChars) {
        if (isEmpty(cs) || searchChars == null || searchChars.length == 0) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            final char ch = cs.charAt(i);
            for (final char searchChar : searchChars) {
                if (searchChar == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsNone(final CharSequence cs, final char... searchChars) {
        if (cs == null || searchChars == null) {
            return true;
        }
        for (int i = 0; i < cs.length(); i++) {
            final char ch = cs.charAt(i);
            for (final char searchChar : searchChars) {
                if (searchChar == ch) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean containsOnly(final CharSequence cs, final char... valid) {
        if (cs == null || valid == null) {
            return false;
        }
        if (cs.length() == 0) {
            return true;
        }
        if (valid.length == 0) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            boolean found = false;
            for (final char v : valid) {
                if (cs.charAt(i) == v) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    // --- rotate ---

    public static String rotate(final String str, final int shift) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0 || shift % len == 0) {
            return str;
        }
        final int offset = -(shift % len);
        final int idx = (offset < 0) ? len + offset : offset;
        return str.substring(idx) + str.substring(0, idx);
    }

    // --- swapCase ---

    public static String swapCase(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final int[] newCodePoints = str.codePoints().map(cp -> {
            if (Character.isUpperCase(cp)) {
                return Character.toLowerCase(cp);
            } else if (Character.isLowerCase(cp)) {
                return Character.toUpperCase(cp);
            }
            return cp;
        }).toArray();
        return new String(newCodePoints, 0, newCodePoints.length);
    }

    // --- isAllUpperCase / isAllLowerCase ---

    public static boolean isAllUpperCase(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isUpperCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllLowerCase(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isLowerCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // --- getDigits ---

    public static String getDigits(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            final char c = str.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // --- difference ---

    public static String difference(final String str1, final String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        final int at = indexOfDifference(str1, str2);
        if (at == NOT_FOUND) {
            return EMPTY;
        }
        return str2.substring(at);
    }

    public static int indexOfDifference(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return NOT_FOUND;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < cs1.length() && i < cs2.length(); i++) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                break;
            }
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return NOT_FOUND;
    }

    public static String getCommonPrefix(final String... strs) {
        if (strs == null || strs.length == 0) {
            return EMPTY;
        }
        final int smallestLen = minLength(strs);
        if (smallestLen == 0) {
            return EMPTY;
        }
        int commonLen = 0;
        for (int i = 0; i < smallestLen; i++) {
            final char c = strs[0].charAt(i);
            boolean allMatch = true;
            for (int j = 1; j < strs.length; j++) {
                if (strs[j].charAt(i) != c) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) {
                commonLen++;
            } else {
                break;
            }
        }
        return strs[0].substring(0, commonLen);
    }

    private static int minLength(final CharSequence... css) {
        int min = Integer.MAX_VALUE;
        for (final CharSequence cs : css) {
            if (cs == null) {
                return 0;
            }
            min = Math.min(min, cs.length());
        }
        return min;
    }

    // --- length ---

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    // --- prependIfMissing / appendIfMissing ---

    public static String prependIfMissing(final String str, final CharSequence prefix, final CharSequence... prefixes) {
        if (str == null || isEmpty(prefix) || startsWith(str, prefix)) {
            return str;
        }
        if (prefixes != null) {
            for (final CharSequence p : prefixes) {
                if (startsWith(str, p)) {
                    return str;
                }
            }
        }
        return prefix.toString() + str;
    }

    public static String appendIfMissing(final String str, final CharSequence suffix, final CharSequence... suffixes) {
        if (str == null || isEmpty(suffix) || endsWith(str, suffix)) {
            return str;
        }
        if (suffixes != null) {
            for (final CharSequence s : suffixes) {
                if (endsWith(str, s)) {
                    return str;
                }
            }
        }
        return str + suffix.toString();
    }
}

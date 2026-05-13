package com.lang.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextUtilsTest {

    // --- Empty / Blank ---

    @Test
    void testIsEmpty() {
        assertTrue(TextUtils.isEmpty(null));
        assertTrue(TextUtils.isEmpty(""));
        assertFalse(TextUtils.isEmpty(" "));
        assertFalse(TextUtils.isEmpty("abc"));
        assertFalse(TextUtils.isEmpty("  abc  "));
    }

    @Test
    void testIsNotEmpty() {
        assertFalse(TextUtils.isNotEmpty(null));
        assertFalse(TextUtils.isNotEmpty(""));
        assertTrue(TextUtils.isNotEmpty(" "));
        assertTrue(TextUtils.isNotEmpty("abc"));
    }

    @Test
    void testIsBlank() {
        assertTrue(TextUtils.isBlank(null));
        assertTrue(TextUtils.isBlank(""));
        assertTrue(TextUtils.isBlank(" "));
        assertTrue(TextUtils.isBlank("  \t\n  "));
        assertFalse(TextUtils.isBlank("abc"));
        assertFalse(TextUtils.isBlank(" abc "));
    }

    @Test
    void testIsNotBlank() {
        assertFalse(TextUtils.isNotBlank(null));
        assertFalse(TextUtils.isNotBlank(""));
        assertFalse(TextUtils.isNotBlank("  "));
        assertTrue(TextUtils.isNotBlank("abc"));
    }

    // --- Trim ---

    @Test
    void testTrim() {
        assertNull(TextUtils.trim(null));
        assertEquals("", TextUtils.trim(""));
        assertEquals("abc", TextUtils.trim("  abc  "));
        assertEquals("abc", TextUtils.trim("abc"));
    }

    @Test
    void testTrimToNull() {
        assertNull(TextUtils.trimToNull(null));
        assertNull(TextUtils.trimToNull(""));
        assertNull(TextUtils.trimToNull("  "));
        assertEquals("abc", TextUtils.trimToNull("  abc  "));
    }

    @Test
    void testTrimToEmpty() {
        assertEquals("", TextUtils.trimToEmpty(null));
        assertEquals("", TextUtils.trimToEmpty(""));
        assertEquals("", TextUtils.trimToEmpty("  "));
        assertEquals("abc", TextUtils.trimToEmpty("  abc  "));
    }

    // --- Strip ---

    @Test
    void testStrip() {
        assertNull(TextUtils.strip(null));
        assertEquals("", TextUtils.strip(""));
        assertEquals("abc", TextUtils.strip("  abc  "));
        assertEquals("bc", TextUtils.strip("abc", "a"));
        assertEquals("bc", TextUtils.strip("abca", "a"));
    }

    @Test
    void testStripToNull() {
        assertNull(TextUtils.stripToNull(null));
        assertNull(TextUtils.stripToNull(""));
        assertNull(TextUtils.stripToNull("  "));
        assertEquals("abc", TextUtils.stripToNull("  abc  "));
    }

    @Test
    void testStripToEmpty() {
        assertEquals("", TextUtils.stripToEmpty(null));
        assertEquals("", TextUtils.stripToEmpty("  "));
        assertEquals("abc", TextUtils.stripToEmpty("  abc  "));
    }

    @Test
    void testStripStart() {
        assertNull(TextUtils.stripStart(null, null));
        assertEquals("abc  ", TextUtils.stripStart("  abc  ", null));
        assertEquals("bc", TextUtils.stripStart("abc", "a"));
        assertEquals("abc", TextUtils.stripStart("abc", ""));
    }

    @Test
    void testStripEnd() {
        assertNull(TextUtils.stripEnd(null, null));
        assertEquals("  abc", TextUtils.stripEnd("  abc  ", null));
        assertEquals("ab", TextUtils.stripEnd("abc", "c"));
        assertEquals("abc", TextUtils.stripEnd("abc", ""));
    }

    // --- Equals ---

    @Test
    void testEquals() {
        assertTrue(TextUtils.equals(null, null));
        assertFalse(TextUtils.equals(null, "abc"));
        assertFalse(TextUtils.equals("abc", null));
        assertTrue(TextUtils.equals("abc", "abc"));
        assertFalse(TextUtils.equals("abc", "ABC"));
        assertFalse(TextUtils.equals("abc", "def"));
    }

    @Test
    void testEqualsIgnoreCase() {
        assertTrue(TextUtils.equalsIgnoreCase(null, null));
        assertFalse(TextUtils.equalsIgnoreCase(null, "abc"));
        assertFalse(TextUtils.equalsIgnoreCase("abc", null));
        assertTrue(TextUtils.equalsIgnoreCase("abc", "ABC"));
        assertTrue(TextUtils.equalsIgnoreCase("abc", "abc"));
        assertFalse(TextUtils.equalsIgnoreCase("abc", "def"));
    }

    // --- Contains ---

    @Test
    void testContains() {
        assertFalse(TextUtils.contains(null, "a"));
        assertFalse(TextUtils.contains("abc", null));
        assertTrue(TextUtils.contains("abc", "bc"));
        assertTrue(TextUtils.contains("abc", ""));
        assertFalse(TextUtils.contains("abc", "xyz"));
    }

    @Test
    void testContainsIgnoreCase() {
        assertFalse(TextUtils.containsIgnoreCase(null, "a"));
        assertFalse(TextUtils.containsIgnoreCase("abc", null));
        assertTrue(TextUtils.containsIgnoreCase("ABC", "abc"));
        assertTrue(TextUtils.containsIgnoreCase("abc", "BC"));
        assertFalse(TextUtils.containsIgnoreCase("abc", "xyz"));
    }

    @Test
    void testContainsWhitespace() {
        assertFalse(TextUtils.containsWhitespace(null));
        assertFalse(TextUtils.containsWhitespace(""));
        assertFalse(TextUtils.containsWhitespace("abc"));
        assertTrue(TextUtils.containsWhitespace("a b"));
        assertTrue(TextUtils.containsWhitespace(" abc"));
    }

    // --- IndexOf ---

    @Test
    void testIndexOf() {
        assertEquals(TextUtils.NOT_FOUND, TextUtils.indexOf(null, "a"));
        assertEquals(TextUtils.NOT_FOUND, TextUtils.indexOf("abc", null));
        assertEquals(1, TextUtils.indexOf("abcabc", "bc"));
        assertEquals(0, TextUtils.indexOf("abc", ""));
    }

    @Test
    void testIndexOfIgnoreCase() {
        assertEquals(TextUtils.NOT_FOUND, TextUtils.indexOfIgnoreCase(null, "a"));
        assertEquals(1, TextUtils.indexOfIgnoreCase("abcABC", "BC"));
        assertEquals(0, TextUtils.indexOfIgnoreCase("ABC", "abc"));
    }

    @Test
    void testLastIndexOf() {
        assertEquals(TextUtils.NOT_FOUND, TextUtils.lastIndexOf(null, "a"));
        assertEquals(3, TextUtils.lastIndexOf("abcabc", "a"));
        assertEquals(TextUtils.NOT_FOUND, TextUtils.lastIndexOf("abc", "xyz"));
    }

    // --- Substring ---

    @Test
    void testSubstring() {
        assertNull(TextUtils.substring(null, 0));
        assertEquals("bc", TextUtils.substring("abc", 1));
        assertEquals("", TextUtils.substring("abc", 10));
        assertEquals("bc", TextUtils.substring("abc", -2));
    }

    @Test
    void testSubstringWithEnd() {
        assertNull(TextUtils.substring(null, 0, 1));
        assertEquals("ab", TextUtils.substring("abc", 0, 2));
        assertEquals("bc", TextUtils.substring("abc", 1, 3));
        assertEquals("", TextUtils.substring("abc", 3, 1));
    }

    @Test
    void testSubstringBefore() {
        assertEquals("abc", TextUtils.substringBefore("abc-def", "-"));
        assertEquals("abc-def", TextUtils.substringBefore("abc-def", "x"));
        assertEquals("", TextUtils.substringBefore("abc", ""));
        assertNull(TextUtils.substringBefore(null, "-"));
    }

    @Test
    void testSubstringAfter() {
        assertEquals("def", TextUtils.substringAfter("abc-def", "-"));
        assertEquals("", TextUtils.substringAfter("abc-def", "x"));
        assertNull(TextUtils.substringAfter(null, "-"));
    }

    @Test
    void testSubstringBetween() {
        assertEquals("b", TextUtils.substringBetween("a[b]c", "[", "]"));
        assertNull(TextUtils.substringBetween(null, "[", "]"));
        assertNull(TextUtils.substringBetween("abc", "[", "]"));
        assertEquals("bb", TextUtils.substringBetween("abba", "a", "a"));
    }

    // --- Split ---

    @Test
    void testSplit() {
        assertNull(TextUtils.split(null));
        assertArrayEquals(new String[0], TextUtils.split(""));
        assertArrayEquals(new String[]{"a", "b", "c"}, TextUtils.split("a b c"));
        assertArrayEquals(new String[]{"a", "b", "c"}, TextUtils.split("  a  b  c  "));
    }

    @Test
    void testSplitWithSeparator() {
        assertArrayEquals(new String[]{"a", "b", "c"}, TextUtils.split("a.b.c", "."));
        assertArrayEquals(new String[]{"a", "b.c"}, TextUtils.split("a.b.c", ".", 2));
    }

    // --- Join ---

    @Test
    void testJoinArray() {
        assertNull(TextUtils.join((Object[]) null, ','));
        assertEquals("a,b,c", TextUtils.join(new String[]{"a", "b", "c"}, ','));
        assertEquals("", TextUtils.join(new String[]{}, ','));
        assertEquals("a,,c", TextUtils.join(new String[]{"a", null, "c"}, ','));
    }

    @Test
    void testJoinWithStringSeparator() {
        assertEquals("a--b--c", TextUtils.join(new String[]{"a", "b", "c"}, "--"));
        assertNull(TextUtils.join((Object[]) null, "--"));
    }

    @Test
    void testJoinIterable() {
        assertEquals("a,b,c", TextUtils.join(Arrays.asList("a", "b", "c"), ','));
        assertNull(TextUtils.join((Iterable<?>) null, ','));
    }

    // --- Replace / Remove ---

    @Test
    void testReplace() {
        assertNull(TextUtils.replace(null, "a", "b"));
        assertEquals("adc", TextUtils.replace("abc", "b", "d"));
        assertEquals("abc", TextUtils.replace("abc", "x", "y"));
        assertEquals("abc", TextUtils.replace("abc", "", "x"));
    }

    @Test
    void testReplaceWithMax() {
        assertEquals("adc_abc", TextUtils.replace("abc_abc", "b", "d", 1));
    }

    @Test
    void testRemove() {
        assertEquals("ac", TextUtils.remove("abc", "b"));
        assertEquals("abc", TextUtils.remove("abc", "x"));
        assertNull(TextUtils.remove(null, "a"));
    }

    @Test
    void testRemoveChar() {
        assertEquals("ac", TextUtils.remove("abc", 'b'));
        assertEquals("abc", TextUtils.remove("abc", 'x'));
    }

    // --- Capitalize ---

    @Test
    void testCapitalize() {
        assertNull(TextUtils.capitalize(null));
        assertEquals("", TextUtils.capitalize(""));
        assertEquals("Abc", TextUtils.capitalize("abc"));
        assertEquals("ABC", TextUtils.capitalize("ABC"));
    }

    @Test
    void testUncapitalize() {
        assertNull(TextUtils.uncapitalize(null));
        assertEquals("", TextUtils.uncapitalize(""));
        assertEquals("abc", TextUtils.uncapitalize("Abc"));
        assertEquals("aBC", TextUtils.uncapitalize("ABC"));
    }

    // --- Case ---

    @Test
    void testUpperCase() {
        assertNull(TextUtils.upperCase(null));
        assertEquals("ABC", TextUtils.upperCase("abc"));
        assertEquals("ABC", TextUtils.upperCase("ABC"));
    }

    @Test
    void testLowerCase() {
        assertNull(TextUtils.lowerCase(null));
        assertEquals("abc", TextUtils.lowerCase("ABC"));
        assertEquals("abc", TextUtils.lowerCase("abc"));
    }

    // --- Padding ---

    @Test
    void testLeftPad() {
        assertNull(TextUtils.leftPad(null, 5));
        assertEquals("  abc", TextUtils.leftPad("abc", 5));
        assertEquals("abc", TextUtils.leftPad("abc", 3));
        assertEquals("abc", TextUtils.leftPad("abc", 2));
        assertEquals("xxabc", TextUtils.leftPad("abc", 5, 'x'));
    }

    @Test
    void testRightPad() {
        assertNull(TextUtils.rightPad(null, 5));
        assertEquals("abc  ", TextUtils.rightPad("abc", 5));
        assertEquals("abc", TextUtils.rightPad("abc", 3));
        assertEquals("abcxx", TextUtils.rightPad("abc", 5, 'x'));
    }

    @Test
    void testCenter() {
        assertNull(TextUtils.center(null, 5));
        assertEquals("  abc  ", TextUtils.center("abc", 7));
        assertEquals("abc", TextUtils.center("abc", 3));
        assertEquals("abc", TextUtils.center("abc", 1));
    }

    // --- Reverse ---

    @Test
    void testReverse() {
        assertNull(TextUtils.reverse(null));
        assertEquals("cba", TextUtils.reverse("abc"));
        assertEquals("", TextUtils.reverse(""));
    }

    // --- Abbreviate ---

    @Test
    void testAbbreviate() {
        assertNull(TextUtils.abbreviate(null, 5));
        assertEquals("abcdefghij", TextUtils.abbreviate("abcdefghij", 10));
        assertEquals("ab...", TextUtils.abbreviate("abcdefghij", 5));
        assertEquals("abcdefghij", TextUtils.abbreviate("abcdefghij", 20));
    }

    @Test
    void testAbbreviateTooSmall() {
        assertThrows(IllegalArgumentException.class, () -> TextUtils.abbreviate("abc", 1));
    }

    // --- Type checks ---

    @Test
    void testIsNumeric() {
        assertFalse(TextUtils.isNumeric(null));
        assertFalse(TextUtils.isNumeric(""));
        assertTrue(TextUtils.isNumeric("123"));
        assertFalse(TextUtils.isNumeric("12 3"));
        assertFalse(TextUtils.isNumeric("12.3"));
        assertFalse(TextUtils.isNumeric("abc"));
    }

    @Test
    void testIsAlpha() {
        assertFalse(TextUtils.isAlpha(null));
        assertFalse(TextUtils.isAlpha(""));
        assertTrue(TextUtils.isAlpha("abc"));
        assertFalse(TextUtils.isAlpha("ab2c"));
        assertFalse(TextUtils.isAlpha("ab c"));
    }

    @Test
    void testIsAlphanumeric() {
        assertFalse(TextUtils.isAlphanumeric(null));
        assertFalse(TextUtils.isAlphanumeric(""));
        assertTrue(TextUtils.isAlphanumeric("abc123"));
        assertTrue(TextUtils.isAlphanumeric("abc"));
        assertTrue(TextUtils.isAlphanumeric("123"));
        assertFalse(TextUtils.isAlphanumeric("ab c"));
    }

    @Test
    void testIsWhitespace() {
        assertFalse(TextUtils.isWhitespace(null));
        assertTrue(TextUtils.isWhitespace(""));
        assertTrue(TextUtils.isWhitespace("   "));
        assertFalse(TextUtils.isWhitespace("abc"));
    }

    // --- CountMatches ---

    @Test
    void testCountMatches() {
        assertEquals(0, TextUtils.countMatches(null, "a"));
        assertEquals(0, TextUtils.countMatches("", "a"));
        assertEquals(2, TextUtils.countMatches("abcabc", "abc"));
        assertEquals(3, TextUtils.countMatches("aaa", "a"));
    }

    @Test
    void testCountMatchesChar() {
        assertEquals(0, TextUtils.countMatches(null, 'a'));
        assertEquals(3, TextUtils.countMatches("abcaac", 'a'));
    }

    // --- Default ---

    @Test
    void testDefaultString() {
        assertEquals("", TextUtils.defaultString(null));
        assertEquals("abc", TextUtils.defaultString("abc"));
        assertEquals("default", TextUtils.defaultString(null, "default"));
    }

    @Test
    void testDefaultIfBlank() {
        assertEquals("default", TextUtils.defaultIfBlank(null, "default"));
        assertEquals("default", TextUtils.defaultIfBlank("", "default"));
        assertEquals("default", TextUtils.defaultIfBlank("  ", "default"));
        assertEquals("abc", TextUtils.defaultIfBlank("abc", "default"));
    }

    @Test
    void testDefaultIfEmpty() {
        assertEquals("default", TextUtils.defaultIfEmpty(null, "default"));
        assertEquals("default", TextUtils.defaultIfEmpty("", "default"));
        assertEquals(" ", TextUtils.defaultIfEmpty(" ", "default"));
        assertEquals("abc", TextUtils.defaultIfEmpty("abc", "default"));
    }

    // --- Wrap / Unwrap ---

    @Test
    void testWrapChar() {
        assertEquals("'abc'", TextUtils.wrap("abc", '\''));
        assertNull(TextUtils.wrap(null, '\''));
        assertEquals("", TextUtils.wrap("", '\''));
    }

    @Test
    void testWrapString() {
        assertEquals("\"abc\"", TextUtils.wrap("abc", "\""));
        assertNull(TextUtils.wrap(null, "\""));
    }

    @Test
    void testUnwrapChar() {
        assertEquals("abc", TextUtils.unwrap("'abc'", '\''));
        assertEquals("abc", TextUtils.unwrap("abc", '\''));
        assertNull(TextUtils.unwrap(null, '\''));
    }

    @Test
    void testUnwrapString() {
        assertEquals("abc", TextUtils.unwrap("\"abc\"", "\""));
        assertEquals("abc", TextUtils.unwrap("abc", "\""));
    }

    // --- NormalizeSpace / DeleteWhitespace ---

    @Test
    void testNormalizeSpace() {
        assertNull(TextUtils.normalizeSpace(null));
        assertEquals("", TextUtils.normalizeSpace(""));
        assertEquals("a b", TextUtils.normalizeSpace("  a  b  "));
        assertEquals("a b c", TextUtils.normalizeSpace(" a  b  c "));
    }

    @Test
    void testDeleteWhitespace() {
        assertNull(TextUtils.deleteWhitespace(null));
        assertEquals("abc", TextUtils.deleteWhitespace("a b c"));
        assertEquals("abc", TextUtils.deleteWhitespace("  a  b  c  "));
    }

    // --- Truncate ---

    @Test
    void testTruncate() {
        assertNull(TextUtils.truncate(null, 5));
        assertEquals("abcd", TextUtils.truncate("abcdefg", 4));
        assertEquals("abcdefg", TextUtils.truncate("abcdefg", 10));
        assertEquals("", TextUtils.truncate("", 5));
    }

    @Test
    void testTruncateInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> TextUtils.truncate("abc", -1));
    }

    // --- Repeat ---

    @Test
    void testRepeatString() {
        assertNull(TextUtils.repeat(null, 3));
        assertEquals("ababab", TextUtils.repeat("ab", 3));
        assertEquals("", TextUtils.repeat("ab", 0));
        assertEquals("aaa", TextUtils.repeat("a", 3));
    }

    @Test
    void testRepeatChar() {
        assertEquals("xxx", TextUtils.repeat('x', 3));
        assertEquals("", TextUtils.repeat('x', 0));
    }

    @Test
    void testRepeatWithSeparator() {
        assertEquals("ab-ab-ab", TextUtils.repeat("ab", "-", 3));
    }

    // --- StartsWith / EndsWith ---

    @Test
    void testStartsWith() {
        assertTrue(TextUtils.startsWith("abc", "ab"));
        assertFalse(TextUtils.startsWith("abc", "bc"));
        assertTrue(TextUtils.startsWith(null, null));
        assertFalse(TextUtils.startsWith("abc", null));
        assertFalse(TextUtils.startsWith(null, "abc"));
    }

    @Test
    void testEndsWith() {
        assertTrue(TextUtils.endsWith("abc", "bc"));
        assertFalse(TextUtils.endsWith("abc", "ab"));
        assertTrue(TextUtils.endsWith(null, null));
        assertFalse(TextUtils.endsWith("abc", null));
    }

    @Test
    void testStartsWithIgnoreCase() {
        assertTrue(TextUtils.startsWithIgnoreCase("ABC", "ab"));
        assertFalse(TextUtils.startsWithIgnoreCase("abc", "BC"));
    }

    @Test
    void testEndsWithIgnoreCase() {
        assertTrue(TextUtils.endsWithIgnoreCase("ABC", "bc"));
        assertFalse(TextUtils.endsWithIgnoreCase("abc", "AB"));
    }

    // --- Chomp / Chop ---

    @Test
    void testChomp() {
        assertEquals("abc", TextUtils.chomp("abc\n"));
        assertEquals("abc", TextUtils.chomp("abc\r"));
        assertEquals("abc", TextUtils.chomp("abc\r\n"));
        assertEquals("abc", TextUtils.chomp("abc"));
        assertEquals("", TextUtils.chomp(""));
        assertNull(TextUtils.chomp(null));
    }

    @Test
    void testChop() {
        assertEquals("ab", TextUtils.chop("abc"));
        assertEquals("abc", TextUtils.chop("abc\r\n"));
        assertEquals("abc", TextUtils.chop("abc\n"));
        assertEquals("", TextUtils.chop("a"));
        assertNull(TextUtils.chop(null));
    }

    // --- RemoveStart / RemoveEnd ---

    @Test
    void testRemoveStart() {
        assertEquals("cde", TextUtils.removeStart("abcde", "ab"));
        assertEquals("abcde", TextUtils.removeStart("abcde", "xyz"));
        assertNull(TextUtils.removeStart(null, "ab"));
    }

    @Test
    void testRemoveEnd() {
        assertEquals("abc", TextUtils.removeEnd("abcde", "de"));
        assertEquals("abcde", TextUtils.removeEnd("abcde", "xyz"));
        assertNull(TextUtils.removeEnd(null, "de"));
    }

    // --- Overlay ---

    @Test
    void testOverlay() {
        assertNull(TextUtils.overlay(null, "x", 0, 0));
        assertEquals("xde", TextUtils.overlay("abcde", "x", 0, 3));
        assertEquals("abXe", TextUtils.overlay("abcde", "X", 2, 4));
    }

    // --- Left / Right / Mid ---

    @Test
    void testLeft() {
        assertNull(TextUtils.left(null, 3));
        assertEquals("ab", TextUtils.left("abc", 2));
        assertEquals("abc", TextUtils.left("abc", 5));
        assertEquals("", TextUtils.left("abc", -1));
    }

    @Test
    void testRight() {
        assertNull(TextUtils.right(null, 3));
        assertEquals("bc", TextUtils.right("abc", 2));
        assertEquals("abc", TextUtils.right("abc", 5));
        assertEquals("", TextUtils.right("abc", -1));
    }

    @Test
    void testMid() {
        assertNull(TextUtils.mid(null, 0, 3));
        assertEquals("bc", TextUtils.mid("abcde", 1, 2));
        assertEquals("cde", TextUtils.mid("abcde", 2, 10));
        assertEquals("", TextUtils.mid("abc", 5, 2));
    }

    // --- ContainsAny / ContainsNone / ContainsOnly ---

    @Test
    void testContainsAny() {
        assertFalse(TextUtils.containsAny(null, 'a'));
        assertTrue(TextUtils.containsAny("abc", 'a', 'x'));
        assertFalse(TextUtils.containsAny("abc", 'x', 'y'));
    }

    @Test
    void testContainsNone() {
        assertTrue(TextUtils.containsNone(null, 'a'));
        assertTrue(TextUtils.containsNone("abc", 'x', 'y'));
        assertFalse(TextUtils.containsNone("abc", 'a', 'x'));
    }

    @Test
    void testContainsOnly() {
        assertFalse(TextUtils.containsOnly(null, 'a'));
        assertTrue(TextUtils.containsOnly("aab", 'a', 'b'));
        assertFalse(TextUtils.containsOnly("abc", 'a', 'b'));
    }

    // --- StartsWithAny / EndsWithAny ---

    @Test
    void testStartsWithAny() {
        assertFalse(TextUtils.startsWithAny(null, "ab"));
        assertTrue(TextUtils.startsWithAny("abc", "ab", "xy"));
        assertFalse(TextUtils.startsWithAny("abc", "xy", "yz"));
    }

    @Test
    void testEndsWithAny() {
        assertFalse(TextUtils.endsWithAny(null, "bc"));
        assertTrue(TextUtils.endsWithAny("abc", "bc", "xy"));
        assertFalse(TextUtils.endsWithAny("abc", "xy", "yz"));
    }

    // --- Rotate / SwapCase ---

    @Test
    void testRotate() {
        assertNull(TextUtils.rotate(null, 2));
        assertEquals("deabc", TextUtils.rotate("abcde", 2));
        assertEquals("cdeab", TextUtils.rotate("abcde", -2));
        assertEquals("abcde", TextUtils.rotate("abcde", 0));
    }

    @Test
    void testSwapCase() {
        assertEquals("aBc", TextUtils.swapCase("AbC"));
        assertEquals("ABC", TextUtils.swapCase("abc"));
        assertEquals("abc", TextUtils.swapCase("ABC"));
        assertNull(TextUtils.swapCase(null));
    }

    // --- isAllUpperCase / isAllLowerCase ---

    @Test
    void testIsAllUpperCase() {
        assertFalse(TextUtils.isAllUpperCase(null));
        assertFalse(TextUtils.isAllUpperCase(""));
        assertTrue(TextUtils.isAllUpperCase("ABC"));
        assertFalse(TextUtils.isAllUpperCase("aBc"));
    }

    @Test
    void testIsAllLowerCase() {
        assertFalse(TextUtils.isAllLowerCase(null));
        assertFalse(TextUtils.isAllLowerCase(""));
        assertTrue(TextUtils.isAllLowerCase("abc"));
        assertFalse(TextUtils.isAllLowerCase("aBc"));
    }

    // --- GetDigits ---

    @Test
    void testGetDigits() {
        assertNull(TextUtils.getDigits(null));
        assertEquals("", TextUtils.getDigits(""));
        assertEquals("123", TextUtils.getDigits("abc123def"));
        assertEquals("", TextUtils.getDigits("abc"));
    }

    // --- Difference ---

    @Test
    void testDifference() {
        assertEquals("", TextUtils.difference("abc", "abc"));
        assertEquals("xyz", TextUtils.difference("abc", "abcxyz"));
        assertEquals("xyz", TextUtils.difference("", "xyz"));
    }

    @Test
    void testIndexOfDifference() {
        assertEquals(TextUtils.NOT_FOUND, TextUtils.indexOfDifference("abc", "abc"));
        assertEquals(3, TextUtils.indexOfDifference("abc", "abcxyz"));
        assertEquals(0, TextUtils.indexOfDifference("abc", "xyz"));
    }

    // --- GetCommonPrefix ---

    @Test
    void testGetCommonPrefix() {
        assertEquals("", TextUtils.getCommonPrefix());
        assertEquals("abc", TextUtils.getCommonPrefix("abcde", "abcfg"));
        assertEquals("", TextUtils.getCommonPrefix("abc", "xyz"));
        assertEquals("a", TextUtils.getCommonPrefix("abc", "ayz"));
    }

    // --- Length ---

    @Test
    void testLength() {
        assertEquals(0, TextUtils.length(null));
        assertEquals(0, TextUtils.length(""));
        assertEquals(3, TextUtils.length("abc"));
    }

    // --- PrependIfMissing / AppendIfMissing ---

    @Test
    void testPrependIfMissing() {
        assertNull(TextUtils.prependIfMissing(null, "pre"));
        assertEquals("preAbc", TextUtils.prependIfMissing("Abc", "pre"));
        assertEquals("preAbc", TextUtils.prependIfMissing("preAbc", "pre"));
    }

    @Test
    void testAppendIfMissing() {
        assertNull(TextUtils.appendIfMissing(null, ".txt"));
        assertEquals("file.txt", TextUtils.appendIfMissing("file", ".txt"));
        assertEquals("file.txt", TextUtils.appendIfMissing("file.txt", ".txt"));
    }

    // --- WrapIfMissing ---

    @Test
    void testWrapIfMissing() {
        assertEquals("'abc'", TextUtils.wrapIfMissing("abc", '\''));
        assertEquals("'abc'", TextUtils.wrapIfMissing("'abc'", '\''));
        assertEquals("'abc'", TextUtils.wrapIfMissing("'abc", '\''));
    }

    // --- ReplaceIgnoreCase ---

    @Test
    void testReplaceIgnoreCase() {
        assertEquals("adc", TextUtils.replaceIgnoreCase("aBc", "B", "d"));
        assertEquals("adc", TextUtils.replaceIgnoreCase("aBc", "b", "d"));
        assertNull(TextUtils.replaceIgnoreCase(null, "a", "b"));
    }

    // --- RemoveIgnoreCase ---

    @Test
    void testRemoveIgnoreCase() {
        assertEquals("ac", TextUtils.removeIgnoreCase("aBc", "b"));
        assertEquals("abc", TextUtils.removeIgnoreCase("abc", "x"));
    }

    // --- Constants ---

    @Test
    void testConstants() {
        assertEquals("", TextUtils.EMPTY);
        assertEquals(" ", TextUtils.SPACE);
        assertEquals(-1, TextUtils.NOT_FOUND);
    }
}

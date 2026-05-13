package com.lang.util;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class PatternUtilsTest {

    @Test
    void testRemoveAllString() {
        assertEquals("hll wrld", PatternUtils.removeAll("hello world", "[aeiou]"));
    }

    @Test
    void testRemoveAllPattern() {
        assertEquals("hll", PatternUtils.removeAll("hello", Pattern.compile("[aeiou]")));
    }

    @Test
    void testRemoveAllNulls() {
        assertNull(PatternUtils.removeAll(null, "a"));
        assertEquals("hello", PatternUtils.removeAll("hello", (String) null));
    }

    @Test
    void testRemoveFirstString() {
        assertEquals("hllo", PatternUtils.removeFirst("hello", "e"));
    }

    @Test
    void testRemoveFirstPattern() {
        assertEquals("hllo", PatternUtils.removeFirst("hello", Pattern.compile("e")));
    }

    @Test
    void testRemoveFirstNulls() {
        assertNull(PatternUtils.removeFirst(null, "a"));
        assertEquals("hello", PatternUtils.removeFirst("hello", (String) null));
    }

    @Test
    void testReplaceAllString() {
        assertEquals("h-ll- w-rld",
                PatternUtils.replaceAll("hello world", "[aeiou]", "-"));
    }

    @Test
    void testReplaceAllPattern() {
        assertEquals("h-ll-",
                PatternUtils.replaceAll("hello", Pattern.compile("[aeiou]"), "-"));
    }

    @Test
    void testReplaceAllNulls() {
        assertNull(PatternUtils.replaceAll(null, "a", "b"));
        assertEquals("hello", PatternUtils.replaceAll("hello", (String) null, "b"));
        assertEquals("hello", PatternUtils.replaceAll("hello", "a", null));
    }

    @Test
    void testReplaceFirstString() {
        assertEquals("hXllo", PatternUtils.replaceFirst("hello", "e", "X"));
    }

    @Test
    void testReplaceFirstPattern() {
        assertEquals("hXllo",
                PatternUtils.replaceFirst("hello", Pattern.compile("e"), "X"));
    }

    @Test
    void testReplaceFirstNulls() {
        assertNull(PatternUtils.replaceFirst(null, "a", "b"));
        assertEquals("hello", PatternUtils.replaceFirst("hello", (String) null, "b"));
        assertEquals("hello", PatternUtils.replaceFirst("hello", "a", null));
    }

    @Test
    void testRemovePattern() {
        assertEquals("ABCabc123", PatternUtils.removePattern("ABCabc123abc", "abc$"));
    }

    @Test
    void testRemovePatternMultiline() {
        assertEquals("", PatternUtils.removePattern("line1\nline2", ".*"));
    }

    @Test
    void testReplacePattern() {
        assertEquals("ABC---123",
                PatternUtils.replacePattern("ABCabc123", "[a-z]+", "---"));
    }

    @Test
    void testReplacePatternNulls() {
        assertNull(PatternUtils.replacePattern(null, "a", "b"));
        assertEquals("hello", PatternUtils.replacePattern("hello", null, "b"));
        assertEquals("hello", PatternUtils.replacePattern("hello", "a", null));
    }

    @Test
    void testRemoveAllNoMatch() {
        assertEquals("hello", PatternUtils.removeAll("hello", "[0-9]"));
    }

    @Test
    void testReplaceAllComplex() {
        assertEquals("12-34-56",
                PatternUtils.replaceAll("12/34/56", "/", "-"));
    }
}

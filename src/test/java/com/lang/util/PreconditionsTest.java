package com.lang.util;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PreconditionsTest {

    @Test
    void testNotNull() {
        assertEquals("abc", Preconditions.notNull("abc"));
        assertThrows(NullPointerException.class, () -> Preconditions.notNull(null));
    }

    @Test
    void testNotNullCustomMessage() {
        final NullPointerException e = assertThrows(NullPointerException.class,
                () -> Preconditions.notNull(null, "custom %s", "msg"));
        assertEquals("custom msg", e.getMessage());
    }

    @Test
    void testNotEmptyCharSequence() {
        assertEquals("abc", Preconditions.notEmpty("abc"));
        assertThrows(NullPointerException.class, () -> Preconditions.notEmpty((CharSequence) null));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notEmpty(""));
    }

    @Test
    void testNotEmptyCollection() {
        final List<String> list = Arrays.asList("a");
        assertSame(list, Preconditions.notEmpty(list));
        assertThrows(NullPointerException.class, () -> Preconditions.notEmpty((Collection<?>) null));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notEmpty(Collections.emptyList()));
    }

    @Test
    void testNotEmptyMap() {
        final Map<String, String> map = Map.of("a", "b");
        assertSame(map, Preconditions.notEmpty(map));
        assertThrows(NullPointerException.class, () -> Preconditions.notEmpty((Map<?, ?>) null));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notEmpty(Collections.emptyMap()));
    }

    @Test
    void testNotEmptyArray() {
        final String[] arr = {"a"};
        assertSame(arr, Preconditions.notEmpty(arr));
        assertThrows(NullPointerException.class, () -> Preconditions.notEmpty((String[]) null));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notEmpty(new String[0]));
    }

    @Test
    void testNotBlank() {
        assertEquals("abc", Preconditions.notBlank("abc"));
        assertThrows(NullPointerException.class, () -> Preconditions.notBlank(null));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notBlank(""));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notBlank("  "));
    }

    @Test
    void testIsTrue() {
        Preconditions.isTrue(true);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.isTrue(false));
    }

    @Test
    void testIsTrueCustomMessage() {
        final IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Preconditions.isTrue(false, "failed: %d", 42L));
        assertEquals("failed: 42", e.getMessage());
    }

    @Test
    void testIsTrueDoubleMessage() {
        final IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Preconditions.isTrue(false, "failed: %f", 1.5));
        assertTrue(e.getMessage().startsWith("failed:"));
    }

    @Test
    void testValidState() {
        Preconditions.validState(true);
        assertThrows(IllegalStateException.class, () -> Preconditions.validState(false));
    }

    @Test
    void testValidStateCustomMessage() {
        final IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> Preconditions.validState(false, "bad state: %s", "test"));
        assertEquals("bad state: test", e.getMessage());
    }

    @Test
    void testValidIndexCharSequence() {
        assertEquals("abc", Preconditions.validIndex("abc", 0));
        assertEquals("abc", Preconditions.validIndex("abc", 2));
        assertThrows(IndexOutOfBoundsException.class, () -> Preconditions.validIndex("abc", 3));
        assertThrows(IndexOutOfBoundsException.class, () -> Preconditions.validIndex("abc", -1));
    }

    @Test
    void testValidIndexCollection() {
        final List<String> list = Arrays.asList("a", "b", "c");
        assertSame(list, Preconditions.validIndex(list, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> Preconditions.validIndex(list, 3));
    }

    @Test
    void testValidIndexArray() {
        final String[] arr = {"a", "b", "c"};
        assertSame(arr, Preconditions.validIndex(arr, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> Preconditions.validIndex(arr, 3));
    }

    @Test
    void testMatchesPattern() {
        assertEquals("abc", Preconditions.matchesPattern("abc", "[a-z]+"));
        assertThrows(IllegalArgumentException.class,
                () -> Preconditions.matchesPattern("123", "[a-z]+"));
    }

    @Test
    void testIsInstanceOf() {
        assertEquals("abc", Preconditions.isInstanceOf(String.class, "abc"));
        assertThrows(IllegalArgumentException.class,
                () -> Preconditions.isInstanceOf(Integer.class, "abc"));
    }

    @Test
    void testIsAssignableFrom() {
        Preconditions.isAssignableFrom(Number.class, Integer.class);
        assertThrows(IllegalArgumentException.class,
                () -> Preconditions.isAssignableFrom(String.class, Integer.class));
    }

    @Test
    void testNoNullElementsArray() {
        final String[] arr = {"a", "b"};
        assertSame(arr, Preconditions.noNullElements(arr));
        assertThrows(IllegalArgumentException.class,
                () -> Preconditions.noNullElements(new String[]{"a", null, "b"}));
    }

    @Test
    void testNoNullElementsIterable() {
        final List<String> list = Arrays.asList("a", "b");
        assertSame(list, Preconditions.noNullElements(list));
        assertThrows(IllegalArgumentException.class,
                () -> Preconditions.noNullElements(Arrays.asList("a", null)));
    }

    @Test
    void testInclusiveBetween() {
        Preconditions.inclusiveBetween(1, 10, 5);
        Preconditions.inclusiveBetween(1, 10, 1);
        Preconditions.inclusiveBetween(1, 10, 10);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.inclusiveBetween(1, 10, 0));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.inclusiveBetween(1, 10, 11));
    }

    @Test
    void testInclusiveBetweenLong() {
        Preconditions.inclusiveBetween(1L, 10L, 5L);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.inclusiveBetween(1L, 10L, 0L));
    }

    @Test
    void testInclusiveBetweenDouble() {
        Preconditions.inclusiveBetween(1.0, 10.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.inclusiveBetween(1.0, 10.0, 0.5));
    }

    @Test
    void testExclusiveBetween() {
        Preconditions.exclusiveBetween(1, 10, 5);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.exclusiveBetween(1, 10, 1));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.exclusiveBetween(1, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.exclusiveBetween(1, 10, 0));
    }

    @Test
    void testExclusiveBetweenLong() {
        Preconditions.exclusiveBetween(1L, 10L, 5L);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.exclusiveBetween(1L, 10L, 1L));
    }

    @Test
    void testExclusiveBetweenDouble() {
        Preconditions.exclusiveBetween(1.0, 10.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.exclusiveBetween(1.0, 10.0, 1.0));
    }

    @Test
    void testFinite() {
        Preconditions.finite(1.0);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.finite(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.finite(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.finite(Double.NEGATIVE_INFINITY));
    }

    @Test
    void testNotNaN() {
        Preconditions.notNaN(1.0);
        Preconditions.notNaN(Double.POSITIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Preconditions.notNaN(Double.NaN));
    }
}

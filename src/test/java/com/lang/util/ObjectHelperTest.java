package com.lang.util;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ObjectHelperTest {

    @Test
    void testDefaultIfNull() {
        assertEquals("default", ObjectHelper.defaultIfNull(null, "default"));
        assertEquals("value", ObjectHelper.defaultIfNull("value", "default"));
    }

    @Test
    void testGetIfNull() {
        assertEquals("default", ObjectHelper.getIfNull(null, () -> "default"));
        assertEquals("value", ObjectHelper.getIfNull("value", () -> "default"));
        assertNull(ObjectHelper.getIfNull(null, null));
    }

    @Test
    void testFirstNonNull() {
        assertEquals("a", ObjectHelper.firstNonNull(null, "a", "b"));
        assertEquals("b", ObjectHelper.firstNonNull(null, null, "b"));
        assertNull(ObjectHelper.firstNonNull(null, null, null));
        assertNull(ObjectHelper.firstNonNull((Object[]) null));
    }

    @Test
    void testGetFirstNonNull() {
        assertEquals("a", ObjectHelper.getFirstNonNull(() -> null, () -> "a", () -> "b"));
        assertNull(ObjectHelper.getFirstNonNull(() -> null, () -> null));
    }

    @Test
    void testAllNotNull() {
        assertTrue(ObjectHelper.allNotNull("a", "b"));
        assertFalse(ObjectHelper.allNotNull("a", null));
        assertFalse(ObjectHelper.allNotNull((Object[]) null));
    }

    @Test
    void testAllNull() {
        assertTrue(ObjectHelper.allNull(null, null));
        assertFalse(ObjectHelper.allNull(null, "a"));
        assertTrue(ObjectHelper.allNull((Object[]) null));
    }

    @Test
    void testAnyNotNull() {
        assertTrue(ObjectHelper.anyNotNull(null, "a"));
        assertFalse(ObjectHelper.anyNotNull(null, null));
    }

    @Test
    void testAnyNull() {
        assertTrue(ObjectHelper.anyNull(null, "a"));
        assertFalse(ObjectHelper.anyNull("a", "b"));
        assertTrue(ObjectHelper.anyNull((Object[]) null));
    }

    @Test
    void testCompare() {
        assertTrue(ObjectHelper.compare("a", "b") < 0);
        assertTrue(ObjectHelper.compare("b", "a") > 0);
        assertEquals(0, ObjectHelper.compare("a", "a"));
        assertTrue(ObjectHelper.compare(null, "a") < 0);
        assertTrue(ObjectHelper.compare("a", null) > 0);
        assertEquals(0, ObjectHelper.compare(null, null));
    }

    @Test
    void testCompareNullGreater() {
        assertTrue(ObjectHelper.compare(null, "a", true) > 0);
        assertTrue(ObjectHelper.compare("a", null, true) < 0);
    }

    @Test
    void testClone() {
        final int[] original = {1, 2, 3};
        final int[] cloned = ObjectHelper.clone(original);
        assertArrayEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    void testCloneNotCloneable() {
        assertNull(ObjectHelper.clone("test"));
    }

    @Test
    void testCloneIfPossible() {
        assertEquals("test", ObjectHelper.cloneIfPossible("test"));
    }

    @Test
    void testIdentityToString() {
        assertNull(ObjectHelper.identityToString(null));
        final Object obj = new Object();
        final String result = ObjectHelper.identityToString(obj);
        assertTrue(result.startsWith("java.lang.Object@"));
    }

    @Test
    void testToString() {
        assertEquals("", ObjectHelper.toString(null));
        assertEquals("abc", ObjectHelper.toString("abc"));
        assertEquals("default", ObjectHelper.toString(null, "default"));
    }

    @Test
    void testIsEmpty() {
        assertTrue(ObjectHelper.isEmpty(null));
        assertTrue(ObjectHelper.isEmpty(""));
        assertFalse(ObjectHelper.isEmpty("abc"));
        assertTrue(ObjectHelper.isEmpty(new Object[0]));
        assertFalse(ObjectHelper.isEmpty(new Object[]{"a"}));
        assertTrue(ObjectHelper.isEmpty(Collections.emptyList()));
        assertFalse(ObjectHelper.isEmpty(Collections.singletonList("a")));
        assertTrue(ObjectHelper.isEmpty(Collections.emptyMap()));
        assertFalse(ObjectHelper.isEmpty(42));
    }

    @Test
    void testIsNotEmpty() {
        assertFalse(ObjectHelper.isNotEmpty(null));
        assertTrue(ObjectHelper.isNotEmpty("abc"));
    }

    @Test
    void testMax() {
        assertEquals("c", ObjectHelper.max("a", "b", "c"));
        assertEquals("b", ObjectHelper.max("a", null, "b"));
        assertNull(ObjectHelper.max((String[]) null));
    }

    @Test
    void testMin() {
        assertEquals("a", ObjectHelper.min("a", "b", "c"));
        assertEquals("a", ObjectHelper.min("a", null, "b"));
        assertNull(ObjectHelper.min((String[]) null));
    }

    @Test
    void testMedian() {
        assertEquals(Integer.valueOf(2), ObjectHelper.median(1, 2, 3));
        assertEquals(Integer.valueOf(3), ObjectHelper.median(3, 1, 2, 4));
    }

    @Test
    void testMedianEmpty() {
        assertThrows(IllegalArgumentException.class, () -> ObjectHelper.median());
    }

    @Test
    void testMode() {
        assertEquals("a", ObjectHelper.mode("a", "a", "b", "c"));
        assertNull(ObjectHelper.mode("a", "b", "c"));
        assertNull(ObjectHelper.mode());
    }

    @Test
    void testRequireNonEmpty() {
        assertEquals("abc", ObjectHelper.requireNonEmpty("abc"));
        assertThrows(IllegalArgumentException.class, () -> ObjectHelper.requireNonEmpty(null));
        assertThrows(IllegalArgumentException.class, () -> ObjectHelper.requireNonEmpty(""));
    }

    @Test
    void testNotEqual() {
        assertTrue(ObjectHelper.notEqual("a", "b"));
        assertFalse(ObjectHelper.notEqual("a", "a"));
        assertTrue(ObjectHelper.notEqual(null, "a"));
        assertFalse(ObjectHelper.notEqual(null, null));
    }

    @Test
    void testHashCode() {
        assertEquals(0, ObjectHelper.hashCode(null));
        assertEquals("test".hashCode(), ObjectHelper.hashCode("test"));
    }

    @Test
    void testHashCodeMulti() {
        assertEquals(Objects.hash("a", "b"), ObjectHelper.hashCodeMulti("a", "b"));
    }

    @Test
    void testGetClass() {
        assertNull(ObjectHelper.getClass(null));
        assertEquals(String.class, ObjectHelper.getClass("test"));
    }
}

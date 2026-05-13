package com.lang.util.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EqualityCheckerTest {

    @Test
    void testEqualObjects() {
        assertTrue(new EqualityChecker()
                .append("hello", "hello")
                .append(42, 42)
                .isEquals());
    }

    @Test
    void testUnequalObjects() {
        assertFalse(new EqualityChecker()
                .append("hello", "world")
                .isEquals());
    }

    @Test
    void testShortCircuitOnFailure() {
        assertFalse(new EqualityChecker()
                .append(1, 2)
                .append("a", "a")
                .isEquals());
    }

    @Test
    void testAppendSuper() {
        assertTrue(new EqualityChecker().appendSuper(true).isEquals());
        assertFalse(new EqualityChecker().appendSuper(false).isEquals());
    }

    @Test
    void testAppendSuperShortCircuit() {
        assertFalse(new EqualityChecker()
                .appendSuper(false)
                .append(1, 1)
                .isEquals());
    }

    @Test
    void testAppendObjectNull() {
        assertTrue(new EqualityChecker().append((Object) null, (Object) null).isEquals());
        assertFalse(new EqualityChecker().append("a", (Object) null).isEquals());
        assertFalse(new EqualityChecker().append((Object) null, "a").isEquals());
    }

    @Test
    void testAppendObjectSameReference() {
        final Object obj = new Object();
        assertTrue(new EqualityChecker().append(obj, obj).isEquals());
    }

    @Test
    void testAppendLong() {
        assertTrue(new EqualityChecker().append(100L, 100L).isEquals());
        assertFalse(new EqualityChecker().append(100L, 200L).isEquals());
    }

    @Test
    void testAppendInt() {
        assertTrue(new EqualityChecker().append(42, 42).isEquals());
        assertFalse(new EqualityChecker().append(42, 43).isEquals());
    }

    @Test
    void testAppendShort() {
        assertTrue(new EqualityChecker().append((short) 1, (short) 1).isEquals());
        assertFalse(new EqualityChecker().append((short) 1, (short) 2).isEquals());
    }

    @Test
    void testAppendChar() {
        assertTrue(new EqualityChecker().append('a', 'a').isEquals());
        assertFalse(new EqualityChecker().append('a', 'b').isEquals());
    }

    @Test
    void testAppendByte() {
        assertTrue(new EqualityChecker().append((byte) 1, (byte) 1).isEquals());
        assertFalse(new EqualityChecker().append((byte) 1, (byte) 2).isEquals());
    }

    @Test
    void testAppendDouble() {
        assertTrue(new EqualityChecker().append(1.0, 1.0).isEquals());
        assertFalse(new EqualityChecker().append(1.0, 2.0).isEquals());
        assertTrue(new EqualityChecker().append(Double.NaN, Double.NaN).isEquals());
    }

    @Test
    void testAppendFloat() {
        assertTrue(new EqualityChecker().append(1.0f, 1.0f).isEquals());
        assertFalse(new EqualityChecker().append(1.0f, 2.0f).isEquals());
        assertTrue(new EqualityChecker().append(Float.NaN, Float.NaN).isEquals());
    }

    @Test
    void testAppendBoolean() {
        assertTrue(new EqualityChecker().append(true, true).isEquals());
        assertTrue(new EqualityChecker().append(false, false).isEquals());
        assertFalse(new EqualityChecker().append(true, false).isEquals());
    }

    @Test
    void testAppendObjectArray() {
        assertTrue(new EqualityChecker()
                .append(new String[]{"a", "b"}, new String[]{"a", "b"}).isEquals());
        assertFalse(new EqualityChecker()
                .append(new String[]{"a", "b"}, new String[]{"a", "c"}).isEquals());
    }

    @Test
    void testAppendObjectArrayNull() {
        assertTrue(new EqualityChecker().append((Object[]) null, (Object[]) null).isEquals());
        assertFalse(new EqualityChecker().append(new Object[0], (Object[]) null).isEquals());
        assertFalse(new EqualityChecker().append((Object[]) null, new Object[0]).isEquals());
    }

    @Test
    void testAppendObjectArrayDifferentLength() {
        assertFalse(new EqualityChecker()
                .append(new String[]{"a"}, new String[]{"a", "b"}).isEquals());
    }

    @Test
    void testAppendIntArray() {
        assertTrue(new EqualityChecker().append(new int[]{1, 2}, new int[]{1, 2}).isEquals());
        assertFalse(new EqualityChecker().append(new int[]{1, 2}, new int[]{1, 3}).isEquals());
    }

    @Test
    void testAppendLongArray() {
        assertTrue(new EqualityChecker().append(new long[]{1L}, new long[]{1L}).isEquals());
        assertFalse(new EqualityChecker().append(new long[]{1L}, new long[]{2L}).isEquals());
    }

    @Test
    void testAppendDoubleArray() {
        assertTrue(new EqualityChecker().append(new double[]{1.0}, new double[]{1.0}).isEquals());
        assertFalse(new EqualityChecker().append(new double[]{1.0}, new double[]{2.0}).isEquals());
    }

    @Test
    void testAppendBooleanArray() {
        assertTrue(new EqualityChecker().append(new boolean[]{true}, new boolean[]{true}).isEquals());
        assertFalse(new EqualityChecker().append(new boolean[]{true}, new boolean[]{false}).isEquals());
    }

    @Test
    void testAppendObjectDetectsArray() {
        assertTrue(new EqualityChecker()
                .append((Object) new int[]{1, 2}, (Object) new int[]{1, 2}).isEquals());
        assertFalse(new EqualityChecker()
                .append((Object) new int[]{1, 2}, (Object) new int[]{1, 3}).isEquals());
    }

    @Test
    void testBuild() {
        assertEquals(Boolean.TRUE, new EqualityChecker().append(1, 1).build());
        assertEquals(Boolean.FALSE, new EqualityChecker().append(1, 2).build());
    }

    @Test
    void testReset() {
        final EqualityChecker checker = new EqualityChecker();
        checker.append(1, 2);
        assertFalse(checker.isEquals());
        checker.reset();
        assertTrue(checker.isEquals());
    }

    @Test
    void testMultipleFields() {
        assertTrue(new EqualityChecker()
                .append(1, 1)
                .append("hello", "hello")
                .append(3.14, 3.14)
                .append(true, true)
                .isEquals());
    }

    @Test
    void testReflectionEquals() {
        final TestObj a = new TestObj("alice", 30);
        final TestObj b = new TestObj("alice", 30);
        final TestObj c = new TestObj("bob", 30);
        assertTrue(EqualityChecker.reflectionEquals(a, b));
        assertFalse(EqualityChecker.reflectionEquals(a, c));
    }

    @Test
    void testReflectionEqualsSameReference() {
        final TestObj a = new TestObj("alice", 30);
        assertTrue(EqualityChecker.reflectionEquals(a, a));
    }

    @Test
    void testReflectionEqualsNull() {
        assertFalse(EqualityChecker.reflectionEquals(null, new TestObj("a", 1)));
        assertFalse(EqualityChecker.reflectionEquals(new TestObj("a", 1), null));
        assertTrue(EqualityChecker.reflectionEquals(null, null));
    }

    @Test
    void testReflectionEqualsExcludeFields() {
        final TestObj a = new TestObj("alice", 30);
        final TestObj b = new TestObj("bob", 30);
        assertTrue(EqualityChecker.reflectionEquals(a, b, "name"));
    }

    @Test
    void testReflectionEqualsIncompatibleTypes() {
        assertFalse(EqualityChecker.reflectionEquals("hello", Integer.valueOf(42)));
    }

    static class TestObj {
        final String name;
        final int age;
        TestObj(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}

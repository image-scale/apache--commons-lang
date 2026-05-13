package com.lang.util.builder;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class OrderingComparatorTest {

    @Test
    void testEqualInts() {
        assertEquals(0, new OrderingComparator().append(5, 5).toComparison());
    }

    @Test
    void testLessThanInt() {
        assertTrue(new OrderingComparator().append(1, 5).toComparison() < 0);
    }

    @Test
    void testGreaterThanInt() {
        assertTrue(new OrderingComparator().append(5, 1).toComparison() > 0);
    }

    @Test
    void testShortCircuit() {
        final OrderingComparator comp = new OrderingComparator()
                .append(1, 2)
                .append(10, 5);
        assertTrue(comp.toComparison() < 0);
    }

    @Test
    void testAppendSuper() {
        assertEquals(0, new OrderingComparator().appendSuper(0).toComparison());
        assertTrue(new OrderingComparator().appendSuper(-1).toComparison() < 0);
        assertTrue(new OrderingComparator().appendSuper(1).toComparison() > 0);
    }

    @Test
    void testAppendSuperShortCircuit() {
        assertTrue(new OrderingComparator()
                .appendSuper(-1)
                .append(100, 1)
                .toComparison() < 0);
    }

    @Test
    void testAppendLong() {
        assertEquals(0, new OrderingComparator().append(100L, 100L).toComparison());
        assertTrue(new OrderingComparator().append(1L, 100L).toComparison() < 0);
        assertTrue(new OrderingComparator().append(100L, 1L).toComparison() > 0);
    }

    @Test
    void testAppendShort() {
        assertEquals(0, new OrderingComparator().append((short) 5, (short) 5).toComparison());
        assertTrue(new OrderingComparator().append((short) 1, (short) 5).toComparison() < 0);
    }

    @Test
    void testAppendChar() {
        assertEquals(0, new OrderingComparator().append('a', 'a').toComparison());
        assertTrue(new OrderingComparator().append('a', 'z').toComparison() < 0);
    }

    @Test
    void testAppendByte() {
        assertEquals(0, new OrderingComparator().append((byte) 1, (byte) 1).toComparison());
        assertTrue(new OrderingComparator().append((byte) 1, (byte) 5).toComparison() < 0);
    }

    @Test
    void testAppendDouble() {
        assertEquals(0, new OrderingComparator().append(3.14, 3.14).toComparison());
        assertTrue(new OrderingComparator().append(1.0, 2.0).toComparison() < 0);
        assertTrue(new OrderingComparator().append(2.0, 1.0).toComparison() > 0);
    }

    @Test
    void testAppendFloat() {
        assertEquals(0, new OrderingComparator().append(1.0f, 1.0f).toComparison());
        assertTrue(new OrderingComparator().append(1.0f, 2.0f).toComparison() < 0);
    }

    @Test
    void testAppendBooleanEqual() {
        assertEquals(0, new OrderingComparator().append(true, true).toComparison());
        assertEquals(0, new OrderingComparator().append(false, false).toComparison());
    }

    @Test
    void testAppendBooleanDifferent() {
        assertTrue(new OrderingComparator().append(true, false).toComparison() > 0);
        assertTrue(new OrderingComparator().append(false, true).toComparison() < 0);
    }

    @Test
    void testAppendComparableObject() {
        assertEquals(0, new OrderingComparator().append((Object) "abc", (Object) "abc").toComparison());
        assertTrue(new OrderingComparator().append((Object) "abc", (Object) "def").toComparison() < 0);
    }

    @Test
    void testAppendObjectNull() {
        assertTrue(new OrderingComparator().append((Object) null, "abc").toComparison() < 0);
        assertTrue(new OrderingComparator().append("abc", (Object) null).toComparison() > 0);
        assertEquals(0, new OrderingComparator().append((Object) null, (Object) null).toComparison());
    }

    @Test
    void testAppendObjectWithComparator() {
        final Comparator<String> reversed = Comparator.reverseOrder();
        assertTrue(new OrderingComparator().append("abc", "def", reversed).toComparison() > 0);
    }

    @Test
    void testAppendObjectArrayEqual() {
        final String[] a = {"a", "b"};
        final String[] b = {"a", "b"};
        assertEquals(0, new OrderingComparator().append(a, b).toComparison());
    }

    @Test
    void testAppendObjectArrayLess() {
        assertTrue(new OrderingComparator()
                .append(new String[]{"a"}, new String[]{"b"}).toComparison() < 0);
    }

    @Test
    void testAppendObjectArrayDifferentLength() {
        assertTrue(new OrderingComparator()
                .append(new String[]{"a"}, new String[]{"a", "b"}).toComparison() < 0);
        assertTrue(new OrderingComparator()
                .append(new String[]{"a", "b"}, new String[]{"a"}).toComparison() > 0);
    }

    @Test
    void testAppendObjectArrayNull() {
        assertTrue(new OrderingComparator()
                .append((Object[]) null, new String[]{"a"}).toComparison() < 0);
        assertTrue(new OrderingComparator()
                .append(new String[]{"a"}, (Object[]) null).toComparison() > 0);
        assertEquals(0, new OrderingComparator()
                .append((Object[]) null, (Object[]) null).toComparison());
    }

    @Test
    void testAppendObjectArraySameRef() {
        final String[] a = {"x"};
        assertEquals(0, new OrderingComparator().append(a, a).toComparison());
    }

    @Test
    void testAppendIntArray() {
        assertEquals(0, new OrderingComparator()
                .append(new int[]{1, 2}, new int[]{1, 2}).toComparison());
        assertTrue(new OrderingComparator()
                .append(new int[]{1}, new int[]{2}).toComparison() < 0);
    }

    @Test
    void testAppendIntArrayNull() {
        assertTrue(new OrderingComparator()
                .append((int[]) null, new int[]{1}).toComparison() < 0);
        assertTrue(new OrderingComparator()
                .append(new int[]{1}, (int[]) null).toComparison() > 0);
    }

    @Test
    void testAppendLongArray() {
        assertEquals(0, new OrderingComparator()
                .append(new long[]{1L}, new long[]{1L}).toComparison());
        assertTrue(new OrderingComparator()
                .append(new long[]{1L}, new long[]{2L}).toComparison() < 0);
    }

    @Test
    void testAppendDoubleArray() {
        assertEquals(0, new OrderingComparator()
                .append(new double[]{1.0}, new double[]{1.0}).toComparison());
        assertTrue(new OrderingComparator()
                .append(new double[]{1.0}, new double[]{2.0}).toComparison() < 0);
    }

    @Test
    void testAppendBooleanArray() {
        assertEquals(0, new OrderingComparator()
                .append(new boolean[]{true}, new boolean[]{true}).toComparison());
        assertTrue(new OrderingComparator()
                .append(new boolean[]{false}, new boolean[]{true}).toComparison() < 0);
    }

    @Test
    void testAppendBooleanArrayNull() {
        assertTrue(new OrderingComparator()
                .append((boolean[]) null, new boolean[]{true}).toComparison() < 0);
    }

    @Test
    void testBuild() {
        assertEquals(Integer.valueOf(0),
                new OrderingComparator().append(1, 1).build());
    }

    @Test
    void testMultipleFields() {
        assertEquals(0, new OrderingComparator()
                .append(1, 1)
                .append("abc", "abc")
                .append(true, true)
                .toComparison());
    }

    @Test
    void testObjectDetectsIntArray() {
        assertTrue(new OrderingComparator()
                .append((Object) new int[]{1}, (Object) new int[]{2}).toComparison() < 0);
    }

    @Test
    void testReflectionCompare() {
        final TestObj a = new TestObj("alice", 30);
        final TestObj b = new TestObj("alice", 30);
        assertEquals(0, OrderingComparator.reflectionCompare(a, b));
    }

    @Test
    void testReflectionCompareLess() {
        final TestObj a = new TestObj("alice", 30);
        final TestObj b = new TestObj("bob", 30);
        assertTrue(OrderingComparator.reflectionCompare(a, b) < 0);
    }

    @Test
    void testReflectionCompareGreater() {
        final TestObj a = new TestObj("bob", 30);
        final TestObj b = new TestObj("alice", 30);
        assertTrue(OrderingComparator.reflectionCompare(a, b) > 0);
    }

    @Test
    void testReflectionCompareSameReference() {
        final TestObj a = new TestObj("alice", 30);
        assertEquals(0, OrderingComparator.reflectionCompare(a, a));
    }

    @Test
    void testReflectionCompareNullThrows() {
        assertThrows(NullPointerException.class,
                () -> OrderingComparator.reflectionCompare(null, new TestObj("a", 1)));
        assertThrows(NullPointerException.class,
                () -> OrderingComparator.reflectionCompare(new TestObj("a", 1), null));
    }

    @Test
    void testReflectionCompareIncompatibleThrows() {
        assertThrows(ClassCastException.class,
                () -> OrderingComparator.reflectionCompare("hello", Integer.valueOf(42)));
    }

    @Test
    void testReflectionCompareExcludeFields() {
        final TestObj a = new TestObj("alice", 30);
        final TestObj b = new TestObj("bob", 30);
        assertEquals(0, OrderingComparator.reflectionCompare(a, b, "name"));
    }

    static class TestObj implements Comparable<TestObj> {
        final String name;
        final int age;
        TestObj(String name, int age) {
            this.name = name;
            this.age = age;
        }
        @Override
        public int compareTo(TestObj o) {
            return name.compareTo(o.name);
        }
    }
}

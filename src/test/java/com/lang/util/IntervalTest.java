package com.lang.util;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {

    @Test
    void testOfNaturalOrder() {
        final Interval<Integer> r = Interval.of(1, 10);
        assertEquals(1, r.getMinimum());
        assertEquals(10, r.getMaximum());
    }

    @Test
    void testOfReversedArguments() {
        final Interval<Integer> r = Interval.of(10, 1);
        assertEquals(1, r.getMinimum());
        assertEquals(10, r.getMaximum());
    }

    @Test
    void testIs() {
        final Interval<Integer> r = Interval.is(5);
        assertEquals(5, r.getMinimum());
        assertEquals(5, r.getMaximum());
    }

    @Test
    void testNullThrows() {
        assertThrows(NullPointerException.class, () -> Interval.of(null, 10));
        assertThrows(NullPointerException.class, () -> Interval.of(1, null));
    }

    @Test
    void testContains() {
        final Interval<Integer> r = Interval.of(1, 10);
        assertTrue(r.contains(1));
        assertTrue(r.contains(5));
        assertTrue(r.contains(10));
        assertFalse(r.contains(0));
        assertFalse(r.contains(11));
        assertFalse(r.contains(null));
    }

    @Test
    void testContainsRange() {
        final Interval<Integer> outer = Interval.of(1, 10);
        assertTrue(outer.containsRange(Interval.of(2, 8)));
        assertTrue(outer.containsRange(Interval.of(1, 10)));
        assertFalse(outer.containsRange(Interval.of(0, 5)));
        assertFalse(outer.containsRange(Interval.of(5, 11)));
        assertFalse(outer.containsRange(null));
    }

    @Test
    void testIsOverlappedBy() {
        final Interval<Integer> r = Interval.of(3, 7);
        assertTrue(r.isOverlappedBy(Interval.of(1, 5)));
        assertTrue(r.isOverlappedBy(Interval.of(5, 10)));
        assertTrue(r.isOverlappedBy(Interval.of(4, 6)));
        assertTrue(r.isOverlappedBy(Interval.of(1, 10)));
        assertFalse(r.isOverlappedBy(Interval.of(8, 10)));
        assertFalse(r.isOverlappedBy(Interval.of(1, 2)));
        assertFalse(r.isOverlappedBy(null));
    }

    @Test
    void testIntersectionWith() {
        final Interval<Integer> r1 = Interval.of(1, 10);
        final Interval<Integer> r2 = Interval.of(5, 15);
        final Interval<Integer> intersection = r1.intersectionWith(r2);
        assertEquals(5, intersection.getMinimum());
        assertEquals(10, intersection.getMaximum());
    }

    @Test
    void testIntersectionWithNoOverlapThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Interval.of(1, 5).intersectionWith(Interval.of(6, 10)));
    }

    @Test
    void testFit() {
        final Interval<Integer> r = Interval.of(16, 64);
        assertEquals(16, r.fit(10));
        assertEquals(16, r.fit(16));
        assertEquals(32, r.fit(32));
        assertEquals(64, r.fit(64));
        assertEquals(64, r.fit(100));
    }

    @Test
    void testFitNullThrows() {
        assertThrows(NullPointerException.class, () -> Interval.of(1, 10).fit(null));
    }

    @Test
    void testIsAfter() {
        final Interval<Integer> r = Interval.of(5, 10);
        assertTrue(r.isAfter(3));
        assertFalse(r.isAfter(5));
        assertFalse(r.isAfter(7));
        assertFalse(r.isAfter(null));
    }

    @Test
    void testIsBefore() {
        final Interval<Integer> r = Interval.of(5, 10);
        assertTrue(r.isBefore(12));
        assertFalse(r.isBefore(10));
        assertFalse(r.isBefore(7));
        assertFalse(r.isBefore(null));
    }

    @Test
    void testIsAfterRange() {
        final Interval<Integer> r = Interval.of(5, 10);
        assertTrue(r.isAfterRange(Interval.of(1, 3)));
        assertFalse(r.isAfterRange(Interval.of(1, 5)));
        assertFalse(r.isAfterRange(null));
    }

    @Test
    void testIsBeforeRange() {
        final Interval<Integer> r = Interval.of(5, 10);
        assertTrue(r.isBeforeRange(Interval.of(12, 15)));
        assertFalse(r.isBeforeRange(Interval.of(10, 15)));
        assertFalse(r.isBeforeRange(null));
    }

    @Test
    void testElementCompareTo() {
        final Interval<Integer> r = Interval.of(5, 10);
        assertEquals(-1, r.elementCompareTo(3));
        assertEquals(0, r.elementCompareTo(7));
        assertEquals(1, r.elementCompareTo(12));
    }

    @Test
    void testElementCompareToNullThrows() {
        assertThrows(NullPointerException.class,
                () -> Interval.of(1, 10).elementCompareTo(null));
    }

    @Test
    void testEquals() {
        assertEquals(Interval.of(1, 10), Interval.of(1, 10));
        assertEquals(Interval.of(10, 1), Interval.of(1, 10));
        assertNotEquals(Interval.of(1, 10), Interval.of(1, 11));
        assertNotEquals(Interval.of(1, 10), null);
        assertNotEquals(Interval.of(1, 10), "hello");
    }

    @Test
    void testEqualsSameRef() {
        final Interval<Integer> r = Interval.of(1, 10);
        assertEquals(r, r);
    }

    @Test
    void testHashCodeConsistent() {
        assertEquals(Interval.of(1, 10).hashCode(), Interval.of(1, 10).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("[1..10]", Interval.of(1, 10).toString());
    }

    @Test
    void testWithCustomComparator() {
        final Comparator<String> byLength = Comparator.comparingInt(String::length);
        final Interval<String> r = Interval.of("a", "hello", byLength);
        assertEquals("a", r.getMinimum());
        assertEquals("hello", r.getMaximum());
        assertTrue(r.contains("hi"));
        assertFalse(r.contains("toolong"));
    }

    @Test
    void testStringRange() {
        final Interval<String> r = Interval.of("alpha", "omega");
        assertTrue(r.contains("beta"));
        assertFalse(r.contains("zzz"));
    }
}

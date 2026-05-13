package com.lang.util.mutable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableLongTest {

    @Test
    void testConstructors() {
        assertEquals(0L, new MutableLong().get());
        assertEquals(42L, new MutableLong(42L).get());
        assertEquals(10L, new MutableLong(Integer.valueOf(10)).get());
        assertEquals(123L, new MutableLong("123").get());
    }

    @Test
    void testSetAndGet() {
        final MutableLong ml = new MutableLong();
        ml.set(99L);
        assertEquals(99L, ml.get());
        ml.set(Double.valueOf(5.9));
        assertEquals(5L, ml.get());
    }

    @Test
    void testIncrementDecrement() {
        final MutableLong ml = new MutableLong(10L);
        ml.increment();
        assertEquals(11L, ml.get());
        assertEquals(12L, ml.incrementAndGet());
        assertEquals(12L, ml.getAndIncrement());
        assertEquals(13L, ml.get());
        ml.decrement();
        assertEquals(12L, ml.get());
        assertEquals(11L, ml.decrementAndGet());
        assertEquals(11L, ml.getAndDecrement());
        assertEquals(10L, ml.get());
    }

    @Test
    void testAddSubtract() {
        final MutableLong ml = new MutableLong(10L);
        ml.add(5L);
        assertEquals(15L, ml.get());
        ml.subtract(3L);
        assertEquals(12L, ml.get());
        assertEquals(12L, ml.getAndAdd(8L));
        assertEquals(20L, ml.get());
        assertEquals(25L, ml.addAndGet(5L));
    }

    @Test
    void testAddSubtractNumber() {
        final MutableLong ml = new MutableLong(10L);
        ml.add(Integer.valueOf(5));
        assertEquals(15L, ml.get());
        ml.subtract(Integer.valueOf(3));
        assertEquals(12L, ml.get());
    }

    @Test
    void testConversions() {
        final MutableLong ml = new MutableLong(42L);
        assertEquals(42, ml.intValue());
        assertEquals(42L, ml.longValue());
        assertEquals(42.0f, ml.floatValue());
        assertEquals(42.0, ml.doubleValue());
        assertEquals(Long.valueOf(42), ml.toLong());
    }

    @Test
    void testCompareToEqualsHashCode() {
        assertTrue(new MutableLong(1).compareTo(new MutableLong(2)) < 0);
        assertEquals(new MutableLong(42), new MutableLong(42));
        assertNotEquals(new MutableLong(42), new MutableLong(43));
        assertEquals(new MutableLong(42).hashCode(), new MutableLong(42).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("42", new MutableLong(42).toString());
    }
}

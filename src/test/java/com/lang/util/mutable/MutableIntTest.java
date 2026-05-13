package com.lang.util.mutable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableIntTest {

    @Test
    void testDefaultConstructor() {
        assertEquals(0, new MutableInt().get());
    }

    @Test
    void testValueConstructor() {
        assertEquals(42, new MutableInt(42).get());
    }

    @Test
    void testNumberConstructor() {
        assertEquals(10, new MutableInt(Long.valueOf(10)).get());
    }

    @Test
    void testStringConstructor() {
        assertEquals(123, new MutableInt("123").get());
    }

    @Test
    void testSetValue() {
        final MutableInt mi = new MutableInt(0);
        mi.set(99);
        assertEquals(99, mi.get());
    }

    @Test
    void testSetNumber() {
        final MutableInt mi = new MutableInt(0);
        mi.set(Double.valueOf(5.9));
        assertEquals(5, mi.get());
    }

    @Test
    void testIncrement() {
        final MutableInt mi = new MutableInt(10);
        mi.increment();
        assertEquals(11, mi.get());
    }

    @Test
    void testIncrementAndGet() {
        final MutableInt mi = new MutableInt(10);
        assertEquals(11, mi.incrementAndGet());
    }

    @Test
    void testGetAndIncrement() {
        final MutableInt mi = new MutableInt(10);
        assertEquals(10, mi.getAndIncrement());
        assertEquals(11, mi.get());
    }

    @Test
    void testDecrement() {
        final MutableInt mi = new MutableInt(10);
        mi.decrement();
        assertEquals(9, mi.get());
    }

    @Test
    void testDecrementAndGet() {
        final MutableInt mi = new MutableInt(10);
        assertEquals(9, mi.decrementAndGet());
    }

    @Test
    void testGetAndDecrement() {
        final MutableInt mi = new MutableInt(10);
        assertEquals(10, mi.getAndDecrement());
        assertEquals(9, mi.get());
    }

    @Test
    void testAdd() {
        final MutableInt mi = new MutableInt(10);
        mi.add(5);
        assertEquals(15, mi.get());
    }

    @Test
    void testAddNumber() {
        final MutableInt mi = new MutableInt(10);
        mi.add(Integer.valueOf(3));
        assertEquals(13, mi.get());
    }

    @Test
    void testAddAndGet() {
        final MutableInt mi = new MutableInt(10);
        assertEquals(15, mi.addAndGet(5));
    }

    @Test
    void testAddAndGetNumber() {
        final MutableInt mi = new MutableInt(10);
        assertEquals(13, mi.addAndGet(Integer.valueOf(3)));
    }

    @Test
    void testGetAndAdd() {
        final MutableInt mi = new MutableInt(10);
        assertEquals(10, mi.getAndAdd(5));
        assertEquals(15, mi.get());
    }

    @Test
    void testGetAndAddNumber() {
        final MutableInt mi = new MutableInt(10);
        assertEquals(10, mi.getAndAdd(Integer.valueOf(3)));
        assertEquals(13, mi.get());
    }

    @Test
    void testSubtract() {
        final MutableInt mi = new MutableInt(10);
        mi.subtract(3);
        assertEquals(7, mi.get());
    }

    @Test
    void testSubtractNumber() {
        final MutableInt mi = new MutableInt(10);
        mi.subtract(Integer.valueOf(3));
        assertEquals(7, mi.get());
    }

    @Test
    void testNumberConversions() {
        final MutableInt mi = new MutableInt(42);
        assertEquals(42, mi.intValue());
        assertEquals(42L, mi.longValue());
        assertEquals(42.0f, mi.floatValue());
        assertEquals(42.0, mi.doubleValue());
    }

    @Test
    void testToInteger() {
        assertEquals(Integer.valueOf(42), new MutableInt(42).toInteger());
    }

    @Test
    void testCompareTo() {
        assertTrue(new MutableInt(1).compareTo(new MutableInt(2)) < 0);
        assertTrue(new MutableInt(2).compareTo(new MutableInt(1)) > 0);
        assertEquals(0, new MutableInt(5).compareTo(new MutableInt(5)));
    }

    @Test
    void testEquals() {
        assertEquals(new MutableInt(42), new MutableInt(42));
        assertNotEquals(new MutableInt(42), new MutableInt(43));
        assertNotEquals(new MutableInt(42), "42");
    }

    @Test
    void testHashCode() {
        assertEquals(new MutableInt(42).hashCode(), new MutableInt(42).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("42", new MutableInt(42).toString());
    }
}

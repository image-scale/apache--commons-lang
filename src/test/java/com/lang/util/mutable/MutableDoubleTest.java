package com.lang.util.mutable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableDoubleTest {

    @Test
    void testConstructors() {
        assertEquals(0.0, new MutableDouble().get());
        assertEquals(3.14, new MutableDouble(3.14).get());
        assertEquals(10.0, new MutableDouble(Integer.valueOf(10)).get());
        assertEquals(1.5, new MutableDouble("1.5").get());
    }

    @Test
    void testSetAndGet() {
        final MutableDouble md = new MutableDouble();
        md.set(9.9);
        assertEquals(9.9, md.get());
        md.set(Integer.valueOf(5));
        assertEquals(5.0, md.get());
    }

    @Test
    void testIncrementDecrement() {
        final MutableDouble md = new MutableDouble(10.0);
        md.increment();
        assertEquals(11.0, md.get());
        assertEquals(12.0, md.incrementAndGet());
        assertEquals(12.0, md.getAndIncrement());
        assertEquals(13.0, md.get());
        md.decrement();
        assertEquals(12.0, md.get());
    }

    @Test
    void testAddSubtract() {
        final MutableDouble md = new MutableDouble(10.0);
        md.add(2.5);
        assertEquals(12.5, md.get());
        md.subtract(0.5);
        assertEquals(12.0, md.get());
        assertEquals(12.0, md.getAndAdd(3.0));
        assertEquals(15.0, md.get());
        assertEquals(20.0, md.addAndGet(5.0));
    }

    @Test
    void testConversions() {
        final MutableDouble md = new MutableDouble(42.7);
        assertEquals(42, md.intValue());
        assertEquals(42L, md.longValue());
        assertEquals(42.7f, md.floatValue(), 0.001f);
        assertEquals(42.7, md.doubleValue());
        assertEquals(Double.valueOf(42.7), md.toDouble());
    }

    @Test
    void testCompareToEqualsHashCode() {
        assertTrue(new MutableDouble(1.0).compareTo(new MutableDouble(2.0)) < 0);
        assertEquals(new MutableDouble(42.0), new MutableDouble(42.0));
        assertNotEquals(new MutableDouble(42.0), new MutableDouble(42.1));
        assertEquals(new MutableDouble(42.0).hashCode(), new MutableDouble(42.0).hashCode());
    }

    @Test
    void testNaNEquals() {
        assertEquals(new MutableDouble(Double.NaN), new MutableDouble(Double.NaN));
    }

    @Test
    void testToString() {
        assertEquals("3.14", new MutableDouble(3.14).toString());
    }
}

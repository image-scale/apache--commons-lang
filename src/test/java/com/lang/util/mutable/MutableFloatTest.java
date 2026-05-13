package com.lang.util.mutable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableFloatTest {

    @Test
    void testConstructors() {
        assertEquals(0.0f, new MutableFloat().get());
        assertEquals(3.14f, new MutableFloat(3.14f).get());
        assertEquals(10.0f, new MutableFloat(Integer.valueOf(10)).get());
        assertEquals(1.5f, new MutableFloat("1.5").get());
    }

    @Test
    void testSetAndGet() {
        final MutableFloat mf = new MutableFloat();
        mf.set(9.9f);
        assertEquals(9.9f, mf.get());
    }

    @Test
    void testIncrementDecrement() {
        final MutableFloat mf = new MutableFloat(10.0f);
        mf.increment();
        assertEquals(11.0f, mf.get());
        assertEquals(12.0f, mf.incrementAndGet());
        assertEquals(12.0f, mf.getAndIncrement());
        assertEquals(13.0f, mf.get());
        mf.decrement();
        assertEquals(12.0f, mf.get());
    }

    @Test
    void testAddSubtract() {
        final MutableFloat mf = new MutableFloat(10.0f);
        mf.add(2.5f);
        assertEquals(12.5f, mf.get());
        mf.subtract(0.5f);
        assertEquals(12.0f, mf.get());
    }

    @Test
    void testConversions() {
        final MutableFloat mf = new MutableFloat(42.5f);
        assertEquals(42, mf.intValue());
        assertEquals(42L, mf.longValue());
        assertEquals(42.5f, mf.floatValue());
        assertEquals(Float.valueOf(42.5f), mf.toFloat());
    }

    @Test
    void testCompareToEqualsHashCode() {
        assertTrue(new MutableFloat(1.0f).compareTo(new MutableFloat(2.0f)) < 0);
        assertEquals(new MutableFloat(42.0f), new MutableFloat(42.0f));
        assertNotEquals(new MutableFloat(42.0f), new MutableFloat(42.1f));
    }

    @Test
    void testToString() {
        assertEquals("3.14", new MutableFloat(3.14f).toString());
    }
}

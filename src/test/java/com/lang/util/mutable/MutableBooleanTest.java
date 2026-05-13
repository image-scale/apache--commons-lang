package com.lang.util.mutable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableBooleanTest {

    @Test
    void testDefaultConstructor() {
        assertFalse(new MutableBoolean().get());
    }

    @Test
    void testBooleanConstructor() {
        assertTrue(new MutableBoolean(true).get());
        assertFalse(new MutableBoolean(false).get());
    }

    @Test
    void testBoxedConstructor() {
        assertTrue(new MutableBoolean(Boolean.TRUE).get());
    }

    @Test
    void testSetValue() {
        final MutableBoolean mb = new MutableBoolean(false);
        mb.set(true);
        assertTrue(mb.get());
    }

    @Test
    void testSetTrueFalse() {
        final MutableBoolean mb = new MutableBoolean(false);
        mb.setTrue();
        assertTrue(mb.isTrue());
        assertFalse(mb.isFalse());
        mb.setFalse();
        assertTrue(mb.isFalse());
        assertFalse(mb.isTrue());
    }

    @Test
    void testToBoolean() {
        assertEquals(Boolean.TRUE, new MutableBoolean(true).toBoolean());
        assertEquals(Boolean.FALSE, new MutableBoolean(false).toBoolean());
    }

    @Test
    void testCompareTo() {
        assertTrue(new MutableBoolean(false).compareTo(new MutableBoolean(true)) < 0);
        assertTrue(new MutableBoolean(true).compareTo(new MutableBoolean(false)) > 0);
        assertEquals(0, new MutableBoolean(true).compareTo(new MutableBoolean(true)));
    }

    @Test
    void testEquals() {
        assertEquals(new MutableBoolean(true), new MutableBoolean(true));
        assertNotEquals(new MutableBoolean(true), new MutableBoolean(false));
        assertNotEquals(new MutableBoolean(true), "true");
    }

    @Test
    void testHashCode() {
        assertEquals(new MutableBoolean(true).hashCode(), new MutableBoolean(true).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("true", new MutableBoolean(true).toString());
        assertEquals("false", new MutableBoolean(false).toString());
    }
}

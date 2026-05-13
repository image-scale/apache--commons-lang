package com.lang.util.mutable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableByteShortTest {

    @Test
    void testMutableByteConstructors() {
        assertEquals((byte) 0, new MutableByte().get());
        assertEquals((byte) 42, new MutableByte((byte) 42).get());
        assertEquals((byte) 10, new MutableByte(Integer.valueOf(10)).get());
        assertEquals((byte) 5, new MutableByte("5").get());
    }

    @Test
    void testMutableByteOperations() {
        final MutableByte mb = new MutableByte((byte) 10);
        mb.increment();
        assertEquals((byte) 11, mb.get());
        assertEquals((byte) 12, mb.incrementAndGet());
        assertEquals((byte) 12, mb.getAndIncrement());
        assertEquals((byte) 13, mb.get());
        mb.decrement();
        assertEquals((byte) 12, mb.get());
        mb.add((byte) 3);
        assertEquals((byte) 15, mb.get());
        mb.subtract((byte) 5);
        assertEquals((byte) 10, mb.get());
    }

    @Test
    void testMutableByteConversions() {
        final MutableByte mb = new MutableByte((byte) 42);
        assertEquals(42, mb.intValue());
        assertEquals(42L, mb.longValue());
        assertEquals(Byte.valueOf((byte) 42), mb.toByte());
    }

    @Test
    void testMutableByteEquality() {
        assertEquals(new MutableByte((byte) 42), new MutableByte((byte) 42));
        assertNotEquals(new MutableByte((byte) 42), new MutableByte((byte) 43));
        assertTrue(new MutableByte((byte) 1).compareTo(new MutableByte((byte) 2)) < 0);
    }

    @Test
    void testMutableShortConstructors() {
        assertEquals((short) 0, new MutableShort().get());
        assertEquals((short) 42, new MutableShort((short) 42).get());
        assertEquals((short) 10, new MutableShort(Integer.valueOf(10)).get());
        assertEquals((short) 5, new MutableShort("5").get());
    }

    @Test
    void testMutableShortOperations() {
        final MutableShort ms = new MutableShort((short) 10);
        ms.increment();
        assertEquals((short) 11, ms.get());
        assertEquals((short) 12, ms.incrementAndGet());
        assertEquals((short) 12, ms.getAndIncrement());
        assertEquals((short) 13, ms.get());
        ms.decrement();
        assertEquals((short) 12, ms.get());
        ms.add((short) 3);
        assertEquals((short) 15, ms.get());
        ms.subtract((short) 5);
        assertEquals((short) 10, ms.get());
    }

    @Test
    void testMutableShortConversions() {
        final MutableShort ms = new MutableShort((short) 42);
        assertEquals(42, ms.intValue());
        assertEquals(42L, ms.longValue());
        assertEquals(Short.valueOf((short) 42), ms.toShort());
    }

    @Test
    void testMutableShortEquality() {
        assertEquals(new MutableShort((short) 42), new MutableShort((short) 42));
        assertNotEquals(new MutableShort((short) 42), new MutableShort((short) 43));
        assertTrue(new MutableShort((short) 1).compareTo(new MutableShort((short) 2)) < 0);
    }

    @Test
    void testMutableByteToString() {
        assertEquals("42", new MutableByte((byte) 42).toString());
    }

    @Test
    void testMutableShortToString() {
        assertEquals("42", new MutableShort((short) 42).toString());
    }
}

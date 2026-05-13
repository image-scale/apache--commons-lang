package com.lang.util.mutable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutableObjectTest {

    @Test
    void testDefaultConstructor() {
        assertNull(new MutableObject<>().get());
    }

    @Test
    void testValueConstructor() {
        assertEquals("hello", new MutableObject<>("hello").get());
    }

    @Test
    void testSetAndGet() {
        final MutableObject<String> mo = new MutableObject<>("a");
        mo.set("b");
        assertEquals("b", mo.get());
    }

    @Test
    void testSetNull() {
        final MutableObject<String> mo = new MutableObject<>("a");
        mo.set(null);
        assertNull(mo.get());
    }

    @Test
    void testEqualsWithValues() {
        assertEquals(new MutableObject<>("hello"), new MutableObject<>("hello"));
    }

    @Test
    void testEqualsWithNulls() {
        assertEquals(new MutableObject<>(null), new MutableObject<>(null));
    }

    @Test
    void testNotEquals() {
        assertNotEquals(new MutableObject<>("a"), new MutableObject<>("b"));
    }

    @Test
    void testNotEqualsNull() {
        assertNotEquals(new MutableObject<>("a"), new MutableObject<>(null));
    }

    @Test
    void testNotEqualsWrongType() {
        assertNotEquals(new MutableObject<>("a"), "a");
    }

    @Test
    void testEqualsSameRef() {
        final MutableObject<String> mo = new MutableObject<>("x");
        assertEquals(mo, mo);
    }

    @Test
    void testHashCodeConsistent() {
        assertEquals(new MutableObject<>("hello").hashCode(), new MutableObject<>("hello").hashCode());
    }

    @Test
    void testHashCodeNull() {
        assertEquals(0, new MutableObject<>(null).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("hello", new MutableObject<>("hello").toString());
    }

    @Test
    void testToStringNull() {
        assertEquals("null", new MutableObject<>(null).toString());
    }
}

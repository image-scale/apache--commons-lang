package com.lang.util.tuple;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DuoTest {

    @Test
    void testFixedDuoOf() {
        final FixedDuo<String, Integer> p = FixedDuo.of("hello", 42);
        assertEquals("hello", p.getLeft());
        assertEquals(42, p.getRight());
    }

    @Test
    void testFixedDuoOfNulls() {
        final FixedDuo<String, Integer> p = FixedDuo.of(null, null);
        assertNull(p.getLeft());
        assertNull(p.getRight());
        assertSame(p, FixedDuo.of(null, null));
    }

    @Test
    void testFixedDuoOfNonNull() {
        final FixedDuo<String, Integer> p = FixedDuo.ofNonNull("a", 1);
        assertEquals("a", p.getLeft());
        assertEquals(1, p.getRight());
    }

    @Test
    void testFixedDuoOfNonNullThrows() {
        assertThrows(NullPointerException.class, () -> FixedDuo.ofNonNull(null, 1));
        assertThrows(NullPointerException.class, () -> FixedDuo.ofNonNull("a", null));
    }

    @Test
    void testFixedDuoSetValueThrows() {
        final FixedDuo<String, Integer> p = FixedDuo.of("a", 1);
        assertThrows(UnsupportedOperationException.class, () -> p.setValue(2));
    }

    @Test
    void testFixedDuoFromEntry() {
        final Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("key", 99);
        final FixedDuo<String, Integer> p = FixedDuo.of(entry);
        assertEquals("key", p.getLeft());
        assertEquals(99, p.getRight());
    }

    @Test
    void testFixedDuoFromNullEntry() {
        final FixedDuo<String, Integer> p = FixedDuo.of((Map.Entry<String, Integer>) null);
        assertNull(p.getLeft());
        assertNull(p.getRight());
    }

    @Test
    void testFlexDuoOf() {
        final FlexDuo<String, Integer> p = FlexDuo.of("hello", 42);
        assertEquals("hello", p.getLeft());
        assertEquals(42, p.getRight());
    }

    @Test
    void testFlexDuoDefaultConstructor() {
        final FlexDuo<String, Integer> p = new FlexDuo<>();
        assertNull(p.getLeft());
        assertNull(p.getRight());
    }

    @Test
    void testFlexDuoSetters() {
        final FlexDuo<String, Integer> p = new FlexDuo<>();
        p.setLeft("x");
        p.setRight(10);
        assertEquals("x", p.getLeft());
        assertEquals(10, p.getRight());
    }

    @Test
    void testFlexDuoSetValue() {
        final FlexDuo<String, Integer> p = FlexDuo.of("a", 1);
        final Integer old = p.setValue(2);
        assertEquals(1, old);
        assertEquals(2, p.getRight());
    }

    @Test
    void testFlexDuoOfNonNull() {
        final FlexDuo<String, Integer> p = FlexDuo.ofNonNull("a", 1);
        assertEquals("a", p.getLeft());
    }

    @Test
    void testFlexDuoOfNonNullThrows() {
        assertThrows(NullPointerException.class, () -> FlexDuo.ofNonNull(null, 1));
    }

    @Test
    void testFlexDuoFromEntry() {
        final Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("k", 5);
        final FlexDuo<String, Integer> p = FlexDuo.of(entry);
        assertEquals("k", p.getLeft());
        assertEquals(5, p.getRight());
    }

    @Test
    void testFlexDuoFromNullEntry() {
        final FlexDuo<String, Integer> p = FlexDuo.of((Map.Entry<String, Integer>) null);
        assertNull(p.getLeft());
        assertNull(p.getRight());
    }

    @Test
    void testDuoFactoryOf() {
        final Duo<String, Integer> p = Duo.of("abc", 123);
        assertEquals("abc", p.getLeft());
        assertEquals(123, p.getRight());
        assertInstanceOf(FixedDuo.class, p);
    }

    @Test
    void testDuoFactoryOfEntry() {
        final Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("x", 1);
        final Duo<String, Integer> p = Duo.of(entry);
        assertEquals("x", p.getLeft());
    }

    @Test
    void testDuoOfNonNull() {
        final Duo<String, Integer> p = Duo.ofNonNull("a", 1);
        assertEquals("a", p.getLeft());
    }

    @Test
    void testMapEntryInterface() {
        final Duo<String, Integer> p = Duo.of("key", 42);
        assertEquals("key", p.getKey());
        assertEquals(42, p.getValue());
    }

    @Test
    void testEqualsWithDuo() {
        final Duo<String, Integer> a = Duo.of("x", 1);
        final Duo<String, Integer> b = Duo.of("x", 1);
        assertEquals(a, b);
    }

    @Test
    void testEqualsWithMapEntry() {
        final Duo<String, Integer> a = Duo.of("x", 1);
        final Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("x", 1);
        assertEquals(a, entry);
    }

    @Test
    void testNotEqualsDifferentValues() {
        final Duo<String, Integer> a = Duo.of("x", 1);
        final Duo<String, Integer> b = Duo.of("x", 2);
        assertNotEquals(a, b);
    }

    @Test
    void testNotEqualsDifferentKeys() {
        final Duo<String, Integer> a = Duo.of("x", 1);
        final Duo<String, Integer> b = Duo.of("y", 1);
        assertNotEquals(a, b);
    }

    @Test
    void testEqualsSameRef() {
        final Duo<String, Integer> a = Duo.of("x", 1);
        assertEquals(a, a);
    }

    @Test
    void testNotEqualsNull() {
        assertNotEquals(null, Duo.of("x", 1));
    }

    @Test
    void testNotEqualsWrongType() {
        assertNotEquals("hello", Duo.of("x", 1));
    }

    @Test
    void testHashCodeConsistent() {
        final Duo<String, Integer> a = Duo.of("x", 1);
        final Duo<String, Integer> b = Duo.of("x", 1);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testHashCodeMatchesMapEntry() {
        final Duo<String, Integer> duo = Duo.of("abc", 42);
        final Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("abc", 42);
        assertEquals(entry.hashCode(), duo.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("(hello,42)", Duo.of("hello", 42).toString());
    }

    @Test
    void testToStringWithFormat() {
        assertEquals("left=hello right=42",
                Duo.of("hello", 42).toString("left=%1$s right=%2$s"));
    }

    @Test
    void testCompareTo() {
        final Duo<String, Integer> a = Duo.of("a", 1);
        final Duo<String, Integer> b = Duo.of("b", 1);
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
        assertEquals(0, a.compareTo(Duo.of("a", 1)));
    }

    @Test
    void testCompareToSecondElement() {
        final Duo<String, Integer> a = Duo.of("a", 1);
        final Duo<String, Integer> b = Duo.of("a", 2);
        assertTrue(a.compareTo(b) < 0);
    }

    @Test
    void testNullDuoSingleton() {
        final FixedDuo<Object, Object> a = FixedDuo.nullDuo();
        final FixedDuo<Object, Object> b = FixedDuo.nullDuo();
        assertSame(a, b);
    }

    @Test
    void testMixedImmutableMutableEquals() {
        final FixedDuo<String, Integer> fixed = FixedDuo.of("a", 1);
        final FlexDuo<String, Integer> flex = FlexDuo.of("a", 1);
        assertEquals(fixed, flex);
        assertEquals(flex, fixed);
    }

    @Test
    void testPublicFieldsOnFixedDuo() {
        final FixedDuo<String, Integer> p = FixedDuo.of("test", 99);
        assertEquals("test", p.left);
        assertEquals(99, p.right);
    }
}

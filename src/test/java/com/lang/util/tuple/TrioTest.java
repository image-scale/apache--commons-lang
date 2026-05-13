package com.lang.util.tuple;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrioTest {

    @Test
    void testFixedTrioOf() {
        final FixedTrio<String, Integer, Boolean> t = FixedTrio.of("a", 1, true);
        assertEquals("a", t.getLeft());
        assertEquals(1, t.getMiddle());
        assertEquals(true, t.getRight());
    }

    @Test
    void testFixedTrioOfNulls() {
        final FixedTrio<String, Integer, Boolean> t = FixedTrio.of(null, null, null);
        assertNull(t.getLeft());
        assertNull(t.getMiddle());
        assertNull(t.getRight());
        assertSame(t, FixedTrio.of(null, null, null));
    }

    @Test
    void testFixedTrioOfNonNull() {
        final FixedTrio<String, Integer, Boolean> t = FixedTrio.ofNonNull("a", 1, true);
        assertEquals("a", t.getLeft());
    }

    @Test
    void testFixedTrioOfNonNullThrows() {
        assertThrows(NullPointerException.class, () -> FixedTrio.ofNonNull(null, 1, true));
        assertThrows(NullPointerException.class, () -> FixedTrio.ofNonNull("a", null, true));
        assertThrows(NullPointerException.class, () -> FixedTrio.ofNonNull("a", 1, null));
    }

    @Test
    void testFixedTrioPublicFields() {
        final FixedTrio<String, Integer, Boolean> t = FixedTrio.of("x", 5, false);
        assertEquals("x", t.left);
        assertEquals(5, t.middle);
        assertEquals(false, t.right);
    }

    @Test
    void testFlexTrioOf() {
        final FlexTrio<String, Integer, Boolean> t = FlexTrio.of("hello", 42, true);
        assertEquals("hello", t.getLeft());
        assertEquals(42, t.getMiddle());
        assertEquals(true, t.getRight());
    }

    @Test
    void testFlexTrioDefaultConstructor() {
        final FlexTrio<String, Integer, Boolean> t = new FlexTrio<>();
        assertNull(t.getLeft());
        assertNull(t.getMiddle());
        assertNull(t.getRight());
    }

    @Test
    void testFlexTrioSetters() {
        final FlexTrio<String, Integer, Boolean> t = new FlexTrio<>();
        t.setLeft("a");
        t.setMiddle(10);
        t.setRight(true);
        assertEquals("a", t.getLeft());
        assertEquals(10, t.getMiddle());
        assertEquals(true, t.getRight());
    }

    @Test
    void testFlexTrioOfNonNull() {
        final FlexTrio<String, Integer, Boolean> t = FlexTrio.ofNonNull("a", 1, false);
        assertEquals("a", t.getLeft());
    }

    @Test
    void testFlexTrioOfNonNullThrows() {
        assertThrows(NullPointerException.class, () -> FlexTrio.ofNonNull(null, 1, true));
        assertThrows(NullPointerException.class, () -> FlexTrio.ofNonNull("a", null, true));
        assertThrows(NullPointerException.class, () -> FlexTrio.ofNonNull("a", 1, null));
    }

    @Test
    void testTrioFactoryOf() {
        final Trio<String, Integer, Boolean> t = Trio.of("x", 5, false);
        assertEquals("x", t.getLeft());
        assertInstanceOf(FixedTrio.class, t);
    }

    @Test
    void testTrioOfNonNull() {
        final Trio<String, Integer, Boolean> t = Trio.ofNonNull("a", 1, true);
        assertEquals("a", t.getLeft());
    }

    @Test
    void testEqualsFixed() {
        final FixedTrio<String, Integer, Boolean> a = FixedTrio.of("a", 1, true);
        final FixedTrio<String, Integer, Boolean> b = FixedTrio.of("a", 1, true);
        assertEquals(a, b);
    }

    @Test
    void testNotEquals() {
        final Trio<String, Integer, Boolean> a = Trio.of("a", 1, true);
        final Trio<String, Integer, Boolean> b = Trio.of("a", 2, true);
        assertNotEquals(a, b);
    }

    @Test
    void testNotEqualsLeft() {
        assertNotEquals(Trio.of("x", 1, true), Trio.of("y", 1, true));
    }

    @Test
    void testNotEqualsRight() {
        assertNotEquals(Trio.of("a", 1, true), Trio.of("a", 1, false));
    }

    @Test
    void testEqualsSameRef() {
        final Trio<String, Integer, Boolean> a = Trio.of("a", 1, true);
        assertEquals(a, a);
    }

    @Test
    void testNotEqualsNull() {
        assertNotEquals(null, Trio.of("a", 1, true));
    }

    @Test
    void testNotEqualsWrongType() {
        assertNotEquals("hello", Trio.of("a", 1, true));
    }

    @Test
    void testHashCodeConsistent() {
        final Trio<String, Integer, Boolean> a = Trio.of("x", 5, false);
        final Trio<String, Integer, Boolean> b = Trio.of("x", 5, false);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("(a,1,true)", Trio.of("a", 1, true).toString());
    }

    @Test
    void testToStringWithFormat() {
        assertEquals("left=a mid=1 right=true",
                Trio.of("a", 1, true).toString("left=%1$s mid=%2$s right=%3$s"));
    }

    @Test
    void testCompareTo() {
        final Trio<String, Integer, Boolean> a = Trio.of("a", 1, false);
        final Trio<String, Integer, Boolean> b = Trio.of("b", 1, false);
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
        assertEquals(0, a.compareTo(Trio.of("a", 1, false)));
    }

    @Test
    void testCompareToMiddle() {
        final Trio<String, Integer, Boolean> a = Trio.of("a", 1, false);
        final Trio<String, Integer, Boolean> b = Trio.of("a", 2, false);
        assertTrue(a.compareTo(b) < 0);
    }

    @Test
    void testCompareToRight() {
        final Trio<String, Integer, Boolean> a = Trio.of("a", 1, false);
        final Trio<String, Integer, Boolean> b = Trio.of("a", 1, true);
        assertTrue(a.compareTo(b) < 0);
    }

    @Test
    void testNullTrioSingleton() {
        final FixedTrio<Object, Object, Object> a = FixedTrio.nullTrio();
        final FixedTrio<Object, Object, Object> b = FixedTrio.nullTrio();
        assertSame(a, b);
    }

    @Test
    void testMixedEquals() {
        final FixedTrio<String, Integer, Boolean> fixed = FixedTrio.of("a", 1, true);
        final FlexTrio<String, Integer, Boolean> flex = FlexTrio.of("a", 1, true);
        assertEquals(fixed, flex);
        assertEquals(flex, fixed);
    }
}

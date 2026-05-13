package com.lang.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanHelperTest {

    @Test
    void testIsTrue() {
        assertTrue(BooleanHelper.isTrue(Boolean.TRUE));
        assertFalse(BooleanHelper.isTrue(Boolean.FALSE));
        assertFalse(BooleanHelper.isTrue(null));
    }

    @Test
    void testIsFalse() {
        assertTrue(BooleanHelper.isFalse(Boolean.FALSE));
        assertFalse(BooleanHelper.isFalse(Boolean.TRUE));
        assertFalse(BooleanHelper.isFalse(null));
    }

    @Test
    void testIsNotTrue() {
        assertFalse(BooleanHelper.isNotTrue(Boolean.TRUE));
        assertTrue(BooleanHelper.isNotTrue(Boolean.FALSE));
        assertTrue(BooleanHelper.isNotTrue(null));
    }

    @Test
    void testIsNotFalse() {
        assertTrue(BooleanHelper.isNotFalse(Boolean.TRUE));
        assertFalse(BooleanHelper.isNotFalse(Boolean.FALSE));
        assertTrue(BooleanHelper.isNotFalse(null));
    }

    @Test
    void testToBooleanFromBoolean() {
        assertTrue(BooleanHelper.toBoolean(Boolean.TRUE));
        assertFalse(BooleanHelper.toBoolean(Boolean.FALSE));
        assertFalse(BooleanHelper.toBoolean((Boolean) null));
    }

    @Test
    void testToBooleanDefaultIfNull() {
        assertTrue(BooleanHelper.toBooleanDefaultIfNull(null, true));
        assertFalse(BooleanHelper.toBooleanDefaultIfNull(null, false));
        assertTrue(BooleanHelper.toBooleanDefaultIfNull(Boolean.TRUE, false));
    }

    @Test
    void testToBooleanFromString() {
        assertTrue(BooleanHelper.toBoolean("true"));
        assertTrue(BooleanHelper.toBoolean("True"));
        assertTrue(BooleanHelper.toBoolean("TRUE"));
        assertTrue(BooleanHelper.toBoolean("yes"));
        assertTrue(BooleanHelper.toBoolean("on"));
        assertTrue(BooleanHelper.toBoolean("y"));
        assertTrue(BooleanHelper.toBoolean("t"));
        assertFalse(BooleanHelper.toBoolean("false"));
        assertFalse(BooleanHelper.toBoolean("no"));
        assertFalse(BooleanHelper.toBoolean("off"));
        assertFalse(BooleanHelper.toBoolean("abc"));
        assertFalse(BooleanHelper.toBoolean((String) null));
    }

    @Test
    void testToBooleanObjectFromString() {
        assertEquals(Boolean.TRUE, BooleanHelper.toBooleanObject("true"));
        assertEquals(Boolean.TRUE, BooleanHelper.toBooleanObject("yes"));
        assertEquals(Boolean.TRUE, BooleanHelper.toBooleanObject("on"));
        assertEquals(Boolean.FALSE, BooleanHelper.toBooleanObject("false"));
        assertEquals(Boolean.FALSE, BooleanHelper.toBooleanObject("no"));
        assertEquals(Boolean.FALSE, BooleanHelper.toBooleanObject("off"));
        assertNull(BooleanHelper.toBooleanObject((String) null));
        assertNull(BooleanHelper.toBooleanObject("abc"));
    }

    @Test
    void testToBooleanFromInt() {
        assertTrue(BooleanHelper.toBoolean(1));
        assertTrue(BooleanHelper.toBoolean(-1));
        assertFalse(BooleanHelper.toBoolean(0));
    }

    @Test
    void testToBooleanObjectFromInt() {
        assertEquals(Boolean.TRUE, BooleanHelper.toBooleanObject(1));
        assertEquals(Boolean.FALSE, BooleanHelper.toBooleanObject(0));
    }

    @Test
    void testToBooleanObjectFromInteger() {
        assertEquals(Boolean.TRUE, BooleanHelper.toBooleanObject(Integer.valueOf(1)));
        assertEquals(Boolean.FALSE, BooleanHelper.toBooleanObject(Integer.valueOf(0)));
        assertNull(BooleanHelper.toBooleanObject((Integer) null));
    }

    @Test
    void testToInteger() {
        assertEquals(1, BooleanHelper.toInteger(true));
        assertEquals(0, BooleanHelper.toInteger(false));
    }

    @Test
    void testToIntegerObject() {
        assertEquals(Integer.valueOf(1), BooleanHelper.toIntegerObject(true));
        assertEquals(Integer.valueOf(0), BooleanHelper.toIntegerObject(false));
        assertNull(BooleanHelper.toIntegerObject((Boolean) null));
    }

    @Test
    void testToStringTrueFalse() {
        assertEquals("true", BooleanHelper.toStringTrueFalse(Boolean.TRUE));
        assertEquals("false", BooleanHelper.toStringTrueFalse(Boolean.FALSE));
        assertNull(BooleanHelper.toStringTrueFalse(null));
    }

    @Test
    void testToStringYesNo() {
        assertEquals("yes", BooleanHelper.toStringYesNo(Boolean.TRUE));
        assertEquals("no", BooleanHelper.toStringYesNo(Boolean.FALSE));
        assertNull(BooleanHelper.toStringYesNo(null));
    }

    @Test
    void testToStringOnOff() {
        assertEquals("on", BooleanHelper.toStringOnOff(Boolean.TRUE));
        assertEquals("off", BooleanHelper.toStringOnOff(Boolean.FALSE));
        assertNull(BooleanHelper.toStringOnOff(null));
    }

    @Test
    void testNegate() {
        assertEquals(Boolean.FALSE, BooleanHelper.negate(Boolean.TRUE));
        assertEquals(Boolean.TRUE, BooleanHelper.negate(Boolean.FALSE));
        assertNull(BooleanHelper.negate(null));
    }

    @Test
    void testAnd() {
        assertTrue(BooleanHelper.and(new boolean[]{true, true}));
        assertFalse(BooleanHelper.and(new boolean[]{true, false}));
        assertFalse(BooleanHelper.and(new boolean[]{false, false}));
        assertTrue(BooleanHelper.and(new boolean[]{true, true, true}));
    }

    @Test
    void testAndBoxed() {
        assertEquals(Boolean.TRUE, BooleanHelper.and(new Boolean[]{Boolean.TRUE, Boolean.TRUE}));
        assertEquals(Boolean.FALSE, BooleanHelper.and(new Boolean[]{Boolean.TRUE, Boolean.FALSE}));
    }

    @Test
    void testAndEmpty() {
        assertThrows(IllegalArgumentException.class, () -> BooleanHelper.and(new boolean[0]));
        assertThrows(IllegalArgumentException.class, () -> BooleanHelper.and((boolean[]) null));
    }

    @Test
    void testOr() {
        assertTrue(BooleanHelper.or(new boolean[]{true, false}));
        assertTrue(BooleanHelper.or(new boolean[]{true, true}));
        assertFalse(BooleanHelper.or(new boolean[]{false, false}));
    }

    @Test
    void testOrBoxed() {
        assertEquals(Boolean.TRUE, BooleanHelper.or(new Boolean[]{Boolean.TRUE, Boolean.FALSE}));
        assertEquals(Boolean.FALSE, BooleanHelper.or(new Boolean[]{Boolean.FALSE, Boolean.FALSE}));
    }

    @Test
    void testXor() {
        assertTrue(BooleanHelper.xor(new boolean[]{true, false}));
        assertFalse(BooleanHelper.xor(new boolean[]{true, true}));
        assertFalse(BooleanHelper.xor(new boolean[]{false, false}));
        assertTrue(BooleanHelper.xor(new boolean[]{true, false, false}));
    }

    @Test
    void testXorBoxed() {
        assertEquals(Boolean.TRUE, BooleanHelper.xor(new Boolean[]{Boolean.TRUE, Boolean.FALSE}));
        assertEquals(Boolean.FALSE, BooleanHelper.xor(new Boolean[]{Boolean.TRUE, Boolean.TRUE}));
    }

    @Test
    void testOneHot() {
        assertTrue(BooleanHelper.oneHot(new boolean[]{true, false, false}));
        assertFalse(BooleanHelper.oneHot(new boolean[]{true, true, false}));
        assertFalse(BooleanHelper.oneHot(new boolean[]{false, false, false}));
    }

    @Test
    void testCompare() {
        assertEquals(0, BooleanHelper.compare(true, true));
        assertEquals(0, BooleanHelper.compare(false, false));
        assertTrue(BooleanHelper.compare(true, false) > 0);
        assertTrue(BooleanHelper.compare(false, true) < 0);
    }
}

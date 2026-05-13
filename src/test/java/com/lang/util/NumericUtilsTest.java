package com.lang.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class NumericUtilsTest {

    // --- toInt ---

    @Test
    void testToInt() {
        assertEquals(123, NumericUtils.toInt("123"));
        assertEquals(0, NumericUtils.toInt("abc"));
        assertEquals(0, NumericUtils.toInt(null));
        assertEquals(-42, NumericUtils.toInt("-42"));
    }

    @Test
    void testToIntWithDefault() {
        assertEquals(123, NumericUtils.toInt("123", 5));
        assertEquals(5, NumericUtils.toInt("abc", 5));
        assertEquals(5, NumericUtils.toInt(null, 5));
    }

    // --- toLong ---

    @Test
    void testToLong() {
        assertEquals(123L, NumericUtils.toLong("123"));
        assertEquals(0L, NumericUtils.toLong("abc"));
        assertEquals(0L, NumericUtils.toLong(null));
    }

    @Test
    void testToLongWithDefault() {
        assertEquals(123L, NumericUtils.toLong("123", 5L));
        assertEquals(5L, NumericUtils.toLong("abc", 5L));
    }

    // --- toFloat ---

    @Test
    void testToFloat() {
        assertEquals(1.5f, NumericUtils.toFloat("1.5"));
        assertEquals(0.0f, NumericUtils.toFloat("abc"));
        assertEquals(0.0f, NumericUtils.toFloat(null));
    }

    @Test
    void testToFloatWithDefault() {
        assertEquals(1.5f, NumericUtils.toFloat("1.5", 1.0f));
        assertEquals(1.0f, NumericUtils.toFloat("abc", 1.0f));
    }

    // --- toDouble ---

    @Test
    void testToDouble() {
        assertEquals(1.5, NumericUtils.toDouble("1.5"));
        assertEquals(0.0, NumericUtils.toDouble("abc"));
        assertEquals(0.0, NumericUtils.toDouble(null));
    }

    @Test
    void testToDoubleWithDefault() {
        assertEquals(1.5, NumericUtils.toDouble("1.5", 1.0));
        assertEquals(1.0, NumericUtils.toDouble("abc", 1.0));
    }

    // --- toByte / toShort ---

    @Test
    void testToByte() {
        assertEquals((byte) 1, NumericUtils.toByte("1"));
        assertEquals((byte) 0, NumericUtils.toByte("abc"));
        assertEquals((byte) 5, NumericUtils.toByte("abc", (byte) 5));
    }

    @Test
    void testToShort() {
        assertEquals((short) 1, NumericUtils.toShort("1"));
        assertEquals((short) 0, NumericUtils.toShort("abc"));
        assertEquals((short) 5, NumericUtils.toShort("abc", (short) 5));
    }

    // --- createNumber ---

    @Test
    void testCreateNumberNull() {
        assertNull(NumericUtils.createNumber(null));
    }

    @Test
    void testCreateNumberBlank() {
        assertThrows(NumberFormatException.class, () -> NumericUtils.createNumber("  "));
    }

    @Test
    void testCreateNumberInteger() {
        assertEquals(Integer.valueOf(123), NumericUtils.createNumber("123"));
        assertEquals(Integer.valueOf(-456), NumericUtils.createNumber("-456"));
    }

    @Test
    void testCreateNumberLong() {
        assertEquals(Long.valueOf(123L), NumericUtils.createNumber("123L"));
        assertEquals(Long.valueOf(123L), NumericUtils.createNumber("123l"));
    }

    @Test
    void testCreateNumberHex() {
        assertEquals(Integer.valueOf(255), NumericUtils.createNumber("0xFF"));
        assertEquals(Integer.valueOf(255), NumericUtils.createNumber("0XFF"));
        assertEquals(Integer.valueOf(255), NumericUtils.createNumber("#FF"));
    }

    @Test
    void testCreateNumberNegativeHex() {
        assertEquals(Integer.valueOf(-255), NumericUtils.createNumber("-0xFF"));
    }

    @Test
    void testCreateNumberFloat() {
        final Number result = NumericUtils.createNumber("1.5f");
        assertInstanceOf(Float.class, result);
        assertEquals(1.5f, result.floatValue());
    }

    @Test
    void testCreateNumberDouble() {
        final Number result = NumericUtils.createNumber("1.5");
        assertInstanceOf(Double.class, result);
        assertEquals(1.5, result.doubleValue());
    }

    @Test
    void testCreateNumberBigInteger() {
        final Number result = NumericUtils.createNumber("999999999999999999999");
        assertInstanceOf(BigInteger.class, result);
    }

    @Test
    void testCreateNumberScientific() {
        final Number result = NumericUtils.createNumber("1.5e10");
        assertNotNull(result);
        assertEquals(1.5e10, result.doubleValue(), 1.0);
    }

    @Test
    void testCreateNumberInvalid() {
        assertThrows(NumberFormatException.class, () -> NumericUtils.createNumber("abc"));
    }

    // --- createInteger / createLong / createFloat / createDouble ---

    @Test
    void testCreateInteger() {
        assertNull(NumericUtils.createInteger(null));
        assertEquals(Integer.valueOf(123), NumericUtils.createInteger("123"));
        assertEquals(Integer.valueOf(255), NumericUtils.createInteger("0xFF"));
    }

    @Test
    void testCreateLong() {
        assertNull(NumericUtils.createLong(null));
        assertEquals(Long.valueOf(123L), NumericUtils.createLong("123"));
    }

    @Test
    void testCreateFloat() {
        assertNull(NumericUtils.createFloat(null));
        assertEquals(Float.valueOf(1.5f), NumericUtils.createFloat("1.5"));
    }

    @Test
    void testCreateDouble() {
        assertNull(NumericUtils.createDouble(null));
        assertEquals(Double.valueOf(1.5), NumericUtils.createDouble("1.5"));
    }

    // --- createBigInteger / createBigDecimal ---

    @Test
    void testCreateBigInteger() {
        assertNull(NumericUtils.createBigInteger(null));
        assertEquals(new BigInteger("123"), NumericUtils.createBigInteger("123"));
        assertEquals(new BigInteger("ff", 16), NumericUtils.createBigInteger("0xFF"));
    }

    @Test
    void testCreateBigDecimal() {
        assertNull(NumericUtils.createBigDecimal(null));
        assertEquals(new BigDecimal("123.45"), NumericUtils.createBigDecimal("123.45"));
    }

    @Test
    void testCreateBigDecimalBlank() {
        assertThrows(NumberFormatException.class, () -> NumericUtils.createBigDecimal("  "));
    }

    // --- isCreatable ---

    @Test
    void testIsCreatable() {
        assertTrue(NumericUtils.isCreatable("123"));
        assertTrue(NumericUtils.isCreatable("-123"));
        assertTrue(NumericUtils.isCreatable("0xFF"));
        assertTrue(NumericUtils.isCreatable("1.5e10"));
        assertTrue(NumericUtils.isCreatable("1.5E10"));
        assertTrue(NumericUtils.isCreatable("1.5"));
        assertTrue(NumericUtils.isCreatable("123L"));
        assertTrue(NumericUtils.isCreatable("123l"));
        assertTrue(NumericUtils.isCreatable("1.5f"));
        assertTrue(NumericUtils.isCreatable("1.5d"));
        assertFalse(NumericUtils.isCreatable("abc"));
        assertFalse(NumericUtils.isCreatable(""));
        assertFalse(NumericUtils.isCreatable(null));
        assertFalse(NumericUtils.isCreatable("0x"));
        assertFalse(NumericUtils.isCreatable("12e"));
    }

    // --- isParsable ---

    @Test
    void testIsParsable() {
        assertTrue(NumericUtils.isParsable("123"));
        assertTrue(NumericUtils.isParsable("12.3"));
        assertTrue(NumericUtils.isParsable("-123"));
        assertTrue(NumericUtils.isParsable("-12.3"));
        assertFalse(NumericUtils.isParsable("0xFF"));
        assertFalse(NumericUtils.isParsable("abc"));
        assertFalse(NumericUtils.isParsable(""));
        assertFalse(NumericUtils.isParsable(null));
        assertFalse(NumericUtils.isParsable("12."));
        assertFalse(NumericUtils.isParsable("-"));
        assertFalse(NumericUtils.isParsable("12.3.4"));
    }

    // --- isDigits ---

    @Test
    void testIsDigits() {
        assertTrue(NumericUtils.isDigits("123"));
        assertFalse(NumericUtils.isDigits(""));
        assertFalse(NumericUtils.isDigits(null));
        assertFalse(NumericUtils.isDigits("12.3"));
        assertFalse(NumericUtils.isDigits("abc"));
        assertFalse(NumericUtils.isDigits("12a"));
    }

    // --- min ---

    @Test
    void testMinInt() {
        assertEquals(1, NumericUtils.min(3, 1, 2));
        assertEquals(-5, NumericUtils.min(-5, 0, 5));
    }

    @Test
    void testMinIntVarargs() {
        assertEquals(1, NumericUtils.min(new int[]{5, 3, 1, 4, 2}));
    }

    @Test
    void testMinLong() {
        assertEquals(1L, NumericUtils.min(3L, 1L, 2L));
    }

    @Test
    void testMinLongVarargs() {
        assertEquals(1L, NumericUtils.min(new long[]{5L, 3L, 1L}));
    }

    @Test
    void testMinDouble() {
        assertEquals(1.0, NumericUtils.min(3.0, 1.0, 2.0));
    }

    @Test
    void testMinDoubleVarargs() {
        assertEquals(1.5, NumericUtils.min(new double[]{5.0, 1.5, 3.0}));
    }

    @Test
    void testMinFloat() {
        assertEquals(1.0f, NumericUtils.min(3.0f, 1.0f, 2.0f));
    }

    @Test
    void testMinShort() {
        assertEquals((short) 1, NumericUtils.min(new short[]{3, 1, 2}));
    }

    @Test
    void testMinByte() {
        assertEquals((byte) 1, NumericUtils.min(new byte[]{3, 1, 2}));
    }

    // --- max ---

    @Test
    void testMaxInt() {
        assertEquals(3, NumericUtils.max(3, 1, 2));
    }

    @Test
    void testMaxIntVarargs() {
        assertEquals(5, NumericUtils.max(new int[]{1, 5, 3}));
    }

    @Test
    void testMaxLong() {
        assertEquals(3L, NumericUtils.max(3L, 1L, 2L));
    }

    @Test
    void testMaxLongVarargs() {
        assertEquals(5L, NumericUtils.max(new long[]{1L, 5L, 3L}));
    }

    @Test
    void testMaxDouble() {
        assertEquals(3.0, NumericUtils.max(3.0, 1.0, 2.0));
    }

    @Test
    void testMaxDoubleVarargs() {
        assertEquals(5.0, NumericUtils.max(new double[]{1.0, 5.0, 3.0}));
    }

    @Test
    void testMaxFloat() {
        assertEquals(3.0f, NumericUtils.max(3.0f, 1.0f, 2.0f));
    }

    @Test
    void testMaxShort() {
        assertEquals((short) 3, NumericUtils.max(new short[]{1, 3, 2}));
    }

    @Test
    void testMaxByte() {
        assertEquals((byte) 3, NumericUtils.max(new byte[]{1, 3, 2}));
    }

    // --- min/max empty arrays ---

    @Test
    void testMinEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> NumericUtils.min(new int[]{}));
    }

    @Test
    void testMinNullArray() {
        assertThrows(IllegalArgumentException.class, () -> NumericUtils.min((int[]) null));
    }

    @Test
    void testMaxEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> NumericUtils.max(new int[]{}));
    }

    @Test
    void testMaxNullArray() {
        assertThrows(IllegalArgumentException.class, () -> NumericUtils.max((long[]) null));
    }

    // --- compare ---

    @Test
    void testCompareInt() {
        assertTrue(NumericUtils.compare(1, 2) < 0);
        assertTrue(NumericUtils.compare(2, 1) > 0);
        assertEquals(0, NumericUtils.compare(1, 1));
    }

    @Test
    void testCompareLong() {
        assertTrue(NumericUtils.compare(1L, 2L) < 0);
        assertTrue(NumericUtils.compare(2L, 1L) > 0);
        assertEquals(0, NumericUtils.compare(1L, 1L));
    }

    @Test
    void testCompareShort() {
        assertTrue(NumericUtils.compare((short) 1, (short) 2) < 0);
        assertEquals(0, NumericUtils.compare((short) 1, (short) 1));
    }

    @Test
    void testCompareByte() {
        assertTrue(NumericUtils.compare((byte) 1, (byte) 2) < 0);
        assertEquals(0, NumericUtils.compare((byte) 1, (byte) 1));
    }

    // --- Constants ---

    @Test
    void testConstants() {
        assertEquals(Integer.valueOf(0), NumericUtils.INTEGER_ZERO);
        assertEquals(Integer.valueOf(1), NumericUtils.INTEGER_ONE);
        assertEquals(Integer.valueOf(-1), NumericUtils.INTEGER_MINUS_ONE);
        assertEquals(Long.valueOf(0L), NumericUtils.LONG_ZERO);
        assertEquals(Long.valueOf(1L), NumericUtils.LONG_ONE);
        assertEquals(Double.valueOf(0.0d), NumericUtils.DOUBLE_ZERO);
        assertEquals(Double.valueOf(1.0d), NumericUtils.DOUBLE_ONE);
        assertEquals(Float.valueOf(0.0f), NumericUtils.FLOAT_ZERO);
    }

    // --- toScaledBigDecimal ---

    @Test
    void testToScaledBigDecimal() {
        assertEquals(new BigDecimal("1.50"), NumericUtils.toScaledBigDecimal(new BigDecimal("1.5")));
        assertEquals(BigDecimal.ZERO, NumericUtils.toScaledBigDecimal((BigDecimal) null));
    }

    @Test
    void testToScaledBigDecimalFromDouble() {
        final BigDecimal result = NumericUtils.toScaledBigDecimal(1.5);
        assertEquals(2, result.scale());
        assertEquals(BigDecimal.ZERO, NumericUtils.toScaledBigDecimal((Double) null));
    }

    @Test
    void testToScaledBigDecimalFromString() {
        final BigDecimal result = NumericUtils.toScaledBigDecimal("1.5");
        assertEquals(2, result.scale());
        assertEquals(BigDecimal.ZERO, NumericUtils.toScaledBigDecimal((String) null));
    }

    // --- NaN handling ---

    @Test
    void testMinDoubleNaN() {
        assertTrue(Double.isNaN(NumericUtils.min(new double[]{1.0, Double.NaN, 3.0})));
    }

    @Test
    void testMaxFloatNaN() {
        assertTrue(Float.isNaN(NumericUtils.max(new float[]{1.0f, Float.NaN, 3.0f})));
    }

    // --- Octal parsing in createNumber ---

    @Test
    void testCreateNumberOctal() {
        final Number result = NumericUtils.createNumber("010");
        assertEquals(Integer.valueOf(8), result);
    }
}

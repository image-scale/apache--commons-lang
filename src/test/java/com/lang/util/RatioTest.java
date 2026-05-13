package com.lang.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatioTest {

    @Test
    void testConstants() {
        assertEquals(0, Ratio.ZERO.getNumerator());
        assertEquals(1, Ratio.ZERO.getDenominator());
        assertEquals(1, Ratio.ONE.getNumerator());
        assertEquals(1, Ratio.ONE.getDenominator());
        assertEquals(1, Ratio.ONE_HALF.getNumerator());
        assertEquals(2, Ratio.ONE_HALF.getDenominator());
    }

    @Test
    void testOfBasic() {
        final Ratio r = Ratio.of(3, 4);
        assertEquals(3, r.getNumerator());
        assertEquals(4, r.getDenominator());
    }

    @Test
    void testOfNormalizesSign() {
        final Ratio r = Ratio.of(3, -4);
        assertEquals(-3, r.getNumerator());
        assertEquals(4, r.getDenominator());
    }

    @Test
    void testOfZeroNumerator() {
        assertSame(Ratio.ZERO, Ratio.of(0, 5));
    }

    @Test
    void testOfZeroDenominatorThrows() {
        assertThrows(ArithmeticException.class, () -> Ratio.of(1, 0));
    }

    @Test
    void testOfWholeParts() {
        final Ratio r = Ratio.of(2, 3, 4);
        assertEquals(11, r.getNumerator());
        assertEquals(4, r.getDenominator());
    }

    @Test
    void testOfWholePartsNegative() {
        final Ratio r = Ratio.of(-2, 3, 4);
        assertEquals(-11, r.getNumerator());
        assertEquals(4, r.getDenominator());
    }

    @Test
    void testOfReduced() {
        final Ratio r = Ratio.ofReduced(6, 8);
        assertEquals(3, r.getNumerator());
        assertEquals(4, r.getDenominator());
    }

    @Test
    void testReduce() {
        final Ratio r = Ratio.of(4, 6).reduce();
        assertEquals(2, r.getNumerator());
        assertEquals(3, r.getDenominator());
    }

    @Test
    void testReduceAlreadyReduced() {
        final Ratio r = Ratio.of(3, 4);
        assertSame(r, r.reduce());
    }

    @Test
    void testReduceZero() {
        assertSame(Ratio.ZERO, Ratio.ZERO.reduce());
    }

    @Test
    void testProperWhole() {
        assertEquals(2, Ratio.of(7, 3).getProperWhole());
    }

    @Test
    void testProperNumerator() {
        assertEquals(1, Ratio.of(7, 3).getProperNumerator());
    }

    @Test
    void testAdd() {
        final Ratio result = Ratio.of(1, 4).add(Ratio.of(1, 4));
        assertEquals(1, result.getNumerator());
        assertEquals(2, result.getDenominator());
    }

    @Test
    void testAddDifferentDenominators() {
        final Ratio result = Ratio.of(1, 3).add(Ratio.of(1, 6));
        assertEquals(1, result.getNumerator());
        assertEquals(2, result.getDenominator());
    }

    @Test
    void testAddZero() {
        final Ratio r = Ratio.of(3, 4);
        assertSame(r, r.add(Ratio.ZERO));
        assertSame(r, Ratio.ZERO.add(r));
    }

    @Test
    void testAddNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> Ratio.ONE.add(null));
    }

    @Test
    void testSubtract() {
        final Ratio result = Ratio.of(3, 4).subtract(Ratio.of(1, 4));
        assertEquals(1, result.getNumerator());
        assertEquals(2, result.getDenominator());
    }

    @Test
    void testSubtractNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> Ratio.ONE.subtract(null));
    }

    @Test
    void testMultiplyBy() {
        final Ratio result = Ratio.of(2, 3).multiplyBy(Ratio.of(3, 4));
        assertEquals(1, result.getNumerator());
        assertEquals(2, result.getDenominator());
    }

    @Test
    void testMultiplyByZero() {
        assertSame(Ratio.ZERO, Ratio.of(3, 4).multiplyBy(Ratio.ZERO));
    }

    @Test
    void testMultiplyByNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> Ratio.ONE.multiplyBy(null));
    }

    @Test
    void testDivideBy() {
        final Ratio result = Ratio.of(1, 2).divideBy(Ratio.of(3, 4));
        assertEquals(2, result.getNumerator());
        assertEquals(3, result.getDenominator());
    }

    @Test
    void testDivideByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> Ratio.ONE.divideBy(Ratio.ZERO));
    }

    @Test
    void testDivideByNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> Ratio.ONE.divideBy(null));
    }

    @Test
    void testNegate() {
        final Ratio r = Ratio.of(3, 4).negate();
        assertEquals(-3, r.getNumerator());
        assertEquals(4, r.getDenominator());
    }

    @Test
    void testNegateNegative() {
        final Ratio r = Ratio.of(-3, 4).negate();
        assertEquals(3, r.getNumerator());
        assertEquals(4, r.getDenominator());
    }

    @Test
    void testAbs() {
        assertEquals(3, Ratio.of(-3, 4).abs().getNumerator());
        final Ratio positive = Ratio.of(3, 4);
        assertSame(positive, positive.abs());
    }

    @Test
    void testInvert() {
        final Ratio r = Ratio.of(3, 4).invert();
        assertEquals(4, r.getNumerator());
        assertEquals(3, r.getDenominator());
    }

    @Test
    void testInvertZeroThrows() {
        assertThrows(ArithmeticException.class, () -> Ratio.ZERO.invert());
    }

    @Test
    void testInvertNegative() {
        final Ratio r = Ratio.of(-3, 4).invert();
        assertEquals(-4, r.getNumerator());
        assertEquals(3, r.getDenominator());
    }

    @Test
    void testPow() {
        final Ratio r = Ratio.of(2, 3).pow(2);
        assertEquals(4, r.getNumerator());
        assertEquals(9, r.getDenominator());
    }

    @Test
    void testPowZero() {
        assertEquals(Ratio.ONE, Ratio.of(2, 3).pow(0));
    }

    @Test
    void testPowOne() {
        final Ratio r = Ratio.of(2, 3);
        assertSame(r, r.pow(1));
    }

    @Test
    void testPowNegative() {
        final Ratio r = Ratio.of(2, 3).pow(-1);
        assertEquals(3, r.getNumerator());
        assertEquals(2, r.getDenominator());
    }

    @Test
    void testPowZeroFraction() {
        assertSame(Ratio.ZERO, Ratio.ZERO.pow(5));
    }

    @Test
    void testNumberConversions() {
        final Ratio r = Ratio.of(7, 2);
        assertEquals(3, r.intValue());
        assertEquals(3L, r.longValue());
        assertEquals(3.5f, r.floatValue());
        assertEquals(3.5, r.doubleValue());
    }

    @Test
    void testCompareTo() {
        assertTrue(Ratio.of(1, 3).compareTo(Ratio.of(1, 2)) < 0);
        assertTrue(Ratio.of(1, 2).compareTo(Ratio.of(1, 3)) > 0);
        assertEquals(0, Ratio.of(1, 2).compareTo(Ratio.of(2, 4)));
    }

    @Test
    void testEquals() {
        assertEquals(Ratio.of(3, 4), Ratio.of(3, 4));
        assertNotEquals(Ratio.of(3, 4), Ratio.of(6, 8));
        assertNotEquals(Ratio.of(3, 4), null);
        assertNotEquals(Ratio.of(3, 4), "3/4");
    }

    @Test
    void testEqualsSameRef() {
        final Ratio r = Ratio.of(1, 2);
        assertEquals(r, r);
    }

    @Test
    void testHashCodeConsistent() {
        assertEquals(Ratio.of(3, 4).hashCode(), Ratio.of(3, 4).hashCode());
    }

    @Test
    void testToString() {
        assertEquals("3/4", Ratio.of(3, 4).toString());
    }

    @Test
    void testToProperString() {
        assertEquals("0", Ratio.ZERO.toProperString());
        assertEquals("2 1/3", Ratio.of(7, 3).toProperString());
        assertEquals("3", Ratio.of(3, 1).toProperString());
        assertEquals("1/3", Ratio.of(1, 3).toProperString());
    }

    @Test
    void testToProperStringNegative() {
        assertEquals("-1/3", Ratio.of(-1, 3).toProperString());
        assertEquals("-2 1/3", Ratio.of(-7, 3).toProperString());
    }
}

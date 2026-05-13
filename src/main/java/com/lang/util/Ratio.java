package com.lang.util;

import java.io.Serializable;

public final class Ratio extends Number implements Comparable<Ratio>, Serializable {

    private static final long serialVersionUID = 1L;

    public static final Ratio ZERO = new Ratio(0, 1);
    public static final Ratio ONE = new Ratio(1, 1);
    public static final Ratio ONE_HALF = new Ratio(1, 2);
    public static final Ratio ONE_THIRD = new Ratio(1, 3);
    public static final Ratio TWO_THIRDS = new Ratio(2, 3);
    public static final Ratio ONE_QUARTER = new Ratio(1, 4);
    public static final Ratio THREE_QUARTERS = new Ratio(3, 4);

    private final int numerator;
    private final int denominator;

    private Ratio(final int numerator, final int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Ratio of(final int numerator, final int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator must not be zero");
        }
        if (numerator == 0) {
            return ZERO;
        }
        int num = numerator;
        int den = denominator;
        if (den < 0) {
            if (num == Integer.MIN_VALUE || den == Integer.MIN_VALUE) {
                throw new ArithmeticException("Overflow: cannot negate");
            }
            num = -num;
            den = -den;
        }
        return new Ratio(num, den);
    }

    public static Ratio of(final int whole, final int numerator, final int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator must not be zero");
        }
        if (denominator < 0) {
            throw new ArithmeticException("Denominator must not be negative");
        }
        final long numLong = whole < 0
                ? (long) whole * denominator - numerator
                : (long) whole * denominator + numerator;
        if (numLong < Integer.MIN_VALUE || numLong > Integer.MAX_VALUE) {
            throw new ArithmeticException("Numerator overflow");
        }
        return of((int) numLong, denominator);
    }

    public static Ratio ofReduced(final int numerator, final int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Denominator must not be zero");
        }
        if (numerator == 0) {
            return ZERO;
        }
        int num = numerator;
        int den = denominator;
        if (den < 0) {
            if (num == Integer.MIN_VALUE || den == Integer.MIN_VALUE) {
                throw new ArithmeticException("Overflow: cannot negate");
            }
            num = -num;
            den = -den;
        }
        final int gcd = gcd(Math.abs(num), den);
        return new Ratio(num / gcd, den / gcd);
    }

    public int getNumerator() { return numerator; }

    public int getDenominator() { return denominator; }

    public int getProperWhole() { return numerator / denominator; }

    public int getProperNumerator() { return Math.abs(numerator % denominator); }

    public Ratio reduce() {
        if (numerator == 0) {
            return ZERO;
        }
        final int gcd = gcd(Math.abs(numerator), denominator);
        if (gcd == 1) {
            return this;
        }
        return new Ratio(numerator / gcd, denominator / gcd);
    }

    public Ratio add(final Ratio other) {
        if (other == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (numerator == 0) {
            return other;
        }
        if (other.numerator == 0) {
            return this;
        }
        final int gcd = gcd(denominator, other.denominator);
        final long num = (long) numerator * (other.denominator / gcd)
                + (long) other.numerator * (denominator / gcd);
        final long den = (long) denominator / gcd * other.denominator;
        if (num < Integer.MIN_VALUE || num > Integer.MAX_VALUE
                || den < Integer.MIN_VALUE || den > Integer.MAX_VALUE) {
            throw new ArithmeticException("Overflow in add");
        }
        return ofReduced((int) num, (int) den);
    }

    public Ratio subtract(final Ratio other) {
        if (other == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        return add(other.negate());
    }

    public Ratio multiplyBy(final Ratio other) {
        if (other == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (numerator == 0 || other.numerator == 0) {
            return ZERO;
        }
        final long num = (long) numerator * other.numerator;
        final long den = (long) denominator * other.denominator;
        if (num < Integer.MIN_VALUE || num > Integer.MAX_VALUE
                || den < Integer.MIN_VALUE || den > Integer.MAX_VALUE) {
            throw new ArithmeticException("Overflow in multiply");
        }
        return ofReduced((int) num, (int) den);
    }

    public Ratio divideBy(final Ratio other) {
        if (other == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (other.numerator == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return multiplyBy(other.invert());
    }

    public Ratio negate() {
        if (numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow: cannot negate");
        }
        return new Ratio(-numerator, denominator);
    }

    public Ratio abs() {
        if (numerator >= 0) {
            return this;
        }
        return negate();
    }

    public Ratio invert() {
        if (numerator == 0) {
            throw new ArithmeticException("Cannot invert zero");
        }
        if (numerator < 0) {
            return new Ratio(-denominator, -numerator);
        }
        return new Ratio(denominator, numerator);
    }

    public Ratio pow(final int power) {
        if (power == 0) {
            return ONE;
        }
        if (power == 1) {
            return this;
        }
        if (numerator == 0) {
            return ZERO;
        }
        if (power < 0) {
            return invert().pow(-power);
        }
        final Ratio half = pow(power / 2);
        final Ratio result = half.multiplyBy(half);
        if (power % 2 != 0) {
            return result.multiplyBy(this);
        }
        return result;
    }

    @Override public int intValue() { return numerator / denominator; }
    @Override public long longValue() { return (long) numerator / denominator; }
    @Override public float floatValue() { return (float) numerator / denominator; }
    @Override public double doubleValue() { return (double) numerator / denominator; }

    @Override
    public int compareTo(final Ratio other) {
        if (this == other) {
            return 0;
        }
        final long lhs = (long) numerator * other.denominator;
        final long rhs = (long) other.numerator * denominator;
        return Long.compare(lhs, rhs);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ratio)) {
            return false;
        }
        final Ratio other = (Ratio) obj;
        return numerator == other.numerator && denominator == other.denominator;
    }

    @Override
    public int hashCode() {
        return 37 * (37 * 17 + numerator) + denominator;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    public String toProperString() {
        if (numerator == 0) {
            return "0";
        }
        final int whole = getProperWhole();
        final int properNum = getProperNumerator();
        if (properNum == 0) {
            return String.valueOf(whole);
        }
        if (whole == 0) {
            return (numerator < 0 ? "-" : "") + properNum + "/" + denominator;
        }
        return whole + " " + properNum + "/" + denominator;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            final int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}

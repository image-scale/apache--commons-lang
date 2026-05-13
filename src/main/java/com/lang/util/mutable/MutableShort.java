package com.lang.util.mutable;

public class MutableShort extends Number implements Comparable<MutableShort> {

    private static final long serialVersionUID = 1L;
    private short value;

    public MutableShort() {}

    public MutableShort(final short value) {
        this.value = value;
    }

    public MutableShort(final Number value) {
        this.value = value.shortValue();
    }

    public MutableShort(final String value) {
        this.value = Short.parseShort(value);
    }

    public short get() { return value; }

    public void set(final short value) { this.value = value; }

    public void set(final Number value) { this.value = value.shortValue(); }

    public void increment() { value++; }

    public short incrementAndGet() { return ++value; }

    public short getAndIncrement() { return value++; }

    public void decrement() { value--; }

    public short decrementAndGet() { return --value; }

    public short getAndDecrement() { return value--; }

    public void add(final short operand) { value += operand; }

    public void add(final Number operand) { value += operand.shortValue(); }

    public void subtract(final short operand) { value -= operand; }

    public void subtract(final Number operand) { value -= operand.shortValue(); }

    @Override public int intValue() { return value; }
    @Override public long longValue() { return value; }
    @Override public float floatValue() { return value; }
    @Override public double doubleValue() { return value; }

    public Short toShort() { return Short.valueOf(value); }

    @Override
    public int compareTo(final MutableShort other) {
        return Short.compare(value, other.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableShort) {
            return value == ((MutableShort) obj).shortValue();
        }
        return false;
    }

    @Override public int hashCode() { return value; }

    @Override public String toString() { return String.valueOf(value); }
}

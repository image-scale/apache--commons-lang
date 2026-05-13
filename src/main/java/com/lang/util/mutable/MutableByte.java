package com.lang.util.mutable;

public class MutableByte extends Number implements Comparable<MutableByte> {

    private static final long serialVersionUID = 1L;
    private byte value;

    public MutableByte() {}

    public MutableByte(final byte value) {
        this.value = value;
    }

    public MutableByte(final Number value) {
        this.value = value.byteValue();
    }

    public MutableByte(final String value) {
        this.value = Byte.parseByte(value);
    }

    public byte get() { return value; }

    public void set(final byte value) { this.value = value; }

    public void set(final Number value) { this.value = value.byteValue(); }

    public void increment() { value++; }

    public byte incrementAndGet() { return ++value; }

    public byte getAndIncrement() { return value++; }

    public void decrement() { value--; }

    public byte decrementAndGet() { return --value; }

    public byte getAndDecrement() { return value--; }

    public void add(final byte operand) { value += operand; }

    public void add(final Number operand) { value += operand.byteValue(); }

    public void subtract(final byte operand) { value -= operand; }

    public void subtract(final Number operand) { value -= operand.byteValue(); }

    @Override public int intValue() { return value; }
    @Override public long longValue() { return value; }
    @Override public float floatValue() { return value; }
    @Override public double doubleValue() { return value; }

    public Byte toByte() { return Byte.valueOf(value); }

    @Override
    public int compareTo(final MutableByte other) {
        return Byte.compare(value, other.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableByte) {
            return value == ((MutableByte) obj).byteValue();
        }
        return false;
    }

    @Override public int hashCode() { return value; }

    @Override public String toString() { return String.valueOf(value); }
}

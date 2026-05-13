package com.lang.util.mutable;

public class MutableInt extends Number implements Comparable<MutableInt> {

    private static final long serialVersionUID = 1L;
    private int value;

    public MutableInt() {}

    public MutableInt(final int value) {
        this.value = value;
    }

    public MutableInt(final Number value) {
        this.value = value.intValue();
    }

    public MutableInt(final String value) {
        this.value = Integer.parseInt(value);
    }

    public int get() { return value; }

    public void set(final int value) { this.value = value; }

    public void set(final Number value) { this.value = value.intValue(); }

    public void increment() { value++; }

    public int incrementAndGet() { return ++value; }

    public int getAndIncrement() { return value++; }

    public void decrement() { value--; }

    public int decrementAndGet() { return --value; }

    public int getAndDecrement() { return value--; }

    public void add(final int operand) { value += operand; }

    public void add(final Number operand) { value += operand.intValue(); }

    public int addAndGet(final int operand) { value += operand; return value; }

    public int addAndGet(final Number operand) { value += operand.intValue(); return value; }

    public int getAndAdd(final int operand) { final int old = value; value += operand; return old; }

    public int getAndAdd(final Number operand) { final int old = value; value += operand.intValue(); return old; }

    public void subtract(final int operand) { value -= operand; }

    public void subtract(final Number operand) { value -= operand.intValue(); }

    @Override public int intValue() { return value; }
    @Override public long longValue() { return value; }
    @Override public float floatValue() { return value; }
    @Override public double doubleValue() { return value; }

    public Integer toInteger() { return Integer.valueOf(value); }

    @Override
    public int compareTo(final MutableInt other) {
        return Integer.compare(value, other.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableInt) {
            return value == ((MutableInt) obj).intValue();
        }
        return false;
    }

    @Override public int hashCode() { return value; }

    @Override public String toString() { return String.valueOf(value); }
}

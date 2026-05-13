package com.lang.util.mutable;

public class MutableDouble extends Number implements Comparable<MutableDouble> {

    private static final long serialVersionUID = 1L;
    private double value;

    public MutableDouble() {}

    public MutableDouble(final double value) {
        this.value = value;
    }

    public MutableDouble(final Number value) {
        this.value = value.doubleValue();
    }

    public MutableDouble(final String value) {
        this.value = Double.parseDouble(value);
    }

    public double get() { return value; }

    public void set(final double value) { this.value = value; }

    public void set(final Number value) { this.value = value.doubleValue(); }

    public void increment() { value++; }

    public double incrementAndGet() { return ++value; }

    public double getAndIncrement() { return value++; }

    public void decrement() { value--; }

    public double decrementAndGet() { return --value; }

    public double getAndDecrement() { return value--; }

    public void add(final double operand) { value += operand; }

    public void add(final Number operand) { value += operand.doubleValue(); }

    public double addAndGet(final double operand) { value += operand; return value; }

    public double addAndGet(final Number operand) { value += operand.doubleValue(); return value; }

    public double getAndAdd(final double operand) { final double old = value; value += operand; return old; }

    public double getAndAdd(final Number operand) { final double old = value; value += operand.doubleValue(); return old; }

    public void subtract(final double operand) { value -= operand; }

    public void subtract(final Number operand) { value -= operand.doubleValue(); }

    @Override public int intValue() { return (int) value; }
    @Override public long longValue() { return (long) value; }
    @Override public float floatValue() { return (float) value; }
    @Override public double doubleValue() { return value; }

    public Double toDouble() { return Double.valueOf(value); }

    @Override
    public int compareTo(final MutableDouble other) {
        return Double.compare(value, other.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableDouble) {
            return Double.doubleToLongBits(value) == Double.doubleToLongBits(((MutableDouble) obj).doubleValue());
        }
        return false;
    }

    @Override public int hashCode() { return Double.hashCode(value); }

    @Override public String toString() { return String.valueOf(value); }
}

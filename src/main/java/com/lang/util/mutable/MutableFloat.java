package com.lang.util.mutable;

public class MutableFloat extends Number implements Comparable<MutableFloat> {

    private static final long serialVersionUID = 1L;
    private float value;

    public MutableFloat() {}

    public MutableFloat(final float value) {
        this.value = value;
    }

    public MutableFloat(final Number value) {
        this.value = value.floatValue();
    }

    public MutableFloat(final String value) {
        this.value = Float.parseFloat(value);
    }

    public float get() { return value; }

    public void set(final float value) { this.value = value; }

    public void set(final Number value) { this.value = value.floatValue(); }

    public void increment() { value++; }

    public float incrementAndGet() { return ++value; }

    public float getAndIncrement() { return value++; }

    public void decrement() { value--; }

    public float decrementAndGet() { return --value; }

    public float getAndDecrement() { return value--; }

    public void add(final float operand) { value += operand; }

    public void add(final Number operand) { value += operand.floatValue(); }

    public float addAndGet(final float operand) { value += operand; return value; }

    public float addAndGet(final Number operand) { value += operand.floatValue(); return value; }

    public float getAndAdd(final float operand) { final float old = value; value += operand; return old; }

    public float getAndAdd(final Number operand) { final float old = value; value += operand.floatValue(); return old; }

    public void subtract(final float operand) { value -= operand; }

    public void subtract(final Number operand) { value -= operand.floatValue(); }

    @Override public int intValue() { return (int) value; }
    @Override public long longValue() { return (long) value; }
    @Override public float floatValue() { return value; }
    @Override public double doubleValue() { return value; }

    public Float toFloat() { return Float.valueOf(value); }

    @Override
    public int compareTo(final MutableFloat other) {
        return Float.compare(value, other.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableFloat) {
            return Float.floatToIntBits(value) == Float.floatToIntBits(((MutableFloat) obj).floatValue());
        }
        return false;
    }

    @Override public int hashCode() { return Float.hashCode(value); }

    @Override public String toString() { return String.valueOf(value); }
}

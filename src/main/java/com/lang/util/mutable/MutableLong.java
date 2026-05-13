package com.lang.util.mutable;

public class MutableLong extends Number implements Comparable<MutableLong> {

    private static final long serialVersionUID = 1L;
    private long value;

    public MutableLong() {}

    public MutableLong(final long value) {
        this.value = value;
    }

    public MutableLong(final Number value) {
        this.value = value.longValue();
    }

    public MutableLong(final String value) {
        this.value = Long.parseLong(value);
    }

    public long get() { return value; }

    public void set(final long value) { this.value = value; }

    public void set(final Number value) { this.value = value.longValue(); }

    public void increment() { value++; }

    public long incrementAndGet() { return ++value; }

    public long getAndIncrement() { return value++; }

    public void decrement() { value--; }

    public long decrementAndGet() { return --value; }

    public long getAndDecrement() { return value--; }

    public void add(final long operand) { value += operand; }

    public void add(final Number operand) { value += operand.longValue(); }

    public long addAndGet(final long operand) { value += operand; return value; }

    public long addAndGet(final Number operand) { value += operand.longValue(); return value; }

    public long getAndAdd(final long operand) { final long old = value; value += operand; return old; }

    public long getAndAdd(final Number operand) { final long old = value; value += operand.longValue(); return old; }

    public void subtract(final long operand) { value -= operand; }

    public void subtract(final Number operand) { value -= operand.longValue(); }

    @Override public int intValue() { return (int) value; }
    @Override public long longValue() { return value; }
    @Override public float floatValue() { return value; }
    @Override public double doubleValue() { return value; }

    public Long toLong() { return Long.valueOf(value); }

    @Override
    public int compareTo(final MutableLong other) {
        return Long.compare(value, other.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableLong) {
            return value == ((MutableLong) obj).longValue();
        }
        return false;
    }

    @Override public int hashCode() { return Long.hashCode(value); }

    @Override public String toString() { return String.valueOf(value); }
}

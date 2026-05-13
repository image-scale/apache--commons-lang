package com.lang.util.mutable;

public class MutableBoolean implements Comparable<MutableBoolean> {

    private boolean value;

    public MutableBoolean() {}

    public MutableBoolean(final boolean value) {
        this.value = value;
    }

    public MutableBoolean(final Boolean value) {
        this.value = value.booleanValue();
    }

    public boolean get() { return value; }

    public void set(final boolean value) { this.value = value; }

    public void setTrue() { this.value = true; }

    public void setFalse() { this.value = false; }

    public boolean isTrue() { return value; }

    public boolean isFalse() { return !value; }

    public Boolean toBoolean() { return Boolean.valueOf(value); }

    @Override
    public int compareTo(final MutableBoolean other) {
        return Boolean.compare(value, other.value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableBoolean) {
            return value == ((MutableBoolean) obj).value;
        }
        return false;
    }

    @Override public int hashCode() { return Boolean.hashCode(value); }

    @Override public String toString() { return String.valueOf(value); }
}

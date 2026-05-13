package com.lang.util.mutable;

public class MutableObject<T> {

    private T value;

    public MutableObject() {}

    public MutableObject(final T value) {
        this.value = value;
    }

    public T get() { return value; }

    public void set(final T value) { this.value = value; }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MutableObject<?>) {
            final Object other = ((MutableObject<?>) obj).value;
            return value == other || (value != null && value.equals(other));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }

    @Override
    public String toString() {
        return value == null ? "null" : value.toString();
    }
}

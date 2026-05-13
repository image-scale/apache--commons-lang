package com.lang.util.concurrent;

import java.util.concurrent.atomic.AtomicLong;

public class ThresholdCircuitBreaker {

    public enum State {
        CLOSED,
        OPEN
    }

    private final long threshold;
    private final AtomicLong used = new AtomicLong(0);
    private volatile State state = State.CLOSED;

    public ThresholdCircuitBreaker(final long threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
        this.threshold = threshold;
    }

    public boolean incrementAndCheckState(final long increment) {
        if (state == State.OPEN) {
            return false;
        }
        final long newValue = used.addAndGet(increment);
        if (newValue > threshold) {
            open();
            return false;
        }
        return true;
    }

    public boolean checkState() {
        return state == State.CLOSED;
    }

    public void open() {
        state = State.OPEN;
    }

    public void close() {
        state = State.CLOSED;
        used.set(0);
    }

    public boolean isOpen() {
        return state == State.OPEN;
    }

    public boolean isClosed() {
        return state == State.CLOSED;
    }

    public State getState() {
        return state;
    }

    public long getThreshold() {
        return threshold;
    }

    public long getUsed() {
        return used.get();
    }
}

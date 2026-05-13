package com.lang.util;

import java.util.concurrent.TimeUnit;

public final class StopWatch {

    public enum State {
        UNSTARTED,
        RUNNING,
        STOPPED,
        SUSPENDED
    }

    private State state = State.UNSTARTED;
    private long startTimeNanos;
    private long stopTimeNanos;
    private long suspendedDurationNanos;
    private long suspendStartNanos;
    private long splitTimeNanos;
    private boolean isSplit;

    public StopWatch() {}

    public static StopWatch createStarted() {
        final StopWatch sw = new StopWatch();
        sw.start();
        return sw;
    }

    public void start() {
        if (state == State.UNSTARTED) {
            state = State.RUNNING;
            startTimeNanos = System.nanoTime();
            suspendedDurationNanos = 0;
            isSplit = false;
        } else {
            throw new IllegalStateException("StopWatch is already started");
        }
    }

    public void stop() {
        if (state == State.RUNNING) {
            stopTimeNanos = System.nanoTime();
            state = State.STOPPED;
        } else if (state == State.SUSPENDED) {
            stopTimeNanos = suspendStartNanos;
            state = State.STOPPED;
        } else {
            throw new IllegalStateException("StopWatch is not running");
        }
    }

    public void reset() {
        state = State.UNSTARTED;
        startTimeNanos = 0;
        stopTimeNanos = 0;
        suspendedDurationNanos = 0;
        suspendStartNanos = 0;
        splitTimeNanos = 0;
        isSplit = false;
    }

    public void suspend() {
        if (state != State.RUNNING) {
            throw new IllegalStateException("StopWatch must be running to suspend");
        }
        suspendStartNanos = System.nanoTime();
        state = State.SUSPENDED;
    }

    public void resume() {
        if (state != State.SUSPENDED) {
            throw new IllegalStateException("StopWatch must be suspended to resume");
        }
        suspendedDurationNanos += System.nanoTime() - suspendStartNanos;
        state = State.RUNNING;
    }

    public void split() {
        if (state != State.RUNNING) {
            throw new IllegalStateException("StopWatch must be running to split");
        }
        splitTimeNanos = System.nanoTime();
        isSplit = true;
    }

    public void unsplit() {
        if (!isSplit) {
            throw new IllegalStateException("StopWatch has not been split");
        }
        isSplit = false;
    }

    public long getNanoTime() {
        switch (state) {
            case STOPPED:
                return stopTimeNanos - startTimeNanos - suspendedDurationNanos;
            case RUNNING:
                return System.nanoTime() - startTimeNanos - suspendedDurationNanos;
            case SUSPENDED:
                return suspendStartNanos - startTimeNanos - suspendedDurationNanos;
            default:
                return 0;
        }
    }

    public long getTime() {
        return TimeUnit.NANOSECONDS.toMillis(getNanoTime());
    }

    public long getTime(final TimeUnit timeUnit) {
        return timeUnit.convert(getNanoTime(), TimeUnit.NANOSECONDS);
    }

    public long getSplitNanoTime() {
        if (!isSplit) {
            throw new IllegalStateException("StopWatch must be split to get split time");
        }
        return splitTimeNanos - startTimeNanos - suspendedDurationNanos;
    }

    public long getSplitTime() {
        return TimeUnit.NANOSECONDS.toMillis(getSplitNanoTime());
    }

    public long getStartTime() {
        if (state == State.UNSTARTED) {
            throw new IllegalStateException("StopWatch has not been started");
        }
        return TimeUnit.NANOSECONDS.toMillis(startTimeNanos);
    }

    public boolean isStarted() {
        return state != State.UNSTARTED;
    }

    public boolean isRunning() {
        return state == State.RUNNING;
    }

    public boolean isStopped() {
        return state == State.STOPPED;
    }

    public boolean isSuspended() {
        return state == State.SUSPENDED;
    }

    public State getState() {
        return state;
    }

    @Override
    public String toString() {
        final long nanos = getNanoTime();
        final long hours = TimeUnit.NANOSECONDS.toHours(nanos);
        final long minutes = TimeUnit.NANOSECONDS.toMinutes(nanos) % 60;
        final long seconds = TimeUnit.NANOSECONDS.toSeconds(nanos) % 60;
        final long millis = TimeUnit.NANOSECONDS.toMillis(nanos) % 1000;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
    }
}

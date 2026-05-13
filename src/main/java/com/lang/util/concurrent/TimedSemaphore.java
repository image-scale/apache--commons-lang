package com.lang.util.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedSemaphore {

    private final long period;
    private final TimeUnit unit;
    private final int limit;
    private final ScheduledExecutorService executor;
    private final boolean ownExecutor;

    private int acquireCount;
    private int totalAcquireCount;
    private int periodCount;
    private long lastPeriodEnd;
    private boolean shutdown;

    public TimedSemaphore(final long timePeriod, final TimeUnit timeUnit, final int limit) {
        this(null, timePeriod, timeUnit, limit);
    }

    public TimedSemaphore(final ScheduledExecutorService service, final long timePeriod,
                          final TimeUnit timeUnit, final int limit) {
        if (timePeriod <= 0) {
            throw new IllegalArgumentException("Time period must be positive");
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("Time unit must not be null");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be positive");
        }
        this.period = timePeriod;
        this.unit = timeUnit;
        this.limit = limit;
        if (service != null) {
            this.executor = service;
            this.ownExecutor = false;
        } else {
            this.executor = Executors.newScheduledThreadPool(1);
            this.ownExecutor = true;
        }
        this.lastPeriodEnd = System.nanoTime();
        startTimer();
    }

    private void startTimer() {
        executor.scheduleAtFixedRate(this::endOfPeriod, period, period, unit);
    }

    synchronized void endOfPeriod() {
        lastPeriodEnd = System.nanoTime();
        periodCount++;
        acquireCount = 0;
        notifyAll();
    }

    public synchronized void acquire() throws InterruptedException {
        if (shutdown) {
            throw new IllegalStateException("TimedSemaphore is shut down");
        }
        while (acquireCount >= limit) {
            wait();
        }
        acquireCount++;
        totalAcquireCount++;
    }

    public synchronized boolean tryAcquire() {
        if (shutdown) {
            throw new IllegalStateException("TimedSemaphore is shut down");
        }
        if (acquireCount < limit) {
            acquireCount++;
            totalAcquireCount++;
            return true;
        }
        return false;
    }

    public synchronized int getAcquireCount() {
        return acquireCount;
    }

    public synchronized int getTotalAcquireCount() {
        return totalAcquireCount;
    }

    public synchronized int getAvailablePermits() {
        return limit - acquireCount;
    }

    public int getLimit() {
        return limit;
    }

    public long getPeriod() {
        return period;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public synchronized int getPeriodCount() {
        return periodCount;
    }

    public synchronized boolean isShutdown() {
        return shutdown;
    }

    public synchronized void shutdown() {
        if (!shutdown) {
            shutdown = true;
            if (ownExecutor) {
                executor.shutdownNow();
            }
            notifyAll();
        }
    }
}

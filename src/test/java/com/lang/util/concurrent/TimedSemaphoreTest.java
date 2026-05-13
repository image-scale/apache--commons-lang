package com.lang.util.concurrent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class TimedSemaphoreTest {

    private TimedSemaphore semaphore;

    @AfterEach
    void tearDown() {
        if (semaphore != null) {
            semaphore.shutdown();
        }
    }

    @Test
    void testBasicProperties() {
        semaphore = new TimedSemaphore(1, TimeUnit.SECONDS, 5);
        assertEquals(1, semaphore.getPeriod());
        assertEquals(TimeUnit.SECONDS, semaphore.getUnit());
        assertEquals(5, semaphore.getLimit());
        assertEquals(0, semaphore.getAcquireCount());
        assertEquals(5, semaphore.getAvailablePermits());
        assertFalse(semaphore.isShutdown());
    }

    @Test
    void testTryAcquire() {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 3);
        assertTrue(semaphore.tryAcquire());
        assertTrue(semaphore.tryAcquire());
        assertTrue(semaphore.tryAcquire());
        assertFalse(semaphore.tryAcquire());
        assertEquals(3, semaphore.getAcquireCount());
        assertEquals(0, semaphore.getAvailablePermits());
    }

    @Test
    void testTotalAcquireCount() {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 10);
        semaphore.tryAcquire();
        semaphore.tryAcquire();
        semaphore.tryAcquire();
        assertEquals(3, semaphore.getTotalAcquireCount());
    }

    @Test
    void testEndOfPeriodResetsCount() {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 2);
        assertTrue(semaphore.tryAcquire());
        assertTrue(semaphore.tryAcquire());
        assertFalse(semaphore.tryAcquire());
        semaphore.endOfPeriod();
        assertEquals(0, semaphore.getAcquireCount());
        assertEquals(2, semaphore.getAvailablePermits());
        assertEquals(1, semaphore.getPeriodCount());
        assertTrue(semaphore.tryAcquire());
        assertEquals(3, semaphore.getTotalAcquireCount());
    }

    @Test
    void testAcquireBlocksUntilPeriodEnd() throws Exception {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 1);
        semaphore.tryAcquire();

        final AtomicInteger acquired = new AtomicInteger(0);
        final CountDownLatch started = new CountDownLatch(1);
        final Thread waiter = new Thread(() -> {
            started.countDown();
            try {
                semaphore.acquire();
                acquired.set(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        waiter.start();
        started.await();
        Thread.sleep(50);
        assertEquals(0, acquired.get());

        semaphore.endOfPeriod();
        waiter.join(2000);
        assertEquals(1, acquired.get());
    }

    @Test
    void testShutdown() {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 5);
        assertFalse(semaphore.isShutdown());
        semaphore.shutdown();
        assertTrue(semaphore.isShutdown());
    }

    @Test
    void testTryAcquireAfterShutdownThrows() {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 5);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, () -> semaphore.tryAcquire());
    }

    @Test
    void testAcquireAfterShutdownThrows() {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 5);
        semaphore.shutdown();
        assertThrows(IllegalStateException.class, () -> semaphore.acquire());
    }

    @Test
    void testCustomExecutor() {
        final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        try {
            semaphore = new TimedSemaphore(exec, 1, TimeUnit.HOURS, 3);
            assertTrue(semaphore.tryAcquire());
            semaphore.shutdown();
            assertFalse(exec.isShutdown());
        } finally {
            exec.shutdownNow();
        }
    }

    @Test
    void testInvalidPeriod() {
        assertThrows(IllegalArgumentException.class,
                () -> new TimedSemaphore(0, TimeUnit.SECONDS, 5));
        assertThrows(IllegalArgumentException.class,
                () -> new TimedSemaphore(-1, TimeUnit.SECONDS, 5));
    }

    @Test
    void testNullTimeUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new TimedSemaphore(1, null, 5));
    }

    @Test
    void testInvalidLimit() {
        assertThrows(IllegalArgumentException.class,
                () -> new TimedSemaphore(1, TimeUnit.SECONDS, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new TimedSemaphore(1, TimeUnit.SECONDS, -1));
    }

    @Test
    void testMultiplePeriods() {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 2);
        semaphore.tryAcquire();
        semaphore.tryAcquire();
        semaphore.endOfPeriod();
        semaphore.tryAcquire();
        semaphore.endOfPeriod();
        assertEquals(2, semaphore.getPeriodCount());
        assertEquals(3, semaphore.getTotalAcquireCount());
        assertEquals(0, semaphore.getAcquireCount());
    }

    @Test
    void testDoubleShutdownIsSafe() {
        semaphore = new TimedSemaphore(1, TimeUnit.HOURS, 5);
        semaphore.shutdown();
        semaphore.shutdown();
        assertTrue(semaphore.isShutdown());
    }
}

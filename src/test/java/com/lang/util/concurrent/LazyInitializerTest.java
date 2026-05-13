package com.lang.util.concurrent;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class LazyInitializerTest {

    @Test
    void testLazyInit() {
        final LazyInitializer<String> lazy = new LazyInitializer<>(() -> "hello");
        assertFalse(lazy.isInitialized());
        assertEquals("hello", lazy.get());
        assertTrue(lazy.isInitialized());
    }

    @Test
    void testCalledOnce() {
        final AtomicInteger counter = new AtomicInteger(0);
        final LazyInitializer<Integer> lazy = new LazyInitializer<>(() -> counter.incrementAndGet());
        assertEquals(1, lazy.get());
        assertEquals(1, lazy.get());
        assertEquals(1, lazy.get());
        assertEquals(1, counter.get());
    }

    @Test
    void testNullFactory() {
        assertThrows(IllegalArgumentException.class, () -> new LazyInitializer<>(null));
    }

    @Test
    void testNullValue() {
        final LazyInitializer<String> lazy = new LazyInitializer<>(() -> null);
        assertNull(lazy.get());
        assertTrue(lazy.isInitialized());
    }

    @Test
    void testThreadSafety() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        final LazyInitializer<Integer> lazy = new LazyInitializer<>(() -> counter.incrementAndGet());
        final int threads = 20;
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(threads);
        final Set<Integer> results = Collections.newSetFromMap(new ConcurrentHashMap<>());
        final ExecutorService exec = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            exec.submit(() -> {
                try {
                    start.await();
                    results.add(lazy.get());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }
        start.countDown();
        done.await();
        exec.shutdown();
        assertEquals(1, results.size());
        assertEquals(1, counter.get());
    }
}

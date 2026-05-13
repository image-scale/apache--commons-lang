package com.lang.util.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MemoizerTest {

    @Test
    void testCompute() {
        final Memoizer<String, Integer> memo = new Memoizer<>(String::length);
        assertEquals(5, memo.compute("hello"));
        assertEquals(3, memo.compute("abc"));
    }

    @Test
    void testCaching() {
        final AtomicInteger callCount = new AtomicInteger(0);
        final Memoizer<String, Integer> memo = new Memoizer<>(k -> {
            callCount.incrementAndGet();
            return k.length();
        });
        assertEquals(5, memo.compute("hello"));
        assertEquals(5, memo.compute("hello"));
        assertEquals(5, memo.compute("hello"));
        assertEquals(1, callCount.get());
    }

    @Test
    void testContains() {
        final Memoizer<String, Integer> memo = new Memoizer<>(String::length);
        assertFalse(memo.contains("key"));
        memo.compute("key");
        assertTrue(memo.contains("key"));
    }

    @Test
    void testGetIfCached() {
        final Memoizer<String, Integer> memo = new Memoizer<>(String::length);
        assertNull(memo.getIfCached("key"));
        memo.compute("key");
        assertEquals(3, memo.getIfCached("key"));
    }

    @Test
    void testInvalidate() {
        final AtomicInteger calls = new AtomicInteger(0);
        final Memoizer<String, Integer> memo = new Memoizer<>(k -> {
            calls.incrementAndGet();
            return k.length();
        });
        memo.compute("test");
        assertEquals(1, calls.get());
        memo.invalidate("test");
        assertFalse(memo.contains("test"));
        memo.compute("test");
        assertEquals(2, calls.get());
    }

    @Test
    void testInvalidateAll() {
        final Memoizer<String, Integer> memo = new Memoizer<>(String::length);
        memo.compute("a");
        memo.compute("bb");
        assertEquals(2, memo.size());
        memo.invalidateAll();
        assertEquals(0, memo.size());
    }

    @Test
    void testSize() {
        final Memoizer<Integer, Integer> memo = new Memoizer<>(k -> k * 2);
        assertEquals(0, memo.size());
        memo.compute(1);
        memo.compute(2);
        memo.compute(3);
        assertEquals(3, memo.size());
    }

    @Test
    void testNullFunction() {
        assertThrows(IllegalArgumentException.class, () -> new Memoizer<>(null));
    }

    @Test
    void testConcurrentAccess() throws Exception {
        final AtomicInteger calls = new AtomicInteger(0);
        final Memoizer<String, Integer> memo = new Memoizer<>(k -> {
            calls.incrementAndGet();
            return k.length();
        });
        final int threads = 20;
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(threads);
        final ExecutorService exec = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            exec.submit(() -> {
                try {
                    start.await();
                    memo.compute("concurrent");
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
        assertEquals(10, memo.compute("concurrent"));
    }
}

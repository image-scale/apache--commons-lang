package com.lang.util.concurrent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThresholdCircuitBreakerTest {

    @Test
    void testInitialState() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(100);
        assertTrue(cb.isClosed());
        assertFalse(cb.isOpen());
        assertEquals(ThresholdCircuitBreaker.State.CLOSED, cb.getState());
        assertEquals(100, cb.getThreshold());
        assertEquals(0, cb.getUsed());
    }

    @Test
    void testIncrementBelowThreshold() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(100);
        assertTrue(cb.incrementAndCheckState(50));
        assertTrue(cb.isClosed());
        assertEquals(50, cb.getUsed());
    }

    @Test
    void testIncrementAtThreshold() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(100);
        assertTrue(cb.incrementAndCheckState(100));
        assertTrue(cb.isClosed());
    }

    @Test
    void testIncrementAboveThreshold() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(100);
        assertFalse(cb.incrementAndCheckState(101));
        assertTrue(cb.isOpen());
        assertEquals(ThresholdCircuitBreaker.State.OPEN, cb.getState());
    }

    @Test
    void testMultipleIncrements() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(100);
        assertTrue(cb.incrementAndCheckState(30));
        assertTrue(cb.incrementAndCheckState(30));
        assertTrue(cb.incrementAndCheckState(30));
        assertFalse(cb.incrementAndCheckState(20));
        assertTrue(cb.isOpen());
    }

    @Test
    void testOpenRejectsIncrements() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(10);
        assertFalse(cb.incrementAndCheckState(20));
        assertFalse(cb.incrementAndCheckState(1));
    }

    @Test
    void testCheckState() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(100);
        assertTrue(cb.checkState());
        cb.open();
        assertFalse(cb.checkState());
    }

    @Test
    void testOpenAndClose() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(100);
        cb.incrementAndCheckState(50);
        cb.open();
        assertTrue(cb.isOpen());
        assertFalse(cb.isClosed());
        cb.close();
        assertTrue(cb.isClosed());
        assertEquals(0, cb.getUsed());
    }

    @Test
    void testCloseResetsUsed() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(100);
        cb.incrementAndCheckState(150);
        assertTrue(cb.isOpen());
        cb.close();
        assertTrue(cb.isClosed());
        assertEquals(0, cb.getUsed());
        assertTrue(cb.incrementAndCheckState(50));
    }

    @Test
    void testNegativeThresholdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ThresholdCircuitBreaker(-1));
    }

    @Test
    void testZeroThreshold() {
        final ThresholdCircuitBreaker cb = new ThresholdCircuitBreaker(0);
        assertTrue(cb.incrementAndCheckState(0));
        assertFalse(cb.incrementAndCheckState(1));
    }
}

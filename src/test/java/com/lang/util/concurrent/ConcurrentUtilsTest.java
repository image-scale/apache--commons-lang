package com.lang.util.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentUtilsTest {

    @Test
    void testConstantFuture() throws Exception {
        final Future<String> future = ConcurrentUtils.constantFuture("hello");
        assertTrue(future.isDone());
        assertFalse(future.isCancelled());
        assertFalse(future.cancel(true));
        assertEquals("hello", future.get(1, TimeUnit.SECONDS));
    }

    @Test
    void testConstantFutureNull() throws Exception {
        final Future<String> future = ConcurrentUtils.constantFuture(null);
        assertTrue(future.isDone());
        assertNull(future.get(1, TimeUnit.SECONDS));
    }

    @Test
    void testExtractResult() throws Exception {
        final Future<Integer> future = ConcurrentUtils.constantFuture(42);
        assertEquals(42, ConcurrentUtils.extractResult(future));
    }

    @Test
    void testExtractResultUnchecked() {
        final Future<Integer> future = ConcurrentUtils.constantFuture(42);
        assertEquals(42, ConcurrentUtils.extractResultUnchecked(future));
    }

    @Test
    void testHandleCauseWithException() {
        final ExecutionException ee = new ExecutionException(new RuntimeException("test"));
        assertThrows(ConcurrentUtils.ConcurrentException.class,
                () -> ConcurrentUtils.handleCause(ee));
    }

    @Test
    void testHandleCauseNull() throws Exception {
        ConcurrentUtils.handleCause(null);
    }

    @Test
    void testHandleCauseUnchecked() {
        final ExecutionException ee = new ExecutionException(new RuntimeException("test"));
        assertThrows(ConcurrentUtils.ConcurrentRuntimeException.class,
                () -> ConcurrentUtils.handleCauseUnchecked(ee));
    }

    @Test
    void testHandleCauseUncheckedNull() {
        ConcurrentUtils.handleCauseUnchecked(null);
    }

    @Test
    void testConcurrentExceptionMessage() {
        final Throwable cause = new RuntimeException("root");
        final ConcurrentUtils.ConcurrentException ex =
                new ConcurrentUtils.ConcurrentException("msg", cause);
        assertEquals("msg", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void testConcurrentRuntimeExceptionMessage() {
        final Throwable cause = new RuntimeException("root");
        final ConcurrentUtils.ConcurrentRuntimeException ex =
                new ConcurrentUtils.ConcurrentRuntimeException("msg", cause);
        assertEquals("msg", ex.getMessage());
        assertSame(cause, ex.getCause());
    }
}

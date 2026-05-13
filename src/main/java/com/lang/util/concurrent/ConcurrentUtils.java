package com.lang.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class ConcurrentUtils {

    private ConcurrentUtils() {}

    public static <T> T extractResult(final Future<T> future) throws ConcurrentException {
        try {
            return future.get();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConcurrentException("Interrupted while getting result", e);
        } catch (final ExecutionException e) {
            throw new ConcurrentException("Execution failed", e.getCause());
        }
    }

    public static <T> T extractResultUnchecked(final Future<T> future) {
        try {
            return future.get();
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConcurrentRuntimeException("Interrupted while getting result", e);
        } catch (final ExecutionException e) {
            throw new ConcurrentRuntimeException("Execution failed", e.getCause());
        }
    }

    public static <T> Future<T> constantFuture(final T value) {
        return new ConstantFuture<>(value);
    }

    public static void handleCause(final ExecutionException ex) throws ConcurrentException {
        if (ex != null && ex.getCause() != null) {
            throw new ConcurrentException(ex.getCause());
        }
    }

    public static void handleCauseUnchecked(final ExecutionException ex) {
        if (ex != null && ex.getCause() != null) {
            throw new ConcurrentRuntimeException(ex.getCause());
        }
    }

    private static final class ConstantFuture<T> implements Future<T> {
        private final T value;

        ConstantFuture(final T value) {
            this.value = value;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public T get(long timeout, TimeUnit unit) {
            return value;
        }
    }

    public static class ConcurrentException extends Exception {
        public ConcurrentException(final String message, final Throwable cause) {
            super(message, cause);
        }
        public ConcurrentException(final Throwable cause) {
            super(cause);
        }
    }

    public static class ConcurrentRuntimeException extends RuntimeException {
        public ConcurrentRuntimeException(final String message, final Throwable cause) {
            super(message, cause);
        }
        public ConcurrentRuntimeException(final Throwable cause) {
            super(cause);
        }
    }
}

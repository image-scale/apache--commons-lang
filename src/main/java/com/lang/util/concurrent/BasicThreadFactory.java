package com.lang.util.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class BasicThreadFactory implements ThreadFactory {

    private final AtomicLong threadCount = new AtomicLong(0);
    private final String namingPattern;
    private final Boolean daemon;
    private final Integer priority;
    private final Thread.UncaughtExceptionHandler exceptionHandler;
    private final ThreadFactory wrappedFactory;

    private BasicThreadFactory(final Builder builder) {
        this.namingPattern = builder.namingPattern;
        this.daemon = builder.daemon;
        this.priority = builder.priority;
        this.exceptionHandler = builder.exceptionHandler;
        this.wrappedFactory = builder.wrappedFactory;
    }

    @Override
    public Thread newThread(final Runnable r) {
        final Thread thread;
        if (wrappedFactory != null) {
            thread = wrappedFactory.newThread(r);
        } else {
            thread = new Thread(r);
        }
        if (namingPattern != null) {
            thread.setName(String.format(namingPattern, threadCount.incrementAndGet()));
        }
        if (daemon != null) {
            thread.setDaemon(daemon);
        }
        if (priority != null) {
            thread.setPriority(priority);
        }
        if (exceptionHandler != null) {
            thread.setUncaughtExceptionHandler(exceptionHandler);
        }
        return thread;
    }

    public long getThreadCount() {
        return threadCount.get();
    }

    public String getNamingPattern() {
        return namingPattern;
    }

    public Boolean getDaemon() {
        return daemon;
    }

    public Integer getPriority() {
        return priority;
    }

    public Thread.UncaughtExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public static class Builder {
        private String namingPattern;
        private Boolean daemon;
        private Integer priority;
        private Thread.UncaughtExceptionHandler exceptionHandler;
        private ThreadFactory wrappedFactory;

        public Builder namingPattern(final String pattern) {
            this.namingPattern = pattern;
            return this;
        }

        public Builder daemon(final boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public Builder priority(final int priority) {
            this.priority = priority;
            return this;
        }

        public Builder exceptionHandler(final Thread.UncaughtExceptionHandler handler) {
            this.exceptionHandler = handler;
            return this;
        }

        public Builder wrappedFactory(final ThreadFactory factory) {
            this.wrappedFactory = factory;
            return this;
        }

        public BasicThreadFactory build() {
            return new BasicThreadFactory(this);
        }
    }
}

package com.lang.util.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class LazyInitializer<T> {

    private final Supplier<T> factory;
    private final AtomicReference<T> instance = new AtomicReference<>();
    private volatile boolean initialized;

    public LazyInitializer(final Supplier<T> factory) {
        if (factory == null) {
            throw new IllegalArgumentException("Factory must not be null");
        }
        this.factory = factory;
    }

    public T get() {
        T result = instance.get();
        if (!initialized) {
            synchronized (this) {
                result = instance.get();
                if (!initialized) {
                    result = factory.get();
                    instance.set(result);
                    initialized = true;
                }
            }
        }
        return result;
    }

    public boolean isInitialized() {
        return initialized;
    }
}

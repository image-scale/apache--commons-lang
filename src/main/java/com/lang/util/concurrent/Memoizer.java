package com.lang.util.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class Memoizer<K, V> {

    private final ConcurrentMap<K, V> cache = new ConcurrentHashMap<>();
    private final Function<K, V> computeFunction;

    public Memoizer(final Function<K, V> computeFunction) {
        if (computeFunction == null) {
            throw new IllegalArgumentException("Compute function must not be null");
        }
        this.computeFunction = computeFunction;
    }

    public V compute(final K key) {
        return cache.computeIfAbsent(key, computeFunction);
    }

    public boolean contains(final K key) {
        return cache.containsKey(key);
    }

    public V getIfCached(final K key) {
        return cache.get(key);
    }

    public void invalidate(final K key) {
        cache.remove(key);
    }

    public void invalidateAll() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }
}

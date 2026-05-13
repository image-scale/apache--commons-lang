package com.lang.util.concurrent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicThreadFactoryTest {

    @Test
    void testDefaultFactory() {
        final BasicThreadFactory factory = new BasicThreadFactory.Builder().build();
        final Thread t = factory.newThread(() -> {});
        assertNotNull(t);
    }

    @Test
    void testNamingPattern() {
        final BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("worker-%d")
                .build();
        assertEquals("worker-%d", factory.getNamingPattern());
        final Thread t1 = factory.newThread(() -> {});
        assertEquals("worker-1", t1.getName());
        final Thread t2 = factory.newThread(() -> {});
        assertEquals("worker-2", t2.getName());
        assertEquals(2, factory.getThreadCount());
    }

    @Test
    void testDaemon() {
        final BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .daemon(true)
                .build();
        assertEquals(Boolean.TRUE, factory.getDaemon());
        final Thread t = factory.newThread(() -> {});
        assertTrue(t.isDaemon());
    }

    @Test
    void testPriority() {
        final BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .priority(Thread.MAX_PRIORITY)
                .build();
        assertEquals(Integer.valueOf(Thread.MAX_PRIORITY), factory.getPriority());
        final Thread t = factory.newThread(() -> {});
        assertEquals(Thread.MAX_PRIORITY, t.getPriority());
    }

    @Test
    void testExceptionHandler() {
        final Thread.UncaughtExceptionHandler handler = (t, e) -> {};
        final BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .exceptionHandler(handler)
                .build();
        assertSame(handler, factory.getExceptionHandler());
        final Thread t = factory.newThread(() -> {});
        assertSame(handler, t.getUncaughtExceptionHandler());
    }

    @Test
    void testWrappedFactory() {
        final BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .wrappedFactory(Thread::new)
                .namingPattern("custom-%d")
                .build();
        final Thread t = factory.newThread(() -> {});
        assertEquals("custom-1", t.getName());
    }

    @Test
    void testFullBuilder() {
        final Thread.UncaughtExceptionHandler handler = (t, e) -> {};
        final BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("pool-%d")
                .daemon(true)
                .priority(Thread.MIN_PRIORITY)
                .exceptionHandler(handler)
                .build();
        final Thread t = factory.newThread(() -> {});
        assertEquals("pool-1", t.getName());
        assertTrue(t.isDaemon());
        assertEquals(Thread.MIN_PRIORITY, t.getPriority());
        assertSame(handler, t.getUncaughtExceptionHandler());
    }

    @Test
    void testNoNamingPatternKeepsDefault() {
        final BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .daemon(false)
                .build();
        assertNull(factory.getNamingPattern());
        final Thread t = factory.newThread(() -> {});
        assertNotNull(t.getName());
    }
}

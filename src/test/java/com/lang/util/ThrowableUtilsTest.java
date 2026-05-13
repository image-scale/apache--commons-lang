package com.lang.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThrowableUtilsTest {

    @Test
    void testGetRootCause() {
        final Exception root = new IllegalArgumentException("root");
        final Exception mid = new RuntimeException("mid", root);
        final Exception top = new Exception("top", mid);
        assertSame(root, ThrowableUtils.getRootCause(top));
    }

    @Test
    void testGetRootCauseNoCause() {
        final Exception e = new Exception("alone");
        assertSame(e, ThrowableUtils.getRootCause(e));
    }

    @Test
    void testGetRootCauseNull() {
        assertNull(ThrowableUtils.getRootCause(null));
    }

    @Test
    void testGetThrowableList() {
        final Exception root = new IllegalArgumentException("root");
        final Exception mid = new RuntimeException("mid", root);
        final Exception top = new Exception("top", mid);
        final List<Throwable> list = ThrowableUtils.getThrowableList(top);
        assertEquals(3, list.size());
        assertSame(top, list.get(0));
        assertSame(mid, list.get(1));
        assertSame(root, list.get(2));
    }

    @Test
    void testGetThrowableListNull() {
        assertTrue(ThrowableUtils.getThrowableList(null).isEmpty());
    }

    @Test
    void testGetThrowables() {
        final Exception e = new Exception("test");
        final Throwable[] arr = ThrowableUtils.getThrowables(e);
        assertEquals(1, arr.length);
        assertSame(e, arr[0]);
    }

    @Test
    void testGetThrowableCount() {
        final Exception root = new Exception("root");
        final Exception top = new Exception("top", root);
        assertEquals(2, ThrowableUtils.getThrowableCount(top));
        assertEquals(0, ThrowableUtils.getThrowableCount(null));
    }

    @Test
    void testGetStackTrace() {
        final Exception e = new Exception("test error");
        final String trace = ThrowableUtils.getStackTrace(e);
        assertTrue(trace.contains("test error"));
        assertTrue(trace.contains("java.lang.Exception"));
    }

    @Test
    void testGetStackTraceNull() {
        assertEquals("", ThrowableUtils.getStackTrace(null));
    }

    @Test
    void testGetMessage() {
        assertEquals("Exception: test", ThrowableUtils.getMessage(new Exception("test")));
        assertEquals("", ThrowableUtils.getMessage(null));
    }

    @Test
    void testGetRootCauseMessage() {
        final Exception root = new IllegalArgumentException("root cause");
        final Exception top = new Exception("wrapper", root);
        assertEquals("IllegalArgumentException: root cause",
                ThrowableUtils.getRootCauseMessage(top));
    }

    @Test
    void testIndexOfThrowable() {
        final IOException root = new IOException("io");
        final RuntimeException top = new RuntimeException("rt", root);
        assertEquals(0, ThrowableUtils.indexOfThrowable(top, RuntimeException.class));
        assertEquals(1, ThrowableUtils.indexOfThrowable(top, IOException.class));
        assertEquals(-1, ThrowableUtils.indexOfThrowable(top, IllegalArgumentException.class));
    }

    @Test
    void testIndexOfThrowableFromIndex() {
        final IOException root = new IOException("io");
        final RuntimeException top = new RuntimeException("rt", root);
        assertEquals(-1, ThrowableUtils.indexOfThrowable(top, RuntimeException.class, 1));
        assertEquals(1, ThrowableUtils.indexOfThrowable(top, IOException.class, 1));
    }

    @Test
    void testIndexOfThrowableNull() {
        assertEquals(-1, ThrowableUtils.indexOfThrowable(null, Exception.class));
    }

    @Test
    void testIndexOfType() {
        final IOException root = new IOException("io");
        final RuntimeException top = new RuntimeException("rt", root);
        assertEquals(0, ThrowableUtils.indexOfType(top, Exception.class));
        assertEquals(1, ThrowableUtils.indexOfType(top, IOException.class));
    }

    @Test
    void testIndexOfTypeNull() {
        assertEquals(-1, ThrowableUtils.indexOfType(null, Exception.class));
    }

    @Test
    void testHasCause() {
        final IOException root = new IOException("io");
        final RuntimeException top = new RuntimeException("rt", root);
        assertTrue(ThrowableUtils.hasCause(top, IOException.class));
        assertTrue(ThrowableUtils.hasCause(top, RuntimeException.class));
        assertFalse(ThrowableUtils.hasCause(top, IllegalArgumentException.class));
        assertFalse(ThrowableUtils.hasCause(top, null));
    }

    @Test
    void testRethrow() {
        final IOException checked = new IOException("test");
        assertThrows(IOException.class, () -> ThrowableUtils.rethrow(checked));
    }

    @Test
    void testIsChecked() {
        assertTrue(ThrowableUtils.isChecked(new Exception()));
        assertTrue(ThrowableUtils.isChecked(new IOException()));
        assertFalse(ThrowableUtils.isChecked(new RuntimeException()));
        assertFalse(ThrowableUtils.isChecked(new Error()));
        assertFalse(ThrowableUtils.isChecked(null));
    }

    @Test
    void testIsUnchecked() {
        assertTrue(ThrowableUtils.isUnchecked(new RuntimeException()));
        assertTrue(ThrowableUtils.isUnchecked(new Error()));
        assertFalse(ThrowableUtils.isUnchecked(new Exception()));
        assertFalse(ThrowableUtils.isUnchecked(null));
    }
}

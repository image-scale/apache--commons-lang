package com.lang.util;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypeUtilsTest {

    @Test
    void testGetNameClass() {
        assertEquals("java.lang.String", TypeUtils.getName(String.class));
        assertNull(TypeUtils.getName((Class<?>) null));
    }

    @Test
    void testGetNameClassDefault() {
        assertEquals("fallback", TypeUtils.getName((Class<?>) null, "fallback"));
    }

    @Test
    void testGetNameObject() {
        assertEquals("java.lang.String", TypeUtils.getName("hello"));
        assertNull(TypeUtils.getName((Object) null));
    }

    @Test
    void testGetNameObjectDefault() {
        assertEquals("def", TypeUtils.getName((Object) null, "def"));
    }

    @Test
    void testGetShortName() {
        assertEquals("String", TypeUtils.getShortName(String.class));
        assertEquals("String", TypeUtils.getShortName("java.lang.String"));
        assertNull(TypeUtils.getShortName((Class<?>) null));
        assertNull(TypeUtils.getShortName((String) null));
    }

    @Test
    void testGetShortNameInnerClass() {
        assertEquals("TypeUtilsTest.Inner", TypeUtils.getShortName(Inner.class));
    }

    @Test
    void testGetPackageName() {
        assertEquals("java.lang", TypeUtils.getPackageName(String.class));
        assertEquals("java.lang", TypeUtils.getPackageName("java.lang.String"));
        assertEquals("", TypeUtils.getPackageName((Class<?>) null));
        assertEquals("", TypeUtils.getPackageName((String) null));
        assertEquals("", TypeUtils.getPackageName("NoPkg"));
    }

    @Test
    void testIsPrimitiveOrWrapper() {
        assertTrue(TypeUtils.isPrimitiveOrWrapper(int.class));
        assertTrue(TypeUtils.isPrimitiveOrWrapper(Integer.class));
        assertFalse(TypeUtils.isPrimitiveOrWrapper(String.class));
        assertFalse(TypeUtils.isPrimitiveOrWrapper(null));
    }

    @Test
    void testIsPrimitiveWrapper() {
        assertTrue(TypeUtils.isPrimitiveWrapper(Integer.class));
        assertTrue(TypeUtils.isPrimitiveWrapper(Boolean.class));
        assertFalse(TypeUtils.isPrimitiveWrapper(int.class));
        assertFalse(TypeUtils.isPrimitiveWrapper(String.class));
    }

    @Test
    void testPrimitiveToWrapper() {
        assertEquals(Integer.class, TypeUtils.primitiveToWrapper(int.class));
        assertEquals(Long.class, TypeUtils.primitiveToWrapper(long.class));
        assertEquals(Boolean.class, TypeUtils.primitiveToWrapper(boolean.class));
        assertEquals(String.class, TypeUtils.primitiveToWrapper(String.class));
        assertNull(TypeUtils.primitiveToWrapper(null));
    }

    @Test
    void testWrapperToPrimitive() {
        assertEquals(int.class, TypeUtils.wrapperToPrimitive(Integer.class));
        assertEquals(long.class, TypeUtils.wrapperToPrimitive(Long.class));
        assertNull(TypeUtils.wrapperToPrimitive(String.class));
    }

    @Test
    void testPrimitivesToWrappers() {
        final Class<?>[] result = TypeUtils.primitivesToWrappers(int.class, long.class);
        assertEquals(Integer.class, result[0]);
        assertEquals(Long.class, result[1]);
        assertNull(TypeUtils.primitivesToWrappers((Class<?>[]) null));
    }

    @Test
    void testWrappersToPrimitives() {
        final Class<?>[] result = TypeUtils.wrappersToPrimitives(Integer.class, Long.class);
        assertEquals(int.class, result[0]);
        assertEquals(long.class, result[1]);
        assertNull(TypeUtils.wrappersToPrimitives((Class<?>[]) null));
    }

    @Test
    void testIsAssignable() {
        assertTrue(TypeUtils.isAssignable(String.class, Object.class));
        assertTrue(TypeUtils.isAssignable(Integer.class, Number.class));
        assertFalse(TypeUtils.isAssignable(String.class, Integer.class));
        assertTrue(TypeUtils.isAssignable(null, Object.class));
        assertFalse(TypeUtils.isAssignable(null, int.class));
        assertFalse(TypeUtils.isAssignable(String.class, null));
    }

    @Test
    void testIsAssignableAutoboxing() {
        assertTrue(TypeUtils.isAssignable(int.class, Integer.class, true));
        assertFalse(TypeUtils.isAssignable(int.class, Integer.class, false));
    }

    @Test
    void testGetAllSuperclasses() {
        final List<Class<?>> supers = TypeUtils.getAllSuperclasses(Integer.class);
        assertNotNull(supers);
        assertTrue(supers.contains(Number.class));
        assertTrue(supers.contains(Object.class));
        assertFalse(supers.contains(Integer.class));
        assertNull(TypeUtils.getAllSuperclasses(null));
    }

    @Test
    void testGetAllInterfaces() {
        final List<Class<?>> ifaces = TypeUtils.getAllInterfaces(Integer.class);
        assertNotNull(ifaces);
        assertTrue(ifaces.contains(Comparable.class));
        assertTrue(ifaces.contains(Serializable.class));
        assertNull(TypeUtils.getAllInterfaces(null));
    }

    @Test
    void testHierarchy() {
        final List<Class<?>> h = TypeUtils.hierarchy(Integer.class);
        assertEquals(Integer.class, h.get(0));
        assertTrue(h.contains(Number.class));
        assertTrue(h.contains(Object.class));
    }

    @Test
    void testIsInnerClass() {
        assertTrue(TypeUtils.isInnerClass(Inner.class));
        assertFalse(TypeUtils.isInnerClass(String.class));
        assertFalse(TypeUtils.isInnerClass(null));
    }

    @Test
    void testGetClass() throws ClassNotFoundException {
        assertEquals(String.class, TypeUtils.getClass("java.lang.String"));
    }

    @Test
    void testGetClassNotFound() {
        assertThrows(ClassNotFoundException.class,
                () -> TypeUtils.getClass("com.nonexistent.Foo"));
    }

    static class Inner {}
}

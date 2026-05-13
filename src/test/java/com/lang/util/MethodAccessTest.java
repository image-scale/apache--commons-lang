package com.lang.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodAccessTest {

    static class Calculator {
        public int add(int a, int b) { return a + b; }
        public double add(double a, double b) { return a + b; }
        public String greet() { return "hello"; }
        private String secret() { return "hidden"; }
        protected String prot() { return "protected"; }

        public static int multiply(int a, int b) { return a * b; }
        public static String staticGreet() { return "static_hello"; }

        @Deprecated
        public void oldMethod() {}

        public String concat(String a, String b) { return a + b; }
        public long widen(long value) { return value * 2; }
        public Number acceptNumber(Number n) { return n; }
        public String withNull(String s) { return s == null ? "null" : s; }
    }

    static class SubCalc extends Calculator {
        public int subtract(int a, int b) { return a - b; }
    }

    // --- getMatchingMethod tests ---

    @Test
    void testGetMatchingMethodExact() {
        final Method m = MethodAccess.getMatchingMethod(Calculator.class, "add",
                int.class, int.class);
        assertNotNull(m);
        assertEquals("add", m.getName());
        assertEquals(int.class, m.getReturnType());
    }

    @Test
    void testGetMatchingMethodWidening() {
        final Method m = MethodAccess.getMatchingMethod(Calculator.class, "widen",
                Integer.class);
        assertNotNull(m);
        assertEquals("widen", m.getName());
    }

    @Test
    void testGetMatchingMethodSubtype() {
        final Method m = MethodAccess.getMatchingMethod(Calculator.class, "acceptNumber",
                Integer.class);
        assertNotNull(m);
        assertEquals("acceptNumber", m.getName());
    }

    @Test
    void testGetMatchingMethodNoArgs() {
        final Method m = MethodAccess.getMatchingMethod(Calculator.class, "greet");
        assertNotNull(m);
        assertEquals("greet", m.getName());
    }

    @Test
    void testGetMatchingMethodNotFound() {
        assertNull(MethodAccess.getMatchingMethod(Calculator.class, "nonexistent"));
    }

    @Test
    void testGetMatchingMethodNullClass() {
        assertThrows(IllegalArgumentException.class,
                () -> MethodAccess.getMatchingMethod(null, "add", int.class));
    }

    @Test
    void testGetMatchingMethodNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> MethodAccess.getMatchingMethod(Calculator.class, null));
    }

    // --- getAccessibleMethod tests ---

    @Test
    void testGetAccessibleMethodPublic() {
        final Method m = MethodAccess.getAccessibleMethod(Calculator.class, "greet");
        assertNotNull(m);
    }

    @Test
    void testGetAccessibleMethodNotFound() {
        assertNull(MethodAccess.getAccessibleMethod(Calculator.class, "secret"));
    }

    @Test
    void testGetAccessibleMethodNullClass() {
        assertThrows(IllegalArgumentException.class,
                () -> MethodAccess.getAccessibleMethod(null, "greet"));
    }

    // --- getMethodsIncludingInherited tests ---

    @Test
    void testGetMethodsIncludingInherited() {
        final List<Method> methods = MethodAccess.getMethodsIncludingInherited(SubCalc.class);
        assertFalse(methods.isEmpty());
        final List<String> names = methods.stream().map(Method::getName).toList();
        assertTrue(names.contains("subtract"));
        assertTrue(names.contains("add"));
        assertTrue(names.contains("greet"));
    }

    @Test
    void testGetMethodsIncludingInheritedNullClass() {
        assertThrows(IllegalArgumentException.class,
                () -> MethodAccess.getMethodsIncludingInherited(null));
    }

    // --- getMethodsWithAnnotation tests ---

    @Test
    void testGetMethodsWithAnnotation() {
        final List<Method> deprecated = MethodAccess.getMethodsWithAnnotation(
                Calculator.class, Deprecated.class);
        assertFalse(deprecated.isEmpty());
        assertTrue(deprecated.stream().anyMatch(m -> m.getName().equals("oldMethod")));
    }

    @Test
    void testGetMethodsWithAnnotationNone() {
        final List<Method> result = MethodAccess.getMethodsWithAnnotation(
                Calculator.class, SuppressWarnings.class);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetMethodsWithAnnotationNullClass() {
        assertThrows(IllegalArgumentException.class,
                () -> MethodAccess.getMethodsWithAnnotation(null, Deprecated.class));
    }

    @Test
    void testGetMethodsWithAnnotationNullAnnotation() {
        assertThrows(IllegalArgumentException.class,
                () -> MethodAccess.getMethodsWithAnnotation(Calculator.class, null));
    }

    // --- invokeMethod tests ---

    @Test
    void testInvokeMethodNoArgs() throws Exception {
        final Calculator calc = new Calculator();
        assertEquals("hello", MethodAccess.invokeMethod(calc, "greet"));
    }

    @Test
    void testInvokeMethodWithArgs() throws Exception {
        final Calculator calc = new Calculator();
        assertEquals("ab", MethodAccess.invokeMethod(calc, "concat", "a", "b"));
    }

    @Test
    void testInvokeMethodPrivateForceAccess() throws Exception {
        final Calculator calc = new Calculator();
        assertEquals("hidden", MethodAccess.invokeMethod(calc, true, "secret"));
    }

    @Test
    void testInvokeMethodInherited() throws Exception {
        final SubCalc sc = new SubCalc();
        assertEquals("hello", MethodAccess.invokeMethod(sc, "greet"));
    }

    @Test
    void testInvokeMethodNullObject() {
        assertThrows(IllegalArgumentException.class,
                () -> MethodAccess.invokeMethod(null, "greet"));
    }

    @Test
    void testInvokeMethodNotFound() {
        final Calculator calc = new Calculator();
        assertThrows(NoSuchMethodException.class,
                () -> MethodAccess.invokeMethod(calc, "nonexistent"));
    }

    @Test
    void testInvokeMethodWithNullArg() throws Exception {
        final Calculator calc = new Calculator();
        assertEquals("null", MethodAccess.invokeMethod(calc, "withNull", (Object) null));
    }

    // --- invokeExactMethod tests ---

    @Test
    void testInvokeExactMethod() throws Exception {
        final Calculator calc = new Calculator();
        assertEquals("hello", MethodAccess.invokeExactMethod(calc, "greet"));
    }

    @Test
    void testInvokeExactMethodNotFound() {
        final Calculator calc = new Calculator();
        assertThrows(NoSuchMethodException.class,
                () -> MethodAccess.invokeExactMethod(calc, "nonexistent"));
    }

    @Test
    void testInvokeExactMethodNullObject() {
        assertThrows(IllegalArgumentException.class,
                () -> MethodAccess.invokeExactMethod(null, "greet"));
    }

    // --- invokeStaticMethod tests ---

    @Test
    void testInvokeStaticMethod() throws Exception {
        assertEquals("static_hello",
                MethodAccess.invokeStaticMethod(Calculator.class, "staticGreet"));
    }

    @Test
    void testInvokeStaticMethodWithArgs() throws Exception {
        assertEquals(12,
                MethodAccess.invokeStaticMethod(Calculator.class, "multiply", 3, 4));
    }

    @Test
    void testInvokeStaticMethodNotFound() {
        assertThrows(NoSuchMethodException.class,
                () -> MethodAccess.invokeStaticMethod(Calculator.class, "noMethod"));
    }

    // --- invokeExactStaticMethod tests ---

    @Test
    void testInvokeExactStaticMethod() throws Exception {
        assertEquals("static_hello",
                MethodAccess.invokeExactStaticMethod(Calculator.class, "staticGreet"));
    }

    @Test
    void testInvokeExactStaticMethodNotFound() {
        assertThrows(NoSuchMethodException.class,
                () -> MethodAccess.invokeExactStaticMethod(Calculator.class, "noMethod"));
    }

    // --- overload resolution tests ---

    @Test
    void testOverloadResolutionIntVsDouble() throws Exception {
        final Calculator calc = new Calculator();
        final Object result = MethodAccess.invokeMethod(calc, "add", 2, 3);
        assertEquals(5, result);
    }

    @Test
    void testOverloadResolutionDouble() throws Exception {
        final Calculator calc = new Calculator();
        final Object result = MethodAccess.invokeMethod(calc, "add", 2.0, 3.0);
        assertEquals(5.0, result);
    }

    // --- matching method on subclass ---

    @Test
    void testGetMatchingMethodInherited() {
        final Method m = MethodAccess.getMatchingMethod(SubCalc.class, "greet");
        assertNotNull(m);
    }

    @Test
    void testGetMatchingMethodSubclassOwn() {
        final Method m = MethodAccess.getMatchingMethod(SubCalc.class, "subtract",
                int.class, int.class);
        assertNotNull(m);
    }
}

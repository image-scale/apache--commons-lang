package com.lang.util.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashGeneratorTest {

    @Test
    void testDefaultConstructor() {
        final int hash = new HashGenerator().append(0).toHashCode();
        assertEquals(17 * 37, hash);
    }

    @Test
    void testCustomInitialAndMultiplier() {
        final int hash = new HashGenerator(7, 11).append(0).toHashCode();
        assertEquals(7 * 11, hash);
    }

    @Test
    void testEvenInitialThrows() {
        assertThrows(IllegalArgumentException.class, () -> new HashGenerator(2, 37));
    }

    @Test
    void testEvenMultiplierThrows() {
        assertThrows(IllegalArgumentException.class, () -> new HashGenerator(17, 4));
    }

    @Test
    void testAppendInt() {
        final int hash = new HashGenerator().append(42).toHashCode();
        assertEquals(17 * 37 + 42, hash);
    }

    @Test
    void testAppendMultipleInts() {
        final int hash = new HashGenerator().append(1).append(2).toHashCode();
        assertEquals((17 * 37 + 1) * 37 + 2, hash);
    }

    @Test
    void testAppendLong() {
        final long val = 123456789L;
        final int expected = 17 * 37 + (int) (val ^ (val >>> 32));
        assertEquals(expected, new HashGenerator().append(val).toHashCode());
    }

    @Test
    void testAppendShort() {
        assertEquals(17 * 37 + 5, new HashGenerator().append((short) 5).toHashCode());
    }

    @Test
    void testAppendChar() {
        assertEquals(17 * 37 + 'a', new HashGenerator().append('a').toHashCode());
    }

    @Test
    void testAppendByte() {
        assertEquals(17 * 37 + 3, new HashGenerator().append((byte) 3).toHashCode());
    }

    @Test
    void testAppendDouble() {
        final long bits = Double.doubleToLongBits(3.14);
        final int expected = 17 * 37 + (int) (bits ^ (bits >>> 32));
        assertEquals(expected, new HashGenerator().append(3.14).toHashCode());
    }

    @Test
    void testAppendFloat() {
        final int bits = Float.floatToIntBits(2.5f);
        assertEquals(17 * 37 + bits, new HashGenerator().append(2.5f).toHashCode());
    }

    @Test
    void testAppendBooleanTrue() {
        assertEquals(17 * 37 + 0, new HashGenerator().append(true).toHashCode());
    }

    @Test
    void testAppendBooleanFalse() {
        assertEquals(17 * 37 + 1, new HashGenerator().append(false).toHashCode());
    }

    @Test
    void testAppendObjectNull() {
        assertEquals(17 * 37, new HashGenerator().append((Object) null).toHashCode());
    }

    @Test
    void testAppendObject() {
        final String s = "hello";
        assertEquals(17 * 37 + s.hashCode(), new HashGenerator().append((Object) s).toHashCode());
    }

    @Test
    void testAppendObjectArray() {
        final String[] arr = {"a", "b"};
        final int hash = new HashGenerator().append(arr).toHashCode();
        int expected = 17;
        expected = expected * 37 + "a".hashCode();
        expected = expected * 37 + "b".hashCode();
        assertEquals(expected, hash);
    }

    @Test
    void testAppendNullObjectArray() {
        assertEquals(17 * 37, new HashGenerator().append((Object[]) null).toHashCode());
    }

    @Test
    void testAppendIntArray() {
        final int[] arr = {10, 20};
        final int hash = new HashGenerator().append(arr).toHashCode();
        int expected = 17;
        expected = expected * 37 + 10;
        expected = expected * 37 + 20;
        assertEquals(expected, hash);
    }

    @Test
    void testAppendNullIntArray() {
        assertEquals(17 * 37, new HashGenerator().append((int[]) null).toHashCode());
    }

    @Test
    void testAppendLongArray() {
        final long[] arr = {100L};
        final int hash = new HashGenerator().append(arr).toHashCode();
        final int longHash = (int) (100L ^ (100L >>> 32));
        assertEquals(17 * 37 + longHash, hash);
    }

    @Test
    void testAppendNullLongArray() {
        assertEquals(17 * 37, new HashGenerator().append((long[]) null).toHashCode());
    }

    @Test
    void testAppendSuper() {
        final int superHash = 999;
        assertEquals(17 * 37 + superHash, new HashGenerator().appendSuper(superHash).toHashCode());
    }

    @Test
    void testBuild() {
        assertEquals(Integer.valueOf(17 * 37 + 5),
                new HashGenerator().append(5).build());
    }

    @Test
    void testConsistency() {
        final int h1 = new HashGenerator().append(1).append("test").toHashCode();
        final int h2 = new HashGenerator().append(1).append("test").toHashCode();
        assertEquals(h1, h2);
    }

    @Test
    void testDifferentValuesProduceDifferentHashes() {
        final int h1 = new HashGenerator().append(1).toHashCode();
        final int h2 = new HashGenerator().append(2).toHashCode();
        assertNotEquals(h1, h2);
    }

    @Test
    void testAppendObjectDetectsIntArray() {
        final Object arr = new int[]{1, 2};
        final int hash1 = new HashGenerator().append(arr).toHashCode();
        final int hash2 = new HashGenerator().append(new int[]{1, 2}).toHashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testAppendObjectDetectsLongArray() {
        final Object arr = new long[]{1L};
        final int hash1 = new HashGenerator().append(arr).toHashCode();
        final int hash2 = new HashGenerator().append(new long[]{1L}).toHashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testReflectionHashCode() {
        final TestObj a = new TestObj("alice", 30);
        final TestObj b = new TestObj("alice", 30);
        assertEquals(HashGenerator.reflectionHashCode(a),
                HashGenerator.reflectionHashCode(b));
    }

    @Test
    void testReflectionHashCodeDifferentValues() {
        final TestObj a = new TestObj("alice", 30);
        final TestObj b = new TestObj("bob", 25);
        assertNotEquals(HashGenerator.reflectionHashCode(a),
                HashGenerator.reflectionHashCode(b));
    }

    @Test
    void testReflectionHashCodeNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> HashGenerator.reflectionHashCode(null));
    }

    @Test
    void testReflectionHashCodeExcludeFields() {
        final TestObj a = new TestObj("alice", 30);
        final TestObj b = new TestObj("bob", 30);
        assertEquals(HashGenerator.reflectionHashCode(a, "name"),
                HashGenerator.reflectionHashCode(b, "name"));
    }

    static class TestObj {
        final String name;
        final int age;
        TestObj(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}

package com.lang.util;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SerialUtilsTest {

    @Test
    void testSerializeDeserializeString() {
        final byte[] data = SerialUtils.serialize("hello");
        final String result = SerialUtils.deserialize(data);
        assertEquals("hello", result);
    }

    @Test
    void testSerializeDeserializeInteger() {
        final byte[] data = SerialUtils.serialize(42);
        final int result = SerialUtils.deserialize(data);
        assertEquals(42, result);
    }

    @Test
    void testSerializeDeserializeList() {
        final ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final byte[] data = SerialUtils.serialize(list);
        final List<String> result = SerialUtils.deserialize(data);
        assertEquals(list, result);
    }

    @Test
    void testSerializeNull() {
        final byte[] data = SerialUtils.serialize(null);
        assertNotNull(data);
        final Object result = SerialUtils.deserialize(data);
        assertNull(result);
    }

    @Test
    void testDeserializeNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> SerialUtils.deserialize(null));
    }

    @Test
    void testClone() {
        final ArrayList<String> original = new ArrayList<>(Arrays.asList("x", "y"));
        final ArrayList<String> cloned = SerialUtils.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    void testCloneNull() {
        assertNull(SerialUtils.clone(null));
    }

    @Test
    void testCloneString() {
        final String s = "test";
        assertEquals(s, SerialUtils.clone(s));
    }

    @Test
    void testRoundtrip() {
        final HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        final HashMap<String, Integer> result = SerialUtils.roundtrip(map);
        assertEquals(map, result);
        assertNotSame(map, result);
    }

    @Test
    void testSerializeCustomObject() {
        final SerializablePerson p = new SerializablePerson("Alice", 30);
        final SerializablePerson cloned = SerialUtils.clone(p);
        assertEquals("Alice", cloned.name);
        assertEquals(30, cloned.age);
        assertNotSame(p, cloned);
    }

    static class SerializablePerson implements Serializable {
        private static final long serialVersionUID = 1L;
        final String name;
        final int age;
        SerializablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}

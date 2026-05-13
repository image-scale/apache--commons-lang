package com.lang.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EnumHelperTest {

    enum Color { RED, GREEN, BLUE }

    @Test
    void testGetEnum() {
        assertEquals(Color.RED, EnumHelper.getEnum(Color.class, "RED"));
        assertNull(EnumHelper.getEnum(Color.class, "PINK"));
        assertNull(EnumHelper.getEnum(Color.class, null));
        assertNull(EnumHelper.getEnum(null, "RED"));
    }

    @Test
    void testGetEnumDefault() {
        assertEquals(Color.RED, EnumHelper.getEnum(Color.class, "RED", Color.BLUE));
        assertEquals(Color.BLUE, EnumHelper.getEnum(Color.class, "PINK", Color.BLUE));
    }

    @Test
    void testGetEnumIgnoreCase() {
        assertEquals(Color.RED, EnumHelper.getEnumIgnoreCase(Color.class, "red"));
        assertEquals(Color.GREEN, EnumHelper.getEnumIgnoreCase(Color.class, "Green"));
        assertNull(EnumHelper.getEnumIgnoreCase(Color.class, "PINK"));
        assertNull(EnumHelper.getEnumIgnoreCase(Color.class, null));
    }

    @Test
    void testGetEnumIgnoreCaseDefault() {
        assertEquals(Color.BLUE,
                EnumHelper.getEnumIgnoreCase(Color.class, "pink", Color.BLUE));
    }

    @Test
    void testIsValidEnum() {
        assertTrue(EnumHelper.isValidEnum(Color.class, "RED"));
        assertFalse(EnumHelper.isValidEnum(Color.class, "PINK"));
        assertFalse(EnumHelper.isValidEnum(Color.class, null));
    }

    @Test
    void testIsValidEnumIgnoreCase() {
        assertTrue(EnumHelper.isValidEnumIgnoreCase(Color.class, "red"));
        assertFalse(EnumHelper.isValidEnumIgnoreCase(Color.class, "pink"));
    }

    @Test
    void testGetEnumList() {
        final List<Color> list = EnumHelper.getEnumList(Color.class);
        assertEquals(3, list.size());
        assertEquals(Color.RED, list.get(0));
        assertEquals(Color.GREEN, list.get(1));
        assertEquals(Color.BLUE, list.get(2));
    }

    @Test
    void testGetEnumMap() {
        final Map<String, Color> map = EnumHelper.getEnumMap(Color.class);
        assertEquals(3, map.size());
        assertEquals(Color.RED, map.get("RED"));
        assertEquals(Color.GREEN, map.get("GREEN"));
    }

    @Test
    void testGenerateBitVectorVarargs() {
        final long vector = EnumHelper.generateBitVector(Color.class, Color.RED, Color.BLUE);
        assertEquals(0b101, vector);
    }

    @Test
    void testGenerateBitVectorIterable() {
        final long vector = EnumHelper.generateBitVector(Color.class,
                Arrays.asList(Color.GREEN, Color.BLUE));
        assertEquals(0b110, vector);
    }

    @Test
    void testProcessBitVector() {
        final EnumSet<Color> set = EnumHelper.processBitVector(Color.class, 0b101);
        assertTrue(set.contains(Color.RED));
        assertFalse(set.contains(Color.GREEN));
        assertTrue(set.contains(Color.BLUE));
    }

    @Test
    void testProcessBitVectorEmpty() {
        final EnumSet<Color> set = EnumHelper.processBitVector(Color.class, 0);
        assertTrue(set.isEmpty());
    }

    @Test
    void testProcessBitVectorAll() {
        final EnumSet<Color> set = EnumHelper.processBitVector(Color.class, 0b111);
        assertEquals(3, set.size());
    }
}

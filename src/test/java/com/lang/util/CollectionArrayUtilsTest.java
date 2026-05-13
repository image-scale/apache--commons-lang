package com.lang.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CollectionArrayUtilsTest {

    // --- isEmpty / isNotEmpty ---

    @Test
    void testIsEmptyObjectArray() {
        assertTrue(CollectionArrayUtils.isEmpty((Object[]) null));
        assertTrue(CollectionArrayUtils.isEmpty(new Object[0]));
        assertFalse(CollectionArrayUtils.isEmpty(new Object[]{"a"}));
    }

    @Test
    void testIsEmptyIntArray() {
        assertTrue(CollectionArrayUtils.isEmpty((int[]) null));
        assertTrue(CollectionArrayUtils.isEmpty(new int[0]));
        assertFalse(CollectionArrayUtils.isEmpty(new int[]{1}));
    }

    @Test
    void testIsEmptyLongArray() {
        assertTrue(CollectionArrayUtils.isEmpty((long[]) null));
        assertFalse(CollectionArrayUtils.isEmpty(new long[]{1L}));
    }

    @Test
    void testIsEmptyDoubleArray() {
        assertTrue(CollectionArrayUtils.isEmpty((double[]) null));
        assertFalse(CollectionArrayUtils.isEmpty(new double[]{1.0}));
    }

    @Test
    void testIsEmptyBooleanArray() {
        assertTrue(CollectionArrayUtils.isEmpty((boolean[]) null));
        assertFalse(CollectionArrayUtils.isEmpty(new boolean[]{true}));
    }

    @Test
    void testIsNotEmptyObjectArray() {
        assertFalse(CollectionArrayUtils.isNotEmpty((Object[]) null));
        assertTrue(CollectionArrayUtils.isNotEmpty(new Object[]{"a"}));
    }

    @Test
    void testIsNotEmptyIntArray() {
        assertFalse(CollectionArrayUtils.isNotEmpty((int[]) null));
        assertTrue(CollectionArrayUtils.isNotEmpty(new int[]{1}));
    }

    // --- contains ---

    @Test
    void testContainsObject() {
        assertFalse(CollectionArrayUtils.contains((Object[]) null, "a"));
        assertFalse(CollectionArrayUtils.contains(new String[]{"a", "b"}, "c"));
        assertTrue(CollectionArrayUtils.contains(new String[]{"a", "b"}, "a"));
        assertTrue(CollectionArrayUtils.contains(new String[]{"a", null}, null));
    }

    @Test
    void testContainsInt() {
        assertFalse(CollectionArrayUtils.contains((int[]) null, 1));
        assertTrue(CollectionArrayUtils.contains(new int[]{1, 2, 3}, 2));
        assertFalse(CollectionArrayUtils.contains(new int[]{1, 2, 3}, 4));
    }

    @Test
    void testContainsLong() {
        assertTrue(CollectionArrayUtils.contains(new long[]{1L, 2L, 3L}, 2L));
        assertFalse(CollectionArrayUtils.contains(new long[]{1L, 2L}, 5L));
    }

    @Test
    void testContainsDouble() {
        assertTrue(CollectionArrayUtils.contains(new double[]{1.0, 2.0}, 2.0));
        assertFalse(CollectionArrayUtils.contains(new double[]{1.0}, 2.0));
    }

    @Test
    void testContainsChar() {
        assertTrue(CollectionArrayUtils.contains(new char[]{'a', 'b'}, 'a'));
        assertFalse(CollectionArrayUtils.contains(new char[]{'a', 'b'}, 'c'));
    }

    @Test
    void testContainsBoolean() {
        assertTrue(CollectionArrayUtils.contains(new boolean[]{true, false}, true));
        assertFalse(CollectionArrayUtils.contains(new boolean[]{false}, true));
    }

    // --- indexOf ---

    @Test
    void testIndexOfObject() {
        assertEquals(-1, CollectionArrayUtils.indexOf((Object[]) null, "a"));
        assertEquals(1, CollectionArrayUtils.indexOf(new String[]{"a", "b", "c"}, "b"));
        assertEquals(-1, CollectionArrayUtils.indexOf(new String[]{"a", "b"}, "x"));
        assertEquals(2, CollectionArrayUtils.indexOf(new String[]{"a", "b", null}, null));
    }

    @Test
    void testIndexOfObjectFromStart() {
        assertEquals(3, CollectionArrayUtils.indexOf(new String[]{"a", "b", "c", "b"}, "b", 2));
    }

    @Test
    void testIndexOfInt() {
        assertEquals(1, CollectionArrayUtils.indexOf(new int[]{1, 2, 3}, 2));
        assertEquals(-1, CollectionArrayUtils.indexOf(new int[]{1, 2, 3}, 4));
    }

    @Test
    void testIndexOfLong() {
        assertEquals(1, CollectionArrayUtils.indexOf(new long[]{1L, 2L, 3L}, 2L));
    }

    @Test
    void testIndexOfDouble() {
        assertEquals(1, CollectionArrayUtils.indexOf(new double[]{1.0, 2.0, 3.0}, 2.0));
    }

    @Test
    void testIndexOfChar() {
        assertEquals(1, CollectionArrayUtils.indexOf(new char[]{'a', 'b', 'c'}, 'b'));
    }

    @Test
    void testIndexOfBoolean() {
        assertEquals(1, CollectionArrayUtils.indexOf(new boolean[]{false, true, false}, true));
    }

    // --- lastIndexOf ---

    @Test
    void testLastIndexOfObject() {
        assertEquals(-1, CollectionArrayUtils.lastIndexOf((Object[]) null, "a"));
        assertEquals(3, CollectionArrayUtils.lastIndexOf(new String[]{"a", "b", "c", "b"}, "b"));
        assertEquals(-1, CollectionArrayUtils.lastIndexOf(new String[]{"a", "b"}, "x"));
    }

    @Test
    void testLastIndexOfInt() {
        assertEquals(3, CollectionArrayUtils.lastIndexOf(new int[]{1, 2, 3, 2}, 2));
    }

    // --- add ---

    @Test
    void testAddObject() {
        final String[] result = CollectionArrayUtils.add(new String[]{"a", "b"}, "c");
        assertArrayEquals(new String[]{"a", "b", "c"}, result);
    }

    @Test
    void testAddObjectToNull() {
        final String[] result = CollectionArrayUtils.add(null, "a");
        assertArrayEquals(new String[]{"a"}, result);
    }

    @Test
    void testAddObjectAtIndex() {
        final String[] result = CollectionArrayUtils.add(new String[]{"a", "c"}, 1, "b");
        assertArrayEquals(new String[]{"a", "b", "c"}, result);
    }

    @Test
    void testAddObjectAtIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> CollectionArrayUtils.add(new String[]{"a"}, 5, "b"));
    }

    @Test
    void testAddInt() {
        assertArrayEquals(new int[]{1, 2, 3}, CollectionArrayUtils.add(new int[]{1, 2}, 3));
    }

    @Test
    void testAddIntToNull() {
        assertArrayEquals(new int[]{1}, CollectionArrayUtils.add((int[]) null, 1));
    }

    @Test
    void testAddLong() {
        assertArrayEquals(new long[]{1L, 2L, 3L}, CollectionArrayUtils.add(new long[]{1L, 2L}, 3L));
    }

    @Test
    void testAddDouble() {
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, CollectionArrayUtils.add(new double[]{1.0, 2.0}, 3.0));
    }

    @Test
    void testAddBoolean() {
        assertArrayEquals(new boolean[]{true, false}, CollectionArrayUtils.add(new boolean[]{true}, false));
    }

    @Test
    void testAddChar() {
        assertArrayEquals(new char[]{'a', 'b'}, CollectionArrayUtils.add(new char[]{'a'}, 'b'));
    }

    // --- addAll ---

    @Test
    void testAddAllObject() {
        final String[] result = CollectionArrayUtils.addAll(new String[]{"a"}, "b", "c");
        assertArrayEquals(new String[]{"a", "b", "c"}, result);
    }

    @Test
    void testAddAllNullFirst() {
        final String[] result = CollectionArrayUtils.addAll(null, "a", "b");
        assertArrayEquals(new String[]{"a", "b"}, result);
    }

    @Test
    void testAddAllNullSecond() {
        final String[] result = CollectionArrayUtils.addAll(new String[]{"a"}, (String[]) null);
        assertArrayEquals(new String[]{"a"}, result);
    }

    @Test
    void testAddAllInt() {
        assertArrayEquals(new int[]{1, 2, 3, 4}, CollectionArrayUtils.addAll(new int[]{1, 2}, 3, 4));
    }

    // --- remove ---

    @Test
    void testRemoveObject() {
        final String[] result = CollectionArrayUtils.remove(new String[]{"a", "b", "c"}, 1);
        assertArrayEquals(new String[]{"a", "c"}, result);
    }

    @Test
    void testRemoveObjectOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> CollectionArrayUtils.remove(new String[]{"a"}, 5));
    }

    @Test
    void testRemoveInt() {
        assertArrayEquals(new int[]{1, 3}, CollectionArrayUtils.remove(new int[]{1, 2, 3}, 1));
    }

    @Test
    void testRemoveElement() {
        final String[] result = CollectionArrayUtils.removeElement(new String[]{"a", "b", "c"}, "b");
        assertArrayEquals(new String[]{"a", "c"}, result);
    }

    @Test
    void testRemoveElementNotFound() {
        final String[] result = CollectionArrayUtils.removeElement(new String[]{"a", "b"}, "x");
        assertArrayEquals(new String[]{"a", "b"}, result);
    }

    @Test
    void testRemoveAllOccurrences() {
        final String[] result = CollectionArrayUtils.removeAllOccurrences(new String[]{"a", "b", "a", "c"}, "a");
        assertArrayEquals(new String[]{"b", "c"}, result);
    }

    @Test
    void testRemoveAllOccurrencesNull() {
        assertNull(CollectionArrayUtils.removeAllOccurrences(null, "a"));
    }

    // --- subarray ---

    @Test
    void testSubarrayObject() {
        final String[] result = CollectionArrayUtils.subarray(new String[]{"a", "b", "c", "d"}, 1, 3);
        assertArrayEquals(new String[]{"b", "c"}, result);
    }

    @Test
    void testSubarrayNull() {
        assertNull(CollectionArrayUtils.subarray((Object[]) null, 0, 1));
    }

    @Test
    void testSubarrayOutOfBounds() {
        final String[] result = CollectionArrayUtils.subarray(new String[]{"a", "b"}, 0, 10);
        assertArrayEquals(new String[]{"a", "b"}, result);
    }

    @Test
    void testSubarrayInt() {
        assertArrayEquals(new int[]{2, 3}, CollectionArrayUtils.subarray(new int[]{1, 2, 3, 4}, 1, 3));
    }

    // --- clone ---

    @Test
    void testCloneObject() {
        final String[] original = {"a", "b"};
        final String[] cloned = CollectionArrayUtils.clone(original);
        assertArrayEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    void testCloneNull() {
        assertNull(CollectionArrayUtils.clone((Object[]) null));
        assertNull(CollectionArrayUtils.clone((int[]) null));
    }

    @Test
    void testCloneInt() {
        final int[] original = {1, 2, 3};
        final int[] cloned = CollectionArrayUtils.clone(original);
        assertArrayEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    // --- reverse ---

    @Test
    void testReverseObject() {
        final String[] array = {"a", "b", "c"};
        CollectionArrayUtils.reverse(array);
        assertArrayEquals(new String[]{"c", "b", "a"}, array);
    }

    @Test
    void testReverseNull() {
        CollectionArrayUtils.reverse((Object[]) null);
    }

    @Test
    void testReverseInt() {
        final int[] array = {1, 2, 3};
        CollectionArrayUtils.reverse(array);
        assertArrayEquals(new int[]{3, 2, 1}, array);
    }

    @Test
    void testReversePartial() {
        final String[] array = {"a", "b", "c", "d"};
        CollectionArrayUtils.reverse(array, 1, 3);
        assertArrayEquals(new String[]{"a", "c", "b", "d"}, array);
    }

    @Test
    void testReverseLong() {
        final long[] array = {1L, 2L, 3L};
        CollectionArrayUtils.reverse(array);
        assertArrayEquals(new long[]{3L, 2L, 1L}, array);
    }

    @Test
    void testReverseDouble() {
        final double[] array = {1.0, 2.0, 3.0};
        CollectionArrayUtils.reverse(array);
        assertArrayEquals(new double[]{3.0, 2.0, 1.0}, array);
    }

    @Test
    void testReverseChar() {
        final char[] array = {'a', 'b', 'c'};
        CollectionArrayUtils.reverse(array);
        assertArrayEquals(new char[]{'c', 'b', 'a'}, array);
    }

    // --- swap ---

    @Test
    void testSwapObject() {
        final String[] array = {"a", "b", "c"};
        CollectionArrayUtils.swap(array, 0, 2);
        assertArrayEquals(new String[]{"c", "b", "a"}, array);
    }

    @Test
    void testSwapInt() {
        final int[] array = {1, 2, 3};
        CollectionArrayUtils.swap(array, 0, 2);
        assertArrayEquals(new int[]{3, 2, 1}, array);
    }

    @Test
    void testSwapEmpty() {
        CollectionArrayUtils.swap(new Object[]{}, 0, 1);
    }

    // --- shift ---

    @Test
    void testShiftObject() {
        final String[] array = {"a", "b", "c", "d"};
        CollectionArrayUtils.shift(array, 1);
        assertArrayEquals(new String[]{"d", "a", "b", "c"}, array);
    }

    @Test
    void testShiftObjectNegative() {
        final String[] array = {"a", "b", "c", "d"};
        CollectionArrayUtils.shift(array, -1);
        assertArrayEquals(new String[]{"b", "c", "d", "a"}, array);
    }

    @Test
    void testShiftInt() {
        final int[] array = {1, 2, 3, 4};
        CollectionArrayUtils.shift(array, 2);
        assertArrayEquals(new int[]{3, 4, 1, 2}, array);
    }

    @Test
    void testShiftNull() {
        CollectionArrayUtils.shift((Object[]) null, 1);
    }

    // --- isSorted ---

    @Test
    void testIsSorted() {
        assertTrue(CollectionArrayUtils.isSorted(new Integer[]{1, 2, 3}));
        assertFalse(CollectionArrayUtils.isSorted(new Integer[]{3, 1, 2}));
        assertTrue(CollectionArrayUtils.isSorted((Integer[]) null));
        assertTrue(CollectionArrayUtils.isSorted(new Integer[]{}));
        assertTrue(CollectionArrayUtils.isSorted(new Integer[]{1}));
    }

    @Test
    void testIsSortedInt() {
        assertTrue(CollectionArrayUtils.isSorted(new int[]{1, 2, 3}));
        assertFalse(CollectionArrayUtils.isSorted(new int[]{3, 1, 2}));
    }

    // --- nullToEmpty ---

    @Test
    void testNullToEmptyObject() {
        assertArrayEquals(new Object[0], CollectionArrayUtils.nullToEmpty((Object[]) null));
        assertArrayEquals(new Object[]{"a"}, CollectionArrayUtils.nullToEmpty(new Object[]{"a"}));
    }

    @Test
    void testNullToEmptyString() {
        assertArrayEquals(new String[0], CollectionArrayUtils.nullToEmpty((String[]) null));
    }

    @Test
    void testNullToEmptyInt() {
        assertArrayEquals(new int[0], CollectionArrayUtils.nullToEmpty((int[]) null));
    }

    @Test
    void testNullToEmptyLong() {
        assertArrayEquals(new long[0], CollectionArrayUtils.nullToEmpty((long[]) null));
    }

    // --- toObject ---

    @Test
    void testToObjectInt() {
        assertNull(CollectionArrayUtils.toObject((int[]) null));
        assertArrayEquals(new Integer[]{1, 2, 3}, CollectionArrayUtils.toObject(new int[]{1, 2, 3}));
        assertArrayEquals(new Integer[0], CollectionArrayUtils.toObject(new int[0]));
    }

    @Test
    void testToObjectLong() {
        assertArrayEquals(new Long[]{1L, 2L}, CollectionArrayUtils.toObject(new long[]{1L, 2L}));
    }

    @Test
    void testToObjectDouble() {
        assertArrayEquals(new Double[]{1.0, 2.0}, CollectionArrayUtils.toObject(new double[]{1.0, 2.0}));
    }

    @Test
    void testToObjectBoolean() {
        assertArrayEquals(new Boolean[]{true, false}, CollectionArrayUtils.toObject(new boolean[]{true, false}));
    }

    @Test
    void testToObjectChar() {
        assertArrayEquals(new Character[]{'a', 'b'}, CollectionArrayUtils.toObject(new char[]{'a', 'b'}));
    }

    // --- toPrimitive ---

    @Test
    void testToPrimitiveInt() {
        assertNull(CollectionArrayUtils.toPrimitive((Integer[]) null));
        assertArrayEquals(new int[]{1, 2, 3}, CollectionArrayUtils.toPrimitive(new Integer[]{1, 2, 3}));
        assertArrayEquals(new int[0], CollectionArrayUtils.toPrimitive(new Integer[0]));
    }

    @Test
    void testToPrimitiveIntWithDefault() {
        assertArrayEquals(new int[]{1, 0, 3}, CollectionArrayUtils.toPrimitive(new Integer[]{1, null, 3}, 0));
    }

    @Test
    void testToPrimitiveLong() {
        assertArrayEquals(new long[]{1L, 2L}, CollectionArrayUtils.toPrimitive(new Long[]{1L, 2L}));
    }

    @Test
    void testToPrimitiveDouble() {
        assertArrayEquals(new double[]{1.0, 2.0}, CollectionArrayUtils.toPrimitive(new Double[]{1.0, 2.0}));
    }

    @Test
    void testToPrimitiveBoolean() {
        assertArrayEquals(new boolean[]{true, false}, CollectionArrayUtils.toPrimitive(new Boolean[]{true, false}));
    }

    @Test
    void testToPrimitiveChar() {
        assertArrayEquals(new char[]{'a', 'b'}, CollectionArrayUtils.toPrimitive(new Character[]{'a', 'b'}));
    }

    // --- toMap ---

    @Test
    void testToMap() {
        assertNull(CollectionArrayUtils.toMap(null));
        final Map<Object, Object> result = CollectionArrayUtils.toMap(new Object[][]{
                {"key1", "val1"}, {"key2", "val2"}
        });
        assertEquals(2, result.size());
        assertEquals("val1", result.get("key1"));
        assertEquals("val2", result.get("key2"));
    }

    @Test
    void testToMapInvalidElement() {
        assertThrows(IllegalArgumentException.class,
                () -> CollectionArrayUtils.toMap(new Object[]{"not-an-array"}));
    }

    // --- insert ---

    @Test
    void testInsert() {
        final String[] result = CollectionArrayUtils.insert(1, new String[]{"a", "d"}, "b", "c");
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, result);
    }

    @Test
    void testInsertNull() {
        assertNull(CollectionArrayUtils.insert(0, null, "a"));
    }

    @Test
    void testInsertOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> CollectionArrayUtils.insert(5, new String[]{"a"}, "b"));
    }

    // --- toArray ---

    @Test
    void testToArray() {
        final String[] result = CollectionArrayUtils.toArray("a", "b", "c");
        assertArrayEquals(new String[]{"a", "b", "c"}, result);
    }

    // --- shuffle ---

    @Test
    void testShuffleNull() {
        CollectionArrayUtils.shuffle(null);
    }

    @Test
    void testShuffleSingle() {
        final Object[] array = {"a"};
        CollectionArrayUtils.shuffle(array);
        assertArrayEquals(new Object[]{"a"}, array);
    }

    // --- getLength ---

    @Test
    void testGetLength() {
        assertEquals(0, CollectionArrayUtils.getLength(null));
        assertEquals(3, CollectionArrayUtils.getLength(new int[]{1, 2, 3}));
        assertEquals(2, CollectionArrayUtils.getLength(new String[]{"a", "b"}));
    }

    // --- Constants ---

    @Test
    void testConstants() {
        assertEquals(0, CollectionArrayUtils.EMPTY_OBJECT_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_STRING_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_INT_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_LONG_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_DOUBLE_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_FLOAT_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_BOOLEAN_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_BYTE_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_CHAR_ARRAY.length);
        assertEquals(0, CollectionArrayUtils.EMPTY_SHORT_ARRAY.length);
    }
}

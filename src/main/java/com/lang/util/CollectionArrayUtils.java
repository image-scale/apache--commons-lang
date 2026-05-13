package com.lang.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CollectionArrayUtils {

    public static final int NOT_FOUND = -1;

    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

    private CollectionArrayUtils() {
    }

    // --- isEmpty / isNotEmpty ---

    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final int[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final long[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final double[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final float[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final boolean[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final byte[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final char[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final short[] array) {
        return getLength(array) == 0;
    }

    public static boolean isNotEmpty(final Object[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(final int[] array) {
        return !isEmpty(array);
    }

    public static boolean isNotEmpty(final long[] array) {
        return !isEmpty(array);
    }

    // --- getLength ---

    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    // --- contains ---

    public static boolean contains(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind) != NOT_FOUND;
    }

    public static boolean contains(final int[] array, final int valueToFind) {
        return indexOf(array, valueToFind) != NOT_FOUND;
    }

    public static boolean contains(final long[] array, final long valueToFind) {
        return indexOf(array, valueToFind) != NOT_FOUND;
    }

    public static boolean contains(final double[] array, final double valueToFind) {
        return indexOf(array, valueToFind) != NOT_FOUND;
    }

    public static boolean contains(final char[] array, final char valueToFind) {
        return indexOf(array, valueToFind) != NOT_FOUND;
    }

    public static boolean contains(final boolean[] array, final boolean valueToFind) {
        return indexOf(array, valueToFind) != NOT_FOUND;
    }

    // --- indexOf ---

    public static int indexOf(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if (array == null) {
            return NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    public static int indexOf(final int[] array, final int valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(final int[] array, final int valueToFind, int startIndex) {
        if (array == null) {
            return NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public static int indexOf(final long[] array, final long valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(final long[] array, final long valueToFind, int startIndex) {
        if (array == null) {
            return NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public static int indexOf(final double[] array, final double valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(final double[] array, final double valueToFind, int startIndex) {
        if (array == null) {
            return NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public static int indexOf(final char[] array, final char valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(final char[] array, final char valueToFind, int startIndex) {
        if (array == null) {
            return NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public static int indexOf(final boolean[] array, final boolean valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        if (array == null) {
            return NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    // --- lastIndexOf ---

    public static int lastIndexOf(final Object[] array, final Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if (array == null || startIndex < 0) {
            return NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i >= 0; i--) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    public static int lastIndexOf(final int[] array, final int valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final int[] array, final int valueToFind, int startIndex) {
        if (array == null || startIndex < 0) {
            return NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    // --- add ---

    @SuppressWarnings("unchecked")
    public static <T> T[] add(final T[] array, final T element) {
        final Class<?> type;
        if (array != null) {
            type = array.getClass().getComponentType();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        final int newLen = getLength(array) + 1;
        @SuppressWarnings("unchecked")
        final T[] newArray = (T[]) copyArrayGrow(array, type, newLen);
        newArray[newLen - 1] = element;
        return newArray;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] add(final T[] array, final int index, final T element) {
        final Class<?> type;
        if (array != null) {
            type = array.getClass().getComponentType();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        final int len = getLength(array);
        if (index > len || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + len);
        }
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) Array.newInstance(type, len + 1);
        if (index > 0) {
            System.arraycopy(array, 0, result, 0, index);
        }
        result[index] = element;
        if (index < len) {
            System.arraycopy(array, index, result, index + 1, len - index);
        }
        return result;
    }

    public static int[] add(final int[] array, final int element) {
        final int[] newArray = copyArrayGrow(array);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static long[] add(final long[] array, final long element) {
        final long[] newArray;
        if (array == null) {
            newArray = new long[1];
        } else {
            newArray = new long[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
        }
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static double[] add(final double[] array, final double element) {
        final double[] newArray;
        if (array == null) {
            newArray = new double[1];
        } else {
            newArray = new double[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
        }
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static boolean[] add(final boolean[] array, final boolean element) {
        final boolean[] newArray;
        if (array == null) {
            newArray = new boolean[1];
        } else {
            newArray = new boolean[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
        }
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static char[] add(final char[] array, final char element) {
        final char[] newArray;
        if (array == null) {
            newArray = new char[1];
        } else {
            newArray = new char[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
        }
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    // --- addAll ---

    @SuppressWarnings("unchecked")
    public static <T> T[] addAll(final T[] array1, @SuppressWarnings("unchecked") final T... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final Class<?> type = array1.getClass().getComponentType();
        @SuppressWarnings("unchecked")
        final T[] joinedArray = (T[]) Array.newInstance(type, array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (final ArrayStoreException e) {
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type.getName(), e);
            }
            throw e;
        }
        return joinedArray;
    }

    public static int[] addAll(final int[] array1, final int... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final int[] result = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    // --- remove ---

    @SuppressWarnings("unchecked")
    public static <T> T[] remove(final T[] array, final int index) {
        final int len = getLength(array);
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + len);
        }
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), len - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < len - 1) {
            System.arraycopy(array, index + 1, result, index, len - index - 1);
        }
        return result;
    }

    public static int[] remove(final int[] array, final int index) {
        final int len = getLength(array);
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + len);
        }
        final int[] result = new int[len - 1];
        System.arraycopy(array, 0, result, 0, index);
        if (index < len - 1) {
            System.arraycopy(array, index + 1, result, index, len - index - 1);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] removeElement(final T[] array, final Object element) {
        final int index = indexOf(array, element);
        if (index == NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] removeAllOccurrences(final T[] array, final Object element) {
        if (array == null) {
            return null;
        }
        int count = 0;
        for (final T item : array) {
            if (element == null ? item == null : element.equals(item)) {
                count++;
            }
        }
        if (count == 0) {
            return clone(array);
        }
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length - count);
        int j = 0;
        for (final T item : array) {
            if (!(element == null ? item == null : element.equals(item))) {
                result[j++] = item;
            }
        }
        return result;
    }

    // --- subarray ---

    @SuppressWarnings("unchecked")
    public static <T> T[] subarray(final T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        final Class<?> type = array.getClass().getComponentType();
        if (newSize <= 0) {
            @SuppressWarnings("unchecked")
            final T[] emptyArray = (T[]) Array.newInstance(type, 0);
            return emptyArray;
        }
        @SuppressWarnings("unchecked")
        final T[] subarray = (T[]) Array.newInstance(type, newSize);
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static int[] subarray(final int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_INT_ARRAY;
        }
        final int[] subarray = new int[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    // --- clone ---

    public static <T> T[] clone(final T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    public static int[] clone(final int[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    public static long[] clone(final long[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    public static double[] clone(final double[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    public static boolean[] clone(final boolean[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    public static char[] clone(final char[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    // --- reverse ---

    public static void reverse(final Object[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    public static void reverse(final Object[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(endIndexExclusive, array.length) - 1;
        while (j > i) {
            final Object tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(final int[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    public static void reverse(final int[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = Math.max(startIndexInclusive, 0);
        int j = Math.min(endIndexExclusive, array.length) - 1;
        while (j > i) {
            final int tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(final long[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        while (j > i) {
            final long tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(final double[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        while (j > i) {
            final double tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    public static void reverse(final char[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        while (j > i) {
            final char tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    // --- swap ---

    public static void swap(final Object[] array, final int offset1, final int offset2) {
        if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    public static void swap(final Object[] array, int offset1, int offset2, int len) {
        if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final Object aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    public static void swap(final int[] array, final int offset1, final int offset2) {
        if (isEmpty(array) || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        final int aux = array[offset1];
        array[offset1] = array[offset2];
        array[offset2] = aux;
    }

    // --- shift ---

    public static void shift(final Object[] array, final int offset) {
        if (array == null || array.length <= 1) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    public static void shift(final Object[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null || startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int n = endIndexExclusive - startIndexInclusive;
        if (n <= 1) {
            return;
        }
        offset %= n;
        if (offset < 0) {
            offset += n;
        }
        while (n > 1 && offset > 0) {
            final int nOffset = n - offset;
            if (offset > nOffset) {
                swap(array, startIndexInclusive, startIndexInclusive + n - nOffset, nOffset);
                n = offset;
                offset -= nOffset;
            } else if (offset < nOffset) {
                swap(array, startIndexInclusive, startIndexInclusive + nOffset, offset);
                startIndexInclusive += offset;
                n = nOffset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + nOffset, offset);
                break;
            }
        }
    }

    public static void shift(final int[] array, final int offset) {
        if (array == null || array.length <= 1) {
            return;
        }
        final int len = array.length;
        int o = offset % len;
        if (o < 0) {
            o += len;
        }
        if (o == 0) {
            return;
        }
        final int[] temp = new int[len];
        for (int i = 0; i < len; i++) {
            temp[(i + o) % len] = array[i];
        }
        System.arraycopy(temp, 0, array, 0, len);
    }

    // --- isSorted ---

    public static <T extends Comparable<? super T>> boolean isSorted(final T[] array) {
        return isSorted(array, Comparator.naturalOrder());
    }

    public static <T> boolean isSorted(final T[] array, final Comparator<T> comparator) {
        if (array == null || array.length < 2) {
            return true;
        }
        T previous = array[0];
        for (int i = 1; i < array.length; i++) {
            final T current = array[i];
            if (comparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public static boolean isSorted(final int[] array) {
        if (array == null || array.length < 2) {
            return true;
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }

    // --- nullToEmpty ---

    public static Object[] nullToEmpty(final Object[] array) {
        if (isEmpty(array)) {
            return EMPTY_OBJECT_ARRAY;
        }
        return array;
    }

    public static String[] nullToEmpty(final String[] array) {
        if (isEmpty(array)) {
            return EMPTY_STRING_ARRAY;
        }
        return array;
    }

    public static int[] nullToEmpty(final int[] array) {
        if (isEmpty(array)) {
            return EMPTY_INT_ARRAY;
        }
        return array;
    }

    public static long[] nullToEmpty(final long[] array) {
        if (isEmpty(array)) {
            return EMPTY_LONG_ARRAY;
        }
        return array;
    }

    public static double[] nullToEmpty(final double[] array) {
        if (isEmpty(array)) {
            return EMPTY_DOUBLE_ARRAY;
        }
        return array;
    }

    public static boolean[] nullToEmpty(final boolean[] array) {
        if (isEmpty(array)) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        return array;
    }

    public static char[] nullToEmpty(final char[] array) {
        if (isEmpty(array)) {
            return EMPTY_CHAR_ARRAY;
        }
        return array;
    }

    // --- toObject / toPrimitive ---

    public static Integer[] toObject(final int[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        final Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(array[i]);
        }
        return result;
    }

    public static Long[] toObject(final long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        final Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Long.valueOf(array[i]);
        }
        return result;
    }

    public static Double[] toObject(final double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        final Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(array[i]);
        }
        return result;
    }

    public static Boolean[] toObject(final boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        final Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? Boolean.TRUE : Boolean.FALSE;
        }
        return result;
    }

    public static Character[] toObject(final char[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        final Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Character.valueOf(array[i]);
        }
        return result;
    }

    public static int[] toPrimitive(final Integer[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    public static int[] toPrimitive(final Integer[] array, final int valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] != null ? array[i].intValue() : valueForNull;
        }
        return result;
    }

    public static long[] toPrimitive(final Long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    public static double[] toPrimitive(final Double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    public static boolean[] toPrimitive(final Boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].booleanValue();
        }
        return result;
    }

    public static char[] toPrimitive(final Character[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }

    // --- toMap ---

    public static Map<Object, Object> toMap(final Object[] array) {
        if (array == null) {
            return null;
        }
        final Map<Object, Object> map = new HashMap<>((int) (array.length * 1.5));
        for (int i = 0; i < array.length; i++) {
            final Object object = array[i];
            if (object instanceof Map.Entry<?, ?> entry) {
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[] inner) {
                if (inner.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + " has fewer than 2 elements");
                }
                map.put(inner[0], inner[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + " is not a Map.Entry or Object[]");
            }
        }
        return map;
    }

    // --- insert ---

    @SuppressWarnings("unchecked")
    public static <T> T[] insert(final int index, final T[] array, @SuppressWarnings("unchecked") final T... values) {
        if (array == null) {
            return null;
        }
        if (values == null || values.length == 0) {
            return clone(array);
        }
        if (index < 0 || index > array.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + array.length);
        }
        final Class<?> type = array.getClass().getComponentType();
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) Array.newInstance(type, array.length + values.length);
        System.arraycopy(values, 0, result, index, values.length);
        if (index > 0) {
            System.arraycopy(array, 0, result, 0, index);
        }
        if (index < array.length) {
            System.arraycopy(array, index, result, index + values.length, array.length - index);
        }
        return result;
    }

    // --- toArray ---

    @SafeVarargs
    public static <T> T[] toArray(final T... items) {
        return items;
    }

    // --- shuffle ---

    public static void shuffle(final Object[] array) {
        shuffle(array, new Random());
    }

    public static void shuffle(final Object[] array, final Random random) {
        if (array == null || array.length <= 1) {
            return;
        }
        for (int i = array.length; i > 1; i--) {
            swap(array, i - 1, random.nextInt(i));
        }
    }

    // --- Helpers ---

    @SuppressWarnings("unchecked")
    private static Object copyArrayGrow(final Object array, final Class<?> type, final int newLen) {
        if (array != null) {
            final int len = Array.getLength(array);
            final Object result = Array.newInstance(type, newLen);
            System.arraycopy(array, 0, result, 0, len);
            return result;
        }
        return Array.newInstance(type, newLen);
    }

    private static int[] copyArrayGrow(final int[] array) {
        if (array == null) {
            return new int[1];
        }
        final int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }
}

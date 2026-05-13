package com.lang.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharHelperTest {

    @Test
    void testIsAscii() {
        assertTrue(CharHelper.isAscii('a'));
        assertTrue(CharHelper.isAscii('0'));
        assertTrue(CharHelper.isAscii('\n'));
        assertFalse(CharHelper.isAscii('é'));
    }

    @Test
    void testIsAsciiAlpha() {
        assertTrue(CharHelper.isAsciiAlpha('a'));
        assertTrue(CharHelper.isAsciiAlpha('Z'));
        assertFalse(CharHelper.isAsciiAlpha('0'));
        assertFalse(CharHelper.isAsciiAlpha(' '));
    }

    @Test
    void testIsAsciiAlphaUpper() {
        assertTrue(CharHelper.isAsciiAlphaUpper('A'));
        assertTrue(CharHelper.isAsciiAlphaUpper('Z'));
        assertFalse(CharHelper.isAsciiAlphaUpper('a'));
        assertFalse(CharHelper.isAsciiAlphaUpper('0'));
    }

    @Test
    void testIsAsciiAlphaLower() {
        assertTrue(CharHelper.isAsciiAlphaLower('a'));
        assertTrue(CharHelper.isAsciiAlphaLower('z'));
        assertFalse(CharHelper.isAsciiAlphaLower('A'));
        assertFalse(CharHelper.isAsciiAlphaLower('0'));
    }

    @Test
    void testIsAsciiNumeric() {
        assertTrue(CharHelper.isAsciiNumeric('0'));
        assertTrue(CharHelper.isAsciiNumeric('9'));
        assertFalse(CharHelper.isAsciiNumeric('a'));
    }

    @Test
    void testIsAsciiAlphanumeric() {
        assertTrue(CharHelper.isAsciiAlphanumeric('a'));
        assertTrue(CharHelper.isAsciiAlphanumeric('0'));
        assertFalse(CharHelper.isAsciiAlphanumeric(' '));
        assertFalse(CharHelper.isAsciiAlphanumeric('!'));
    }

    @Test
    void testIsAsciiPrintable() {
        assertTrue(CharHelper.isAsciiPrintable('a'));
        assertTrue(CharHelper.isAsciiPrintable(' '));
        assertFalse(CharHelper.isAsciiPrintable('\n'));
        assertFalse(CharHelper.isAsciiPrintable('\0'));
    }

    @Test
    void testIsAsciiControl() {
        assertTrue(CharHelper.isAsciiControl('\n'));
        assertTrue(CharHelper.isAsciiControl('\0'));
        assertTrue(CharHelper.isAsciiControl((char) 127));
        assertFalse(CharHelper.isAsciiControl('a'));
        assertFalse(CharHelper.isAsciiControl(' '));
    }

    @Test
    void testIsHex() {
        assertTrue(CharHelper.isHex('0'));
        assertTrue(CharHelper.isHex('9'));
        assertTrue(CharHelper.isHex('a'));
        assertTrue(CharHelper.isHex('f'));
        assertTrue(CharHelper.isHex('A'));
        assertTrue(CharHelper.isHex('F'));
        assertFalse(CharHelper.isHex('g'));
        assertFalse(CharHelper.isHex('G'));
    }

    @Test
    void testIsOctal() {
        assertTrue(CharHelper.isOctal('0'));
        assertTrue(CharHelper.isOctal('7'));
        assertFalse(CharHelper.isOctal('8'));
        assertFalse(CharHelper.isOctal('a'));
    }

    @Test
    void testToCharFromCharacter() {
        assertEquals('a', CharHelper.toChar(Character.valueOf('a')));
        assertThrows(IllegalArgumentException.class, () -> CharHelper.toChar((Character) null));
    }

    @Test
    void testToCharFromCharacterDefault() {
        assertEquals('a', CharHelper.toChar(Character.valueOf('a'), 'x'));
        assertEquals('x', CharHelper.toChar((Character) null, 'x'));
    }

    @Test
    void testToCharFromString() {
        assertEquals('a', CharHelper.toChar("abc"));
        assertThrows(IllegalArgumentException.class, () -> CharHelper.toChar((String) null));
        assertThrows(IllegalArgumentException.class, () -> CharHelper.toChar(""));
    }

    @Test
    void testToCharFromStringDefault() {
        assertEquals('a', CharHelper.toChar("abc", 'x'));
        assertEquals('x', CharHelper.toChar("", 'x'));
        assertEquals('x', CharHelper.toChar((String) null, 'x'));
    }

    @Test
    void testToCharacterObject() {
        assertEquals(Character.valueOf('a'), CharHelper.toCharacterObject("abc"));
        assertNull(CharHelper.toCharacterObject(null));
        assertNull(CharHelper.toCharacterObject(""));
    }

    @Test
    void testToIntValue() {
        assertEquals(0, CharHelper.toIntValue('0'));
        assertEquals(9, CharHelper.toIntValue('9'));
        assertEquals(5, CharHelper.toIntValue('5'));
        assertThrows(IllegalArgumentException.class, () -> CharHelper.toIntValue('a'));
    }

    @Test
    void testToIntValueDefault() {
        assertEquals(5, CharHelper.toIntValue('5', -1));
        assertEquals(-1, CharHelper.toIntValue('a', -1));
    }

    @Test
    void testToIntValueCharacter() {
        assertEquals(5, CharHelper.toIntValue(Character.valueOf('5')));
        assertThrows(IllegalArgumentException.class, () -> CharHelper.toIntValue((Character) null));
    }

    @Test
    void testToIntValueCharacterDefault() {
        assertEquals(5, CharHelper.toIntValue(Character.valueOf('5'), -1));
        assertEquals(-1, CharHelper.toIntValue((Character) null, -1));
    }

    @Test
    void testToString() {
        assertEquals("a", CharHelper.toString('a'));
        assertEquals("0", CharHelper.toString('0'));
        assertNull(CharHelper.toString((Character) null));
    }

    @Test
    void testUnicodeEscaped() {
        assertEquals("\\u0061", CharHelper.unicodeEscaped('a'));
        assertEquals("\\u0041", CharHelper.unicodeEscaped('A'));
        assertNull(CharHelper.unicodeEscaped((Character) null));
    }

    @Test
    void testCompare() {
        assertTrue(CharHelper.compare('a', 'b') < 0);
        assertTrue(CharHelper.compare('b', 'a') > 0);
        assertEquals(0, CharHelper.compare('a', 'a'));
    }

    @Test
    void testConstants() {
        assertEquals('\n', CharHelper.LF);
        assertEquals('\r', CharHelper.CR);
        assertEquals('\0', CharHelper.NUL);
    }
}

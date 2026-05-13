package com.lang.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PlatformUtilsTest {

    @Test
    void testOsNameNotNull() {
        assertNotNull(PlatformUtils.OS_NAME);
    }

    @Test
    void testOsVersionNotNull() {
        assertNotNull(PlatformUtils.OS_VERSION);
    }

    @Test
    void testJavaVersionNotNull() {
        assertNotNull(PlatformUtils.JAVA_VERSION);
    }

    @Test
    void testJavaHomeNotNull() {
        assertNotNull(PlatformUtils.JAVA_HOME);
    }

    @Test
    void testUserNameNotNull() {
        assertNotNull(PlatformUtils.USER_NAME);
    }

    @Test
    void testLineSeparatorNotNull() {
        assertNotNull(PlatformUtils.LINE_SEPARATOR);
    }

    @Test
    void testExactlyOneOsFamily() {
        int count = 0;
        if (PlatformUtils.IS_OS_WINDOWS) count++;
        if (PlatformUtils.IS_OS_LINUX) count++;
        if (PlatformUtils.IS_OS_MAC) count++;
        assertTrue(count <= 1);
    }

    @Test
    void testIsOsUnix() {
        if (PlatformUtils.IS_OS_LINUX || PlatformUtils.IS_OS_MAC) {
            assertTrue(PlatformUtils.IS_OS_UNIX);
        }
    }

    @Test
    void testGetJavaHome() {
        final File home = PlatformUtils.getJavaHome();
        assertNotNull(home);
        assertTrue(home.exists());
    }

    @Test
    void testGetJavaIoTmpDir() {
        final File tmp = PlatformUtils.getJavaIoTmpDir();
        assertNotNull(tmp);
    }

    @Test
    void testGetUserDir() {
        final File dir = PlatformUtils.getUserDir();
        assertNotNull(dir);
        assertTrue(dir.exists());
    }

    @Test
    void testGetUserHome() {
        final File home = PlatformUtils.getUserHome();
        assertNotNull(home);
    }

    @Test
    void testIsJavaVersionAtLeast() {
        assertTrue(PlatformUtils.isJavaVersionAtLeast(11));
        assertTrue(PlatformUtils.isJavaVersionAtLeast(17));
        assertFalse(PlatformUtils.isJavaVersionAtLeast(99));
    }

    @Test
    void testGetSystemProperty() {
        assertEquals(System.getProperty("os.name"), PlatformUtils.getSystemProperty("os.name"));
        assertNull(PlatformUtils.getSystemProperty("nonexistent.property.xyz"));
    }
}

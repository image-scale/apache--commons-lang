package com.lang.util;

import java.io.File;

public final class PlatformUtils {

    private PlatformUtils() {}

    public static final String OS_NAME = getSystemProperty("os.name");
    public static final String OS_VERSION = getSystemProperty("os.version");
    public static final String OS_ARCH = getSystemProperty("os.arch");
    public static final String JAVA_VERSION = getSystemProperty("java.version");
    public static final String JAVA_VENDOR = getSystemProperty("java.vendor");
    public static final String JAVA_HOME = getSystemProperty("java.home");
    public static final String JAVA_CLASS_PATH = getSystemProperty("java.class.path");
    public static final String JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir");
    public static final String USER_NAME = getSystemProperty("user.name");
    public static final String USER_HOME = getSystemProperty("user.home");
    public static final String USER_DIR = getSystemProperty("user.dir");
    public static final String LINE_SEPARATOR = getSystemProperty("line.separator");
    public static final String FILE_SEPARATOR = getSystemProperty("file.separator");
    public static final String PATH_SEPARATOR = getSystemProperty("path.separator");

    public static final boolean IS_OS_WINDOWS = isOsNameMatch("Windows");
    public static final boolean IS_OS_LINUX = isOsNameMatch("Linux");
    public static final boolean IS_OS_MAC = isOsNameMatch("Mac");
    public static final boolean IS_OS_UNIX = IS_OS_LINUX || IS_OS_MAC
            || isOsNameMatch("AIX") || isOsNameMatch("FreeBSD")
            || isOsNameMatch("HP-UX") || isOsNameMatch("SunOS");

    public static final boolean IS_JAVA_17 = isJavaVersionMatch("17");
    public static final boolean IS_JAVA_21 = isJavaVersionMatch("21");

    public static String getSystemProperty(final String property) {
        try {
            return System.getProperty(property);
        } catch (final SecurityException e) {
            return null;
        }
    }

    public static File getJavaHome() {
        return JAVA_HOME != null ? new File(JAVA_HOME) : null;
    }

    public static File getJavaIoTmpDir() {
        return JAVA_IO_TMPDIR != null ? new File(JAVA_IO_TMPDIR) : null;
    }

    public static File getUserDir() {
        return USER_DIR != null ? new File(USER_DIR) : null;
    }

    public static File getUserHome() {
        return USER_HOME != null ? new File(USER_HOME) : null;
    }

    public static boolean isJavaVersionAtLeast(final int major) {
        if (JAVA_VERSION == null) {
            return false;
        }
        try {
            final String majorStr = JAVA_VERSION.contains(".")
                    ? JAVA_VERSION.substring(0, JAVA_VERSION.indexOf('.'))
                    : JAVA_VERSION;
            return Integer.parseInt(majorStr) >= major;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOsNameMatch(final String prefix) {
        return OS_NAME != null && OS_NAME.startsWith(prefix);
    }

    private static boolean isJavaVersionMatch(final String version) {
        return JAVA_VERSION != null && JAVA_VERSION.startsWith(version);
    }
}

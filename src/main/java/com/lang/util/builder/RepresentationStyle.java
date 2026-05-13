package com.lang.util.builder;

import java.lang.reflect.Array;
import java.util.Arrays;

public class RepresentationStyle {

    public static final RepresentationStyle DEFAULT = new RepresentationStyle(
            true, true, false, true,
            "[", "]", "=", ",", false, "{", "}", "<null>");

    public static final RepresentationStyle MULTI_LINE = new RepresentationStyle(
            true, true, false, true,
            "[", System.lineSeparator() + "]", "=",
            System.lineSeparator() + "  ", true, "{", "}", "<null>");

    public static final RepresentationStyle SHORT_PREFIX = new RepresentationStyle(
            true, false, true, true,
            "[", "]", "=", ",", false, "{", "}", "<null>");

    public static final RepresentationStyle SIMPLE = new RepresentationStyle(
            false, false, false, false,
            "", "", "=", ",", false, "{", "}", "<null>");

    public static final RepresentationStyle JSON = new RepresentationStyle(
            false, false, false, true,
            "{", "}", ":", ",", false, "[", "]", "null");

    private final boolean useClassName;
    private final boolean useIdentityHashCode;
    private final boolean useShortClassName;
    private final boolean useFieldNames;
    private final String contentStart;
    private final String contentEnd;
    private final String fieldNameValueSeparator;
    private final String fieldSeparator;
    private final boolean fieldSeparatorAtStart;
    private final String arrayStart;
    private final String arrayEnd;
    private final String nullText;

    private RepresentationStyle(final boolean useClassName, final boolean useIdentityHashCode,
                                final boolean useShortClassName, final boolean useFieldNames,
                                final String contentStart, final String contentEnd,
                                final String fieldNameValueSeparator, final String fieldSeparator,
                                final boolean fieldSeparatorAtStart,
                                final String arrayStart, final String arrayEnd,
                                final String nullText) {
        this.useClassName = useClassName;
        this.useIdentityHashCode = useIdentityHashCode;
        this.useShortClassName = useShortClassName;
        this.useFieldNames = useFieldNames;
        this.contentStart = contentStart;
        this.contentEnd = contentEnd;
        this.fieldNameValueSeparator = fieldNameValueSeparator;
        this.fieldSeparator = fieldSeparator;
        this.fieldSeparatorAtStart = fieldSeparatorAtStart;
        this.arrayStart = arrayStart;
        this.arrayEnd = arrayEnd;
        this.nullText = nullText;
    }

    void appendStart(final StringBuilder sb, final Object object) {
        if (object != null) {
            if (useClassName) {
                if (useShortClassName) {
                    final String fullName = object.getClass().getName();
                    final int lastDot = fullName.lastIndexOf('.');
                    sb.append(lastDot < 0 ? fullName : fullName.substring(lastDot + 1));
                } else {
                    sb.append(object.getClass().getName());
                }
                if (useIdentityHashCode) {
                    sb.append('@');
                    sb.append(Integer.toHexString(System.identityHashCode(object)));
                }
            }
            sb.append(contentStart);
        }
    }

    void appendEnd(final StringBuilder sb) {
        sb.append(contentEnd);
    }

    void appendField(final StringBuilder sb, final String fieldName, final Object value,
                     final boolean isFirst) {
        if (!isFirst) {
            sb.append(fieldSeparator);
        } else if (fieldSeparatorAtStart) {
            sb.append(fieldSeparator);
        }
        if (this == JSON) {
            appendJsonField(sb, fieldName, value);
        } else {
            if (useFieldNames) {
                sb.append(fieldName);
                sb.append(fieldNameValueSeparator);
            }
            appendValue(sb, value);
        }
    }

    private void appendJsonField(final StringBuilder sb, final String fieldName, final Object value) {
        sb.append('"').append(fieldName).append('"');
        sb.append(fieldNameValueSeparator);
        if (value == null) {
            sb.append(nullText);
        } else if (value instanceof CharSequence) {
            sb.append('"').append(value).append('"');
        } else if (value instanceof Character) {
            sb.append('"').append(value).append('"');
        } else if (value.getClass().isArray()) {
            appendArrayValue(sb, value);
        } else {
            sb.append(value);
        }
    }

    void appendValue(final StringBuilder sb, final Object value) {
        if (value == null) {
            sb.append(nullText);
        } else if (value.getClass().isArray()) {
            appendArrayValue(sb, value);
        } else {
            sb.append(value);
        }
    }

    private void appendArrayValue(final StringBuilder sb, final Object array) {
        sb.append(arrayStart);
        final int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            final Object item = Array.get(array, i);
            if (this == JSON) {
                if (item == null) {
                    sb.append(nullText);
                } else if (item instanceof CharSequence) {
                    sb.append('"').append(item).append('"');
                } else if (item instanceof Character) {
                    sb.append('"').append(item).append('"');
                } else {
                    sb.append(item);
                }
            } else {
                if (item == null) {
                    sb.append(nullText);
                } else {
                    sb.append(item);
                }
            }
        }
        sb.append(arrayEnd);
    }
}

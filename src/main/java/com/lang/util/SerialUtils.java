package com.lang.util;

import java.io.*;

public final class SerialUtils {

    private SerialUtils() {}

    public static byte[] serialize(final Serializable obj) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        } catch (final IOException e) {
            throw new UncheckedIOException("Serialization failed", e);
        }
        return baos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(final byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Data must not be null");
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (T) ois.readObject();
        } catch (final IOException e) {
            throw new UncheckedIOException("Deserialization failed", e);
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException("Deserialization failed: class not found", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(final T object) {
        if (object == null) {
            return null;
        }
        final byte[] data = serialize(object);
        return (T) deserialize(data);
    }

    public static <T extends Serializable> T roundtrip(final T object) {
        return clone(object);
    }
}

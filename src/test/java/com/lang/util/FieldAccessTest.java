package com.lang.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldAccessTest {

    @Deprecated
    static class GrandParent {
        public String publicGrand = "gpub";
        protected String protectedGrand = "gprot";
        private String privateGrand = "gpriv";
        public static final String STATIC_GRAND = "static_grand";
    }

    static class Parent extends GrandParent {
        public String publicParent = "ppub";
        private String privateParent = "ppriv";
        @Deprecated
        public String annotatedField = "anno";
    }

    static class Child extends Parent {
        public String publicChild = "cpub";
        private String privateChild = "cpriv";
        public int primitiveField = 42;
    }

    interface HasConstant {
        String IFACE_CONST = "iface_value";
    }

    static class Implementor extends GrandParent implements HasConstant {
        public String implField = "impl";
    }

    // --- getField tests ---

    @Test
    void testGetFieldDirect() {
        final Field f = FieldAccess.getField(Child.class, "publicChild");
        assertNotNull(f);
        assertEquals("publicChild", f.getName());
    }

    @Test
    void testGetFieldInherited() {
        final Field f = FieldAccess.getField(Child.class, "publicParent");
        assertNotNull(f);
        assertEquals("publicParent", f.getName());
    }

    @Test
    void testGetFieldFromGrandParent() {
        final Field f = FieldAccess.getField(Child.class, "publicGrand");
        assertNotNull(f);
    }

    @Test
    void testGetFieldPrivateWithoutForceAccess() {
        final Field f = FieldAccess.getField(Child.class, "privateChild");
        assertNotNull(f);
    }

    @Test
    void testGetFieldPrivateWithForceAccess() {
        final Field f = FieldAccess.getField(Child.class, "privateChild", true);
        assertNotNull(f);
        assertEquals("privateChild", f.getName());
    }

    @Test
    void testGetFieldNotFound() {
        assertNull(FieldAccess.getField(Child.class, "nonexistent"));
    }

    @Test
    void testGetFieldNullClass() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.getField(null, "field"));
    }

    @Test
    void testGetFieldNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.getField(Child.class, null));
    }

    @Test
    void testGetFieldFromInterface() {
        final Field f = FieldAccess.getField(Implementor.class, "IFACE_CONST");
        assertNotNull(f);
        assertEquals("IFACE_CONST", f.getName());
    }

    // --- getDeclaredField tests ---

    @Test
    void testGetDeclaredFieldDirect() {
        final Field f = FieldAccess.getDeclaredField(Child.class, "publicChild", false);
        assertNotNull(f);
    }

    @Test
    void testGetDeclaredFieldDoesNotWalkHierarchy() {
        assertNull(FieldAccess.getDeclaredField(Child.class, "publicParent", false));
    }

    @Test
    void testGetDeclaredFieldPrivateForceAccess() {
        final Field f = FieldAccess.getDeclaredField(Child.class, "privateChild", true);
        assertNotNull(f);
    }

    @Test
    void testGetDeclaredFieldNullClass() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.getDeclaredField(null, "f", false));
    }

    @Test
    void testGetDeclaredFieldNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.getDeclaredField(Child.class, null, false));
    }

    // --- getAllFields tests ---

    @Test
    void testGetAllFields() {
        final List<Field> fields = FieldAccess.getAllFields(Child.class);
        assertFalse(fields.isEmpty());
        final List<String> names = fields.stream().map(Field::getName).toList();
        assertTrue(names.contains("publicChild"));
        assertTrue(names.contains("privateChild"));
        assertTrue(names.contains("publicParent"));
        assertTrue(names.contains("publicGrand"));
        assertTrue(names.contains("privateGrand"));
    }

    @Test
    void testGetAllFieldsNullClass() {
        assertThrows(IllegalArgumentException.class, () -> FieldAccess.getAllFields(null));
    }

    // --- getFieldsWithAnnotation tests ---

    @Test
    void testGetFieldsWithAnnotation() {
        final List<Field> deprecated = FieldAccess.getFieldsWithAnnotation(
                Parent.class, Deprecated.class);
        assertFalse(deprecated.isEmpty());
        assertTrue(deprecated.stream().anyMatch(f -> f.getName().equals("annotatedField")));
    }

    @Test
    void testGetFieldsWithAnnotationNone() {
        final List<Field> result = FieldAccess.getFieldsWithAnnotation(
                Child.class, SuppressWarnings.class);
        assertTrue(result.isEmpty());
    }

    // --- readField tests ---

    @Test
    void testReadFieldPublic() throws Exception {
        final Child c = new Child();
        assertEquals("cpub", FieldAccess.readField(c, "publicChild"));
    }

    @Test
    void testReadFieldPrivateForceAccess() throws Exception {
        final Child c = new Child();
        assertEquals("cpriv", FieldAccess.readField(c, "privateChild", true));
    }

    @Test
    void testReadFieldInherited() throws Exception {
        final Child c = new Child();
        assertEquals("ppub", FieldAccess.readField(c, "publicParent"));
    }

    @Test
    void testReadFieldPrimitive() throws Exception {
        final Child c = new Child();
        assertEquals(42, FieldAccess.readField(c, "primitiveField"));
    }

    @Test
    void testReadFieldNullTarget() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.readField(null, "field"));
    }

    @Test
    void testReadFieldNotFound() {
        final Child c = new Child();
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.readField(c, "noSuchField"));
    }

    // --- readStaticField tests ---

    @Test
    void testReadStaticField() throws Exception {
        assertEquals("static_grand",
                FieldAccess.readStaticField(GrandParent.class, "STATIC_GRAND"));
    }

    @Test
    void testReadStaticFieldNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.readStaticField(Child.class, "noField"));
    }

    // --- writeField tests ---

    @Test
    void testWriteFieldPublic() throws Exception {
        final Child c = new Child();
        FieldAccess.writeField(c, "publicChild", "newValue");
        assertEquals("newValue", c.publicChild);
    }

    @Test
    void testWriteFieldPrivateForceAccess() throws Exception {
        final Child c = new Child();
        FieldAccess.writeField(c, "privateChild", "newPriv", true);
        assertEquals("newPriv", FieldAccess.readField(c, "privateChild", true));
    }

    @Test
    void testWriteFieldPrimitive() throws Exception {
        final Child c = new Child();
        FieldAccess.writeField(c, "primitiveField", 99);
        assertEquals(99, c.primitiveField);
    }

    @Test
    void testWriteFieldNullTarget() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.writeField(null, "field", "val"));
    }

    @Test
    void testWriteFieldNotFound() {
        final Child c = new Child();
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.writeField(c, "noField", "val"));
    }

    // --- writeStaticField tests ---

    @Test
    void testWriteStaticField() throws Exception {
        FieldAccess.writeStaticField(StaticHolder.class, "mutableStatic", "updated", true);
        assertEquals("updated", StaticHolder.mutableStatic);
        StaticHolder.mutableStatic = "original";
    }

    @Test
    void testWriteStaticFieldNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> FieldAccess.writeStaticField(Child.class, "noField", "val", true));
    }

    static class StaticHolder {
        static String mutableStatic = "original";
    }
}

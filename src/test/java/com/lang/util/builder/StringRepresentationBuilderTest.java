package com.lang.util.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringRepresentationBuilderTest {

    @Test
    void testDefaultStyle() {
        final String result = new StringRepresentationBuilder(new SamplePerson("John", 33, false))
                .append("name", "John")
                .append("age", 33)
                .append("smoker", false)
                .build();
        assertTrue(result.startsWith("com.lang.util.builder.StringRepresentationBuilderTest$SamplePerson@"));
        assertTrue(result.contains("[name=John,age=33,smoker=false]"));
    }

    @Test
    void testDefaultStyleFormat() {
        final SamplePerson person = new SamplePerson("Alice", 25, true);
        final String result = new StringRepresentationBuilder(person, RepresentationStyle.DEFAULT)
                .append("name", "Alice")
                .append("age", 25)
                .build();
        assertTrue(result.contains("SamplePerson@"));
        assertTrue(result.contains("[name=Alice,age=25]"));
    }

    @Test
    void testShortPrefixStyle() {
        final SamplePerson person = new SamplePerson("Bob", 40, false);
        final String result = new StringRepresentationBuilder(person, RepresentationStyle.SHORT_PREFIX)
                .append("name", "Bob")
                .append("age", 40)
                .build();
        assertTrue(result.startsWith("StringRepresentationBuilderTest$SamplePerson["));
        assertFalse(result.contains("@"));
        assertTrue(result.contains("name=Bob,age=40"));
        assertTrue(result.endsWith("]"));
    }

    @Test
    void testSimpleStyle() {
        final String result = new StringRepresentationBuilder(
                new SamplePerson("Carol", 28, true), RepresentationStyle.SIMPLE)
                .append("name", "Carol")
                .append("age", 28)
                .append("smoker", true)
                .build();
        assertEquals("Carol,28,true", result);
    }

    @Test
    void testMultiLineStyle() {
        final SamplePerson person = new SamplePerson("Dave", 55, false);
        final String result = new StringRepresentationBuilder(person, RepresentationStyle.MULTI_LINE)
                .append("name", "Dave")
                .append("age", 55)
                .build();
        assertTrue(result.contains("SamplePerson@"));
        assertTrue(result.contains("["));
        assertTrue(result.contains("name=Dave"));
        assertTrue(result.contains("age=55"));
        assertTrue(result.endsWith("]"));
        assertTrue(result.contains(System.lineSeparator()));
    }

    @Test
    void testJsonStyle() {
        final String result = new StringRepresentationBuilder(
                new SamplePerson("Eve", 22, true), RepresentationStyle.JSON)
                .append("name", "Eve")
                .append("age", 22)
                .append("smoker", true)
                .build();
        assertEquals("{\"name\":\"Eve\",\"age\":22,\"smoker\":true}", result);
    }

    @Test
    void testJsonStyleNull() {
        final String result = new StringRepresentationBuilder(
                new SamplePerson(null, 0, false), RepresentationStyle.JSON)
                .append("name", (Object) null)
                .append("age", 0)
                .build();
        assertEquals("{\"name\":null,\"age\":0}", result);
    }

    @Test
    void testJsonStyleArray() {
        final String result = new StringRepresentationBuilder(
                new Object(), RepresentationStyle.JSON)
                .append("items", new int[]{1, 2, 3})
                .build();
        assertEquals("{\"items\":[1,2,3]}", result);
    }

    @Test
    void testDefaultStyleNull() {
        final String result = new StringRepresentationBuilder(new Object())
                .append("field", (Object) null)
                .build();
        assertTrue(result.contains("field=<null>"));
    }

    @Test
    void testDefaultStyleArray() {
        final String result = new StringRepresentationBuilder(new Object())
                .append("nums", new int[]{1, 2})
                .build();
        assertTrue(result.contains("nums={1,2}"));
    }

    @Test
    void testAppendAllPrimitives() {
        final String result = new StringRepresentationBuilder(new Object(), RepresentationStyle.SIMPLE)
                .append("a", 42)
                .append("b", 100L)
                .append("c", 3.14)
                .append("d", 2.5f)
                .append("e", true)
                .append("f", (short) 7)
                .append("g", (byte) 3)
                .append("h", 'x')
                .build();
        assertEquals("42,100,3.14,2.5,true,7,3,x", result);
    }

    @Test
    void testAppendSuper() {
        final String result = new StringRepresentationBuilder(new Object(), RepresentationStyle.SIMPLE)
                .appendSuper("parentInfo")
                .append("x", 1)
                .build();
        assertEquals("parentInfo,1", result);
    }

    @Test
    void testAppendSuperNull() {
        final String result = new StringRepresentationBuilder(new Object(), RepresentationStyle.SIMPLE)
                .appendSuper(null)
                .append("x", 1)
                .build();
        assertEquals("1", result);
    }

    @Test
    void testToStringDoesNotConsumeBuilder() {
        final StringRepresentationBuilder builder = new StringRepresentationBuilder(
                new Object(), RepresentationStyle.SIMPLE);
        builder.append("x", 1);
        final String s1 = builder.toString();
        builder.append("y", 2);
        final String s2 = builder.build();
        assertEquals("1", s1);
        assertEquals("1,2", s2);
    }

    @Test
    void testNullStyle() {
        final String result = new StringRepresentationBuilder(new Object(), null)
                .append("x", 1)
                .build();
        assertTrue(result.contains("x=1"));
    }

    @Test
    void testReflectionToString() {
        final SamplePerson person = new SamplePerson("Frank", 45, true);
        final String result = StringRepresentationBuilder.reflectionToString(person);
        assertTrue(result.contains("SamplePerson@"));
        assertTrue(result.contains("name=Frank"));
        assertTrue(result.contains("age=45"));
        assertTrue(result.contains("smoker=true"));
    }

    @Test
    void testReflectionToStringWithStyle() {
        final SamplePerson person = new SamplePerson("Grace", 30, false);
        final String result = StringRepresentationBuilder.reflectionToString(
                person, RepresentationStyle.SIMPLE);
        assertTrue(result.contains("Grace"));
        assertTrue(result.contains("30"));
        assertTrue(result.contains("false"));
        assertFalse(result.contains("SamplePerson"));
    }

    @Test
    void testReflectionToStringExcludeFields() {
        final SamplePerson person = new SamplePerson("Hank", 50, true);
        final String result = StringRepresentationBuilder.reflectionToString(
                person, RepresentationStyle.DEFAULT, false, null, "smoker");
        assertTrue(result.contains("name=Hank"));
        assertTrue(result.contains("age=50"));
        assertFalse(result.contains("smoker"));
    }

    @Test
    void testReflectionToStringNull() {
        assertEquals("<null>", StringRepresentationBuilder.reflectionToString(null));
    }

    @Test
    void testReflectionToStringNullStyle() {
        final SamplePerson person = new SamplePerson("Ivy", 20, false);
        final String result = StringRepresentationBuilder.reflectionToString(person, null);
        assertTrue(result.contains("name=Ivy"));
    }

    @Test
    void testJsonStyleStringValues() {
        final String result = new StringRepresentationBuilder(new Object(), RepresentationStyle.JSON)
                .append("msg", "hello")
                .append("count", 5)
                .build();
        assertEquals("{\"msg\":\"hello\",\"count\":5}", result);
    }

    @Test
    void testEmptyBuilder() {
        final String result = new StringRepresentationBuilder(new Object(), RepresentationStyle.SIMPLE)
                .build();
        assertEquals("", result);
    }

    @Test
    void testSingleField() {
        final String result = new StringRepresentationBuilder(new Object(), RepresentationStyle.SIMPLE)
                .append("x", 42)
                .build();
        assertEquals("42", result);
    }

    @Test
    void testObjectArrayInDefaultStyle() {
        final String result = new StringRepresentationBuilder(new Object())
                .append("tags", new String[]{"a", "b"})
                .build();
        assertTrue(result.contains("tags={a,b}"));
    }

    static class SamplePerson {
        final String name;
        final int age;
        final boolean smoker;
        SamplePerson(String name, int age, boolean smoker) {
            this.name = name;
            this.age = age;
            this.smoker = smoker;
        }
    }
}

package fr.pinguet62.java2typescript.converter;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Test;

/** @see TypeConverter */
public final class TypeConverterTest {

    @Test
    public void test_native() {
        Function<String, String> converter = new TypeConverter();

        assertEquals("boolean", converter.apply("boolean"));
        assertEquals("boolean", converter.apply("Boolean"));

        assertEquals("number", converter.apply("byte"));
        assertEquals("number", converter.apply("Byte"));
        assertEquals("number", converter.apply("char"));
        assertEquals("number", converter.apply("Character"));
        assertEquals("number", converter.apply("short"));
        assertEquals("number", converter.apply("Short"));
        assertEquals("number", converter.apply("int"));
        assertEquals("number", converter.apply("Integer"));
        assertEquals("number", converter.apply("long"));
        assertEquals("number", converter.apply("Long"));
        assertEquals("number", converter.apply("float"));
        assertEquals("number", converter.apply("Float"));

        assertEquals("string", converter.apply("String"));

        // assertEquals("Array<number>", converter.apply("Iterable<Integer>"));
        // assertEquals("Array<number>", converter.apply("Collection<Integer>"));
        // assertEquals("Array<number>", converter.apply("List<Integer>"));

        // TODO Tuple
        // TODO Enum
        // TODO Any
        // TODO Void
    }

    @Test
    public void test_custom() {
        Function<String, String> converter = new TypeConverter();

        assertEquals("CustomDto", converter.apply("CustomDto"));
    }

}
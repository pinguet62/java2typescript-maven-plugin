package fr.pinguet62.java2typescript.java;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** @see Type */
public class TypeTest {

    @Test
    public void test_toTypeScript() {
        assertEquals("Array<string>", new Type("List", asList(new Type("String"))).toTypeScript());
        assertEquals("Array<string>", new Type("java.util.List", asList(new Type("String"))).toTypeScript());
    }

}
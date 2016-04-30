package fr.pinguet62.java2typescript.java;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** @see Attribute */
public final class AttributeTest {

    /** @see Attribute#toTypeScript() */
    @Test
    public void test_toTypeScript() {
        Type type = new Type("Type");
        Attribute attribute = new Attribute(type, "attribute");
        assertEquals("    attribute: Type;", attribute.toTypeScript());
    }

}
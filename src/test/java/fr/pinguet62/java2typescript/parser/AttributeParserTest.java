package fr.pinguet62.java2typescript.parser;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.pinguet62.java2typescript.java.Attribute;

/** @see AttributeParser */
public final class AttributeParserTest {

    /** @see AttributeParser#apply(String) */
    @Test
    public void test() {
        AttributeParser parser = new AttributeParser("", asList());
        Attribute attribute = parser.apply("private Type name;");
        assertEquals("name", attribute.getName());
        assertEquals("Type", attribute.getType().getName());
    }

}
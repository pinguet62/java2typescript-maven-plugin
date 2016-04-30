package fr.pinguet62.java2typescript.parser;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.pinguet62.java2typescript.java.Type;

/** @see TypeParser */
public final class TypeParserTest {

    @Test
    public void test() {
        Type map = new TypeParser("", asList()).apply("Map<List<String>, Collection<Integer>>");

        assertNotNull(map);
        assertEquals(2, map.getGenericArguments().size());
        {
            Type list = map.getGenericArguments().get(0);
            assertEquals(1, list.getGenericArguments().size());
            assertEquals("List", list.getName());
            {
                Type string = list.getGenericArguments().get(0);
                assertEquals("String", string.getName());
            }
        }
        {
            Type collection = map.getGenericArguments().get(1);
            assertEquals(1, collection.getGenericArguments().size());
            assertEquals("Collection", collection.getName());
            {
                Type integer = collection.getGenericArguments().get(0);
                assertEquals("Integer", integer.getName());
            }
        }
    }

}
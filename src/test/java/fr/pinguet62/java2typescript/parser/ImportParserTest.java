package fr.pinguet62.java2typescript.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.pinguet62.java2typescript.java.Import;

/** @see ImportParser */
public final class ImportParserTest {

    @Test
    public void test() {
        assertEquals(new Import("java.util.List"), new ImportParser().apply("import java.util.List;"));
    }

}
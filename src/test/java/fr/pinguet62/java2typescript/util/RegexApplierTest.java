package fr.pinguet62.java2typescript.util;

import static fr.pinguet62.java2typescript.util.RegexApplier.from;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.pinguet62.java2typescript.util.RegexApplier;

/** @see RegexApplier */
public final class RegexApplierTest {

    @Test
    public void test() {
        assertEquals("Integer", from("List<Integer>").extract("<.+>").remove("^<").remove(">$").toString());
    }

}
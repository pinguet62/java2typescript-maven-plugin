package fr.pinguet62.java2typescript.converter;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/** @see FileConverter */
public final class FileConverterTest {

    @Test
    public void test_primitive() {
        FileConverter converter = new FileConverter();
        // @formatter:off
        List<String> java = asList(
                "public class TestDto {",
                "    private int attribute;",
                "}"
            );
        // @formatter:on
// @formatter:off
        List<String> typescript = asList(
                "export class TestDto {",
                "    attribute: number;",
                "}"
            );
        // @formatter:on
        List<String> converted = converter.apply(java).stream().filter(line -> !line.isEmpty()).collect(toList());
        assertEquals(typescript, converted);
    }

    @Test
    public void test_array() {
        FileConverter converter = new FileConverter();
        // @formatter:off
        List<String> java = asList(
                "import java.util.List;",
                "public class TestDto {",
                "    private List<Integer> attribute;",
                "}"
            );
        // @formatter:on
// @formatter:off
        List<String> typescript = asList(
                "export class TestDto {",
                "    attribute: Array<number>;",
                "}"
            );
        // @formatter:on
        List<String> converted = converter.apply(java).stream().filter(line -> !line.isEmpty()).collect(toList());
        assertEquals(typescript, converted);
    }

    @Test
    public void test_dependency_samePackage() {
        FileConverter converter = new FileConverter();
        // @formatter:off
        List<String> java = asList(
                "package fr.pinguet62;",
                "import fr.pinguet62.OtherDto;",
                "public class TestDto {",
                "    private OtherDto attribute;",
                "}"
            );
        // @formatter:on
// @formatter:off
        List<String> typescript = asList(
                "import {OtherDto} from './OtherDto';",
                "export class TestDto {",
                "    attribute: OtherDto;",
                "}"
            );
        // @formatter:on
        List<String> converted = converter.apply(java).stream().filter(line -> !line.isEmpty()).collect(toList());
        assertEquals(typescript, converted);
    }

}
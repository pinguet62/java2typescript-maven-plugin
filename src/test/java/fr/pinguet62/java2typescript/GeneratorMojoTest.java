package fr.pinguet62.java2typescript;

import static java.nio.file.Files.exists;
import static java.nio.file.Paths.get;
import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** @see GeneratorMojo */
public final class GeneratorMojoTest {

    private static final String tgtDir = "target/out/";

    /** Path to DTO Java root folder.<br> Initialized {@link Before} each {@link Test}. */
    private String srcDir;

    @After
    public void after() throws IOException {
        clean();
    }

    @Before
    public void before() throws IOException {
        clean();
    }

    /** Clear generated resources. */
    private void clean() throws IOException {
        deleteDirectory(new File(tgtDir));
    }

    private void executeCase(String folder) {
        GeneratorMojo mojo = new GeneratorMojo();
        srcDir = Paths.get("src/test/resources", folder, "fr/pinguet62/java2typescript/dto").toString();
        mojo.setSrcDir(srcDir);
        mojo.setTgtDir(tgtDir);
        try {
            mojo.execute();
        } catch (MojoExecutionException e) {
            fail();
        }
    }

    /** The DTO aggregate another DTO. */
    @Test
    public void test_dependencies() throws Exception {
        String parent1Filename = "Parent1Dto";
        String parent2Filename = "Parent2Dto";
        String child1Filename = "child1/Child1Dto";
        String child2Filename = "child2/Child2Dto";

        executeCase("dependencies");

        assertTrue(exists(get(srcDir, parent1Filename + ".java")));
        assertTrue(exists(get(srcDir, parent2Filename + ".java")));
        assertTrue(exists(get(srcDir, child1Filename + ".java")));
        assertTrue(exists(get(srcDir, child2Filename + ".java")));

        // Parent1
        List<String> parent1TgtContent = Files.readAllLines(get(tgtDir, parent1Filename + ".ts"));
        assertTrue(parent1TgtContent.contains("import {Parent2Dto} from './Parent2Dto';"));
        assertTrue(parent1TgtContent.contains("import {Child1Dto} from './child1/Child1Dto';"));
        assertTrue(parent1TgtContent.contains("import {Child2Dto} from './child2/Child2Dto';"));
        // Parent2
        List<String> parent2TgtContent = Files.readAllLines(get(tgtDir, parent2Filename + ".ts"));
        assertTrue(parent2TgtContent.contains("import {Parent1Dto} from './Parent1Dto';"));
        assertTrue(parent2TgtContent.contains("import {Child1Dto} from './child1/Child1Dto';"));
        assertTrue(parent2TgtContent.contains("import {Child2Dto} from './child2/Child2Dto';"));
        // Child1
        List<String> child1TgtContent = Files.readAllLines(get(tgtDir, child1Filename + ".ts"));
        assertTrue(child1TgtContent.contains("import {Parent1Dto} from '../Parent1Dto';"));
        assertTrue(child1TgtContent.contains("import {Parent2Dto} from '../Parent2Dto';"));
        assertTrue(child1TgtContent.contains("import {Child2Dto} from '../child2/Child2Dto';"));
        // Child2
        List<String> child2TgtContent = Files.readAllLines(get(tgtDir, child2Filename + ".ts"));
        assertTrue(child2TgtContent.contains("import {Parent1Dto} from '../Parent1Dto';"));
        assertTrue(child2TgtContent.contains("import {Parent2Dto} from '../Parent2Dto';"));
        assertTrue(child2TgtContent.contains("import {Child1Dto} from '../child1/Child1Dto';"));
    }

    /** A non-matching filename must be ignored. */
    @Test
    public void test_notMaching() {
        executeCase("not-matching");

        String filename = "NotMatching";
        assertTrue(exists(get(srcDir, filename + ".java")));
        assertFalse(exists(get(tgtDir, filename + ".ts")));
    }

    /** Iterate recursively on source folder. */
    @Test
    public void test_recursive() {
        executeCase("recursive");

        // Root
        String rootFilename = "RootDto";
        assertTrue(exists(get(srcDir, rootFilename + ".java")));
        assertTrue(exists(get(tgtDir, rootFilename + ".ts")));

        // Sub-folder
        String subFilename = "sub/SubDto";
        assertTrue(exists(get(srcDir, subFilename + ".java")));
        assertTrue(exists(get(tgtDir, subFilename + ".ts")));
    }

}
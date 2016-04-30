package fr.pinguet62.java2typescript;

import static java.nio.file.Paths.get;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import fr.pinguet62.java2typescript.converter.FileConverter;

@Mojo(name = "generate", defaultPhase = GENERATE_SOURCES)
public final class GeneratorMojo extends AbstractMojo {

    @Parameter(property = "java2typescript.srcDir")
    private String srcDir;

    @Parameter(property = "java2typescript.tgtDir")
    private String tgtDir;

    /**
     * Convert the Java file to TypeScript file.
     * @param srcPath
     *            The {@link Path} to source Java file.
     * @param tgtPath
     *            The {@link Path} to target TypeScript file.
     */
    private void convertFile(Path srcPath, Path tgtPath) {
        // Folder
        try {
            Files.createDirectories(tgtPath.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Error creating intermediate folders", e);
        }

        // File
        try {
            List<String> java = Files.readAllLines(srcPath);
            List<String> typeScript = new FileConverter().apply(java);
            Files.write(tgtPath, typeScript);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Execute the Maven plugin. */
    public void execute() throws MojoExecutionException {
        getLog().info("Source Java directory: " + srcDir);
        getLog().info("Target TypeScript directory: " + tgtDir);

        try {
            // srcDir = "src/main/java/fr/pinguet62/test/dto"
            Files.walk(get(srcDir)).filter(p -> p.toString().endsWith("Dto.java")).forEach(this::resolvePathAndLaunchConversion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resolve paths and {@link #convertFile(Path, Path) run conversion}.
     * @param srcPath
     */
    private void resolvePathAndLaunchConversion(Path srcPath) {
        new PathResolver(srcDir, srcPath);

        getLog().debug("Source file to convert: " + srcPath); // srcPath = src\test\resources\aggregation\fr\pinguet62\java2typescript\dto\sub\OtherDto.java

        Path subPath = get(srcDir).relativize(srcPath); // subPath = sub/OtherDto.java
        getLog().debug("Sub path of resource: " + subPath);

        Path tgtPath = get(tgtDir).resolve(subPath);
        tgtPath = get(removeExtension(tgtPath.toString()) + ".ts"); // .java to .ts
        getLog().debug("Target generated file: " + tgtPath);

        convertFile(srcPath, tgtPath);
    }

    // TODO tmp
    void setSrcDir(String srcDir) {
        this.srcDir = srcDir;
    }

    // TODO tmp
    void setTgtDir(String tgtDir) {
        this.tgtDir = tgtDir;
    }

}
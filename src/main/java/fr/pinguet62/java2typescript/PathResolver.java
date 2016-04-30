package fr.pinguet62.java2typescript;

import static java.nio.file.Paths.get;

import java.nio.file.Path;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

public final class PathResolver {

    private static final Log LOGGER = new SystemStreamLog();

    private final Path srcDir; // src/main/java/fr/pinguet62/java2typescript/dto/sub/

    private final Path srcFile; // src/main/java/fr/pinguet62/java2typescript/dto/sub/ExempleDto.java

    private final Path pathToFile; // ./sub/ExempleDto.java

    private final Path pathToFolder; // ./sub/
    private final String filename;

    private Path tgtDir; // target/out/

    public PathResolver(String srcDir, Path srcFile) {
        this.srcDir = get(srcDir);
        LOGGER.debug("[configuration: srcDir] Source Java folder: " + srcDir);

        this.srcFile = srcFile;
        LOGGER.debug("[srcFile] Source Java file: " + srcFile);

        pathToFile = get(srcDir).relativize(srcFile);
        LOGGER.debug("[subPath] Path to file: " + srcDir);

        pathToFolder = pathToFile.getParent();
        LOGGER.debug("[pathToFolder] Parent of sub-folder: " + pathToFolder);

        filename = srcFile.getFileName().toString();
        LOGGER.debug("[filename] Name of file: " + filename);
    }

    public Path getTgtFile() {
        return tgtDir.resolve(pathToFile).resolve(get(filename + ".ts"));
    }

}
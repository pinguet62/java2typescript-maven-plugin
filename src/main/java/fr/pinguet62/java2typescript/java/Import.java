package fr.pinguet62.java2typescript.java;

import static fr.pinguet62.java2typescript.util.RegexApplier.from;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.base.Objects;

public final class Import {

    private final String fullClassName;

    public Import(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    /** The class name, without package. */
    public String getClassName() {
        return from(fullClassName).extract("[^.]+$").toString();
    }

    /** The package name, without class name. */
    public String getPackage() {
        return from(fullClassName).remove("[^.]+$").toString();
    }

    /** The full name, with package and class name. */
    public String getFullName() {
        return fullClassName;
    }

    /**
     * The TypeScript declaration.
     * @param packag
     *            Package (without class name) of class who use this import.
     * @return The relative path, from origin folder, to this class.
     */
    public String getImportDeclaration(String packag) {
        Path currentPath = Paths.get(packag.replace('.', '/'));
        Path dependencyPath = Paths.get(fullClassName.replace('.', '/'));
        String relativePath = currentPath.relativize(dependencyPath).toString().replace('\\', '/');
        if (!relativePath.startsWith("../"))
            relativePath = "./" + relativePath;
        return "import {" + getClassName() + "} from '" + relativePath + "';";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!obj.getClass().equals(getClass()))
            return false;
        Import other = (Import) obj;
        return Objects.equal(fullClassName, other.fullClassName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fullClassName);
    }

    @Override
    public String toString() {
        return fullClassName;
    }

}
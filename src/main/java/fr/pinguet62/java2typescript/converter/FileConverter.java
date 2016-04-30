package fr.pinguet62.java2typescript.converter;

import static fr.pinguet62.java2typescript.util.RegexApplier.from;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import fr.pinguet62.java2typescript.java.Attribute;
import fr.pinguet62.java2typescript.java.Import;
import fr.pinguet62.java2typescript.java.Type;
import fr.pinguet62.java2typescript.parser.AttributeParser;
import fr.pinguet62.java2typescript.parser.ImportParser;

public final class FileConverter implements Function<List<String>, List<String>> {

    private String packag = "";
    private final List<Import> imports = new ArrayList<>(); // Use to link class name into field, to folder path.
    private String className;
    private final List<Attribute> attributes = new ArrayList<>();

    @Override
    public List<String> apply(List<String> java) {
        parse(java);
        return generate();
    }

    private void parse(List<String> java) {
        for (String line : java) {
            // Package
            if (line.matches("^package .*;$"))
                packag = from(line).remove("^package ").remove(";$").toString();

            // Import
            if (line.matches("^import .*;$"))
                imports.add(new ImportParser().apply(line));

            // Class
            if (line.matches("^public class .*"))
                className = from(line).remove("^(public )?(final )?class ").extract("^[^ ]+").toString();

            // Attribute
            if (line.matches("^( |\t)*private [^ ]+ [^ ]+;"))
                attributes.add(new AttributeParser(packag, imports).apply(line));
        }
    }

    private List<String> generate() {
        List<String> typescript = new ArrayList<>();

        // Imports
        Set<Import> dependencies = new HashSet<>();
        attributes.stream().map(Attribute::getType).map(Type::getDependencies).forEach(dependencies::addAll);
        dependencies.stream().map(dep -> dep.getImportDeclaration(packag)).forEach(typescript::add);

        typescript.add("");

        // Class
        typescript.add("export class " + className + " {");

        // Attributes
        for (Attribute attribute : attributes)
            typescript.add(attribute.toTypeScript());

        typescript.add("");

        typescript.add("}");

        return typescript;
    }

}
package fr.pinguet62.java2typescript.parser;

import static fr.pinguet62.java2typescript.util.RegexApplier.from;

import java.util.List;
import java.util.function.Function;

import fr.pinguet62.java2typescript.java.Attribute;
import fr.pinguet62.java2typescript.java.Import;
import fr.pinguet62.java2typescript.java.Type;

/** Parse Java line of attribute class declaration. */
public final class AttributeParser implements Function<String, Attribute> {

    private final String currentPackage;

    private final List<Import> imports;

    public AttributeParser(String currentPackage, List<Import> imports) {
        this.currentPackage = currentPackage;
        this.imports = imports;
    }

    @Override
    public Attribute apply(String line) {
        String[] typeName = from(line).remove("^( |\t)*private ").remove(";$").toString().split(" ");
        Type type = new TypeParser(currentPackage, imports).apply(typeName[0]);
        String name = typeName[1];
        return new Attribute(type, name);
    }

}
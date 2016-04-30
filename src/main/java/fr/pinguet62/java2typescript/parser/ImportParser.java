package fr.pinguet62.java2typescript.parser;

import static fr.pinguet62.java2typescript.util.RegexApplier.from;

import java.util.function.Function;

import fr.pinguet62.java2typescript.java.Import;

/** Parse Java line of {@code import} declaration. */
public final class ImportParser implements Function<String, Import> {

    @Override
    public Import apply(String line) {
        String fullClassName = from(line).remove("^import ").remove(";$").toString();
        return new Import(fullClassName);
    }

}
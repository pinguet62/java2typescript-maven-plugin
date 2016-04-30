package fr.pinguet62.java2typescript.parser;

import java.util.List;
import java.util.Stack;
import java.util.function.Function;

import fr.pinguet62.java2typescript.java.Import;
import fr.pinguet62.java2typescript.java.Type;

/** Parse the Java type, and resolve types using imports. */
public final class TypeParser implements Function<String, Type> {

    private final String currentPackage;
    
    private final List<Import> imports;

    public TypeParser(String currentPackage, List<Import> imports) {
        this.currentPackage = currentPackage;
        this.imports = imports;
    }

    @Override
    public Type apply(String type) {
        Stack<Type> queue = new Stack<>();

        Type attribute = new Type();
        queue.add(attribute);
        for (int i = 0; i < type.length(); i++) {
            char c = type.charAt(i);
            if (c == ' ') {
            } else if (c == '<') {
                Type child = new Type();
                queue.peek().getGenericArguments().add(child);
                queue.add(child); // iterate on this child
            } else if (c == ',') {
                queue.pop(); // current type terminated
                Type child = new Type(); // new child
                queue.peek().getGenericArguments().add(child); // add to parent
                queue.add(child); // iterate on this other child
            } else if (c == '>') {
                queue.pop();
            } else {
                Type current = queue.peek();
                current.setName(current.getName() + c);
            }
        }

        attribute.resolveImports(currentPackage, imports);

        return attribute;
    }

}
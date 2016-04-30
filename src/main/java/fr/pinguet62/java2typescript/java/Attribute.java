package fr.pinguet62.java2typescript.java;

public final class Attribute {

    private final String name;

    private final Type type;

    public Attribute(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String toTypeScript() {
        return "    " + name + ": " + type.toTypeScript() + ";";
    }

}
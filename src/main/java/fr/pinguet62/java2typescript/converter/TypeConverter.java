package fr.pinguet62.java2typescript.converter;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/** @see <a href="https://www.typescriptlang.org/docs/handbook/basic-types.html">Basic Types</a> */
public final class TypeConverter implements Function<String, String> {

    public static final Map<String, String> NATIVES = new HashMap<>();

    static {
        for (String java : asList("boolean", Boolean.class.getName(), Boolean.class.getSimpleName()))
            NATIVES.put(java, "boolean");
        for (String java : asList("byte", Byte.class.getName(), Byte.class.getSimpleName()))
            NATIVES.put(java, "number");
        for (String java : asList("char", Character.class.getName(), Character.class.getSimpleName()))
            NATIVES.put(java, "number");
        for (String java : asList("short", Short.class.getName(), Short.class.getSimpleName()))
            NATIVES.put(java, "number");
        for (String java : asList("int", Integer.class.getName(), Integer.class.getSimpleName()))
            NATIVES.put(java, "number");
        for (String java : asList("long", Long.class.getName(), Long.class.getSimpleName()))
            NATIVES.put(java, "number");
        for (String java : asList("float", Float.class.getName(), Float.class.getSimpleName()))
            NATIVES.put(java, "number");
        for (String java : asList("String", String.class.getName(), String.class.getSimpleName()))
            NATIVES.put(java, "string");
        for (String java : asList(Iterable.class.getName(), Iterable.class.getSimpleName(), Collection.class.getName(), Collection.class.getSimpleName(), List.class.getName(), List.class.getSimpleName()))
            NATIVES.put(java, "Array");
    }

    /**
     * @param The
     *            Java type.<br>Full or short name.<br>Without generic arguments!
     * @return The corresponding TypeScript type.<br>The same type for custom (for example DTO) types.
     */
    @Override
    public String apply(String java) {
        return NATIVES.getOrDefault(java, java);
    }

}
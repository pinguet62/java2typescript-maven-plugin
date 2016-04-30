package fr.pinguet62.java2typescript.java;

import static fr.pinguet62.java2typescript.converter.TypeConverter.NATIVES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import fr.pinguet62.java2typescript.converter.TypeConverter;

/** Composite pattern: {@link #dependency} not {@link Collection#isEmpty() empty} if generic class. */
public final class Type {

    private String name;

    private Import dependency;

    /** {@link Collection#isEmpty() Empty} for non-generic types. */
    private List<Type> arguments = new ArrayList<>();

    public Type() {
        this("");
    }

    public Type(String name) {
        this(name, new ArrayList<>());
    }

    public Type(String name, List<Type> arguments) {
        if (name.contains("<") || name.contains(">"))
            throw new IllegalArgumentException("Use 'arguments' argument to define generic arguments.");
        this.name = name;
        this.arguments = arguments;
    }

    public List<Type> getGenericArguments() {
        return arguments;
    }

    public List<Import> getDependencies() {
        List<Import> dependencies = new ArrayList<>();
        if (dependency != null)
            dependencies.add(dependency);
        arguments.stream().map(Type::getDependencies).forEach(dependencies::addAll);
        dependencies.removeIf(dep -> NATIVES.containsKey(dep.getFullName())); // TODO Best place
        return dependencies;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        if (!name.contains("."))
            return name;
        String[] packagesName = name.split("\\.");
        return packagesName[packagesName.length - 1];
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Replace short class name to full name with package. */
    public void resolveImports(String currentPackage, List<Import> imports) {
        if (!name.contains(".")) {
            // imports.stream().filter(i -> i.getClassName().equals(name)).findAny().ifPresent(impor -> {
            // name = impor.getFullName();
            // dependency = impor;
            // });
            Optional<Import> impor = imports.stream().filter(i -> i.getClassName().equals(name)).findAny();
            if (impor.isPresent()) { // import => other package
                Import importFound = impor.get();
                name = importFound.getFullName();
                dependency = importFound;
            } else { // no import => same package
                name = ("".equals(currentPackage) ? "" : currentPackage + ".") + name;
                if (!NATIVES.containsKey(name))
                    dependency = new Import(name);
            }
        }
        arguments.forEach(arg -> arg.resolveImports(currentPackage, imports));
    }

    /** @see #name */
    @Override
    public String toString() {
        return format(t -> t.getShortName());
    }

    /** @see #getShortName() */
    public String toTypeScript() {
        return format(t -> new TypeConverter().apply(t.getShortName()));
    }

    private String format(Function<Type, String> namingConverter) {
        if (arguments.isEmpty())
            return namingConverter.apply(this);

        StringBuilder sb = new StringBuilder(namingConverter.apply(this)).append("<");
        for (Iterator<Type> it = arguments.iterator(); it.hasNext();) {
            Type child = it.next();
            sb.append(child.format(namingConverter));
            if (it.hasNext())
                sb.append(", ");
        }
        return sb.append(">").toString();
    }

}